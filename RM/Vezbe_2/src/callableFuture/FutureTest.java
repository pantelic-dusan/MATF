package callableFuture;

import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTest {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.print("Enter base directory: ");
        String directory = in.nextLine();
        System.out.print("Enter keyword (e.g. volatile): ");
        String keyword = in.nextLine();
        in.close();

        MatchCounter counter = new MatchCounter(Paths.get(directory), keyword);
        FutureTask<Integer> task = new FutureTask<>(counter);
        Thread t = new Thread(task);
        t.start();

        try {
            System.out.println(task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
