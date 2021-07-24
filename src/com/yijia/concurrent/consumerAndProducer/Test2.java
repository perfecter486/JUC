package com.yijia.concurrent.consumerAndProducer;


import java.util.Collections;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Printer{

    private int number=1;

    Lock lock =new ReentrantLock();
    Condition A =lock.newCondition();
    Condition B=lock.newCondition();
    Condition C=lock.newCondition();


    public  void printA() throws InterruptedException {


        lock.lock();

        try {
            while (number!=1){
                A.await();
            }
            for(int i=0;i<10;i++){
                System.out.println("a在使用打印机。。。。。"+i+"次");
            }

            number=2;//轮到b打印
            B.signal();
        } finally {
            lock.unlock();
        }

    }


    public  void printB() throws InterruptedException {
        lock.lock();

        try {
            while (number!=2){
                B.await();
            }
            for(int i=0;i<15;i++){
                System.out.println("b在使用打印机。。。。。"+i+"次");
            }

            number=3;//轮到b打印
            C.signal();
        } finally {
            lock.unlock();
        }

    }

    public  void printC() throws InterruptedException{
        lock.lock();

        try {
            while (number!=3){
                C.await();
            }
            for(int i=0;i<5;i++){
                System.out.println("c在使用打印机。。。。。"+i+"次");
            }

            number=1;//轮到b打印
            A.signal();

        } finally {
            lock.unlock();
        }

    }

   public  void   print(int needFlag,int nextFlag,int loop,Condition waitKey,Condition wakeKey) throws InterruptedException {
       lock.lock();

       try {
           while (number!=needFlag){
               waitKey.await();
           }
           for(int i=0;i<loop;i++){
               System.out.println(Thread.currentThread().getName()+"在使用打印机。。。。。"+i+"次");
           }

           number=nextFlag;//轮到b打印
           wakeKey.signal();

       } finally {
           lock.unlock();
       }

   }
}

public class Test2 {

    public static void main(String[] args) {

        Printer printer=new Printer();

        new Thread(()->{
            for (int i = 0; i <10 ; i++) {
                try {
                    printer.print(1,2,10,printer.A,printer.B);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        },"A").start();


            new Thread(()->{
                for (int i = 0; i <10 ; i++) {
                    try {
                        printer.print(2,3,15,printer.B,printer.C);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            },"B").start();



                new Thread(()->{
                    for (int i = 0; i <10 ; i++) {
                        try {
                            printer.print(3,1,5,printer.C,printer.A);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                },"C").start();
    }
}
