package edu.escuelaing.co.app;

import java.io.IOException;

public class HttpServerController {

    public static void main(String[] args) {
        HttpServer servidor = HttpServer.getInstance();
        try {
            servidor.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
