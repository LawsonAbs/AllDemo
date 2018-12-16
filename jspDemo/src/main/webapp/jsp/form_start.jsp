<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
</head>
<body>
<h1>使用 GET 方法读取数据</h1>
<%--step 1.这里的action就是用于页面的跳转。这个功能就是将这个页面得到的参数（name,url）提交给form.jsp这个页面，然后获取并打印出来 --%>
<form action="form.jsp" method="GET">

    <%-- step 2. 这里定义了一个表单，表单中包含两个变量，分别是name/url --%>
    站点名: <input type="text" name="name">
    <br />
    网址: <input type="text" name="url" />
    <input type="submit" value="提交" />
</form>
</body>
</html>
