package introThreads;

public class ThreadsIntroMain {

    private static class MyThread extends Thread {
        @Override
        public void run() {

        }
    }

    private static class MyRunnableThread implements Runnable {

        @Override
        public void run() {

        }
    }

    public static void main(String[] args) {

        Thread t1 = new MyThread();
        t1.start();
        // t1.run() WRONG

        Thread t2 = new Thread(new MyRunnableThread());
        t2.start();
    }
}
