package servlet;

import entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("处理get 请求...");
        PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=utf-8");
        out.println("<strong>Login Servlet</strong>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("处理 post 请求...");
        request.setCharacterEncoding("utf-8");

        User user = new User();
        user.setUserName(request.getParameter("userName"));
        user.setTelephone(request.getParameter("telephone"));
        user.setStartMonth(request.getParameter("startMonth"));
        user.setEndMonth(request.getParameter("endMonth"));
        response.setContentType("text/html;charset=utf-8");

        //把注册成功的用户对象保存在session中
        request.getSession().setAttribute("user",user);

        //跳转到注册成功页面-> userInfo.jsp，同时传递这request 和 response 对象
        request.getRequestDispatcher("../userInfo.jsp").forward(request,response);
    }
}
