package com.jacky.auth_center.config;

import com.jacky.auth_center.common.JWTToken;
import com.jacky.auth_center.model.DO.SysUser;
import com.jacky.auth_center.util.JwtUtil;
import common.BizException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

/**
 * @Description
 * @Author liyj
 * @Date 2020/12/1 5:31 下午
 */
public class CustJwtAuthFilter extends AuthenticatingFilter {

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Object attribute = request.getAttribute("auth-token");
        if (Objects.isNull(attribute)){
            return false;
        }
        return true;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (this.isLoginRequest(request,response)){
            return true;
        }
        boolean allow = false;
        try {
            allow = executeLogin(request, response);
        } catch (Exception e) {
            throw new BizException(e.getMessage());
        }
        return allow;
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        String username = servletRequest.getParameter("username");
        String token = JwtUtil.sign(username, JwtUtil.salt, 10);
        if (StringUtils.isNotBlank(token) && !JwtUtil.isTokenExpired(token)){
            return new JWTToken(token);
        }
        return null;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletResponse httpResponse = WebUtils.toHttp(servletResponse);
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json;charset=UTF-8");
        httpResponse.setStatus(HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION);
        fillCorsHeader(WebUtils.toHttp(servletRequest), httpResponse);
        return false;
    }

    protected void fillCorsHeader(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,HEAD");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
    }

    /**
     *  如果Shiro Login认证成功，会进入该方法，等同于用户名密码登录成功，我们这里还判断了是否要刷新Token
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        String newToken = null;
        if(token instanceof JWTToken){
            JWTToken jwtToken = (JWTToken)token;
            SysUser user = (SysUser) subject.getPrincipal();
            Date expireDate = JwtUtil.getIssuedAt(jwtToken.getToken());
            LocalDateTime expireDateTime = LocalDateTime.ofInstant(expireDate.toInstant(), ZoneId.systemDefault());
            //token 过期时间还剩小于一分钟就重新刷新token
            boolean needReBuild = LocalDateTime.now().minusSeconds(60).isAfter(expireDateTime);
            if(needReBuild) {
                newToken = JwtUtil.sign(user.getName(),JwtUtil.salt,JwtUtil.time);
            }
        }
        if(StringUtils.isNotBlank(newToken)) {
            httpResponse.setHeader("auth-token", newToken);
        }
        return true;
    }
}
