<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>
<%
String strViewID=request.getParameter("ViewID");
int intWorkflowID = 1;
List listjxp=new ArrayList();
if (strViewID==null||strViewID.equals(""))
{
  strViewID="JXPSQ_ZDSP";
}
if (strViewID.equals("JXPSQ_BDWSH"))
{
NewJxp jxp=new NewJxp();
jxp.setSQDW((String)session.getAttribute("dangwei"));
listjxp=jxp.getselfjxp(6);//本单位票面审核
}
if (strViewID.equals("JXPSQ_ZDSP")){
NewJxp jxp=new NewJxp ();
jxp.setSQDW((String)session.getAttribute("dangwei"));
listjxp=jxp.getselfjxp(0);//中调核审中的票
}
if (strViewID.equals("JXPSQ_All")){//上报单位的所有申请
NewJxp jxp=new NewJxp ();
jxp.setSQDW((String)session.getAttribute("dangwei"));
listjxp=jxp.queryExact();
}
if (strViewID.equals("JXPSQ_YZX")){//已执行的申请
NewJxp jxp=new NewJxp ();
jxp.setSQDW((String)session.getAttribute("dangwei"));
listjxp=jxp.getselfjxp(2);
}
if (strViewID.equals("JXPSQ_YZF")){//已作费的申请
NewJxp jxp=new NewJxp ();
jxp.setSQDW((String)session.getAttribute("dangwei"));
listjxp=jxp.getselfjxp(3);
}
if (strViewID.equals("JXPSQ_XGAll")){//相关单位审核
NewJxp jxp=new NewJxp ();
jxp.setSQDW((String)session.getAttribute("dangwei"));
listjxp=jxp.getselfjxp(7);
}
if (strViewID.equals("JXPSQ_CTX")){//重填写
NewJxp jxp=new NewJxp();
jxp.setSQDW((String)session.getAttribute("dangwei"));
listjxp=jxp.getselfjxp(8);

}
%>
<html>
<head>
<meta http-equiv='pragma' content='no-cache'>   <STYLE type=text/css>    A:link {text-decoration:none;}     A:visited {text-decoration:none;}     A:active {text-decoration:none;}     A:hover {text-decoration:underline;}       A.a01:link {text-decoration:underline;}     A.a01:visited {text-decoration:underline;}     A.a01:active {text-decoration:underline;}     A.a01:hover {text-decoration:underline;}      border-right-style: 1px solid rgb(0,0,0);  border-top-style: 1px solid rgb(200,200,255);  border-bottom-style: 1px solid rgb(0,0,0) }    table{font-size:9pt;} tr{font-size:9pt;line-height:1.5;} td{font-size:9pt;font-family:"宋体";line-height:1.5;}    .p9 {font-size:9pt}  .p10 {font-size:10pt;} </STYLE><title>检修票远程申请</title>

</head>
<body text="#000000" bgcolor="#FFFFFF" topmargin=0 leftmargin=0>

<TABLE style="FONT-SIZE: 11pt; FONT-FAMILY: 宋体" cellSpacing=0 cellPadding=0
width="100%" border=0>
  <TBODY>
  <TR vAlign=top>
    <TD
    style="BORDER-RIGHT: rgb(0,0,0) 1px solid; BORDER-TOP: rgb(200,255,255) 1px solid; BORDER-LEFT: rgb(200,255,255) 1px solid; BORDER-BOTTOM: rgb(0,0,0) 1px solid"
    vAlign=center width="15%"><IMG height=83 src="../image/T1.jpg"
      width="100%" border=0></TD>
    <TD vAlign=center width="88%">
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR vAlign=top>
          <TD
          style="BORDER-RIGHT: rgb(0,0,0) 1px solid; BORDER-TOP: rgb(200,255,255) 1px solid; BORDER-LEFT: rgb(200,255,255) 1px solid; BORDER-BOTTOM: rgb(0,0,0) 1px solid"
          vAlign=center width="100%" height=40><IMG
            src="../image/T1_WebDD.jpg" border=0></TD></TR>
        <TR vAlign=top>
          <TD
          style="BORDER-RIGHT: rgb(0,0,0) 1px solid; BORDER-TOP: rgb(200,255,255) 1px solid; BORDER-LEFT: rgb(200,255,255) 1px solid; BORDER-BOTTOM: rgb(0,0,0) 1px solid"
          vAlign=center width="100%" bgColor=#339933 height=25>
            <TABLE style="FONT-SIZE: 11pt; FONT-FAMILY: 宋体" cellSpacing=0
            cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR>
                <TD align=middle width="10%"><A
                  href="index.jsp"><FONT
                  color=white>主页</FONT></A></TD>
                <TD align=middle width="10%"></TD>
                <TD align=middle width="10%"><A
                  href="search.jsp"><FONT
                  color=white>检修票查询</FONT></A></TD>
                <TD align=middle width="10%"></TD>
                <TD align=middle width="10%"></TD>
                <TD align=middle width="10%"></TD>
                <TD align=middle width="10%"></TD></TR></TBODY></TABLE></TD></TR>
        <TR vAlign=top>
          <TD vAlign=center width="100%" bgColor=#5f5f5f height=20>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR vAlign=top>
                <TD width="58%" colSpan=2><FONT
                  color=#ffffff>Welcome：</FONT><FONT
                  color=#ffffff><%=(String)session.getAttribute("dangwei")%></FONT> <FONT
                  color=#ffffff><%=(String)session.getAttribute("xingming")%></FONT> <FONT
                  color=#ffffff><%=Tools.getTime()%></FONT></TD>
                <TD width="42%">
                  <DIV align=right><A href="http://localhost/JXP_SQ.htm"
                  target=_blank><FONT color=#ffffff>帮助</FONT></A>
				  <A onclick='F_URL = "/" + document.forms[0].SYS_Path.value + "/User.nsf/ModifySQUserPassword?OpenForm&amp;UserID=" + document.forms[0].SYS_UserID.value&#13;width=400;height=250;LeftNum = (screen.width - width)/2;TopNum = (screen.height - height)/2;&#13;TX = "left=" + LeftNum + ",top=" + TopNum + ",width=" + width + ",height=" + height + ",status=no,scrollbars=no,resizable=yes";&#13;window.open(F_URL,"",TX);&#13;return false;'
                  href="http://localhost/DMIS/WebDD.nsf/"><FONT
                  color=#ffffff>修改密码</FONT></A> <A
                  onclick='SetCookie("SYS_USERUNID","")&#13;window.location.href = "/" + document.forms[0].SYS_Path.value + "/WebDD.nsf/Login?OpenForm"&#13;return false;'
                  href="loginout.jsp"><FONT
                  color=#ffffff>退出系统</FONT></A> <FONT
                color=#ffffff></FONT></DIV></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>


<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr valign="top"><td style="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" width="100%"><table border=0><tr>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0"><a href=search.jsp?ViewID=JXPSQ_ZDSP>&nbsp<font color=blue>中调审批中的申请</font>&nbsp</a></td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0"><a href=search.jsp?ViewID=JXPSQ_DDZX>&nbsp<font color=blue>调度台执行的申请</font>&nbsp</a></td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0"><a href=search.jsp?ViewID=JXPSQ_YZX>&nbsp<font color=blue>已执行的申请</font>&nbsp</a></td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0"><a href=search.jsp?ViewID=JXPSQ_YZF>&nbsp<font color=blue>已作废的申请</font>&nbsp</a></td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0"><a href=search.jsp?ViewID=JXPSQ_All>&nbsp<font color=blue>上报单位所有申请</font>&nbsp</a></td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0"><a href=search.jsp?ViewID=JXPSQ_XGAll>&nbsp<font color=blue>审核相关单位申请</font>&nbsp</a></td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0"><a href=search.jsp?ViewID=JXPSQ_BDWSH>&nbsp<font color=blue>待本单位审核的申请</font>&nbsp</a></td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0"><a href=search.jsp?ViewID=JXPSQ_CTX>&nbsp<font color=blue>需要重填写的申请</font>&nbsp</a></td>
</tr></table></td></tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr valign="top">
  <td style="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" height=400 width="100%">
<input name="ViewID" value="JXPSQ_ZDSP" size=1 type=hidden>
  <font size="2" color="#0021bf">
	<Table border=0 cellpadding=2 cellspacing=1 width="100%" style="font-family: 宋体;">
	  <tr><td colspan=8 align=center style="line-height:1">
		<font color=blue size=3><%
		if (strViewID.equals("JXPSQ_ZDSP"))
		{
		out.print("中 调 审 批 中 的 申 请");
		}
		if (strViewID.equals("JXPSQ_DDZX")){
		out.print("调 度 台 执 行 的 申 请");
		}
		if (strViewID.equals("JXPSQ_YZX")){
		  out.print("已 执 行 的 申 请");
		}
		if (strViewID.equals("JXPSQ_YZF")){
		  out.print("已 作 废 的 申 请");
		}
		if (strViewID.equals("JXPSQ_All")){
		  out.print("上 报 单 位 所 有 申 请");
		}
		if (strViewID.equals("JXPSQ_XGAll")){
		  out.print("审 核 相 关 单 位 申 请");
		}
		if (strViewID.equals("JXPSQ_BDWSH")){
		  out.print("待 本 单 位 审 核 申 请");
		}
		if (strViewID.equals("JXPSQ_CTX")){
		  out.print("需 要 重 填 写 申 请");
		}
		%></font></td></tr>
		<tr bgcolor=#C0C0C0 style="font-family: 宋体;color:green">
		  <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
			序号</td>
			<td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
			  申请时间</td>
			  <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
				申请人</td>
				<td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
				  检修设备名称</td><td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
					工作内容</td><td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
					  停电范围</td><td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
						申请开工时间</td><td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
						  申请完工时间</td></tr>
						  <%Iterator itjxp=listjxp.iterator();
						  int i=1;
						 while (itjxp.hasNext()){
						   NewJxp jxp=(NewJxp)itjxp.next();
						   %>
						  <tr bgcolor=#F2F2F2>
							<td align=center width=30><%=i%></td>
							<td align=left width=60>
							  <a href=bdwsh.jsp?WorkflowID=<%=intWorkflowID%>&ID=<%=jxp.getID()%>&Op=<%=strViewID%>><font color=black><%=jxp.getSQSJ()%></font></a>
							</td>
							<td align=center width=60>
							  <a href=bdwsh.jsp?WorkflowID=<%=intWorkflowID%>&ID=<%=jxp.getID()%>&Op=<%=strViewID%>><font color=black><%=jxp.getSQR()%></font></a>
							</td>
							<td align=center width=150><a href=bdwsh.jsp?WorkflowID=<%=intWorkflowID%>&ID=<%=jxp.getID()%>&Op=<%=strViewID%>><font color=black><%=jxp.getSBMC()%></font></a></td>
							<td align=left><a href=bdwsh.jsp?WorkflowID=<%=intWorkflowID%>&ID=<%=jxp.getID()%>&Op=<%=strViewID%>><font color=black><%=jxp.getGZNR()%></font></a></td>
							<td align=left width=200><a href=bdwsh.jsp?WorkflowID=<%=intWorkflowID%>&ID=<%=jxp.getID()%>&Op=<%=strViewID%>><font color=black><%=jxp.getTDFW()%></font></a></td>
							<td width=60 nowrap><a href=bdwsh.jsp?WorkflowID=<%=intWorkflowID%>&ID=<%=jxp.getID()%>&Op=<%=strViewID%>><font color=black><%=jxp.getSQTDSJ()%></font></a></td>
							<td width=60 nowrap><a href=bdwsh.jsp?WorkflowID=<%=intWorkflowID%>&ID=<%=jxp.getID()%>&Op=<%=strViewID%>><font color=black><%=jxp.getSQFDSJ()%></font></a></td></tr>
							<%
						  i++;
						  }%>
						  <tr><td align=right colspan=8><font color=black>共<font color=blue><%=listjxp==null?"0":String.valueOf(listjxp.size())%></font>行</font></td></tr>


						</table><br>
<br>
</td></tr>
</table>

</form>
</body>
</html>
