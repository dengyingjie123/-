<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page import="com.youngbook.entity.wvo.production.ProductionWVO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>注册及认证</title>

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
    <a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex">帮助中心</a> &gt; 注册及认证
</div>


<div id="container">
    <div>
        <div class="about_cont" style="display:block;text-align: center;">
            <h1>注册及认证</h1>
        </div>
    </div>

    <div class="link-center">
        <div class="about_cont" style="display:block;text-align: left;font-size: 10px;">
            <h5>1.请用你的手机号码进行注册，分分钟搞定！</h5>
        </div>
        <p><img src="<%=Config.getWebRoot()%>/w2/img/about/001.png"/></p>
    </div>
    <br/>
    <div class="link-center">
        <div class="about_cont" style="display:block;text-align: left;font-size: 10px;">
            <h5>2.快速设置交易密码</h5>
        </div>
        <p><img src="<%=Config.getWebRoot()%>/w2/img/about/002.png"/></p>
    </div>
    <br/>
    <div class="link-center">
        <div class="about_cont" style="display:block;text-align: left;font-size: 10px;">
            <h5>3.一步实名认证</h5>
        </div>
        <p><img src="<%=Config.getWebRoot()%>/w2/img/about/003.png"/></p>
    </div>
</div>

<div style="height: 60px;"></div>

<jsp:include page="/w2/bottom.jsp"/>

</body>
</html>
