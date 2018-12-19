<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%
  CustomerPersonalPO loginCustomer =  Config.getLoginCustomerInSession(request);
%>
<div class="pages">

  <div data-page="mine-change-mobile-success" class="page">

    <div class="navbar">
      <div class="navbar-inner">
        <div class="left"></div>
        <div class="center sliding">更换手机</div>
      </div>
    </div>

    <div class="page-content">

      <div class="content-block">
        更改手机号成功
      </div>

      <div class="content-block">
        <a id="btn-customer-mobile-change-success" href="<%=Config.getWebRoot()%>/dehecircle/loadPage_dehecircle_mine_list" class="button button-raised button-fill">返回</a>
      </div>

    </div>
  </div>
</div>