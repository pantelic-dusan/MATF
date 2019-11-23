package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {

    public static void main(String[] args) {
        ChatClient client = new ChatClient("localhost", ChatServer.SERVER_TEST_PORT);
        System.err.println("Connecting to local port: " + ChatServer.SERVER_TEST_PORT);
        client.execute();
    }

    private String hostname;
    private int port;
    private String name;

    public ChatClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    private void execute() {
        try {
            this.setName();

            Socket socket = new Socket(this.hostname, this.port);
            System.out.println("Connected to the chat server @ " + this.hostname);

            new ClientReadThread(this.name, socket).start();
            new ClientWriteThread(this.name, socket).start();
        } catch (UnknownHostException e) {
            System.out.println("Server not found: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setName() throws IOException {
        System.out.print("Enter name: ");
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        this.name = stdin.readLine();
    }
}
