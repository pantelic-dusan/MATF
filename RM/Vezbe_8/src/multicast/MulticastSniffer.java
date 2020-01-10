package multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class MulticastSniffer {

    public static void main(String[] args) throws UnknownHostException {
        InetAddress group = InetAddress.getByName("all-systems.mcast.net");
        int port = 4000;

        MulticastSocket ms = null;

        try {
            ms = new MulticastSocket(port);
            ms.joinGroup(group);
            byte[] buf = new byte[8192];

            while (true) {
                DatagramPacket dp = new DatagramPacket(buf, buf.length);
                ms.receive(dp);
                String s = new String(dp.getData(), StandardCharsets.ISO_8859_1);
                System.out.println(s.substring(0, s.indexOf(0)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ms != null) {
                try {
                    ms.leaveGroup(group);
                    ms.close();
                } catch (IOException e) {
                    // ignored
                }
            }
        }

    }
}
