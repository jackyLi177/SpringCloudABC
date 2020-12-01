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
        return passwordEncoder.matches(currentPass,dbPass);
    }
    
}
