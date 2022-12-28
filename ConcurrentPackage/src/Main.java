import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Main {
    public static final String EOF = "EOF";

    public static void main(String[] args) {
        List<String> buffer = new ArrayList<>();
        // Array list is unsynchronized.
        MyProducer producer = new MyProducer(buffer);
        MyConsumer consumer1 = new MyConsumer(buffer);
        MyConsumer consumer2 = new MyConsumer(buffer);


        new Thread(producer).start();
        new Thread(consumer1).start();
        new Thread(consumer2).start();
    }
}

class MyProducer implements Runnable {
    private List<String> buffer;

    public MyProducer(List<String> buffer) {
        this.buffer = buffer;
    }

    public void run() {
        Random random = new Random();
        String[] nums = { "1", "2", "3", "4","5"};

        for(String num : nums) {
            try {
                System.out.println(Thread.currentThread().getName()+ " Adding " + num);
                synchronized (buffer) {
                    buffer.add(num);
                }

                Thread.sleep(random.nextInt(1000));
            }catch (InterruptedException e) {
                System.out.println("Producer was interrupted.");
            }
        }

        System.out.println(Thread.currentThread().getName()+ " Adding eof and exiting");
        synchronized (buffer){
            buffer.add("EOF");
            //End of File
        }
    }

}

class MyConsumer implements Runnable {
    private List<String> buffer;

    public MyConsumer(List<String> buffer) {
        this.buffer = buffer;
    }

    public void run() {
        while(true){
            synchronized (buffer){
                if(buffer.isEmpty()){
                    continue;
                }
                if(buffer.get(0).equals(Main.EOF)){
                    System.out.println(Thread.currentThread().getName() + " Exiting");
                    break;
                }else {
                    System.out.println(Thread.currentThread().getName() + " Removed " + buffer.remove(0));
                }
            }
        }
    }
}












