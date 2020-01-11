package daytime;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class Handler extends URLStreamHandler {

    public int getDefaultPort() {
        return DaytimeUrlConnection.DEFAULT_PORT;
    }

    @Override
    protected URLConnection openConnection(URL url) throws IOException {
        return new DaytimeUrlConnection(url);
    }
}
