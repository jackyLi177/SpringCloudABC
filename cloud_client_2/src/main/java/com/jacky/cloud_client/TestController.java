package com.jacky.cloud_client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：lyj
 * @date ：2019/4/26 11:33
 */
@RestController
public class TestController {

    @Value("${server.port}")
    private Integer port;

    @GetMapping("/index")
    public String index(){
        return "book";
    }

    public static class MyTask implements Runnable{

        private String name;

        public MyTask(String name){
            this.name = name;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread " + name + " run-------------");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyTask task1 = new MyTask("A");
        MyTask task2 = new MyTask("B");
        MyTask task3 = new MyTask("C");

        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);
        Thread thread3 = new Thread(task3);

        thread1.start();
        thread1.join();
        thread2.start();
        thread2.join();
        thread3.start();
    }

}
