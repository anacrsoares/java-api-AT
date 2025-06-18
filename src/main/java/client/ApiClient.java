package client;

import java.io.IOException;

public class ApiClient {
    public static void main(String[] args) throws IOException {
        PostTaskClient.create(); // ex11
        GetTaskClient.list(); // ex12
        GetTaskByIdClient.searchIdClient(); // ex13
        StatusClient.getStatus(); //ex14
    }
}
