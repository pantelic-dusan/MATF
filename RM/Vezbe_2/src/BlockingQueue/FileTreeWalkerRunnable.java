package BlockingQueue;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;

public class FileTreeWalkerRunnable implements Runnable {

    public static final Path END_OF_WORK = Paths.get("");

    private BlockingQueue<Path> queue;
    private Path startingDir;

    public FileTreeWalkerRunnable(Path path, BlockingQueue<Path> queue) {
        this.queue = queue;
        this.startingDir = path;
    }

    @Override
    public void run() {
        try {
            walk(this.startingDir);
            this.queue.put(END_OF_WORK);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void walk(Path startingDir) throws InterruptedException {

        try (DirectoryStream<Path> ds = Files.newDirectoryStream(startingDir)) {
            for (Path p:ds) {
                if (Files.isDirectory(p)) {
                    walk(p);
                } else {
                    this.queue.put(p);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
