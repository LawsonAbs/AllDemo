<%--
  Created by IntelliJ IDEA.
  User: enmonster
  Date: 2018/7/3
  Time: 11:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head >
    <meta charset="en">
    <style>
        <!--html, body {. .}这个是设置html, body的样式 -->
        html, body {
            width: 100%;
            height: 100%;
            /*padding: 0;*/
            /*margin: 0;*/
        }
    </style>

    <style type="text/css">
        .bodystyle{
            filter:alpha(opacity=50);
            -moz-opacity:0.5;
            -khtml-opacity: 0.5;
            opacity: 0.7;
            width:100%;
            position:absolute;
            background:url(../resources/image/backgroud.jpg) ;
            background-attachment:fixed;
        }
    </style>
</head>
<body>

<%-- display: table; --%>
<div id="content"; style="height:100%; width:100%; align-content: center;text-align: center;">
    <%-- background: #99B898; --%>
    <div style=" width:100%; height:100%; top:0;left: 0px; ">
            <p style="text-align: center; font-weight:600;font-size: 30px;color: #000000;">The Statistics of Call</p>
    </div>
    <div id ="footer" style="width: 100%;height: 20%;position: absolute;bottom: 10px">
        <ul >
            <li><strong>email:</strong>shenliu@ahnu.edu.cn</li>
            <li><strong>csdn:</strong><a href="https://blog.csdn.net/liu16659" target="_blank">https://blog.csdn.net/liu16659</a></li>
            <li><strong>github:</strong><a href="https://github.com/LittleLawson" target="_blank">https://github.com/LittleLawson</a></li>
        </ul>
    </div>
</div>
</body>
</html>
