<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>
<%
NewJxp jxp=new NewJxp();
jxp.setSQDW((String)session.getAttribute("dangwei"));
List lsbdwsh=jxp.getselfjxp(6);//本单位票面审核
List lshgdwsh=jxp.getselfjxp(7);//相关单位审核
List lsctx=jxp.getselfjxp(8);//重填写的
%>
<HTML><HEAD><TITLE>检修票远程申请</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<META http-equiv=pragma content=no-cache>
<STYLE type=text/css>
TABLE {
	FONT-SIZE: 9pt
}
TR {
	FONT-SIZE: 9pt; LINE-HEIGHT: 1.5
}
TD {
	FONT-SIZE: 9pt; LINE-HEIGHT: 1.5; FONT-FAMILY: "宋体"
}
.p9 {
	FONT-SIZE: 9pt
}
.p10 {
	FONT-SIZE: 10pt
}
</STYLE>



<META content="MSHTML 6.00.2900.2523" name=GENERATOR>
<link href="../css/common.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.style1 {color: #FFFFFF}
-->
</style>
</HEAD>
<BODY text=#000000 bgColor=#ffffff leftMargin=0 topMargin=0>

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
				            <a href="javascript:" class="style1">修改密码</a> <a href="../index.jsp" class="style1">退出系统</a> </DIV></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  <TBODY>
  <TR vAlign=top>
    <TD
    style="BORDER-RIGHT: rgb(0,0,0) 1px solid; BORDER-TOP: rgb(200,200,255) 1px solid; BORDER-LEFT: rgb(200,200,255) 1px solid; BORDER-BOTTOM: rgb(0,0,0) 1px solid"
    width="100%" bgColor=#9f9fe0>&nbsp;</TD></TR></TBODY></TABLE>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  <TBODY>
  <TR vAlign=top>
    <TD
    style="BORDER-RIGHT: rgb(0,0,0) 1px solid; BORDER-TOP: rgb(200,200,255) 1px solid; BORDER-LEFT: rgb(200,200,255) 1px solid; BORDER-BOTTOM: rgb(0,0,0) 1px solid"
    vAlign=center width="100%" height=400>
      <DIV align=center>
      <TABLE style="BORDER-COLLAPSE: collapse" borderColor=#008000 cellSpacing=0
      cellPadding=0 border=0>
        <TBODY>
        <TR vAlign=top>
          <TD vAlign=center width=605 height=80>
            <DIV align=center><B><FONT face=幼圆 color=#008000
            size=4>欢 迎 使 用 本 系 统</FONT></B></DIV></TD></TR>
        <TR vAlign=top>
          <TD vAlign=center width=605>
            <TABLE width=200 align=center border=0>
              <TBODY>
              <TR>
                <TD
                style="BORDER-RIGHT: rgb(0,0,0) 1px solid; BORDER-TOP: rgb(200,200,255) 1px solid; BORDER-LEFT: rgb(200,200,255) 1px solid; BORDER-BOTTOM: rgb(0,0,0) 1px solid"
                bgColor=#9f9fe0>&nbsp;<A
                  href="sq.jsp?WorkflowID=1&Type=1"><FONT
                  color=black>检 修 票 申 请</font></A>&nbsp;&nbsp;</TD>
              </TR></TBODY></TABLE></TD></TR>
        <TR vAlign=top>
          <TD vAlign=center width=605>&nbsp;</TD></TR>
        <TR vAlign=top>
          <TD width=605>
            <TABLE width=200 align=center border=0>
              <TBODY>
              <TR>
                <TD
                style="BORDER-RIGHT: rgb(0,0,0) 1px solid; BORDER-TOP: rgb(200,200,255) 1px solid; BORDER-LEFT: rgb(200,200,255) 1px solid; BORDER-BOTTOM: rgb(0,0,0) 1px solid"
                bgColor=#9f9fe0>&nbsp;<A
                  href="sq.jsp?WorkflowID=1&Type=2"><FONT
                  color=black>新 设 备 投 产 申 请</FONT></A>&nbsp;&nbsp;</TD>
              </TR></TBODY></TABLE></TD></TR>
        <TR vAlign=top>
          <TD width=605>&nbsp;</TD></TR>
        <TR vAlign=top>
          <TD vAlign=center width=605>
            <TABLE width=200 align=center border=0>
              <TBODY>
              <TR>
                <TD
                style="BORDER-RIGHT: rgb(0,0,0) 1px solid; BORDER-TOP: rgb(200,200,255) 1px solid; BORDER-LEFT: rgb(200,200,255) 1px solid; BORDER-BOTTOM: rgb(0,0,0) 1px solid"
                bgColor=#9f9fe0>&nbsp;<a href=search.jsp?ViewID=JXPSQ_BDWSH><FONT
                  color=black>票 面 审 核（<%=lsbdwsh==null?"0":String.valueOf(lsbdwsh.size())%> 张票）</FONT></A>&nbsp;</TD></TR></TBODY></TABLE></TD></TR>
        <TR vAlign=top>
          <TD vAlign=center width=605>&nbsp;</TD></TR>
        <TR vAlign=top>
          <TD vAlign=center width=605>
            <TABLE width=200 align=center border=0>
              <TBODY>
              <TR>
                <TD
                style="BORDER-RIGHT: rgb(0,0,0) 1px solid; BORDER-TOP: rgb(200,200,255) 1px solid; BORDER-LEFT: rgb(200,200,255) 1px solid; BORDER-BOTTOM: rgb(0,0,0) 1px solid"
                bgColor=#9f9fe0>&nbsp;<A
                  href=search.jsp?ViewID=JXPSQ_XGAll><FONT
                  color=black>审核相关单位票（<%=lshgdwsh==null?"0":String.valueOf(lshgdwsh.size())%> 张票）</FONT></A>&nbsp;</TD></TR></TBODY></TABLE></TD></TR>
        <TR vAlign=top>
          <TD vAlign=center width=605>&nbsp;</TD></TR>
        <TR vAlign=top>
          <TD vAlign=center width=605>
            <TABLE width=200 align=center border=0>
              <TBODY>
              <TR>
                <TD
                style="BORDER-RIGHT: rgb(0,0,0) 1px solid; BORDER-TOP: rgb(200,200,255) 1px solid; BORDER-LEFT: rgb(200,200,255) 1px solid; BORDER-BOTTOM: rgb(0,0,0) 1px solid"
                bgColor=#9f9fe0>&nbsp;<A
                  href=search.jsp?ViewID=JXPSQ_CTX><FONT
                  color=black>重新填写的申请（<%=lsctx==null?"0":String.valueOf(lsctx.size())%> 张票）</FONT></A>&nbsp;</TD></TR></TBODY></TABLE></TD></TR>
        <TR vAlign=top>
          <TD width=605>&nbsp;</TD></TR>
        <TR vAlign=top>
          <TD width=605 height=200><FONT
      size=2></FONT></TD></TR></TBODY></TABLE></DIV></TD></TR></TBODY></TABLE></FORM></BODY></HTML>
