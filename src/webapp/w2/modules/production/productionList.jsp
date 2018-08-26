<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    CustomerPersonalPO loginUser = null;
    if (request.getSession().getAttribute("loginUser") != null) {
        loginUser = (CustomerPersonalPO) request.getSession().getAttribute("loginUser");
    }
%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv=“X-UA-Compatible” content=“IE=8″>
    <title>点金派</title>
    <link href="../../css/common.css" rel="stylesheet" type="text/css"/>
    <link href="../../css/index/index.css" rel="stylesheet" type="text/css"/>
    <link href="../../css/production/production.css" rel="stylesheet" type="text/css"/>

    <script src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/plus/easypiechart/jquery.easypiechart.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/plus/formatCurrency/jquery.formatCurrency.js"></script>

    <script src="<%=Config.getWebRoot()%>/w2/js/common/fweb.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/modules/production/productionList.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/c.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/justgage.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/raphael-2.1.4.min.js"></script>
</head>

<body>
<div id="container">
    <jsp:include page="/w2/top.jsp"/>

    <%--导航栏--%>
    <div style="width: 100%;background: #fafafa;" >
        <div id="nav" class="w1200" style="padding-top: 7px;padding-bottom: 7px;">
            <a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex"><img src="<%=Config.getWebRoot()%>/w2/img/logo_Assistor.png" alt="开普乐"/></a>
        <span>
          <a id="param1" href="<%=Config.getWebRoot()%>/w2/index/ShowIndex" class="item" >首页</a>
        <a id="param2" href="<%=Config.getWebRoot()%>/w2/modules/production/productionList.jsp"style="color: #d28d2a;padding-bottom: 28px;border-bottom: 2px solid #d28d2a;" >投资专区</a>

          <a id="param3" href="<%=Config.getWebRoot()%>/w2/modules/about.jsp" >新手指引</a> |
            <a id="param4" href="<%=Config.getWebRoot()%>/w2/customer/IndexShow" >我的账户</a>
        </span>
        </div>
    </div>


    <div class="position r">
        <a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex">首页</a>
        >
        <a href="#">产品列表</a>
    </div>
    <div id="productions" class="w1200">

        <div id="tableTitle">推荐列表</div>
        <div id="productionList">

        </div>
        </div>


        <%--<div id="tableDetail">--%>
            <%--<table class="w1200" cellpadding="0" cellspacing="0">--%>


                <%--&lt;%&ndash;<tbody id="productionList">&ndash;%&gt;--%>
                <%--&lt;%&ndash;&lt;%&ndash;js加载产品列表&ndash;%&gt;&ndash;%&gt;--%>
                <%--&lt;%&ndash;<tr><td colspan='7' class='otherLine'>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<div id="load">&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<div class="icon_load">&ndash;%&gt;--%>
                    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                     <%--&lt;%&ndash;<label class="load_title">加载中</label>&ndash;%&gt;--%>
                 <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                <%--&lt;%&ndash;</td>&ndash;%&gt;--%>
                <%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
                <%--&lt;%&ndash;</tbody>&ndash;%&gt;--%>
            <%--</table>--%>
        <%--</div>--%>

    </div>


    <div class="pagelist">
        <nav>
            <ul class="pagination" id="pagination">
                <li><a href='javascript:void(0);' aria-label='Previous' style="color:#000000;"><span aria-hidden='true'>上一页</span></a>
                </li>
                <li class='active'><a href='javascript:void(0);' style="color:#000000;">1</a></li>
                <li><a href='javascript:void(0);' aria-label='Next' style="color:#000000;"><span
                        aria-hidden='true'>下一页</span></a></li>
            </ul>
        </nav>
    </div>
 
    <jsp:include page="/w2/bottom.jsp"/>
</div>


</body>
</html>