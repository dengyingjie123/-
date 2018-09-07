<%@ page import="com.youngbook.common.config.Config" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<div data-page="order-pay-code" class="page">


  <div class="navbar">
    <div class="navbar-inner navbar-ph">
      <div class="left"><a href="#" class="back link"> <i class="icon-white icon-back"></i><span></span></a></div>
      <div class="center">支付码支付说明</div>
      <div class="right">
        <a href="customer/customer_save.jsp" class="link"><i class="icon material-icons">add</i></a>
        <%--<a href="#" class="link"><i class="icon material-icons">more_vert</i></a>--%>
      </div>
    </div>
  </div>

  <div class="page-content">

    <%--<div class="content-block-title">支付码为【{{payCode}}】</div>--%>
    <%--<div class="list-block">--%>
      <%--<ul>--%>
        <%--<li>--%>
          <%--<div class="item-content">--%>
            <%--<div class="item-media"><i class="icon material-icons">local_atm</i></div>--%>
            <%--<div class="item-inner">--%>
              <%--<div class="item-title">网址</div>--%>
              <%--<div class="item-after">http://pay.dianjinpai.com</div>--%>
            <%--</div>--%>
          <%--</div>--%>
        <%--</li>--%>
      <%--</ul>--%>
    <%--</div>--%>


    <div class="content-block">
      <span style="font-size: 20px; color:darkred;">支付码为【{{payCode}}】</span><br>
      请用PC电脑，访问网址 <br>
      <span style="font-size: 20px; color:darkblue;">http://pay.dianjinpai.com</span><br>
      便可以通过U盾网银方式完成支付。<br>
      注意：此支付码仅在15分钟内有效。
    </div>

  </div>

</div>