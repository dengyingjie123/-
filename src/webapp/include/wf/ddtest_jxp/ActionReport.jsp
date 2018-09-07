








<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
 * 标题：工作流流转信息查看页面
 * 时间：2005-10-17 18:09:44
 * 描述：根据工作流编号(WorkflowID)，业务编号(YWID)查出该业务的流转信息
 * 版权：
 */
%>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%@ page import="com.ydtf.dmis.ddtest.DDJxp" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import = "java.util.*" %>
<%
List listAP = new ArrayList();  //记录流转信息
int intWorkflowID = Integer.parseInt(request.getParameter("WorkflowID"));  //获得工作流编号
String strYWID = request.getParameter("YWID");  //获得业务编号
listAP = ClientApplications.getActionReport(intWorkflowID, strYWID);  //查找出流转信息
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>流转记录</title>
</head>
<body>
<table width="100%"  border="1" cellpadding="0">
  <tr>
    <td align='center'>序号</td>
    <td align='center'>时间</td>
    <td align='center'>操作人</td>
    <td align='center'>当前节点</td>
    <td align='center'>成功转发节点</td>
    <td align='center'>未成功转发节点</td>
    <td align='center'>操作</td>
    <td align='center'>查看历史数据</td>
  </tr>
  <%
  int intCount = 0;  //计数器，记录序号
  Iterator itAP = listAP.iterator();
  while (itAP.hasNext()) {
    //循环获得流转信息
    intCount++;
    String strPreNodeName = new String();  //前一节点名称
    String strCurrentNodeName = new String();  //当前节点名称
    String[] strForward = null;  //记录成功转发节点编号
    String [] strForwardName = null;  //记录成功转发节点名称（数组形式）
    StringBuffer sbForwardName = new StringBuffer();  //记录成功转发节点名称（XXX,XX 形式）
    String[] strNext = null;  //记录转发不成功节点编号
    String[] strNextName = null; //记录转发不成功节点名称（数组形式）
    StringBuffer sbNextName = new StringBuffer(); //记录转发不成功节点名称（XXX,XX 形式）
    ActionReport ap = (ActionReport)itAP.next();
    //获得前一节点名称
    strPreNodeName = Node.getNodeName(ap.getWorkflowID(), ap.getPreNode());
    //获得当前节点名称
    strCurrentNodeName = Node.getNodeName(ap.getWorkflowID(), ap.getCurrentNode());
    //获得转发成功节点编号（数组形式）
    strForward = ap.getForwarded().split("\\|");
    if (strForward != null && strForward.length > 0) {
      //将获得的转发节点成功编号转换为节点名称（XXX,XX,XX 形式）
      strForwardName = new String[strForward.length - 1];
      for (int i = 0; strForwardName != null && i < strForwardName.length; i++) {
        strForwardName[i] = Node.getNodeName(ap.getWorkflowID(), Integer.parseInt(strForward[i+1]));
        sbForwardName.append(strForwardName[i]);
        sbForwardName.append("，");
      }
    }
    //去除掉最后一个"，"
    if (sbForwardName.length() > 0) {
      sbForwardName.delete(sbForwardName.length() - 1, sbForwardName.length());
    }
    //获得转发不成功节点编号（数组形式）
    strNext = ap.getNextNode().split("\\|");
    if (strNext != null && strNext.length > 0) {
      //将转发不成功节点编号转换成节点名称（XX,XXX形式）
      strNextName = new String[strNext.length - 1];
      for (int i = 0; strNextName != null && i < strNextName.length; i++) {
        strNextName[i] = Node.getNodeName(ap.getWorkflowID(), Integer.parseInt(strNext[i+1]));
        sbNextName.append(strNextName[i]);
        sbNextName.append(",");
      }
    }
    //去掉最后一个","
    if (sbNextName.length() > 0) {
      sbNextName.delete(sbNextName.length() - 1, sbNextName.length());
    }
  %>
  <tr>
    <td align='center'><%=intCount%></td>
    <td align='center'><%=ap.getActionTime()%></td>
    <td align='center'><%=ap.getParticipant()%></td>
    <td align='center'><%=strCurrentNodeName%></td>
    <td align='center'><%=sbForwardName.toString() + "&nbsp"%></td>
    <td align='center'><%=sbNextName.toString() + "&nbsp"%></td>
    <%
    String strActionType = new String();
    switch(ap.getActionType()) {
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
    <td align='center'><%=strActionType%> &nbsp</td>
    <td align='center'><a href="HistoryData.jsp?WorkflowID=<%=ap.getWorkflowID()%>&YWID=<%=ap.getYWID()%>&ActionID=<%=ap.getID()%>" target="_blank">查看数据变化</a></td>
  <%
  }
  %>
  </tr>
</table>
</body>
</html>


