package com.yijia.concurrent.consumerAndProducer;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Barrier {


    public static void main(String[] args) {


        CyclicBarrier cyclicBarrier =new CyclicBarrier(5,()->{

            System.out.println("5年了，你知道我这5年是怎么过的吗？");

        });
        for (int i = 0; i <5 ; i++) {

            new Thread(()->{

                System.out.println(Thread.currentThread().getName()+":阿伟死了");

                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },"A"+i).start();
        }


    }
}
