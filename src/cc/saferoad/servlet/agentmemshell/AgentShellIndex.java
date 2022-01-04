package cc.saferoad.servlet.agentmemshell;/*
@auther S0cke3t
@date 2021-12-30
*/

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import javassist.ClassPath;

@WebServlet(name = "AgentShellIndex", value = "/AgentShellIndex")
public class AgentShellIndex extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println(request.getQueryString());
    }
}
