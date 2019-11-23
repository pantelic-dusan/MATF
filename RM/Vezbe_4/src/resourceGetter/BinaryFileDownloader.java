package resourceGetter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class BinaryFileDownloader {

    public static void main(String[] args) {
        try {
            URL url = new URL("http://www.matf.bg.ac.rs/images/matf.gif");
            URLConnection conn = url.openConnection();

            String contentType = conn.getContentType();
            int contentLength = conn.getContentLength();

            if (contentLength == -1 || contentType.startsWith("text"))
                throw new IOException("Content is not a binary file!");

            BufferedInputStream in = new BufferedInputStream(
                    conn.getInputStream()
            );
            String filename = url.getFile();
            filename = filename.substring(filename.lastIndexOf('/')+1);
            System.out.println(filename);

            try (BufferedOutputStream out = new BufferedOutputStream(
                    new FileOutputStream("res/"+filename)
            )) {
                for (int i=0; i<contentLength; i++) {
                    int b = in.read();
                    out.write(b);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
