package headerPrinter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class HeaderPrinter {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            try {
                String line = sc.nextLine();
                if (line.trim().equals(""))
                    continue;
                if (line.equalsIgnoreCase("exit"))
                    break;

                URL u = new URL(line);
                URLConnection uc = u.openConnection();

                System.out.println("--------------------------------------");
                System.out.println("0th header: " + uc.getHeaderField(0));
                for (int i=1;;i++) {
                    String header = uc.getHeaderField(i);
                    if (header == null)
                        break;
                    System.out.println(uc.getHeaderFieldKey(i) + ": " + header);
                }
                System.out.println("--------------------------------------");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        sc.close();
    }
}
