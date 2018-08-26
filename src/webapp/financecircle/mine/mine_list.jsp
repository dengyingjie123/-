<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.NumberUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%
    String type = com.youngbook.common.utils.HttpUtils.getParameter(request, "type");
    String loginUserId = Config.getLoginUserInSession(request).getId();
%>

<div data-page="mine-list" class="page">

    <div class="navbar">
        <div class="navbar-inner">
            <div class="center">我</div>
        </div>
    </div>

    <div class="page-content hide-bars-on-scroll">


        <div class="card ks-facebook-card">
            <div class="card-header">
                <div class="ks-facebook-avatar btn-user-avatar"><img src="<%//=Config.getWebRoot() + Config.getUserAavtarUrl(loginUserId)%>" width="34" height="34"/></div>
                <div class="ks-facebook-name"><%=Config.getLoginUserInSession(request).getName()%></div>
                <div class="ks-facebook-date">理财圈认证理财师</div>
            </div>
            <a href="<%=Config.getWebRootCircle()%>/mine/mine_change_description.jsp?r=<%=NumberUtils.randomNumbers(5)%>" class="link color-black">
            <div class="card-content">
                <div class="card-content-inner">
                    <p class="color-gray">我的介绍</p>
                    <p>{{info['user.description']}}</p>
                </div>
            </div>
            </a>
        </div>

        <div class="content-block-title">基本信息</div>
        <div class="list-block">
            <ul>
                <li>
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">face</i></div>
                        <div class="item-inner">
                            <div class="item-title">姓名</div>
                            <div class="item-after"><%=Config.getLoginUserInSession(request).getName()%></div>
                        </div>
                    </div>
                </li>
                <%--<a href="<%=Config.getWebRootCircle()%>/mine/mine_change_mobile.jsp" class="link-black">--%>
                <li>
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">phone</i></div>
                        <div class="item-inner">
                            <div class="item-title">手机号</div>
                            <div class="item-after"><%=Config.getLoginUserInSession(request).getMobile()%></div>
                        </div>
                    </div>
                </li>
                <%--</a>--%>
                <li>
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">account_circle</i></div>
                        <div class="item-inner">
                            <div class="item-title">我的推荐码</div>
                            <div class="item-after"><%=Config.getLoginUserInSession(request).getReferralCode()%></div>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">settings</i></div>
                        <div class="item-inner">
                            <div class="item-title">系统设置</div>
                            <div class="item-after"> <span class="badge">0</span></div>
                        </div>
                    </div>
                </li>
                <li><a href="#" class="item-link btn-logout">
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">power_settings_new</i></div>
                        <div class="item-inner">
                            <div class="item-title">退出系统</div>
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