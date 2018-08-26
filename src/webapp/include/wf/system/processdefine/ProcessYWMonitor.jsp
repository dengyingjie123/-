<jsp:include page="../checkLogin.jsp"></jsp:include>
<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
 * 程序：李扬
 * 时间：2004-11-23
 * 说明：查看业务流转信息业面，用于业务监控
 */
%>
<%@ page import = "java.util.*" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%
int intWorkflowID = Integer.parseInt(request.getParameter("WorkflowID"));
Node node = new Node();
RouteList rl = new RouteList();
node.setWorkflowID(intWorkflowID);
rl.setWorkflowID(intWorkflowID);
List listNode = new ArrayList();
List listRL = new ArrayList();
listNode = node.searchNodeObject();
listRL = rl.query();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>工作流监控</title>
<link href="../style/common.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.style1 {font-size: 12pt}
-->
</style>
</head>

<body>
<div align="center" class="style1"><strong>工作流业务监控</strong><br>
  </div>
<br>
<table width="80%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><table width="100%"  border="0" cellpadding="0" cellspacing="0" id="bg">
      <tr>
        <td><table width="100%"  border="0" cellpadding="0" cellspacing="1" class="TableINBgStyle">
            <tr class="TableBgStyle">
              <td width="12%"><div align="center">工作流编号：</div></td>
              <td width="9%"><div align="center"><%=intWorkflowID %></div></td>
              <td width="12%" height="20"><div align="center">工作流名称：</div></td>
              <td width="67%"><%=ProcessInfo.getWorkflwoName(intWorkflowID)%></td>
            </tr>
            <tr class="TableBgStyle">
              <td colspan="2"><div align="center">节点名称：</div></td>
              <td height="20"><div align="center">所处状态</div></td>
              <td><div align="center">业务编号</div></td>
            </tr>
            <%
  for (int i = 0; listNode != null && i < listNode.size(); i++) {
    node = (Node)listNode.get(i);
    %>
            <tr class="TableBgStyle">
              <td colspan="2" rowspan="4"><div align="center"><%=node.getName() %></div></td>
              <td height="20"><div align="center">未处理状态</div></td>
              <td><%
        for (int j = 0; listRL != null && j < listRL.size(); j++) {
          RouteList rlActive = (RouteList)listRL.get(j);
          if (rlActive.getCurrentNode() == node.getID() && rlActive.getStatu() == 1) {
            out.println("<a href=\"ProcessActionReport.jsp?WorkflowID="+intWorkflowID+
                        "&RouteListID="+rlActive.getID()+
                        "&YWID="+rlActive.getYWID()+"\">"+rlActive.getYWID()+"</a>");
          }
        }
      out.println("&nbsp");
      %>
              </td>
            </tr>
            <tr>
              <td height="20" bgcolor="#ECE9D8"><div align="center">等待状态</div></td>
              <td bgcolor="#ECE9D8"><%
        for (int j = 0; listRL != null && j < listRL.size(); j++) {
          RouteList rlWait = (RouteList)listRL.get(j);
          if (rlWait.getCurrentNode() == node.getID() && rlWait.getStatu() == 2) {
            out.println("<a href=\"ProcessActionReport.jsp?WorkflowID="+intWorkflowID+
                        "&RouteListID="+rlWait.getID()+
                        "&YWID="+rlWait.getYWID()+"\">"+rlWait.getYWID()+"</a>");
          }
        }
      out.println("&nbsp");
      %>
              </td>
            </tr>
            <tr>
              <td height="20" class="TableBgStyle"><div align="center">中止状态</div></td>
              <td class="TableBgStyle"><%
        for (int j = 0; listRL != null && j < listRL.size(); j++) {
          RouteList rlCancel = (RouteList)listRL.get(j);
          if (rlCancel.getCurrentNode() == node.getID() && rlCancel.getStatu() == 3) {
            out.println("<a href=\"ProcessActionReport.jsp?WorkflowID="+intWorkflowID+
                        "&RouteListID="+rlCancel.getID()+
                        "&YWID="+rlCancel.getYWID()+"\">"+rlCancel.getYWID()+"</a> &nbsp");
          }
        }
      out.println("&nbsp");
      %>
              </td>
            </tr>
            <tr>
              <td height="20" bgcolor="#ECE9D8"><div align="center">已处理状态</div></td>
              <td bgcolor="#ECE9D8"><%
        for (int j = 0; listRL != null && j < listRL.size(); j++) {
          RouteList rlOver = (RouteList)listRL.get(j);
          if (rlOver.getCurrentNode() == node.getID() && rlOver.getStatu() == 4) {
            out.println("<a href=\"ProcessActionReport.jsp?WorkflowID="+intWorkflowID+
                        "&RouteListID="+rlOver.getID()+
                        "&YWID="+rlOver.getYWID()+"\">"+rlOver.getYWID()+"</a>");
          }
        }
      out.println("&nbsp");
      %>
              </td>
            </tr>
            <%
    }
   %>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
