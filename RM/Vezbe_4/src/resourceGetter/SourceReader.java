package resourceGetter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class SourceReader {

    private static final String URL_STRING = "http://poincare.matf.bg.ac.rs/~ivan_ristovic";

    public static void main(String[] args) throws IOException {

        URL u = new URL(URL_STRING);
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(u.openStream())
        )){
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        URLConnection uc = u.openConnection();
        String encoding = uc.getContentEncoding();
        if (encoding == null)
            encoding = "UTF-8";

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        uc.getInputStream(),
                        encoding
                )
        )) {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
