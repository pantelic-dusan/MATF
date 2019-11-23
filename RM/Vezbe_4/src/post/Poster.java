package post;

import queryBuilder.QueryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class Poster {
    private URL url;
    private QueryBuilder query;


    public Poster(URL url) {
        if (!url.getProtocol().toLowerCase().startsWith("http"))
            throw new IllegalArgumentException("Posting only works for http(s) URLs");
        this.url = url;
        this.query = new QueryBuilder(this.url.toString());
    }

    public void add(String name, String value) {
        this.query.append(name, value);
    }

    public URL getURL() {
        return this.url;
    }

    public InputStream post() throws IOException {
        URLConnection uc = url.openConnection();
        uc.setDoOutput(true);

        OutputStreamWriter out = new OutputStreamWriter(uc.getOutputStream());

        out.write(this.query.toString());
        out.flush();
        out.close();

        return uc.getInputStream();
    }
}
