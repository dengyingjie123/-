<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <script>

        function downloaded(){
            var ua = navigator.userAgent.toLowerCase();
            if(ua.match(/MicroMessenger/i)=="micromessenger") {
                // alert("是");

            } else {
                //location.href="http://emoji.adline.com.cn/"
                window.location="http://dhcircle.keplerlab.cn/core/dehecircle/release/apk/dehecircle.apk";
            }
        }
    </script>
</head>
<div style="margin: 0 auto; width:100%; text-align: center"><img src="<%=Config.getWebDehecircle()%>/include/img/open_with_browser.png" style="width: 100%;" /></div>
<br><br>
<br>

<script>downloaded();</script>
