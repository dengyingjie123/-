<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%
    String type = com.youngbook.common.utils.HttpUtils.getParameter(request, "type");
%>

<div data-page="index_market" class="page">

    <div class="navbar">
        <div class="navbar-inner">
            <div class="center">市场</div>
        </div>
    </div>

    <div class="page-content hide-bars-on-scroll">
        <div class="content-block">
            项目分类
        </div>

        <div class="row">
            <div class="col-25">
                <a class="production-type-link" href="#" style="width: 125px;">
                    <i class="production-type-icon-1"></i>
                    <span class="production-type-text">私募</span>
                </a>
            </div>

            <div class="col-25">
                <a class="production-type-link" href="#" style="width: 125px;">
                    <i class="production-type-icon-2"></i>
                    <span class="production-type-text">公募</span>
                </a>
            </div>

            <div class="col-25">
                <a class="production-type-link" href="#" style="width: 125px;">
                    <i class="production-type-icon-3"></i>
                    <span class="production-type-text">理财</span>
                </a>
            </div>

            <div class="col-25">
                <a class="production-type-link" href="#" style="width: 125px;">
                    <i class="production-type-icon-4"></i>
                    <span class="production-type-text">保险</span>
                </a>
            </div>
        </div>






        <div class="content-block">
            产品列表
        </div>
        {{#each this.productions1}}
        <div class="card ks-facebook-card">
            <div class="card-header">
                <div>{{name}}<a href="#" class="production-tag">私募</a></div>
            </div>
            <div class="card-content">
                <div class="card-content-inner">
                    <div class="row col-header">
                        <div class="col-25 red"><h3>{{#js_compare "this.maxExpectedYield > 0"}}{{maxExpectedYield}}%{{else}}浮动{{/js_compare}}</h3></div>
                        <div class="col-25"><h3>{{investTermView}}</h3></div>
                        <div class="col-25"><h3>{{js "this.minSizeStart/10000"}}万</h3></div>
                        <div class="col-25 red"><h3>认证可见</h3></div>
                    </div>
                    <div class="row col-header-description">
                        <div class="col-25">最高年化</div>
                        <div class="col-25">投资期限</div>
                        <div class="col-25">起投金额</div>
                        <div class="col-25">最高返佣</div>
                    </div>
                </div>
            </div>
            <div class="card-footer">
                <%--<a href="#" class="link">收藏</a>--%>
                <a href="market/production_detail.jsp?productionId={{id}}" class="link">详情</a>
            </div>
        </div>
        {{/each}}



        <div class="content-block">
            &nbsp;
        </div>
        <div class="content-block">
            &nbsp;
        </div>

    </div>
</div>