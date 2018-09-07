<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page import="com.youngbook.entity.wvo.production.ProductionWVO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv=“X-UA-Compatible” content=“IE=8″>
    <title>新手指引</title>

    <link href="<%=Config.getWebRoot()%>/w2/dist/css/bootstrap-combined.min.css" rel="stylesheet" media="screen">
    <link rel="stylesheet" href="<%=Config.getWebRoot()%>/w2/dist/css/bootstrap-theme.min.css"/>

    <link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/about/about.css" rel="stylesheet" type="text/css"/>

    <script language="javascript" src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/js/bootstrap.min.js"></script>


</head>

<body>


<jsp:include page="/w2/top.jsp"/>
<%--导航栏--%>
<div style="width: 100%;background: #fafafa;" >
    <div id="nav" class="w1200" style="padding-top: 7px;padding-bottom: 7px;">
        <a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex"><img src="<%=Config.getWebRoot()%>/w2/img/logo_Assistor.png" alt="开普乐"/></a>
        <span>
          <a id="param1" href="<%=Config.getWebRoot()%>/w2/index/ShowIndex" class="item" >首页</a>
        <a id="param2" href="<%=Config.getWebRoot()%>/w2/modules/production/productionList.jsp" class="item">投资专区</a>

          <a id="param3" href="<%=Config.getWebRoot()%>/w2/modules/about.jsp" style="color:#d28d2a" >新手指引</a> |
            <a id="param4" href="<%=Config.getWebRoot()%>/w2/customer/IndexShow" >我的账户</a>
        </span>
    </div>
</div>


<div class="w1200" id="container" style=" padding-top: 0px;">

<div class="bg_top" >
</div>

    <div class="bg_about_title">

    <img src="<%=Config.getWebRoot()%>/w2/img/bg_hbtb_title.png">
    </div>

<div class="font-24pk" style="margin: 0 auto">
    <p>点金派是开普乐旗下独立运作的互联网金融服务平台</p>
    <p>由深圳开普乐科技有限公司引用先进创新的技术手段成功搭建并提供维护支持服务</p>
    <p>&nbsp;</p>
    <p>点金派依托实力雄厚的股东背景、丰富的行业资源和强大资本运作能力</p>
    <p>集合自身产品研发优势以及健全的风控体系，严格筛选优质项目</p>
    <p>为投资者用户提供安全、高收益的理财产品，实现财富保值增值</p>

</div>

    <div class="bg_about_title">

        <img src="<%=Config.getWebRoot()%>/w2/img/bg_hb_advantage.png">
    </div>

<div class="bg_advantage_icon">
</div>


    <div class="bg_about_title">

        <img src="<%=Config.getWebRoot()%>/w2/img/bg_hb_flow.png">
    </div>

    <%--流程图片--%>
    <div class="row-fluid flow-pic">
        <div class="span12">
            <div class="carousel slide" id="carousel-884809">
                <div class="carousel-inner">
                    <div class="item active">
                        <img alt="" src="<%=Config.getWebRoot()%>/w2/img/bg_investmentflow1.png" style="width: 940px;height:600px;"/>

                    </div>
                    <div class="item">
                    <img alt="" src="<%=Config.getWebRoot()%>/w2/img/bg_investmentflow2.png"  style="width: 940px;height:600px;"/>
                    </div>
                    <div class="item">
                    <img alt="" src="<%=Config.getWebRoot()%>/w2/img/bg_investmentflow3.png"  style="width: 940px;height:600px;"/>
                    </div>
                    <div class="item">
                    <img alt="" src="<%=Config.getWebRoot()%>/w2/img/bg_investmentflow4.png"  style="width: 940px;height:600px;"/>
                    </div>

                </div>
                <%--左右滑动图片--%>
                <a data-slide="prev" href="#carousel-884809" class="left carousel-control carousel-control-left" ></a>
                <a data-slide="next" href="#carousel-884809" class="carousel-control carousel-control-right" id="next-onclick"    > </a>
            </div>
        </div>
    </div>

</div>
<div style="height:60px; "></div>
<jsp:include page="/w2/bottom.jsp"/>

</body>
</html>
