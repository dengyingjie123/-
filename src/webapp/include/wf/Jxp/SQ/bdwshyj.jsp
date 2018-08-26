<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>申请单位审核意见</title>
<%
String strID = request.getParameter("ID");
String strUserName = (String)session.getAttribute("xingming");
%>
<script src="../script/common.js"></script>
<script language="javascript">
var isModify = 0;
var id = "<%=strID%>";
var user = "<%=strUserName%>";
function writeYJ(form) {
	form.YJ.value += "   "+user+"  "+ getNowTime();
	if (isModify == 1) {
	  opener.resetYJ(id+"td");
	}
  opener.modify(id, form.YJ.value);
	window.close();
}

function getYJ() {
  var yj = opener.document.all[id+"td"].innerText;
	document.all.YJ.value = Trim(yj);
	isModify = 1;
}
</script>
<link href="../css/common.css" rel="stylesheet" type="text/css">
</head>

<body>
<table width="500"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><form name="form" method="post" action="">
      <table width="100%"  border="0" cellpadding="2" cellspacing="1" class="tablebg">
        <tr>
          <td class="tdbg">请输入您的意见：</td>
        </tr>
        <tr>
          <td class="tdbg"><textarea name="YJ" cols="50" rows="7" wrap="VIRTUAL" id="YJ"></textarea></td>
        </tr>
        <tr>
          <td class="tdbg"><p>
              <input type="button" name="Button" value="签名" onClick="writeYJ(this.form)">
              <input type="button" name="Button" value="修改意见" onClick="getYJ()">
              <input type="reset" name="Reset" value="重写">
              <input type="button" name="Button" value="取消">
</p>
            </td>
        </tr>
      </table>
    </form></td>
  </tr>
</table>
</body>
</html>
