package urlIntro;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class UrlIntro {

    public static void main(String[] args) {
        URL u;

        try {
            u = new URL("http://www.matf.bg.ac.rs:8080");
            System.out.println("Host: " + u.getHost());
            System.out.println("Port: " + u.getPort());
            System.out.println();

            u = new URL("http://www.matf.bg.ac.rs/~ivan_ristovic/index.html");
            System.out.println("Host: " + u.getHost());
            System.out.println("Port: " + u.getPort());
            System.out.println("Default port: " + u.getDefaultPort());
            System.out.println("Path: " + u.getPath());
            System.out.println("Authority: " + u.getAuthority());
            System.out.println();

            u = new URL("mailto:ivan_ristovic@matf.bg.ac.rs");
            System.out.println("Path: " + u.getPath());
            System.out.println("Authority: " + u.getAuthority());
            System.out.println();

            u = new URL("http://poincare.matf.bg.ac.rs/~ivan_ristovic/");
            URLConnection uc = u.openConnection();
            System.out.println("Content type: " + uc.getContentType());
            System.out.println("Content encoding: " + uc.getContentEncoding());
            System.out.println("Date: " + new Date(uc.getDate()));
            System.out.println("Last modified: " + new Date(uc.getLastModified()));
            System.out.println("Expiration date: " + new Date(uc.getExpiration()));
            System.out.println("Content length: " + uc.getContentLength());
            System.out.println("URL: " + uc.getURL());
            System.out.println();



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
