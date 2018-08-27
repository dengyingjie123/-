<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<div class="pages">

  <div data-page="circle-tag-list" class="page">

    <div class="navbar">
      <div class="navbar-inner">
        <div class="left"><a id="btn-circle-back" href="#" class="back link"> <i class="icon icon-back"></i><span>&nbsp;</span></a></div>
        <div class="center sliding">选择标签</div>
        <div class="right">
          <a href="#" class="link circle-tag-add"><i class="icon material-icons">add</i></a>
          <%--<a href="#" class="link"><i class="icon material-icons">more_vert</i></a>--%>
        </div>
      </div>
    </div>

    <form data-search-list=".search-here" data-search-in=".item-title, .item-subtitle" class="searchbar searchbar-init">
      <div class="searchbar-input">
        <input type="search" placeholder="输入搜索内容"/><a href="#" class="searchbar-clear"></a>
      </div>
    </form>
    <div class="searchbar-overlay"></div>
    <div class="page-content">

      <div class="list-block">
        <ul>
          <li>
            <label class="label-checkbox item-content">
              <input type="checkbox" name="ks-checkbox" value="Books" checked="checked"/>
              <div class="item-media"><i class="icon icon-form-checkbox"></i></div>
              <div class="item-inner">
                <div class="item-title">Books</div>
              </div>
            </label>
          </li>
          <li>
            <label class="label-checkbox item-content">
              <input type="checkbox" name="ks-checkbox" value="Movies"/>
              <div class="item-media"><i class="icon icon-form-checkbox"></i></div>
              <div class="item-inner">
                <div class="item-title">Movies</div>
              </div>
            </label>
          </li>
        </ul>
      </div>



      <div class="content-block">
        <a id="btn-circle-save" href="#" class="button button-big button-fill button-raised color-blue">保存</a>
      </div>
    </div>
  </div>
</div>