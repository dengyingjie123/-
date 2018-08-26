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
    <div class="navbar">
        <div class="navbar-inner navbar-ph">
            <div class="left"><a href="#" class="back link"> <i class="icon-white icon-back"></i><span></span></a></div>
            <div class="center sliding">银行卡</div>
            <div class="right">
                <a href="<%=Config.getWebRoot()%>/ph/phAddCard?r=<%=NumberUtils.randomNumbers(5)%>" class="link"><i class="icon material-icons color-white">add</i></a>
            </div>
        </div>
    </div>
    <div class="page-content">

        <%--<div class="content-block-title">投资信息</div>--%>
        <div class="list-block">
            <ul>
                <%
                    for (int i = 0; customerAccounts != null && i < customerAccounts.size(); i++) {
                        CustomerAccountPO customerAccountPO = customerAccounts.get(i);
                %>
                <li><a href="<%=Config.getWebPH()%>/mine/card_save.jsp?id={{id}}&number={{number}}&bank={{bank}}&bankCode={{bankCode}}" data-id="{{id}}" class="item-link item-content">
                    <div class="item-media"><i class="icon material-icons">payment</i></div>
                    <div class="item-inner">
                        <div class="item-title"><%=customerAccountPO.getBank()%></div>
                        <div class="item-after"><%=customerAccountPO.getNumber()%></div>
                    </div></a>
                </li>
                <%
                    }
                %>

                <%
                    if (fdcgCustomerAccountPO != null) {

                %>
                <li><a href="<%=Config.getWebPH()%>/mine/card_save.jsp?id={{id}}&number={{number}}&bank={{bank}}&bankCode={{bankCode}}" data-id="{{id}}" class="item-link item-content">
                    <div class="item-media"><i class="icon material-icons">payment</i></div>
                    <div class="item-inner">
                        <div class="item-title"><%=fdcgCustomerAccountPO.getBank()%>（存管）</div>
                        <div class="item-after"><%=fdcgCustomerAccountPO.getBankAccountNo()%></div>
                    </div></a>
                </li>
                <%
                    }
                %>
            </ul>
        </div>

            <div class="content-block">
                <p class="buttons-row">
                    <%
                        if (fdcgCustomerAccountPO == null) {
                    %>
                    <a href="#" class="button button-raised button-fill btn-fdcg-customer-account-bind">绑定存管银行账号</a>
                    <%
                        }
                        else {
                    %>
                    <a href="#" class="button button-raised button-fill btn-fdcg-customer-account-unbind">存管银行账号已绑定</a>
                    <%
                        }
                    %>

                </p>
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