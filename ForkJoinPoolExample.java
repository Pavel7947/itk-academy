import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolExample {

    public static void main(String[] args) {
        int n = 1; // Вычисление факториала для числа 10

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        FactorialTask factorialTask = new FactorialTask(n);

        long result = forkJoinPool.invoke(factorialTask);

        System.out.println("Факториал " + n + "! = " + result);
    }
}

class FactorialTask extends RecursiveTask<Integer> {
    private final int n;

    public FactorialTask(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n <= 5) { // базовый случай
            return multiplySequence(1, n);
        }
        ForkJoinTask<Integer> task = new FactorialTask(n / 2).fork();
        return multiplySequence(n / 2 + 1, n) * task.join();
    }

    private int multiplySequence(int start, int end) {
        int result = 1;
        for (int i = start; i <= end; i++) {
            result *= i;
        }
        return result;
    }
}