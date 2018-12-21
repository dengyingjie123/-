<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.NumberUtils" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerAccountPO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.youngbook.entity.po.fdcg.FdcgCustomerAccountPO" %>
<%@ page import="com.youngbook.common.fdcg.FdcgCommon" %>
<%@ page import="com.youngbook.entity.vo.customer.CustomerPersonalVO" %>
<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    CustomerPersonalVO customerPersonalVO = HttpUtils.getAttribute("customerPersonalVO", CustomerPersonalVO.class, request);
%>
<div data-page="mine-account-list" class="page">
    <div class="navbar">
        <div class="navbar-inner navbar-ph">
            <div class="left"><a href="#" class="back link"> <i class="icon-white icon-back"></i><span></span></a></div>
            <div class="center sliding">我的存管账户</div>
        </div>
    </div>
    <div class="page-content">

        <%--<div class="content-block-title">投资信息</div>--%>
        <div class="list-block">
            <ul>
                <li><a href="<%=Config.getWebPH()%>/mine/account_recharge.jsp?r=<%=NumberUtils.randomNumbers(5)%>" class="item-link item-content">
                    <div class="item-media"><i class="icon material-icons">attach_money</i></div>
                    <div class="item-inner">
                        <div class="item-title">我要充值</div>
                    </div></a>
                </li>
                <li><a href="<%=Config.getWebPH()%>/mine/account_withdraw.jsp?r=<%=NumberUtils.randomNumbers(5)%>" class="item-link item-content">
                    <div class="item-media"><i class="icon material-icons">attach_money</i></div>
                    <div class="item-inner">
                        <div class="item-title">我要提现</div>
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