package pooledWebLog;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PooledWebLogTest {

    public static void main(String[] args) throws FileNotFoundException {

        PooledWebLog pwl = new PooledWebLog(
                new FileInputStream("res/apache.logfile"),
                System.out,
                6
        );

        pwl.processLogFile();
    }
}
