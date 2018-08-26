<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
 * 程序：李升华
 * 时间：2004-12-16
 * 说明：上传附件页面
 */
%>
<%@ page import="com.ydtf.dmis.jxp.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.Iterator" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.File" %>
<%@ page import="java.lang.*" %>

<%
String strWorkflowID = request.getParameter("WorkflowID");
String strYWID = request.getParameter("YWID");
String strAddress = request.getParameter("Address");

//根据业务编号得到上传的文件名
  FileLoad fileLoad = new FileLoad();
  int intID = 0;
  String strFileName = "";
  fileLoad.setYWID(strYWID);
  fileLoad.setAddress(strAddress);
  List listFile = fileLoad.query();
  Iterator itFile = listFile.iterator();
  int intCounter = 0;

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>上传附件页面</title>
</head>
<script src="../script/common.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
//若能查询得到附件则显示附件一览表
function init(){
	<%if(itFile.hasNext()) {%>
	table.style.display="";
<%}%>

	now = new Date();
 	date = now.getDate();
	month = now.getMonth()+1;
 	year = now.getYear();
	strday = year + "-" + month + "-" + date;
	form.Time.value = strday;
}

//删除已经存在的附件
function deleteFile(){
	if(confirm("您确定要删除此附件？")){
  	form.action = "delete_done.jsp";
	form.target = "_self";
	form.submit();
  }
}
function window_close() {
//刷新本单位审核页面，使得盖页面可以显示所上传的文件
  var workflowID = "<%=strWorkflowID%>";
	var ywid = "<%=strYWID%>";
  opener.location.reload();
	window.close();
}
</script>
<body onLoad="init()">
<form action="uploadFile_done.jsp" method="post" name="form" target="_blank">
<p align="center">添加（删除）附件</p>
<table width="421"  border=0 align=center cellpadding=0 cellspacing=0>
<tr><td width="466" height=25 align=center><input type="file" name="file">
</td>
</tr>
<tr><td height="25"></td></tr>
<tr>
  <td align=center height=25><input type="submit" name="button" value="上传附件">
    <input type="button" name="Button" value="取消" onClick="window_close()"></td>
</tr></table><br><br>
<table name="table" id="table" cellSpacing=0 cellPadding=0 width="50%" align=center border=1 bordercolor=#CCCCFF style="display:none ">
      <tr><td colspan=2 align=center style="line-height:1.5"><font color=blue>附件一览表</font></td></tr>
      <tr bgcolor=#70a6ff align=center style="color:white">
			 <td width=20% align=center nowrap>选择</td><td width=80% align=center nowrap>附件名称</td></tr>
<%
	while(itFile.hasNext()){
		fileLoad = (FileLoad)itFile.next();
		strFileName = fileLoad.getFileName();
		intID = fileLoad.getID();
		intCounter++;
%>
	<tr bgColor=#f2f2f2>
	 <td align=center nowrap><input type='radio' name='FJName' id="FJName<%=intCounter%>" checked value='<%=intID%>' BACKGROUND-COLOR='#ffffff' ></td>
		<td align=center nowrap><%=strFileName%></td>
    </tr>
<%
	}
%>

<tr><td colspan="2"><br>
<div align="center">
<input type="button" name="button" value="删除附件" onClick="deleteFile()" >
<input type="button" name="close" value="关闭窗口" onClick="window_close()">
</div>
</td></tr>
</table>
<input type="hidden" name="WorkflowID" value="<%=strWorkflowID%>">
<input type="hidden" name="YWID" value="<%=strYWID%>">
<input type="hidden" name="Address" value="<%=strAddress%>">
<input type="hidden" name="Time">
</form>
</body>
</html>
