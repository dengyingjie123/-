<%@ page import="com.youngbook.common.config.Config" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<div class="pages">

  <div data-page="mine-change-mobile" class="page">

    <div class="navbar">
      <div class="navbar-inner">
        <div class="left"><a id="btn-customer-back" href="#" class="back link"> <i class="icon icon-back"></i><span>&nbsp;</span></a></div>
        <div class="center sliding">更换手机</div>
      </div>
    </div>

    <div class="page-content">

      <div class="content-block-title">资料验证</div>
      <form class="list-block inputs-list" id="customer-save-form">
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
                    <input id="txt-login-mobile-code" name="mobile" type="text" placeholder="请输入短信验证码" value=""/>
                  </div>
                </div>
                <div class="item-inner">
                  <div class="item-input">
                    <a href="#" class="button">获取验证码</a>
                  </div>
                </div>
              </div>
            </li>
          </ul>
        </div>
        <input type="hidden" name="id" />
      </form>
      <div class="content-block">
        <a id="btn-customer-save" href="#" class="button button-big button-fill button-raised color-teal">保存</a>
      </div>
      <div>
        <div class="row">
          <div class="col-30">&nbsp;</div>
          <div class="col-30"><a href="<%=Config.getWebRootCircle()%>/mine/mine_change_password.jsp">忘记密码？</a></div>
        </div>
      </div>
    </div>
  </div>
</div>