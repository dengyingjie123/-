<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page import="com.youngbook.entity.wvo.production.ProductionWVO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>新手指引</title>

    <link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/about/about.css" rel="stylesheet" type="text/css"/>

    <script language="javascript" src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>

</head>

<body>


<jsp:include page="/w2/top.jsp"/>
<%--导航栏--%>
<div style="width: 100%;background: #fafafa;" >
    <div id="nav" class="w1200" style="padding-top: 7px;padding-bottom: 7px;">
        <a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex"><img src="<%=Config.getWebRoot()%>/w2/img/logo_Assistor.png" alt="开普乐"/></a>
        <span>
          <a id="param1" href="<%=Config.getWebRoot()%>/w2/index/ShowIndex" class="item" >首页</a>
        <a id="param2" href="<%=Config.getWebRoot()%>/w2/modules/production/productionList.jsp" class="item">投资专区</a>

          <a id="param3" href="<%=Config.getWebRoot()%>/w2/modules/about.jsp" style="color:#d28d2a" >新手指引</a> |
            <a id="param4" href="<%=Config.getWebRoot()%>/w2/customer/IndexShow" >我的账户</a>
        </span>
    </div>
</div>


<div class="w1200" id="container">

<div class="bg_top" >
</div>
<div class="font-40p"><img src="<%=Config.getWebRoot()%>/w2/img/newcomer_item1.png" align="center">&nbsp;&nbsp;为什么选择点金派 </div>

<div class="font-24pk" style="margin: 0 auto">

</div>
<div class="font-40p"><img src="<%=Config.getWebRoot()%>/w2/img/newcomer_item2.png" align="center">&nbsp;&nbsp;购买流程 </div>

<table cellpadding="10" cellspacing="0" align="center" style="width: 840px;">
    <tr>
        <td><img src="<%=Config.getWebRoot()%>/w2/img/guide_item1.png"></td>
        <td><img src="<%=Config.getWebRoot()%>/w2/img/guide_item2.png"></td>
    </tr>
    <tr>
        <td><img src="<%=Config.getWebRoot()%>/w2/img/guide_item3.png"></td>
        <td><img src="<%=Config.getWebRoot()%>/w2/img/guide_item4.png"></td>
    </tr>

</table>
    <div class="font-40p"><img src="<%=Config.getWebRoot()%>/w2/img/newcomer_item3.png" align="center">&nbsp;&nbsp;开普乐的优势 </div>
    <table cellpadding="0" cellspacing="0" align="center" class="table_td">
        <tr>
            <td><img src="<%=Config.getWebRoot()%>/w2/img/icon_good1.png">
            稳健
            </td>
            <td/>
            <td><img src="<%=Config.getWebRoot()%>/w2/img/icon_good2.png">信赖</td>
            <td/>
            <td><img src="<%=Config.getWebRoot()%>/w2/img/icon_good3.png">梦想</td>
        </tr>
        <tr>
            <td>
            <td/>
                        <img src="<%=Config.getWebRoot()%>/w2/img/icon_good4.png">专业
                        </td>
            <td/>
                        <td>
                       <img src="<%=Config.getWebRoot()%>/w2/img/icon_good5.png">成长
                     </td>
            <td/>
                </tr>
                </table>

            </td>
        </tr>

    </table>
</div>
<div style="height:60px; "></div>
<jsp:include page="/w2/bottom.jsp"/>

</body>
</html>
