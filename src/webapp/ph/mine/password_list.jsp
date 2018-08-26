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
<div data-page="mine-password-list" class="page">
    <div class="navbar">
        <div class="navbar-inner navbar-ph">
            <div class="left"><a href="#" class="back link"> <i class="icon-white icon-back"></i><span></span></a></div>
            <div class="center sliding">我的密码</div>
        </div>
    </div>
    <div class="page-content">

        <%--<div class="content-block-title">投资信息</div>--%>
        <div class="list-block">
            <ul>
                <li><a href="<%=Config.getWebPH()%>/mine/password_save.jsp?r=<%=NumberUtils.randomNumbers(5)%>" class="item-link">
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">lock_outline</i></div>
                        <div class="item-inner">
                            <div class="item-title">修改我的登录密码</div>
                            <div class="item-after"></div>
                        </div>
                    </div></a>
                </li>
                <li><a href="#" class="item-link item-content btn-mine-password-trade-change">
                    <div class="item-media"><i class="icon material-icons">lock_outline</i></div>
                    <div class="item-inner">
                        <div class="item-title">修改我的存管交易密码</div>
                    </div></a>
                </li>
            </ul>
        </div>

            <div class="content-block">
                <p class="buttons-row">
                    <a href="#" class="button button-raised button-fill btn-fdcg-customer-account-unbind">存管银行账号已绑定</a>
                </p>
            </div>

            <form id="form-customer-password-trade-change" name="form1" method="post" action="<%=FdcgCommon.getApiUrl("thirdparty.fdcg.api.user.pwd.reset")%>">
                <input type="hidden" id="reqData" name="reqData" value=""/>
            </form>


        <div class="content-block">
            &nbsp;
        </div>

        <div class="content-block">
            &nbsp;
        </div>

    </div>
</div>