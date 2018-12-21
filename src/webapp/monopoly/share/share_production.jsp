<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.TimeUtils" %>
<%@ page import="com.youngbook.service.cms.ArticleService" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="com.youngbook.entity.wvo.cms.ArticleWVO" %>
<%@ page import="com.youngbook.common.utils.StringUtils" %>
<%@ page import="com.youngbook.service.system.UserService" %>
<%@ page import="com.youngbook.entity.po.UserPO" %>
<%@ page import="com.youngbook.entity.po.cms.ArticlePO" %>
<%@ page import="com.youngbook.entity.vo.production.ProductionVO" %>
<%@ page import="com.youngbook.service.production.ProductionService" %>
<%@ page import="com.youngbook.entity.po.production.ProductionPO" %>
<%@ page import="com.youngbook.common.MyException" %>
<%@ page import="com.youngbook.entity.wvo.production.ProductionWVO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.youngbook.entity.vo.Sale.ProductionCommissionVO" %>
<%@ page import="com.youngbook.service.sale.ProductionCommissionService" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String productionId = request.getParameter("productionId");
    String userId = request.getParameter("userId");
    ProductionService productionService = Config.getBeanByName("productionService", ProductionService.class);
    ProductionCommissionService productionCommissionService = Config.getBeanByName("productionCommissionService", ProductionCommissionService.class);


    if (StringUtils.isEmpty(productionId)) {
        MyException.newInstance("无法获得产品编号").throwException();
    }

    List<ProductionWVO> listProductionWVO = productionService.getListProductionWVOById(productionId);
    ProductionWVO productionWVO = new ProductionWVO();

    StringBuffer sbExpectedYield = new StringBuffer();

    for (int i = 0; listProductionWVO != null && i < listProductionWVO.size(); i++) {
        productionWVO = listProductionWVO.get(i);
        sbExpectedYield.append(productionWVO.getExpectedYield()).append("%，");
    }

    sbExpectedYield = StringUtils.removeLastLetters(sbExpectedYield, "，");
    if (sbExpectedYield.toString().equals("0.0%")) {
        sbExpectedYield = new StringBuffer("浮动");
    }

    productionWVO.setExpectedYieldDescription(sbExpectedYield.toString());

    List<ArticlePO> productionArticles = productionService.getListProductionArticle(productionId);

    UserPO userPO = new UserPO();

    if (StringUtils.isEmpty(userId) && Config.getLoginUserInSession(request) != null) {
        userId = Config.getLoginUserInSession(request).getId();
    }


    if (!StringUtils.isEmpty(userId)) {
        UserService userService = Config.getBeanByName("userService", UserService.class);
        userPO = userService.loadUserByUserId(userId);
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <title>理财圈</title>
    <script type="text/javascript" src="<%=Config.getWebDehesales()%>/include/js/config.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/hashMap.js"></script>
    <!-- Path to Framework7 Library CSS-->
    <!--<link rel="stylesheet" href="include/framework/framework7-1.6.5/css/framework7.material.min.css">-->
    <link rel="stylesheet" href="<%=Config.getWebDehesales()%>/include/framework/framework7-1.6.5/css/framework7.material.css">
    <link rel="stylesheet" href="<%=Config.getWebDehesales()%>/include/framework/framework7-1.6.5/css/framework7.material.colors.css">
    <!-- Path to your custom app styles-->
    <link rel="stylesheet" href="<%=Config.getWebDehesales()%>/include/css/my-app.css">
    <!--<link rel="stylesheet/less" type="text/css" href="include/less/my-app.less">-->
    <link rel="stylesheet" href="<%=Config.getWebDehesales()%>/include/css/material-icons.css">
</head>
<body>
<!-- Status bar overlay for fullscreen mode-->
<div class="statusbar-overlay"></div>
<!-- Panels overlay-->
<div class="panel-overlay"></div>

<!-- Views, and they are tabs-->
<!-- We need to set "toolbar-through" class on it to keep space for our tab bar-->
<div class="views tabs toolbar-through">
    <!-- Your first view, it is also a .tab and should have "active" class to make it visible by default-->
    <div id="view-1" class="view view-main tab active">

        <div class="pages">


            <div data-page="production-share" class="page">


                <div class="page-content">

                    <div class="card ks-facebook-card">
                        <div class="card-header">
                            <div class="ks-facebook-avatar"><img src="<%=Config.getWebRoot()%>/system/file/FileDownload.action?moduleId=9D451710&bizId=<%=userId%>" width="34" height="34"/></div>
                            <div class="ks-facebook-name"><%=userPO.getName()%></div>
                            <div class="ks-facebook-date">
                                <p><%=userPO.getDescription()%></p>
                                <p>
                                    <i class="icon material-icons">phone</i>
                                    <a href="tel:<%=userPO.getMobile()%>"><%=userPO.getMobile()%></a>
                                </p>
                            </div>
                        </div>
                    </div>

                    <div class="card ks-facebook-card">
                        <div class="card-header">
                            <%=productionWVO.getName()%>
                        </div>
                        <div class="row production-detail-v">
                            <!-- Each "cell" has col-[widht in percents] class -->
                            <div class="col-25"><%=productionWVO.getExpectedYield()%></div>
                            <div class="col-25"><%=productionWVO.getInvestTermView()%></div>
                            <div class="col-25">-</div>
                        </div>
                        <div class="row production-detail-d">
                            <!-- Each "cell" has col-[widht in percents] class -->
                            <div class="col-25">最高年化</div>
                            <div class="col-25">投资期限</div>
                            <div class="col-25">起投金额</div>
                        </div>


                        <div class="content-block-title">产品说明</div>
                        <div class="item-content">
                            <div class="row">
                                <!-- Each "cell" has col-[widht in percents] class -->
                                <div class="col-30" style="text-align: left; color: #cccccc; padding:2px 0 2px 20px;">发行规模</div>
                                <div class="col-70" style="text-align: left; color: #333333; padding:2px 0 2px 5px;"><%=productionWVO.getSize()/10000%>万</div>
                            </div>
                            <div class="row">
                                <!-- Each "cell" has col-[widht in percents] class -->
                                <div class="col-30" style="text-align: left; color: #cccccc; padding:2px 0 2px 20px;">产品期限</div>
                                <div class="col-70" style="text-align: left; color: #333333; padding:2px 0 2px 5px;"><%=productionWVO.getInvestTermView()%></div>
                            </div>
                            <div class="row">
                                <!-- Each "cell" has col-[widht in percents] class -->
                                <div class="col-30" style="text-align: left; color: #cccccc; padding:2px 0 2px 20px;">付息方式</div>
                                <div class="col-70" style="text-align: left; color: #333333; padding:2px 0 2px 5px;">-</div>
                            </div>
                        </div>

                        <%
                            for (int i = 0; productionArticles != null && i < productionArticles.size(); i++) {
                                ArticlePO articlePO = productionArticles.get(i);
                        %>
                        <div class="content-block-title"><%=articlePO.getTitle()%></div>
                        <div class="item-content production-detail-content">
                            <%=articlePO.getContent()%>
                        </div>
                        <%
                            }
                        %>
                    </div>





                    <div class="content-block">
                        &nbsp;
                    </div>




                    <div class="content-block">
                        &nbsp;
                    </div>



                </div>


                    </div>
            </div>

        </div>

    </div>
    <!-- Second view (for second wrap)-->

</div>
    <!-- Path to Framework7 Library JS-->
<!-- Path to Framework7 Library JS-->
<script type="text/javascript" src="<%=Config.getWebDehesales()%>/include/framework/framework7-1.6.5/js/framework7.min.js"></script>
<script type="text/javascript" src="<%=Config.getModernFrameworkRoot()%>/jquery.min.js"></script>
<script type="text/javascript" src="<%=Config.getWebRoot()%>/include/extensions/framework-modern.js"></script>
<!-- Path to your app js-->
<script type="text/javascript" src="<%=Config.getWebDehesales()%>/include/js/pages.js"></script>
<script type="text/javascript" src="<%=Config.getWebDehesales()%>/include/js/my-app.js"></script>
<script type="text/javascript" src="<%=Config.getWebRoot()%>/include/extensions/md5.js"></script>
</body>
</html>