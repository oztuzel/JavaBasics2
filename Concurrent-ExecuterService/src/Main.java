import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;


public class Main {
    public static final String EOF = "EOF";

    public static void main(String[] args) {
        List<String> buffer = new ArrayList<>();
        // Array list is unsynchronized.
        ReentrantLock bufferLock = new ReentrantLock();

        ExecutorService executorService = Executors.newFixedThreadPool(3);


        MyProducer producer = new MyProducer(buffer, bufferLock);
        MyConsumer consumer1 = new MyConsumer(buffer, bufferLock);
        MyConsumer consumer2 = new MyConsumer(buffer,bufferLock);


        executorService.execute(producer);
        executorService.execute(consumer1);
        executorService.execute(consumer2);

        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("I am printing for the Callable class");
                return "This is callable result";
            }
        });

        try {
            System.out.println(future.get());
        }catch (InterruptedException e){

        }catch (ExecutionException e){

        }

        executorService.shutdown();
    }
}

class MyProducer implements Runnable {
    private List<String> buffer;
    private ReentrantLock bufferLock;

    public MyProducer(List<String> buffer, ReentrantLock bufferLock) {
        this.buffer = buffer;
        this.bufferLock = bufferLock;
    }

    public void run() {
        Random random = new Random();
        String[] nums = { "1", "2", "3", "4","5"};

        for(String num : nums) {
            try {
                System.out.println(Thread.currentThread().getName()+ " Adding " + num);
                bufferLock.lock();
                try{
                    buffer.add(num);
                }finally {
                    bufferLock.unlock();
                }

                Thread.sleep(random.nextInt(1000));
            }catch (InterruptedException e) {
                System.out.println("Producer was interrupted.");
            }
        }

        System.out.println(Thread.currentThread().getName()+ " Adding eof and exiting");
        bufferLock.lock();
        try{
            buffer.add("EOF");
            //End of File
        }finally {
            bufferLock.unlock();
        }

    }

}

class MyConsumer implements Runnable {
    private List<String> buffer;
    private ReentrantLock bufferLock;

    public MyConsumer(List<String> buffer, ReentrantLock bufferlock) {
        this.buffer = buffer;
        this.bufferLock = bufferlock;
    }

    public void run() {
        int counter = 0;
        while(true){
            if(bufferLock.tryLock()){
                try{
                    if(buffer.isEmpty()){
                        continue;
                    }
                    System.out.println("Counter = " + counter);
                    counter= 0;
                    if(buffer.get(0).equals(Main.EOF)){
                        System.out.println(Thread.currentThread().getName() + " Exiting");
                        break;
                    }else {
                        System.out.println(Thread.currentThread().getName() + " Removed " + buffer.remove(0));
                    }
                }finally {
                    bufferLock.unlock();
                }
            }else {
                counter++;
            }

        }
    }
}












