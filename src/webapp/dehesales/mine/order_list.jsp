<%@ page import="com.youngbook.common.config.Config" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<div data-page="order-list" class="page">


  <div class="navbar">
    <div class="navbar-inner navbar-ph">
      <div class="left"><a href="#" class="back link"> <i class="icon-white icon-back"></i><span></span></a></div>
      <div class="center">{{title}}</div>
      <div class="right">
        <a href="customer/customer_save.jsp" class="link"><i class="icon material-icons">add</i></a>
        <%--<a href="#" class="link"><i class="icon material-icons">more_vert</i></a>--%>
      </div>
    </div>
  </div>

  <div class="page-content">

    {{#each this.data.returnValue}}
    <div class="content-block-title">{{productionName}}</div>
    <div class="list-block">
      <ul>
        <li>
          <div class="item-content">
            <div class="item-media"><i class="icon material-icons">local_atm</i></div>
            <div class="item-inner">
              <div class="item-title">投资金额</div>
              <div class="item-after">{{money}}&nbsp;元</div>
            </div>
          </div>
        </li>
        <li>
          <div class="item-content">
            <div class="item-media"><i class="icon material-icons">payment</i></div>
            <div class="item-inner">
              <div class="item-title">银行卡</div>
              <div class="item-after">{{bankNumber}}&nbsp;&nbsp;<i class="icon material-icons">payment</i></div>
            </div>
          </div>
        </li>
        <li>
          <div class="item-content">
            <div class="item-media"><i class="icon material-icons">assessment</i></div>
            <div class="item-inner">
              <div class="item-title">预期收益</div>
              <div class="item-after">{{productionCompositionName}}</div>
            </div>
          </div>
        </li>
        <%--<a href="<%=Config.getWebRootCircle()%>/mine/mine_change_mobile.jsp" class="link-black">--%>
        <li>
          <div class="item-content">
            <div class="item-media"><i class="icon material-icons">date_range</i></div>
            <div class="item-inner">
              <div class="item-title">投资时间</div>
              <div class="item-after">{{payTime}}</div>
            </div>
          </div>
        </li>
        {{#js_compare "this.status != 0"}}
        <li><a href="<%=Config.getWebPH()%>/mine/payment_list.jsp?orderId={{id}}" class="item-link">
          <div class="item-content">
            <div class="item-media"><i class="icon material-icons">view_module</i></div>
            <div class="item-inner">
              <div class="item-title">兑付计划</div>
              <div class="item-after"></div>
            </div>
          </div></a>
        </li>
        <li><a href="<%=Config.getWebPH()%>/phGetPagePaperInvestment?orderId={{id}}" class="item-link">
          <div class="item-content">
            <div class="item-media"><i class="icon material-icons">view_module</i></div>
            <div class="item-inner">
              <div class="item-title">认购协议</div>
              <div class="item-after"></div>
            </div>
          </div></a>
        </li>
        {{else}}
        <li><a href="<%=Config.getWebPH()%>/mine/order_pay_code.jsp?orderId={{id}}" class="item-link">
          <div class="item-content">
            <div class="item-media"><i class="icon material-icons">view_module</i></div>
            <div class="item-inner">
              <div class="item-title">支付码 {{payCode}}</div>
              <div class="item-after"></div>
            </div>
          </div></a>
        </li>
        {{/js_compare}}
      </ul>
    </div>
    {{/each}}


    <div class="content-block">
      &nbsp;
    </div>

  </div>

</div>