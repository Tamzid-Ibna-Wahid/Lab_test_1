import java.util.Stack;
import java.util.Random;

class SynchronizedStack {
    private Stack<Integer> stack = new Stack<>();
    private final int MAX_SIZE = 10;

    public synchronized void push(int value) {
        while (stack.size() == MAX_SIZE) { 
            try {
                wait(); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        stack.push(value);  
        System.out.println("Pushed: " + value);
        notifyAll(); 
    }

    public synchronized void pop() {
        while (stack.isEmpty()) { 
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int value = stack.pop();  
        System.out.println("Popped: " + value);
        notifyAll(); 
    }
}

class Producer extends Thread {
    private SynchronizedStack stack;

    public Producer(SynchronizedStack stack) {
        this.stack = stack;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int randomValue = random.nextInt(100) + 1; 
            stack.push(randomValue);  
            try {
                Thread.sleep(50); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer extends Thread {
    private SynchronizedStack stack;

    public Consumer(SynchronizedStack stack) {
        this.stack = stack;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) { 
            stack.pop(); 
            try {
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Q3 {
    public static void main(String[] args) {
        SynchronizedStack stack = new SynchronizedStack(); 

        Producer producer = new Producer(stack); 
        Consumer consumer = new Consumer(stack); 

       
        producer.start();
        consumer.start();

       
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Finished execution.");
    }
}
