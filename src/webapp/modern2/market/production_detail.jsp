<%@ page import="com.youngbook.common.config.Config" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div data-page="production-detail" class="page">
    <div class="navbar">
        <div class="navbar-inner" style="background-color: #EC4650;color: #ffffff;">
            <div class="left"><a href="#" class="back link"> <i class="icon-white icon-back"></i><span></span></a></div>
            <div class="center sliding">{{this.production.name}}</div>
        </div>
    </div>
    <div class="page-content">

        <div class="row production-detail-v">
            <!-- Each "cell" has col-[widht in percents] class -->
            <div class="col-25">{{#js_compare "this.production.expectedYield > 0"}}{{production.expectedYield}}%{{else}}浮动{{/js_compare}}</div>
            <div class="col-25">{{production.investTermView}}</div>
            <div class="col-25">-</div>
            <div class="col-25">认证可见</div>
        </div>
        <div class="row production-detail-d">
            <!-- Each "cell" has col-[widht in percents] class -->
            <div class="col-25">最高年化</div>
            <div class="col-25">投资期限</div>
            <div class="col-25">起投金额</div>
            <div class="col-25">最高返佣</div>
        </div>


        <div class="content-block-title">产品说明</div>
        <div class="item-content">
            <div class="row">
                <!-- Each "cell" has col-[widht in percents] class -->
                <div class="col-30" style="text-align: left; color: #cccccc; padding:2px 0 2px 20px;">发行规模</div>
                <div class="col-70" style="text-align: left; color: #333333; padding:2px 0 2px 5px;">{{production.size}}</div>
            </div>
            <div class="row">
                <!-- Each "cell" has col-[widht in percents] class -->
                <div class="col-30" style="text-align: left; color: #cccccc; padding:2px 0 2px 20px;">产品期限</div>
                <div class="col-70" style="text-align: left; color: #333333; padding:2px 0 2px 5px;">{{production.investTermView}}</div>
            </div>
            <div class="row">
                <!-- Each "cell" has col-[widht in percents] class -->
                <div class="col-30" style="text-align: left; color: #cccccc; padding:2px 0 2px 20px;">付息方式</div>
                <div class="col-70" style="text-align: left; color: #333333; padding:2px 0 2px 5px;">-</div>
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
                <a id="btn-production-share" data-id="{{this.production.id}}" data-title="{{this.production.name}}" data-summary-text="{{this.production.name}}" data-user-id="<%=Config.getLoginUserInSession(request).getId()%>" href="#" class="button button-raised button-fill color-blue">分享</a>
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