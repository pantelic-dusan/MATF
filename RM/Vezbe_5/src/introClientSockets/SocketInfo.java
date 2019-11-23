package introClientSockets;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketInfo {

    public static void main(String[] args) {
        try (Socket s = new Socket("www.matf.bg.ac.rs", 80)){
            System.out.println(s);
            System.out.println(s.getInetAddress());
            System.out.println(s.getPort());
            System.out.println(s.getLocalPort());
            System.out.println(s.getLocalAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
