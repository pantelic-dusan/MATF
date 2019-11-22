package callableFuture;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class MatchCounter implements Callable<Integer> {
    private Path directory;
    private String keyword;


    public MatchCounter(Path directory, String keyword) {
        this.directory = directory;
        this.keyword = keyword;
    }

    @Override
    public Integer call() throws Exception {
        int count = 0;
        try {
            ArrayList<Future<Integer>> results = new ArrayList<Future<Integer>>();
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
                for (Path p: stream) {
                    if (Files.isDirectory(p)) {
                        MatchCounter counter = new MatchCounter(p, keyword);
                        FutureTask<Integer> task = new FutureTask<Integer>(counter);
                        results.add(task);
                        Thread t = new Thread(task);
                        t.start();
                    } else if (this.search(p)) {
                        count++;
                    }
                }
            } catch (IOException e) {
                System.err.println(e);
            }

            for (Future<Integer> r: results) {
                try {
                    count += r.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return count;
    }

    public boolean search(Path file) {
        try (Scanner in = new Scanner(file)) {
            boolean found = false;
            while (!found && in.hasNextLine()) {
                String line = in.nextLine();
                if (line.contains(keyword))
                    found = true;
            }
            return found;
        } catch (IOException e) {
            return false;
        }
    }

}
