<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.KVObjects" %>
<%@ page import="com.youngbook.common.KVObject" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.mchange.v1.util.StringTokenizerUtils" %>
<%@ page import="com.youngbook.common.utils.StringUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    CustomerPersonalPO loginCustomer =  Config.getLoginCustomerInSession(request);

    String id = request.getParameter("id");
    if (StringUtils.isEmpty(id)) {
        id = "";
    }

    String number = request.getParameter("number");
    if (StringUtils.isEmpty(number)) {
        number = "";
    }

    String bank = request.getParameter("bank");
    if (StringUtils.isEmpty(bank)) {
        bank = "";
    }

    String bankCode = request.getParameter("bankCode");
    if (StringUtils.isEmpty(bankCode)) {
        bankCode = "";
    }

%>
<%
    KVObjects banks = new KVObjects();
    banks.addItem("0801020000","中国工商银行");
    banks.addItem("0801030000","中国农业银行");
    banks.addItem("0801040000","中国银行");
    banks.addItem("0801050000","中国建设银行");
    banks.addItem("0801000000","中国邮政储蓄银行");
    banks.addItem("0804100000","平安银行");
    banks.addItem("0803050000","中国民生银行");
    banks.addItem("0803030000","中国光大银行");
    banks.addItem("0803060000","广发银行");
    banks.addItem("0803020000","中信银行");
    banks.addItem("0803090000","兴业银行");
    banks.addItem("0803040000","华夏银行");
    banks.addItem("0803010000","交通银行");
    banks.addItem("08030080000","招商银行");
    banks.addItem("0803100000","上海浦东发展银行");
    banks.addItem("0804031000","北京银行");
    banks.addItem("0804010000","上海银行");
%>
<div data-page="card-save" class="page">
    <div class="navbar">
        <div class="navbar-inner navbar-ph">
            <div class="left"><a href="#" class="back link"> <i class="icon-white icon-back"></i><span></span></a></div>
            <div class="center sliding">银行卡信息</div>
        </div>
    </div>

    <div class="page-content">

        <div class="list-block">
            <ul>
                <li><a href="#" class="item-link smart-select" data-back-on-select="true">
                    <select id="bankCode" name="bankCode">
                        <%
                            for (int i = 0; i < banks.size(); i++) {
                                KVObject kvObject = banks.get(i);
                                String selected = "";
                                if (kvObject.getKeyStringValue().equals(bankCode)) {
                                    selected = "selected";
                                }
                                out.println("<option value=\""+kvObject.getKeyStringValue()+"\" "+selected+">"+kvObject.getValueStringValue()+"</option>");
                            }
                        %>
                    </select>
                    <div class="item-content">
                        <div class="item-inner">
                            <div class="item-title">银行名称</div>
                        </div>
                    </div></a></li>
            </ul>
        </div>

        <div class="list-block">
            <ul>
                <li>
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">payment</i></div>
                        <div class="item-inner">
                            <div class="item-input">
                                <input type="text" name="number" id="number" placeholder="银行卡号" value="<%=number%>"/>
                            </div>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">account_balance</i></div>
                        <div class="item-inner">
                            <div class="item-input">
                                <input type="text" id="bankBranchName" name="bankBranchName" placeholder="开户行名称（选填）"/>
                            </div>
                        </div>
                    </div>
                </li>
            </ul>

        </div>
        <input type="hidden" id="customerId" name="customerId" value="<%=loginCustomer.getId()%>"/>
        <input type="hidden" id="id" name="id" value="<%=id%>"/>





        <div class="content-block">
            <%
                if (StringUtils.isEmpty(id)) {

            %>
            <p class="buttons-row">
                <a href="#" class="button button-raised button-fill color-01 btn-card-add">添加</a>
            </p>
            <%
                }
                else {
            %>
            <p class="buttons-row">
                <%--<a href="#" class="button button-raised button-fill btn-card-delete">解绑</a>--%>
                <a href="<%=Config.getWebPH()%>/mine/card_save_fuiou.jsp" class="button button-raised button-fill">解绑</a>
            </p>
            <%
                }
            %>
        </div>


        <div class="content-block">
            &nbsp;
        </div>

        <div class="content-block">
            &nbsp;
        </div>

    </div>
</div>