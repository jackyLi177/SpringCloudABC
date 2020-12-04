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
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @Description preHandle->isAccessAllowed->isLoginAttempt->executeLogin
 * @Author liyj
 * @Date 2020/12/1 5:31 下午
 */
public class CustJwtAuthFilter extends AuthenticatingFilter {

    /**
     * 跨域支持
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     * @throws Exception
     */
/*    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(org.springframework.http.HttpStatus.OK.value());
            return false;
        }
        return true;
    }*/

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            if (isLoginRequest(request, response)) {
                boolean allow = false;
                allow = executeLogin(request, response);
                return allow;
            } else {
                saveRequestAndRedirectToLogin(request, response);
            }
        } catch (Exception e) {
            throw new BizException(e.getMessage());
        }
        return false;
    }

    @Override
    protected boolean isLoginRequest(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String header = httpServletRequest.getHeader("auth-token");
        return StringUtils.isNotBlank(header);
    }

    /**
     * 执行登陆操作
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("auth-token");
        JWTToken jwtToken = new JWTToken(token);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(jwtToken);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    @Override
    protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        saveRequest(request);
        redirectToLogin(request, response);
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        String username = servletRequest.getParameter("username");
        String token = JwtUtil.sign(username, JwtUtil.salt, 10);
        if (StringUtils.isNotBlank(token) && !JwtUtil.isTokenExpired(token)) {
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
        return false;
    }

    /**
     * 如果Shiro Login认证成功，会进入该方法，等同于用户名密码登录成功，我们这里还判断了是否要刷新Token
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        String newToken = null;
        if (token instanceof JWTToken) {
            JWTToken jwtToken = (JWTToken) token;
            SysUser user = (SysUser) subject.getPrincipal();
            Date expireDate = JwtUtil.getIssuedAt(jwtToken.getToken());
            LocalDateTime expireDateTime = LocalDateTime.ofInstant(expireDate.toInstant(), ZoneId.systemDefault());
            //token 过期时间还剩小于一分钟就重新刷新token
            boolean needReBuild = LocalDateTime.now().minusSeconds(60).isAfter(expireDateTime);
            if (needReBuild) {
                newToken = JwtUtil.sign(user.getName(), JwtUtil.salt, JwtUtil.time);
            }
        }
        if (StringUtils.isNotBlank(newToken)) {
            httpResponse.setHeader("auth-token", newToken);
        }
        return true;
    }


}
