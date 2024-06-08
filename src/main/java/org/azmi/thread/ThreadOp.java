package org.azmi.thread;

public class ThreadOp {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        Thread thread = new Thread(myThread);
        thread.setPriority(Thread.MAX_PRIORITY);

        MyThreadClass myThreadClass = new MyThreadClass();
        thread.setPriority(Thread.MIN_PRIORITY);

        System.out.println(Thread.currentThread().isDaemon());


        myThreadClass.start();
        thread.start();
    }
}
