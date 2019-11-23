package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ChatServer {

    static final int SERVER_TEST_PORT = 12345;

    public static void main(String[] args) {
        ChatServer server = new ChatServer(SERVER_TEST_PORT);
        server.execute();
    }

    private int port;
    private Set<UserThread> users = new HashSet<>();

    private ChatServer(int port) {
        this.port = port;
    }

    private void execute() {
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            System.err.println("Chat server is listening on port: " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.err.println("Client connected.");

                UserThread user = new UserThread(socket, this);
                this.users.add(user);
                user.start();
            }

        } catch (IOException e) {
            System.err.println("Server errored: " + e.getMessage());
            e.printStackTrace();
        }
    }

    void broadcast(String message, UserThread except) {
        this.users.stream()
                .filter(u -> u!=except)
                .forEach(u -> u.sendMessage(message));
        ;
    }

    void remove(UserThread user) {
        String username = user.getNickname();
        this.users.remove(user);
        System.err.println("Client disconnected: " + username);
    }

    List<String> getUserNames() {
        return this.users.stream()
                .map(UserThread::getNickname)
                .collect(Collectors.toList())
                ;
    }
}
