<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.KVObjects" %>
<%@ page import="com.youngbook.common.KVObject" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.mchange.v1.util.StringTokenizerUtils" %>
<%@ page import="com.youngbook.common.utils.StringUtils" %>
<%@ page import="com.youngbook.common.utils.NumberUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    CustomerPersonalPO loginCustomer =  Config.getLoginCustomerInSession(request);
%>
<div data-page="order-catalog" class="page">
    <div class="navbar">
        <div class="navbar-inner navbar-ph">
            <div class="left"><a href="#" class="back link"> <i class="icon-white icon-back"></i><span></span></a></div>
            <div class="center sliding">我的投资</div>
        </div>
    </div>

    <div class="page-content">


        <div class="list-block">
            <ul>
                <li><a href="<%=Config.getWebPH()%>/mine/order_list.jsp?orderStatus=0&projectTypeId=2,4&customerId=<%=loginCustomer.getId()%>&r=<%=NumberUtils.randomNumbers(5)%>" class="item-link">
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">assignment</i></div>
                        <div class="item-inner">
                            <div class="item-title">未支付订单</div>
                            <div class="item-after"></div>
                        </div>
                    </div></a>
                </li>
                <li><a href="<%=Config.getWebPH()%>/mine/order_list.jsp?orderStatus=1&projectTypeId=2,4&customerId=<%=loginCustomer.getId()%>&r=<%=NumberUtils.randomNumbers(5)%>" class="item-link">
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">assignment_turned_in</i></div>
                        <div class="item-inner">
                            <div class="item-title">已支付订单</div>
                            <div class="item-after"></div>
                        </div>
                    </div></a>
                </li>
                <li><a href="<%=Config.getWebPH()%>/mine/order_list.jsp?orderStatus=2&projectTypeId=2,4&customerId=<%=loginCustomer.getId()%>&r=<%=NumberUtils.randomNumbers(5)%>" class="item-link">
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">assignment_returned</i></div>
                        <div class="item-inner">
                            <div class="item-title">已兑付订单</div>
                            <div class="item-after"></div>
                        </div>
                    </div></a>
                </li>
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