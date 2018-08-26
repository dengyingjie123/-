<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.NumberUtils" %>
<%@ page import="com.youngbook.entity.vo.customer.CustomerPersonalVO" %>
<%@ page import="com.youngbook.common.fdcg.FdcgCommon" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    CustomerPersonalVO customerPersonalVO = com.youngbook.common.utils.HttpUtils.getAttribute("customerPersonalVO", CustomerPersonalVO.class, request);
%>
<div data-page="mobile-list" class="page">
    <div class="navbar">
        <div class="navbar-inner navbar-ph">
            <div class="left"><a href="#" class="back link"> <i class="icon-white icon-back"></i><span></span></a></div>
            <div class="center sliding">手机号</div>
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
                    if (customerPersonalVO != null) {
                %>
                <li><a href="#" data-id="{{id}}" class="item-link item-content">
                    <div class="item-media"><i class="icon material-icons">phone</i></div>
                    <div class="item-inner">
                        <div class="item-title"><%=customerPersonalVO.getMobile()%></div>
                    </div></a>
                </li>
                <%
                    }
                %>

            </ul>
        </div>

            <div class="content-block">
                <p class="buttons-row">
                    <a href="<%=Config.getWebPH()%>/thirdparty/fdcg/customer_mobile_change.jsp" class="button button-raised button-fill">更改存管手机号</a>
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