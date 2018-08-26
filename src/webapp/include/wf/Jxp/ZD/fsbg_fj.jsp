<%@ page contentType="text/html; charset=utf-8" %>
<%
String strUserName = (String)session.getAttribute("xingming");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>否决</title>
<script src="../script/common.js"></script>
<script src="../script/jxp.js"></script>
<script language="javascript">
function FJ(form) {
  var pz = getQuery("PZ");
	var yj = getQuery("YJ");
	if (pz != null && yj != null) {
	  var value = "<%=strUserName%> 否决 " + getNowTime();
	  opener.buildOneField(yj,value);
		opener.buildOneField(pz,"0");
	}
  opener.form.ServiceType.value = "SaveForward";
  opener.form.TargetURL.value = "/jxp/ZD/fsbg_lzsp_done.jsp";
  opener.form.action = "/workflow/WorkflowService";
	opener.form.NextNode.value = form.NextNode.value;
	opener.form.submit();
	window.close();
}
</script>
</head>

<body>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><form action="" method="post" name="form" id="form">
      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="20"><p align="center">否决到状态<br>
            <br>
          </p>
            </td>
        </tr>
        <tr>
          <td><div align="center">
            <select name="NextNode" id="NextNode">
              <option value="2" selected>营运科安排</option>
            </select>
            <br>
            <br>
          </div></td>
        </tr>
        <tr>
          <td><div align="center">
              <input type="button" name="Button" value="否决" onClick="FJ(this.form)">
							&nbsp;              
							<input type="button" name="Button" value="取消" onclick="javascript:window.close()">
          </div></td>
        </tr>
      </table>
    </form></td>
  </tr>
</table>
</body>
</html>
