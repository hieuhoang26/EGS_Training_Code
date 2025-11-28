package org.example.simpleSer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleServer {
    private static final int PORT = 8080;
    private static final int THREAD_POOL = 5;

    private final ExecutorService threadPool;

    private  ServerSocket serverSocket;

    private final UserService userService;

    public SimpleServer() {
        this.threadPool = Executors.newFixedThreadPool(THREAD_POOL);
        // Khởi tạo dependencies thủ công
        this.userService = new UserService();
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("WebServer started on port " + PORT);

            while (!Thread.currentThread().isInterrupted()) {
                Socket clientSocket = serverSocket.accept();
                threadPool.execute(new RequestHandler(clientSocket, this));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public UserService getUserService() {
        return userService;
    }

    public void stop() {
        try {
            threadPool.shutdown();
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SimpleServer server = new SimpleServer();
        server.start();
    }

}