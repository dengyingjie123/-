<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.youngbook.common.utils.NumberUtils" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%
    String type = com.youngbook.common.utils.HttpUtils.getParameter(request, "type");
%>

<div data-page="production-list" class="page">

    <div class="navbar">
        <div class="navbar-inner navbar-ph">
            <div class="center">市场</div>
        </div>
    </div>

    <div class="page-content hide-bars-on-scroll">
        <div class="content-block">
            产品列表
        </div>
        {{#each this.productions4}}

        <div class="card ks-facebook-card">
            <div class="card-header">
                <div class="production-title">{{name}}<a href="#" class="production-tag">在售</a></div>
            </div>
            <a href="market/production_detail.jsp?productionId={{id}}&r=<%=NumberUtils.randomNumbers(5)%>">
                <div class="card-content">
                    <div class="card-content-inner">
                        <div class="row col-header">
                            <div class="col-25 red"><h2>{{#js_compare "this.maxExpectedYield > 0"}}{{maxExpectedYield}}%{{else}}浮动{{/js_compare}}</h2></div>
                            <div class="col-25"><h2>{{investTermView}}</h2></div>
                            <div class="col-25"><h2>{{js "this.minSizeStart/10000"}}万</h2></div>
                        </div>
                        <div class="row col-header-description">
                            <div class="col-25">最高年化</div>
                            <div class="col-25">投资期限</div>
                            <div class="col-25">起投金额</div>
                        </div>
                    </div>
                </div>
            </a>
            <div class="card-footer">
                <%--<a href="#" class="link">收藏</a>--%>
                <div class="chip">
                    <div class="chip-label">政府项目</div>
                </div>
                <div class="chip">
                    <div class="chip-label">财政担保</div>
                </div>
                <a href="market/production_detail.jsp?productionId={{id}}&r=<%=NumberUtils.randomNumbers(5)%>" class="link">详情</a>
            </div>
        </div>

        {{/each}}


        {{#each this.productions2}}

        <div class="card ks-facebook-card">
            <div class="card-header">
                <div class="production-title">{{name}}<a href="#" class="production-tag">在售</a></div>
            </div>
            <a href="market/production_detail.jsp?productionId={{id}}&r=<%=NumberUtils.randomNumbers(5)%>">
                <div class="card-content">
                    <div class="card-content-inner">
                        <div class="row col-header">
                            <div class="col-25 red"><h2>{{#js_compare "this.maxExpectedYield > 0"}}{{maxExpectedYield}}%{{else}}浮动{{/js_compare}}</h2></div>
                            <div class="col-25"><h2>{{investTermView}}</h2></div>
                            <div class="col-25"><h2>{{js "this.minSizeStart/10000"}}万</h2></div>
                        </div>
                        <div class="row col-header-description">
                            <div class="col-25">最高年化</div>
                            <div class="col-25">投资期限</div>
                            <div class="col-25">起投金额</div>
                        </div>
                    </div>
                </div>
            </a>
            <div class="card-footer">
                <a href="market/production_detail.jsp?productionId={{id}}&r=<%=NumberUtils.randomNumbers(5)%>" class="link">详情</a>
            </div>
        </div>

        {{/each}}



        <%--<div class="content-block">--%>
            <%--<a href="#"  class="link btn-test">测试</a>--%>
        <%--</div>--%>
        <div class="content-block">
            &nbsp;
        </div>

    </div>
</div>