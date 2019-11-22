package zipFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPOutputStream;

public class ZipFileMain {

    public static void main(String[] args) {

        try{
            long startTime = System.currentTimeMillis();

            BufferedInputStream in = new BufferedInputStream(
                    new FileInputStream("res/in.txt")
            );
            BufferedOutputStream out = new BufferedOutputStream(
                    new GZIPOutputStream(
                            new FileOutputStream("res/out.gz")
                    )
            );

            byte[] buff = new byte[512];
            int bytesRead = 0;

            while ((bytesRead = in.read(buff)) != -1)
                out.write(buff, 0, bytesRead);

            in.close();
            out.close();

            long stopTime = System.currentTimeMillis();
            System.out.println("Finished in: " + (stopTime-startTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
