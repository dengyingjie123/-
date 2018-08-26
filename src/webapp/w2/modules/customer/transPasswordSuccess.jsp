<%@ page import="com.youngbook.common.ReturnObject" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.StringUtils" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	// 判断用户是否登陆
	CustomerPersonalPO loginUser = null;
	if (request.getSession().getAttribute("loginUser") != null) {
		loginUser = (CustomerPersonalPO) request.getSession().getAttribute("loginUser");
	} else {
		String url = Config.getWebRoot() + Config.Web_URL_Login;
		response.sendRedirect(url);
		return;
	}
	String success = "";
	String notLogin = "";
	String resetPasswordSUCCESS = "";
	ReturnObject returnObject = new ReturnObject();
	if (request.getAttribute("returnObject") != null) {
		returnObject = (ReturnObject) request.getAttribute("returnObject");
	}
	if (returnObject.getMessage() == "注册成功") {
		success = returnObject.getMessage();
	}
	if (returnObject.getMessage() == "未登录") {
		notLogin = returnObject.getMessage();
	}
	if (returnObject.getMessage() == "重置登录密码成功") {
		resetPasswordSUCCESS = returnObject.getMessage();
	}
	if (StringUtils.isEmpty(returnObject.getMessage())) {
		returnObject.setMessage("出现未知错误，请重试！");
	}

%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv=“X-UA-Compatible” content=“IE=8″>
	<title>提示</title>

	<link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>
	<link href="<%=Config.getWebRoot()%>/w2/css/index/info.css" rel="stylesheet" type="text/css"/>

	<script language="javascript" src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>


	<script src="<%=Config.getWebRoot()%>/w2/dist/js/bootstrap.min.js"></script>
	<script src="<%=Config.getWebRoot()%>/w2/js/common/c.js"></script>
	<script src="<%=Config.getWebRoot()%>/w2/dist/plus/formatCurrency/jquery.formatCurrency.js"></script>
	<script src="<%=Config.getWebRoot()%>/w2/js/framework/HopeAlert.js"></script>
	<script src="<%=Config.getWebRoot()%>/w2/js/common/fweb.js"></script>

</head>

<body>


<%--top--%>
<jsp:include page="/w2/top.jsp"/>
<%--top 结束--%>
<div style="width: 100%;background: #fafafa;" >
    <div id="nav" class="w1200" style="padding-top: 7px;padding-bottom: 7px;">
        <a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex"><img src="<%=Config.getWebRoot()%>/w2/img/logo_Assistor.png" alt="开普乐"/></a>
        <span>
          <a id="param1" href="<%=Config.getWebRoot()%>/w2/index/ShowIndex" class="item" >首页</a>
        <a id="param2" href="<%=Config.getWebRoot()%>/w2/modules/production/productionList.jsp" class="item" >投资专区</a>

          <a id="param3" href="<%=Config.getWebRoot()%>/w2/modules/about.jsp" >新手指引</a> |
            <a id="param4" href="<%=Config.getWebRoot()%>/w2/customer/IndexShow" style="color:#d28d2a">我的账户</a>
        </span>
    </div>
</div>

<div style="margin: 0 auto;background: #fafafa;padding: 150px 30px 150px 30px;width: 940px;border: #dcdcdc 1px solid;text-align: center;">
	<div class="pay-pass">
		<div class="ht">设置交易密码结果</div>
		<%
			if (loginUser != null) {
		%>
		<div class="content">
			<form class="form-horizontal" action="" method="post">
				<div class="form-group">
					<div>
						<br /><br /><br /><br /><br /><br />
						设置成功，请牢记您的交易密码，感谢您的支持！<br /><br />
						我们将在<span id="minutes">数</span>秒后为您跳转到 <a href="<%=Config.getWebRoot()%>/w2/modules/customer/account.jsp">账户管理</a> 页面。
						<br /><br /><br /><br /><br /><br />
					</div>
				</div>
			</form>
		</div>
		<%
		} else {
		%>
		<div class="content">
			<a href="<%=Config.getWebRoot()%>/w2/customer/LoginRequest">未登录</a>
		</div>
		<%
			}
		%>
	</div>
</div>
</div>


<jsp:include page="/w2/bottom.jsp"/>
<script type="text/javascript">
	// 3 秒后跳转
	var i = 3;
	var intervalid;
	intervalid = setInterval("fun()", 1000);
	function fun() {
		if (i == 0) {
			window.location.href = "/core/w2/modules/customer/account.jsp";
			clearInterval(intervalid);
		}
		document.getElementById("minutes").innerHTML = "&nbsp;" + i + "&nbsp;";
		i--;
	}



</script>
</body>

<script src="<%=Config.getWebRoot()%>/w2/dist/plus/easypiechart/jquery.easypiechart.min.js"></script>
</html>
