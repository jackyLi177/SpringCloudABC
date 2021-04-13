package com.jacky.auth_center.config;

import com.jacky.auth_center.model.DO.SysUser;
import com.jacky.auth_center.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Description
 * @Author liyj
 * @Date 2020/12/1 10:33 上午
 */
@Component
public class CustRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 认证，失败的话返回null
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        //密码
        String password = String.copyValueOf(usernamePasswordToken.getPassword());

        //用户名
        String principal = usernamePasswordToken.getUsername();

        SysUser user = sysUserService.getByName(principal);
        if (Objects.isNull(user)) {
            return null;
        }

        /**
         * 第一个参数 getPrincipal()获得，自己按需要传
         * 第二个参数 是用来和authenticationToken里的密码比对
         */
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user,user.getPass(), this.getName());
        return simpleAuthenticationInfo;
    }

    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        CustomerCredentialsMatcher customerCredentialsMatcher = new CustomerCredentialsMatcher();
        super.setCredentialsMatcher(customerCredentialsMatcher);
    }

    /**
     * 当传入的是 UsernamePasswordToken 时使用本Realm
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }
}
