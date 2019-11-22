package printStream;

import java.io.*;
import java.util.Scanner;

public class PrintStreamMain {

    public static void main(String[] args) {

        try (PrintStream ps = new PrintStream(
                new BufferedOutputStream(
                        new FileOutputStream("res/ps_out.txt")
                )
            )
        ) {
            ps.print("Hello");
            ps.println(" world!");
            ps.println(1);
            ps.println(333.4444);
            float temp = 30.2f;
            ps.printf("Today is %4.2f C", temp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try (Scanner sc = new Scanner(new FileInputStream("res/ps_out.txt"))) {
            if (sc.hasNextLine())
                System.out.println(sc.nextLine());
            System.out.println(sc.nextInt());
            System.out.println(sc.nextLine());
            System.out.println(sc.nextLine());
            System.out.println(sc.nextLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
