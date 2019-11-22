package copyImage;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class CopyImageMain {

    public static void main(String[] args) {

        try{
            long startTime = System.currentTimeMillis();

            FileInputStream in = new FileInputStream("res/in.png");
            FileOutputStream out = new FileOutputStream("res/out.png");

            int b;
            while ((b = in.read()) != -1)
                out.write(b);

            in.close();
            out.close();

            long stopTime = System.currentTimeMillis();
            System.out.println("Finished in: " + (stopTime-startTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
