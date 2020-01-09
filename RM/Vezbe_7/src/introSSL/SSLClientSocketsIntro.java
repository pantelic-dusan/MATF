package introSSL;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class SSLClientSocketsIntro {

    public static void main(String[] args) {
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        try (Socket socket = factory.createSocket("host", 1234)) {
            Writer out = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
            out.write("something");
            out.flush();
            out.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
