package edu.escuelaing.co.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestProcessor implements Runnable {

    private Socket clientSocket;
    private boolean bandera;

    public RequestProcessor(Socket clientSocket, boolean bandera) {
        this.clientSocket = clientSocket;
        this.bandera = bandera;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String path = HttpServer.lecturaRequest(in);
            if (bandera) {
                HttpServer.response(path, clientSocket);
            } else {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                System.out.println("Se ha apagado el servidor");
                out.println("El servidor web se ha apagado");
                out.close();
            }
            in.close();
            clientSocket.close();
            System.out.println(Thread.currentThread());
        } catch (Exception e) {
            System.out.println("No se pudo crear el hilo");
        }
    }

}
