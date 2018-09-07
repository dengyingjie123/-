<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerMoneyPO" %>
<%@ page import="com.youngbook.entity.wvo.production.ProductionWVO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.youngbook.common.Pager" %>
<%@ page import="com.youngbook.entity.vo.cms.ColumnVO" %>
<%@ page import="com.youngbook.entity.wvo.cms.ArticleWVO" %>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="/w2/home.jsp" %>

<%
//    // 判断用户是否登陆
//    CustomerPersonalPO loginUser = null;
//    if (request.getSession().getAttribute("loginUser") != null) {
//        loginUser = (CustomerPersonalPO) request.getSession().getAttribute("loginUser");
//    } else {
//        String url = Config.getWebRoot() + Config.Web_URL_Login;
//        response.sendRedirect(url);
//        return;
//    }

    List<ColumnVO> columnList = (List<ColumnVO>) request.getAttribute("column");

    List<ArticleWVO> articles= (List<ArticleWVO>) request.getAttribute("articles");

    String token = (String) request.getAttribute("token");
%>

<!DOCTYPE html><html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv=“X-UA-Compatible” content=“IE=8″>
    <title>我的账户</title>
    <link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/cms/guidance.css" rel="stylesheet" type="text/css"/>

    <script language="javascript" src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>
    <script language="javascript"
            src="<%=Config.getWebRoot()%>/w2/dist/plus/easypiechart/jquery.easypiechart.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/fweb.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/html5/excanvas.compiled.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/c.js"></script>

    <script src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/js/bootstrap.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/plus/easypiechart/jquery.easypiechart.min.js"></script>


</head>

<body>


<%--top--%>
<jsp:include page="/w2/top.jsp"/>

<%--导航栏--%>
<div style="width: 100%;background: #fafafa;" >
    <div id="nav" class="w1200" style="padding-top: 7px;padding-bottom: 7px;">
        <a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex"><img src="<%=Config.getWebRoot()%>/w2/img/logo_Assistor.png" alt="开普乐"/></a>
        <span>
          <a id="param1" href="<%=Config.getWebRoot()%>/w2/index/ShowIndex" class="item" >首页</a>
        <a id="param2" href="<%=Config.getWebRoot()%>/w2/modules/production/productionList.jsp" class="item">投资专区</a>

          <a id="param3" href="<%=Config.getWebRoot()%>/w2/modules/about.jsp"   style="color:#d28d2a">新手指引</a> |
            <a id="param4" href="<%=Config.getWebRoot()%>/w2/customer/IndexShow">我的账户</a>
        </span>
    </div>
</div>

<%--top 结束--%>
<div id="position" class="w1200">
    <a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex">首页</a> &gt;
    <a href="<%=Config.getWebRoot()%>/w2/customer/IndexShow">关于我们</a>
</div>

<div id="container" class="w1200">
    <div id="menu">
        <ul>
            <li  style="background: url(<%=Config.getWebRoot()%>/w2/img/menu_guidance_current.png) no-repeat;">
                <a href="#" class="font20" style="color: white;">关于我们</a>
            </li>
            <% if(columnList!=null&&columnList.size()>0){
                for (int i=0;i<columnList.size();i++) {
                    String flag="guidance_"+i;
            %>
            <li><a href="<%=Config.getWebRoot()%>/w2/cms/getColumnAll?id=<%=columnList.get(i).getId()%>&token=guidance_<%=i%>" class="font20G"   <%=token.equals(flag)?"style='color:#d28d2a'":""%>><%=columnList.get(i).getName()%></a></li>
           <% }}%>
        </ul>
    </div>
    <div id="detail" >
        <div class="cms_title"  >
            公告和新闻
        </div>

        <div class="cms_Info">
            <% if(articles!=null){
                for (ArticleWVO article : articles) {
            %>
            <div class="cms_content about_cont">
         <a href="javascript:void(0)" onclick="loadArticle('<%=article.getId()%>')"> <h5> <%=article.getTitle()%></h5></a>
            <div class="publicTime"><%=article.getPublishedTime()==null?"":article.getPublishedTime()%></div>
            <p><%=article.getContent()%></p>
            </div>
            <%}
            }%>
        </div>
    </div>

    <!--- 这是文章详情-->
    <div class="news_detail" id="new_detail_id">
        <div class="cms_title_detail about_cont2"  >
            <h5><label id="news_title"></label></h5>
            <p id="news_time" style="font-size: 12px;"></p>
        </div>
         <div class="about_cont" style="width: 815px;margin: 0 auto">
            <p>
                <label id="news_content"></label>
            </p>

         </div>


    </div>
</div>
<div style="height: 100px;"></div>

<jsp:include page="/w2/bottom.jsp"/>
</body>
</html>
<script src="<%=Config.getWebRoot()%>/w2/js/modules/cms/guidance.js"></script>