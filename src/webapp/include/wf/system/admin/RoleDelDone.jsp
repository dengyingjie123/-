<jsp:include page="../checkLogin.jsp"></jsp:include>
<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
* 程序：李扬
* 时间：2004-10-28
* 说明：删除角色页面
*
*/
%>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.Iterator" %>
<%@ page import="java.sql.*" %>


<link href="../style/common.css" rel="stylesheet" type="text/css">
<%
try {
 Role role = new Role();
 String strID = request.getParameter("ID");  //获得角色编号
 if (strID == null || strID.equals("")) {
   throw new Exception("执行RoleDelDone.jsp页面发生异常，无法获得角色编号");
 }
 //设置所要删除的角色编号
 role.setID(Integer.parseInt(strID));
 //删除该角色
 int intResult = role.delete();
 if (intResult == 1) {
 //  out.print("<a href='ListRole.jsp'>删除成功！</a>");
%>
<table width="300"  border="0" align="center" cellpadding="0" cellspacing="0" id="bg">
  <tr>
    <td><table width="100%"  border="0" cellpadding="0" cellspacing="1" class="TableBorderStyle">
        <tr>
          <td height="23" class="TableTdStyle">系统提示:</td>
        </tr>
        <tr>
          <td class="TableBgStyle"><div align="center"><br>
  删除角色成功!<br>
              <br>
              [ <a href="ListRole.jsp">继续</a> ] <br>
              <br>
          </div></td>
        </tr>
    </table></td>
  </tr>
</table>
<%
 }
}
catch (Exception e) {
  //异常处理
  out.print("删除失败，信息如下："+e.getMessage());
  out.print("<a href='javascript:history.back();'>返回</a>");
}
%>

