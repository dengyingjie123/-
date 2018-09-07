<%--
  Created by IntelliJ IDEA.
  User: yux
  Date: 2016/6/29
  Time: 11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>

<%
    String token = request.getParameter("token");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
</head>

<body>
<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding-left:5px;padding-right:5px;padding-bottom:5px;background:#fff;border:1px solid #ccc;">
        <br>
        项目名称：<span id="projectName<%=token %>"/> <br><br>
        产品名称：<span id="productionHomeName<%=token %>"/><br><br>
        <span id="production<%=token %>">产品分期名称：<span id="productName<%=token %>"/><br><br></span>
        签约状态&nbsp; &nbsp; 空白：<span id="totalUnsignContract<%=token %>"/> &nbsp; &nbsp; 已签约：<span
            id="totalSignedContract<%=token %>"/> &nbsp; &nbsp; 作废：<span id="totalCanceledContract<%=token %>"/><br><br>
        金额相关&nbsp; &nbsp; 产品额度：<span id="size<%=token %>"/> &nbsp; &nbsp; 已签约额度：<span id="saleMoney<%=token %>"/> &nbsp;
        &nbsp; 签约比例：<span id="salePercent<%=token %>"/><br><br>
        <table id="ContractAbstractTable<%=token%>"></table>
    </div>

    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('ContractAbstractByProductionWindow<%=token%>')" id="btnCotractAbstractCancel<%=token %>">取消</a>
    </div>
</div>
</body>
</html>
