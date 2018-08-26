<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>
<%
//填写备注
int intID = Integer.parseInt(request.getParameter("ID"));
NewJxp jxp = new NewJxp();
jxp.setID(intID);
jxp = jxp.BuildObject();

String strUserName = (String)session.getAttribute("xingming");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>备注</title>
<script language="javascript">
function submitForm(form) {
//  opener.resetTD("BZ");
	opener.modifyTD("BZ", form.BZ.value);
	opener.updateJxp();
	window.close();
}
</script>
<link href="../css/common.css" rel="stylesheet" type="text/css">
</head>

<body>
<table width="500"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><form name="form" id="form" method="post" action="">
      <table width="100%"  border="0" cellpadding="2" cellspacing="1" class="tablebg">
        <tr>
          <td colspan="2" class="tdbg">备注</td>
          </tr>
        <tr>
          <td colspan="2" class="tdbg"><div align="center">
            <textarea name="BZ" cols="40" rows="5" wrap="VIRTUAL" id="BZ"></textarea>
          </div></td>
          </tr>
        <tr>
          <td colspan="2" class="tdbg"><div align="center">
              <input type="button" name="Button" value="确定" onClick="submitForm(this.form)">
              &nbsp;&nbsp;
              <input type="button" name="Button" value="取消" onClick="window.close()">
          </div></td>
          </tr>
      </table>
    </form></td>
  </tr>
</table>
</body>
</html>
