import exchangeapp.models.Info;
import exchangeapp.models.OperationData;
import exchangeapp.utils.DatabaseHandlers;
import exchangeapp.utils.EnterCurrency;
import exchangeapp.utils.OperationDate;
import exchangeapp.utils.ExchangeAPI;


import java.text.DecimalFormat;
import java.util.*;

public class ExchangeApp {
    static final String SUPPORT = "https://www.exchangerate-api.com/docs/supported-currencies";

    public static void main(String[] args) {

        String greeting1 = "***Bienvenido al conversor de moneda***";
        String greeting2 = "Monedas comunes: USD, EUR, ARS, BRL, COP, VES";
        String menu = """
                Seleccione:
                1- Convertir moneda
                2- Mostrar historial de conversiones
                3- Borrar historial
                7- Salir
                """;
        String g1 = "Moneda que desee convertir: ";
        String g2 = "Moneda a la que desee convertir: ";
        String currencyFrom = "";
        String currencyTo;
        String option;
        OperationData op;


        double value, convertedValue;
        DecimalFormat df = new DecimalFormat("#.##");
        Scanner scan = new Scanner(System.in);
        ExchangeAPI Exchange = new ExchangeAPI();
        DatabaseHandlers dbHandler = new DatabaseHandlers();
        ArrayList<OperationData> db;

        try {
            Info rates = Exchange.newInfo(); //Get data from API once
            db = dbHandler.getDatabase();

            //Checks if convertion rates from USD were succesfully stored
            if (Exchange.newInfo().result().equals("success")) {
                while (!currencyFrom.equals("7")) {
                    System.out.println(greeting1);
                    System.out.println(menu);
                    option = scan.nextLine();

                    switch (option) {
                        case "1" -> {
                            while (!option.equals("7")) {
                                System.out.println(greeting2);
                                System.out.println("Para más monedas: " + SUPPORT);
                                System.out.println(g1);
                                currencyFrom = EnterCurrency.exist(rates.conversion_rates(), scan);

                                System.out.println(g2);
                                currencyTo = EnterCurrency.exist(rates.conversion_rates(), scan);

                                System.out.println("Ingrese cantidad de " + currencyFrom + " a convertir a " + currencyTo + ": ");
                                value = Double.parseDouble(scan.nextLine());
                                convertedValue = value * rates.conversion_rates().get(currencyTo) / rates.conversion_rates().get(currencyFrom); //Base USD convertion
                                System.out.println("La cantidad de " + value + currencyFrom + " es equivalente a: " + df.format(convertedValue) + currencyTo);
                                if (db.get(0).result() == 0){
                                    db.clear();
                                }
                                op = new OperationData(currencyFrom, value, currencyTo, convertedValue, OperationDate.getDate());
                                db.add(op);
                                dbHandler.putDatabase(db);

                                System.out.println("Presione enter para continuar o ingrese 7 para salir");
                                option = scan.nextLine();
                            }
                        }
                        case "2" -> {
                            if (db.get(0).result() == 0) {
                                System.out.println("No se han realizado operaciones");
                            } else {
                                System.out.println("Historial");
                                db.sort(Comparator.comparing(OperationData::date).reversed());
                                db.forEach((data) ->
                                        System.out.println(data.amountToConvert() + data.currencyFrom() + " a " + df.format(data.result()) + data.currencyTo() + ", fecha: " + data.date())
                                );
                            }
                            System.out.println("Presione enter para continuar o ingrese 7 para salir");
                            currencyFrom = scan.nextLine();
                        }
                        case "3" -> {
                            System.out.println("Borrando historial");
                            op = new OperationData("", 0, "", 0, "");
                            db.clear();
                            db.add(op);
                            dbHandler.putDatabase(db);
                            System.out.println("Presione enter para continuar o ingrese 7 para salir");
                            currencyFrom = scan.nextLine();
                        }
                        case "7" -> currencyFrom = "7";
                        default -> System.out.println("Ingrese opción válida");
                    }

                }
            } else System.out.println("No hay acceso a los valores de conversion con respecto a USD");


        } catch (
                RuntimeException e) {
            System.out.println(e.getMessage());
        }
        scan.close();
    }
}




