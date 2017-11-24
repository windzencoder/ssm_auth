package com.wz.filter;

import com.wz.common.RequestHolder;
import com.wz.model.SysUser;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//登录过滤
@Slf4j
public class LoginFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        SysUser sysUser = (SysUser)req.getSession().getAttribute("user");
        if (sysUser == null) {//未登录 跳转到登录页面
            String path = "/signin.jsp";
            resp.sendRedirect(path);
            return;
        }
        //threadlocal
        RequestHolder.add(sysUser);//保存user到threadlocal
        RequestHolder.add(req);//保存request到threadlocal
        filterChain.doFilter(servletRequest, servletResponse);//过滤链继续
        return;
    }

    public void destroy() {

    }
}
