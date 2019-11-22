package introThreads;

public class RaceConditionMain {

    private static int x = 0;
    private static int LIMIT = 10000;

    public static class Test implements Runnable {

        @Override
        public void run() {
            for (int i=0; i<LIMIT; i++) {
                x++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        int TH_NUM = 10;

        for (int i=0; i<TH_NUM; i++) {
            new Thread(new Test()).start();
        }

        Thread.sleep(5000);
        System.out.println("Excepted: " + TH_NUM*LIMIT);
        System.out.println("Actual: " + x);

    }
}
