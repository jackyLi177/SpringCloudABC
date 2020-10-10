package com.jacky.configserver;

import java.util.concurrent.locks.LockSupport;

/**
 * @Description
 * @Author Liyj
 * @Date 2020/10/9
 */
public class Test {

    public static void test() throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("打游戏中。。。。。。。。。。");
                LockSupport.park(); //其他事情都不干了，阻塞
                System.out.println("其他事情。。。。。。。。。。");
            }
        });
        t.start();
        Thread.sleep(3000);
        LockSupport.unpark(t);  //别打游戏了，干点正事！
        System.out.println("线程唤醒！！！！！！！！！！！！！");
    }

    public void test2(){
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    public static void main(String[] args) throws InterruptedException {
        test();
    }

}
