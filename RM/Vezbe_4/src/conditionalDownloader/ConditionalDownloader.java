package conditionalDownloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class ConditionalDownloader {
    private static final long MILLISECONDS_PER_DAY = 24 * 60 * 60 * 1000;
    private static final String URL_STRING = "http://www.matf.bg.ac.rs/";

    public static void main(String[] args) {
        Date today = new Date();

        try {
            URL u = new URL(URL_STRING);
            URLConnection uc = u.openConnection();
            System.out.println("Will retrieve file if it's modified since " + new Date(uc.getIfModifiedSince()));
            uc.setIfModifiedSince((new Date(today.getTime()-MILLISECONDS_PER_DAY)).getTime());
            System.out.println("Will retrieve file if it's modified since " + new Date(uc.getIfModifiedSince()));
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));

            String line;
            while((line=in.readLine()) != null)
                System.out.println(line);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
