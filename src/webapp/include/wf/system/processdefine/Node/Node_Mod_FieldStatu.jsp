<jsp:include page="../../checkLogin.jsp"></jsp:include>
<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
 * 程序：李扬
 * 时间：2004-11-16
 * 说明：修改节点页面，修改节点可编辑字段信息
 */
%>
<%@ page import = "java.util.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="org.jdom.*" %>
<% //Session Bean 用于保存修改的节点信息 %>
<jsp:useBean id="modNode" class="com.youngbook.common.wf.processdefine.Node" scope="session">
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
  modNode.setBizField(listBF);
  //保存修改节点
  modNode.update();
%>
<jsp:forward page="Node_Mod_Done.jsp"></jsp:forward>
<%
}
%>
<%
//用于显示该工作流下所有的业务数据，供用户配置
BizData bd = new BizData();
bd.setWorkflowID(modNode.getWorkflowID());

List listElem = bd.search();
if (listElem != null && listElem.size() > 0) {
  Element elem = (Element)listElem.get(0);
  bd = bd.buildObject(elem);
}

List listBF = bd.getBizField();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script language="JavaScript">
function submitForm(form) {
  if (checkForm(form)) {
    form.action = "Node_Mod_FieldStatu.jsp";
    form.submit();
  }
}
function checkForm(form) {
  return true;
}
</script>
<title>配置字段状态</title>
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
                  <td height="20"><div align="center">字段名称></div></td>
                  <td><div align="center">
                    <div align="center">字段显示名</div>
                  </div></td>
                  <td><div align="center">可编辑状态</div></td>
                </tr>
                <%
                //列出所有该工作流的业务数据
                int intCount = 0;
                //可编辑列表
                List listModField = modNode.getBizField();
                Iterator itBF = listBF.iterator();
                while (itBF.hasNext()) {
                  boolean boolCanEdit = false;  //标示该业务数据是否可编辑
                  intCount++;
                  BizField bf = (BizField)itBF.next();
                  String strFieldName = bf.getName();
                  String strFieldTitle = bf.getTitle();
                  //循环比较该业务数据是否可编辑
                  for (int i = 0; listModField.size() > 0 && i < listModField.size(); i++) {
                    BizField bfModify = (BizField)listModField.get(i);
                    String strModFieldName = bfModify.getName();
                    if (strFieldName.equals(strModFieldName)) {
                      //该业务数据可编辑
                      boolCanEdit = true;
                      break;
                    }
                  }
                  %>
                <tr class="TableBgStyle">
                  <td><div align="center"><%=strFieldName%>
                        <input type="hidden" name="FieldName" id="FieldName" value="<%=strFieldName%>"/>
                  </div></td>
                  <td class="TableTdBgStyle"><div align="center"><%=strFieldTitle%>
                  </div></td>
                  <td class="TableTdBgStyle"><div align="center">
                    <select name="Statu" class="TextStyle">
                        <option value="1" <%= boolCanEdit ? "":"Selected"%>>不可编辑</option>
                        <option value="0"<%= boolCanEdit ? "Selected":""%>>可编辑</option>
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
            <input name="Button" type="button" class="ButtonStyle" style="width:60pt " onclick="submitForm(this.form)" value="保存">
            <input name="Reset" type="reset" class="ButtonStyle" style="width:60pt " value="重置">
  </div>
      </form>
    <br>
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
