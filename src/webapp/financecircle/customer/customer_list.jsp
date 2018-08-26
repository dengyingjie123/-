<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.youngbook.entity.vo.customer.CustomerPersonalVO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%
  List<CustomerPersonalVO> listCustomerPersonalVO = (List<CustomerPersonalVO>)request.getAttribute("listCustomerPersonalVO");
%>
<div data-page="customer-list" class="page">


  <div class="navbar">
    <div class="navbar-inner">
      <div class="center">客户</div>
      <div class="right">
        <a href="customer/customer_save.jsp" class="link"><i class="icon material-icons">add</i></a>
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
    <div class="list-block searchbar-not-found">
      <ul>
        <li class="item-content">
          <div class="item-inner">
            <div class="item-title">无法找到符合条件的记录</div>
          </div>
        </li>
      </ul>
    </div>

    <div class="card-content">
      <div class="list-block media-list search-here searchbar-found">
        <ul>
          <%
            for (int i = 0; listCustomerPersonalVO != null && i <listCustomerPersonalVO.size(); i++) {
              CustomerPersonalVO customerPersonalVO = listCustomerPersonalVO.get(i);

          %>
          <a href="customer/customer_save.jsp?customerId=<%=customerPersonalVO.getId()%>">
          <li class="item-content" style="color:#333333">
            <div class="item-media"><img src="include/img/customer_man.png" width="44"/></div>
            <div class="item-inner">
              <div class="item-title-row">
                <div class="item-title"><%=customerPersonalVO.getName()%></div>
              </div>
              <div class="item-subtitle"><%=customerPersonalVO.getMobile()%></div>
            </div>
          </li></a>
          <%
            }
          %>
        </ul>
      </div>
    </div>


    <div class="content-block">
      &nbsp;
    </div>

  </div>

</div>