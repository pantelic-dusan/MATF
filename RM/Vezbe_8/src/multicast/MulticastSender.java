package multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class MulticastSender {

    public static void main(String[] args) throws UnknownHostException {
        InetAddress addr = InetAddress.getByName("all-systems.mcast.net");
        int port = 4000;
        byte ttl = (byte)1;

        byte[] data = "Here's some multicast data\r\n".getBytes();
        DatagramPacket dp = new DatagramPacket(data, data.length, addr, port);

        try (MulticastSocket ms = new MulticastSocket()) {
            ms.setTimeToLive(ttl);
            ms.joinGroup(addr);
            for (int i = 1; i<10;i++) {
                ms.send(dp);
            }
            ms.leaveGroup(addr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
