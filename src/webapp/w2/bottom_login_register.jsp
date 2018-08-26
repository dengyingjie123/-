<%--
  Created by IntelliJ IDEA.
  User: zhouhaihong
  Date: 2015/10/15
  Time: 15:25
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
</head>
<body>


<div id="footer" style="height: 200px;margin-top: 0px;">
<br/>
    <br/>
    <div id="copyright" class="w1200" style="text-align:center;width:100%">
      <div style="font-size: 12px;margin-bottom: 10px;">版权所有 &copy; 深圳开普乐科技有限公司 Copyright Kelper Co.,Ltd. ALL Rights Reserved</div>
        <div style="font-size: 12px;margin-bottom: 10px;">粤ICP备16072634号</div>
    </div>
</div>
</body>
</html>
