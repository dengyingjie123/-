<jsp:include page="../../checkLogin.jsp"></jsp:include>
<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
 * 程序：李扬
 * 时间：2004-11-18
 * 说明：
 */
%>
<%@ page import="java.util.*" %>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>


<%
String strWorkflowID = request.getParameter("WorkflowID");
BizData bd = new BizData();
bd.setWorkflowID(Integer.parseInt(strWorkflowID));
List listBD = bd.searchObject();
bd = (BizData)listBD.get(0);
List listBF = new ArrayList();
listBF = bd.getBizField();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Untitled Document</title>
<script src="../../script/CheckBox.js" type=""></script>
<script src="../../script/CheckText.js" type=""></script>
<script language="javascript" type="">
function submitForm(form) {
  if(checkForm(form)) {
    target = form.AutoMake.value;
    switch(target) {
      case "1":
        form.action = "MakeCode.jsp";
        form.submit();
        break;
      case "2":
        form.action = "MakeLoginPage.jsp";
        form.submit();
        break;
      case "3":
        form.action = "MakeListPage.jsp";
        form.submit();
        break;
      case "4":
        form.action = "MakeMainPage.jsp";
        form.submit();
        break;
      case "5":
        form.action = "MakeDonePage.jsp";
        form.submit();
        break;
      case "6":
        form.action = "MakeActionReportPage.jsp";
        form.submit();
        break;
      case "7":
        form.action = "MakeHistoryDataPage.jsp";
        form.submit();
        break;
      case "8":
        form.action = "MakeLocationPage.jsp";
        form.submit();
        break;
    }
  }
}
function submitForms(form, target) {
  if (checkForm(form)) {
    if (target == "MakeCode") {
      form.action = "MakeCode.jsp";
      form.submit();
    }
    else if (target == "MakeLoginPage") {
      form.action = "MakeLoginPage.jsp";
      form.submit();
    }
    else if (target == "MakeListPage") {
      form.action = "MakeListPage.jsp";
      form.submit();
    }
    else if (target == "MakeMainPage") {
      form.action = "MakeMainPage.jsp";
      form.submit();
    }
    else if (target == "MakeDonePage") {
      form.action = "MakeDonePage.jsp";
      form.submit();
    }
    else if (target == "MakeActionReportPage") {
      form.action = "MakeActionReportPage.jsp";
      form.submit();
    }
    else if (target == "MakeHistoryDataPage") {
      form.action = "MakeHistoryDataPage.jsp";
      form.submit();
    }
    else if (target == "MakeLocationPage"){
      form.action = "MakeLocationPage.jsp";
      form.submit();
    }
  }
}
function checkForm(form) {
  if (!checkFormItem(form.ClassName, "C", 0, 0, 1, "类名")) {
    return false;
  }
  else if (!checkFormItem(form.PackageName, "C", 0, 0, 1, "包结构")) {
    return false;
  }
  else if (!checkFormItem(form.Dir, "C", 0, 0, 1, "页面存放目录")) {
    return false;
  }
  else {
    return true;
  }
}
</script>
<link href="../../style/common.css" rel="stylesheet" type="text/css">
</head>

<body>
<form name="form" method="post" action=""><br><div align="center"><strong style="font-size:12pt">代码生成器</strong></div><br>
  <table width="40%"  border="0" align="center" cellpadding="0" cellspacing="0" id="bg">
    <tr>
      <td><table width="100%"  border="0" cellpadding="0" cellspacing="1" class="TableINBgStyle">

        <tr>
          <td class="TableBgStyle"><div align="right">IBizDao子类类名：</td>
          <td class="TableBgStyle"><input name="ClassName" type="text" class="TextStyle" id="ClassName"></td>
        </tr>
        <tr>
          <td class="TableBgStyle"><div align="right">包结构：</td>
          <td class="TableBgStyle"><input name="PackageName" type="text" class="TextStyle" id="PackageName"></td>
        </tr>
        <tr>
          <td class="TableBgStyle"><div align="right">页面存放目录：</td>
          <td class="TableBgStyle"><input name="Dir" type="text" class="TextStyle" id="Dir"></td>
        </tr>
        <tr>
          <td class="TableBgStyle"><div align="right">主键：</td>
          <td class="TableBgStyle"><select name="PrimaryKey" id="PrimaryKey">
            <%
            for (int i = 0; listBF != null && i < listBF.size(); i++) {
              BizField bf = (BizField)listBF.get(i);
              %>
              <option value="<%=bf.getName()%>"><%=bf.getName()+"("+bf.getTitle()+")" %></option>
              <%
              }
              %>
              </select></td>
        </tr>
        <tr>
          <td class="TableBgStyle"><div align="right">自动生成：</td>
          <td class="TableBgStyle"><select name="AutoMake" id="AutoMake" >
            <option value="1" selected>IBizDao子类</option>
            <option value="2">登录页面</option>
            <option value="3">工作流列表页面</option>
            <option value="4">流转主页面</option>
            <option value="5">操作完成页面</option>
            <option value="6">流转记录页面</option>
            <option value="7">查看历史数据记录页面</option>
            <option value="8">查看业务所在位置页面</option>
                                                            </select>            </td>
        </tr>
      </table>
      <div align="center"></div></td>
    </tr>
  </table>
  <div align="center"><br>
    <input name="Button" type="button" class="ButtonStyle" onClick="submitForm(this.form)" value="确定" style="width:60pt">
    <input name="Reset" type="reset" class="ButtonStyle" value="重写" style="width:60pt">
    <input type="hidden" name="WorkflowID" value="<%=strWorkflowID%>"/>
  </div>
</form>
<p>&nbsp;</p>
</body>
</html>
