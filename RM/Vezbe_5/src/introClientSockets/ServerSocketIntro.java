package introClientSockets;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketIntro {

    public static void main(String[] args) {
        int port = 9000;

        try (ServerSocket server = new ServerSocket(port)){
            Socket client = server.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ServerSocket server = new ServerSocket()){
            server.bind(new InetSocketAddress(port));

            while (true) {
                Socket client = server.accept();
                serve(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void serve(Socket client) {

    }
}
