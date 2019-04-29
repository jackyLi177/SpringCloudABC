package com.jacky.zuuldemo.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：lyj
 * @date ：2019/4/29 10:37
 */
@Component
public class TokenFilter extends ZuulFilter {
    /**
     * 过滤器类型
     * pre：路由之前,可以在请求被路由之前调用，用在路由映射的阶段是寻找路由映射表的
     * routing：路由之时,在路由请求时候被调用，具体的路由转发过滤器是在routing路由器具体的请求转发的时候会调用
     * post： 路由之后,当routing，error运行完后才会调用该过滤器，是在最后阶段的
     * error：发送错误调用
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器顺序
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 判断是否需要进行过滤，为rue执行过滤
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 具体过滤的逻辑
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String token = request.getHeader("TOKEN");
        if (token == null){
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(401);
            context.setResponseBody("not logged in");
        }
        if (token.equals("12345")){
            context.setResponseStatusCode(200);
            context.setSendZuulResponse(true);
        }
        return null;
    }
}
