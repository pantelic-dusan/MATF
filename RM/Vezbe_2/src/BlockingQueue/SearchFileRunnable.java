package BlockingQueue;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class SearchFileRunnable implements Runnable {

    private BlockingQueue<Path> queue;
    private String keyword;

    public SearchFileRunnable(BlockingQueue<Path> queue, String keyword) {
        this.queue = queue;
        this.keyword = keyword;
    }

    @Override
    public void run() {
        try {
            boolean done = false;
            while (!done) {
                Path p = this.queue.take();
                if (p == FileTreeWalkerRunnable.END_OF_WORK) {
                    done = true;
                    this.queue.put(p);
                } else {
                    this.search(p);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void search(Path p) {
        try (Scanner sc = new Scanner(p)) {
            for (int ln=1; sc.hasNextLine(); ln++) {
                String line = sc.nextLine();
                if (line.contains(this.keyword)) {
                    System.out.printf("%s:%d\n", p.getFileName(), ln);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
