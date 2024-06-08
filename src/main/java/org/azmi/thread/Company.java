package org.azmi.thread;

public class Company {

    private int n;
    boolean flag = false;

    synchronized public void produce_item(int n) {
        if (flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.n = n;
        System.out.println("Produced : " + n);
        flag = true;
        notifyAll();
    }

    synchronized public int consume_item() {
        if (!flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Consumed : " + n);
        flag = false;
        notifyAll();
        return n;

    }

    public static void main(String[] args) {
        Company company = new Company();

        // Creating threads
        Thread producerThread = new Thread(company.t1);
        Thread consumerThread = new Thread(company.t2);

        // Starting threads
        producerThread.start();
        consumerThread.start();
    }

    // Runnable instances
    Runnable t1 = () -> {
        int i = 1;
        while (true) {
            produce_item(i++);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    Runnable t2 = () -> {
        while (true) {
            consume_item();
            n--;
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
}
