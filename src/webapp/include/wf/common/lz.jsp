<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
 * 标题：主页面
 * 描述：工作流主页面
 * 时间：2005-10-25 17:03:40
 * 版权：
 */
%>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.youngbook.common.wf.engines.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.ydtf.dmis.workflow.Workflow_Data_Jxp" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="java.util.*;" %>
<%
int intWorkflowID = 1;  //获得工作流编号
int intCurrNodeID = Integer.parseInt(request.getParameter("CurrNodeID"));  //获得当前节点编号
%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/style.css" rel="stylesheet" type="text/css">
</head>

<body>
<p align="center" class="SmallTitleStyle">**发送</p>
<table width="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td><form action="" method="post" name="formLz" id="formLz">
      <table width="100%" border="0" cellpadding="2" cellspacing="1" class="TableStyle" >
        <tr>
          <td width="30%" height="30"><div align="center">发&nbsp;送&nbsp;到：</div></td>
          <td>
					<select name="select" size="6" style="width:250px ">
					<%
          List listNode = ClientApplications.getNextNode(intWorkflowID, intCurrNodeID);
          Iterator itNode = listNode.iterator();
          while (itNode.hasNext()) {
            Node node = (Node)itNode.next();
            String strNodeName = node.getName();
            int intNextNodeID = node.getID();
            %>
            <option value="<%=intNextNodeID%>"><%=strNodeName %></option>
            <%
          }
          %>
          </select>
					</td>
        </tr>
        <tr>
          <td height="30">&nbsp;</td>
          <td><input type="button" name="Button" value="签名">
            <input type="button" name="Submit2" value="取消" onClick="window.close()"></td>
        </tr>
      </table>
    </form></td>
  </tr>
</table>
</body>
</html>
<script language="javascript">
window.focus();
</script>