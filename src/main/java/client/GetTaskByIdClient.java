package client;

import com.google.gson.Gson;
import dtos.PostRequest;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static utils.readPrettyResponseUtils.readResponse;

public class GetTaskByIdClient {
    public static void searchIdClient() {
        try {
            String taskId = "1";
            URL url = URI.create("http://localhost:7000/tasks/" + taskId).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            System.out.println("GET /tasks/id => HTTP " + conn.getResponseCode());

            readResponse(conn);

        } catch (Exception e) {
            System.err.println("Erro ao buscar task por ID:");
            e.printStackTrace();
        }
    }
}
