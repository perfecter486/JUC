package com.yijia.concurrent;


import com.sun.org.apache.xpath.internal.functions.Function;

import java.util.TreeMap;
import java.util.concurrent.locks.Lock;



import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class LockUtil {


    public  static  Object lockAndUnlock(Lock lock,NeedLock needLock,Object ... args){


        lock.lock();
        Object res=null;

        try {
            res=needLock.needLock(args);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            lock.unlock();
        }

        return res;

    }

}


interface  NeedLock {

    Object needLock(Object ... args);
}



class Ticket   implements  NeedLock {

    private   int number=200;
    private  Lock lock=new ReentrantLock();

    public   void sale(){

        for (int i=0;i<30;i++){
            LockUtil.lockAndUnlock(lock,this::needLock,null);
            System.out.println(Thread.currentThread().getName()+": "+i);
        }

    }

    @Override
    public Object needLock(Object ... args) {

        if (number>0){
            System.out.println(Thread.currentThread().getName() +"卖了一张票,还剩："+(--number));
        }

        return null;
    }
}
public class SaleTicket {


    public static void main(String[] args) {

            Ticket ticket=new Ticket();

            Thread ti=new Thread(()->ticket.sale(),"售票员1");

            Thread t2=new Thread(()->ticket.sale(),"售票员2");


            ti.start();
            t2.start();


    }


}
