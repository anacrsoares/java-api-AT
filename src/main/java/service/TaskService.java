package service;

import model.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskService {

        List<Task> tasks = new ArrayList<>();
        AtomicInteger idCounter = new AtomicInteger(1); // para gerar IDs únicos

        // Metodo add Task a lista
        public Task addTask(Task task){
            task.id = idCounter.getAndIncrement();
            task.dataCriacao = LocalDateTime.now();
            tasks.add(task);
            return task;
        }

        // Metodo retornar lista de tasks
        public List<Task> listTasks() {
            return tasks;
        }

        // Metodo filtro de tarefas por ID
        public Task searchId(int id) {
            return tasks.stream()
                    .filter(t -> t.id == id)
                    .findFirst()
                    .orElse(null);
        }
}
