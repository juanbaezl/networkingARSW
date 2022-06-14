package edu.escuelaing.co.app;

import java.io.*;
import java.net.*;

public class URLReader {
    public static void main(String[] args) throws Exception {
        URL google = new URL("https://ldbn.is.escuelaing.edu.co/");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(google.openStream()))) {
            String inputLine = null;
            while ((inputLine = reader.readLine()) != null) {
                System.out.println(inputLine);
            }
        } catch (IOException x) {
            System.err.println(x);
        }
    }
}
