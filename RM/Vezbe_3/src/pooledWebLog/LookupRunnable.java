package pooledWebLog;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

public class LookupRunnable implements Runnable {
    private PooledWebLog log;
    private final List<String> entries;

    public LookupRunnable(PooledWebLog log) {
        this.log = log;
        this.entries = log.getEntries();
    }

    @Override
    public void run() {
        for (String entry = this.getNextEntry(); entry!=null; entry = this.getNextEntry()) {
            String workResult = this.analyzeEntryAndGetResult(entry);
            if (workResult == null)
                continue;

            try {
                log.log(workResult);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Thread.yield();
        }
    }

    private String getNextEntry() {
        synchronized (this.entries) {
            while (entries.size() == 0) {
                if (log.isFinished()) {
                    System.err.println("Thread exiting: " + Thread.currentThread());
                    return null;
                }

                try {
                    entries.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return entries.remove(0);
        }
    }

    private String analyzeEntryAndGetResult(String entry) {
        int index = entry.indexOf(' ');
        if (index == -1)
            return null;

        String remoteHost = entry.substring(0, index);
        String theRest = entry.substring(index);

        try {
            remoteHost = InetAddress.getByName(remoteHost).getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return remoteHost + theRest;
    }
}
