<jsp:include page="../checkLogin.jsp"></jsp:include>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.admin.Config" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="javax.servlet.http.*" %>


<%
//接收从Config.jsp页面传来的参数，并根据Config.java中的Write()方法写入XML中
  Config config1 = new Config();
  //设置数据库地址
  config1.setDBAddr(Tools.toUTF8(request.getParameter("Addr")));
  //设置数据库用户名
  config1.setDBUserName(Tools.toUTF8(request.getParameter("User")));
  //设置数据库密码
  config1.setDBPwd(Tools.toUTF8(request.getParameter("Pwd")));
  //设置SID
  config1.setSid(Tools.toUTF8(request.getParameter("Sid")));
  //设置用户表
  config1.setUserTable(Tools.toUTF8(request.getParameter("TableName")));
  //设置用户名字段
  config1.setUserName(Tools.toUTF8(request.getParameter("UserName")));
  //设置用户真实姓名字段
  config1.setTrueName(Tools.toUTF8(request.getParameter("TrueName")));
  //设置管理员密码
  config1.setAdminPwd(Tools.toUTF8(request.getParameter("AdminPwd")));
  //将数据库配置信息写入XML文件中
  config1.Write();
%>
<html>

<head>
<title>保存所输入的数据信息</title>
<link href="../style/common.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></head>
<body>
      <table width="400" align="center" border="0" cellpadding="" cellspacing="" id="bg">
	  <tr bgcolor="#FFFFFF">
           <td height="28" colspan="2" align="center"><font style="font-size:12pt"><strong>环境配置</strong></font><p></p></td>
		</tr>
        <tr>
          <td class="TableINBgStyle"><table width="100%"  border="0" cellspacing="1" cellpadding="1" class="TableBorderStyle">
		  <tr class="TableBgStyle">
              <td height="25"><div align="right">管理员密码：</div></td>
              <td>&nbsp;&nbsp;********</td>
            </tr>
            <tr class="TableBgStyle">
              <td height="25"><div align="right">数据库地址：</div></td>
              <td>&nbsp;&nbsp;<%=config1.getDBAddr()%></td>
            </tr>
            <tr class="TableBgStyle">
              <td height="25"><div align="right">数据库用户名：</div></td>
              <td>&nbsp;&nbsp;<%=config1.getDBUserName()%></td>
            </tr>
            <tr class="TableBgStyle">
              <td height="25"><div align="right">数据库密码：</div></td>
              <td>&nbsp;&nbsp;<%="********"%></td>
            </tr>
            <tr class="TableBgStyle">
              <td height="25"><div align="right">数据库SID ：</div></td>
              <td>&nbsp;&nbsp;<%=config1.getSid()%></td>
            </tr>
            <tr class="TableBgStyle">
              <td height="25"><div align="right">用户信息表名：</div></td>
              <td>&nbsp;&nbsp;<%=config1.getUserTable()%></td>
            </tr>
            <tr class="TableBgStyle">
              <td height="25"><div align="right">用户名字段名称：</div></td>
              <td>&nbsp;&nbsp;<%=config1.getUserName()%></td>
            </tr>
            <tr class="TableBgStyle">
              <td height="25"><div align="right">用户真实姓名字段名称：</div></td>
              <td>&nbsp;&nbsp;<%=config1.getTrueName()%></td>
            </tr>
          </table></td>
        </tr>
      </table>
      <div align="center"><br>
    <font style="font-size:11pt" color="#990066">修改数据库后，请重新启动Tomcat服务器</font></div></td>
  </body>

</html>
