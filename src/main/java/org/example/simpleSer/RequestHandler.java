package org.example.simpleSer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private final Socket clientSocket;
    private final SimpleServer server;

    public RequestHandler(Socket socket, SimpleServer server) {
        this.clientSocket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            OutputStream out = clientSocket.getOutputStream();

            // Parse HTTP request
            String requestLine = in.readLine();
            if (requestLine == null) return;

            String[] requestParts = requestLine.split(" ");
            String method = requestParts[0];
            String path = requestParts[1];

            // Route requests manually
            String response = handleRequest(method, path);

            // Send HTTP response
            out.write(("HTTP/1.1 200 OK\r\n").getBytes());
            out.write(("Content-Type: text/html; charset=UTF-8\r\n").getBytes());
            out.write(("\r\n").getBytes());
            out.write(response.getBytes());
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String handleRequest(String method, String path) {
        // Manual routing
        if ("GET".equals(method)) {
            if ("/users".equals(path)) {
                return server.getUserService().getAllUsers();
            } else if (path.startsWith("/users/")) {
                String userId = path.substring(7);
                return server.getUserService().getUserById(userId);
            }
        }

        return "<html><body><h1>404 Not Found</h1></body></html>";
    }

}
