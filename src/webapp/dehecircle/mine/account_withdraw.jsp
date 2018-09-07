<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.KVObjects" %>
<%@ page import="com.youngbook.common.KVObject" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.mchange.v1.util.StringTokenizerUtils" %>
<%@ page import="com.youngbook.common.utils.StringUtils" %>
<%@ page import="com.youngbook.common.fdcg.FdcgCommon" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  CustomerPersonalPO loginCustomer = Config.getLoginCustomerInSession(request);
  if (loginCustomer == null) {
    out.println("<script>window.location='"+Config.getWebPH()+"/login.jsp'</script>");
    return;
  }
%>
<div data-page="mine-account-withdraw" class="page">
  <div class="navbar">
    <div class="navbar-inner navbar-ph">
      <div class="left"><a href="#" class="back link"> <i class="icon-white icon-back"></i><span></span></a></div>
      <div class="center sliding">提现申请</div>
    </div>
  </div>

  <div class="page-content">

    <div class="content-block">

        <div class="list-block">
          <ul>
            <li>
              <div class="item-content">
                <div class="item-media"><i class="icon material-icons">attach_money</i></div>
                <div class="item-inner">
                  <div class="item-input">
                    <input id="amount" name="amount" type="text" placeholder="提现金额" value=""/>
                  </div>
                </div>
              </div>
            </li>
          </ul>
        </div>

    </div>

    <form id="form-customer-account-withdraw" name="form1" method="post" action="<%=FdcgCommon.getApiUrl("thirdparty.fdcg.api.user.app.withdraw")%>">
      <input type="hidden" id="reqData" name="reqData" value=""/>
    </form>


    <div class="content-block">
      <p class="buttons-row">
        <a href="#" class="button button-raised button-fill btn-mine-account-withdraw">提现</a>
      </p>
    </div>


    <div class="content-block">
      &nbsp;
    </div>


    <div class="content-block">
      &nbsp;
    </div>

  </div>
</div>