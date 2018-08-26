<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
 * 程序：李升华
 * 时间：2004-12-27
 * 说明：常用批示设置主页面
 */
%>
<%@ page import="java.util.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>常用批示设置</title>
<script src="../script/common.js"></script>

<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style></head>

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
            <td><table width="30%" align="center"  border="0" cellspacing="0" cellpadding="0"> 
              <tr>
                <td colspan="2"><form name="form" method="post" action="">
                  <table width="100%"  border="0">
                    <tr><td>&nbsp;</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td align="center">
						<a href="ListYJ.jsp?TYPE=营运科意见" style="text-decoration:none "><font face="黑体" size="5" color="#0066FF">营运安排批示设置</font></a>
					</td><tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td align="center">
						<a href="ListYJ.jsp?TYPE=保护专业批示" style="text-decoration:none"><font face="黑体" size="5" color="#0066FF">保护专业批示设置</font></a>
					</td></tr>
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
  