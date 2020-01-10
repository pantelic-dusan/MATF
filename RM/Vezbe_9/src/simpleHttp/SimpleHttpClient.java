package simpleHttp;

import java.io.*;
import java.net.Socket;

public class SimpleHttpClient {

    public static void main(String[] args) {

        // Clients can be written using normal TCP sockets

        try (Socket s = new Socket("localhost", 12345);
             PrintWriter out = new PrintWriter(s.getOutputStream(), true);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(
                             new BufferedInputStream(s.getInputStream())
                     )
             )
        ) {
            System.err.println("Connected to server.");

            // Oversimplified HTTP request, normally we would send proper headers
            out.println("index.html");
            //out.println("serverfile.txt");

            // Read response
            String line;
            while ((line = in.readLine()) != null)
                System.out.println(line);

            // FIXME: This way of reading the response is not correct.
            // This way client assumes that the response ends with new line.
            // Proper way of reading the response would be to parse the
            // `Content-length` header and read only that amount of bytes.
            // Since we are focused on server implementation, we will skip this.

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
