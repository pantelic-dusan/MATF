package copyText;

import java.io.*;

public class CopyTextBufferedMain {

    public static void main(String[] args) {

        try{
            long startTime = System.currentTimeMillis();

            BufferedInputStream in = new BufferedInputStream(
                    new FileInputStream("res/in.png")
            );
            BufferedOutputStream out = new BufferedOutputStream(
                    new FileOutputStream("res/out.png")
            );

            byte[] buff = new byte[512];
            int bytesReaded;
            while ((bytesReaded = in.read(buff)) != -1)
                out.write(buff, 0, bytesReaded);

            in.close();
            out.close();

            long stopTime = System.currentTimeMillis();
            System.out.println("Finished in: " + (stopTime-startTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
