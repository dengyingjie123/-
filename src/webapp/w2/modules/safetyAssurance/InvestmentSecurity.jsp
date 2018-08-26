<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.service.cms.ArticleService" %>
<%@ page import="com.youngbook.entity.po.cms.ArticlePO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv=“X-UA-Compatible” content=“IE=8″>
    <title>投资安全</title>

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
    <a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex">安全保障</a> &gt; 投资安全
</div>

<div id="container" style="">
    <div class="tabs-wrap" style="text-align: center;width:1000px;margin:0 auto;background-color:white;padding: 10px;">
        <div style="display:block;;" class="about_cont">
            <h1>投资安全</h1>
        </div>
        <div style="text-align: left;" class="about_cont">
            <h5>财富行业领导者 保驾护航</h5>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点金派平台依托于强大的股东背景，拥有一支专业、资深而富有实战经验的高管及理财规划师团队，专注于高端人群财富管理，通过向其提供度身定造的金融服务，将客户的资产进行管理与配置，以满足其不同阶段的财务需求，帮助其达到降低风险、实现财富增值的目的。</p>
            <h5>严格风控标准 项目优选</h5>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点金派平台的建立监管、公司内控、外部律所/审计顾问等的多维度风控体系标准，投资产品经过严格的内部审核，全流程、多指标的评估与筛选，为客户投资理财的稳健收益提供坚实的保障。</p>
            <h5>投资项目信息披露 公开透明</h5>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点金派平台投资项目上线时，基础信息均在网站进行完整公示，基础资产、交易对手、增信措施、控制流程、风险要点等一目了然。故客户购买前即可清楚了解项目品质并根据自身风险偏好精确选择产品。即使错过该款产品，有心客户仍可根据公开透明的披露信息时刻关注项目进展，感受优质项目的发展进度与过程。正所谓点金派在手，信息不愁。</p>
        </div>
    </div>

</div>
<jsp:include page="/w2/bottom.jsp"/>

</body>
</html>
