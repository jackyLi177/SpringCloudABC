package com.jacky.auth_center.config;

import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

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
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/auth/*"); //过滤规则，即所有的请求
        return filterRegistration;
    }

    /**
     * 设置自定义realm
     * @return
     */
    @Bean(name="securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(custRealm());
        return manager;
    }

    @Bean
    public CustRealm custRealm() {
        CustRealm userRealm = new CustRealm();
        return userRealm;
    }

    @Bean
    public JWTRealm jwtShiroRealm() {
        JWTRealm jwtRealm = new JWTRealm();
        return jwtRealm;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 设置过滤器，将自定义的Filter加入
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager());
        Map<String, Filter> filterMap = factoryBean.getFilters();
        filterMap.put("jwtFilter", createCustJwtAuthFilter());
        factoryBean.setFilters(filterMap);
        Map<String,String> filterChainDefinition = new HashMap<>();
        filterChainDefinition.put("/auth/*","jwtFilter");
        factoryBean.setFilterChainDefinitionMap(filterChainDefinition);
        return factoryBean;
    }

    //注意不要加@Bean注解，不然spring会自动注册成filter
    protected CustJwtAuthFilter createCustJwtAuthFilter(){
        return new CustJwtAuthFilter();
    }

    /**
     * 使用token验证后，取消session存储，请求都是无状态请求
     * @return
     */
    @Bean
    protected SessionStorageEvaluator sessionStorageEvaluator(){
        DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }

}
