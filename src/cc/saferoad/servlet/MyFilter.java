package cc.saferoad.servlet;/*
@auther S0cke3t
@date 2021-12-03
*/

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "MyFilter", urlPatterns = {"/login"})
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String str = servletRequest.getParameter("pwd");
        if("111".equals(str)){
            filterChain.doFilter(servletRequest,servletResponse);
        }
        else {
            PrintWriter out = servletResponse.getWriter();
            out.println("Login error");
            out.flush();
            out.close();
        }
    }

    @Override
    public void destroy() {

    }
}
