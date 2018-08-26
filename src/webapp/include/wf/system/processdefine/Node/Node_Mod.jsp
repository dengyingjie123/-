<jsp:include page="../../checkLogin.jsp"></jsp:include>
<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
 * 程序：李扬
 * 时间：2004-11-16
 * 说明：修改节点页面，保存修改节点的部分信息（节点编号，节点名称，节点状态，节点类型，可转向节点）
 */
%>
<%@ page import = "java.util.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="org.jdom.*" %>
<jsp:useBean id="modNode" class="com.youngbook.common.wf.processdefine.Node" scope="session">
</jsp:useBean>
<%
try {
//获得操作请求  "AddNode" 配置新的工作流节点
String strActions = request.getParameter("Actions");
if (strActions != null && strActions.equals("Mod")) {
  //获得节点编号
  String strID = request.getParameter("ID");
  //获得节点名称
  String strName = request.getParameter("Name");
  //获得节点状态
  String strStatu = request.getParameter("Statu");
  //获得节点类型 0：开始节点， 1：中间节点， 2：结束节点
  String strType = request.getParameter("Type");
  //获得可转向节点类表
  String[] strToID = request.getParameterValues("To");
  List listTransition = new ArrayList();
  for (int i = 0; strToID != null && i < strToID.length; i++) {
    listTransition.add(strToID[i]);
  }
  //更新节点属性
  modNode.setID(Integer.parseInt(strID));
  modNode.setName(Tools.toUTF8(strName));
  modNode.setStatu(Tools.toUTF8(strStatu));
  modNode.setType(Integer.parseInt(strType));
  modNode.setTransition(listTransition);
%>
<jsp:forward page="Node_Mod_Condition.jsp"></jsp:forward>
<%
}
String strWorkflowID = new String();  //工作流编号
String strID = new String();  //节点编号
List listModTran = new ArrayList();
strWorkflowID = request.getParameter("WorkflowID");  //从request中获得工作流编号
strID = request.getParameter("ID");  //从request中获得节点编号
//判断工作流编号和节点编号的合法性
if (strWorkflowID != null && !strWorkflowID.equals("") &&
  strID != null && !strID.equals("")) {
  int intWorkflowID = Integer.parseInt(strWorkflowID);
  int intID = Integer.parseInt(strID);
  //实例化一个节点，并用它来初始化session JavaBean
  //该实例保存了修改前的信息
  Node nodeTemp = Node.searchNodeObject(intWorkflowID, intID);
  modNode.setWorkflowID(nodeTemp.getWorkflowID());
  modNode.setID(nodeTemp.getID());
  modNode.setName(nodeTemp.getName());
  modNode.setStatu(nodeTemp.getStatu());
  modNode.setType(nodeTemp.getType());
  modNode.setTransition(nodeTemp.getTransition());
  modNode.setCondition_In(nodeTemp.getCondition_In());
  modNode.setCondition_Out(nodeTemp.getCondition_Out());
  modNode.setBizField(nodeTemp.getBizField());
  listModTran = modNode.getTransition();
  nodeTemp = null;
}
%>

<%

Node node = new Node();
//实例一个节点列表，用于用户选择可转向节点
List listNode = new ArrayList();
if (strWorkflowID != null && !strWorkflowID.equals("")) {
  int intWorkflowID = Integer.parseInt(strWorkflowID);
  //设置工作流编号
  node.setWorkflowID(intWorkflowID);
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>配置节点</title>
<script src="../../script/CheckText.js"></script>
<script language="javascript">
function submitForm(form) {
  if (checkForm(form)) {
    for (i = 0; i < document.form.To.length; i++) {
      document.form.To[i].selected = true;
    }
    form.action = "Node_Mod.jsp";
    form.submit();
  }
}
function checkForm(form) {
  if (!checkFormItem(form.Name, "C", 0, 0, 1, "工作流节点名称"))
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

<form name="form" method="post" action="">
<p align="center">
		<font style="font-size:12pt "><strong>配置节点</strong></font>
	</p>
  <div align="center">
    <table width="500"  border="0" cellpadding="0" cellspacing="1" class="TableINBgStyle">
          <tr class="TableBgStyle">
            <td width="35%" height="25" class="TableTdBgBlueStyle"><div align="right">节点编号：</div></td>
            <td class="TableTdBgStyle"><input name="ID" type="text" class="TextStyle" id="ID" value="<%=modNode.getID()%>" readonly="true"></td>
          </tr>
          <tr class="TableBgStyle">
            <td height="25"><div align="right">节点名称:&nbsp; </div></td>
            <td class="TableTdBgStyle"><input name="Name" type="text" class="TextStyle" id="Name" value="<%=Tools.Decode(modNode.getName())%>"></td>
          </tr>
          <tr class="TableBgStyle">
            <td height="25"><div align="right">节点状态：</div></td>
            <td class="TableTdBgStyle"><input name="Statu" type="text" class="TextStyle" id="Statu" value="<%=Tools.Decode(modNode.getStatu())%>"></td>
          </tr>
          <tr class="TableBgStyle">
            <td height="25"><div align="right">节点类型：</div></td>
            <td class="TableTdBgStyle"><p>
                <%//=modNode.getType()%> <select name="Type" class="TextStyle" id="Type" style="width:115pt ">
                  <option value="0" <%= modNode.getType() == 0 ? "selected" : "" %>>开始节点</option>
                  <option value="1" <%= modNode.getType() == 1 ? "selected" : "" %>>中间节点</option>
                  <option value="2" <%= modNode.getType() == 2 ? "selected" : "" %>>结束节点</option>
                </select>
            </p></td>
          </tr>
          <tr>
            <td class="TableBgStyle"><div align="right">节点转向：</div></td>
            <td class="TableBgStyle"><table width="42%"  border="0" cellspacing="0" cellpadding="0">
                <tr class="DateListTableHeadStyle">
                  <td width="37%" height="20">可选节点</td>
                  <td width="31%"><div align="center"> </div>
                      <div align="center"> </div></td>
                  <td width="32%">可转向节点</td>
                </tr>
                <tr>
                  <td rowspan="2"><select name="From" size="6" class="TextFieldStyle" id="From" style="width:130pt">
                    <%
                    //可选节点列表，供用户选择可转向的节点
                    listNode = node.searchNodeObject();
                    Iterator itNode = listNode.iterator();
                    while (itNode.hasNext()) {
                      boolean bolIsIn = false;  //标示该节点是不是自身节点，或是已经选为可转向节点， true:将不在可选节点显示框内显示。
                      node = (Node)itNode.next();
                      for (int i = 0; i < listModTran.size(); i++) {
                        String strTranID = (String)listModTran.get(i);
                        if (node.getID() == Integer.parseInt(strTranID) ||
                        node.getID() == modNode.getID()) {
                          //判断该节点是否是自身节点，或是已经选为可转向节点
                          bolIsIn = true;
                          break;
                        }
                      }
                      if (node.getID() == modNode.getID()) {
                        bolIsIn = true;
                      }
                      if (!bolIsIn) {
                        %>
                        <option value="<%=node.getID()%>"><%=node.getID()+"("+node.getName()+")"%></option>
                        <%
                        }
                      }
                      %>
                  </select></td>
                  <td height="43" class="TableBgStyle"><input name="Button" type="button" class="ButtonStyle" style="width:50pt " onClick="add()" value="---&gt;"></td>
                  <td rowspan="2"><select name="To" size="6" multiple class="TextFieldStyle" id="To"  style="width:130pt">
                    <%
                    //已选节点列表
                    //此处显示拥护以选为可转向节点的列表，该裂变中部包含自身
                    itNode = listNode.iterator();
                    while (itNode.hasNext()) {
                      node = (Node)itNode.next();
                      for (int i = 0; i < listModTran.size(); i++) {
                        String strTranID = (String)listModTran.get(i);
                        if (node.getID() == Integer.parseInt(strTranID) &&
                        node.getID() != modNode.getID()) {
                          //判断是否满足显示条件：已经选为可转向节点并且不是本身
                          %>
                          <option value="<%=node.getID()%>"><%=node.getID()+"("+node.getName()+")"%></option>
                          <%
                          break;
                        }
                      }
                    }
                   %>
                  </select></td>
                </tr>
                <tr>
                  <td><input name="Button" type="button" class="ButtonStyle" style="width:50pt " onClick="remove()" value="&lt;---"></td>
                </tr>
            </table></td>
          </tr>
    </table>
    <br>
      <input type="hidden" name="ID" id="ID" value="<%=modNode.getID()%>" />
      <input type="hidden" name="WorkflowID" id="WorkflowID" value="<%=modNode.getWorkflowID()%>" />
      <input type="hidden" name="Actions" id="Actions" value="Mod"/>
      <input name="Button" type="button" class="ButtonStyle" style="width:60pt " onclick="submitForm(this.form)" value="下一步">
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
