<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.UserPO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  UserPO loginUserInSession = Config.getLoginUserInSession(request);
  if (loginUserInSession == null) {
    out.println("<script>window.location='"+Config.getWebDeheSales()+"/login.jsp'</script>");
    return;
  }
%>
<%
  String version = request.getParameter("version");
  System.out.println("version: " + version);
%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <title>理财圈</title>
    <script type="text/javascript" src="<%=Config.getWebDeheSales()%>/include/js/config.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/hashMap.js"></script>
    <link rel="stylesheet" href="<%=Config.getWebDeheSales()%>/include/framework/framework7-1.6.5/css/framework7.material.css">
    <link rel="stylesheet" href="<%=Config.getWebDeheSales()%>/include/framework/framework7-1.6.5/css/framework7.material.colors.css">
    <link rel="stylesheet" href="<%=Config.getWebDeheSales()%>/include/css/my-app.css">
    <link rel="stylesheet" href="<%=Config.getWebDeheSales()%>/include/css/material-icons.css">
    <script type="text/javascript">
        var loginUser = '<%=Config.getLoginUserInSession2Json(request)%>';
        console.log(loginUser);
        loginUser = eval("(" + loginUser + ")")
    </script>
  </head>
  <body>
    <div class="statusbar-overlay"></div>
    <div class="panel-overlay"></div>

    <div class="views tabs toolbar-through">
      <div id="view-home" class="view view-main tab active">

        <div class="pages navbar-through">
          <div data-page="index-1" class="page">
            <div class="page-content">

            </div>
          </div>
        </div>

      </div>


      <!-- 市场视图 开始 -->
      <div id="view-market" class="view tab">

        <div class="pages navbar-fixed">

          <div data-page="index_market" class="page">

            <div class="page-content">

            </div>
          </div>
        </div>

      </div>
      <!-- 市场视图 结束 -->

      <!-- -->
      <div id="view-customer" class="view tab">


        <div class="pages navbar-fixed">
          <div data-page="index-customer" class="page">



            <div class="page-content">

            </div>
          </div>
        </div>

      </div>
      <div id="view-4" class="view tab">
        <div class="pages navbar-fixed">
          <div data-page="index-4" class="page">

            <div class="page-content">


            </div>
          </div>
        </div>
      </div>



      <div id="view-5" class="view tab">
        <div class="pages navbar-fixed">
          <div data-page="index-4" class="page">

            <div class="page-content">

            </div>
          </div>
        </div>
      </div>






      <div id="mainToolbar" class="toolbar tabbar tabbar-labels">
        <div class="toolbar-inner">
          <a id="btn-view-home" href="#view-home" class="tab-link active"><i class="icon tabbar-index-icon-home"></i><span class="tabbar-label">首页</span></a>
          <a id="btn-view-2" href="#view-market" class="tab-link"><i class="icon tabbar-index-icon-market"></i><span class="tabbar-label">市场</span></a>
          <a id="btn-view-customer" href="#view-customer" class="tab-link"> <i class="icon tabbar-index-icon-customer"></i><span class="tabbar-label">客户</span></a>
          <a id="btn-view-4" href="#view-4" class="tab-link"> <i class="icon tabbar-index-icon-circel"></i><span class="tabbar-label">圈子</span></a>
          <a id="btn-view-5" href="#view-5" class="tab-link"> <i class="icon tabbar-index-icon-mine"></i><span class="tabbar-label">我</span></a>
        </div>
      </div>
    </div>
    <script type="text/javascript" src="<%=Config.getWebDeheSales()%>/include/framework/framework7-1.6.5/js/framework7.min.js"></script>
    <script type="text/javascript" src="<%=Config.getModernFrameworkRoot()%>/jquery.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/include/extensions/framework-modern.js"></script>
    <script type="text/javascript" src="<%=Config.getWebDeheSales()%>/include/js/pages.js"></script>
    <script type="text/javascript" src="<%=Config.getWebDeheSales()%>/include/js/my-app.js"></script>
    <script type="text/javascript" src="<%=Config.getWebDeheSales()%>/include/js/checkLogin.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/include/extensions/md5.js"></script>
  </body>
<script type="text/javascript">
    window.android.invokeAndroid('{jsId:"set_login_user",id:"'+loginUser['id']+'",name:"'+loginUser['name']+'",mobile:"'+loginUser['mobile']+'"}');
</script>
</html>