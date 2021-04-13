package com.jacky.auth_center.common;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Description
 * @Author liyj
 * @Date 2020/12/1 5:38 下午
 */
@Data
public class JWTToken implements AuthenticationToken {

    private String token;

    public JWTToken(String token){
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return this.token;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }
}
