package org.azmi.thread;

public class MyThread implements Runnable {

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        Thread thread = new Thread(myThread);
        thread.start();
        MyThreadClass myThreadClass = new MyThreadClass();
        myThreadClass.start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Thread 1: " + Thread.currentThread().getName() + "  " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
