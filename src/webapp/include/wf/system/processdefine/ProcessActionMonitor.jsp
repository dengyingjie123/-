<jsp:include page="../checkLogin.jsp"></jsp:include>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import = "java.util.*" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%
int intWorkflowID = Integer.parseInt(request.getParameter("WorkflowID"));
List listYWID = ClientApplications.getYWID(intWorkflowID);

String strYWID = new String();
//记录业务编号
if (request.getParameter("YWID")!=null&&!request.getParameter("YWID").equals("")){
  strYWID=request.getParameter("YWID");
  List listap=ClientApplications.getActionReport(intWorkflowID,strYWID);
  Iterator itap=listap.iterator();
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>工作流监控</title>
</head>
<script language="javascript">
function History(){
  if (Action.DefActionID.value=="")
  {
    alert("请选择一流转记录！");
  }
  else
  {
    showModalDialog("BizDataHistory.jsp?WorkflowID="+Action.WorkflowID.value+"&ActionID="+Action.DefActionID.value+"&YWID="+Action.YWID.value,"","dialogHeight:600px;dialogWidth:700px;dialogTop:150px; dialogLeft:200px;help:no;scroll:yws;status:no;");
  }
}
//判断表单是否做了唯一选择
function doSelect(intCounter) {
  Action.DefActionID.value=intCounter;
  document.all("ActionID" + intCounter).checked = true;
}
function deleteYW(form) {
  if (form.YWID.value != "") {
		if (confirm("是否确定删除此业务？"+form.YWID.value)) {
			form.action = "ProcessDeleteYW.jsp";
			form.submit();
		}
	}
	else {
	  window.alert("没有选择业务编号！");
	}
}
</script>
<link href="../style/common.css" rel="stylesheet" type="text/css">
<body>

  <center>
  <strong style="font-size:12pt">工作流流转监控</strong><form action="ProcessActionMonitor.jsp" method="POST">
<table class="TabledStyle" width=700 border=0 align="center" cellpadding=0 cellspacing=1>
             <tr >
              <td  class="TableBgStyle" height="25"><div align="center">
                工作流编号：
              </div></td>
              <td  class="TableBgStyle"><%=intWorkflowID %></td>
              <td class="TableBgStyle" align="center">工作流名称：</td>
              <td  class="TableBgStyle" align="center"><div align="center"><%=ProcessInfo.getWorkflwoName(intWorkflowID) %></div></td>
              <td   class="TableBgStyle" align="center"><div align="center">选择业务编号</div></td>
              <td  class="TableBgStyle"><div align="center"><select name="YWID">
			  <%for (int i=0;i<listYWID.size();i++){

				int intYWID=Integer.parseInt((String)listYWID.get(i));
				%>
				<option value="<%=intYWID%>" <%=strYWID.equals(String.valueOf(intYWID))?"selected":""%>><%=intYWID%></option>
				<%
			  }	%>
			  </select></div></td>
              <td  class="TableBgStyle"><div align="center"><input name="Submit" type="submit" class="ButtonStyle"  value="流转记录">
                <input name="Button" type="button" class="ButtonStyle" value="删除业务" onClick="deleteYW(this.form)">
              </div></td>
            </tr>

</table>
<input type="hidden" name="WorkflowID" value=<%=intWorkflowID %>>
</form>

<%
if (request.getParameter("YWID")!=null&&!request.getParameter("YWID").equals("")){
  strYWID=request.getParameter("YWID");
  List listap=ClientApplications.getActionReport(intWorkflowID,strYWID);
  Iterator itap=listap.iterator();
  %>
  <form action="" method="post" id="Action" name="Action">
<input type="hidden" name="WorkflowID" value=<%=intWorkflowID %>>
<input type="hidden" name="YWID" value=<%=strYWID %>>
<input type="hidden" name="DefActionID">
  <table class="TabledStyle" width=700 border=0 align="center" cellpadding=0 cellspacing=1>
             <tr class="DateListTableHeadStyle">
			   <td width="40" class="TableBgStyle"><div align="center">选中</div></td>
              <td  class="TableBgStyle"><div align="center">
               参与者
              </div></td>
              <td  class="TableBgStyle"><div align="center">
               时间
              </div></td>
              <td  class="TableBgStyle" align="center"><div align="center">
               操作类型
              </div></td>
              <td width="93" class="TableBgStyle" align="center"><div align="center">当前节点</div></td>
              <td  width="100" class="TableBgStyle" align="center"><div align="center">已转发节点</div></td>
              <td width="150" class="TableBgStyle"><div align="center">待转发节点</div></td>
            </tr>
 <%while (itap.hasNext()){
  ActionReport ap=(ActionReport)itap.next();
  %>
   <tr style="cursor:hand" onClick="doSelect('<%=ap.getID()%>')">
	<td width="40" class="TableBgStyle" height="25" ><div align="center"><input id="ActionID<%=ap.getID()%>" type="radio" name="ActionID" value="<%=ap.getID()%>" ></div></td>
      <td  class="TableBgStyle"><div align="center">
               <%=ap.getParticipant() %>
              </div></td>
              <td  class="TableBgStyle"><div align="center">
               <%=ap.getActionTime() %>
              </div></td>
              <td  class="TableBgStyle" align="center"><div align="center">
               <%=ap.getActionType()==2?"转发":ap.getActionType()== 3?"自动转发":ap.getActionType()==4?"中止流转":ap.getActionType()==5?"结束流转":ap.getActionType()==6?"退回":""%>
              </div></td>
              <td width="93" class="TableBgStyle" align="center"><div align="center"><%=Node.getNodeName(intWorkflowID,ap.getCurrentNode())%></div></td>
              <td  width="100" class="TableBgStyle" align="center"><div align="center">
			  <%
			  String[] strForward=ap.getForwarded().split("\\|");
			  for (int i=0;i<strForward.length&&strForward!=null;i++){
				if (strForward[i]!=null&&!strForward[i].equals("")){
				  out.print(Node.getNodeName(intWorkflowID,Integer.parseInt(strForward[i]))+"<br>");
				}
			  }
			  %>
			  </div></td>
              <td width="150" class="TableBgStyle"><div align="center">
			   <%
			  String[] strNext=ap.getNextNode().split("\\|");
			  for (int i=0;i<strNext.length&&strNext!=null;i++){
				if (strNext[i]!=null&&!strNext[i].equals("")){
				  out.print(Node.getNodeName(intWorkflowID,Integer.parseInt(strNext[i]))+"<br>");
				}
			  }
			  %></div></td>
            </tr>
  <%
 }
 %>
</table><br /><br />
<center><input type="button" class="ButtonStyle" style="width:80pt " value="查看业务数据历史" onclick="javascript:History()">  <input type=reset class="ButtonStyle" value="重写" style="width:60pt"/>
</form>
  <%
}
%>


</body>
</html>
