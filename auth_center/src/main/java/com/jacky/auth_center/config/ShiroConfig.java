package com.jacky.auth_center.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.*;

/**
 * @Description
 * @Author liyj
 * @Date 2020/12/1 10:42 上午
 */
@Configuration
public class ShiroConfig {

    /**
     * 注入过滤器
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean filterRegistrationBean(SecurityManager securityManager) throws Exception {

        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        //设置登陆地址
        factoryBean.setLoginUrl("/user/need_login");
        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new HashMap<>();
        //设置我们自定义的JWT过滤器
        filterMap.put("jwt", new CustJwtAuthFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setSecurityManager(securityManager);
        Map<String, String> filterRuleMap = new LinkedHashMap<>();
        filterRuleMap.put("/user/login","anon");
        filterRuleMap.put("/user/register","anon");
        filterRuleMap.put("/user/need_login","anon");
        filterRuleMap.put("/user/index","anon");
        filterRuleMap.put("/index/index","jwt");
        // 所有请求通过我们自己的JWT Filter
        filterRuleMap.put("/**", "jwt");

        factoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return factoryBean;
    }

    /**
     * 设置自定义realm
     * @return
     */
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(JWTRealm realm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        List<Realm> realmList = new ArrayList<>();
        realmList.add(realm);
        manager.setRealms(realmList);
        return manager;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}
