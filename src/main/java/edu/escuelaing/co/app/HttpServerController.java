package edu.escuelaing.co.app;

import java.io.PrintWriter;
import java.net.Socket;

/**
 * Clase que sirve de controlador del servidor web
 */
public class HttpServerController {

    /**
     * Metodo que inicia el servidor web
     * 
     * @param args
     */
    public static void main(String[] args) {
        HttpServer servidor = HttpServer.getInstance();
        try {
            if (args[0].equals("start")) {
                servidor.start();
            } else if (args[0].equals("stop")) {
                Socket echoSocket = new Socket("127.0.0.1", 35000);
                PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
                out.println("exit");
                System.out.println("Se ha apagado el servidor web");
                out.close();
                echoSocket.close();
            } else {
                System.out.println("Solo se acepta como argumento stop o start");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
