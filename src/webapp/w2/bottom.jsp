<%@ page import="com.youngbook.common.config.Config" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="info.jsp" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%
  // 判断用户是否登陆
  CustomerPersonalPO loginUser = null;
  if (request.getSession().getAttribute("loginUser") != null) {
    loginUser = (CustomerPersonalPO) request.getSession().getAttribute("loginUser");
  }
%>
<html>
<head>
  <title></title>
</head>
<body>

<div id="footer">
  <div id="footer-content" class="w1200" style="height:400px;">
    <div id="content1">
      <table>
        <tr>
          <td><img src="<%=Config.getWebRoot()%>/w2/img/area_about.png"/></td>
        </tr>
        <tr>
          <td class="footer-text"><a href="<%=Config.getWebRoot()%>/w2/modules/about/HopeWealth_Info.jsp">点金派介绍</a></td>
        </tr>
        <tr>
          <td class="footer-text"><a href="<%=Config.getWebRoot()%>/w2/modules/about/Management_Team.jsp">管理层介绍</a></td>
        </tr>
        <tr>
          <td class="footer-text"><a href="<%=Config.getWebRoot()%>/w2/modules/about/AnnouncementsAndNews.jsp">公告和新闻</a></td>
        </tr>
        <tr>
          <td class="footer-text"><a href="<%=Config.getWebRoot()%>/w2/modules/about/CompanyGroup.jsp">股东背景介绍</a></td>
        </tr>
      </table>
    </div>
    <div id="content2">
      <table>
        <tr>
          <td><img src="<%=Config.getWebRoot()%>/w2/img/area_help.png"/></td>
        </tr>
        <tr>
          <td class="footer-text"><a href="<%=Config.getWebRoot()%>/w2/modules/helpCenter/RegistrationAndCertification.jsp">注册及认证</a></td>
        </tr>
        <tr>
          <td class="footer-text"><a href="<%=Config.getWebRoot()%>/w2/modules/helpCenter/InvestmentOperation.jsp">投资操作</a></td>
        </tr>
        <tr>
          <td class="footer-text"><a href="<%=Config.getWebRoot()%>/w2/modules/helpCenter/TransferOperation.jsp">转让操作</a></td>
        </tr>
        <tr>
          <td class="footer-text"><a href="<%=Config.getWebRoot()%>/w2/modules/about.jsp">新手必读</a></td>
        </tr>
      </table>
    </div>
    <div id="content3">
      <table>
        <tr>
          <td><img src="<%=Config.getWebRoot()%>/w2/img/area_saft.png"/></td>
        </tr>
        <tr>
          <td class="footer-text"><a href="<%=Config.getWebRoot()%>/w2/modules/safetyAssurance/InvestmentSecurity.jsp">投资安全</a></td>
        </tr>
        <tr>
          <td class="footer-text"><a href="<%=Config.getWebRoot()%>/w2/modules/safetyAssurance/FundSecurity.jsp">资金安全</a></td>
        </tr>
        <tr>
          <td class="footer-text"><a href="<%=Config.getWebRoot()%>/w2/modules/safetyAssurance/DataAndInfo.jsp">技术安全</a></td>
        </tr>
        <tr>
          <td class="footer-text"><a href="<%=Config.getWebRoot()%>/w2/modules/safetyAssurance/Privacy.jsp">合法合规</a></td>
        </tr>
      </table>
    </div>

    <span id="footer-split">

    </span>

    <%--<div id="content4" style="float:left;margin-left:60px;">--%>
    <%--<img src="<%=Config.getWebRoot()%>/w2/img/tongbaocode.png" style="width:100px;height: 100px;"/><br/><br/>安卓手机客户端下载--%>
    <%--</div>--%>
    <%--<div id="content5" style="float:left;margin-left:90px;">--%>
    <%--<img src="<%=Config.getWebRoot()%>/w2/img/code.jpg" style="width:100px;height: 100px;"/><br/><br/>苹果手机客户端下载--%>
    <%--</div>--%>
    <div class="fTDC" style="display: none">

      <div class="fl">
        <img src="<%=Config.getWebRoot()%>/w2/img/androidCode.jpg" style="width:130px;height: 130px;margin-left:25px"/>
      </div>

      <div class="fr">
        <img src="<%=Config.getWebRoot()%>/w2/img/iOSCode.jpg" style="width:130px;height: 130px; margin-left: 60px;"/>
      </div>

      <div style="float: left; width:100px; margin: 10px 0px 0px 137px;">APP 手机端下载</div>

    </div>

    <div id="copyright" class="w1200" style="text-align: center;margin-top:20px;">
      <div style="font-size: 12px;margin-bottom: 10px;">版权所有 &copy; 深圳开普乐科技有限公司 Copyright Keplerlab Co.,Ltd. ALL Rights Reserved</div>
      <div style="font-size: 12px;margin-bottom: 10px;">粤ICP备16072634号</div>
    </div>
  </div>
</div>

</body>
</html>
