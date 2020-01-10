package udpSocketsIntro;

import java.io.IOException;
import java.net.*;

public class UdpSocketsIntro {

    public static void main(String[] args) {
        try (DatagramSocket ds = new DatagramSocket()) {
            InetAddress host = InetAddress.getByName("host");
            DatagramPacket send = new DatagramPacket(new byte[512], 512, host, 1234);

            DatagramPacket recv = new DatagramPacket(new byte[512], 512);

            ds.send(send);
            ds.receive(recv);

        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
