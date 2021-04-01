package com.jacky.auth_center.config;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Description
 * @Author liyj
 * @Date 2020/12/1 4:25 下午
 */
public class CustomerCredentialsMatcher implements CredentialsMatcher {
    
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        //前端传来的密码
        String currentPass = String.copyValueOf((char[]) authenticationToken.getCredentials());
        //数据库密码
        String dbPass = (String) authenticationInfo.getCredentials();
        //注册时候，数据库存储的时候用的是该 加密类的密文，所以这里登录的时候需要用该类来解密
        return passwordEncoder.matches(currentPass,dbPass);
    }
    
}
