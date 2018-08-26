<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>
<%
String strYWID = request.getParameter("ID");
NewJxp jxp = new NewJxp();
jxp.setYWID(strYWID);
jxp = jxp.BuildObject();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>已执行</title>
<script src="../script/common.js"></script>
<script language="javascript">
function submitForm(form) {
  /*
	ServiceType
	TargetURL
	NextNode
	*/
	var value = 1;
		var target = "12";// getQuery("CurrNodeID");
		opener.form.taraget = "_self";
		opener.form.ServiceType.value = "Over";
		opener.form.TargetURL.value = "/jxp/ZD/done.jsp?target="+target;
		opener.form.action = "/workflow/WorkflowService";
		opener.form.BZ.value += "\r" + "已执行 " + getNowTime();
		opener.submitForm(opener.form);
		window.close();
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
          <td><%=jxp.getSQDW() %></td>
        </tr>
        <tr class="tdbg">
          <td>设备名称：</td>
          <td><%=jxp.getSBMC() %></td>
        </tr>
        <tr class="tdbg">
          <td colspan="2"><div align="center">
              <input type="Button" name="Button" value="设置为已执行" onClick="submitForm(this.form)">
              &nbsp;
              <input type="button" name="Button" value="取消" onClick="window.close()">
          </div></td>
          </tr>
      </table>
    </form></td>
  </tr>
</table>
</body>
</html>
