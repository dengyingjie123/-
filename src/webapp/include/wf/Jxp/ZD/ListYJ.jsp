<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
 * 程序：李升华
 * 时间：2004-12-27
 * 说明：常用批示列表页面
 */
%>
<%@ page import="java.util.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>
<%
	String strTYPE = Tools.toUTF8(request.getParameter("TYPE"));
	JxpYJ jxpYJ = new JxpYJ();
	jxpYJ.setTYPE(strTYPE);
	List listYJ = jxpYJ.queryMENU();
	Iterator itJxpYJ = listYJ.iterator();
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>常用批示列表页面</title>
<script src="../script/common.js"></script>

</head>

<body>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><jsp:include flush="true" page="title.jsp"></jsp:include></td>
  </tr>
  <tr>
    <td><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><div align="center">
          <p>&nbsp;</p>
          </div></td>
      </tr>
      <tr>
        <td><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><table width="100%" align="center"  border="0" cellspacing="0" cellpadding="0"> 
              <tr>
                <td colspan="2"><form name="form" method="post" action="">
                  <table width="100%"  border="0">
                    <tr><td colspan=4 align=center><font color=blue size=3><%=strTYPE%></font></td></tr>
<tr><td colspan=4>
<table border=1 cellpadding=0 cellspacing=0 style="font-family: 宋体;"><tr>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0"><a href="AddYJ.jsp?TYPE=<%=strTYPE%>" style="text-decoration:none"><font color=blue>&nbsp新建常用意见项目&nbsp</font></a></td>
</tr></table>
</td></tr>
<tr bgcolor=#C0C0C0 style="font-family: 宋体;color:green"><td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap width=3%>序号</td>
															<td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap width=10%>分类</td>
															<td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap width=80%>项目</td></tr>
<%
	int intID = 0;
	int intCounter = 0;
	while(itJxpYJ.hasNext()){
		String strMENU = (String)itJxpYJ.next();
%>
<tr bgColor=#D9FFF5><td align="center">&nbsp;</td>
					<td colspan="2"><%=strMENU%></td></tr>
<%
	jxpYJ.setMENU(strMENU);
	jxpYJ.setID(Integer.MAX_VALUE);
	jxpYJ.setITEM("");
	jxpYJ.setLIST("");
	List ZongYJ = jxpYJ.queryExact();
	Iterator itZongYJ = ZongYJ.iterator();
	while(itZongYJ.hasNext()){
		intCounter++;
		jxpYJ = (JxpYJ)itZongYJ.next();
		intID = jxpYJ.getID();
		String strITEM = jxpYJ.getITEM();
		strITEM = strITEM.equals("")?"无标题":strITEM;
%>
<tr bgColor=#C2EFFF><td align="center"><%=intCounter%></td>
					<td align="center">&nbsp;</td>
					<td align="center"><a href="queryYJ.jsp?ID=<%=intID%>" style="text-decoration:none "><%=strITEM%></a></td></tr>
<%
	}
	}
%>
                  </table>
                </form></td>
              </tr>
            </table>
              <br>
              <br>
              <table width="100%"  border="0" cellpadding="0" cellspacing="0" id="jxp">
              <tr>
                <td>&nbsp;								</td>
              </tr>
            </table></td>
          </tr>
        </table></td>
      </tr>
    </table>

  