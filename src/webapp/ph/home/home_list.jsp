<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%
    String type = com.youngbook.common.utils.HttpUtils.getParameter(request, "type");
%>

<div data-page="home-list" class="page">

    <div class="navbar">
        <div class="navbar-inner navbar-ph">
            <div class="center">首页</div>
        </div>
    </div>

    <div class="page-content hide-bars-on-scroll">

        <div class="content-block">
            财经资讯
        </div>

        {{#each this.rows}}
        <div class="card ks-facebook-card">
            <div class="card-header">
                {{title}}
            </div>
            <div class="card-content">
                <div class="card-content-inner">
                    <p class="color-gray">{{publishedTime}}</p>
                    <p>{{summaryHtml}}</p>
                </div>

                <%--<div class="list-block media-list">--%>
                    <%--<ul>--%>
                        <%--<li class="item-content">--%>
                            <%--<div class="item-media"><img src="include/img/cms002.png" width="44"/></div>--%>
                            <%--<div class="item-inner">--%>
                                <%--<div class="item-title-row">--%>
                                    <%--<div class="item-title">3分钟弄懂中国金融体系</div>--%>
                                <%--</div>--%>
                                <%--<div class="item-subtitle">金融风暴、债务危机、房地产资金链断裂、财政赤字……</div>--%>
                            <%--</div>--%>
                        <%--</li>--%>
                        <%--<li class="item-content">--%>
                            <%--<div class="item-media"><img src="include/img/cms003.png" width="44"/></div>--%>
                            <%--<div class="item-inner">--%>
                                <%--<div class="item-title-row">--%>
                                    <%--<div class="item-title">一文读懂股权投资的“募、投、管、退”</div>--%>
                                <%--</div>--%>
                                <%--<div class="item-subtitle">股权投资的本质，就是支持那些去发现新大陆的人。</div>--%>
                            <%--</div>--%>
                        <%--</li>--%>
                        <%--<li class="item-content">--%>
                            <%--<div class="item-media"><img src="include/img/cms004.png" width="44"/></div>--%>
                            <%--<div class="item-inner">--%>
                                <%--<div class="item-title-row">--%>
                                    <%--<div class="item-title">股权投资的四个阶段</div>--%>
                                <%--</div>--%>
                                <%--<div class="item-subtitle">股权投资分为四个阶段，就是我们通常所说的，募集，投资，管理，退出。</div>--%>
                            <%--</div>--%>
                        <%--</li>--%>
                    <%--</ul>--%>
                <%--</div>--%>

            </div>
            <div class="card-footer">
                <%--<a href="#" class="link">收藏</a>--%>
                <a href="#" data-id="{{id}}" data-user-id="<%=Config.getLoginUserInSession(request).getId()%>" data-title="{{title}}" data-summary-text="{{summaryText}}" class="link btn-home-share-preview">预览</a>
                <a href="#" data-id="{{id}}" data-user-id="<%=Config.getLoginUserInSession(request).getId()%>" data-title="{{title}}" data-summary-text="{{summaryText}}" class="link btn-home-share">分享</a>
            </div>
        </div>
        {{/each}}


        <%--<div class="content-block">--%>
            <%--热销产品--%>
        <%--</div>--%>

        <%--<div class="card ks-facebook-card">--%>
            <%--<div class="card-header">--%>
                <%--<div>{{name}}<a href="#" class="production-tag"><%=type%></a></div>--%>
            <%--</div>--%>
            <%--<div class="card-content">--%>
                <%--<div class="card-content-inner">--%>
                    <%--<div class="row col-header">--%>
                        <%--<div class="col-25 red"><h3>{{#js_compare "this.maxExpectedYield > 0"}}{{maxExpectedYield}}%{{else}}浮动{{/js_compare}}</h3></div>--%>
                        <%--<div class="col-25"><h3>{{investTermView}}</h3></div>--%>
                        <%--<div class="col-25"><h3>{{js "this.minSizeStart/10000"}}万</h3></div>--%>
                        <%--<div class="col-25 red"><h3>认证可见</h3></div>--%>
                    <%--</div>--%>
                    <%--<div class="row col-header-description">--%>
                        <%--<div class="col-25">最高年化</div>--%>
                        <%--<div class="col-25">投资期限</div>--%>
                        <%--<div class="col-25">起投金额</div>--%>
                        <%--<div class="col-25">最高返佣</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div class="card-footer"><a href="#" class="link">收藏</a><a href="market/production_detail.jsp?productionId={{id}}" class="link">详情</a></div>--%>
        <%--</div>--%>

        <div class="content-block">
            &nbsp;
        </div>
        <div class="content-block">
            &nbsp;
        </div>

    </div>
</div>