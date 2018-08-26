<jsp:include page="../checkLogin.jsp"></jsp:include>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.Iterator" %>


<%
//从ProcessModify.jsp页面得到各个参数，并利用ProcessInfo.java中的方法设置各个字段
String strInfo = new String();
String strActions = new String();
try {
 ProcessInfo processInfo = new ProcessInfo();

 boolean bolSaveAction = Boolean.valueOf(request.getParameter("SaveAction")).booleanValue();
 boolean bolSaveHistory = Boolean.valueOf(request.getParameter("SaveHistory")).booleanValue();
 processInfo.setID(Integer.parseInt(request.getParameter("ID")));
 processInfo.setName(Tools.toUTF8(request.getParameter("Name")));
 processInfo.setInfo(Tools.toUTF8(request.getParameter("Info")));
 processInfo.setCreateDate(Tools.toUTF8(request.getParameter("CreateDate")));
 processInfo.setSaveAction(bolSaveAction);
 processInfo.setSaveHistory(bolSaveHistory);

if (!bolSaveAction){
   if(bolSaveHistory){
	   strInfo = "如果需要记录业务数据修改历史,则记录用户动作必须为是!";
		 strActions = "<a href='javascript:history.back();'>返回</a>";
   }
   else if(processInfo.update() == 1){
	   strInfo = "修改成功!";
		 strActions = "<a href='ProcessDef.jsp'>继续</a>";
   }
 }
 else if(processInfo.update() == 1){
   strInfo = "修改成功!";
   strActions = "<a href='ProcessDef.jsp'>继续</a>";
 }
}
catch(Exception e){
  strInfo = "修改失败，信息如下："+e.getMessage();
	strActions = "<a href='javascript:history.back();'>返回</a>";
}
%>
<link href="../style/common.css" rel="stylesheet" type="text/css">
<table width="350"  border="0" align="center" cellpadding="0" cellspacing="0" class="TableBorderStyle" id="bg">
  <tr>
    <td><table width="100%"  border="0" cellspacing="1" cellpadding="0">
        <tr>
          <td height="23" class="TableTdStyle">系统提示:</td>
        </tr>
        <tr>
          <td class="TableBgStyle"><div align="center"><br>
              <%=strInfo%><br>
              <br>
              [ <%=strActions%> ] <br>
              <br>
          </div></td>
        </tr>
    </table></td>
  </tr>
</table>
