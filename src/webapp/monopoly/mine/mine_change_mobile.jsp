<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%
  CustomerPersonalPO loginCustomer =  Config.getLoginCustomerInSession(request);
%>
<div class="pages">

  <div data-page="mine-change-mobile" class="page">

    <div class="page-content">

      <div class="header_bar2">
        <a href="#" class="back link"><div class="header_back"><span></span></div></a>
        <div class="header_new_title">更换手机</div>
      </div>
      <div class="content-block-title">资料验证</div>
      <form class="list-block inputs-list" id="form-mine-change-mobile">
        <div class="list-block inputs-list">
          <ul>

            <li>
              <div class="item-content">
                <div class="item-media"><i class="icon material-icons">fingerprint</i></div>
                <div class="item-inner">
                  <div class="item-input">
                    <input id="txt-login-password" type="password" name="password" placeholder="请输入密码" value=""/>
                  </div>
                </div>
              </div>
            </li>

            <li>
              <div class="item-content">
                <div class="item-media"><i class="icon material-icons">phone_iphone</i></div>
                <div class="item-inner">
                  <div class="item-input">
                    <input id="txt-login-mobile" name="mobile" type="text" placeholder="请输入新手机号" value=""/>
                  </div>
                </div>
              </div>
            </li>
            <li>
              <div class="item-content">
                <div class="item-media"><i class="icon material-icons">sms</i></div>
                <div class="item-inner">
                  <div class="item-input">
                    <input id="txt-login-mobile-code" name="mobileCode" type="text" placeholder="请输入短信验证码" value=""/>
                  </div>
                </div>
                <div class="item-inner">
                  <div class="item-input">
                    <a id="btn-change-mobile-code" href="#" class="button">获取验证码</a>
                  </div>
                </div>
              </div>
            </li>
          </ul>
        </div>
        <input type="hidden" name="_s" value="4" />
        <input name="checkCode" type="hidden" />
        <input name="customerId" type="hidden" value="<%=loginCustomer.getId()%>" />
        <input type="hidden" name="id" />
      </form>
      <div class="content-block">
        <a id="btn-customer-mobile-change" href="#" class="button button-raised button-fill">保存</a>
      </div>
      <div>
        <div class="row">
          <div class="col-30">&nbsp;</div>
          <div class="col-30"><a href="<%=Config.getWebDehecircle()%>/mine/mine_change_password.jsp">忘记密码？</a></div>
        </div>
      </div>
    </div>
  </div>
</div>