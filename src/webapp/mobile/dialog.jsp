<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<style>
    body{
        font-size: 0.75em;
    }
</style>
<jsp:include page="./include/_head.jsp"></jsp:include>
<body>

    <div data-role="page" data-dialog="true">
		<div data-role="header" data-theme="b">
		<h1>信息提示</h1>
		</div>

		<div role="main" class="ui-content">
			<h3>预定成功！</h3>
			<div class="text-right">
                <a href="detail" data-rel="back" class="ui-btn ui-shadow ui-corner-all ui-btn-a btn btn-success">确定</a>
                <a href="detail" data-rel="back" class="ui-btn ui-shadow ui-corner-all ui-btn-a btn">取消</a>
            </div>
		</div>
	</div>

</body>