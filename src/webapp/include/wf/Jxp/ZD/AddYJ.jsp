<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
 * 程序：李升华
 * 时间：2004-12-27
 * 说明：常用批示语修改成功页面
 */
%>
<%@ page import="java.util.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>
<%
	String strTYPE = Tools.toUTF8(request.getParameter("TYPE"));
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>增加常用批示语页面</title>
<script src="../script/common.js"></script>
<script language="javascript">
function submitForm(){
	strMenu = form.MENU.value;
	if(strMenu == ""){
		window.alert("请输入分类名称！");
	}else{
		form.action = "AddYJ_Done.jsp";
		form.submit();
	}
}
</script>

</head>

<body>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><jsp:include flush="true" page="title.jsp"></jsp:include></td>
  </tr>
  <tr valign="top"><td width="108%" colspan="2"><table border=0><tr>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0"><a href="ListYJ.jsp?TYPE=<%=strTYPE%>" style="text-decoration:none ">&nbsp<font color=blue>退&nbsp;&nbsp;出</font>&nbsp</a></td>
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
                <td colspan="2"><form name="form" method="post">
				<div align="center"><font size="5" color="#008000" face="幼圆">常用意见库</font></div><br>
                  <table width="91%" align="center"  border="1">
				  <tr valign="top"><td height=30 width="14%" valign="middle"><br>
<div align="center"><font size="2">分&nbsp;&nbsp;&nbsp;&nbsp;类</font></div></td><td width="86%" valign="middle"><br>
<font size="2"><input type="text" name="MENU" style="width=600"></font></td></tr>

<tr valign="top"><td height=30 width="14%" valign="middle"><br>
<div align="center"><font size="2">项&nbsp;&nbsp;&nbsp;&nbsp;目</font></div></td><td width="86%" valign="middle"><br>
<font size="2"><input type="text" name="ITEM" style="width=600"></font></td></tr>

<tr valign="top"><td height=80 nowrap width="14%" valign="middle"><br>
<div align="center"><font size="2">意见列表</font></div></td><td width="86%" valign="middle"><br>
<font size="2"><textarea name="LIST" STYLE="width=600;height=150"></textarea></font></td></tr>
                  </table>
				  <input type="hidden" name="TYPE" value="<%=strTYPE%>">
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
  