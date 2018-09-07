<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
 * 标题：主页面
 * 描述：工作流主页面
 * 时间：2005-10-25 17:03:40
 * 版权：
 */
%>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.youngbook.common.wf.engines.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.ydtf.dmis.workflow.Workflow_Data_Jxp" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="java.util.*;" %>
<%
int intWorkflowID = Integer.parseInt(request.getParameter("WorkflowID"));  //获得工作流编号
int intCurrNodeID = Integer.parseInt(request.getParameter("CurrNodeID"));  //获得当前节点编号
int intRouteListID = Integer.parseInt(request.getParameter("RouteListID"));  //获得RouteList编号
String strYWID = request.getParameter("YWID");  //获得业务编号
Workflow_Data_Jxp objWorkflow_Data_Jxp = new Workflow_Data_Jxp();
if (strYWID != null && !strYWID.equals("")) {
  objWorkflow_Data_Jxp.setID(Integer.parseInt(strYWID));
  List listWorkflow_Data_Jxp = objWorkflow_Data_Jxp.queryExact();
  objWorkflow_Data_Jxp = (Workflow_Data_Jxp)listWorkflow_Data_Jxp.get(0);
}
%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/style.css" rel="stylesheet" type="text/css">
<script src="../script/common.js"></script>
</head>

<body>
<p align="center" class="TitleStyle">玉溪供电局配网停电检修申请表</p>
<form name="form" id="form" method="post" action="/workflow/WorkflowService">
<table width="100%" border="0" cellpadding="0" cellspacing="0" >
    <tr>
      <td width="80%" height="30">&nbsp;</td>
      <td><span class="BoldStyle">NO:12345</span></td>
    </tr>
    <tr>
      <td colspan="2"><table width="100%" border="0" cellpadding="2" cellspacing="1" class="TableStyle" >
        <tr>
          <td width="12%" height="30">		    <div align="center">
			  <a href="javascript:;">申请单位</a>
			</div>
		  </td>
          <td colspan="2"><input name="textfield2" type="text" class="InputeStyle" size="40"></td>
          <td width="14%"><div align="center">申请人</div></td>
          <td width="12%"><input name="textfield22" type="text" class="InputeStyle"></td>
          <td width="10%"><div align="center">受理人</div></td>
          <td width="12%"><input name="textfield23" type="text" class="InputeStyle"></td>
        </tr>
        <tr>
          <td height="30"><div align="center">停电设<br>
            备名称</div></td>
          <td colspan="7"><table width="98%" cellpadding="1" cellspacing="1" class="TableStyle" >
            <tr>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
          </table>            <p>
            <input type="button" name="Button" value="添加设备">
          </p>            </td>
          </tr>
        <tr>
          <td height="30"><div align="center">安全措施</div></td>
          <td colspan="7"><p>
            <textarea name="textfield" cols="80" rows="5" wrap="VIRTUAL" class="InputeStyle"></textarea>
            </p>            </td>
          </tr>
        <tr>
          <td height="30"><div align="center">停电范围</div></td>
          <td colspan="7"><p>
            <textarea name="textarea" cols="80" rows="5" wrap="VIRTUAL" class="InputeStyle"></textarea>
</p>            </td>
          </tr>
        <tr>
          <td height="30" rowspan="3"><div align="center">计划<br>
              工作<br>
            时间</div></td>
          <td width="12%" height="30"><div align="center">计划时间</div></td>
          <td height="30" colspan="6">		    <table width="100%" border="0" cellpadding="0" cellspacing="0" >
            <tr>
              <td width="350">
			  <jsp:include flush="false" page="../Common/TimeYMDHM.jsp">
                <jsp:param name="ID" value="JHSJQ"></jsp:param>
              </jsp:include>			  </td>
              <td><div align="center">至</div></td>
              <td width="350">
			  <jsp:include flush="false" page="../Common/TimeYMDHM.jsp">
			    <jsp:param name="ID" value="JHSJZ"></jsp:param>
			  </jsp:include></td>
              </tr>
          </table></td>
          </tr>
        <tr>
          <td height="30"><div align="center">更改时间</div></td>
          <td height="30" colspan="6"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
            <tr>
              <td width="350">
                <jsp:include flush="false" page="../Common/TimeYMDHM.jsp">
                <jsp:param name="ID" value="GGSJQ"></jsp:param>
                </jsp:include>
              </td>
              <td><div align="center">至</div></td>
              <td width="350">
                <jsp:include flush="false" page="../Common/TimeYMDHM.jsp">
                <jsp:param name="ID" value="GGSJZ"></jsp:param>
              </jsp:include></td>
            </tr>
          </table></td>
          </tr>
        <tr>
          <td height="30"><div align="center">延期时间</div></td>
          <td height="30" colspan="6"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
            <tr>
              <td width="350">
                <jsp:include flush="false" page="../Common/TimeYMDHM.jsp">
                <jsp:param name="ID" value="YQSJQ"></jsp:param>
                </jsp:include>
              </td>
              <td><div align="center">至</div></td>
              <td width="350">
                <jsp:include flush="false" page="../Common/TimeYMDHM.jsp">
                <jsp:param name="ID" value="YQSJZ"></jsp:param>
              </jsp:include></td>
            </tr>
          </table></td>
          </tr>
        <tr>
          <td height="30" colspan="2"><div align="center">停复电联系方法</div></td>
          <td colspan="6"><textarea name="textarea2" cols="80" rows="3" wrap="VIRTUAL" class="InputeStyle"></textarea></td>
          </tr>        
      </table>
	  <div id="hiddenValue"></div>
      <p align="center">
        <input type="button" name="Button" value="提交" onClick="openLz('<%=intWorkflowID%>','<%=intCurrNodeID%>')">
        <input type="button" name="Submit2" value="保存">
        <input type="button" name="Submit4" value="取消">
</p></td>
    </tr>
  </table>
<input name="TargetURL" type="hidden" id="TargetURL">
<input name="ServiceType" type="hidden" id="ServiceType">
<input name="BizDaoName" type="hidden" id="BizDaoName" value="com.ydtf.dmis.workflow.Workflow_Data_Jxp">
<input name="CurrentNode" type="hidden" id="CurrentNode" value="<%=intCurrNodeID%>">
<input name="WorkflowID" type="hidden" id="WorkflowID" value="<%=intWorkflowID%>">
<input name="YWID" type="hidden" value=""/>
<input name="RouteListID" type="hidden" id="RouteListID" value="<%=intRouteListID%>">
<input name="Participant" type="hidden" value=<%=Tools.toUTF8(request.getParameter("UserID"))%> id="Participant">
<input name="NextNode" type="hidden" id="NextNode"/>
</form>
</body>
</html>
<script language="javascript">
function submitForm(form, target) {
  if (target == "SaveOnly") {
    form.TargetURL.value = "/Jxp/Done.jsp";
    form.action = "/workflow/WorkflowService";
    form.target = "_self";
    form.ServiceType.value = "SaveOnly";
    form.submit();
  }
  else if (target == "SaveForward") {
    form.TargetURL.value = "/Jxp/Done.jsp";
    form.action = "/workflow/WorkflowService";
    form.target = "_self";
    form.ServiceType.value = "SaveForward";
    form.submit();
  }
  else if (target == "Cancel") {
    form.TargetURL.value = "/Jxp/Done.jsp";
    form.action = "/workflow/WorkflowService";
    form.target = "_self";
    form.ServiceType.value = "Cancel";
    form.submit();
  }
  else if (target=="Reject"){
	form.TargetURL.value="/Jxp/Done.jsp";
    form.action = "/workflow/WorkflowService";
	form.target="_self";
	form.ServiceType.value="Reject";
	form.submit();
  }
    else if (target=="AutoForward"){
	form.TargetURL.value="/Jxp/Done.jsp";
    form.action = "/workflow/WorkflowService";
	form.target="_self";
	form.ServiceType.value="AutoForward";
	form.submit();
  }
  else if (target == "Over") {
    form.TargetURL.value = "/Jxp/Done.jsp";
    form.action = "/workflow/WorkflowService";
    form.target = "_self";
    form.ServiceType.value = "Over";
    form.submit();
  }
  else if (target == "ActionReport") {
    form.target = "_blank";
    form.YWID.value = form.ID.value;
    form.action = "ActionReport.jsp";
    form.submit();
  }
}
</script>