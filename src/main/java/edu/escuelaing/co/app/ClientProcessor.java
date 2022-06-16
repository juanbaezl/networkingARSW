package edu.escuelaing.co.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientProcessor implements Runnable {

    private int port;

    public ClientProcessor(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        Socket echoSocket = reconexion();
        PrintWriter out = null;
        try {
            out = new PrintWriter(echoSocket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Error al crear PrintWriter");
            System.exit(1);
        }
        out.println("GET /resources/index.html HTTP/1.1\r\n" +
                "Host: localhost:35000\r\n" +
                "Connection: keep-alive\r\n" +
                "Cache-Control: max-age=0\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.5005.115 Safari/537.36\r\n"
                +
                "Accept-Language: en-US\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\r\n"
                +
                "Sec-GPC: 1\r\n" +
                "Sec-Fetch-Site: none\r\n" +
                "Sec-Fetch-Mode: navigate\r\n" +
                "Sec-Fetch-User: ?1\r\n" +
                "Sec-Fetch-Dest: document\r\n" +
                "Accept-Encoding: gzip, deflate, br\r\n");
        out.close();

    }

    /**
     * Metodo que realiza una conexion o reconexion si la cola esta llena
     * 
     * @return Socket de conexion
     */
    private Socket reconexion() {
        Socket echoSocket = null;
        try {
            echoSocket = new Socket("127.0.0.1", port);
        } catch (UnknownHostException e) {
            System.err.println("Don’t know about host!.");
            System.exit(1);
        } catch (IOException e) {
            return reconexion();
        }
        return echoSocket;
    }
}
