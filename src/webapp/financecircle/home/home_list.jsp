<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.vo.cms.ArticleVO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.youngbook.entity.po.UserPO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%
    UserPO loginUserInSession = Config.getLoginUserInSession(request);
    if (loginUserInSession == null) {
        out.println("<script>window.location='"+Config.getWebDeheSales()+"/login.jsp'</script>");
        return;
    }
%>
<%
    String type = com.youngbook.common.utils.HttpUtils.getParameter(request, "type");

    List<ArticleVO> listArticleVO = (List<ArticleVO>)request.getAttribute("listArticleVO");
%>

<div data-page="home-list" class="page">

    <div class="navbar">
        <div class="navbar-inner">
            <div class="center">首页</div>
        </div>
    </div>

    <div class="page-content hide-bars-on-scroll">

        <div class="content-block">
            财经资讯
        </div>

        <%
            for (int i = 0; listArticleVO != null && i < listArticleVO.size(); i++) {
                ArticleVO articleVO = listArticleVO.get(i);

        %>
        <div class="card ks-facebook-card">
            <div class="card-header">
                <%=articleVO.getTitle()%>
            </div>
            <div class="card-content">
                <div class="card-content-inner">
                    <p class="color-gray"><%=articleVO.getPublishedTime()%></p>
                    <p><%=articleVO.getSummaryHtml()%></p>
                </div>
            </div>
            <div class="card-footer">
                <%--<a href="#" class="link">收藏</a>--%>
                <a href="#" data-id="<%=articleVO.getId()%>" data-user-id="<%=Config.getLoginUserInSession(request).getId()%>" data-title="<%=articleVO.getTitle()%>" data-summary-text="<%=articleVO.getSummaryText()%>" class="link btn-home-share-preview">预览</a>
                <a href="#" data-id="<%=articleVO.getId()%>" data-user-id="<%=Config.getLoginUserInSession(request).getId()%>" data-title="<%=articleVO.getTitle()%>" data-summary-text="<%=articleVO.getSummaryText()%>" class="link btn-home-share">分享</a>
            </div>
        </div>
        <%
            }
        %>

        <div class="content-block">
            &nbsp;
        </div>
        <div class="content-block">
            &nbsp;
        </div>

    </div>
</div>