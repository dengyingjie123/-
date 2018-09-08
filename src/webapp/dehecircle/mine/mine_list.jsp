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
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%
  CustomerPersonalVO customerPersonalVO = (CustomerPersonalVO)request.getAttribute("customerPersonalVO");
  PaymentPlanVO paymentPlanVO = (PaymentPlanVO)request.getAttribute("paymentPlanVO");
  CustomerScoreVO customerScoreVO = (CustomerScoreVO)request.getAttribute("customerScoreVO");

//  FdcgCustomerQueryInfoPO fdcgCustomerQueryInfoPO = HttpUtils.getAttribute("fdcgCustomerQueryInfoPO", FdcgCustomerQueryInfoPO.class, request);

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
    <div class="assets_info clearfix">
      <div class="amount_money clearfix"><div class="amount_money_ct"><p><%=paymentPlanVO.getTotalPaymentMoneyFormatted()%></p><p class="money_text">资产总额(元）</p></div><i></i></div>
      <ul class="assets_info_ct clearfix">
        <li><p><%=paymentPlanVO.getTotalPaymentPrincipalMoneyFormatted()%></p><p>我的本金</p></li>
        <li><p>&nbsp;</p><p>&nbsp;</p></li>
        <li><p><%=paymentPlanVO.getTotalProfitMoneyFormatted()%></p><p>预期收益</p></li>
      </ul>
    </div>
    <div class="my_list clearfix">
      <ul>
        <li>
          <a href="<%=Config.getWebDehecircle()%>/mine/order_catalog.jsp?r=<%=NumberUtils.randomNumbers(5)%>" class="item-link">
            <div class="my_list_img1"><i></i>我的投资<div class="lf_href">查看订单<b></b></div></div>
          </a>
        </li>
        <li>
          <a href="<%=Config.getWebDehecircle()%>/mine/mine_info.jsp?r=<%=NumberUtils.randomNumbers(5)%>" class="item-link">
            <div class="my_list_img2"><i></i>我的账户 <div class="lf_href"><%=customerPersonalVO.getName()%><b></b></div></div>
          </a>
        </li>

        <li>
          <%--<a href="<%=Config.getWebDehecircle()%>/loadPage_dehecircle_mobile_list?r=<%=NumberUtils.randomNumbers(5)%>" class="item-link">--%>
            <div class="my_list_img3"><i></i>手机号 <div class="lf_href"><%=customerPersonalVO.getMobile()%><b></b></div></div>
          <%--</a>--%>
        </li>

        <li>
          <a href="<%=Config.getWebDehecircle()%>/loadPage_dehecircle_card_list?r=<%=NumberUtils.randomNumbers(5)%>" class="item-link">
            <div class="my_list_img4"><i></i>银行卡<div class="lf_href"><b></b></div></div>
          </a>
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

      <ul>
        <li><div class="my_list_img5 btn-systen-config"><i></i>系统设置<div class="lf_href"><b></b></div></div></li>
        <li><div class="my_list_img5 btn-clear-cache"><i></i>清空缓存<div class="lf_href"><b></b></div></div></li>
        <li><div class="my_list_img6 btn-logout"><i></i>退出系统<div class="lf_href"><b></b></div></div></li>

      </ul>

    </div>


    <div class="content-block">
      &nbsp;
    </div>


  </div>

</div>