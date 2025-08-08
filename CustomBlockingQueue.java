import java.util.ArrayDeque;
import java.util.Queue;

public class CustomBlockingQueue {
    private final Queue<Runnable> queue;
    private final int limit;

    public CustomBlockingQueue(int limit) {
        this.queue = new ArrayDeque<>();
        this.limit = limit;
    }

    public synchronized void enqueue(Runnable task) {
        while (queue.size() == limit) {
            try {
                System.out.println("Очередь заполнена, ожидание освобождения места");
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        queue.add(task);
        System.out.println("Добавлен элемент: " + task);
        notifyAll();
    }

    public synchronized Runnable dequeue() {
        while (queue.isEmpty()) {
            try {
                System.out.println("Очередь пуста, ожидание элементов");
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        Runnable task = queue.remove();
        System.out.println("Извлечен элемент: " + task);
        notifyAll();
        return task;
    }

    public synchronized int size() {
        return queue.size();
    }
}

