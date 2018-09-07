<jsp:include page="../../checkLogin.jsp"></jsp:include>
<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
 * 程序：李扬
 * 时间：2004-11-16
 * 说明：新增节点页面，保存新增节点可编辑数据字段信息
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
//获得请求
String strActions = request.getParameter("Actions");
if (strActions != null && strActions.equals("AddFieldStatu")) {
  //业务数据状态列表
  //业务数据表名
  String[] strFieldName = request.getParameterValues("FieldName");
  //业务数据状态  0：可编辑， 1：不可编辑（不写入XML中）
  String[] strStatu = request.getParameterValues("Statu");
  List listBF = new ArrayList();
  for (int i = 0; i < strStatu.length; i++) {
    if (strStatu[i].equals("0")) {
      //将状态为0的业务数据写入XML中
      BizField bd = new BizField();
      bd.setName(strFieldName[i]);
      bd.setState(Integer.parseInt(strStatu[i]));
      listBF.add(bd);
    }
  }
  newNode.setBizField(listBF);
  //插入该节点
  newNode.insert();

  //新增节点成功，转向成功页面
%>
<jsp:forward page="Node_Add_Done.jsp"></jsp:forward>
<%
}
%>
<%
//用于显示该工作流下所有的业务数据，供用户配置
BizData bd = new BizData();
bd.setWorkflowID(newNode.getWorkflowID());

List listElem = bd.search();
if (listElem != null && listElem.size() > 0) {
  Element elem = (Element)listElem.get(0);
  bd = bd.buildObject(elem);
}

List listBF = bd.getBizField();
if (listBF == null || listBF.size() == 0) {
  throw new Exception("无法获得业务数据配置，请重新配置业务数据。");
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script language="JavaScript">
function submitForm(form) {
  if (checkForm(form)) {
    form.action = "Node_Add_FieldStatu.jsp";
    form.submit();
  }
}
function checkForm(form) {
  return true;
}
</script>
<title>字段状态</title>
<link href="../../style/common.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.style1 {color: #FFFFFF}
-->
</style>
</head>

<body>

<form name="form1" method="post" action="">
      <div align="center">

    		<p align="center"><font style="font-size:12pt "><strong>业务数据项状态</strong></font></p>

            <table width="55%"  border="0" cellpadding="0" cellspacing="1" class="TableINBgStyle">
              <tr class="DateListTableHeadStyle">
                <td height="20"><div align="center">字段名称</div></td>
                <td><div align="center">字段显示名</div></td>
                <td><div align="center">可编辑状态</div></td>
              </tr>
              <%
              //列出所有该工作流的业务数据
              int intCount = 0;
              Iterator itBF = listBF.iterator();
              while (itBF.hasNext()) {
                intCount++;
                BizField bf = (BizField)itBF.next();
                String strFieldName = bf.getName();  //字段名
                String strFieldTitle = bf.getTitle();  //字段显示名
                int intStatu = bf.getState();  //字段状态 （1：不可编辑；0：可编辑，2：隐藏）
                %>
              <tr class="TableBgStyle">
                <td class="TableTdBgStyle"><div align="center"><%=strFieldName%>
                      <input type="hidden" name="FieldName" id="FieldName" value="<%=strFieldName%>"/>
                </div></td>
                <td><div align="center"><%=strFieldTitle%>
                </div></td>
                <td class="TableTdBgStyle"><div align="center">
                  <select name="Statu" class="TextStyle">
                      <option value="1" <%= intStatu == 1?"Selected":""%>>不可编辑</option>
                      <option value="0" <%= intStatu == 0?"Selected":""%>>可编辑</option>
                      <option value="2" <%= intStatu == 2?"Selected":""%>>隐藏</option>
                  </select>
                </div></td>
              </tr>
              <%
    }
    %>
            </table>
        <br>
          <input type="hidden" name="Count" id="Count" value="<%=intCount%>"/>
          <input type="hidden" name="Actions" id="Actions" value="AddFieldStatu"/>
          <input name="Button" type="button" class="ButtonStyle" style="width:60pt " onClick="submitForm(this.form)" value="保存">
          <input name="Reset" type="reset" class="ButtonStyle" style="width:60pt " value="重置">
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
