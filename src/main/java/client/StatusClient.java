package client;

import utils.readPrettyResponseUtils;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class StatusClient {
    public static void getStatus() {
        try {
            URL url = URI.create("http://localhost:7000/status").toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            System.out.println("GET /status => HTTP " + conn.getResponseCode());

            readPrettyResponseUtils.readResponse(conn);

        } catch (Exception e) {
            System.err.println("Erro ao acessar /status:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        getStatus();
    }
}
