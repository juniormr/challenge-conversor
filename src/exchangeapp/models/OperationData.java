package exchangeapp.models;

public record OperationData(
        String currencyFrom,
        double amountToConvert,
        String currencyTo,
        double result,
        String date

        ) {

}
