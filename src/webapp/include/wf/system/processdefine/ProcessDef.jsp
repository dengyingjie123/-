<jsp:include page="../checkLogin.jsp"></jsp:include>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.Iterator" %>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>工作流基本信息</title>
<link href="../style/common.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.style1 {font-weight: bold}
-->
</style>
</head>
<script src="../script/CheckBox.js"></script>
<script src="../script/CheckText.js"></script>
<script language="javascript">
function submitForm(form,str){
  if(str == "modify"){
    if(isCheckedUnique(form,"WorkflowID")){
      form.action = "ProcessModify.jsp";
      form.target = "_self";
      form.submit();
    }
  }
  if(str == "delete"){
    if(isCheckedUnique(form,"WorkflowID")){
      if(confirm("您确定要删除该工作流?如果确定，数据库中所有信息都建被删除！！！")){
			  if (confirm("您确定要删除该工作流？")) {
          form.action = "ProcessDelDone.jsp";
          form.target = "_self";
          form.submit();
				}
      }
    }
  }
  if (str == "YWMonitor") {
    if(isCheckedUnique(form,"WorkflowID")){
      form.action = "ProcessYWMonitor.jsp";
      form.target = "_self";
      form.submit();
    }
  }
  if (str == "ActionMonitor") {
    if(isCheckedUnique(form,"WorkflowID")){
      form.action = "ProcessActionMonitor.jsp";
      form.target = "_self";
      form.submit();
    }
  }
  if(str == "add"){
    form.action = "ProcessAdd.jsp";
    form.target = "_self";
    form.submit();
  }
}
</script>


<body topMargin="5" leftMargin="5">

<table width="90%" align="center"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td height="10"></td>
  </tr>
  <tr>
    <td align="center" height="20"><font style="font-size:12pt"><strong><br>工作流管理</strong></font></td>
  </tr>
  <tr>
    <td><form name="form" method="post" id="form" action="">
      <jsp:include page="ListCommon.jsp"></jsp:include>
<div align="center"><br>
          <input name="RequestID" type="hidden" id="RequestID">
          <input name="button" type="button" class="ButtonStyle" id="add" style="width:60pt" value="新增" onClick="submitForm(this.form, 'add')">
          <input name="button" type="button" class="ButtonStyle" id="modify" style="width:60pt" value="修改" onClick="submitForm(this.form, 'modify')">
          <input name="button1" type="button" class="ButtonStyle" id="delete" style="width:60pt" value="删除" onClick="submitForm(this.form, 'delete')">
          <input type="button" name="Button" class="ButtonStyle"  value="业务监控" onClick="submitForm(this.form, 'YWMonitor')">
          <input type="button" name="Button" class="ButtonStyle"  value="流转监控" onClick="submitForm(this.form, 'ActionMonitor')">
          <input name="button" type="reset" class="ButtonStyle" style="width:60pt" value="重写">
      </div>
    </form></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
</body>
</html>
