package com.youngbook.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 作者：quan.zeng
 * 内容：创建代码
 * 时间：2015-12-5
 * 描述：跨域访问请求头设置
 *
 */
public class ApiFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
    }
    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        response.setHeader("Access-Control-Allow-Origin", "*");
        //允许的请求方法，一般是GET,POST,PUT,DELETE,OPTIONS
        response.setHeader("Access-Control-Allow-Methods", "POST,GET");
        //允许的请求头
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");

        chain.doFilter(request, response);
    }
    public void destroy() {
    }

}
