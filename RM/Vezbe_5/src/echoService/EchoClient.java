package echoService;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class EchoClient {

    public static void main(String[] args) {
        String hostname = "localhost";

        System.err.println("Connecting to " + hostname);

        try (Socket sock = new Socket(hostname, EchoServer.DEFAULT_PORT);
             BufferedReader networkIn = new BufferedReader(
                     new InputStreamReader(sock.getInputStream(), StandardCharsets.UTF_8)
             );
             BufferedWriter networkOut = new BufferedWriter(
                     new OutputStreamWriter(sock.getOutputStream(), StandardCharsets.UTF_8)
             );
             BufferedReader userIn = new BufferedReader(
                     new InputStreamReader(System.in)
             )
        ){
            System.out.println("Connected to the echo server @ " + hostname);

            while (true) {
                String line = userIn.readLine();
                if (line.trim().equalsIgnoreCase("exit"))
                    break;

                networkOut.write(line);
                networkOut.newLine();
                networkOut.flush();

                System.out.println(networkIn.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.err.println("Disconnected from " + hostname);

    }
}
