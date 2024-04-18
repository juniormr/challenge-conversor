package exchangeapp.utils;

import com.google.gson.Gson;
import exchangeapp.models.Info;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ExchangeAPI {
    public Info newInfo (){

        URI apiURL = URI.create("https://v6.exchangerate-api.com/v6/d72c480dd02138c5b9ff423b/latest/USD");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(apiURL)
                .build();
        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return new Gson().fromJson(response.body(), Info.class);

        } catch (Exception e) {
            throw new RuntimeException("Error al conectar con la API");
        }
    }

}
