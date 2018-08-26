<jsp:include page="../checkLogin.jsp"></jsp:include>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.Iterator" %>


<%
//从ProcessAdd.jsp页面得到新增工作流的各个参数
String strInfo = new String();
String strActions = new String();
try {

 ProcessInfo processInfo = new ProcessInfo();
 int intID = Integer.MAX_VALUE;
 boolean bolSaveAction = Boolean.valueOf(request.getParameter("SaveAction")).booleanValue();
 boolean bolSaveHistory = Boolean.valueOf(request.getParameter("SaveHistory")).booleanValue();

 //若工作流编号为空或是已经存在，则抛出异常信息
 String strID = request.getParameter("ID");
   try {
	processInfo.setID(Integer.parseInt(strID));
	} catch (Exception e1){}

//将页面上得到的各个参数写入XML中
 processInfo.setName(Tools.toUTF8(request.getParameter("Name")));
 processInfo.setInfo(Tools.toUTF8(request.getParameter("Info")));
 processInfo.setCreateDate(Tools.toUTF8(request.getParameter("CreateDate")));
 processInfo.setSaveAction(bolSaveAction);
 processInfo.setSaveHistory(bolSaveHistory);

//根据业务数据修改历史的记录判断是否记录用户动作
 if (!bolSaveAction){
   if(bolSaveHistory){
	   strInfo = "如果需要记录业务数据修改历史,则记录用户动作必须为是!";
		 strActions = "<a href='javascript:history.back();'>返回</a>";
}
   else if(processInfo.insert() == 1){
	   strInfo = "增加成功！";
		 strActions = "<a href='ProcessDef.jsp'>继续</a>";
   }
 }
 else if(processInfo.insert() == 1){
   strInfo = "增加成功！";
	 strActions = "<a href='ProcessDef.jsp'>继续</a>";
 }
}
catch (Exception e) {
  strInfo = "增加失败,信息如下:"+e.getMessage();
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
