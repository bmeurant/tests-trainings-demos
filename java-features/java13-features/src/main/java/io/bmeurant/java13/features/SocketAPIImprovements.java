package io.bmeurant.java13.features;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketAPIImprovements {

    public static void main(String[] args) {
        System.out.println("This demo shows standard Socket usage, which benefits from Java 13's internal improvements.");

        final int PORT = 8080;

        // Start a simple server in a separate thread
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("\nServer listening on port " + PORT + "...");
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    System.out.println("Client connected: " + clientSocket.getInetAddress());
                    String clientMessage;
                    while ((clientMessage = in.readLine()) != null) {
                        System.out.println("Server received: " + clientMessage);
                        if ("bye".equalsIgnoreCase(clientMessage)) {
                            out.println("Goodbye!");
                            break;
                        } else {
                            out.println("Server echoes: " + clientMessage);
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Server error: " + e.getMessage());
            }
        }).start();

        // Give the server a moment to start
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Start a simple client
        try (Socket clientSocket = new Socket("localhost", PORT);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            System.out.println("\nClient connected to localhost:" + PORT);

            out.println("Hello Server!");
            System.out.println("Client received: " + in.readLine());

            out.println("How are you?");
            System.out.println("Client received: " + in.readLine());

            out.println("bye");
            System.out.println("Client received: " + in.readLine());

        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        }
    }
}
