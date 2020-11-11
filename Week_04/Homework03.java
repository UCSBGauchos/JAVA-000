import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好。
 * <p>
 * 一个简单的代码参考：
 */
public class Homework03 {

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        //这是得到的返回值
        int result = sum();

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}

/**
 * join
 * 启动新线程，join，结果通过 final int[] result 传递（或使用 static volatile Integer result）
 */
class Work1 {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        final int[] result = new int[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                result[0] = sum();
            }
        });
        thread.start();
        thread.join();

        System.out.println("异步计算结果为：" + result[0]);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}

/**
 * synchronized + wait/notify
 * 启动新线程，不使用join而是使用 synchronized + wait/notify，结果通过 static volatile Integer result
 */
class Work2 {
    private static volatile Integer result;

    public static void main(String[] args) throws InterruptedException {
        Work2 work = new Work2();

        long start = System.currentTimeMillis();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                work.sum();
            }
        });
        thread.start();

        work.getResult();

        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    private synchronized void getResult() throws InterruptedException {
        wait();
    }

    private synchronized void sum() {
        result = fibo(36);
        notifyAll();
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}

/**
 * Lock + await/signal
 */
class Work3 {
    private static volatile Integer result;
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        Work3 work = new Work3();

        long start = System.currentTimeMillis();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                work.sum();
            }
        });
        // 计算前先加锁（否则可能会先执行getResult中的lock）
        work.lock.lock();
        thread.start();

        work.getResult();

        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    private void getResult() throws InterruptedException {
        lock.lock();
        try {
            condition.await();
        } finally {
            lock.unlock();
        }
    }

    private void sum() {
        try {
            result = fibo(36);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}

/**
 * Semaphore
 * Semaphore(1)，同一时刻只允许一个线程获取信号量
 */
class Work4 {
    private static volatile Integer result;
    private static final Semaphore SEMAPHORE = new Semaphore(1);

    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                sum();
            }
        });
        // 获取信号量，启动线程做计算
        SEMAPHORE.acquire();
        thread.start();

        // 如果上次的 acquire对应的release没有执行，会阻塞等待
        SEMAPHORE.acquire();
        SEMAPHORE.release();
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    private static void sum() {
        result = fibo(36);
        // 计算完成后释放信号量
        SEMAPHORE.release();
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}

/**
 * CountDownLatch
 */
class Work5 {
    private static volatile Integer result;
    private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                sum();
            }
        });
        thread.start();

        COUNT_DOWN_LATCH.await();
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    private static void sum() {
        result = fibo(36);
        COUNT_DOWN_LATCH.countDown();
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}

/**
 * CyclicBarrier
 */
class Work6 {
    private static volatile Integer result;
    private static CyclicBarrier CYCLIC_BARRIER = null;

    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();

        CYCLIC_BARRIER = new CyclicBarrier(1, new Runnable() {
            @Override
            public void run() {
                System.out.println("异步计算结果为：" + result);
                System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
            }
        });

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                sum();
            }
        });
        thread.start();
    }

    private static void sum() {
        result = fibo(36);
        try {
            CYCLIC_BARRIER.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}

/**
 * Executors.newFixedThreadPool + Future
 */
class Work7 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<Object> future = executorService.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return sum();
            }
        });

        int result = (int) future.get();

        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        executorService.shutdown();
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}

/**
 * FutureTask + Thread
 */
class Work8 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        FutureTask<Integer> futureTask = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sum();
            }
        });

        Thread thread = new Thread(futureTask);
        thread.start();

        int result = futureTask.get();

        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}

/**
 * FutureTask + Thread
 */
class Work9 {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        int result = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                return sum();
            }
        }).join();

        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}
