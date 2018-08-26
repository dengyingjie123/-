<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
 * 程序：李升华
 * 时间：2004-12-27
 * 说明：修改常用批示语页面
 */
%>
<%@ page import="java.util.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>
<%
	int intID = Integer.parseInt(request.getParameter("ID"));
	
	JxpYJ jxpYJ = new JxpYJ();
	jxpYJ.setID(intID);
	List listYJ = jxpYJ.queryExact();
	Iterator itYJ = listYJ.iterator();
	if(itYJ.hasNext()){
		jxpYJ = (JxpYJ)itYJ.next();
	}	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看常用批示语页面</title>
<script src="../script/common.js"></script>
<script language="javascript">
function submitForm(){
	form.action = "ModifyYJ_Done.jsp";
	form.submit();
}
</script>

</head>

<body>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><jsp:include flush="true" page="title.jsp"></jsp:include></td>
  </tr>
  <tr valign="top"><td width="108%" colspan="2"><table border=0><tr>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0"><a href="ListYJ.jsp?TYPE=<%=jxpYJ.getTYPE()%>" style="text-decoration:none ">&nbsp<font color=blue>退&nbsp;&nbsp;出</font>&nbsp</a></td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0"><a href="javascript:submitForm()" style="text-decoration:none ">&nbsp<font color=blue>保&nbsp;&nbsp;存</font>&nbsp</a></td>
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
                <td colspan="2"><form name="form" method="post" >
				<div align="center"><font size="5" color="#008000" face="幼圆">常用意见库</font></div><br>
                  <table width="91%" align="center" border="1">
                    <tr valign="top"><td height=30 width="14%" valign="middle"><br>
<div align="center"><font size="2">分&nbsp;&nbsp;&nbsp;&nbsp;类</font></div></td><td width="86%" valign="middle"><br>
<font size="2"><input type="text" name="MENU" value="<%=jxpYJ.getMENU()%>" style="width=600"></font></td></tr>

<tr valign="top"><td height=30 width="14%" valign="middle"><br>
<div align="center"><font size="2">项&nbsp;&nbsp;&nbsp;&nbsp;目</font></div></td><td width="86%" valign="middle"><br>
<font size="2"><input type="text" name="ITEM" value="<%=jxpYJ.getITEM()%>" style="width=600"></font></td></tr>

<tr valign="top"><td height=80 nowrap width="14%" valign="middle"><br>
<div align="center"><font size="2">意见列表</font></div></td><td width="86%" valign="middle"><br>
<font size="2"><textarea name="LIST" STYLE="width=600;height=150"><%=jxpYJ.getLIST()%></textarea></font></td></tr>
<input type="hidden" name="TYPE" value="<%=jxpYJ.getTYPE()%>">
                  </table>
				  <input type="hidden" name="ID" value="<%=intID%>">
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
  