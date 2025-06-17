import io.javalin.Javalin;
import model.Task;
import service.TaskService;

import java.time.ZonedDateTime;
import java.util.Map;

public class JavalinMain {
    public static void main(String[] args) {
        // Criando e inicializando o servidor Javalin na porta padrao 7000
        Javalin app = Javalin.create().start(7000);

        // Endpoint Hello
        app.get("/hello", context -> {
            context.contentType("text/plain; charset=UTF-8");
            context.result("Olá, Javalin");
        });

        // Endpoint status
        app.get("/status", ctx -> {
            String timestamp = ZonedDateTime.now().toString(); // ISO-8601

            ctx.json(Map.of(
                   "status", "ok",
                   "timestamp", timestamp
           ));
        });

        // Endpoint echo
        app.post("/echo", ctx -> {
            String message = ctx.body();
            ctx.json("Testando: "  + message);
        });

        // Endpoint saudacao com path param /saudacao/{nome}
        app.get("/saudacao/{nome}", context -> {
            context.contentType("text/plain; charset=UTF-8");
            String nome = context.pathParam("nome");
            context.result("Olá, " + nome);
        });

        // Endpoint saudacao com query param /saudacao?nome=Júlia
        app.get("/saudacao", context -> {
            context.contentType("text/plain; charset=UTF-8");
            String nome = context.queryParam("nome");
            context.result("Olá, " + nome);
        });

        TaskService taskService = new TaskService();
        // Post /tasks
        app.post("/tasks", ctx ->{
            Task newTask = ctx.bodyAsClass(Task.class);
            taskService.addTask(newTask);
            ctx.status(201).json(newTask);
        });

        // Get /tasks
        app.get("/tasks", ctx ->{
            ctx.json(taskService.listTasks());
        });

        // Get com path param
        app.get("/tasks/{id}", ctx -> {
            String taskId = ctx.pathParam("id");
            int id = Integer.parseInt(taskId);

            Task task = taskService.searchId(id);
            if (task != null) {
                ctx.json(task);
            } else {
                ctx.status(404).result("Tarefa não encontrada");
            }
        });


    }
}
