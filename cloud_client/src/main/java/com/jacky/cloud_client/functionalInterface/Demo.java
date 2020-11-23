package com.jacky.cloud_client.functionalInterface;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.List;
import java.util.function.Supplier;

/**
 * @Description
 * @Author liyj
 * @Date 2020/11/23 4:35 下午
 */
public class Demo {

    /**
     * Predicate<T>
     *     method : test(T t)
     * 判断，返回boolean
     */
    public static void testPredicate(){
        Predicate<BigDecimal> predicate = x -> x.compareTo(BigDecimal.ZERO) > 0;
        Predicate<BigDecimal> predicate2 = x -> x.compareTo(BigDecimal.TEN) <= 0;
        System.out.println(predicate.test(new BigDecimal(-1)));
        System.out.println(predicate.and(predicate2).test(new BigDecimal(10)));
    }

    /**
     * Consumer<T>
     *     method : accept(T t)
     * 消费一条消息，无返回值
     */
    public static void testConsumer(){
        Consumer<List<Student>> consumer = x -> {
            for (Student student : x) {
                student.setScore(student.getScore() * 100);
            }
        };
        List<Student> list = new ArrayList<>(2);
        list.add(new Student(0.3));
        list.add(new Student(0.5));
        for (Student student : list) {
            System.out.println(student.getScore());
        }
        consumer.accept(list);
        for (Student student : list) {
            System.out.println(student.getScore());
        }
    }

    /**
     * Function
     *      method : R apply(T t)
     * 讲 T 转换成 R
     */
    public static void testFunction(){
        Function<Student,Double> function = x -> x.getScore();
        Student s = new Student(89d);
        System.out.println(s);
        System.out.println(function.apply(s));
    }

    /**
     * 生产一条消息
     */
    public static void testSupplier(){
        Supplier<String> supplier = () -> "supplier return";
        System.out.println(supplier.get());
    }

    public static void test(PredicateInterface predicateInterface){
        System.out.println(predicateInterface.isBiggerThen100(50));
        System.out.println(predicateInterface.isBigger(100));
    }

    public static void main(String[] args) {
//        testPredicate();
//        testConsumer();
//        testFunction();
        testSupplier();
        PredicateInterface<Integer> predicateInterface = x->x.compareTo(10) > 0;
        test(predicateInterface);
    }

    static class Student{
        private Double score;

        public Student(Double score) {
            this.score = score;
        }

        public Double getScore() {
            return score;
        }

        public void setScore(Double score) {
            this.score = score;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "score=" + score +
                    '}';
        }
    }

}
