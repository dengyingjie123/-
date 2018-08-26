<%@ page import="com.youngbook.common.ReturnObject" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.StringUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%


%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv=“X-UA-Compatible” content=“IE=8″>
<title>银行卡更换提示</title>

<link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>
<link href="<%=Config.getWebRoot()%>/w2/css/index/info.css" rel="stylesheet" type="text/css"/>


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

          <a id="param3" href="<%=Config.getWebRoot()%>/w2/modules/about.jsp" >新手指引</a> |
            <a id="param4" href="<%=Config.getWebRoot()%>/w2/customer/IndexShow" style="color:#d28d2a">我的账户</a>
        </span>
       </div>
   </div>

    <div style="text-align: center; padding: 30px 0px 0px 0px;">

 		<img src="<%=Config.getWebRoot()%>/w2/img/new_bankcard_procedure.jpg" />

    </div>

   <jsp:include page="/w2/bottom.jsp"/>

</body>
</html>
