<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.TimeUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">

<head>

    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0">

    <title>厚币财富销售龙虎榜！</title>

    <script type="text/javascript" src="<%=Config.getWebRoot() %>/w2/js/common/public.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/w2/js/common/fweb.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/w2/js/common/leaderboard.js"></script>

    <link rel="stylesheet" href="<%=Config.getWebRoot() %>/w2/css/leaderboard/css.css" />

    <script>

        /**
         * 切换 Tab 事件和请求数据
         *
         * 作者：刘幻
         * 更新：邓超，增加数据请求代码
         *
         * 时间：2016年6月29日
         */
        $(function() {

            // 默认加载当日数据
            reqDate(0);

            $(".rank-list-cnt li").click(function() {
                $(this).find("div").addClass("active").parent().siblings().find("div").removeClass("active");

                // Tab 的序号，0 为统计当日的，1 为统计当月的
                var index = $(this).index();
                // 请求数据
                reqDate(index);

                $(".tab-cnt-item .item").eq(index).show().siblings(".item").hide();
            });
            var stickyTop = $('.smint').offset().top;
            var stickyMenu = function() {
                var scrollTop = $(window).scrollTop();
                if (scrollTop > stickyTop) {
                    $('.smint').css({
                        'position': 'fixed',
                        'top': 0
                    }).addClass('fxd');
                } else {
                    $('.smint').css({
                        'position': 'relative',
                        'top': 0
                    }).removeClass('fxd');
                }
            };
            stickyMenu();
            $(window).scroll(function() {
                stickyMenu();
            });
        });

    </script>
</head>

<body>

<section class="rank-list">
    <div class="rank-list-hd">
        <img src="/core/w2/img/leaderboard/rank-list-hd.jpg">
        <p class="data-time"><%=TimeUtils.getNowDate()%></p>
    </div>
    <div class="rank-list-cnt">
        <ul class="rank-list-item smint clearfix ">
            <li class="day-record"><div class="active"></div></li>
            <li class="month-record"><div></div></li>
        </ul>
        <div class="tab-cnt-item">
            <div class="item day-record clearfix"><!-- Ajax 动态写入内容 --></div>
            <div class="item month-record  clearfix" style="display: none;"><!-- Ajax 动态写入内容 --></div>
        </div>
    </div>
</section>

<div class="loading_box"><img src="/core/w2/img/leaderboard/loading.gif"> </div>

</body>
</html>