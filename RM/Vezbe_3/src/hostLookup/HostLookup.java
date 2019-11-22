package hostLookup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostLookup {

    public static void main(String[] args) {

        if (args.length > 0) {
            for (String arg: args) {
                System.out.println(lookup(arg));
            }
        } else {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter names and IP addresses. Enter \"exit\" to quit.");

            try {
                while (true) {
                    String host = in.readLine();
                    if (host.equalsIgnoreCase("exit")) {
                        break;
                    }
                    System.out.println(lookup(host));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String lookup(String host) {

        InetAddress node = null;

        try {
            node = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if (isHostName(host)) {
            return node.getHostAddress();
        } else {
            return node.getHostName();
        }
    }

    private static boolean isHostName(String host) {
        if (host.indexOf(':') != -1) {
            return false;
        }

        return  host.chars().anyMatch(c -> !Character.isDigit(c) && c != '.');
    }
}
