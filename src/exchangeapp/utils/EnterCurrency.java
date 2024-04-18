package exchangeapp.utils;

import java.util.HashMap;
import java.util.Scanner;

public class EnterCurrency {
    static String currency;
    static boolean currencyExist;

    public static String exist(HashMap<String, Double> allCurrencies, Scanner scan) {

        while (!currencyExist) {

            currency = scan.nextLine().toUpperCase();

            if (allCurrencies.containsKey(currency)) {
                currencyExist = allCurrencies.containsKey(currency);

            } else {
                System.out.println("Opción inválida ingrese de nuevo: ");
                currencyExist = false;
            }
        }
        currencyExist = false;
        return currency;
    }
}
