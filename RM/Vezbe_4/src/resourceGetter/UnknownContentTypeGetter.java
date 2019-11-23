package resourceGetter;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;

public class UnknownContentTypeGetter {
    private static final String URL_STRING = "https://helloacm.com/api/random/?n=128";

    public static void main(String[] args) {

        try {
            URL u = new URL(URL_STRING);
            System.out.println("I got: " + u.getContent().getClass().getName());

            Class<?>[] types = new Class[3];
            types[0] = String.class;
            types[1] = Reader.class;
            types[2] = InputStream.class;
            Object o = u.getContent(types);

            if (o instanceof String) {
                System.out.println("STRING");
                System.out.println(o);
            } else if (o instanceof Reader) {
                System.out.println("READER");
                int c;
                Reader r = (Reader) o;
                while ((c = r.read()) != -1) {
                    System.out.print(c);
                }
                r.close();
            } else if (o instanceof InputStream) {
                System.out.println("INPUT STREAM");
                int c;
                InputStream in = (InputStream) o;
                while ((c = in.read()) != -1)
                    System.out.print(c);
                in.close();
            } else {
                System.out.println("Unexpected type: " + o.getClass());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
