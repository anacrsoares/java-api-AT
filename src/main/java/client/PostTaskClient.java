package client;

import com.google.gson.Gson;
import dtos.PostRequest;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

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

        PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        String resposta = new String(conn.getInputStream().readAllBytes());
        out.println(resposta);

        conn.disconnect();
    }
}
