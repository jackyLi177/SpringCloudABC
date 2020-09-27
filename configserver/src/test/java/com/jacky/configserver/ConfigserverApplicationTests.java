package com.jacky.configserver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigserverApplicationTests {

    public class MyTask implements Runnable{

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
            System.out.println("Thread" + name + "run-------------");
        }
    }

    @Test
    public void contextLoads() {
        MyTask task1 = new MyTask("A");
        MyTask task2 = new MyTask("B");
        MyTask task3 = new MyTask("C");

        new Thread(task1).start();
        new Thread(task2).start();
        new Thread(task3).start();

    }

}
