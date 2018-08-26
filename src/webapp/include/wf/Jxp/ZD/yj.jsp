<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>
<%
String strID = request.getParameter("ID");
String strUserName = (String)session.getAttribute("xingming");
strUserName = strUserName.trim();
JxpYJ yj = new JxpYJ();
List listYJ = new ArrayList();
if (strID.equals("YYKYJ") || strID.equals("YYKSHYJ")) {
  yj.setTYPE("营运科意见");
  listYJ = yj.queryExact();
}
else if(strID.equals("JDKYJ") || strID.equals("JDKSHYJ")) {
  yj.setTYPE("保护专业批示");
  listYJ = yj.queryExact();
}


%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改意见</title>
<script src="../script/common.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
var id = getQuery("ID");
var user = "<%=strUserName%>";
var isEdit = false;
function writeYJ(form) {
  if (Trim(form.YJ.value) == "") {
		form.YJ.value += user+"  "+ getNowTime();
	}
	else {
		form.YJ.value += "   "+user+"  "+ getNowTime();
	}
	if (isEdit) {
	  opener.resetTD(id);
	}
  opener.modifyTD(id, form.YJ.value);
	window.close();
}
function getYJ() {
  var yj = opener.document.all[id+"td"].innerText;
	document.all.YJ.value = Trim(yj);
	isEdit = true;
}
function setYJ(form) {
  var oldValue = form.YJ.value;
	if (Trim(oldValue) != "") {
	  form.YJ.value += "\n" + form.selectYJ.value;
	}
	else {
	  form.YJ.value = form.selectYJ.value;
	}
}
</script>
<link href="../css/common.css" rel="stylesheet" type="text/css">
</head>

<body>
<table width="500" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td><form name="form" method="post" action="">
      <table width="100%" border="0" cellpadding="2" cellspacing="1" class="tablebg" >
        <tr>
          <td class="tdbg">请输入您的意见：字库
            <select name="selectYJ" id="selectYJ" style="width:150px " onChange="setYJ(this.form)">
              <option selected>请选择您的意见</option>
              <%
              for (int i = 0; listYJ != null && i < listYJ.size(); i++) {
                yj = (JxpYJ)listYJ.get(i);
                %>
                <option value="<%=yj.getLIST()%>"><%=yj.getLIST() %></option>
                <%
                }
              %>
              <option value="同意">同意</option>
            </select>
            设置字库 从意见库选择</td>
        </tr>
        <tr>
          <td class="tdbg"><textarea name="YJ" cols="70" rows="10" wrap="VIRTUAL" id="YJ"></textarea></td>
        </tr>
        <tr>
          <td class="tdbg"><div align="center">
              <input type="button" name="Button" value="签名" onClick="writeYJ(this.form)">
              <input type="button" name="Button" value="修改意见" onClick="getYJ()">
              <input type="reset" name="Reset" value="重写">
              <input type="button" name="Button" value="取消" onClick="javascript:window.close()">
          </div></td>
        </tr>
      </table>
    </form></td>
  </tr>
</table>
</body>
</html>
