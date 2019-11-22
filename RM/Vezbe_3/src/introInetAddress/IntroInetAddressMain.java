package introInetAddress;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class IntroInetAddressMain {

    public static void main(String[] args) {

        InetAddress address;
        Inet4Address ipv4addr;
        Inet6Address ipv6addr;

        try {
            System.out.println(InetAddress.getByName("www.google.com"));
            System.out.println(InetAddress.getByName("www.v6.facebook.com"));
            System.out.println(Arrays.toString(InetAddress.getAllByName("google.com")));
            System.out.println(Arrays.toString(InetAddress.getAllByName("www.v6.facebook.com")));
            System.out.println(InetAddress.getByName("208.201.239.101"));

            System.out.println();

            System.out.println(InetAddress.getLocalHost());
            System.out.println(Arrays.toString(InetAddress.getByName("www.math.rs").getAddress()));
            try {
                ipv4addr = (Inet4Address) InetAddress.getByName("ipv6.google.com");
            } catch (ClassCastException e) {
                System.out.println("Cast failed");
            }

            ipv6addr = (Inet6Address) InetAddress.getByName("ipv6.google.com");
            System.out.println(ipv6addr.getHostName());
            System.out.println(ipv6addr.getCanonicalHostName());
            System.out.println(ipv6addr.getHostAddress());
            System.out.println();

            InetAddress matfShort = InetAddress.getByName("www.math.rs");
            InetAddress matfFull = InetAddress.getByName("www.matf.bg.ac.rs");

            if (matfShort.equals(matfFull)) {
                System.out.println("Same");
            } else {
                System.out.println("Different");
            }

            System.out.println(matfShort);
            System.out.println(matfFull);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
