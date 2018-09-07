<jsp:include page="../checkLogin.jsp"></jsp:include>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.Iterator" %>


<%
/**从ProcessDef.jsp页面得到WorkflowID参数，根据此参数查询出相应的工作流信息，
* 通过执行ProcessInfo.java中的Delete()方法删除此工作流信息
* 删除成功返回1，失败则返回失败信息
*/
String strInfo = new String();
String strActions = new String();
try {
  ProcessInfo processInfo = new ProcessInfo();
  int intID = Integer.parseInt(request.getParameter("WorkflowID"));
  processInfo.setID(intID);
  List listDef = processInfo.searchObject();
  Iterator itDef = listDef.iterator();
  if(itDef.hasNext()){
    processInfo = (ProcessInfo)itDef.next();
  }
  processInfo.setID(intID);
  int intResult = processInfo.Delete();
  if(intResult == 1){
    strInfo = "删除成功!";
    strActions = "<a href='ProcessDef.jsp'>继续</a>";
  }
}
catch(Exception e){
  strInfo = "删除失败，信息如下："+e.getMessage();
  strActions = "<a href='javascript:history.back();'>返回</a>";
}
%>
<link href="../style/common.css" rel="stylesheet" type="text/css">
<table width="350"  border="0" align="center" cellpadding="0" cellspacing="0" id="bg">
  <tr>
    <td><table width="100%"  border="0" cellpadding="0" cellspacing="1" class="TableBorderStyle">
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
