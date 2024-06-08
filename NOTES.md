### Multithreading in Java

#### 1. Introduction to Multithreading
- **Multithreading** is the capability of a CPU, or a single core in a multi-core processor, to execute multiple processes or threads concurrently.
- **Thread**: A thread is the smallest unit of a process that can be scheduled and executed by the CPU.
- **Multithreading** allows the execution of multiple parts of a program simultaneously, which can significantly improve the performance of applications, especially those that are IO-bound or require parallel processing.

#### 2. Benefits of Multithreading
- **Improved performance**: Better utilization of CPU resources.
- **Concurrent execution**: Allows multiple operations to run in parallel.
- **Simplified modeling**: Easier to model real-world scenarios where multiple events occur simultaneously.
- **Resource sharing**: Threads within the same process share resources more efficiently.

#### 3. Key Concepts in Multithreading

- **Thread Lifecycle**: A thread in Java has several states:
  - **New**: A thread that is created but not yet started.
  - **Runnable**: A thread that is ready to run and is waiting for CPU time.
  - **Blocked**: A thread that is blocked and waiting for a monitor lock.
  - **Waiting**: A thread that is waiting indefinitely for another thread to perform a particular action.
  - **Timed Waiting**: A thread that is waiting for another thread to perform an action for a specified amount of time.
  - **Terminated**: A thread that has exited.

- **Thread Priority**: Each thread has a priority that helps the thread scheduler determine the order of thread execution.

#### 4. Creating Threads in Java

- **Extending the `Thread` class**:
  ```java
  class MyThread extends Thread {
      public void run() {
          // Code to execute in the thread
          System.out.println("Thread is running...");
      }
  }

  public class Main {
      public static void main(String[] args) {
          MyThread t1 = new MyThread();
          t1.start();  // Start the thread
      }
  }
  ```

- **Implementing the `Runnable` interface**:
  ```java
  class MyRunnable implements Runnable {
      public void run() {
          // Code to execute in the thread
          System.out.println("Thread is running...");
      }
  }

  public class Main {
      public static void main(String[] args) {
          Thread t1 = new Thread(new MyRunnable());
          t1.start();  // Start the thread
      }
  }
  ```

#### 5. Thread Synchronization

- **Need for Synchronization**: When multiple threads access shared resources simultaneously, it can lead to data inconsistency. Synchronization is used to control the access of multiple threads to shared resources.

- **Synchronized Methods**:
  ```java
  class Counter {
      private int count = 0;

      public synchronized void increment() {
          count++;
      }

      public int getCount() {
          return count;
      }
  }

  public class Main {
      public static void main(String[] args) {
          Counter counter = new Counter();
          // Create and start multiple threads that access the counter
      }
  }
  ```

- **Synchronized Blocks**:
  ```java
  class Counter {
      private int count = 0;
      private final Object lock = new Object();

      public void increment() {
          synchronized (lock) {
              count++;
          }
      }

      public int getCount() {
          return count;
      }
  }

  public class Main {
      public static void main(String[] args) {
          Counter counter = new Counter();
          // Create and start multiple threads that access the counter
      }
  }
  ```

- **Volatile Keyword**: Used to indicate that a variable's value will be modified by different threads.
  ```java
  class VolatileExample {
      private volatile boolean flag = true;

      public void stop() {
          flag = false;
      }

      public void run() {
          while (flag) {
              // Do something
          }
      }
  }
  ```

#### 6. Inter-Thread Communication

- **wait(), notify(), notifyAll() Methods**:
  ```java
  class Message {
      private String message;

      public synchronized void produce(String message) {
          while (this.message != null) {
              try {
                  wait();
              } catch (InterruptedException e) {
                  Thread.currentThread().interrupt();
              }
          }
          this.message = message;
          notify();
      }

      public synchronized String consume() {
          while (this.message == null) {
              try {
                  wait();
              } catch (InterruptedException e) {
                  Thread.currentThread().interrupt();
              }
          }
          String temp = message;
          message = null;
          notify();
          return temp;
      }
  }

  public class Main {
      public static void main(String[] args) {
          Message message = new Message();
          // Create and start producer and consumer threads
      }
  }
  ```

#### 7. Java Concurrency Utilities

- **Executor Framework**: Provides a higher-level replacement for working with threads directly.
  ```java
  import java.util.concurrent.ExecutorService;
  import java.util.concurrent.Executors;

  public class Main {
      public static void main(String[] args) {
          ExecutorService executor = Executors.newFixedThreadPool(2);
          executor.submit(() -> {
              // Task 1
          });
          executor.submit(() -> {
              // Task 2
          });
          executor.shutdown();
      }
  }
  ```

- **Locks and Condition Variables**:
  ```java
  import java.util.concurrent.locks.Lock;
  import java.util.concurrent.locks.ReentrantLock;
  import java.util.concurrent.locks.Condition;

  class SharedResource {
      private Lock lock = new ReentrantLock();
      private Condition condition = lock.newCondition();
      private boolean isAvailable = false;

      public void produce() {
          lock.lock();
          try {
              while (isAvailable) {
                  condition.await();
              }
              // Produce item
              isAvailable = true;
              condition.signalAll();
          } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
          } finally {
              lock.unlock();
          }
      }

      public void consume() {
          lock.lock();
          try {
              while (!isAvailable) {
                  condition.await();
              }
              // Consume item
              isAvailable = false;
              condition.signalAll();
          } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
          } finally {
              lock.unlock();
          }
      }
  }
  ```

- **Atomic Variables**:
  ```java
  import java.util.concurrent.atomic.AtomicInteger;

  class Counter {
      private AtomicInteger count = new AtomicInteger(0);

      public void increment() {
          count.incrementAndGet();
      }

      public int getCount() {
          return count.get();
      }
  }
  ```
  
#### 8. Daemon Thread
- **Daemon Thread**: in java is a service provider thread that provides services to other threads.

```
setDaemon(Boolean)
public boolean isDaemon()
```
##### Garbage collection is the example of daemon thread


#### 9. Best Practices

- **Minimize synchronized blocks**: Keep synchronized blocks as short as possible to reduce contention.
- **Prefer higher-level concurrency utilities**: Use java.util.concurrent package classes like ExecutorService, Semaphore, CountDownLatch, etc., instead of low-level synchronization.
- **Avoid deadlocks**: Be cautious of acquiring multiple locks in different orders in different threads.
- **Use thread-safe collections**: Use classes from the `java.util.concurrent` package like ConcurrentHashMap, CopyOnWriteArrayList, etc.

By understanding and utilizing these concepts and practices, you can effectively use multithreading to enhance the performance and responsiveness of your Java applications.