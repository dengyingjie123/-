<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.vo.customer.CustomerPersonalVO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
CustomerPersonalVO loginCustomerVO = Config.getLoginCustomerVOInSession(request);
%>
<div data-page="mine-info" class="page">

    <div class="page-content">
        <div class="header_bar2">
            <a href="#" class="back link"><div class="header_back"><span></span></div></a>
            <div class="header_new_title">我的信息</div>
        </div>
        <%--<div class="content-block-title">投资信息</div>--%>
            <div class="list-block">
                <ul>
                    <li>
                        <div class="item-content">
                            <div class="item-media"><i class="icon material-icons">face</i></div>
                            <div class="item-inner">
                                <div class="item-title">姓名</div>
                                <div class="item-after">{{name}}</div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-media"><i class="icon material-icons">payment</i></div>
                            <div class="item-inner">
                                <div class="item-title">身份证</div>
                                <div class="item-after">{{idCard}}</div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-media"><i class="icon material-icons">phone_iphone</i></div>
                            <div class="item-inner">
                                <div class="item-title">手机号</div>
                                <div class="item-after">{{mobile}}</div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>


            <%--<div class="content-block">--%>
                <%--<p class="buttons-row">--%>
                    <%--{{#js_compare "this.fdcgCustomerId === ''"}}--%>
                    <%--<a href="<%=Config.getWebPH()%>/thirdparty/fdcg/customer_register.jsp?customerId=<%=loginCustomerVO.getId()%>" class="button button-raised button-fill">开通银行存管账户</a>--%>
                    <%--{{else}}--%>
                    <%--<a href="#" class="button button-raised button-fill">银行存管已开通</a>--%>
                    <%--{{/js_compare}}--%>
                <%--</p>--%>
            <%--</div>--%>

            <div class="content-block">
                &nbsp;
            </div>

            <div class="content-block">
                &nbsp;
            </div>

    </div>
</div>