<%
/**
 * 程序：李升华
 * 时间：2004-10-28
 * 说明：上传附件成功页
 */
%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.Iterator" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.File" %>
<%@ page import="java.lang.*" %>

<%
try {
  String strUserName = (String)session.getAttribute("xingming");
  strUserName = strUserName.trim();
  int intWorkflowID = 0;
  String strYWID = "";
  String strAddress = "";
  String strTime = "";
  //得到工作流编号
  if(!request.getParameter("WorkflowID").equals("null")){
  	intWorkflowID = Integer.parseInt(request.getParameter("WorkflowID"));
  }
  //得到业务编号
  if(!request.getParameter("YWID").equals("null")){
  	strYWID = (String)request.getParameter("YWID");
  }
  //得到上传时间
  if(!request.getParameter("Time").equals("null")){
  	strTime = (String)request.getParameter("Time");
  }
  //得到上传要放置的字段
  if(!request.getParameter("Address").equals("null")){
  	strAddress = (String)request.getParameter("Address");
  }

//将上传附件的信息写入数据库中
  String strFilePath = "";
  String strFileName = "";
  String strFileType = "";
  int intID = 0;

FileLoad fileLoad = new FileLoad();
strFilePath = request.getParameter("file");
File file = new File(strFilePath);
strFileName = Tools.toUTF8(file.getName());


int intIndex = strFilePath.indexOf('.') + 1;
strFileType = strFilePath.substring(intIndex);

intID = fileLoad.getMaxID();

fileLoad.setID(intID);
fileLoad.setFileType(strFileType);
fileLoad.setFileName(strFileName);
fileLoad.setFilePath(Tools.toUTF8(strFilePath));
fileLoad.setWorkflowID(intWorkflowID);
fileLoad.setYWID(strYWID);
fileLoad.setTime(Tools.getTime());
fileLoad.setAddress(strAddress);
fileLoad.setUserName(strUserName);
int intResult = -1;
//intResult = fileLoad.delete();
intResult = fileLoad.Insert();
if (intResult == 1) {
%>
<script language="javascript" type="text/javascript">
window.alert("附件上传成功！");
opener.window_close();
window.close();
</script>
<%
  }
}
catch (Exception e) {
  //异常处理
	%>
	<script language="javascript" type="text/javascript">
	window.alert("上传文件失败！");
	window.close();
	</script>
	<%
/*
 out.print("增加或刷新失败，信息如下："+e.getMessage());
 out.print("<a href='javascript:history.back();'>返回</a>");
 */
}
%>
