package daytimeService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class DaytimeClient {

    public static void main(String[] args) {
        String hostname = "localhost";
        System.err.println("Connecting to " + hostname);

        try (Socket sock = new Socket(hostname, DaytimeServer.DEFAULT_PORT)) {
            System.err.println("Connected!");
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(sock.getInputStream())
            )){
                String time = in.readLine().trim();
                System.out.println("It is " + time + " at " + hostname);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
