<jsp:include page="../checkLogin.jsp"></jsp:include>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.Iterator" %>


<%
//根据ProcessDef.jsp页面得到的参数WorkflowID作为所要查询的工作流编号查询出此编号的工作流信息
 ProcessInfo processInfo = new ProcessInfo();
 int intID = Integer.parseInt(request.getParameter("WorkflowID"));
 processInfo.setID(intID);
 List listDef = processInfo.searchObject();
 Iterator itDef = listDef.iterator();
 if(itDef.hasNext()){
   processInfo = (ProcessInfo)itDef.next();
 }
%>

<html>
<head>
<script language="javascript">
//根据业务数据修改历史判断是否记录用户动作
function checkSelect(){
 if(form.SaveAction.value == "false"){
    form.SaveHistory.value = "false";
 }
}
</script>
<style type="text/css">
<!--
.readonlyStyle {
	background-color: #CCCCCC;
}
-->
</style>
<title>修改工作流基本信息页面</title>
<link href="../style/common.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></head>
<body>
<form name="form" action="ProcessModDone.jsp" method="post">
	<p align="center">
		<font style="font-size:12pt "><strong>修改工作流基本信息</strong></font>
	</p>
   <table width="450" class="TabledStyle"  border=0 align="center" cellpadding=0 cellspacing=1>
              <tr class="TableBgStyle">
                <td width="250" height="24" align="right">工作流编号：</td>
                <td width="283" height="24" align="left" class="TableTdBgStyle"><input readonly type="text" name="ID" id="ID" style="WIDTH:100pt" class="TextStyle" value="<%=processInfo.getID()%>"></td>
              </tr>
              <tr class="TableBgStyle">
                <td height="24" align="right">工作流名称：</td>
                <td height="24" align="left" class="TableTdBgStyle"><input name="Name" type="text" class="TextStyle" id="Name" style="WIDTH:100pt" value="<%=processInfo.getName()%>">
    　                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         </td>
              </tr>
              <tr class="TableBgStyle">
                <td height="24" align="right">工作流说明：</td>
                <td height="24" align="left" class="TableTdBgStyle"><input name="Info" type="text" class="TextStyle" id="Info" style="WIDTH:100pt" value="<%=processInfo.getInfo()%>"></td>
              </tr>
              <tr class="TableBgStyle">
                <td height="23" align="right">创建日期：</td>
                <td height="23" align="left" class="TableTdBgStyle"><input name="CreateDate" type="text" class="TextStyle" id="CreateDate" style="WIDTH:100pt" value="<%=processInfo.getCreateDate()%>"></td>
              </tr>
              <tr class="TableBgStyle">
                <td height="25" align="right">是否记录用户动作：</td>
                <td height="25" align="left" class="TableTdBgStyle"><select name="SaveAction" class="TextStyle" id="SaveAction" style="WIDTH:100pt" onchange="checkSelect()">

                    <option value="true" <%=processInfo.getSaveAction()?"selected":""%>>是</option>
                    <option value="false" <%=!processInfo.getSaveAction()?"selected":""%>>否</option>
                </select></td>
              </tr>
              <tr class="TableBgStyle">
                <td height="27" align="right">是否记录业务数据修改历史：</td>
                <td align="left" class="TableTdBgStyle"><select name="SaveHistory" class="TextStyle" id="SaveHistory" style="WIDTH:100pt">
                    <option value="true" <%=processInfo.getSaveHistory()?"selected":""%>>是</option>
                    <option value="false" <%=!processInfo.getSaveHistory()?"selected":""%>>否</option>
                </select></td>
              </tr>
            </table>
        <div align="center"><br>
          <input type="submit" name="query" value="确定" style="WIDTH:60pt"   class="ButtonStyle">
          <input type="reset"  name="reset"  value="重写" class="ButtonStyle" style="WIDTH:60pt" >
          <br>
        </div>
</form>

<BR>
</body>
</html>

