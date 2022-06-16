package edu.escuelaing.co.app;

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
            servidor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
