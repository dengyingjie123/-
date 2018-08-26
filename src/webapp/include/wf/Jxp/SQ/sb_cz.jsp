<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.ydtf.dmis.jxp.Sfixing" %>
<%@ page import="com.ydtf.dmis.jxp.Sprotect" %>
<%@ page import="com.ydtf.dmis.jxp.JxpSb" %>
<%@ page import="com.youngbook.common.wf.common.Tools" %>

<%
String strClass = request.getParameter("Class");
String strUnitClass = Tools.toUTF8(request.getParameter("UnitClass"));
List listUnitClass = new ArrayList();
List listUnit = new ArrayList();
if (strClass != null && strClass.equals("1")) {
  Sfixing sf = new Sfixing();
  listUnitClass = sf.queryUnitClass();
  if (strUnitClass != null && !strUnitClass.equals("")) {
    sf.setUNITCLASS(strUnitClass);
  }
  else {
    sf.setUNITCLASS("220KV变电站");
    strUnitClass = "220KV变电站";
  }
  listUnit = sf.queryUnit();
}
else if (strClass != null && strClass.equals("2")) {
  Sprotect sp = new Sprotect();
  listUnitClass = sp.queryUnitClass();
  if (strUnitClass != null && !strUnitClass.equals("")) {
    sp.setUNITCLASS(strUnitClass);
  }
  else {
    sp.setUNITCLASS("220KV变电站");
    strUnitClass = "220KV变电站";
  }
  listUnit = sp.queryUnit();
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>请选择厂站</title>
<style type="text/css">
<!--
bg {
	background-color: #CCCCFF;
}
tdbg1 {
	background-color: #F0F0F0;
}
tdbg2 {
	background-color: #FFFFFF;
}
-->
</style>
</head>

<body>
<table width="550"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><div align="center">请选择厂站<br>
      </div>
      <table width="90%"  border="1" align="center">
      <%
      int i = 0;
      for (i = 0; listUnitClass != null && i < listUnitClass.size(); i++) {
        if ((i % 5) == 0) {
          out.println("<tr>");
        }
        String strUC = (String)listUnitClass.get(i);
        out.println("  <td><a href='sb_cz.jsp?Class="+strClass+"&UnitClass=" + strUC + "'>" + strUC + "</a></td>");
        if ((i % 5) == 4 && i > 5) {
          out.println("</tr>");
        }
      }
      if ((i % 5) != 0) {
        for (int j = 0; j < 5 - (i % 5); j++) {
          out.println("<td>&nbsp</td>");
        }
        out.println("</tr>");
      }
      %>

    </table>
    <br>
      <%=strUnitClass%>
    <table width="90%"  border="1" align="center">
      <%
      for (i = 0; listUnit != null && i < listUnit.size() ; i++) {
        if ((i % 3) == 0) {
          out.println("<tr>");
        }
        String strU = (String)listUnit.get(i);
        out.println("  <td><a href='sb.jsp?Class="+ strClass + "&UnitClass="+strUnitClass+"&Unit="+strU+"'>" + strU + "</a></td>");
        if ((i % 3) == 2 && i > 3) {
          out.println("</tr>");
        }
      }
      if ((i % 3) != 0) {
        for (int j = 0; j < 3 - (i % 3) && i > 3; j ++) {
          out.println("<td>&nbsp</td>");
        }
        out.println("</tr>");
      }
      %>
    </table></td>
  </tr>
</table>
<br>
<center><a href="javascript:window.close()">关闭</a></center>
</body>
</html>
