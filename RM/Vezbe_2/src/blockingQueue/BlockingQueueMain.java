package blockingQueue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueMain {

    private static final int FILE_QUEUE_SIZE = 10;
    private static final int THREADS_NUM = 5;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter base dir:");
        String dir = sc.nextLine();
        System.out.println("Enter keyword:");
        String keyword = sc.nextLine();
        sc.close();

        BlockingQueue<Path> fileQueue = new ArrayBlockingQueue<>(FILE_QUEUE_SIZE);

        FileTreeWalkerRunnable ftw = new FileTreeWalkerRunnable(Paths.get(dir), fileQueue);
        new Thread(ftw).start();

        for (int i=0; i<THREADS_NUM; i++) {
            new Thread(new SearchFileRunnable(fileQueue, keyword)).start();
        }
    }
}
