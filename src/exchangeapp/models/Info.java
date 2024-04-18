package exchangeapp.models;

import java.util.HashMap;

public record Info(
        String result,
        HashMap<String, Double> conversion_rates
) {
}
