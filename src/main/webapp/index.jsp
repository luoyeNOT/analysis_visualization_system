<%--
  Created by IntelliJ IDEA.
  User: luoyeN
  Date: 2020/5/17
  Time: 17:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    request.setAttribute("path", request.getContextPath());
%>

<html>
<head>
    <title>首页</title>
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="${path}/static/js/toastr.min.css">
    <script src="${path}/static/js/jquery-1.12.4.js" type="application/javascript"></script>
    <script src="${path}/static/js/toastr.min.js" type="application/javascript"></script>
    <script type="application/javascript"
            src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        toastr.options = {
            closeButton: false, //是否显示关闭按钮
            debug: false, //是否使用debug模式
            progressBar: false,
            positionClass: "toast-top-center", //弹出窗的位置
            onclick: null,
            showDuration: "500", //显示的动画时间
            hideDuration: "500", //消失的动画时间
            timeOut: "2000", //展现时间
            extendedTimeOut: "2000", //加长展示时间
            showEasing: "swing", //显示时的动画缓冲方式
            hideEasing: "linear", //消失时的动画缓冲方式
            showMethod: "fadeIn", //显示时的动画方式
            hideMethod: "fadeOut" //消失时的动画方式
        };
    </script>
</head>
<body>


<div style="margin-left: auto;margin-top: 50px;margin-right: auto; width: 800px;height:600px;" align="center">
    <img src="${path}/static/img/logo.png" style="width: 400px;height: 130px">
    <form class="bs-example bs-example-form" role="form">

        <div class="input-group form-group "
             style="width: 500px; margin-left: auto;margin-top:50px;margin-right: auto; " id="div_product_id_input">
            <input type="text" class="form-control" placeholder="请输入商品编号" id="product_id">
            <span class="input-group-btn">
                        <button class="btn btn-default" type="button" id="btn_crawler"
                                class="btn btn-primary">爬取</button>
                    </span>
        </div>


    </form>
    <br><br>
    <div style="clear:both">


        <button id="btn_data_clean" class="btn btn-primary">数据清洗</button>
        <button id="btn_data_analysis" class="btn btn-warning">数据分析与持久化</button>
        <button class="btn btn-success" onclick="jump()">数据可视化</button>


        <br><br>
        <span>HDFS操作:</span>

        <button id="btn_file_upload" class="btn btn-default" data-toggle="tooltip" data-placement="bottom"
                title="上传爬取到的数据至HDFS">上传
        </button>
        <button id="btn_file_download" class="btn btn-info" data-toggle="tooltip" data-placement="bottom"
                title="下载处理后的数据至Linux本地">下载
        </button>
        <button id="btn_file_delete" class="btn btn-danger" data-toggle="tooltip" data-placement="right"
                title="删除HDFS中的文件目录">删除
        </button>
    </div>
</div>

</body>

<script>
    //爬虫按钮点击事件
    $('#btn_crawler').click(function () {
        var product_id = $('#product_id').val();
        var product_id_reg = /(^[\-0-9][0-9]*(.[0-9]+)?)$/;
        var div_product_id_input = $('#div_product_id_input');
        toastr.info('正在处理...');
        setTimeout(function () {
            if (product_id.length <= 0) {
                toastr.error('不可为空');
                div_product_id_input.addClass('has-error');
                return;
            } else if (!product_id_reg.test(product_id)) {
                toastr.error('商品编号仅能使用数字');
                div_product_id_input.addClass('has-error');
                return;
            } else {
                div_product_id_input.removeClass('has-error');
                div_product_id_input.addClass('has-success');
                $.ajax({
                    url: '${path}/crawler',
                    type: 'GET',
                    data: {'product_id': product_id},
                    success: function (result) {
                        toastr.success(result.map.msg);
                    },
                    error: function () {
                        toastr.error('出错了！');
                    }
                });
            }
        }, 1000);

        return false;
    });

    $('#btn_file_upload').click(function () {
        $.ajax({
            url: '${path}/fileUpload',
            type: 'POST',
            success: function (result) {
                toastr.success(result.map.msg);
            },
            error: function () {
                toastr.error('出错了！');
            }
        });
        return false;
    })

    $('#btn_file_download').click(function () {
        $.ajax({
            url: '${path}/fileDownload',
            type: 'POST',
            success: function (result) {
                toastr.success(result.map.msg);
            },
            error: function () {
                toastr.error('出错了！');
            }
        });
        return false;
    })

    $('#btn_file_delete').click(function () {
        $.ajax({
            url: '${path}/fileDelete',
            type: 'POST',
            success: function (result) {
                toastr.success(result.map.msg);
            },
            error: function () {
                toastr.error('出错了！');
            }
        });
        return false;
    })

    $('#btn_data_clean').click(function () {
        toastr.info('正在处理')
        $.ajax({
            url: '${path}/cleanDriver',
            type: 'GET',
            success: function (result) {
                toastr.success('数据清洗完成');
            },
            error: function () {
                toastr.error('出错了！');
            }
        })
    })

    $('#btn_data_analysis').click(function () {
        toastr.info('正在处理')
        $.ajax({
            url: '${path}/dataAnalysis',
            type: 'GET',
            success: function (result) {
                toastr.success('数据分析完成');
            },
            error: function () {
                toastr.error('出错了！');
            }
        })
    })

    function jump() {
        location.href = "${path}/visualization"
    }

    $(function () {
        $("[data-toggle='tooltip']").tooltip();
    });

</script>
</html>
