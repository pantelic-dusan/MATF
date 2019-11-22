package threadPool;


import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.*;

public class ThreadPoolTest {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.print("Enter base directory: ");
        String directory = in.nextLine();
        System.out.print("Enter keyword (e.g. volatile): ");
        String keyword = in.nextLine();
        in.close();

        ExecutorService pool = Executors.newCachedThreadPool();

        MatchCounterTP counter = new MatchCounterTP(Paths.get(directory), keyword, pool);

        Future<Integer> result = pool.submit(counter);

        try {
            System.out.println(result.get() + " matching files.");
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        pool.shutdown();

        int largestPoolSize = ((ThreadPoolExecutor) pool).getLargestPoolSize();
        System.out.println("largest pool size: " + largestPoolSize);
    }
}
