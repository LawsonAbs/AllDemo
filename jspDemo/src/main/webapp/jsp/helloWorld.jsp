<%@ page import="java.util.Date" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<body>
<h2>Hello World!</h2>
<%
    out.println("Your ip is :" + request.getRemoteAddr()+"</br>");
%>
<%--step 1. 定义java 变量--%>
<%int i = 0; %>
<%int a, b, c; %>
<% Date date = new Date() ;%>

<%--step 2. 输出 java 变量值--%>
<%
    out.println(date.toString()+"</br>");
    out.println("the value of i: "+i);
%>
</body>
</html>
