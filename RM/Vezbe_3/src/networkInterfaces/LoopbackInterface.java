package networkInterfaces;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

public class LoopbackInterface {

    public static void main(String[] args) {
        try {
            InetAddress local = InetAddress.getLoopbackAddress();

            NetworkInterface ni = NetworkInterface.getByInetAddress(local);

            if (ni == null) {
                System.err.println("No local loopback address");
            }

            System.out.println(ni);
            System.out.printf("%s\t%s\n%s\n", ni.getName(), ni.getDisplayName(), ni.getIndex());

        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
