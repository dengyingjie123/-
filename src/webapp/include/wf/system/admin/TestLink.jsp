
<jsp:include page="../checkLogin.jsp"></jsp:include>
<link href="../style/common.css" rel="stylesheet" type="text/css">
<script>
//关闭窗口
function closeWindow() {
  window.close();
}
</script>

<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.admin.Config" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.youngbook.common.wf.common.Tools" %>
<%@ page import="java.sql.*" %>
<%@ page import="javax.servlet.http.*" %>
<%
//从Config.jsp页面得到各个参数
  String strDBAddr = request.getParameter("Addr");
  String strDBUserName = request.getParameter("User");
  String strDBPwd = request.getParameter("Pwd");
  String strSid = request.getParameter("Sid");
  String strUserTable = request.getParameter("TableName");
  String strUserName = request.getParameter("UserName");
  String strTrueName = request.getParameter("TrueName");

/**
 * 若用户名或用户真实姓名不为空，则根据这两个字段及相应的用户信息表名
 * 查询是否存在此数据库地址，若存在，则连接测试成功，否则连接测试失败，抛出异常
 */

  String strSQL = null;
  if (!strUserName.equals("")||!strTrueName.equals("")) {
    strSQL = "Select "+strUserName+","+strTrueName+" from "+strUserTable;
  }

/**
 *若用户名及用户真实姓名字段都为空，则查询所有，若存在从页面上得到的数据库地址则
 *连接成功，否则连接失败，抛出异常
 */
  else {
    strSQL = "Select * from dual";
  }
  Connection conn;
  Class.forName("oracle.jdbc.driver.OracleDriver");
  conn=DriverManager.getConnection("jdbc:oracle:thin:@"+strDBAddr+":1521:"+strSid, strDBUserName, strDBPwd);
  Statement st = conn.createStatement();
	try {
 	 ResultSet rs = st.executeQuery(strSQL);
	 %>
	 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><br>
      <br>
      <br>
      <table width="300"  border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td><table width="100%"  border="0" cellpadding="2" cellspacing="0" class="TableBorderStyle">
          <tr>
            <td class="TableTdStyle" height="23"><div align="center">
              <p align="left" class="TitleStyle">                系统提示: <br>
              </p>
              </div></td>
          </tr>
          <tr>
            <td class="TableTdBgStyle"><div align="center" class="TableBgStyle"><br>
                连接测试成功！<br>
                  <br>
                  [ <a href="1" onClick="closeWindow()">关闭窗口</a> ]<br>
            </div></td>
          </tr>
        </table></td>
      </tr>
    </table>
    <br></td>
  </tr>
</table>
	<%}
	catch (Exception E) {%>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><br>
      <br>
      <br>
      <table width="300"  border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td><table width="100%"  border="0" cellpadding="2" cellspacing="0" class="TableBorderStyle">
          <tr>
            <td class="TableTdStyle" height="23"><div align="center">
              <p align="left" class="TitleStyle">                系统提示: <br>
              </p>
              </div></td>
          </tr>
          <tr>
            <td class="TableTdBgStyle"><div align="center" class="TableBgStyle"><br>
                连接测试发生错误，信息如下：<%=E.getMessage()%><br>
                  <br>
                  [ <a href="1" onClick="closeWindow()">关闭窗口</a> ]<br>
            </div></td>
          </tr>
        </table></td>
      </tr>
    </table>
    <br></td>
  </tr>
</table>
	<%}
	finally{
	if (st!=null){
		st.close();
	}
	conn.close();
	}
%>



