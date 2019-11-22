package inetAddressVersion;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressVersionMain {

    public static void main(String[] args) {

        try {
            InetAddress addressv4 = InetAddress.getByName("google.com");
            System.out.println(addressv4.getHostAddress());
            printAddress(addressv4);
            System.out.println("IPv" + getVersion(addressv4));
            System.out.println();
            InetAddress addressv6 = InetAddress.getByName("ipv6.google.com");
            System.out.println(addressv6.getHostAddress());
            printAddress(addressv6);
            System.out.println("IPv" + getVersion(addressv6));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static int getVersion(InetAddress addr) {
        byte[] address = addr.getAddress();
        switch (address.length){
            case 4: return 4;
            case 16: return 6;
            default: return -1;
        }
    }

    public static void printAddress(InetAddress addr) {
        byte[] address = addr.getAddress();
        System.out.print("IP address bytes: ");
        for (byte b: address) {
            int unsignedByte = b < 0? b+256:b;
            System.out.print(unsignedByte + " ");
        }
        System.out.println();
    }
}
