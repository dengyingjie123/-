<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<div data-page="payment-list" class="page">

  <div class="page-content">

    <div class="header_bar2">
      <a href="#" class="back link"><div class="header_back"><span></span></div></a>
      <div class="header_new_title">兑付计划</div>
    </div>

    <%--<div class="content-block-title">XXX产品名称</div>--%>
    <div class="list-block">
      <ul>
        {{#each this.returnValue}}
        <li>
          <div class="item-content">
            <div class="item-media"><i class="icon material-icons">date_range</i></div>
            <div class="item-inner">
              <div class="item-title">{{paymentTime}}</div>
              <%--<div class="item-after">{{js "this.totalPaymentPrincipalMoney + this.totalProfitMoney"}}&nbsp;元</div>--%>
              <div class="item-after">{{totalPaymentMoneyFormatted}}&nbsp;元</div>
            </div>
          </div>
        </li>
        {{/each}}
      </ul>
    </div>


    <div class="content-block">
      &nbsp;
    </div>

  </div>

</div>