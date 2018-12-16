<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>

<h2>Hello World!</h2>
<%-- 将这个值传递给HelloServlet这个类处理，使用post方法 --%>
<p>测试中文输出</p>
<form>
    <%--<input type="submit" value="Post请求方式处理HelloServlet">--%>
    <jsp:useBean id="user" scope="session" class="entity.User"/>
    <table>
        <tr>
            <td>用户名：</td>
            <td><jsp:getProperty name="user" property="userName" /></td>
            <td>手机：</td>
            <td><jsp:getProperty name="user" property="telephone" /></td>
            <td>开始日期：</td>
            <td><jsp:getProperty name="user" property="startMonth" /></td>
            <td>结束日期：</td>
            <td><jsp:getProperty name="user" property="endMonth" /></td>
        </tr>
    </table>
</form>
</body>
</html>
