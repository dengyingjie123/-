<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.NumberUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div data-page="production-detail" class="page">
    <div class="navbar">
        <div class="navbar-inner navbar-ph">
            <div class="left"><a href="#" class="back link"> <i class="icon-white icon-back"></i><span></span></a></div>
            <div class="center sliding">{{this.production.name}}</div>
        </div>
    </div>
    <div class="page-content">

        <div class="row production-detail-v">
            <!-- Each "cell" has col-[widht in percents] class -->
            <div class="col-25">{{#js_compare "this.production.expectedYield > 0"}}{{production.expectedYield}}%{{else}}浮动{{/js_compare}}</div>
            <div class="col-25">{{production.investTermView}}</div>
            <div class="col-25">5万元</div>
        </div>
        <div class="row production-detail-d">
            <!-- Each "cell" has col-[widht in percents] class -->
            <div class="col-25">最高年化</div>
            <div class="col-25">投资期限</div>
            <div class="col-25">起投金额</div>
        </div>


        <div class="content-block-title">产品说明</div>
        <div class="item-content">
            <%--<div class="row">--%>
                <%--<!-- Each "cell" has col-[widht in percents] class -->--%>
                <%--<div class="col-30" style="text-align: left; color: #cccccc; padding:2px 0 2px 20px;">发行规模</div>--%>
                <%--<div class="col-70" style="text-align: left; color: #333333; padding:2px 0 2px 5px;">{{production.size}}</div>--%>
            <%--</div>--%>
            <div class="row">
                <!-- Each "cell" has col-[widht in percents] class -->
                <div class="col-30" style="text-align: left; color: #cccccc; padding:2px 0 2px 20px;">产品期限</div>
                <div class="col-70" style="text-align: left; color: #333333; padding:2px 0 2px 5px;">{{production.investTermView}}</div>
            </div>
            <div class="row">
                <!-- Each "cell" has col-[widht in percents] class -->
                <div class="col-30" style="text-align: left; color: #cccccc; padding:2px 0 2px 20px;">付息方式</div>
                <div class="col-70" style="text-align: left; color: #333333; padding:2px 0 2px 5px;">{{production.interestPaymentDescription}}</div>
            </div>
        </div>

        {{#each this.productionArticles}}
        <div class="content-block-title">{{title}}</div>
        <div class="item-content production-detail-content">
            {{content}}
        </div>
        {{/each}}



        <div class="content-block">
            <p class="buttons-row">
                <a data-id="{{this.production.id}}" href="#" class="button button-raised button-fill color-01 btn-investment">投资</a>
            </p>
            <div class="row">

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