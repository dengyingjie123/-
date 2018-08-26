<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.vo.customer.CustomerPersonalVO" %>
<%@ page import="com.youngbook.common.fdcg.FdcgCommon" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    CustomerPersonalVO loginCustomerVO = Config.getLoginCustomerVOInSession(request);
%>
<div data-page="fdcg-customer-register" class="page">
    <div class="navbar">
        <div class="navbar-inner navbar-ph">
            <div class="left"><a href="#" class="back link"> <i class="icon-white icon-back"></i><span></span></a></div>
            <div class="center sliding">开通银行存管账户</div>
        </div>
    </div>

    <div class="page-content">



        <div class="list-block">
            <ul>
                <li>
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">face</i></div>
                        <div class="item-inner">
                            <div class="item-input">
                                <input type="text" name="realName" id="realName" placeholder="姓名（必填）" value="<%=loginCustomerVO.getName()%>"/>
                            </div>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">payment</i></div>
                        <div class="item-inner">
                            <div class="item-input">
                                <input type="text" id="identityCode" name="identityCode" placeholder="证件号（必填）" value="<%=loginCustomerVO.getIdCard()%>"/>
                            </div>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">phone_iphone</i></div>
                        <div class="item-inner">
                            <div class="item-input">
                                <input type="text" id="mobilePhone" name="mobilePhone" placeholder="手机号（必填）" value="<%=loginCustomerVO.getMobile()%>"/>
                            </div>
                        </div>
                    </div>
                </li>
            </ul>

        </div>
        <input type="hidden" id="customerId" name="customerId" value=""/>
        <input type="hidden" id="id" name="id" value=""/>





        <div class="content-block">
            <p class="buttons-row">
                <%--<a href="#" class="button button-raised button-fill btn-card-delete">解绑</a>--%>
                <a href="#" class="button button-raised button-fill btn-fdcg-customer-register">下一步</a>
            </p>
        </div>

        <form id="form-customer-register" name="form1" method="post" action="<%=FdcgCommon.getApiUrl("thirdparty.fdcg.api.user.register")%>">
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