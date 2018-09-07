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
String strUserID = (String)session.getAttribute("xingming");
NewJxp jxp = new NewJxp();

String strType = new String();

int intCurrNodeID = 4;
List listRL = ClientApplications.getRouteListByYWIDAndNodeIDAndStatu(intWorkflowID,intCurrNodeID,strYWID,"ACTIVE");
int intRouteListID = ((RouteList)listRL.get(0)).getID();

if (strYWID != null && !strYWID.equals("")) {
  jxp.setID(Integer.parseInt(strYWID));
  jxp = jxp.BuildObject();
  strType = jxp.getTYPE();
}
//-------------------------------------------------------
String strActions = request.getParameter("Actions");
if (strActions != null && strActions.equals("ModifyPH")) {
  //jxp.setPH(strPH);
  jxp.update();
  %>
  <script language="javascript" type="text/javacript">
    opener.location.reload();
    window.close();
    </script>
    <%
    }
    else {
      //jxp = jxp.BuildObject();
//------------------------------------------------------
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>设置票号</title>
<script language="javascript" type="text/javascript">
function submitForm(form) {
  /*
  form.Actions.value = "ModifyPH";
  form.action = "setPH.jsp";
  form.submit();
  */
  /*
  <input name="NextNode" type="hidden" id="NextNode" value="">
  <input name="TargetURL" type="hidden" id="TargetURL">
  <input name="ServiceType" type="hidden" id="ServiceType">
  <input name="BizDaoName" type="hidden" id="BizDaoName" value="com.ydtf.dmis.jxp.NewJxp">
  <input name="CurrentNode" type="hidden" id="CurrentNode" value="<%=intCurrNodeID%>">
  <input name="WorkflowID" type="hidden" id="WorkflowID" value="<%=intWorkflowID%>">
  <input name="YWID" type="hidden" value="<%=strYWID%>"/>
  <input name="ID" type="hidden" value="<%=strYWID%>"/>
  <input name="RouteListID" type="hidden" id="RouteListID" value="<%=intRouteListID%>">
  <input name="Participant" type="hidden" value="<%=strUserID%>" id="Participant">
  */
  var openerForm = opener.form;
  if (typeof(openerForm) == "undefined") {
    form.ServiceType.value = "SaveOnly";
    form.TargetURL.value = "/jxp/ZD/done.jsp?target="+3;
    form.action = "/workflow/WorkflowService";
    form.submit();
    opener.location.reload();
    window.close();
  }
  else {
    opener.buildOneField("PH", form.PH.value);
    opener.form.ServiceType.value = "SaveOnly";
    opener.form.TargetURL.value = "/jxp/ZD/done.jsp?target="+3;
    opener.form.action = "/workflow/WorkflowService";

    opener.form.submit();
    window.close();
  }
}
</script>
</head>

<body>
<table width="500"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><form name="form" id="form" method="post" action="">
      <table width="100%"  border="1">
        <tr>
          <td height="20"><div align="center">申请单位：<%=jxp.getSQDW() %></div></td>
        </tr>
        <tr>
          <td height="20"><div align="center">设备名称：<%=jxp.getSBMC()%></div></td>
        </tr>
        <tr>
          <td height="20"><div align="center">编号：</div></td>
        </tr>
        <tr>
          <td height="20"><div align="center">
            <select name="select">
              <option value="2004" selected>2004</option>
              <option value="2005">2005</option>
              <option value="2006">2006</option>
            </select>
            <input name="PH" type="text" id="PH" size="10" value="<%=jxp.isEmptyPH()?jxp.getYWID():jxp.getPH() %>">
</div></td>
        </tr>
        <tr>
          <td height="20"><div align="center">
              <input type="button" name="Button" value="确定" onClick="submitForm(this.form)">
              <input type="button" name="Button" value="取消" onClick="javascript:window.close()">
              <input name="Actions" type="hidden" id="Actions">
              <input name="YWID" type="hidden" id="YWID" value="<%=strYWID%>">

              <!--  ======================  -->
              <input name="NextNode" type="hidden" id="NextNode" value="">
              <input name="TargetURL" type="hidden" id="TargetURL">
              <input name="ServiceType" type="hidden" id="ServiceType">
              <input name="BizDaoName" type="hidden" id="BizDaoName" value="com.ydtf.dmis.jxp.NewJxp">
              <input name="CurrentNode" type="hidden" id="CurrentNode" value="<%=intCurrNodeID%>">
              <input name="WorkflowID" type="hidden" id="WorkflowID" value="<%=intWorkflowID%>">
              <input name="YWID" type="hidden" value="<%=strYWID%>"/>
              <input name="ID" type="hidden" value="<%=strYWID%>"/>
              <input name="RouteListID" type="hidden" id="RouteListID" value="<%=intRouteListID%>">
              <input name="Participant" type="hidden" value="<%=strUserID%>" id="Participant">
              <!--  ======================  -->
          </div></td>
        </tr>
      </table>
    </form></td>
  </tr>
</table>
<blockquote>&nbsp;</blockquote>
</body>
</html>
<%
}
%>
