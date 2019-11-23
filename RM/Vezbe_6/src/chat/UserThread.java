package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class UserThread extends Thread {
    private Socket sock;
    private ChatServer server;
    private PrintWriter toUser;
    private String name;

    UserThread(Socket socket, ChatServer server) {
        this.sock = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            BufferedReader fromUser = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
            this.toUser = new PrintWriter(this.sock.getOutputStream(), true);

            this.name = fromUser.readLine();
            this.sendMessage("Connected users: " + this.server.getUserNames());

            this.server.broadcast("New user connected: " + this.name, this);

            String clientMessage;
            do {
                clientMessage = fromUser.readLine();
                if (clientMessage == null)
                    break;
                this.server.broadcast("[" + this.name + "]: " + clientMessage, this);
            } while (!clientMessage.equals("bye"));
            this.server.remove(this);
            this.sock.close();
            this.server.broadcast(this.name + " has left the chat.", this);
        } catch (IOException e) {
            System.out.println("Error in UserThread: " + e.getMessage());
            e.printStackTrace();
        }
    }

    void sendMessage(String message) {
        if (this.toUser != null)
            this.toUser.println(message);
    }

    String getNickname() {
        return this.name;
    }
}
