<%@ page import="com.youngbook.common.config.Config" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="format-detection" content="email=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>

    <title>产品详情</title>

    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/mobile/css/public.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/mobile/css/idangerous.swiper.css">

    <script type="text/javascript" src="<%=Config.getWebRoot() %>/mobile/js/public.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/mobile/js/idangerous.swiper.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/mobile/js/jquery-1.10.1.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/mobile/js/swiper.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/w2/js/common/fweb.js"></script>
    <script type="text/javascript"
            src="<%=Config.getWebRoot() %>/w2/js/modules/production/productionDetail2.js"></script>

</head>

<body>

<section class="pro-details">
    <div class="header-all">
        <span class="hd-news"><i class="icon-arrow1"></i></span>
        <h3>浏览产品信息</h3>
        <span class="hd-cfact"></span>
    </div>
    <div class="head-dtl">
        <ul>
            <li id="productionNames">
                <p>认购金额（万元）</p>
            </li>
            <li id="productionRate">
                <p>预期收益率</p>
            </li>

        </ul>
    </div>
    <div class="home-recommend-ct">
        <h3 class="tit"><i class="sjx-ico1"></i><i class="triangle-right"></i>基本信息</h3>
        <ul class="pro-info" id="productionBaseInfo">

        </ul>
    </div>


</section>
<div class="zbc-box"></div>
<div class="share-type">
    <div class="share-type-cd">
        <a href="#">
            <img src="<%=Config.getWebRoot() %>/mobile/images/detail/share-type1.png">
            <span class="name">微厚币 </span>
        </a>
        <a href="#">
            <img src="<%=Config.getWebRoot() %>/mobile/images/detail/share-type2.png">
            <span class="name">微信好友 </span>
        </a>
        <a href="#">
            <img src="<%=Config.getWebRoot() %>/mobile/images/detail/share-type3.png">
            <span class="name">朋友圈 </span>
        </a>
        <a href="#">
            <img src="<%=Config.getWebRoot() %>/mobile/images/detail/share-type4.png">
            <span class="name">QQ空间 </span>
        </a>
        <a href="#">
            <img src="<%=Config.getWebRoot() %>/mobile/images/detail/share-type5.png">
            <span class="name">新浪微博 </span>
        </a>
        <a href="#">
            <img src="<%=Config.getWebRoot() %>/mobile/images/detail/share-type6.png">
            <span class="name">复制链接 </span>
        </a>
    </div>
    <div class="share-type-btn">
        <span class="btn-close">取消</span>
        <span class="btn-share">立即分享</span>

    </div>
</div>

</body>

</html>