package pooledWebLog;

import java.awt.desktop.SystemSleepEvent;
import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PooledWebLog {
    private BufferedReader in;
    private BufferedWriter out;
    private int numOfThreads;
    private boolean finished;

    private final List<String> entries = Collections.synchronizedList(new LinkedList<>());

    public PooledWebLog(FileInputStream in, PrintStream out, int threads) {
        this.in = new BufferedReader(new InputStreamReader(in));
        this.out = new BufferedWriter(new OutputStreamWriter(out));
        this.numOfThreads = threads;
    }

    public void processLogFile() {
        for (int i=0; i<this.numOfThreads; i++) {
            Thread t = new Thread(new LookupRunnable(this));
            t.start();
        }

        try {
            for(String entry = in.readLine(); entry!=null; entry=in.readLine()) {
                while (this.entries.size() > this.numOfThreads) {
                    try {
                        Thread.sleep((long)(1000.0/this.numOfThreads));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                synchronized (this.entries) {
                    this.entries.add(0, entry);
                    this.entries.notifyAll();
                }

                Thread.yield();
            }

            this.finished = true;
            System.err.println("Work finished...");
            synchronized (this.entries) {
                this.entries.notifyAll();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(String entry) throws IOException {
        out.write(entry);
        out.newLine();
        out.flush();
    }

    public List<String> getEntries() {
        return this.entries;
    }

    public boolean isFinished() {
        return this.finished;
    }
}
