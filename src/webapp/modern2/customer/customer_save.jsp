<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<div class="pages">

  <div data-page="customer-save" class="page">

    <div class="navbar">
      <div class="navbar-inner">
        <div class="left"><a id="btn-customer-back" href="#" class="back link"> <i class="icon icon-back"></i><span>&nbsp;</span></a></div>
        <div class="center sliding">客户编辑</div>
      </div>
    </div>

    <div class="page-content">

      <%--<div class="content-block-title">基本资料</div>--%>
      <form class="list-block inset inputs-list" id="customer-save-form">
        <ul>
          <li>
            <div class="item-content">
              <div class="item-media"><i class="icon material-icons">person_outline</i></div>
              <div class="item-inner">
                <div class="item-title label">姓名</div>
                <div class="item-input">
                  <input type="text" name="name" placeholder="姓名"/>
                </div>
              </div>
            </div>
          </li>
          <li>
            <div class="item-content">
              <div class="item-media"><i class="icon material-icons">call</i></div>
              <div class="item-inner">
                <div class="item-title label">手机</div>
                <div class="item-input">
                  <input type="tel" name="mobile" placeholder="手机"/>
                </div>
              </div>
            </div>
          </li>
          <li>
            <div class="item-content">
              <div class="item-media"><i class="icon material-icons">people_outline</i></div>
              <div class="item-inner">
                <div class="item-title label">性别</div>
                <div class="item-input">
                  <select name="sex">
                    <option value="1">男</option>
                    <option value="0">女</option>
                  </select>
                </div>
              </div>
            </div>
          </li>
          <li>
            <div class="item-content">
              <div class="item-media"><i class="icon material-icons">today</i></div>
              <div class="item-inner">
                <div class="item-title label">生日</div>
                <div class="item-input">
                  <input name="birthday" type="date" placeholder="Birth day" value="2014-04-30"/>
                </div>
              </div>
            </div>
          </li>
          <li class="align-top">
            <div class="item-content">
              <div class="item-media"><i class="icon material-icons">chat_bubble_outline</i></div>
              <div class="item-inner">
                <div class="item-title label">备注</div>
                <div class="item-input">
                  <textarea name="remark" class="resizable"></textarea>
                </div>
              </div>
            </div>
          </li>
        </ul>
        <input type="hidden" name="id" />
      </form>
        <div class="content-block">
          <a id="btn-customer-save" href="#" class="button button-big button-fill button-raised color-blue">保存</a>
        </div>
        <div class="content-block">
          <a id="btn-customer-delete" href="#" class="button button-big button-fill button-raised color-red">删除</a>
        </div>
        <div class="content-block">
          &nbsp;
        </div>
        <div class="content-block">
          &nbsp;
        </div>
        <div class="content-block">
          &nbsp;
        </div>
    </div>
  </div>
</div>