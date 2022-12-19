public class Main {
    public static void main(String[] args) {
        CountDown countdown = new CountDown();
        CountDownThread t1 = new CountDownThread(countdown);
        t1.setName("Thread 1");

        CountDownThread t2 = new CountDownThread(countdown);
        t2.setName("Thread 2");

        t1.start();
        t2.start();
    }
}

class CountDown {
    // private int i ; thats stored in heap (all threads shared heap)
    public void doCountDown (){
        for(int i=10; i>1 ; i--){
            System.out.println(Thread.currentThread().getName() + ": i=" + i);
        }
    }
}

class CountDownThread extends Thread{
    private CountDown threadCountDown;

    public CountDownThread(CountDown threadCountDown) {
        this.threadCountDown = threadCountDown;
    }

    public void run (){
        threadCountDown.doCountDown();
    }
}