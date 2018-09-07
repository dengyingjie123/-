<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2014/12/3
  Time: 13:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String token = request.getParameter ("token");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>Untitled Document</title>
</head>
<body>

<div style="padding:5px;">

	<div class='easyui-panel' title='查询' iconCls='icon-search'>
		<table border="0" cellpading="3" cllspacing="0">
			<tr>
				<td>发送者</td>
				<td><input type="text" id="search_SenderId<%=token%>" name="message.SenderId" style="width:140px"/>
				</td>
				<td>主题</td>
				<td><input type="text" id="search_Subject<%=token%>" name="message.Subject" style="width:140px"/>
				</td>
				<td>内容</td>
				<td><input type="text" id="search_Content<%=token%>" name="message.Content" style="width:183px"/>
				</td>
				<td><a id="btn_SearchMessage<%=token%>" href="javascript:void(0)" class="easyui-linkbutton"
				       iconCls="icon-search">查询</a></td>
				<td><a id="btn_ResetSearchMessage<%=token%>" href="javascript:void(0)" class="easyui-linkbutton"
				       iconCls="icon-cut">重置</a></td>

			</tr>
			<tr>
				<td>状态</td>
				<td><input type="text" id="search_IsRead<%=token%>" name="message.IsRead" class="easyui-combotree"
				           editable="false" style="width:140px"/></td>
				<td>类型</td>
				<td><input type="text" id="search_Type<%=token%>" name="message.Type" class="easyui-combotree"
				           editable="false" style="width:140px"/></td>
				<td>开始时间</td>
				<td><input type="text" class="easyui-datebox" name="message.FromTime" id="search_FromTime<%=token%>"
				           style="width:140px" editable="false"/></td>
				<td>结束时间</td>
				<td><input type="text" class="easyui-datebox" name="message.ReceiveTime"
				           id="search_ReceiveTime<%=token%>" style="width:140px" editable="false"/></td>
			</tr>

		</table>

	</div>
	<br/>
	<table id="messageShowTable<%=token%>"></table>

</div>

</body>
</html>
