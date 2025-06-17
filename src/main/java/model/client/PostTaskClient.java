package model.client;

import com.google.gson.Gson;
import dtos.PostRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PostTaskClient {
    public static void create() throws IOException {
        String endpoint = "http://localhost:7000/tasks";
        URL url = URI.create(endpoint).toURL();

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);



        Gson gson = new Gson();
        var postRequest = new PostRequest("Criada via PostTaskClient", "Testando POST", false, "média");
        String jsonInputString = gson.toJson(postRequest);


        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input);
        }

        System.out.println("POST /tasks => " + conn.getResponseCode());
//
        String resposta = new String(conn.getInputStream().readAllBytes());

        System.out.println("Resposta da API: " + resposta);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            String resposta2 = responseBuilder.toString();
            System.out.println("Resposta da API: " + resposta2);
        }


        conn.disconnect();
    }
}
