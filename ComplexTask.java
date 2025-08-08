import java.util.concurrent.*;

class ComplexTask implements Runnable {
    private final CyclicBarrier barrier;
    private final int taskId;
    private int result;

    public ComplexTask(CyclicBarrier barrier, int taskId) {
        this.barrier = barrier;
        this.taskId = taskId;
    }

    @Override
    public void run() {
        try {
            System.out.println("Задача " + taskId + " начала выполнение");
            Thread.sleep(1000 + (long) (Math.random() * 1000));
            result = taskId * 10;
            barrier.await();

            System.out.println("Задача " + taskId + " завершила выполнение. Результат: " + result);
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public int getResult() {
        return result;
    }
}

class ComplexTaskExecutor {
    private ExecutorService executor;
    private CyclicBarrier barrier;
    private ComplexTask[] tasks;

    public ComplexTaskExecutor(int numberOfTasks) {
        this.barrier = new CyclicBarrier(numberOfTasks);
        this.tasks = new ComplexTask[numberOfTasks];
    }

    public synchronized void executeTasks(int count) {
        executor = Executors.newFixedThreadPool(count);

        for (int i = 0; i < count; i++) {
            tasks[i] = new ComplexTask(barrier, i + 1);
            executor.execute(tasks[i]);
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int totalResult = 0;
        for (ComplexTask task : tasks) {
            totalResult += task.getResult();
        }
        System.out.println("Общий результат: " + totalResult);
    }
}
