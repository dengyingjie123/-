<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.wf.common.Tools" %>
<%@ page import="com.youngbook.common.utils.NumberUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%
    String type = com.youngbook.common.utils.HttpUtils.getParameter(request, "type");
    String loginUserId = Config.getLoginUserInSession(request).getId();
%>

<div data-page="circle-list" class="page toolbar-fixed">

    <div class="navbar">
        <div class="navbar-inner">
            <div class="center">圈子</div>
            <div class="right">
                <a href="circle/circle_save.jsp" id="btn-circle-add" class="link"><i class="icon material-icons">add</i></a>
            </div>
        </div>
    </div>
    <div class="toolbar tabbar">
        <div class="toolbar-inner">
            <a href="#tab-circle-mine" class="tab-link active">我的圈子</a>
        </div>
    </div>

    <div class="page-content hide-bars-on-scroll">

        <div class="tabs">
            <div id="tab-circle-mine" class="tab active">

                {{#each this.returnValue}}
                <div class="card ks-facebook-card">
                    <div class="card-header">
                        <div class="ks-facebook-avatar"><img src="<%=Config.getWebRoot() + Config.getUserAavtarUrl(loginUserId)%>" width="34" height="34"/></div>
                        <div class="ks-facebook-name"><%=Config.getLoginUserInSession(request).getName()%></div>
                        <div class="ks-facebook-date">理财圈认证理财师</div>
                        <div><a href="#" class="production-tag">私募</a></div>
                    </div>
                    <div class="card-content">
                        <div class="card-content-inner">
                            <p class="color-gray">{{publishedTime}}</p>
                            <p>{{content}}</p>
                            <div class="chip">
                                <div class="chip-label">金融</div>
                            </div>
                            <div class="chip">
                                <div class="chip-media bg-teal">美</div>
                                <div class="chip-label">美食</div>
                            </div>
                        </div>
                    </div>
                    <div class="card-footer">
                        <a data-id="{{id}}" href="#" class="link btn-circle-list-delete">删除</a>
                        <a data-id="{{id}}" data-user-id="<%=Config.getLoginUserInSession(request).getId()%>" data-title="<%=Config.getLoginUserInSession(request).getName()%>随想" data-summary-text="{{content}}" data-avatar="<%=Config.getUserAavtarUrl(loginUserId)%>" href="#" class="link btn-circle-list-share">分享</a>
                    </div>
                </div>
                {{/each}}
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