package quote;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

public class QuoteServer extends Thread {

    static final int PORT = 12345;

    private final DatagramSocket socket;
    private BufferedReader in;

    public static void main(String[] args) throws SocketException {
        new QuoteServer().start();
    }

    private QuoteServer() throws SocketException {
        this.socket = new DatagramSocket(PORT);
        try {
            this.in = Files.newBufferedReader(Paths.get("src/quote/one_liners.txt"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.err.println("Quote server listening on port " + PORT);
        try {
            while (true) {
                byte[] buf = new byte[512];
                DatagramPacket request = new DatagramPacket(buf, buf.length);
                socket.receive(request);

                System.err.println("Received packet.");

                buf = getData();
                if (buf == null)
                    return;
                System.err.println("Sending data to client..");
                DatagramPacket response = new DatagramPacket(buf, buf.length, request.getAddress(), request.getPort());
                this.socket.send(response);
                System.err.println("Client served.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.socket.close();
        }
    }

    private byte[] getData() throws IOException {
        String data = this.in == null ? new Date().toString() : this.in.readLine();
        return  data == null ? null : data.getBytes();
    }
}
