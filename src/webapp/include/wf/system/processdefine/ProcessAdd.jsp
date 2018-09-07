<jsp:include page="../checkLogin.jsp"></jsp:include>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>

<%
//获得最大的工作流编号
int intMaxID = ProcessInfo.getMaxWorkflowID();
intMaxID++;
%>

<html>
<head>
<title>增加工作流信息页面</title>
<link href="../style/common.css" rel="stylesheet" type="text/css">
<script src="../script/CheckText.js"></script>
<script language="javascript">
//得到当前日期并写入创建日期中
var strdate;
function getdate(){
 now = new Date();
 date = now.getDate();
 month = now.getMonth()+1;
 year = now.getYear();
 strdate = year+"-"+month+"-"+date;
 form.CreateDate.value=strdate;
}

//根据业务数据修改历史判断是否记录用户动作
function checkSelect(){
 if(form.SaveAction.value == "false"){
    form.SaveHistory.value = "false";
 }
}

function submitForm(form) {
  if (checkForm(form)) {
    form.submit();
  }
}
function checkForm(form) {
  if (!checkFormItem(form.ID, "N", 0, 0, 1, "工作流编号")) {
    return false;
  }
  else if (!checkFormItem(form.Name, "C", 0, 0, 1, "工作流名称")) {
    return false;
  }
  else {
    return true;
  }
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></head>
<body onLoad="getdate()">
<form name="form" action="ProcessAddDone.jsp" method="post">
<p align="center">
		<font style="font-size:12pt "><strong>新增工作流基本信息</strong></font>
	</p>
      <table width="450" class="TabledStyle"  border=0 align="center" cellpadding=0 cellspacing=1>
       <tr class="TableBgStyle">

                  <td width="250" height="24" align="right">工作流编号：</td>
                  <td width="283" height="24" align="left" class="TableBgStyle"><input name="ID" type="text" class="TextStyle" id="ID" style="WIDTH:100pt" value="<%=intMaxID%>"></td>
                </tr>
                <tr class="TableBgStyle">
                  <td height="24" align="right">工作流名称：</td>
                  <td height="24" align="left" class="TableBgStyle"><input name="Name" type="text" class="TextStyle" id="Name" style="WIDTH:100pt" value="">
    　                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         </td>
                </tr>
                <tr class="TableBgStyle">
                  <td height="24" align="right">工作流说明：</td>
                  <td height="24" align="left" class="TableBgStyle"><input name="Info" type="text" class="TextStyle" id="Info" style="WIDTH:100pt" value=""></td>
                </tr>
                <tr class="TableBgStyle">
                  <td height="23" align="right">创建日期：</td>
                  <td height="23" align="left" class="TableBgStyle"><input name="CreateDate" type="text" class="TextStyle" id="CreateDate" style="WIDTH:100pt" value=""></td>
                </tr>
                <tr class="TableBgStyle">
                  <td height="25" align="right">是否记录用户动作：</td>
                  <td height="25" align="left" class="TableBgStyle"><select name="SaveAction" class="TextStyle" id="SaveAction" style="WIDTH:100pt" onchange="checkSelect()">
                      <option value="true">是</option>
                      <option value="false">否</option>
                  </select></td>
                </tr>
                <tr class="TableBgStyle">
                  <td height="25" align="right">是否记录业务数据修改历史：</td>
                  <td height="25" align="left" class="TableBgStyle"><select name="SaveHistory" class="TextStyle" id="SaveHistory" style="WIDTH:100pt">
                      <option value="true">是</option>
                      <option value="false">否</option>
                  </select></td>
                </tr>
              </table>

      	  <div align="center"><br>
                <input type="button" name="query" value="增加" style="WIDTH:60pt"   class="ButtonStyle" onClick="submitForm(this.form)">
                <input type="reset"  name="reset"  value="重写" class="ButtonStyle" style="WIDTH:60pt" >
        </div>
    </form>
</body>
</html>

