<jsp:include page="../checkLogin.jsp"></jsp:include>
<%
/**
* 程序：李扬
* 时间：2004-10-19
* 说明：业务数据配置
*      选择工作流
*/
%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import = "java.util.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="com.youngbook.common.wf.admin.*" %>


<%
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>业务数据配置</title>
<link href="../style/common.css" rel="stylesheet" type="text/css">
<script language="javascript">
 function submitForm(){
 	if(isCheckedUnique(form,"WorkflowID")){
		form.action = "SaveBizData.jsp";
		form.target = "_self";
		form.submit();
	}
 }
//检查是否选择了唯一的复选框
function isCheckedUnique(form, CheckBoxName) {
  var selectedCount = 0;
  for (var i = 0; i < form.elements.length; i++) {
    var element = form.elements[i];
    if (element.name.indexOf(CheckBoxName) >= 0 &&
    	element.name.indexOf("SELECTALL") < 0) {
      if (element.checked) {
        selectedCount = selectedCount + 1;
      }
    }
  }
  if (selectedCount != 1) {
    alert("输入错误，请做出唯一一项选择！");
    return false;
  }
  return true;
}
</script>
</head>

<body>

<table align="center" width="90%" border="0" cellspacing="0" cellpadding="0">

  <tr>
    <td align="center" height="20"><font style="font-size:12pt"><strong><br>业务配置</strong></font></td>
  </tr>
  <tr>
    <td><form name="form" method="post">
      <jsp:include page="../processdefine/ListCommon.jsp"></jsp:include>
      <br/>
	  <center>
        <input name="button" type="button" class="ButtonStyle" style="width:60pt " value="下一步" onClick="submitForm()">
      </center>
    </form>
	</td>
  </tr>
</table>
<p>&nbsp;</p>
</body>
</html>
