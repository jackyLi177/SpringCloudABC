package com.jacky.auth_center.common;

import com.alibaba.fastjson.JSONObject;
import com.jacky.auth_center.util.ADESUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description
 * @Author liyj
 * @Date 2020/12/4 10:45 上午
 */
@Intercepts({
        @Signature(type= Executor.class,method="update",args={MappedStatement.class,Object.class})
})
@Component
public class EncryptIntercept implements Interceptor {

    private ADESUtils adesUtils = ADESUtils.getInstance();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        System.out.println(args);
        Map map = JSONObject.parseObject(JSONObject.toJSONString(args[1]), Map.class);
        if (map.containsKey("tel")){
            String tel = (String) map.get("tel");
            String encrypt = adesUtils.encrypt(tel);
            map.put("tel",encrypt);
        }
        if (map.containsKey("idNum")){
            String idNum = (String) map.get("idNum");
            String encrypt = adesUtils.encrypt(idNum);
            map.put("idNum",encrypt);
        }
        args[1] = map;
        return invocation.proceed();
    }
}
