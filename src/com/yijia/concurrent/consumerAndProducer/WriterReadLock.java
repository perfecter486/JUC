package com.yijia.concurrent.consumerAndProducer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class WriterReadLock {


    static class LockWR {


        Map<Integer,Integer> chached = new HashMap<>();
        ReadWriteLock lock= new ReentrantReadWriteLock();

        public  void  write(Integer key,Integer val){

            lock.writeLock().lock();
            try {
                System.out.println(Thread.currentThread().getName()+"写入开起");
                chached.put(key,val);
                System.out.println(Thread.currentThread().getName()+"写入完成");
            } finally {
                lock.writeLock().unlock();
            }


        }


        public  void  read(Integer key){

            lock.readLock().lock();
            try {
                System.out.println(Thread.currentThread().getName()+"读取开起");
                chached.get(key);
                System.out.println(Thread.currentThread().getName()+"读取完成："+   chached.get(key));
            } finally {
                lock.readLock().unlock();
            }


        }




    }


    public static void main(String[] args) {


        LockWR lock=new LockWR();

        for (int i = 0; i < 50; i++) {

            int tmp=i;
            new Thread(()->{
                lock.write(tmp,tmp);
            },"A"+i).start();
        }


        for (int i = 0; i < 50; i++) {

            int tmp=i;
            new Thread(()->{
                lock.read(tmp);
            },"A"+i).start();
        }

    }
}
