<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.ydtf.dmis.jxp.Sfixing" %>
<%@ page import="com.ydtf.dmis.jxp.Sprotect" %>
<%@ page import="com.ydtf.dmis.jxp.JxpSb" %>
<%@ page import="com.youngbook.common.wf.common.Tools" %>
<%
Sfixing sf = new Sfixing();
Sprotect sp = new Sprotect();
String strClass = request.getParameter("Class");
String strUnitClass = Tools.toUTF8(request.getParameter("UnitClass"));
String strUnit = Tools.toUTF8(request.getParameter("Unit"));
String strFixClass = Tools.toUTF8(request.getParameter("FixClass"));
JxpSb jxpsb = new JxpSb();
List listSB = new ArrayList();
List listFixClass = new ArrayList();
if (strClass != null && strClass.equals("1")) {
  sf = new Sfixing();
  if (strUnitClass != null && !strUnitClass.equals("") &&
      strUnit != null && !strUnit.equals("")) {
    sf.setUNITCLASS(strUnitClass);
    sf.setUNIT(strUnit);
  }
	if (strFixClass != null && !strFixClass.equals("")) {
	  sf.setFIXCLASS(strFixClass);
	}
  listSB = sf.queryExact();
  listFixClass = sf.queryFixClass();
}
else if (strClass != null && strClass.equals("2")) {
  sp = new Sprotect();
  if (strUnitClass != null && !strUnitClass.equals("") &&
      strUnit != null && !strUnit.equals("")) {
    sp.setUNITCLASS(strUnitClass);
    sp.setUNIT(strUnit);
  }
	if (strFixClass != null && !strFixClass.equals("")) {
	  sp.setFIXCLASS(strFixClass);
	}
  listSB = sp.queryExact();
  listFixClass = sp.queryFixClass();
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>请选择设备</title>
<script language="javascript">
function addSB(sbid, czmc, sblx, sbmc) {
  opener.addSB(sbid,czmc,sblx,sbmc);
	window.close();
}
</script>
</head>

<body>
<table width="550"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><a href="sb_cz.jsp?Class=<%=strClass%>">返回并重新选择厂站</a><br>
      <table width="80%"  border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td><div align="center">请选择设备类型</div></td>
        </tr>
        <tr>
          <td>
            <table width="100%"  border="1">
            <%
            int i = 0;
            for (i = 0; listFixClass != null && i < listFixClass.size(); i++) {
              String strFixClassTemp = (String)listFixClass.get(i);
              if ((i % 7) == 0) {
                out.println("<tr>");
              }
              out.println("<td><a href='sb.jsp?Class="+strClass+"&UnitClass="+strUnitClass+"&Unit="+strUnit+"&FixClass="+strFixClassTemp+"'>" + strFixClassTemp + "</a></td>");
              if ((i % 7) == 6) {
                out.println("</tr>");
              }
            }
            if ((i % 7) != 1) {
              for (int j = 0; j < 7 - (i % 7) && i > 7; j++) {
                out.println("<td>&nbsp</td>");
              }
              out.println("</tr>");
            }
            %>
          </table></td>
        </tr>
      </table>
    <br>
    <table width="90%"  border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td><%=strUnit%></td>
      </tr>
      <tr>
        <td><table width="100%"  border="1">
          <tr>
            <td>序号</td>
            <td>设备名称</td>
            <td>选择</td>
          </tr>
          <%
          for (i = 0; listSB != null && i < listSB.size(); i++) {
            jxpsb = (JxpSb)listSB.get(i);
            %>
            <tr>
              <td><%=i + 1 %></td>
              <td>
                <%
                String strSBMC = new String();
                strFixClass = new String();
                if (strClass != null && strClass.equals("1")) {
                  strSBMC = ((Sfixing)jxpsb).getFIXING();
                }
                else if (strClass != null && strClass.equals("2")) {
                  strSBMC =((Sprotect)jxpsb).getFIXING() + " " +((Sprotect)jxpsb).getPROCLASS();
                }
                strFixClass = jxpsb.getFIXCLASS();
                int intSBID = jxpsb.getSBID();
                %>
              <%=strSBMC %>
              </td>
              <td><a href="javascript:addSB('<%=intSBID%>','<%=strUnit%>','<%=strFixClass%>','<%=strSBMC%>')">选择</a></td>
            </tr>
            <%
            }
          %>
        </table></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
