<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page import="com.youngbook.entity.wvo.production.ProductionWVO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>点金派介绍</title>
    <link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/about/about.css" rel="stylesheet" type="text/css"/>

    <script language="javascript" src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>

</head>

<body>

<jsp:include page="/w2/top.jsp"/>

<div style="width: 100%;background: #fafafa;" >
    <div id="nav" class="w1200" style="padding-top: 7px;padding-bottom: 7px;">
        <a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex"><img src="<%=Config.getWebRoot()%>/w2/img/logo_Assistor.png" alt="开普乐"/></a>
        <span>
          <a id="param1" href="<%=Config.getWebRoot()%>/w2/index/ShowIndex" class="item" >首页</a>
        <a id="param2" href="<%=Config.getWebRoot()%>/w2/modules/production/productionList.jsp" >投资专区</a>

          <a id="param3" href="<%=Config.getWebRoot()%>/w2/modules/about.jsp" >新手指引</a> |
            <a id="param4" href="<%=Config.getWebRoot()%>/w2/customer/IndexShow" >我的账户</a>
        </span>
    </div>
</div>

<div id="position" class="w1200">
    <a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex">关于我们</a> &gt; 点金派介绍
</div>

<div id="container">
    <div>
        <div   style="display:block" class="about_cont">
            <h1 style="text-align: center">点金派介绍</h1>
        </div>
    </div>

    <div class="link-center">
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点金派是开普乐旗下独立运作的互联网金融服务平台，由深圳开普乐科技有限公司引用先进创新的技术手段成功搭建并提供维护支持服务。</p>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点金派依托实力雄厚的股东背景、丰富的行业资源和强大资本运作能力，集合自身产品研发优势以及健全的风控体系，严格筛选优质项目，为投资者用户提供安全、高收益的理财产品，实现财富保值增值。</p>
    </div>
</div>

<div style="height: 60px;"></div>
<jsp:include page="/w2/bottom.jsp"/>

</body>
</html>
