<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.KVObjects" %>
<%@ page import="com.youngbook.common.KVObject" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<div data-page="bank-list" class="page">
    <div class="navbar">
        <div class="navbar-inner navbar-ph">
            <div class="left"><a href="#" class="back link"> <i class="icon-white icon-back"></i><span></span></a></div>
            <div class="center sliding">开户银行</div>
            <div class="right">
            </div>
        </div>
    </div>
    <div class="page-content">

        <div class="list-block">
            <ul>

                <li><a href="#" class="item-link smart-select">
                    <select name="car" multiple="multiple">
                        <optgroup label="Japanese">
                            <option value="honda" selected="selected">Honda</option>
                            <option value="lexus">Lexus</option>
                            <option value="mazda">Mazda</option>
                            <option value="nissan">Nissan</option>
                            <option value="toyota">Toyota</option>
                        </optgroup>
                        <optgroup label="German">
                            <option value="audi" selected="selected">Audi</option>
                            <option value="bmw">BMW</option>
                            <option value="mercedes">Mercedes</option>
                            <option value="vw">Volkswagen</option>
                            <option value="volvo">Volvo</option>
                        </optgroup>
                        <optgroup label="American">
                            <option value="cadillac">Cadillac</option>
                            <option value="chrysler">Chrysler</option>
                            <option value="dodge">Dodge</option>
                            <option value="ford" selected="selected">Ford</option>
                        </optgroup>
                    </select>
                    <div class="item-content">
                        <div class="item-inner">
                            <div class="item-title">Car</div>
                        </div>
                    </div></a></li>
                <li><a href="#" class="item-link smart-select">
                    <select name="mac-windows">
                        <option value="mac" selected="selected">Mac</option>
                        <option value="windows">Windows</option>
                    </select>
                    <div class="item-content">
                        <div class="item-inner">
                            <div class="item-title">Mac or Windows</div>
                        </div>
                    </div></a>
                </li>
            </ul>
        </div>

        <%--&lt;%&ndash;<div class="content-block-title">投资信息</div>&ndash;%&gt;--%>
        <%--<div class="list-block">--%>
            <%--<ul>--%>
                <%--<%--%>
                    <%--for (int i = 0; i < banks.size(); i++) {--%>
                        <%--KVObject bank = banks.get(i);--%>
                <%--%>--%>
                <%--<li><a href="#" class="item-link item-content">--%>
                    <%--<div class="item-media"><i class="icon material-icons">payment</i></div>--%>
                    <%--<div class="item-inner">--%>
                        <%--<div class="item-title"><%=bank.getValue()%></div>--%>
                    <%--</div></a>--%>
                <%--</li>--%>
                <%--<%--%>
                    <%--}--%>
                <%--%>--%>
            <%--</ul>--%>
        <%--</div>--%>



        <div class="content-block">
            &nbsp;
        </div>

        <div class="content-block">
            &nbsp;
        </div>

    </div>
</div>