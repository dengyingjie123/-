<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>

<%
//发送到
//NEED:  WorkflowID, Field, CurrentNodeID
String strWorkflowID = "1";//request.getParameter("WorkflowID");
int intWorkflowID = Integer.parseInt(strWorkflowID);
String strField = request.getParameter("Field");
int intCurrNodeID = Integer.parseInt(request.getParameter("CurrNodeID"));
List listNode = ClientApplications.getNextNode(intWorkflowID, intCurrNodeID);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>发送</title>
<script src="../script/common.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
function submitForm(form) {
  /*
  ServiceType
  TargetURL
  NextNode
  */
  var value = 1;
  var YWID = opener.form.YWID.value;
  nextNodeID = form.NextNode.value;
  if (nextNodeID != "") {
    var field = getQuery("Field");
    var target = getQuery("CurrNodeID");
    opener.form.ServiceType.value = "SaveForward";
    opener.form.TargetURL.value = "/jxp/ZD/done.jsp?target="+target;
    opener.form.action = "/workflow/WorkflowService";
    opener.form.NextNode.value = nextNodeID;
    if (nextNodeID == "2" || nextNodeID == "3") {
      value = 0;
    }
    if (field != null) {
      opener.buildOneField(field, value);
    }
    if (nextNodeID == "11") {
      window.open("selectHGDW.jsp","","width=550,height=350,scrollbars=auto");
    }
    else {
      fsSubmitForm();
    }
  }
  else {
    window.alert("请选择流程下一个状态");
  }
}

function setXGDW(value) {
  opener.buildOneField("XGDWMC", value);
}

function fsSubmitForm() {
  opener.submitForm(opener.form);
  window.close();
}
</script>
<link href="../css/common.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.style1 {font-size: 16px}
-->
</style>
</head>

<body>
<table width="500"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><form name="form" id="form" method="post" action="">
      <table width="100%"  border="0" cellpadding="2" cellspacing="1" class="tablebg">
        <tr>
          <td class="tdbg"><p align="center" class="style1">请选择流程下一个状态</p>
            </td>
        </tr>
        <tr>
          <td class="tdbg"><div align="center">
            <select name="NextNode" id="NextNode">
              <option>请选择</option>
              <%
              for (int i = 0; listNode != null && i < listNode.size(); i++) {
                Node node = (Node)listNode.get(i);
                %>
                <option value="<%=node.getID()%>"><%=node.getName()%></option>
                <%
                }
              %>
            </select>
          </div></td>
        </tr>
        <tr>
          <td class="tdbg"><div align="center">
              <input type="button" name="Button" value="发送" onClick="submitForm(this.form)">
              <input type="button" name="Button" value="取消" onClick="javascript:window.close()">
          </div></td>
        </tr>
      </table>
    </form></td>
  </tr>
</table>
</body>
</html>
