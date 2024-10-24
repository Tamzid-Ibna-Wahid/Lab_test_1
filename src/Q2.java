class SimpleQueue {
    private static class Node { // Node to store integer and next pointer
        int data;
        Node next;
        Node(int data) { this.data = data; this.next = null; }
    }
    private Node head = null, tail = null;

    public synchronized void enqueue(int data) { // Add element to end
        Node newNode = new Node(data);
        if (tail == null) { head = tail = newNode; } 
        else { tail.next = newNode; tail = newNode; }
        System.out.println("Enqueued: " + data);
    }

    public synchronized int dequeue() { // Remove element from front
        if (head == null) { System.out.println("Queue is empty"); return -1; }
        int data = head.data; head = head.next;
        if (head == null) tail = null;
        System.out.println("Dequeued: " + data); return data;
    }

    public synchronized boolean isEmpty() { return head == null; } // Check if empty
}

public class Q2 {
    public static void main(String[] args) throws InterruptedException {
        SimpleQueue queue = new SimpleQueue();
        
        Thread enqueuer = new Thread(() -> {
             for (int i = 1; i <= 5; i++){
                 queue.enqueue(i);
            try {
                 Thread.sleep(50); 
                }
             catch (Exception e) {

             } 
            }
         });
        Thread dequeuer = new Thread(() -> {
             for (int i = 1; i <= 5; i++) {
                 queue.dequeue();
                  try {
                     Thread.sleep(100); 
                }
                 catch (Exception e) {

                 } 
                }
         });

        enqueuer.start();
         dequeuer.start();
        enqueuer.join();
         dequeuer.join();

        System.out.println("Is queue empty? " + queue.isEmpty());
    }
}
