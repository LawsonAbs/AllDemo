<%--
  Created by IntelliJ IDEA.
  User: enmonster
  Date: 2018/7/3
  Time: 11:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="en">
    <style>
        <!--html, body {. .}这个是设置html, body的样式 -->
        html, body {
            width: 100%;
            height: 100%;
            padding: 0;
            margin: 0;
        }
    </style>
    <script src="../resources/js/echarts.min.js"></script>
    <script src="../resources/js/jquery-3.3.1.min.js"></script>
    <script src="../resources/js/DatePicker.js"></script>
</head>
<body>
<div id="content" style="height:100%; width:100%;display: table;">

    <div id="left" style="background: #99B898; width:40%; height:100%;
            position:relative;top:0;left: 0px; display:table-cell;">
        <div id = "notation" style="left: 3%; position: absolute">
            <p style="text-align: center; font-weight:600;font-size: 30px;color: #000000;">The Statistics of Call</p>
            <p style="background-color: darkcyan;width: 120px; font-size: 18px ">1.Annotation</p>

            <ol class="notation">
                <li>Picture in upper left corner: His/Her  specific call information.</li>
                <li>Picture in upper right corner: His/Her best friend.</li>
                <li>Picture in bottom Left Corner: His/Her best friend</li>
            </ol>

            <p style="background-color: darkcyan;width: 90px; font-size: 18px">2.Options</p>
            <!--
            1.实现点击点击submit不跳转的功能
            2.但是必须满足width=0,height=0这个条件，因为iframe这是一个内联窗口的属性。
            -->
            <iframe width="0px" height="0px" name="actionframe" style="border: none"></iframe>
            <form style=""
                  action="/monthStat.getPhoneNumber" method="get" onsubmit="return sumbit_sure()" target="actionframe">
                <br>
                Phone&nbsp;Number:<input name="phoneNumber" style="font-size: 15px" type="text" value="18907263863">
                <br><br>
                Start&nbsp;&nbsp;Month:<input name="startMonth" style="font-size: 15px" type="text" onclick="setmonth(this)">
                <br><br>
                End&nbsp;&nbsp;&nbsp;&nbsp;Month:<input name="endMonth" style="font-size: 15px" type="text" onclick="setmonth(this)">
                <input style="right: 10%;background: #12aff0;color: #000000;font-size: 15px;border: none;cursor:pointer;height: 22px"
                       type="submit" value="Submit">
                <br>
            </form>
        </div>
        <div id ="footer" style="width: 100%;height: 20%;position: absolute;bottom: 10px">
            <ul style="">
                <li><strong>email:</strong>shenliu@ahnu.edu.cn</li>
                <li><strong>csdn:</strong><a href="https://blog.csdn.net/liu16659" target="_blank">https://blog.csdn.net/liu16659</a></li>
                <li><strong>github:</strong><a href="https://github.com/LittleLawson" target="_blank">https://github.com/LittleLawson</a></li>
            </ul>
        </div>
    </div>

    <div id="right" style="height:100%; width:60%; position: relative;display: table-cell;">
        <div id="top" style="background: #FFFFFF; height: 420px; position: absolute; top:0;left: 0;width: 50%">
            <div id="topLeft" style="position:absolute; height:100%;width: 90%">
            </div>
            <div id="topRight" style="position:absolute; left: 552px; width: 90%;height:100%;"><!--#FFA500-->
            </div>
        </div>

        <div id="bottom" style="background: #FFFFFF; height: 420px; position: absolute;top:490px;width: 50%;">
            <div id="bottomLeft" style="position: absolute;height:100%;width:90% ">
            </div>
            <!--width:90%  指的是在父框架的基础上的90%-->
            <div id="bottomRight" style="position: absolute; left: 552px; height:100%;width: 90%;">
            </div>
        </div>
    </div>
</div>


<!--每月通话详情-->
<script type="text/javascript">
    var myChart = echarts.init(document.getElementById('topLeft'));
    myChart.showLoading();
    var option = {
        xAxis: {
            type: 'category',
            data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            data: [120, 200, 150, 80, 70, 110, 130],
            type: 'bar'
        }]
    };
    //设置option
    myChart.setOption(option);
</script>

</body>
</html>
