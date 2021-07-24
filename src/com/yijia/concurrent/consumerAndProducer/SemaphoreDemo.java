package com.yijia.concurrent.consumerAndProducer;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

    public static void main(String[] args) {

        Semaphore parkingSpace=new Semaphore(3);


        int i=6;
        while(i-->0){


            new Thread(()->{

                try {
                    parkingSpace.acquire();

                    System.out.println(Thread.currentThread().getName()+"抢到位置了,还有："+parkingSpace.getQueueLength()+"在等待");
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    parkingSpace.release();
                }

            },"A"+i).start();
        }


    }
}
