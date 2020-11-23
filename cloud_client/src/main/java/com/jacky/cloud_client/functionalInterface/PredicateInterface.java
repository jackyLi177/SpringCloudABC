package com.jacky.cloud_client.functionalInterface;

import java.util.List;
/**
 * @Description
 * @Author liyj
 * @Date 2020/11/23 4:36 下午
 */
@FunctionalInterface
public interface PredicateInterface<T> {

    boolean flag = false;

    boolean isBigger(T t);

    default boolean isBiggerThen100(Integer target){
        return target.compareTo(new Integer(100)) > 0;
    }

}
