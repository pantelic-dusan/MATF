package chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientWriteThread extends Thread {

    private PrintWriter toServer;
    private String name;

    public ClientWriteThread(String name, Socket socket) {
        this.name = name;
        try {
            this.toServer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Error getting output stream: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.toServer.println(this.name);

        try (Scanner sc = new Scanner(System.in)) {
            String text;
            do {
                System.out.printf("\r[%s]: ", this.name);
                text = sc.nextLine();
                this.toServer.println(text);
            } while (!text.equals("bye"));
        }
    }
}
