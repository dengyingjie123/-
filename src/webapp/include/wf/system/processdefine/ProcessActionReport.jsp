<jsp:include page="../checkLogin.jsp"></jsp:include>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import = "java.util.*" %>
<%
List listAP = new ArrayList();
int intWorkflowID = Integer.parseInt(request.getParameter("WorkflowID"));
String strYWID = request.getParameter("YWID");
listAP = ClientApplications.getActionReport(intWorkflowID, strYWID);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>流转记录</title>
<script src="../script/CheckBox.js"></script>
<script type="" language="JavaScript">
function submitForm(form) {
  if (isCheckedUnique(form, "ActionID")) {
	  showModalDialog("BizDataHistory.jsp?WorkflowID="+form.WorkflowID.value+"&ActionID="+form.ActionSelected.value+"&YWID="+form.YWID.value,"","dialogHeight:600px;dialogWidth:700px;dialogTop:150px; dialogLeft:200px;help:no;scroll:yws;status:no;");
	}
}
function doSelect(ActionID) {
  form.ActionSelected.value = ActionID;
	document.all("ActionID"+ActionID).checked = true;
}
</script>
<link href="../style/common.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.style1 {
	font-size: 12pt;
	font-weight: bold;
}
-->
</style>
</head>
<body>

<div align="center" class="style1">工作流业务监控 流转记录</div>
<br>
<table width="80%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><form name="form" method="post" action="" id="form">
      <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="TableINBgStyle">
        <tr class="DateListTableHeadStyle">
          <td height="20"><div align="center">选择</div></td>
          <td><div align="center">序号</div></td>
          <td><div align="center">时间</div></td>
          <td><div align="center">操作人</div></td>
          <td><div align="center">当前节点</div></td>
          <td><div align="center">转发节点</div></td>
          <td><div align="center">操作</div></td>
        </tr>
        <%
  int intCount = 0;
  Iterator itAP = listAP.iterator();
  while (itAP.hasNext()) {
    intCount++;
    String strPreNodeName = new String();
    String strCurrentNodeName = new String();
    String[] strForward = null;
    String [] strForwardName = null;
    StringBuffer sbForwardName = new StringBuffer();
    ActionReport ap = (ActionReport)itAP.next();
    strPreNodeName = Node.getNodeName(ap.getWorkflowID(), ap.getPreNode());
    strCurrentNodeName = Node.getNodeName(ap.getWorkflowID(), ap.getCurrentNode());
    strForward = ap.getForwarded().split("\\|");
    if (strForward != null) {
      strForwardName = new String[strForward.length - 1];
      for (int i = 0; strForwardName != null && i < strForwardName.length; i++) {
        strForwardName[i] = Node.getNodeName(ap.getWorkflowID(), Integer.parseInt(strForward[i+1]));
        sbForwardName.append(strForwardName[i]);
        sbForwardName.append("，");
      }
    }
    if (sbForwardName.length() > 0) {
      sbForwardName.delete(sbForwardName.length() - 1, sbForwardName.length());
    }
  %>
        <tr class="TableBgStyle" onClick="doSelect('<%=ap.getID()%>')" style="cursor:hand">
          <td height="25"><div align="center">
            <input name="ActionID" id="ActionID<%=ap.getID()%>" type="radio" value="<%=ap.getID()%>">
          </div></td>
          <td><div align="center"><%=intCount%></div></td>
          <td><div align="center"><%=ap.getActionTime()%></div></td>
          <td><div align="center"><%=ap.getParticipant()%></div></td>
          <td><div align="center"><%=strCurrentNodeName%></div></td>
          <td><div align="center"><%=sbForwardName.toString()%></div></td>
          <%
    String strActionType = new String();
    switch(ap.getActionType()) {
      case 0:
        strActionType = "开始业务";
        break;
      case 1:
        strActionType = "只保存";
        break;
      case 2:
        strActionType = "转发";
        break;
      case 3:
        strActionType = "自动转发";
        break;
      case 4:
        strActionType = "中止业务";
        break;
      case 5:
        strActionType = "完成业务";
        break;
      case 6:
        strActionType = "退回业务";
        break;
      default : break;
    }
    %>
          <td><div align="center"><%=strActionType%> </div></td>
          <%
  }
  %>
        </tr>
      </table>
      <div align="center"><br>
          <input name="ActionSelected" type="hidden" id="ActionSelected">
          <input name="WorkflowID" type="hidden" id="WorkflowID" value="<%=intWorkflowID%>">
          <input name="YWID" type="hidden" id="YWID" value="<%=strYWID%>">
          <input name="Button" type="button" class="ButtonStyle" onClick="submitForm(this.form)" value="查看业务数据历史">
          <input name="Reset" type="reset" class="ButtonStyle" value="重写" style="width:60pt">
          </div>
    </form></td>
  </tr>
</table>
</body>
</html>
