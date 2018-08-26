<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.TimeUtils" %>
<%@ page import="com.youngbook.service.cms.ArticleService" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="com.youngbook.entity.wvo.cms.ArticleWVO" %>
<%@ page import="com.youngbook.common.utils.StringUtils" %>
<%@ page import="com.youngbook.service.system.UserService" %>
<%@ page import="com.youngbook.entity.po.UserPO" %>
<%@ page import="com.youngbook.entity.po.cms.ArticlePO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String articleId = request.getParameter("articleId");
    String userId = request.getParameter("userId");
    ArticleService articleService = Config.getBeanByName("articleService", ArticleService.class);
    ArticlePO article = articleService.loadArticlePOById(articleId);

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
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern2/include/js/config.js"></script>
    <!-- Path to Framework7 Library CSS-->
    <!--<link rel="stylesheet" href="include/framework/framework7-1.6.5/css/framework7.material.min.css">-->
    <link rel="stylesheet" href="<%=Config.getWebRoot()%>/modern2/include/framework/framework7-1.6.5/css/framework7.material.css">
    <link rel="stylesheet" href="<%=Config.getWebRoot()%>/modern2/include/framework/framework7-1.6.5/css/framework7.material.colors.css">
    <!-- Path to your custom app styles-->
    <link rel="stylesheet" href="<%=Config.getWebRoot()%>/modern2/include/css/my-app.css">
    <!--<link rel="stylesheet/less" type="text/css" href="include/less/my-app.less">-->
    <link rel="stylesheet" href="<%=Config.getWebRoot()%>/modern2/include/css/material-icons.css">
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


            <div data-page="share_article" class="page">

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
                        <div class="card-header" style="display: <%=!StringUtils.isEmpty(article.getTitle())?"":"none"%>">
                            <%=!StringUtils.isEmpty(article.getTitle())?article.getTitle():userPO.getName()+"的随想"%>
                        </div>
                        <div class="card-content">
                            <div class="card-content-inner">
                                <p class="color-gray"><%=article.getPublishedTime()%></p>
                                <p><%=article.getContent()%></p>
                            </div>
                        </div>
                        <%--<div class="card-footer"><a href="#" class="link">收藏</a><a href="#" class="link">评论</a><a href="#" class="link">分享</a></div>--%>
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
    <!-- Second view (for second wrap)-->

</div>
    <!-- Path to Framework7 Library JS-->
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern2/include/framework/framework7-1.6.5/js/framework7.min.js"></script>
    <script type="text/javascript" src="<%=Config.getModernFrameworkRoot()%>/jquery.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern2/include/extensions/framework-modern.js"></script>
    <!-- Path to your app js-->
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern2/include/js/my-app.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern2/include/extensions/md5.js"></script>
</body>
</html>