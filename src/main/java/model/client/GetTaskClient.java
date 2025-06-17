package model.client;

import com.google.gson.Gson;
import dtos.PostRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import static utils.readPrettyResponseUtils.readResponse;


public class GetTaskClient {
    public static void list() {
        try {
            URL url = URI.create("http://localhost:7000/tasks").toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json; charset=UTF-8");

            System.out.println("GET /tasks => HTTP " + conn.getResponseCode());

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine).append('\n');
            }

            in.close();
            conn.disconnect();

            readResponse(conn);

        } catch (Exception e) {
            System.err.println("Erro ao executar GET /tasks:");
        }
    }
}
