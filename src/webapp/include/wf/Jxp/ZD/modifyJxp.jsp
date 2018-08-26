<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>
<%
int intWorkflowID = 1;
String strYWID = request.getParameter("YWID");
NewJxp jxp = new NewJxp();
jxp.setID(Integer.parseInt(strYWID));
jxp = jxp.BuildObject();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改检修票</title>
<script language="javascript">
function submitForm(form) {
  opener.resetTD("GZNR");
	opener.resetTD("TDFW");
  opener.modifyTD("GZNR", form.GZNR.value);
	opener.modifyTD("TDFW", form.TDFW.value);
	window.close();
}
function get() {
  var form = document.form;
  var gznr = opener.document.all["GZNRtd"].innerText;
	var tdfw = opener.document.all["TDFWtd"].innerText;
	form.GZNR.value = gznr;
	form.TDFW.value = tdfw;
}
</script>
<link href="../css/common.css" rel="stylesheet" type="text/css">
</head>

<body>
<table width="500"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><form action="" method="post" name="form" id="form">
      <table width="100%"  border="0" cellpadding="2" cellspacing="1" class="tablebg">
        <tr class="tdbg">
          <td>申请单位：</td>
          <td><%=jxp.getSQDW()%></td>
        </tr>
        <tr class="tdbg">
          <td>设备名称：</td>
          <td><%=jxp.getSBMC() %></td>
        </tr>
        <tr class="tdbg">
          <td>工作内容：</td>
          <td><textarea name="GZNR" cols="40" rows="5" wrap="VIRTUAL" id="GZNR"><%=jxp.getGZNR() %></textarea></td>
        </tr>
        <tr class="tdbg">
          <td>停电范围：</td>
          <td><textarea name="TDFW" cols="40" rows="5" wrap="VIRTUAL" id="TDFW"><%=jxp.getTDFW() %></textarea></td>
        </tr>
        <tr class="tdbg">
          <td colspan="2"><input type="button" name="Button" value="确定修改" onClick="submitForm(this.form)">
            <input type="submit" name="Submit" value="取消修改" onClick="window.close()">            </td>
          </tr>
      </table>
    </form></td>
  </tr>
</table>
</body>
</html>
<script language="javascript">
get();
</script>