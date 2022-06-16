package edu.escuelaing.co.app;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpClient {
    public static void main(String[] args) throws IOException {
        int numThreads = Integer.parseInt(args[0]);
        ExecutorService poolDeHilos = Executors.newFixedThreadPool(10);
        while (numThreads > 0) {
            numThreads--;
            ClientProcessor processor = new ClientProcessor(getPort());
            poolDeHilos.execute(processor);
        }
        poolDeHilos.shutdown();
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt((System.getenv("PORT")));
        }
        return 35000;
    }
}
