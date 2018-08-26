<%--
  Created by IntelliJ IDEA.
  User: zhouhaihong
  Date: 2015/10/15
  Time: 15:24
  To change this template use File | Settings | File Templates.
--%><%@ page import="com.youngbook.common.config.Config" %>
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
    <meta http-equiv=“X-UA-Compatible” content=“IE=8″>
<style type="text/css">
    .curren_item{
        color: #d28d2a;
    }

</style>
    <script>
        /**
         * 当点击后样式发生变化（设为当前的）
         * @param element
         */
        function current(element) {
            var id = element.id;
            var parent = element.parentNode;
            var hrefs = parent.getElementsByTagName("a");
            for(var i = 0; i < hrefs.length; i ++) {
                var href = hrefs[i];
                var hrefId = href.id;
                if(id == hrefId) {
                    href.setAttribute("class", "curren_item");
                } else {
                    href.setAttribute("class", "");
                }
            }
        }

    </script>
</head>
<body>

<div id="top">
  <div id="top-content" class="w1200" >
      开普乐旗下在线投资理财金融服务平台 | 客服热线：0755-85024000
    <%
      if (loginUser == null) { // 未登录
    %>  <span>
             <a href="<%=Config.getWebRoot()%>/w2/customer/LoginRequest">登录</a>
            <a href="<%=Config.getWebRoot()%>/w2/customer/MobileRegisterRequest" style="background:#ff6900;">立即注册</a>
         </span>
    <%
    } else { // 已登录
    %>
             <span>
            	<font color="#ff6900">欢迎：<%=loginUser.getLoginName()%>
                </font>
            	<a href="<%=Config.getWebRoot()%>/w2/customer/IndexShow">我的账户</a>
                <a href="<%=Config.getWebRoot()%>/w2/customer/RemoveUser">退出</a>
                <a href="<%=Config.getWebRoot()%>/w2/help.jsp">帮助</a>
            </span>
    <%
      }
    %>
  </div>
</div>

<div style="width: 100%;background: #fafafa;" >
<div id="nav" class="w1200" style="padding-top: 7px;padding-bottom: 7px;">
  <a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex"><img src="<%=Config.getWebRoot()%>/w2/img/logo_Assistor.png" alt="开普乐"/></a>
 <span>
     <img src="<%=Config.getWebRoot()%>/w2/img/home.png" align="left"><a id="param1" href="<%=Config.getWebRoot()%>/w2/index/ShowIndex" >返回首页</a>
     </span>
</div>
</div>
</body>
</html>
