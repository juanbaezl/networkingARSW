package edu.escuelaing.co.app;

import java.net.*;
import java.io.*;

public class MathServer {

    static int operacion = 0;

    public static void main(String[] args) throws IOException, Exception {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(34000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 34000.");
            System.exit(1);
        }
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        String inputLine, outputLine;
        while ((inputLine = in.readLine()) != null) {
            outputLine = "Cambio de funcion: " + inputLine;
            if (inputLine.equals("fun:cos"))
                operacion = 0;
            else if (inputLine.equals("fun:sin"))
                operacion = 1;
            else if (inputLine.equals("fun:tan"))
                operacion = 2;
            else {
                try {
                    Double value = Double.valueOf(inputLine);
                    double res = operacion(value);
                    outputLine = "El resultado es: " + res;
                } catch (Exception e) {
                    outputLine = "Debe entregar un numero";
                }
            }
            printLinea(out, outputLine);
            if (outputLine.equals("chao"))
                break;
        }
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }

    public static Double operacion(Double valor) {
        double res = 0.0;
        switch (operacion) {
            case 0:
                res = Math.cos(valor);
                break;
            case 1:
                res = Math.sin(valor);
                break;
            case 2:
                res = Math.tan(valor);
                break;
            default:
                break;
        }
        return res;
    }

    public static void printLinea(PrintWriter out, String mensaje) {
        System.out.println(mensaje);
        out.println(mensaje);
    }
}
