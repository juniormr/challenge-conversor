package exchangeapp.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import exchangeapp.models.OperationData;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DatabaseHandlers {


    URI databaseURL = URI.create("https://exchangerecord-e1d13-default-rtdb.firebaseio.com/record.json");
    Gson gson = new GsonBuilder().create();

    public void putDatabase(ArrayList<OperationData> data) {

        HttpClient client = HttpClient.newHttpClient();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .PUT(HttpRequest.BodyPublishers.ofString(gson.toJson(data)))
                    .uri(databaseURL)
                    .build();

            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            throw new RuntimeException("Error al hacer PUT a Firebase");
        }
    }

    public ArrayList<OperationData> getDatabase() {

        Type myType = new TypeToken<ArrayList<OperationData>>() {
        }.getType();

        HttpClient client = HttpClient.newHttpClient();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(databaseURL)
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            ArrayList<OperationData> db = new Gson().fromJson(response.body(), myType);

//            db.sort(Comparator.comparing(OperationData::date));

            return db;

        } catch (Exception e) {
            throw new RuntimeException("Error al hacer GET a Firebase");
        }
    }


}
