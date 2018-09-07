<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<div class="pages">

  <div data-page="circle-save" class="page">

    <div class="navbar">
      <div class="navbar-inner">
        <div class="left"><a id="btn-circle-back" href="#" class="back link"> <i class="icon icon-back"></i><span>&nbsp;</span></a></div>
        <div class="center sliding">圈子文章</div>
      </div>
    </div>

    <div class="page-content">
      <%--<div class="content-block-title">基本资料</div>--%>
      <form class="list-block inset inputs-list" id="circle-save-form">
        <ul>
          <li>
            <div class="item-content">
              <div class="item-inner">
                <div class="item-title label">您的想法</div>
                <div class="item-input">
                  <textarea id="txt-circle-save-content" name="content" class="resizable"></textarea>
                </div>
              </div>
            </div>
          </li>
          <%--<li>--%>
            <%--<div class="item-content">--%>
              <%--<div class="row">--%>
                <%--<div class="col-auto"><img src="include/img/cms005.png" height="80px"/></div>--%>
                <%--<div class="col-auto"><img src="include/img/circle_add.png" height="80px"/></div>--%>
              <%--</div>--%>
            <%--</div>--%>
          <%--</li>--%>
        </ul>
        <input type="hidden" name="id" />
      </form>

        <div class="content-block-title">图片</div>
        <div class="content-block">
          <%--<img src="include/img/cms005.png" height="80px"/>--%>
          <%--<img src="include/img/cms005.png" height="80px"/>--%>
          <%--<img src="include/img/cms005.png" height="80px"/>--%>
          <%--<img src="include/img/cms005.png" height="80px"/>--%>
          <img src="include/img/circle_add.png" height="80px"/>
        </div>



        <div class="content-block-title">文章标签</div>
        <div class="content-block">
          <div class="chip">
            <div class="chip-label">金融</div><a href="#" class="chip-delete"></a>
          </div>
          <div class="chip">
            <div class="chip-media bg-teal">美</div>
            <div class="chip-label">美食</div><a href="#" class="chip-delete"></a>
          </div>
          <a href="<%=Config.getWebRoot()%>/modern2/circle/circle_tag_list.jsp">
            <div class="chip">
              <div class="chip-label">+</div>
            </div>
          </a>
        </div>
      <div class="content-block">
        <a id="btn-circle-save" href="#" class="button button-big button-fill button-raised color-blue">保存</a>
      </div>
    </div>
  </div>
</div>