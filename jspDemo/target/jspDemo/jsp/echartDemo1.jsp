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
        <!--html, body {. .}这个是设置html, body的样式-- >
        html, body {
            width: 100%;
            height: 100%;
            padding: 0;
            margin: 0;
        }
    </style>
    <script src="../resources/js/echarts.min.js"></script>
    <script src="../resources/js/jquery-3.3.1.min.js"></script>
</head>
<body>
<div id="content" style="height:100%; width:100%;display: table;">

    <div id="left" style="background: #99B898; width:40%; height:100%;
            position:relative;top:0;left: 0; display:table-cell;">
        <p style="text-align: center; font-weight: 400;font-size: 30px">The demo of Echart in Jsp</p>
        <h2 style="left: 50px; background-color: #123ff3;width: 150px; font-size: 18px ">1. Annotations</h2>
        <br style="text-align: left;left: 100px">
            This is a demo project about echart in jsp.</br>
            <strong>step 1:</strong> you should know how use java</br>
            <strong>step 2:</strong> you should know how use jsp</br>
            <strong>step 3:</strong> you should know how use html</br>
            <strong>step 4:</strong> you should know how use echart</br></br>
            <strong>outcome:</strong> As shown in the right!</br>
        </p>

        <h2 style="left:50px;background-color: #123ff3;width: 120px; font-size: 18px ">
            2.synopsis
        </h2>
        <p>
            This is a static html. The graph's data is in hard code, so you can't adjust the output.
            But you can study ajax to learn how load data dynamically.
        </p>
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
            <div id="topLeft" style="position:absolute; height:80%;width: 80%">
            </div>

            <div id="topRight" style="position:absolute; left: 500px;  height:80%;width: 80%">
            </div>
        </div>
        <div id="bottom" style="background: #FFFFFF; height: 400px; position: absolute;top:400px;width: 50%;">
            <div id="bottomLeft" style="position: absolute;height:80%;width:80% ">            </div>
            <!--width:90%  指的是在父框架的基础上的90%-->
            <div id="bottomRight" style="position: absolute; left: 500px; height:80%;width: 80%;">            </div>
        </div>
    </div>
</div>


<%-- topLeft --%>
<script type="text/javascript">
/*
01. 根据topLeft 的id，找到这个元素
02. 其后的方法则是 echart 生成图形所必须的
 */
    var myChart = echarts.init(document.getElementById('topLeft'));
    var option = {

        title:{
            text:'work hour',
            x:'center'
        },
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


<%-- topRight --%>
<script type="text/javascript">
    var myChart = echarts.init(document.getElementById('topRight'));
    var option = {

        title:{
            text:'study hour',
            x:'center'
        },
        xAxis: {
            type: 'category',
            data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            data: [20, 10, 50, 80, 100, 160, 180],
            type: 'bar'
        }]
    };
    //设置option
    myChart.setOption(option);
</script>


<%-- bottomLeft --%>
<script type="text/javascript">
   var option = {
        title : {
            text: '南丁格尔玫瑰图',
            subtext: '纯属虚构',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            x : 'center',
            y : 'bottom',
            data:['rose1','rose2','rose3','rose4','rose5','rose6','rose7','rose8']
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {
                    show: true,
                    type: ['pie', 'funnel']
                },
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        series : [
            {
                name:'半径模式',
                type:'pie',
                radius : [20, 110],
                center : ['25%', '50%'],
                roseType : 'radius',
                label: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: true
                    }
                },
                lableLine: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: true
                    }
                },
                data:[
                    {value:10, name:'rose1'},
                    {value:5, name:'rose2'},
                    {value:15, name:'rose3'},
                    {value:25, name:'rose4'},
                    {value:20, name:'rose5'},
                    {value:35, name:'rose6'},
                    {value:30, name:'rose7'},
                    {value:40, name:'rose8'}
                ]
            },
            {
                name:'面积模式',
                type:'pie',
                radius : [30, 110],
                center : ['75%', '50%'],
                roseType : 'area',
                data:[
                    {value:10, name:'rose1'},
                    {value:5, name:'rose2'},
                    {value:15, name:'rose3'},
                    {value:25, name:'rose4'},
                    {value:20, name:'rose5'},
                    {value:35, name:'rose6'},
                    {value:30, name:'rose7'},
                    {value:40, name:'rose8'}
                ]
            }
        ]
    };
   var myChart = echarts.init(document.getElementById('bottomLeft'));
   myChart.setOption(option);
</script>

<%-- bottomRight --%>
<script type="text/javascript">
    var option = {
        title: {
            text: '天气情况统计',
            subtext: '虚构数据',
            left: 'center'
        },
        legend: {
            // orient: 'vertical',
            // top: 'middle',
            bottom: 10,
            left: 'center',
            data: ['西凉', '益州','兖州','荆州','幽州']
        },
        series : [
            {
                type: 'pie',
                radius : '65%',
                center: ['50%', '50%'],
                selectedMode: 'single',
                data:[
                    {
                        value:1548,
                        name: '幽州',
                        label: {
                            normal: {
                                backgroundColor: '#eee',
                                borderColor: '#777',
                                borderWidth: 1,
                                borderRadius: 4,
                                rich: {
                                    title: {
                                        color: '#eee',
                                        align: 'center'
                                    },
                                    abg: {
                                        backgroundColor: '#333',
                                        width: '100%',
                                        align: 'right',
                                        height: 25,
                                        borderRadius: [4, 4, 0, 0]
                                    },
                                    Sunny: {
                                        height: 30,
                                        align: 'left'
                                    },
                                    Cloudy: {
                                        height: 30,
                                        align: 'left'
                                    },
                                    Showers: {
                                        height: 30,
                                        align: 'left'

                                    },
                                    weatherHead: {
                                        color: '#333',
                                        height: 24,
                                        align: 'left'
                                    },
                                    hr: {
                                        borderColor: '#777',
                                        width: '100%',
                                        borderWidth: 0.5,
                                        height: 0
                                    },
                                    value: {
                                        width: 20,
                                        padding: [0, 20, 0, 30],
                                        align: 'left'
                                    },
                                    valueHead: {
                                        color: '#333',
                                        width: 20,
                                        padding: [0, 20, 0, 30],
                                        align: 'center'
                                    },
                                    rate: {
                                        width: 40,
                                        align: 'right',
                                        padding: [0, 10, 0, 0]
                                    },
                                    rateHead: {
                                        color: '#333',
                                        width: 40,
                                        align: 'center',
                                        padding: [0, 10, 0, 0]
                                    }
                                }
                            }
                        }
                    },
                    {value:535, name: '荆州'},
                    {value:510, name: '兖州'},
                    {value:634, name: '益州'},
                    {value:735, name: '西凉'}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    var myChart = echarts.init(document.getElementById('bottomRight'));
    myChart.setOption(option);
</script>


</body>
</html>
