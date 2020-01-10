package echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class EchoServer extends Thread implements AutoCloseable {

    public static final int PORT = 12345;

    private final DatagramSocket socket;


    EchoServer() throws SocketException {
        this.socket = new DatagramSocket(PORT);
    }

    @Override
    public void run() {
        System.err.println("Server started...");
        try {
            while (true) {
                byte[] buf = new byte[256];
                try {
                    DatagramPacket request = new DatagramPacket(buf, buf.length);
                    this.socket.receive(request);

                    DatagramPacket response = new DatagramPacket(buf, buf.length, request.getAddress(), request.getPort());
                    this.socket.send(response);

                    String received = this.getString(request.getData());
                    System.err.println("Server recv: " + received);
                    if (received.equalsIgnoreCase("end")) {
                        System.err.println("Server received end signal.");
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            System.err.println("Server is shutting down...");
            try {
                this.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.err.println("Server successfully closed!");
        }
    }

    @Override
    public void close() throws Exception {
        this.socket.close();
    }

    private String getString(byte[] buf) {
        if (buf == null)
            return null;
        String str = new String(buf, StandardCharsets.UTF_8);
        return str.substring(0, str.indexOf(0));
    }
}
