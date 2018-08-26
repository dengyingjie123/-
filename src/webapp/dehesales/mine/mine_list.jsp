<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.NumberUtils" %>
<%@ page import="com.youngbook.action.customer.CustomerPersonalAction" %>
<%@ page import="com.youngbook.entity.vo.customer.CustomerPersonalVO" %>
<%@ page import="com.youngbook.entity.vo.Sale.PaymentPlanVO" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerScorePO" %>
<%@ page import="com.youngbook.entity.po.fdcg.FdcgCustomerQueryInfoPO" %>
<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.youngbook.common.utils.MoneyUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%
  CustomerPersonalVO customerPersonalVO = (CustomerPersonalVO)request.getAttribute("customerPersonalVO");
  PaymentPlanVO paymentPlanVO = (PaymentPlanVO)request.getAttribute("paymentPlanVO");
  CustomerScorePO customerScorePO = (CustomerScorePO)request.getAttribute("customerScorePO");

//  FdcgCustomerQueryInfoPO fdcgCustomerQueryInfoPO = HttpUtils.getAttribute("fdcgCustomerQueryInfoPO", FdcgCustomerQueryInfoPO.class, request);

%>
<div data-page="mine-list" class="page">


  <div class="navbar">
    <div class="navbar-inner navbar-ph">
      <div class="center">我的账户</div>
      <div class="right">
      </div>
    </div>
  </div>

  <div class="page-content">

    <div class="row mine-top-bg">
      <div class="col-100">
        <div class="row">
          <div class="col-100 font-size-50"><%=paymentPlanVO.getTotalPaymentMoneyFormatted()%></div>
        </div>
        <div class="row">
          <div class="col-100">投资总资产</div>
        </div>
        <div class="row">
          <div class="col-50 font-size-20">已投资本金</div>
          <div class="col-50 font-size-20"><%=paymentPlanVO.getTotalPaymentPrincipalMoneyFormatted()%></div>

        </div>
        <div class="row">
          <div class="col-50 font-size-20">预期投资收益</div>
          <div class="col-50 font-size-20"><%=paymentPlanVO.getTotalProfitMoneyFormatted()%></div>
        </div>
        <%--<div class="row">--%>
          <%--<div class="col-50 font-size-20">存管账户余额</div>--%>
          <%--<div class="col-50 font-size-20"><%=MoneyUtils.format2String(fdcgCustomerQueryInfoPO.getBalance())%></div>--%>
        <%--</div>--%>
      </div>
    </div>


    <div class="content-block-title">基本信息</div>
    <div class="list-block">
      <ul>
        <li><a href="<%=Config.getWebPH()%>/mine/order_catalog.jsp?r=<%=NumberUtils.randomNumbers(5)%>" class="item-link">
          <div class="item-content">
            <div class="item-media"><i class="icon material-icons">assessment</i></div>
            <div class="item-inner">
              <div class="item-title">我的投资</div>
              <div class="item-after">查看订单</div>
            </div>
          </div></a>
        </li>
        <li><a href="<%=Config.getWebPH()%>/loadPage_PH_account_list?r=<%=NumberUtils.randomNumbers(5)%>" class="item-link">
          <div class="item-content">
            <div class="item-media"><i class="icon material-icons">assessment</i></div>
            <div class="item-inner">
              <div class="item-title">我的账户</div>
              <div class="item-after">查看余额</div>
            </div>
          </div></a>
        </li>
        <li><a href="<%=Config.getWebPH()%>/mine/mine_info.jsp?r=<%=NumberUtils.randomNumbers(5)%>" class="item-link">
          <div class="item-content">
            <div class="item-media"><i class="icon material-icons">face</i></div>
            <div class="item-inner">
              <div class="item-title">我的姓名</div>
              <div class="item-after"><%=customerPersonalVO.getName()%></div>
            </div>
          </div></a>
        </li>

        <%--<a href="<%=Config.getWebRootCircle()%>/mine/mine_change_mobile.jsp" class="link-black">--%>
        <li><a href="<%=Config.getWebPH()%>/loadPage_PH_mobile_list?r=<%=NumberUtils.randomNumbers(5)%>" class="item-link">
          <div class="item-content">
            <div class="item-media"><i class="icon material-icons">phone</i></div>
            <div class="item-inner">
              <div class="item-title">手机号</div>
              <div class="item-after"><%=customerPersonalVO.getMobile()%></div>
            </div>
          </div></a>
        </li>
        <li><a href="<%=Config.getWebPH()%>/loadPage_PH_card_list?r=<%=NumberUtils.randomNumbers(5)%>" class="item-link">
          <div class="item-content">
            <div class="item-media"><i class="icon material-icons">payment</i></div>
            <div class="item-inner">
              <div class="item-title">银行卡</div>
              <div class="item-after"></div>
            </div>
          </div></a>
        </li>
        <li><a href="<%=Config.getWebPH()%>/loadPage_PH_password_list?r=<%=NumberUtils.randomNumbers(5)%>" class="item-link">
          <div class="item-content">
            <div class="item-media"><i class="icon material-icons">lock_outline</i></div>
            <div class="item-inner">
              <div class="item-title">我的密码</div>
              <div class="item-after"></div>
            </div>
          </div></a>
        </li>
        <li><a href="<%=Config.getWebPH()%>/mine/score_list.jsp?customerId=<%=customerPersonalVO.getIdCard()%>&r=<%=NumberUtils.randomNumbers(5)%>" class="item-link">
          <div class="item-content">
            <div class="item-media"><i class="icon material-icons">shopping_cart</i></div>
            <div class="item-inner">
              <div class="item-title">我的积分</div>
              <div class="item-after"><%=customerScorePO.getScore()%></div>
            </div>
          </div></a>
        </li>
      </ul>
    </div>

    <div class="content-block-title">偏好设置</div>
    <div class="list-block">
      <ul>
        <li>
          <div class="item-content">
            <div class="item-media"><i class="icon material-icons">settings</i></div>
            <div class="item-inner">
              <div class="item-title">系统设置</div>
              <div class="item-after"><span class="badge">0</span></div>
            </div>
          </div>
        </li>
        <li><a href="#" class="item-link btn-logout">
          <div class="item-content">
            <div class="item-media"><i class="icon material-icons">power_settings_new</i></div>
            <div class="item-inner">
              <div class="item-title">退出系统</div>
              <div class="item-after"></div>
            </div>
          </div></a>
        </li>
      </ul>
    </div>


    <div class="content-block">
      &nbsp;
    </div>

  </div>

</div>