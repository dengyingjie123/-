<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.KVObjects" %>
<%@ page import="com.youngbook.common.KVObject" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.mchange.v1.util.StringTokenizerUtils" %>
<%@ page import="com.youngbook.common.utils.StringUtils" %>
<%@ page import="com.youngbook.common.utils.NumberUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    CustomerPersonalPO loginCustomer =  Config.getLoginCustomerInSession(request);
%>
<div data-page="system-list" class="page">

    <div class="page-content">

        <div class="header_bar2">
            <a href="#" class="back link"><div class="header_back"><span></span></div></a>
            <div class="header_new_title">系统设置</div>
        </div>
        <div class="list-block">
            <ul>
                <li><a href="#" class="item-link btn-check-version">
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">autorenew</i></div>
                        <div class="item-inner">
                            <div class="item-title">检查更新</div>
                            <div class="item-after"></div>
                        </div>
                    </div></a>
                </li>
                <li><a href="#" class="item-link btn-clear-cache">
                    <div class="item-content ">
                        <div class="item-media"><i class="icon material-icons">swap_vertical_circle</i></div>
                        <div class="item-inner">
                            <div class="item-title">清空缓存</div>
                            <div class="item-after"></div>
                        </div>
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