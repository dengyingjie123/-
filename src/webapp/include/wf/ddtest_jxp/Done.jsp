








<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
 * 标题：业务完成页面
 * 时间：2005-10-17 18:09:36
 * 描述：显示业务完成信息
 * 版权：
 */
%>
<%@ page import="com.youngbook.common.wf.admin.RouteList"%>
<%@ page import="com.ydtf.dmis.ddtest.DDJxp" %>
<%@ page import="com.youngbook.common.wf.processdefine.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
</head>
<body>
<h1>
操作结果页
</h1>
<br>
操作结果如下：(request.getAttribute("Result"))<br>
<%
String strResult=(String)request.getAttribute("Result");
if (strResult.equals("1")){
	out.print("成功<br>");
	out.print("操作信息如下：RouteList rl=(RouteList)request.getAttribute(\"RouteList \")<br>");
	RouteList rl=(RouteList)request.getAttribute("RouteList");
	out.print("操作结果：int intStatu=rl.getStatu();<br>");
	int intStatu=rl.getStatu();
	if (intStatu==4){
		out.print("所有目的地都转发完成<br>");
	}
	if (intStatu==2){
		out.print("只转发到了部份目的地<br>");
	}
	String strForWarded=rl.getForwarded();
	String strNextNode=rl.getNextNode();
	String arrForWarded[]=strForWarded.split("\\|");
	out.print("完成了以下节点的转发：");
	for (int i=0;arrForWarded!=null&&i<arrForWarded.length;i++){
		if (arrForWarded[i]!=null&&!arrForWarded[i].equals("")){
			Node node=Node.searchNodeObject(rl.getWorkflowID(),Integer.parseInt(arrForWarded[i]));
  			out.print(node.getName() + " ");
		}
	}
	String arrNextNode[]=strNextNode.split("\\|");
	out.print("<br>未完成以下节点的转发：");
	for (int i=0;arrNextNode!=null&&i<arrNextNode.length;i++){
		if (arrNextNode[i]!=null&&!arrNextNode[i].equals("")){
			Node node=Node.searchNodeObject(rl.getWorkflowID(),Integer.parseInt(arrNextNode[i]));
  			out.print(node.getName() + " ");
		}
	}
}
else
{
	out.print("失败<br>");
	out.print("原因如下：(Exception)request.getAttribute(\"Exception \")<br>");
	Exception e=(Exception)request.getAttribute("Exception");
	out.print(e.getMessage());
e.printStackTrace();
}
%>
</body>
</html>

