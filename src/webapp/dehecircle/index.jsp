<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.StringUtils" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page import="com.youngbook.common.utils.NumberUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  CustomerPersonalPO loginCustomer = Config.getLoginCustomerInSession(request);
  if (loginCustomer == null) {
      out.println("<script>window.location='"+Config.getWebDehecircle()+"/login.jsp'</script>");
      return;
  }
%>
<%
  String version = request.getParameter("version");
  System.out.println("version: " + version);
  String show = request.getParameter("show");
  if (StringUtils.isEmpty(show)) {
      show = "0";
  }
%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <title>德合汇</title>

    <script type="text/javascript" src="<%=Config.getModernFrameworkRoot()%>/jquery.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebDehecircle()%>/include/js/common.js"></script>
    <link rel="stylesheet" href="<%=Config.getWebDehecircle()%>/include/css/base.css" />
    <link rel="stylesheet" href="<%=Config.getWebDehecircle()%>/include/css/swiper.min.css" />

    <!-- Path to Framework7 Library CSS-->
    <!--<link rel="stylesheet" href="include/framework/framework7-1.6.5/css/framework7.material.min.css">-->
    <script type="text/javascript" src="<%=Config.getWebDehecircle()%>/include/js/config.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/hashMap.js"></script>
    <script type="text/javascript" src="<%=Config.getWebDehecircle()%>/include/framework/<%=Config.getF7Folder()%>/js/framework7.min.js"></script>

    <script type="text/javascript" src="<%=Config.getWebRoot()%>/include/framework/third-party/accounting.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/include/extensions/framework-modern.js?r=<%=NumberUtils.randomNumbers(5)%>"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/include/extensions/md5.js"></script>
    <!-- Path to your app js-->


    <link rel="stylesheet" href="<%=Config.getWebDehecircle()%>/include/framework/<%=Config.getF7Folder()%>/css/framework7.material.css">
    <link rel="stylesheet" href="<%=Config.getWebDehecircle()%>/include/framework/<%=Config.getF7Folder()%>/css/framework7.material.colors.css">
    <!-- Path to your custom app styles-->
    <link rel="stylesheet" href="<%=Config.getWebDehecircle()%>/include/css/my-app.css">
    <!--<link rel="stylesheet/less" type="text/css" href="include/less/my-app.less">-->
    <link rel="stylesheet" href="<%=Config.getWebDehecircle()%>/include/css/material-icons.css">
    <script type="text/javascript">
        var loginCustomer = '<%=Config.getLoginCustomerInSession2Json(request)%>';
        console.log(loginCustomer);
        loginCustomer = fm.convert2Json(loginCustomer);
    </script>
  </head>
  <body>
    <!-- Status bar overlay for fullscreen mode-->
    <div class="statusbar-overlay"></div>
    <!-- Panels overlay-->
    <div class="panel-overlay"></div>

    <div class="views tabs toolbar-through">

      <!-- Second view (for second wrap)-->
      <div id="view-home" class="view tab <%=show.equals("0")?"active":""%>">
        <div class="pages">
          <div data-page="index_home" class="page">
            <div class="page-content">
            </div>
          </div>
        </div>

      </div>


      <!-- 市场视图 开始 -->
      <div id="view-production" class="view tab <%=show.equals("1")?"active":""%>">
        <div class="pages">
          <div data-page="index_production" class="page">
            <div class="page-content">
            </div>
          </div>
        </div>

      </div>
      <!-- 市场视图 结束 -->

      <!-- -->
      <div id="view-customer" class="view tab <%=show.equals("2")?"active":""%>">
        <div class="pages">
          <div data-page="index-customer" class="page">
            <div class="page-content">
            </div>
          </div>
        </div>
      </div>


      <div id="mainToolbar" class="toolbar tabbar tabbar-labels toolbar-dehecricle">
        <div class="toolbar-inner">
          <a id="btn-view-home" href="#view-home" class="tab-link active"><i class="icon tabbar-index-icon-home"></i><span class="tabbar-label">首页</span></a>
          <a id="btn-view-production" href="#view-production" class="tab-link <%=show.equals("1")?"active":""%>"><i class="icon tabbar-index-icon-production"></i><span class="tabbar-label">投资理财</span></a>
          <a id="btn-view-3" href="#view-customer" class="tab-link <%=show.equals("2")?"active":""%>"> <i class="icon tabbar-index-icon-mine"></i><span class="tabbar-label">我的账户</span></a>
        </div>
      </div>


    </div>


    <!-- Path to Framework7 Library JS-->
    <script type="text/javascript" src="<%=Config.getWebDehecircle()%>/include/js/pages.js?r=<%=NumberUtils.randomNumbers(5)%>"></script>
    <script type="text/javascript" src="<%=Config.getWebDehecircle()%>/include/js/my-app.js?r=<%=NumberUtils.randomNumbers(5)%>"></script>
    <script type="text/javascript" src="<%=Config.getWebDehecircle()%>/include/js/checkLogin.js?r=<%=NumberUtils.randomNumbers(5)%>"></script>
    <script type="text/javascript" src="<%=Config.getWebDehecircle()%>/include/js/functions.js?r=<%=NumberUtils.randomNumbers(5)%>"></script>
  </body>
<script type="text/javascript">
    window.android.invokeAndroid('{jsId:"set_login_user",id:"'+loginCustomer['id']+'",name:"'+loginCustomer['name']+'",mobile:"'+loginCustomer['mobile']+'"}');
</script>
</html>