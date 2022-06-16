package edu.escuelaing.co.app;

import java.net.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;

/**
 * Clase que crea un servidor web con instancia unica
 */
public class HttpServer {

    // Singleton servidor
    private static HttpServer _instance = new HttpServer();

    // Bandera de salida
    private static boolean bandera = true;

    // Errores
    private static String ERROR404 = "HTTP/1.1 404 Not Found \r\n\r\n" + "<!DOCTYPE html>" + "<html>"
            + "<h1>No se pudo encontrar el recurso</h1>" + "</html>";
    private static String ERROR501 = "HTTP/1.1 501 Not Implemented\r\n\r\n" + "<!DOCTYPE html>" + "<html>"
            + "<h1>No se ha implementado</h1>" + "</html>";

    /**
     * Metodo que retorna la instancia del servidor
     * 
     * @return servidor
     */
    public static HttpServer getInstance() {
        return _instance;
    }

    /**
     * Metodo que inicia el servidor web y acepta sockets
     * Para parar este se debe escribir a este servidor con echo client o math
     * client la palabra 'exit'
     * 
     * @throws IOException
     */
    public void start() throws IOException {
        ExecutorService poolDeHilos = Executors.newFixedThreadPool(10);
        ServerSocket serverSocket = startServer();
        while (bandera) {
            Socket clientSocket = startSocket(serverSocket);
            RequestProcessor processor = new RequestProcessor(clientSocket, bandera);
            poolDeHilos.execute(processor);
        }
        serverSocket.close();
    }

    /**
     * Metodo que inicia el servidor web
     * 
     * @return socket del servidor
     */
    private ServerSocket startServer() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(getPort());
        } catch (IOException e) {
            System.err.println("Could not listen on port:");
            System.exit(1);
        }
        return serverSocket;
    }

    /**
     * Metodo que inicia los socket
     * 
     * @param serverSocket servidor al cual se le va a pedir que acepte las
     *                     conexiones
     * @return socket
     */
    private Socket startSocket(ServerSocket serverSocket) {
        Socket clientSocket = null;
        try {
            System.out.println("Listo para recibir ...");
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        return clientSocket;
    }

    /**
     * Metodo que da respuesta al cliente dada una url
     * 
     * @param path         Recurso al que desea acceder el cliente
     * @param clientSocket Conexion con el cliente
     * @throws IOException
     */
    public static void response(String path, Socket clientSocket) throws IOException {
        String outputLine, encabezado = "HTTP/1.1 200 OK\r\n", extension = path.substring(path.indexOf(".") + 1);
        PrintWriter out;
        File archivo = new File(path);
        if (archivo.exists()) {
            if (extension.equals("png") || extension.equals("jpg") || extension.equals("gif")
                    || extension.equals("jpeg")) {
                DataOutputStream binaryOut;
                binaryOut = new DataOutputStream(clientSocket.getOutputStream());
                byte[] imagen = lectorImagen(archivo);
                outputLine = encabezado
                        + "Content-Type: image/" + extension + "\r\n"
                        + "Content-Length: " + imagen.length + "\r\n"
                        + "\r\n";
                binaryOut.writeBytes(outputLine);
                System.out.println("Respuesta: " + encabezado);
                binaryOut.write(imagen);
                binaryOut.close();
                binaryOut.close();
            } else if (extension.equals("html") || extension.equals("js")) {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                outputLine = encabezado
                        + "Content-Type: text/" + extension + "\r\n"
                        + "\r\n"
                        + lectorArchivo(archivo);
                out.println(outputLine);
                System.out.println("Respuesta: " + encabezado);
                out.close();
            } else {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println(ERROR501);
                System.out.println("Respuesta: " + "HTTP/1.1 501 Not Implemented");
                out.close();
            }
        } else {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println(ERROR404);
            System.out.println("Respuesta: " + "HTTP/1.1 404 Not Found");
            out.close();
        }
    }

    /**
     * Metodo que lee la peticion del cliente
     * 
     * @param in BufferedReader de la peticion
     * @return Path al cual quiere acceder el cliente
     * @throws IOException
     */
    public static String lecturaRequest(BufferedReader in) throws IOException {
        String inputLine = "", path = "";
        boolean firstLine = true;
        while ((inputLine = in.readLine()) != null) {
            if (firstLine && inputLine.startsWith("GET")) {
                path = inputLine.split(" ")[1].substring(1);
                System.out.println("Path: " + path);
                firstLine = false;
            }
            System.out.println("Received: " + inputLine);
            if (inputLine.equals("exit"))
                bandera = false;
            if (!in.ready())
                break;
        }
        return path;
    }

    /**
     * Metodo que lee un archivo de tipo texto
     * 
     * @param archivo Archivo a buscar
     * @return String del contenido del archivo
     * @throws FileNotFoundException
     */
    private static String lectorArchivo(File archivo) throws FileNotFoundException {
        String file = "";
        Scanner obj;
        obj = new Scanner(archivo);
        while (obj.hasNextLine()) {
            file += obj.nextLine();
        }
        obj.close();
        return file;
    }

    /**
     * Metodo que lee un archivo de tipo imagen
     * 
     * @param archivo Archivo a buscar
     * @return Array de bytes de la imagen
     * @throws IOException
     */
    private static byte[] lectorImagen(File archivo) throws IOException {
        byte[] imagen = new byte[0];
        FileInputStream inputImage = new FileInputStream(archivo);
        byte[] bytes = new byte[(int) archivo.length()];
        inputImage.read(bytes);
        inputImage.close();
        imagen = bytes;
        return imagen;
    }

    private int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt((System.getenv("PORT")));
        }
        return 35000;
    }
}
