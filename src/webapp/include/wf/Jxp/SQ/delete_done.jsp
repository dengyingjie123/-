<%
/**
 * 程序：李升华
 * 时间：2004-10-28
 * 说明：删除附件成功页
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
	String strWorkflowID = request.getParameter("WorkflowID");
	String strYWID = request.getParameter("YWID");
	
  int intID = 0;
  if(!request.getParameter("FJName").equals("null")){ 
  	intID = Integer.parseInt(request.getParameter("FJName"));
  }

  FileLoad fileLoad = new FileLoad();
  fileLoad.setID(intID);

int intResult = -1;
intResult = fileLoad.delete();
  if (intResult == 1) {
%>
<jsp:forward page="uploadFile.jsp?WorkflowID=<%=strWorkflowID%>&YWID=<%=strYWID%>"></jsp:forward>
<%
  }else{
%>
<html>
<head>
<title>删除附件</title>
<link href="../style/common.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

</head>
<body>
<link href="../style/common.css" rel="stylesheet" type="text/css">
<form name="form">
<table width="400"  border="0" align="center" cellpadding="0" cellspacing="0" id="bg">
  <tr>
    <td><table width="106%"  border="0" cellpadding="0" cellspacing="1" class="TableBorderStyle">
        <tr>
          <td height="23" class="TableTdStyle">系统提示:</td>
        </tr>
        <tr>
          <td class="TableBgStyle"><div align="center"><br>
  删除失败，请查找原因！<br>
              <br>
             [ <a href="javascript:history.back()">返回</a> ] <br>
              <br>
			  
          </div></td>
        </tr>
    </table></td>
  </tr>
</table>
</form>
</body>

</html>
<%
  }

}catch(Exception e){
}

%>
