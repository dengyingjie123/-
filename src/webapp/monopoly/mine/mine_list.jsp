<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.NumberUtils" %>
<%@ page import="com.youngbook.action.customer.CustomerPersonalAction" %>
<%@ page import="com.youngbook.entity.vo.customer.CustomerPersonalVO" %>
<%@ page import="com.youngbook.entity.vo.Sale.PaymentPlanVO" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerScorePO" %>
<%@ page import="com.youngbook.entity.po.fdcg.FdcgCustomerQueryInfoPO" %>
<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.youngbook.common.utils.MoneyUtils" %>
<%@ page import="com.youngbook.entity.vo.customer.CustomerScoreVO" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%
  CustomerPersonalVO customerPersonalVO = (CustomerPersonalVO)request.getAttribute("customerPersonalVO");
  PaymentPlanVO paymentPlanVO = (PaymentPlanVO)request.getAttribute("paymentPlanVO");
  CustomerScoreVO customerScoreVO = (CustomerScoreVO)request.getAttribute("customerScoreVO");

//  FdcgCustomerQueryInfoPO fdcgCustomerQueryInfoPO = HttpUtils.getAttribute("fdcgCustomerQueryInfoPO", FdcgCustomerQueryInfoPO.class, request);

  CustomerPersonalPO loginCustomerInSession = Config.getLoginCustomerInSession(request);

%>
<div data-page="mine-list" class="page">


  <%--<div class="navbar">--%>
    <%--<div class="navbar-inner navbar-ph-bk">--%>
      <%--<div class="center">我的账户</div>--%>
      <%--<div class="right">--%>
      <%--</div>--%>
    <%--</div>--%>
  <%--</div>--%>


  <div class="page-content">

    <div class="header_bar3">
      <div class="header_company"><i></i>我的</div>
    </div>




    <!-- 确认用户 显示资产 开始 -->
    <%
      if (customerPersonalVO.getCustomerCatalogId().equals("1")) {
          %>

    <div class="assets_info clearfix">
      <div class="amount_money clearfix"><div class="amount_money_ct"><p><%=paymentPlanVO.getTotalPaymentMoneyFormatted()%></p><p class="money_text">资产总额(元）</p></div><i></i></div>
      <ul class="assets_info_ct clearfix">
        <li><p><%=paymentPlanVO.getTotalPaymentPrincipalMoneyFormatted()%></p><p>我的本金</p></li>
        <li><p>&nbsp;</p><p>&nbsp;</p></li>
        <li><p><%=paymentPlanVO.getTotalProfitMoneyFormatted()%></p><p>预期收益</p></li>
      </ul>
    </div>

    <%
      }
    %>
    <!-- 确认用户 显示资产 结束 -->



    <!-- 注册用户 开始 -->
    <%
      if (customerPersonalVO.getCustomerCatalogId().equals("0")) {
    %>

    <div class="assets_info clearfix">
      <div class="amount_money clearfix"><div class="amount_money_ct"><p><%=customerPersonalVO.getMobile()%></p><p class="money_text">欢迎注册用户</p></div><i></i></div>
      <ul class="assets_info_ct clearfix">
        <li><p></p><p></p></li>
        <li><p>&nbsp;</p><p>&nbsp;</p></li>
        <li><p></p><p></p></li>
      </ul>
    </div>

    <%
      }
    %>
    <!-- 注册用户 结束 -->



    <!-- 游客 开始 -->
    <%
      if (loginCustomerInSession == null) {
    %>

    <div class="assets_info clearfix">
      <div class="amount_money clearfix"><div class="amount_money_ct"><p>欢迎您：游客</p><p class="money_text"></p></div><i></i></div>
      <ul class="assets_info_ct clearfix">
        <li><p></p><p></p></li>
        <li><p>&nbsp;</p><p>&nbsp;</p></li>
        <li><p></p><p></p></li>
      </ul>
    </div>

    <%
      }
    %>
    <!-- 游客 结束 -->



    <div class="my_list clearfix">
      <ul>

        <!-- 确认用户 开始 -->
        <%
          if (customerPersonalVO.getCustomerCatalogId().equals("1")) {
              %>
        <li>
          <a href="<%=Config.getWebMonopoly()%>/mine/order_catalog.jsp?r=<%=NumberUtils.randomNumbers(5)%>" class="item-link">
            <div class="my_list_img1"><i></i>我的投资<div class="lf_href">查看订单<b></b></div></div>
          </a>
        </li>
        <li>
          <a href="<%=Config.getWebMonopoly()%>/mine/mine_info.jsp?r=<%=NumberUtils.randomNumbers(5)%>" class="item-link">
            <div class="my_list_img2"><i></i>我的账户 <div class="lf_href"><%=customerPersonalVO.getName()%><b></b></div></div>
          </a>
        </li>
        <li>
          <a href="<%=Config.getWebMonopoly()%>/loadPage_dehecircle_card_list?r=<%=NumberUtils.randomNumbers(5)%>" class="item-link">
            <div class="my_list_img4"><i></i>银行卡<div class="lf_href"><b></b></div></div>
          </a>
        </li>
        <%
          }
        %>
        <!-- 确认用户 结束 -->

        <li>
          <%--<a href="<%=Config.getWebDehecircle()%>/loadPage_dehecircle_mobile_list?r=<%=NumberUtils.randomNumbers(5)%>" class="item-link">--%>
            <div class="my_list_img3"><i></i>手机号 <div class="lf_href"><%=customerPersonalVO.getMobile()%><b></b></div></div>
          <%--</a>--%>
        </li>


        <%--<li>--%>
          <%--<a href="<%=Config.getWebDehecircle()%>/loadPage_PH_password_list?r=<%=NumberUtils.randomNumbers(5)%>">--%>
            <%--<div class="my_list_img5"><i></i>我的密码<div class="lf_href"><b></b></div></div>--%>
          <%--</a>--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--<div class="my_list_img6"><i></i>我的积分</div>--%>
        <%--</li>--%>

      </ul>
      <p class="bcg_cr"></p>



      <!-- 资讯设置 开始 -->
      <ul>
        <li>
          <div class="my_list_img6"><i></i>要闻资讯<div class="lf_href">已开启<b></b></div></div>
        </li>
        <li><div class="my_list_img6"><i></i>基金资讯<div class="lf_href">已开启<b></b></div></div></li>
        <li><div class="my_list_img6"><i></i>股票资讯<div class="lf_href">已开启<b></b></div></div></li>
        <li><div class="my_list_img6"><i></i>投资理财<div class="lf_href">已开启<b></b></div></div></li>
      </ul>
      <!-- 资讯设置 结束 -->

      <p class="bcg_cr"></p>


      <ul>
        <li>
          <a href="<%=Config.getWebMonopoly()%>/mine/system_list.jsp?r=<%=NumberUtils.randomNumbers(5)%>" class="item-link">
          <div class="my_list_img5"><i></i>系统设置<div class="lf_href"><b></b></div></div>
          </a>
        </li>



        <!-- 游客 显示登录 开始 -->
        <%
          if (loginCustomerInSession == null) {
              %>
        <li><div class="my_list_img6 btn-login"><i></i>登录享有更多功能<div class="lf_href"><b></b></div></div></li>
        <%
          }
          else {
              %>
        <li><div class="my_list_img6 btn-logout"><i></i>退出系统<div class="lf_href"><b></b></div></div></li>
        <%
          }
        %>
        <!-- 游客 显示登录 开始 -->

      </ul>

    </div>


    <div class="content-block">
      &nbsp;
    </div>


  </div>

</div>