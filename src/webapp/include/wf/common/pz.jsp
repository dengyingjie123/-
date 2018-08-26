<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/style.css" rel="stylesheet" type="text/css">
<script src="../script/common.js"></script>
<script language="javascript">
var pzrid = getQuery("pzrid"); 
var pzsjid = getQuery("pzsjid");
var pzid = getQuery("pzid");
</script>
</head>

<body>
<p align="center" class="SmallTitleStyle">**批示</p>
<table width="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td><form action="" method="post" name="formPz" id="formPz">
      <table width="100%" border="0" cellpadding="2" cellspacing="1" class="TableStyle" >
        <tr>
          <td width="30%" height="30"><div align="center">是否批准：</div></td>
          <td>
					  <input name="pzRadio" type="radio" value="1" onClick="javascript:if (this.checked) pz=1">&nbsp;批&nbsp;&nbsp;准&nbsp;&nbsp;
						<input name="pzRadio" type="radio" value="0" onClick="javascript:if (this.checked) pz=0">&nbsp;不&nbsp;批&nbsp;准
					</td>
        </tr>
        <tr>
          <td height="30"><div align="center">用&nbsp;&nbsp;&nbsp;&nbsp;户：</div></td>
          <td><input name="PZR" type="text" id="PZR" size="30"></td>
        </tr>
        <tr>
          <td height="30">&nbsp;</td>
          <td><input type="button" name="Button" value="签名" onClick="writePz(this.form)">
            <input type="button" name="Submit2" value="取消" onClick="window.close()"></td>
        </tr>
      </table>
    </form></td>
  </tr>
</table>
</body>
</html>
<script language="javascript">
var isChecked = false;
var pz = 0;
function writePz(form) {
  if (checkData(form)) {
	  buildTdFromFormField(pzrid, form.PZR.value);
	  buildFormField(pzrid, form.PZR.value, opener);
		buildFormField(pzsjid, getNowTime(), opener);
		buildFormField(pzid, pz, opener);
		//缺少批准人ID
		window.close();
	}
	else {
	  alert("请选择是否批准!");
	}	
}
function checkData(form) {
  var objCheck = form.pzRadio;
	for (i = 0; i < objCheck.length; i++) {
	  if (objCheck[i].checked) {
		  return true;
		}
	}
	return false;
}
window.focus();
</script>