<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.NumberUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%
    String type = com.youngbook.common.utils.HttpUtils.getParameter(request, "type");
    String loginCustomerId = Config.getLoginCustomerInSession(request).getId();
%>

<div data-page="more-list" class="page">

    <div class="navbar">
        <div class="navbar-inner navbar-ph">
            <div class="center">更多</div>
        </div>
    </div>

    <div class="page-content hide-bars-on-scroll">

        <a href="<%=Config.getWebPH()%>/more/company_info.jsp" class="link color-black">
        <div class="card ks-facebook-card">
            <div class="card-content">
                <div class="card-content-inner">
                    <p class="color-gray">德恒普惠介绍</p>
                    <p>德恒普惠，专注于政信项目金融产品投资交易，是以投资、转让为核心业务的综合服务平台，并致力于为特定机构及个人持有的资产提供优质的综合服务。</p>
                    <p>更多</p>
                </div>
            </div>
        </div>
        </a>

        <div class="content-block-title">更多信息</div>
        <div class="list-block">
            <ul>
                <li><a href="#" class="link color-black check-version">
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">sync</i></div>
                        <div class="item-inner">
                            <div class="item-title">检查更新</div>
                            <div class="item-after"><%=Config.getSystemConfig("ph.version")%></div>
                        </div>
                    </div>
                </li></a>
                <%--<a href="<%=Config.getWebRootCircle()%>/mine/mine_change_mobile.jsp" class="link-black">--%>
                <li>
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">phone</i></div>
                        <div class="item-inner">
                            <div class="item-title">联系客服</div>
                            <div class="item-after"><%=Config.getSystemConfig("ph.service.mobile")%></div>
                        </div>
                    </div>
                </li>
                <li><a href="<%=Config.getWebPH()%>/more/help_list.jsp" class="link color-black">
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">help_outline</i></div>
                        <div class="item-inner">
                            <div class="item-title">系统帮助</div>
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