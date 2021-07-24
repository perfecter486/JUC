package com.yijia.concurrent.consumerAndProducer;


import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

class MyCallable  implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {


        System.out.println(Thread.currentThread().getName()+"执行了这个方法");

        return 1024;
    }
}

public class Test3 {


    public static void main(String[] args) {


        MyCallable callable =new MyCallable();
        FutureTask<Integer> futureTask1 =new FutureTask<Integer>(callable);

        new Thread(futureTask1,"A").start();
        new Thread(futureTask1,"B").start();


    }
}
