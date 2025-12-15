package com.itheima.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
//@ServletComponentScan 这个注解要加到启动类上
//@WebFilter(urlPatterns = "/*")
@Slf4j

public class demoFilter implements Filter {
    //初始化方法
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("初始化方法");
    }
    //拦截到请求后执行 可以多次
    @Override
    // 1. ServletRequest servletRequest - HTTP请求对象
    // 这是客户端发起的HTTP请求的封装对象
    // 2. ServletResponse servletResponse - HTTP响应对象
    // 用于向客户端发送响应的对象
    // 3. FilterChain filterChain - 过滤器链对象
    // 这是最重要的参数，用于控制请求是否继续传递
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("拦截到了请求");
        //放行
        filterChain.doFilter(servletRequest, servletResponse);

    }

    //web服务器关闭时销毁方法
    @Override
    public void destroy() {
        log.info("销毁方法");
    }
}
