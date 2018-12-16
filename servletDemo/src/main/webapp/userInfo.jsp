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
    <jsp:useBean id="user" scope="session" class="entity.User"/>
    <table>
        <>
            <td>用户名：</td>
            <td><jsp:getProperty name="user" property="userName" /></td></br>
            <td>手机：</td>
            <td><jsp:getProperty name="user" property="telephone" /></td></br>
            <td>开始日期：</td>
            <td><jsp:getProperty name="user" property="startMonth" /></td></br>
            <td>结束日期：</td>
            <td><jsp:getProperty name="user" property="endMonth" /></td></br>
        </tr>
    </table>
</form>
</body>
</html>
