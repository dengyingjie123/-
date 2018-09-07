<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.NumberUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<div class="pages">

  <div data-page="mine-change-description" class="page">

    <div class="navbar">
      <div class="navbar-inner">
        <div class="left"><a id="btn-customer-back" href="#" class="back link"> <i class="icon icon-back"></i><span>&nbsp;</span></a></div>
        <div class="center sliding">资料修改</div>
      </div>
    </div>

    <div class="page-content">

      <%--<div class="content-block-title">资料验证</div>--%>
      <form class="list-block inputs-list" id="form-mine-change-description">
        <div class="list-block inset inputs-list">
          <ul>

            <li>
              <div class="item-content">
                <div class="item-inner">
                  <div class="item-title label">我的介绍</div>
                  <div class="item-input">
                    <textarea id="mine-change-description-description" name="user.description" class="resizable">{{returnValue['user.description']}}</textarea>
                  </div>
                </div>
              </div>
            </li>

          </ul>
        </div>
        <input type="hidden" name="user.id" value="{{returnValue['user.id']}}" />
      </form>
      <div class="content-block">
        <a id="btn-mine-change-description" href="#" class="button button-big button-fill button-raised color-blue">保存</a>
      </div>
    </div>
  </div>
</div>