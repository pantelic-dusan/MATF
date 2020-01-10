package echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class EchoClient implements AutoCloseable{

    private final DatagramSocket socket;
    private final InetAddress address;


    EchoClient() throws IOException {
        this.socket = new DatagramSocket();
        this.address = InetAddress.getLoopbackAddress();

    }

    String sendEcho(String msg) throws IOException {
        byte [] buf = msg.getBytes(StandardCharsets.UTF_8);
        System.err.println("Client send: " + msg + " " + Arrays.toString(buf));

        DatagramPacket request = new DatagramPacket(buf, buf.length, this.address, EchoServer.PORT);
        this.socket.send(request);

        DatagramPacket response = new DatagramPacket(buf, buf.length);
        this.socket.receive(response);

        System.err.println("Client recv: " + Arrays.toString(response.getData()));

        // Return parsed resposnse data
        return new String(response.getData(), 0, response.getLength(), StandardCharsets.UTF_8);
    }

    @Override
    public void close() throws Exception {
        this.socket.close();
    }
}
