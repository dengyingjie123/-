<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.common.Tools" %>
<%
String strUserName = (String)session.getAttribute("xingming");
%>
<style type="text/css">
<!--
.linkTitle {
	font-size: 12px;
	color: #FFFFFF;
	text-decoration: none;
}
.txt_White {
	font-size: 12px;
	color: #FFFFFF;
}
.style1 {color: #FFFFFF}
-->
</style>

<table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="88" rowspan="3"><img src="../image/T1.jpg" width="144" height="88"></td>
        <td><img src="../image/T1_JXP.jpg" width="543" height="34"></td>
      </tr>
      <tr>
        <td height="28"><table width="100%"  border="0" cellpadding="0" cellspacing="0" bgcolor="#1B8AC9">
            <tr>
              <td height="28"><div align="center"><a href="index.jsp" class="linkTitle">主页</a></div></td>
              <td><div align="center"><a href="slsq.jsp" class="linkTitle">受理申请</a></div></td>
              <td><div align="center"><a href="lzsp.jsp" class="linkTitle">流转审批</a></div></td>
              <td><div align="center"><a href="ddzx.jsp" class="linkTitle">调度执行</a></div></td>
              <td><div align="center"><a href="SetYJ.jsp" class="linkTitle">常用批示设置</a></div></td>
              <td><div align="center"><a href="search.jsp" class="linkTitle">查询</a></div></td>
              <td><div align="center"><a href="javascript:" class="linkTitle">统计</a></div></td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td><table width="100%"  border="0" cellpadding="0" cellspacing="0" bgcolor="#666666" class="txt_White">
            <tr>
              <td width="103" height="26"><div align="center">Welcome: <%=strUserName %></div></td>
              <td width="151"><div align="center"><%=Tools.getDate() %></div></td>
              <td width="198"><div align="center"></div></td>
              <td width="53"><div align="center"><a href="javascript:" class="linkTitle">帮助</a></div></td>
              <td width="66"><div align="center"><a href="javascript:" class="linkTitle">修改密码</a></div></td>
              <td width="66"><div align="center"><a href="../index.jsp" class="linkTitle">退出系统</a></div></td>
            </tr>
        </table></td>
      </tr>
</table>
