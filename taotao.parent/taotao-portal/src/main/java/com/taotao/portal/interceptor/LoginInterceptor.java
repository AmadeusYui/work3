package com.taotao.portal.interceptor;

import com.taotao.pojo.TbUser;
import com.taotao.portal.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import utils.CookieUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserServiceImpl userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //在handler执行之前处理
        //判断用户是否登录
        //1.cookie中取token
        String token = CookieUtils.getCookieValue(httpServletRequest, "TT_TOKEN");
        //2.根据token换取用户信息，调用sso的服务
        TbUser user = userService.getUserByToken(token);
        //取不到用户信息要强制登录,把请求的url传递给登陆页面
        if (null == user){
            httpServletResponse.sendRedirect(userService.SSO_BASE_URL+userService.SSO_PAGE_LOGIN + "?redirect=" +httpServletRequest.getRequestURL());
            //返回false
            return false;
        }
        //取到就放行
        //把用户信息放入request
        httpServletRequest.setAttribute("user",user);
        //返回值决定handler是否执行,true执行false不执行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        //在handler执行之后处理，返回modelandview之前

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        //返回modelandview之后，相应用户之后
    }
}
