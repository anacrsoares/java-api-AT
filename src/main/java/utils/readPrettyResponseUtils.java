package utils;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class readPrettyResponseUtils {
    public static void readResponse(HttpURLConnection conn){
        try {
            BufferedReader in = new BufferedReader(
            new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

            // Lê a resposta
            StringBuilder responseBody = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                responseBody.append(inputLine).append("\n");
            }

            // Parse com Gson
            Gson gson = new Gson();
            JsonElement jsonElement = JsonParser.parseString(responseBody.toString());

            // pretty print
            Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
            String prettyJson = prettyGson.toJson(jsonElement);

            out.println("Entidades:");
            out.println(prettyJson);

        } catch (Exception e) {
            System.err.println("Erro ao acessar a API: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            conn.disconnect();
        }
    }
}
