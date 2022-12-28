import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private double balance;
    private String accountNumber;
    private Lock lock; //

    public BankAccount(String accountNumber, double initialBalance ) {
        this.balance = initialBalance;
        this.accountNumber = accountNumber;
        this.lock = new ReentrantLock();
    }

    public String getAccountNumber () {
        return accountNumber;
    }

    public void printAccountNumber () {
        System.out.println("Account number : " + accountNumber);
    }

    // First Solution syncronized methods
//    public synchronized void deposit (double amount) {
//        balance += amount;
//    }
//
//    public synchronized void withdraw (double amount) {
//        balance -= amount;
//    }



    // Second Solution: using synchronized in methods
//    public void deposit (double amount) {
//        synchronized (this) {
//            balance += amount;
//        }
//    }
//
//    public void withdraw (double amount) {
//        synchronized (this) {
//            balance -= amount;
//        }
//    }
//



    // Third Solution: using reentrant lock for synchronized
//    public void deposit (double amount) {
//        lock.lock();
//        try{
//            balance += amount;
//        }finally {
//            lock.unlock();
//        }
//
//    }
//
//    public void withdraw (double amount) {
//        lock.lock();
//        try {
//            balance -= amount;
//        }finally {
//            lock.unlock();
//        }
//
//    }


    // using tryLock(max wait time, time unit) methods, and only lock when trylock returns true // timeunit is generally millisecond

    public void deposit (double amount) {
        boolean status = false;

        try {

            if(lock.tryLock(1000, TimeUnit.MILLISECONDS)){
                try {
                    balance += amount;
                    status = true;
                }finally {
                    lock.unlock();
                }
            }else {
                System.out.println("Could not get the lock");
            }

        }catch (InterruptedException e) {

        }

        System.out.println("Transaction status: " + status);
    }

    public void withdraw (double amount) {
        boolean status = false;
        try{
            
            if(lock.tryLock(1000, TimeUnit.MILLISECONDS)){
                try{
                    balance -= amount;
                    status = true;
                }finally {
                    lock.unlock();
                }
            }else {
                System.out.println("Could not get the lock");
            }

        }catch (InterruptedException e){

        }

        System.out.println("Transaction status: " + status);
    }

}

