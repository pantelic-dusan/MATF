package quote;

import java.io.IOException;
import java.net.*;

public class QuoteClient {

    public static void main(String[] args) {

        try (DatagramSocket socket = new DatagramSocket()) {
            byte [] buf = new byte[256];
            InetAddress adress = InetAddress.getLocalHost();
            DatagramPacket request = new DatagramPacket(buf, buf.length, adress, QuoteServer.PORT);
            socket.send(request);

            DatagramPacket response = new DatagramPacket(buf, buf.length);
            socket.receive(response);

            String received = new String(response.getData(), 0, response.getLength());
            System.out.println("Quote of the Moment: " + received);
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
