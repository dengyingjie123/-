<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
 * 程序：李升华
 * 时间：2004-12-27
 * 说明：查看常用批示语页面
 */
%>
<%@ page import="java.util.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>
<%
	int intID = Integer.parseInt(request.getParameter("ID"));
	String strMenu = "";
	String strItem = "";
	String strList = "";
	String strType = "";
	
	JxpYJ jxpYJ = new JxpYJ();
	jxpYJ.setID(intID);
	List listYJ = jxpYJ.queryExact();
	Iterator itYJ = listYJ.iterator();
	if(itYJ.hasNext()){
		jxpYJ = (JxpYJ)itYJ.next();
		strMenu = jxpYJ.getMENU();
		strItem = jxpYJ.getITEM();
		strList = jxpYJ.getLIST();
		strType = jxpYJ.getTYPE();
	}
	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看常用批示语页面</title>
<script src="../script/common.js"></script>

</head>

<body>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><jsp:include flush="true" page="title.jsp"></jsp:include></td>
  </tr>
  <tr valign="top"><td width="108%" colspan="2"><table border=0><tr>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0"><a href="ListYJ.jsp?TYPE=<%=strType%>" style="text-decoration:none ">&nbsp<font color=blue>退&nbsp;&nbsp;出</font>&nbsp</a></td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0"><a href="ModifyYJ.jsp?ID=<%=intID%>" style="text-decoration:none ">&nbsp<font color=blue>编&nbsp;&nbsp;辑</font>&nbsp</a></td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0"><a href="DeleteYJ_Done.jsp?ID=<%=intID%>&TYPE=<%=strType%>" style="text-decoration:none ">&nbsp<font color=blue>删&nbsp;&nbsp;除</font>&nbsp</a></td>
<%
	//查询得到相同类型（营运科或保护）的上一个意见,若上一个id在数据库中查询到则不显示此按扭
  int upID = intID - 1;
  String strtype = "";
  label:if(upID > 0){
  	JxpYJ yj = new JxpYJ();
  	yj.setID(upID);
	List listyj = yj.queryExact();
	Iterator ityj = listyj.iterator();
	if(ityj.hasNext()){
		yj = (JxpYJ)ityj.next();
		strtype = yj.getTYPE();
	}
	while((!strtype.equals(strType)) && (upID > 0)){
		upID--;
		JxpYJ jxpyj = new JxpYJ();
  		jxpyj.setID(upID);
		List list = jxpyj.queryExact();
		Iterator it = list.iterator();
		if(it.hasNext()){
			jxpyj = (JxpYJ)it.next();
			strtype = jxpyj.getTYPE();
		}else{
				break label;
		}
	}
%>
<td STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)' bgcolor='9F9FE0'><a href="queryYJ.jsp?ID=<%=upID%>" style="text-decoration:none "><font color=blue>&nbsp上一个&nbsp</font></td>
<%
	}else{
%>
<td STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)' bgcolor='9F9FE0'><a href="ListYJ.jsp?TYPE=<%=strType%>" style="text-decoration:none "><font color=blue>&nbsp上一个&nbsp</font></td>
<%
	}
%>

<%
	//查询得到相同类型（营运科或保护）的下一个意见,若下一个id在数据库中查询到则不显示此按扭
  int nextID = intID + 1;
  JxpYJ nextyj = new JxpYJ();
  String nexttype = "";
  label2:if(nextID < (nextyj.getMaxID())){  	
  	nextyj.setID(nextID);
	List nextlist = nextyj.queryExact();
	Iterator nextit = nextlist.iterator();
	if(nextit.hasNext()){
		nextyj = (JxpYJ)nextit.next();
		nexttype = nextyj.getTYPE();
	}
	while((!nexttype.equals(strType)) && (nextID < (nextyj.getMaxID()))){
		nextID++;
		JxpYJ xyj = new JxpYJ();
  		xyj.setID(nextID);
		List xlist = xyj.queryExact();
		Iterator xit = xlist.iterator();
		if(xit.hasNext()){
			xyj = (JxpYJ)xit.next();
			nexttype = xyj.getTYPE();
		}else{
			break label2;
		}
	}
%>
<td STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)' bgcolor='9F9FE0'><a href="queryYJ.jsp?ID=<%=nextID%>" style="text-decoration:none "><font color=blue>&nbsp下一个&nbsp</font></td>
<%
	}else{
%>
<td STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)' bgcolor='9F9FE0'><a href="ListYJ.jsp?TYPE=<%=strType%>" style="text-decoration:none "><font color=blue>&nbsp下一个&nbsp</font></td>
<%
	}
%>
</tr></table></td></tr>
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
            <td><table width="80%" align="center"  border="0" cellspacing="0" cellpadding="0"> 
              <tr>
                <td colspan="2"><form name="form" method="post" action="ModifyYJ.jsp">
				<div align="center"><font size="5" color="#008000" face="幼圆">常用意见库</font></div><br>
                  <table align="center" width="91%"  border="1">
                    <tr valign="top"><td height=30 width="14%" valign="middle"><br>
<div align="center"><font size="2">分&nbsp;&nbsp;&nbsp;&nbsp;类</font></div></td><td width="86%" valign="middle"><br>
<font size="2"><%=strMenu%></font></td></tr>

<tr valign="top"><td height=30 width="14%" valign="middle"><br>
<div align="center"><font size="2">项&nbsp;&nbsp;&nbsp;&nbsp;目</font></div></td><td width="86%" valign="middle"><br>
<font size="2"><%=strItem%></font></td></tr>

<tr valign="top"><td height=80 nowrap width="14%" valign="middle"><br>
<div align="center"><font size="2">意见列表</font></div></td><td width="86%" valign="middle"><br>
<font size="2"><%=strList%></font></td></tr>
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
  