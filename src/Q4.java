import java.util.concurrent.ArrayBlockingQueue;

class ResourcePool {
    private final ArrayBlockingQueue<Object> resources;

    public ResourcePool(int size) {
        resources = new ArrayBlockingQueue<>(size); // Fixed-size resource pool
        for (int i = 0; i < size; i++) {
            resources.add(new Object()); // Populate the pool with resources
        }
    }

    // Acquire a resource; blocks if none are available
    public Object acquire() throws InterruptedException {
        return resources.take(); // Waits until a resource is available
    }

    // Release a resource back to the pool
    public void release(Object resource) {
        resources.offer(resource); // Makes the resource available
    }
}

// Thread class that simulates acquiring and releasing resources
class ResourceUser implements Runnable {
    private final ResourcePool resourcePool;

    public ResourceUser(ResourcePool resourcePool) {
        this.resourcePool = resourcePool;
    }

    @Override
    public void run() {
        try {
            // Acquire a resource
            Object resource = resourcePool.acquire();
            System.out.println(Thread.currentThread().getName() + " acquired a resource.");

            // Simulate some work with the resource
            Thread.sleep(100);

            // Release the resource
            resourcePool.release(resource);
            System.out.println(Thread.currentThread().getName() + " released a resource.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
            System.out.println(Thread.currentThread().getName() + " was interrupted.");
        }
    }
}

// Main class to test the ResourcePool
public class Q4 {
    public static void main(String[] args) {
        final int POOL_SIZE = 5; // Number of resources in the pool
        final int NUMBER_OF_THREADS = 20; // Number of threads trying to access the pool

        ResourcePool resourcePool = new ResourcePool(POOL_SIZE);
        
        // Create and start multiple threads
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            new Thread(new ResourceUser(resourcePool), "Thread-" + i).start();
        }
    }
}
