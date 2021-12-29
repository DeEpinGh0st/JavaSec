package cc.saferoad.servlet.filtermemshell;/*
@auther S0cke3t
@date 2021-11-24
*/

import org.apache.catalina.core.StandardContext;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "MyServlet", value = "/MyServlet")
public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.println("Hello World~");
        out.println("Server path: " + request.getServletPath());
        out.println("Remote addr: " + request.getRemoteAddr());
        out.println("======Filters======");
        ServletContext servletContext = request.getServletContext();
        StandardContext standardContext = null;
        try {
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
            Field FilterConfigField = standardContext.getClass().getDeclaredField("filterConfigs");
            FilterConfigField.setAccessible(true);
            HashMap<String,Object> ConfigMap = (HashMap<String,Object>)FilterConfigField.get(standardContext);
            for (String name:
                 ConfigMap.keySet()) {
                out.println(name);
            }
            } catch(Exception e){
                e.printStackTrace();
            }
            out.flush();
            out.close();
        }
    }
