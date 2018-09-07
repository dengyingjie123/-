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
<p align="center" class="SmallTitleStyle">**备注</p>
<table width="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td><form action="" method="post" name="formBZ" id="formBZ">
      <table width="100%" border="0" cellpadding="1" cellspacing="1" class="TableStyle" >
        <tr>
          <td rowspan="2" valign="top"><div align="center">备注：</div></td>
          <td height="30"><textarea name="BZ" cols="60" rows="10" wrap="VIRTUAL" class="InputeStyle" id="BZ"></textarea></td>
        </tr>
        <tr>
          <td height="30"><input type="button" name="Button" value="签名" onClick="writeBz(this.form)">
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
initBz(id);
function initBz(id) {
  var openerForm = opener.document.form;
	var thisForm = document.formBZ;
	if (typeof(openerForm.all[id+"td"] != "undefined")) {
	  thisForm.BZ.value = openerForm.all[id+"td"].innerText;
	}
}
function writeBz(form) {
	var bz = form.BZ.value;
	buildTdFromFormField(id, bz);
	buildFormField(id, bz, opener);
	window.close();
}
</script>