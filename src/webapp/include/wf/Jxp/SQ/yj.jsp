<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>意见</title>
<%
String strID = request.getParameter("ID");
String strUserName = (String)session.getAttribute("xingming");
out.println("<script language='javascript'>");
out.println("var user = '"+strUserName+"';");
out.println("</script>");
%>
<script src="../script/common.js"></script>
<script language="javascript">
var id = getQuery("ID");
var isEdit = false;
function writeYJ(form) {
  var value = form.YJ.value;
	if (Trim(value) == "") {
	  if (!isEdit) {
	    value += user + " " + getNowTime();
		}
		else {
		  value = user + " " + getNowTime();
		}
	}
	else {
	  if (!isEdit) {
	    value += "   " + user + " " + getNowTime();
		}
		else {
		  value = "   " + user + " " + getNowTime();
		}
	}
	if (isEdit) {
	  opener.resetTD(id);
	}
  opener.modifyTD(id, value);
	window.close();
}
function getYJ() {
  var oldYJ = opener.document.all[id+"td"].innerText;
	form.YJ.value = oldYJ;
	isEdit = true;
}
</script>
</head>

<body>
<table width="500"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><form name="form" method="post" action="">
      <table width="100%"  border="1">
        <tr>
          <td>请输入您的意见：</td>
        </tr>
        <tr>
          <td><textarea name="YJ" cols="50" rows="7" wrap="VIRTUAL" id="YJ"></textarea></td>
        </tr>
        <tr>
          <td><p>
              <input type="button" name="Button" value="签名" onClick="writeYJ(this.form)">
              <input type="button" name="Button" value="修改意见" onClick="getYJ()">
              <input type="reset" name="Reset" value="重写">
              <input type="button" name="Button" value="取消" onClick="javascript:window.close()">
</p>
            </td>
        </tr>
      </table>
    </form></td>
  </tr>
</table>
</body>
</html>
