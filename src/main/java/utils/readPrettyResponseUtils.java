package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class readPrettyResponseUtils {
    public static void readResponse(HttpURLConnection conn){
        try{
            // response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;
            StringBuilder responseBody = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                responseBody.append(inputLine).append("\n");
            }

            // close resources
            in.close();
            conn.disconnect();

            // Parse com Gson
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(responseBody.toString(), JsonObject.class);
            JsonArray entitiesArray = jsonObject.getAsJsonArray("entities");

            // pretty print
            Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
            String prettyJson = prettyGson.toJson(entitiesArray);

            System.out.println("Entidades:");
            System.out.println(prettyJson);

        } catch (Exception e) {
            System.err.println("Erro ao acessar a API: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
