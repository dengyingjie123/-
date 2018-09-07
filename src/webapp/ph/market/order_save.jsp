<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page import="com.youngbook.service.customer.CustomerPersonalService" %>
<%@ page import="com.youngbook.entity.vo.customer.CustomerPersonalVO" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerAccountPO" %>
<%@ page import="com.youngbook.entity.vo.customer.CustomerAccountVO" %>
<%
    CustomerPersonalPO customer = Config.getLoginCustomerInSession(request);
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div data-page="order-save" class="page">
    <div class="navbar">
        <div class="navbar-inner navbar-ph">
            <div class="left"><a href="#" class="back link"> <i class="icon-white icon-back"></i><span></span></a></div>
            <div class="center sliding">投资订单</div>
        </div>
    </div>
    <div class="page-content">

        <div class="content-block-title">产品信息</div>
        <div class="item-content">
            <div class="row">
                <!-- Each "cell" has col-[widht in percents] class -->
                <div class="col-30 production-description-title">产品名称</div>
                <div class="col-70 production-description-content">{{this.production.production.name}}</div>
            </div>
            <div class="row">
                <!-- Each "cell" has col-[widht in percents] class -->
                <div class="col-30 production-description-title">最高年化</div>
                <div class="col-70 production-description-content">{{this.production.production.expectedYieldDescription}}</div>
            </div>
            <div class="row">
                <!-- Each "cell" has col-[widht in percents] class -->
                <div class="col-30 production-description-title">产品期限</div>
                <div class="col-70 production-description-content">{{this.production.production.investTermView}}</div>
            </div>
        </div>


        <%--<div class="content-block-title">银行信息</div>--%>
        <%--<div class="list-block">--%>
            <%--<ul>--%>
                <%--<li><a href="#" class="item-link smart-select">--%>
                    <%--<select id="accountId" name="accountId">--%>
                        <%--{{#each this.customer.accountVOs}}--%>
                        <%--<option value="{{id}}">{{bank}} {{numberWithoutMask}}</option>--%>
                        <%--{{/each}}--%>
                    <%--</select>--%>
                    <%--<div class="item-content">--%>
                        <%--<div class="item-inner">--%>
                            <%--<div class="item-title">银行名称</div>--%>
                        <%--</div>--%>
                    <%--</div></a></li>--%>
            <%--</ul>--%>
        <%--</div>--%>


        <div class="content-block-title">个人信息</div>
        <form id="investment" action="<%=Config.getSystemConfig("fuiou.pay.mobile.send.url")%>" class="list-block inset inputs-list">
            <ul>
                <li>
                    <div class="item-content">
                        <%--<div class="item-media"><i class="icon material-icons">person_outline</i></div>--%>
                        <div class="item-inner">
                            <div class="item-title label">投资人</div>
                            <div class="item-input">
                                <input type="text" name="name" placeholder="您的姓名" value="{{this.customer.name}}" {{#js_if "this.customer.customerCatalogId === '1'"}}readonly{{else}}{{/js_if}}/>
                            </div>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="item-content">
                        <div class="item-inner">
                            <div class="item-title label">银行名称</div>
                            <div class="item-input">
                                <select id="accountId" name="accountId">
                                    {{#each this.customer.accountVOs}}
                                        <option value="{{id}}">{{bank}} {{numberWithoutMask}}</option>
                                    {{/each}}
                                </select>
                            </div>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="item-content">
                        <div class="item-inner">
                            <div class="item-title label">支付方式</div>
                            <div class="item-input">
                                <select id="payMethod" name="payMethod">
                                    <option value="0">手机支付</option>
                                    <option value="1">PC网银支付</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="item-content">
                        <%--<div class="item-media"><i class="icon material-icons">local_atm</i></div>--%>
                        <div class="item-inner">
                            <div class="item-title label">投资金额</div>
                            <div class="item-input">
                                <input type="number" pattern="\d*" name="money" placeholder="5万起投" value=""/>
                            </div>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="item-content">
                        <%--<div class="item-media"><i class="icon material-icons">phone_android</i></div>--%>
                        <div class="item-inner">
                            <div class="item-title label">身份证号码</div>
                            <div class="item-input">
                                <input type="text" name="customerIdCardNumber" placeholder="身份证号码" value="{{this.customer.idCard}}" {{#js_if "this.customer.customerCatalogId === '1'"}}readonly{{else}}{{/js_if}}/>
                            </div>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="item-content">
                        <%--<div class="item-media"><i class="icon material-icons">account_circle</i></div>--%>
                        <div class="item-inner">
                            <div class="item-title label">推荐码（选填）</div>
                            <div class="item-input">
                                <input id="txt-login-referralCode" type="text" name="referralCode" placeholder="请输入推荐码（选填）"/>
                            </div>
                        </div>
                    </div>
                </li>
            </ul>
            <input type="hidden" name="id" />
            <input type="hidden" name="ENCTP" value="1" />
            <input type="hidden" name="VERSION" value="2.0" />
            <input type="hidden" name="MCHNTCD" value="<%=Config.getSystemConfig("fuiou.merchant.id")%>" />
            <input type="hidden" name="FM" value="" />
            <input type="hidden" name="customerId" value="<%=customer.getId()%>" />
            <input type="hidden" name="productionId" value="{{this.production.production.id}}" />
            <input type="hidden" name="sizeStart" value="{{this.production.production.sizeStart}}" />
            <input type="hidden" name="increase" value="<%=Config.getSystemConfig("ph.pay.increase")%>" />
        </form>

        <div class="content-block">
            <p class="buttons-row">
                <a href="#" class="button button-raised button-fill color-01 btn-investment">同意并投资</a>
            </p>
        </div>
        <div>
            <div class="row">
                <div class="col-100">
                    投资则表示同意如下文件，请仔细阅读：<br>
                    {{#js_if "this.productionTypeName === '政信通'"}}
                    <a href="<%=Config.getWebPH()%>/mine/paper_investment.jsp">《认购协议》</a>、
                    <a href="<%=Config.getWebPH()%>/mine/paper_risk.jsp">《风险提示及说明》</a><br>
                    <a href="<%=Config.getWebPH()%>/mine/paper_instruction.jsp?bizId={{this.production.production.productHomeId}}&columnName=instruction">《产品说明书》</a>、
                    <a href="<%=Config.getWebPH()%>/mine/paper_confirm.jsp">《投资人确认函》</a><br>
                    {{else}}
                    <a href="<%=Config.getWebPH()%>/mine/paper_zn_001.jsp">《产品收益权转让及服务协议》</a><br>
                    <a href="<%=Config.getWebPH()%>/mine/paper_zn_002.jsp">《收益权转让及回购协议》</a>
                    {{/js_if}}
                </div>
            </div>
        </div>


        <div class="content-block">
            &nbsp;
        </div>

        <div class="content-block">
            &nbsp;
        </div>

    </div>
</div>