<%--
  Created by IntelliJ IDEA.
  User: luoyeN
  Date: 2020/5/15
  Time: 8:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>电商评论数据可视化</title>
    <!-- 引入 echarts.js -->
    <script src="https://cdn.jsdelivr.net/npm/echarts@4.7.0/dist/echarts.min.js" ></script>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="margin-left: auto;margin-right: auto; width: 800px;height:600px;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '商品评论关键词可视化',
            subtext: '仅展示前25个关键字',
            left: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
            left: 'center',
            top: 'bottom',
            data: [
                <c:forEach items="${commentList}" var="comment">
                ["${comment.commentKey}"],
                </c:forEach>
            ]
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {
                    show: true,
                    type: ['pie', 'funnel']
                },
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        series: [

            {
                name: '关键词：',
                type: 'pie',
                radius: [30, 180],
                center: ['50%', '50%'],
                roseType: 'area',
                data: [
                    <c:forEach items="${commentList}" var="comment">
                    {value:"${comment.commentValue}", name:"${comment.commentKey}"},
                    </c:forEach>
                ]
            }
        ]
    };


    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
</script>
</body>
</html>
