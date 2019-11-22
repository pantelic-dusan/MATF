package filteredCopy;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class FilteredCopyMain {

    public static void main(String[] args) {

        try (Scanner in = new Scanner(
                new BufferedReader(
                        new InputStreamReader(
                            new FileInputStream("res/in.txt"),
                            StandardCharsets.UTF_8
                        )
                )
            );
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(
                        new FileOutputStream("res/out.txt"),
                        StandardCharsets.US_ASCII
                    )
            );

        ) {
            in.useDelimiter("\\b");

            while (in.hasNext()) {
                String word = in.next();
                if (isName(word)) {
                    out.write(word);
                    out.newLine();
                }
            }

            System.out.println("Done");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isName(String word) {
        if (word.length() < 2)
            return false;

        if (!Character.isUpperCase(word.charAt(0)))
            return false;

        return word.chars().skip(1).allMatch(Character::isLowerCase);
    }
}

