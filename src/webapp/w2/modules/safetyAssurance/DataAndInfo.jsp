<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.service.cms.ArticleService" %>
<%@ page import="com.youngbook.entity.po.cms.ArticlePO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv=“X-UA-Compatible” content=“IE=8″>
    <title>技术安全</title>

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
    <a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex">安全保障</a> &gt; 技术安全
</div>

<div id="container">
    <div class="tabs-wrap" style="text-align: left;width:1000px;margin:0 auto;background-color:white;padding: 10px;">
        <div  style="display:block" class="about_cont">
            <h1>技术安全</h1>
        </div>
        <div style="text-align: left;" class="about_cont">
          <h5>  1.存储安全</h5>
          <p>  敏感数据全加密存储<br/>
            合同文件只读防篡改<br/>
            热备/每天全量冷备<br/>
            数据异地存储多份<br/></p>
            <h5>   2.网络安全</h5>
            <p>  HTTPS加密传输<br/>
            内网加密传输<br/>
            跨机房专线传输<br/>
            多重防火墙、堡垒机<br/></p>
            <h5>      3.网站安全</h5>
            <p>     参数签名防篡改<br/>
            常见Web漏洞预防<br/>
            账户安全锁定策略<br/>
            安全扫描/渗透性测试<br/></p>
            <h5>  4.管理安全</h5>
            <p>  完善的权限控制体系<br/>
            规范的内部操作流程<br/>
            关键岗位操作监控<br/>
            数据禁止提供给第三方<br/></p>
        </div>
    </div>


</div>

<jsp:include page="/w2/bottom.jsp"/>

</body>
</html>
