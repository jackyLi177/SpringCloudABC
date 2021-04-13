package com.jacky.auth_center.config;

import com.jacky.auth_center.common.JWTToken;
import com.jacky.auth_center.mapper.SysUserMapper;
import com.jacky.auth_center.model.DO.SysUser;
import com.jacky.auth_center.util.JwtUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Description
 * @Author liyj
 * @Date 2020/12/1 6:15 下午
 */
@Component
public class JWTRealm extends AuthorizingRealm {

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 最后返回的【SimpleAuthenticationInfo】参数解析：
     *      由于我们没有自定义我们的 【CredentialsMatcher】，所以会使用默认的【SimpleCredentialsMatcher】来进行登陆验证。
     *      查看该类源码 【doCredentialsMatch(...)】方法 可见 是通过使用token 和 【simpleAuthenticationInfo】构造函数的第二个参数进行简单的 equels 匹配。
     *      所以第二个参数要传 token 串
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JWTToken jwtToken = (JWTToken) authenticationToken;
        String username = JwtUtil.getUsername(jwtToken);
        if (StringUtils.isBlank(username)){
            throw new AuthenticationException("无效token");
        }
        SysUser user = userMapper.getOneByName(username);
        if (Objects.isNull(user)) {
            throw new AuthenticationException("user invalid");
        }
        boolean tokenExpired = JwtUtil.isTokenExpired(jwtToken.getToken());
        if (tokenExpired) {
            throw new AuthenticationException("您太久没有活动，请重新登陆");
        }
        if (!JwtUtil.verify(jwtToken.getToken(),username,user.getPass())) {
            throw new AuthenticationException("Username or password error");
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(jwtToken,jwtToken.getToken(),"jwtRealm");
        return simpleAuthenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
