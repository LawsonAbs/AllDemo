package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("处理get 请求...");
        PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=utf-8");
        out.println("<strong>Hello Servlet</strong>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("处理 post 请求...");
        PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=utf-8");
        String userName = request.getParameter("userName");
        System.out.println("jsp页面的值是："+userName);

        out.println("<strong>Hello Servlet</strong><br> userName :<strong>"+userName+"</strong>");
    }
}
