<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.NumberUtils" %>
<%@ page import="com.youngbook.entity.vo.customer.CustomerPersonalVO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%
    List<CustomerPersonalVO> listCustomerPersonalVO = (List<CustomerPersonalVO>)request.getAttribute("listCustomerPersonalVO");
%>
<div data-page="customer-list" class="page">

    <div class="navbar">
        <div class="navbar-inner navbar-ph">
            <div class="center">我的客户</div>
            <div class="right">
                <a href="<%=Config.getWebDeheSales()%>/customer/customer_save.jsp" class="link"><i class="icon material-icons color-white">add</i></a>
            </div>
        </div>
    </div>

    <div class="page-content hide-bars-on-scroll">

        <div class="list-block">
            <ul>
                <%
                    for (int i = 0; listCustomerPersonalVO != null && i < listCustomerPersonalVO.size(); i++) {

                        CustomerPersonalVO customerPersonalVO = listCustomerPersonalVO.get(i);
                %>
                <li><a href="<%=Config.getWebRoot()%>/dehesales/loadPage_deheSales_customer_detail?customerId=<%=customerPersonalVO.getId()%>&_r=<%=NumberUtils.randomNumbers(5)%>" class="link color-black">
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">face</i></div>
                        <div class="item-inner">
                            <div class="item-title"><%=customerPersonalVO.getName()%></div>
                            <div class="item-after"><%=customerPersonalVO.getMobile()%></div>
                        </div>
                    </div>
                </a></li>
                <%
                    }
                %>
            </ul>
        </div>

        <div class="content-block">
            &nbsp;
        </div>
        <div class="content-block">
            &nbsp;
        </div>

    </div>
</div>