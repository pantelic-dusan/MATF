package copyText;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class CopyTextMain {

    public static void main(String[] args) {

        try{
            long startTime = System.currentTimeMillis();

            InputStreamReader in = new InputStreamReader(new FileInputStream("res/in.txt"));
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("res/out.txt"));

            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            in.close();
            out.close();

            long stopTime = System.currentTimeMillis();
            System.out.println("Finished in: " + (stopTime-startTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
