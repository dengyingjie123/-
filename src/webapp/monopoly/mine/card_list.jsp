<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.NumberUtils" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerAccountPO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.youngbook.entity.po.fdcg.FdcgCustomerAccountPO" %>
<%@ page import="com.youngbook.common.fdcg.FdcgCommon" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<CustomerAccountPO> customerAccounts = (List<CustomerAccountPO>)request.getAttribute("customerAccounts");

    FdcgCustomerAccountPO fdcgCustomerAccountPO = (FdcgCustomerAccountPO) request.getAttribute("fdcgCustomerAccountPO");
%>
<div data-page="card-list" class="page">

    <div class="page-content">

        <div class="header_bar2">
            <a href="#" class="back link"><div class="header_back"><span></span></div></a>
            <div class="header_new_title">银行卡</div>
        </div>

        <%--<div class="content-block-title">投资信息</div>--%>
        <div class="list-block">
            <ul>
                <%
                    for (int i = 0; customerAccounts != null && i < customerAccounts.size(); i++) {
                        CustomerAccountPO customerAccountPO = customerAccounts.get(i);
                %>
                <li class="item-content">
                    <div class="item-media"><i class="icon material-icons">payment</i></div>
                    <div class="item-inner">
                        <div class="item-title"><%=customerAccountPO.getBank()%></div>
                        <div class="item-after"><%=customerAccountPO.getNumber()%></div>
                    </div>
                </li>
                <%
                    }
                %>

            </ul>
        </div>

            <form id="form-customer-account-bind" name="form1" method="post" action="<%=FdcgCommon.getApiUrl("thirdparty.fdcg.api.user.account.bind")%>">
                <input type="hidden" id="reqDataBind" name="reqData" value=""/>
            </form>

            <form id="form-customer-account-unbind" name="form1" method="post" action="<%=FdcgCommon.getApiUrl("thirdparty.fdcg.api.user.account.unbind")%>">
                <input type="hidden" id="reqDataUnbind" name="reqData" value=""/>
            </form>

        <div class="content-block">
            &nbsp;
        </div>

        <div class="content-block">
            &nbsp;
        </div>

    </div>
</div>