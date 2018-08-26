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
<title>作废</title>
<script src="../script/common.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
function submitForm(form) {
  /*
  ServiceType
  TargetURL
  NextNode
  */
  var value = 1;
  var target = "12";// getQuery("CurrNodeID");
  opener.form.ServiceType.value = "Cancel";
  opener.form.TargetURL.value = "/jxp/ZD/done.jsp?target="+target;
  opener.form.action = "/workflow/WorkflowService";
  opener.buildOneField("BZ","");
  opener.form.BZ.value += "\r" + "已作废 " + getNowTime();
  opener.buildOneField("ZFYY",form.ZFYY.value);
  opener.submitForm(opener.form);
  window.close();
}
</script>
</head>

<body>
<table width="500"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><form name="form1" method="post" action="">
      <table width="100%"  border="1">
        <tr>
          <td colspan="2"><div align="center">作废</div><div align="center"></div></td>
          </tr>
        <tr>
          <td>
            <div align="center">设备名称：</div></td>
          <td><div align="center"><%=jxp.getSBMC()%></div></td>
        </tr>
        <tr>
          <td><div align="center">申请单位：</div></td>
          <td><div align="center"><%=jxp.getSQDW()%></div></td>
        </tr>
        <tr>
          <td colspan="2"><div align="center">
            <textarea name="ZFYY" cols="50" rows="4" wrap="VIRTUAL" id="ZFYY"></textarea>
          </div></td>
          </tr>
        <tr>
          <td colspan="2"><div align="center">
              <input type="button" name="Button" value="作废该票" onClick="submitForm(this.form)">
              <input type="button" name="Button" value="取消" onClick="javascript:window.close()">
          </div></td>
          </tr>
      </table>
    </form></td>
  </tr>
</table>
</body>
</html>
