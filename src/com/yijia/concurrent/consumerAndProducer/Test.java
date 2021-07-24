package com.yijia.concurrent.consumerAndProducer;



import com.sun.media.sound.AiffFileReader;

import java.sql.Time;
import java.util.Date;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



class AirCondition {

    private  int number=0;
    ReentrantLock lock=new ReentrantLock();
    Condition empty=lock.newCondition();
    Condition full=lock.newCondition();

    public  void add() throws InterruptedException {

        lock.lock();
        try {
            while(number>0){
                empty.await();
            }

            number++;
//            System.out.println(Thread.currentThread().getName()+"生产了一个空调,库存为："+number);
            full.signalAll();
        } finally {
            lock.unlock();
        }


    }
    public void sub() throws InterruptedException {

        lock.lock();
        try {
            while(number<=0){
                full.await();
            }

            number--;
//            System.out.println(Thread.currentThread().getName()+"消费了一个空调,库存为："+number);
            empty.signalAll();
        } finally {
            lock.unlock();
        }
    }

}


public class Test {

    public static void main(String[] args) throws InterruptedException {


        AirCondition airCondition=new AirCondition();

        Thread t1=  new Thread(()->{


                for (int i=0;i<10000;i++){
                try {
                    airCondition.add();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }},"A"
                    );

       Thread t2= new Thread(()->{
            for (int i=0;i<10000;i++){
            try {
                airCondition.add();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }},"B"
        );
        Thread t3=  new Thread(()->{
            for (int i=0;i<10000;i++){
            try {
                airCondition.sub();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }},"C"
        );

       Thread t4= new Thread(()->{
            for (int i=0;i<10000;i++){
            try {
                airCondition.sub();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }},"D"
        );

        Long start = System.nanoTime();
        t1.start();t2.start();t3.start();t4.start();
        t1.join();t2.join();t3.join();t4.join();
        Long end = System.nanoTime();


        System.out.println("总共用时："+(Long.valueOf(end-start)/1e6));
    }



}
