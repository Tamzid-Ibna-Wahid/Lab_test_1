import java.util.ArrayList;
import java.util.List;

class AddElementsTask extends Thread{
    @Override
    public void run(){
        for (int i = 0; i < 1000; i++) {
           Q1.list.add(i);
            System.out.println("Added: " + i);
            try {
                Thread.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
class RemoveElementsTask extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            if (!Q1.list.isEmpty()) {
                Integer removed =Q1.list.remove(0); 
                System.out.println("Removed: " + removed);
            }
            try {
                Thread.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


public class Q1 {
    public static List<Integer> list = new ArrayList<>();
    public static void main(String[] args) {
        AddElementsTask adder = new AddElementsTask();
        RemoveElementsTask remover = new RemoveElementsTask();

        adder.start();
        remover.start();

      try{
        adder.join();
      } 
      catch(Exception e){
        System.out.println(e);
      }
      try{
        remover.join();
      }
      catch(Exception e){
        System.out.println(e);
      }
        System.out.println("Final List: " + list);

    }
}
