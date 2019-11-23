package introClientSockets;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocketIntro {

    public static void main(String[] args) {
        try (Socket sock = new Socket("hostname", 80)){
            OutputStream out = new BufferedOutputStream(sock.getOutputStream());
            InputStream in = new BufferedInputStream(sock.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("The specified hostname is unknown.");
        } catch (IOException e) {
            System.err.println("Connection failed");
        }
    }
}
