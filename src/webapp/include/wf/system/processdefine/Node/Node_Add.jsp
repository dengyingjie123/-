<jsp:include page="../../checkLogin.jsp"></jsp:include>
<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
 * 程序：李扬
 * 时间：2004-11-16
 * 说明：新增节点页面，记录新增节点基本信息（节点编号，节点名称，节点状态，节点类型，可转向节点）
 */
%>
<%@ page import = "java.util.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="org.jdom.*" %>
<%//Session Bean 用于保存新增节点的信息 %>
<jsp:useBean id="newNode" class="com.youngbook.common.wf.processdefine.Node" scope="session">
</jsp:useBean>
<%
try {
//获得操作请求  "AddNode" 添加新的工作流节点
String strActions = request.getParameter("Actions");
if (strActions != null && strActions.equals("AddNode")) {
  //获得工作流ID
  String strWorkflowID = request.getParameter("WorkflowID");
  //获得节点ID
  String strID = request.getParameter("ID");
  //获得节点名称
  String strName = Tools.toUTF8(request.getParameter("Name"));
  //获得节点状态
  String strStatu = Tools.toUTF8(request.getParameter("Statu"));
  //获得节点类型 0：开始节点， 1：中间节点， 2：结束节点
  String strType = request.getParameter("Type");
  //获得可转向节点类表
  String[] strToID = request.getParameterValues("To");
  List listTransition = new ArrayList();
  for (int i = 0; strToID != null && i < strToID.length; i++) {
    listTransition.add(strToID[i]);
  }
  //设置节点属性
  newNode.setWorkflowID(Integer.parseInt(strWorkflowID));
  newNode.setID(Integer.parseInt(strID));
  newNode.setName(Tools.Encode(strName));
  newNode.setStatu(Tools.Encode(strStatu));
  newNode.setType(Integer.parseInt(strType));
  newNode.setTransition(listTransition);

  //新增节点的部分信息保存以后，转向下一页面
%>
<jsp:forward page="Node_Add_Condition.jsp"></jsp:forward>
<%
}
//获得工作流ID
String strWorkflowID = request.getParameter("WorkflowID");
int intWorkflowID = Integer.MAX_VALUE;
Node node = new Node();
//实例一个节点列表，用于用户选择可转向节点（可转向节点：除自身以外的所有该工作留下的节点）
List listNode = new ArrayList();
//判断工作流编号的合法性
if (strWorkflowID != null && !strWorkflowID.equals("")) {
  intWorkflowID = Integer.parseInt(strWorkflowID);
  //设置工作流编号，用于查找该工作流下可以转向的节点
  node.setWorkflowID(intWorkflowID);
}
else {
  throw new Exception("执行AddNode.jsp页面时发生异常，无法获得工作编号（WorkflowID）");
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>新增节点</title>
<script src="../../script/CheckText.js" type=""></script>
<script language="javascript" type="">
function submitForm(form) {
  if (checkForm(form)) {
    for (i = 0; i < document.form.To.length; i++) {
    //选中可转向节点
      document.form.To[i].selected = true;
    }
    form.action = "Node_Add.jsp";
    form.submit();
  }
}
function checkForm(form) {
  if (!checkFormItem(form.ID, "N", 8, 0, 1, "工作流节点编号"))
	  return false;
	else if (!checkFormItem(form.Name, "C", 0, 0, 1, "工作流节点名称"))
	  return false;
	else if (!checkFormItem(form.Statu, "C", 0, 0, 1, "工作流节点状态"))
	  return false;
	else
	  return true;
}
function add() {
  var isHave = 0;
  if (document.form.From.selectedIndex == -1) {
    window.alert("请选择可转向节点");
  }
  else {
  if (isHave == 0) {
    //在可转向节点列表中添加所选节点
    document.form.To.options[document.form.To.length] =
      new Option(document.form.From[document.form.From.selectedIndex].text,
      document.form.From[document.form.From.selectedIndex].value);
    //删除可选节点中选中的节点
      document.form.From.remove(document.form.From.selectedIndex);
    }
  }
}
function remove() {
  if (document.form.To.selectedIndex == -1) {
    window.alert("请选择删除的转向节点");
  }
  else {
    //在可选节点中添加选中节点
    document.form.From.options[document.form.From.length] =
      new Option(document.form.To[document.form.To.selectedIndex].text,
      document.form.To[document.form.To.selectedIndex].value);
    //删除可转向节点列表中选中的节点
    document.form.To.remove(document.form.To.selectedIndex);
  }
}
</script>
<link href="../../style/common.css" rel="stylesheet" type="text/css">
</head>

<body>
<br>
<form name="form" method="post" action="">
	<p align="center">
		<font style="font-size:12pt "><strong>新增节点</strong></font>
	</p><center>
    <table width="500"  border="0" align="center" cellpadding="0" cellspacing="1" class="TableINBgStyle">
                <tr class="TableBgStyle">
                  <td width="35%" height="25" class="TableTdBgBlueStyle"><div align="right">节点编号:&nbsp; </div></td>
                  <td class="TableTdBgStyle"><input name="ID" type="text" class="TextStyle" id="ID" value="<%=Node.getMaxNodeID(intWorkflowID) + 1%>"></td>
                </tr>
                <tr class="TableBgStyle">
                  <td class="TableTdBgBlueStyle" height="25"><div align="right">节点名称:&nbsp;</div></td>
                  <td class="TableTdBgStyle"><input name="Name" type="text" class="TextStyle" id="Name"></td>
                </tr>
                <tr class="TableBgStyle">
                  <td class="TableTdBgBlueStyle" height="25"><div align="right">节点状态:&nbsp;</div></td>
                  <td class="TableTdBgStyle"><input name="Statu" type="text" class="TextStyle" id="Statu"></td>
                </tr>
                <tr class="TableBgStyle">
                  <td class="TableTdBgBlueStyle" height="25"><div align="right">节点类型:</div></td>
                  <td class="TableTdBgStyle">
                    <select name="Type" class="TextStyle" id="Type" style="width:115pt ">
                      <option value="0" selected>开始节点</option>
                      <option value="1">中间节点</option>
                      <option value="2">结束节点</option>
                    </select>
                  </td>
                </tr>
                <tr>
                  <td class="TableBgStyle"><div align="right">节点转向：</div></td>
                  <td class="TableTdBgStyle"><table width="42%"  border="0" cellpadding="0" cellspacing="0" class="TableINBgStyle">
                      <tr class="DateListTableHeadStyle">
                        <td width="37%" height="20" align="center">可选节点</td>
                        <td width="31%"><div align="center"> </div>
                            <div align="center"> </div></td>
                        <td width="32%" align="center">可转向节点</td>
                    </tr>
                      <tr>
                        <td rowspan="2"><select name="From" size="6" class="TextFieldStyle" id="From" style="width:130pt">
                          <%
                          //显示可转向节点，供用户选择
                          listNode = node.searchNodeObject();
                          Iterator itNode = listNode.iterator();
                          while (itNode.hasNext()) {
                            node = (Node)itNode.next();
                            %>
                            <option value="<%=node.getID()%>"><%=node.getID()+"(" + node.getName() + ")"%></option>
                            <%
                            }
                            %>
                        </select></td>
                        <td height="43" class="TableBgStyle"><input name="Button" type="button" class="ButtonStyle" style="width:50pt " onClick="add()" value="---&gt;"></td>
                        <td rowspan="2"><select name="To" size="6" multiple class="TextFieldStyle" id="To"  style="width:130pt">
                                                </select></td>
                      </tr>
                      <tr>
                        <td class="TableBgStyle"><input name="Button" type="button" class="ButtonStyle" style="width:50pt " onClick="remove()" value="&lt;---"></td>
                      </tr>
                  </table></td>
                </tr>
  </table>
          <br>
		  <div align="center">
            <input type="hidden" name="WorkflowID" id="WorkflowID" value="<%=strWorkflowID%>"/>
            <input type="hidden" name="Actions" id="Actions" value="AddNode"/>
            <input name="Button" type="button" class="ButtonStyle" style="width:60pt "onclick="submitForm(this.form)" value="下一步">
            <input name="Reset" type="reset" class="ButtonStyle" style="width:60pt " value="重写">
  </div>
</form>
</body>
</html>
<%
}
catch (Exception e) {
  //异常处理
  out.println(e.getMessage());
  out.println("<a href = 'Node_SelectWorkflow.jsp'>返回</a>");
}
%>
