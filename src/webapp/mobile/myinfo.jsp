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
        $('#topHeadText').html('我的信息');
    });
</script>
<div data-role="page" id="info" data-add-back-btn="true">
	<jsp:include page="./include/_topNav.jsp"></jsp:include>	
	<div role="main" class="ui-content">
       <form action="" method="post">
           <div class="ui-field-contain">
                <label for="password">原密码</label>
                <input type="password" name="oldpassword" id="old-password" placeholder="请输入原密码！" value="">
                <label for="password">新密码</label>
                <input type="password" name="newpassword" id="new-password" placeholder="请输入新密码！" value="">
                <label for="password">重复密码</label>
                <input type="password" name="confirmpassword" id="confirm-password" placeholder="请输入重复新密码！" value="">
           </div>
           <button type="submit" class="btn" style="color: #fff;background-color: #5cb85c;border-color: #4cae4c;text-shadow: 0px 0px;">确定</button>
        </form>
	</div>
	<jsp:include page="./include/_panel.jsp"></jsp:include>
</div>