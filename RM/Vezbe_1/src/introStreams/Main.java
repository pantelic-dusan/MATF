package introStreams;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

public class Main {

    public static void main(String[] args) {

        try {
            Stream s;

            OutputStream out;
            FileOutputStream fOut = new FileOutputStream("res/example.txt");
            ByteArrayOutputStream bOut;

            InputStream in;
            FileInputStream fIn = new FileInputStream("res/example.txt");
            ByteArrayInputStream bIn;
            GZIPInputStream gzIn;

            BufferedInputStream bfIn = new BufferedInputStream(fIn);
            BufferedOutputStream bfOut = new BufferedOutputStream(fOut);

            Reader r;
            Writer w;
            InputStreamReader sr = new InputStreamReader(fIn, StandardCharsets.UTF_8);
            OutputStreamWriter sw = new OutputStreamWriter(fOut, StandardCharsets.UTF_8);

            FileReader fr;
            FileWriter fw;

            sr.close();
            sw.close();

            try (InputStream sAutoClosed = new FileInputStream("res/example.txt")) {
                // IO
            } catch (Exception e) {
                // Handle Exception
            } finally {

            }

            PrintStream ps1 = new PrintStream(
                    new FileOutputStream("res/example.txt"), false
            );
            PrintStream ps2 = new PrintStream(
                    new BufferedOutputStream(
                            new FileOutputStream("res/example.txt")
                    ), false
            );

            ps2.println("Hello");
            ps2.println(2);
            ps2.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
