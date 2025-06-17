package model.client;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;


public class GetTaskByIdClient {
    public static void searchIdClient() {
        try {
            URL url = URI.create("http://localhost:7000/tasks/").toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int status = conn.getResponseCode();

        } catch (Exception e) {
            System.err.println("Erro ao buscar task por ID:");
            e.printStackTrace();
        }
    }
}
