








<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
 * 标题：工作流业务列表
 * 描述：根据用户名，列出该用户所能处理的业务
 * 时间：2005-10-17 18:09:21
 * 版权：
 */
%>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.youngbook.common.wf.engines.*" %>
<%@ page import="com.ydtf.dmis.ddtest.DDJxp" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*;" %>
<%
int intWorkflowID = Integer.parseInt(request.getParameter("WorkflowID"));
String strUserID = Tools.toUTF8(request.getParameter("UserID"));
int intNodeID = Integer.MAX_VALUE;
List listNode = ClientApplications.getOperableNodebyUserID(intWorkflowID, strUserID, intNodeID);
%>
<html>
<head>
<script language="javascript" type="text/javascript">
function submitForm(form, target) {
  if (form.YWID.value != "") {
    if (target == "location") {
      form.action = "Location.jsp";
      form.submit();
    }
    else if (target == "AP") {
      form.action = "ActionReport.jsp";
      form.actions.value = "AP";
      form.submit();
    }
  }
  else {
    window.alert("请输入索要查询的业务编号！");
  }
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>业务列表</title>
<link href="style.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.style1 {font-size: 18px}
-->
</style>
</head>
<body>
<a href=Main.jsp?UserID='<%=strUserID%>'&RouteListID=0&CurrNodeID=1 class="style1">检修票申请</a>
<form action="" method="post" name="form" target="_blank">
  <p class="TextStyle">查询检修票所处位置
    <input name="YWID" type="text" class="TextFieldStyle" id="YWID" size="4">
    <input name="WorkflowID" type="hidden" value="<%=intWorkflowID%>"/>
    <input name="UserID" type="hidden" value="<%=strUserID%>"/>
    <input type="button" name="Button" value="查询" onClick="submitForm(this.form, 'location')">
    <input type="button" name="Button" value="查看流转记录" onClick="submitForm(this.form, 'AP')">
    <input type="reset" name="Reset" value="重写">
    <input name="actions" type="hidden" id="actions">
<br>
</p>
</form>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="100%"  border="0" cellpadding="2" cellspacing="1" class="table_bg_black">
      <tr class="td_blue">
        <td width="13%" height="25"><div align="center">节点名称</div></td>
        <td width="6%"><div align="center">状态</div></td>
        <td width="81%"><div align="center">业务号</div></td>
      </tr>
      <%
      for (int i = 0; listNode != null && i < listNode.size(); i++) {
        Node node = (Node)listNode.get(i);
        RouteList rlActive = new RouteList();
        RouteList rlFinish = new RouteList();
        rlActive.setWorkflowID(intWorkflowID);
        rlActive.setCurrentNode(node.getID());
        rlActive.setStatu(1);
        rlFinish.setCurrentNode(node.getID());
        rlFinish.setWorkflowID(intWorkflowID);
        rlFinish.setStatu(4);
        List listRLActive = rlActive.queryExact();
        List listRLFinish = rlFinish.queryExact();
        %>
        <tr class="table_bg_white">
          <td rowspan="2"><div align="center"><%=node.getName()%></div></td>
          <td height="25"><div align="center">等待</div></td>
          <td>
          <%
          for (int m = 0; listRLActive != null && m < listRLActive.size(); m++) {
            rlActive = (RouteList)listRLActive.get(m);
            out.print("<a href='Main.jsp?UserID="+strUserID+"&CurrNodeID="+node.getID()+
                  "&RouteListID="+rlActive.getID()+"&YWID="+rlActive.getYWID()+"'>"+
                  rlActive.getYWID()+"</a> ");
          }
          %>
          &nbsp;
          </td>
        </tr>
        <tr>
          <td height="25" class="table_bg_white"><div align="center">完成</div></td>
          <td class="table_bg_white">
          <%
          for (int n = 0; listRLFinish != null && n < listRLFinish.size(); n++) {
            rlFinish = (RouteList)listRLFinish.get(n);
            out.print(rlFinish.getYWID()+" ");
          }
          %>
&nbsp;          </td>
        </tr>
        <%
        }
        %>
    </table></td>
  </tr>
</table>
</body>
</html>

