package finger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class FingerTest {

    public static void main(String[] args) throws IOException {
        URL url = new URL(null, "finger://localhost:12345/usernames", new Handler());
        var conn = url.openConnection();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null)
                System.out.println(line);
        }
    }
}
