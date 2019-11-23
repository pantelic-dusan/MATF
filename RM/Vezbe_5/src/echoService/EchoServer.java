package echoService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    public final static int DEFAULT_PORT = 4444;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(DEFAULT_PORT)) {
            System.err.println("Started server on port " + DEFAULT_PORT);
            while (true) {
                System.err.println("Listening for clients...");
                Socket client = server.accept();
                System.err.println("Client accepted! Dispatching thread...");
                new Thread(new ClientHandlerRunnable(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
