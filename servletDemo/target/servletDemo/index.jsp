<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<h2>Hello World!</h2>
<p>中文输出 is messy code?</p>
<a href="servlet/HelloServlet">First Servlet</a>

<%-- 将这个值传递给HelloServlet这个类处理，使用post方法 --%>
<form action="servlet/HelloServlet" method="post">
    <%--<input type="submit" value="Post请求方式处理HelloServlet">--%>
        <input type="text" name="userName" >
</form>
</body>
</html>
