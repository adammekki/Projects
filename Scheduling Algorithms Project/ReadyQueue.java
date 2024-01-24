import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ReadyQueue {
    private Queue<ArrayList<String>> queue;

    public ReadyQueue() {
        this.queue = new LinkedList<>();
    }

    public void enqueue(ArrayList<String> a) {
        queue.add(a);
    }

    public ArrayList<String> peek()
    {
        return queue.peek();
    }

    public ArrayList<String> dequeue() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

}