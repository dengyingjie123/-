<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<jsp:include page="./include/_head.jsp"></jsp:include>
<style type="text/css">
    .pinfo{
        font-size: 1em;
    }
</style>
<script type="text/javascript">
    $(function(){
        $('#topHeadText').html('用户登录');
    });
    function login(){
       var userName= $("#account").val();
        window.location.href='mobile/checkLogin.action?logUser.account='+userName;
    }
</script>
<div data-role="page" id="info" data-add-back-btn="true">
	<jsp:include page="./include/_topNav.jsp"></jsp:include>	
	<div role="main" class="ui-content">
       <form action="mobile/checkLogin.action" method="post">
           <div class="ui-field-contain">
                <label for="account">用户名</label>
                <input type="text" name="logUser.account" id="account" placeholder="请输入用户名！" value="HW00125">
                <label for="password">密码</label>
                <input type="password" name="logUser.password" id="password" placeholder="请输入密码！" value="">
           </div>
           <button type="button" onclick="javascript:login()"  class="btn" style="color: #fff;background-color: #5cb85c;border-color: #4cae4c;text-shadow: 0px 0px;">登录</button>
        </form>
	</div>
	<jsp:include page="./include/_panel.jsp"></jsp:include>
</div>