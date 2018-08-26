<%@ page contentType="text/html; charset=utf-8"%>
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
<div align="center"><span class="SmallTitleStyle">调度执行</span><br>
  <br>
</div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td><form action="" method="post" name="formDdzx" id="formDdzx">
      <table width="100%" border="0" cellpadding="1" cellspacing="1" class="TableStyle" >
        <tr>
          <td width="30%" height="30"><div align="center">时&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;间：</div></td>
          <td><table width="100%" border="0" cellpadding="0" cellspacing="0" >
            <tr>
              <td width="300" height="30">
			        <jsp:include page="../../Common/TimeYMDHM.jsp">
				        <jsp:param name="ID" value="SJ"></jsp:param>
				      </jsp:include>
							</td>
              <td>&nbsp;</td>
            </tr>
          </table></td>
        </tr>
        <tr>
          <td height="30"><div align="center">对方负责人：</div></td>
          <td><input name="FZR" type="text" id="FZR" size="35">
            <input type="button" name="Submit3" value="选择"></td>
        </tr>
        <tr>
          <td height="30"><div align="center">当值调度员：</div></td>
          <td><input name="DDY" type="text" id="DDY" size="40"></td>
        </tr>
        <tr>
          <td height="30">&nbsp;</td>
          <td><input type="button" name="Button" value="签名" onClick="writeDdzx(this.form)">
            <input type="button" name="Submit2" value="取消"  onClick="window.close()"></td>
        </tr>
      </table>
    </form></td>
  </tr>
</table>
</body>
</html>
<script language="javascript">
//initYj(id);
function initDdzx(id) {
  var openerForm = opener.document.form;
	var thisForm = document.formYJ;
	if (typeof(openerForm.all[id+"td"] != "undefined")) {
	  thisForm.YJ.value = openerForm.all[id+"td"].innerText;
	}
}
function writeDdzx(form) {
	var sj = form.SJ.value;
	var fzr = form.FZR.value;
	var ddy = form.DDY.value;
	buildTdFromFormField(id+"SJ", sj);
	buildTdFromFormField(id+"FZR", fzr);
	buildTdFromFormField(id+"DDY", ddy);
	buildFormField(id+"SJ", sj, opener);
	buildFormField(id+"FZR", fzr, opener);
	buildFormField(id+"DDY", ddy,opener);
	window.close();
}
window.focus();
</script>