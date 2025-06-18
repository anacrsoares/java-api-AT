import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.javalin.Javalin;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.TaskService;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class JavalinMainTest {
    private TaskService taskService;
    Javalin app;
    int PORT;

    @BeforeEach
    public void setUp(){
        TaskService taskService = new TaskService();
        taskService.clear(); // garante lista limpa
    }

    @Test
    @DisplayName("ex7 - teste hello endpoint")
    public void testHelloEndpoint() throws Exception {
        // ARRANGE
        URL url = URI.create("http://localhost:7000/hello").toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int statusCode = connection.getResponseCode();

        // ACT
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        // ASSERT
        assertEquals(200, statusCode, "Status code deve ser 200");
        assertEquals("Olá, Javalin", response.toString(), "Resposta deve ser 'Olá, Javalin'");
    }

    @Test
    @DisplayName("ex8 - teste post endpoint")
    public void testPostEndpoint_deveRetornar201() throws Exception {
        // ARRANGE
        URL url = URI.create("http://localhost:7000/tasks").toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        // ACT
        String newTask = """
            {
                "titulo": "Criada no teste",
                "descricao": "Teste POST",
                "feito": false,
                "prioridade": "alta"
            }
        """;

        try (DataOutputStream out = new DataOutputStream(connection.getOutputStream())) {
            out.write(newTask.getBytes(StandardCharsets.UTF_8));
        }

        // ASSERT
        int status = connection.getResponseCode();
        assertEquals(201, status, "O status retornado deve ser 201 Created");

        // Verifica conteúdo retornado
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = reader.lines().reduce("", (acc, line) -> acc + line);
        reader.close();

        JsonObject json = JsonParser.parseString(response).getAsJsonObject();
        assertEquals("Criada no teste", json.get("titulo").getAsString());
        assertEquals("Teste POST", json.get("descricao").getAsString());
        assertEquals(false, json.get("feito").getAsBoolean());
        assertEquals("alta", json.get("prioridade").getAsString());
    }

    @Test
    @DisplayName("ex9 - GET com path param")
    public void testGetTaskById_deveRetornarNovoItemCriadoPeloSeuId() throws Exception {
        // ARRANGE
        URL url = URI.create("http://localhost:7000/tasks").toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        Task task = new Task();
        task.titulo = "Buscar por ID";
        task.descricao = "Criada pelo teste";
        task.feito = false;
        task.prioridade = "alta";

        TaskService taskService = new TaskService();
        taskService.addTask(task);

        int id = task.id;

        // ACT
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // ASSERT: Verifica se o status é 200
        assertEquals(200, conn.getResponseCode());

        // ASSERT — verifica o conteúdo da resposta
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String response = reader.lines().reduce("", (acc, line) -> acc + line);
        reader.close();

        JsonObject json = JsonParser.parseString(response).getAsJsonObject();
        assertEquals("1", json.get("id").getAsString());
        assertEquals("Buscar por ID", json.get("titulo").getAsString());
        assertEquals("Criada pelo teste", json.get("descricao").getAsString());
        assertEquals(false, json.get("feito").getAsBoolean());
        assertEquals("alta", json.get("prioridade").getAsString());

    }


    @Test
    @DisplayName("ex10 - GET")
    public void testGet_deveRetornarArrayNaoVazio() throws Exception {
        // ARRANGE
        Task task = new Task();
        task.titulo = "Tarefa da listagem";
        task.descricao = "Teste de GET";
        task.feito = false;
        task.prioridade = "média";

        TaskService taskService = new TaskService();
        taskService.addTask(task);

        // ACT
        URL url = URI.create("http://localhost:7000/tasks").toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // ASSERT: Verifica status
        assertEquals(200, conn.getResponseCode());
}
}