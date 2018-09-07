<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.service.cms.ArticleService" %>
<%@ page import="com.youngbook.entity.po.cms.ArticlePO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv=“X-UA-Compatible” content=“IE=8″>
    <title>资金安全</title>

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
    <a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex">安全保障</a> &gt; 资金安全
</div>

<div id="container">
    <div class="tabs-wrap" style="text-align: center;width:1000px;margin:0 auto;background-color:white;padding: 10px;">
        <div class="about_cont" style="display:block">
            <h1 style="text-align: center">资金安全</h1>
        </div>
        <div style="text-align: left;" class="about_cont">
            <h5>1.第三方支持 划扣透明</h5>
            <p>客户授权委托第三方支付公司或银行对客户资金进行代付，资金流向全程清晰透明。<br/>
                第三方划付——客户资金的安全搬用工！</p>
            <h5>2.银行卡绑定 资金安全保障</h5>
            <p>客户本金划扣与收益支付只能通过客户在点金派绑定并认证过的银行账户来实现，保障客户资金来源特定，去向唯一。</p>

            <h5>3.传输加密 多级内控</h5>
            <p>点金派网站采用银行级监控技术，内部资金信息传输多层加密，分级设限、交叉复核，形成严格内部控制体系。<br/>
                点金派平台——客户资金的安全堡垒！</p>
        </div>
    </div>

</div>

<jsp:include page="/w2/bottom.jsp"/>

</body>
</html>
