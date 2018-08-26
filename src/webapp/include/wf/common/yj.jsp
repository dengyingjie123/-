<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../script/common.js"></script>
<script language="javascript">
var id = getQuery("id");
</script>
<link href="../css/style.css" rel="stylesheet" type="text/css">
</head>

<body>
<p align="center" class="SmallTitleStyle">**意见</p>
<table width="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td><form action="" method="post" name="formYJ" id="formYJ">
      <table width="100%" border="0" cellpadding="1" cellspacing="1" class="TableStyle" >
        <tr>
          <td height="30"><div align="center">常用批示：</div></td>
          <td><select name="select" style="width:272px ">
          </select>
            <input type="button" name="Button" value="选择">
            <input type="button" name="Button" value="设置常用批示"></td>
        </tr>
        <tr>
          <td valign="middle"><div align="center">是否批准</div></td>
          <td height="30">
					  <input name="pzRadio" type="radio" value="1" onClick="javascript:if (this.checked) pz=1">
            批准 
            <input name="pzRadio" type="radio" value="0" onClick="javascript:if (this.checked) pz=0">
            不批准</td>
        </tr>
        <tr>
          <td rowspan="2" valign="top"><div align="center">意见：</div></td>
          <td height="30"><textarea name="YJ" cols="60" rows="8" wrap="VIRTUAL" class="InputeStyle" id="textarea"></textarea></td>
        </tr>
        <tr>
          <td height="30"><input type="button" name="Button2" value="签名" onClick="writeYj(this.form)">
            <input type="button" name="Submit2" value="取消" onClick="window.close()"></td>
        </tr>
      </table>
    </form></td>
  </tr>
</table>
</body>
</html>
<script language="javascript">
window.focus();
initYj(id);
function initYj(id) {
  var openerForm = opener.document.form;
	var thisForm = document.formYJ;
	if (typeof(openerForm.all[id+"YJtd"] != "undefined")) {
	  thisForm.YJ.value = openerForm.all[id+"YJtd"].innerText;
	}
}
function writeYj(form) {
	var yj = form.YJ.value;
	if (checkData(form)) {
	  buildTdFromFormField(id+"YJ", yj);
	  buildFormField(id+"YJ", yj, opener);
		buildFormField(id+"PZ", pz, opener);
		buildFormField(id+"SJ", getNowTime(),opener);
		window.close();
	}
	else {
	  alert("请选择是否批准!");
	}
	
}
var pz = 0;
function checkData(form) {
  var objCheck = form.pzRadio;
	for (i = 0; i < objCheck.length; i++) {
	  if (objCheck[i].checked) {
		  return true;
		}
	}
	return false;
}
</script>