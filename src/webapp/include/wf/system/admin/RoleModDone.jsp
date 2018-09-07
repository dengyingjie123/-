<jsp:include page="../checkLogin.jsp"></jsp:include>
<link href="../style/common.css" rel="stylesheet" type="text/css">
<%
/**
* 程序：李扬
* 时间：2004-10-28
* 说明：修改角色成功页面
*
*/
%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.Iterator" %>
<%@ page import="java.sql.*" %>


<%
try {
  Role role = new Role();
  String strID = request.getParameter("ID");  //获得角色编号
  String strRoleName = request.getParameter("RoleName");  //获得角色名称
  String[] strUserList = request.getParameterValues("UserTo");  //获得该角色组所有用户
  if (strID == null || strID.equals("") ||
      strRoleName == null || strRoleName.equals("")) {
    throw new Exception("执行RoleModDone.jsp发生异常，不能获得角色编号或角色名称!");
  }
  role.setID(Integer.parseInt(strID));  //设置角色编号
  role.setRoleName(Tools.toUTF8(strRoleName));  //设置角色名称
  //将用户从数组形式改为|XXX|XXX|XXX|
  StringBuffer sbUser = new StringBuffer();
  for (int i = 0; strUserList != null && i < strUserList.length; i++) {
    sbUser.append("|");
    sbUser.append(Tools.toUTF8(strUserList[i]));
  }
  //添加最后一个“|”
  if (sbUser.length() > 0) {
    sbUser.append("|");
  }
  //设置角色包含的用户
  role.setUserList(sbUser.toString());
  int intResult = 0;
  //更新角色
  intResult = role.update();
  if (intResult == 1) {
%>
<table width="300"  border="0" align="center" cellpadding="0" cellspacing="0" id="bg">
  <tr>
    <td><table width="100%"  border="0" cellpadding="0" cellspacing="1" class="TableBorderStyle">
        <tr>
          <td height="23" class="TableTdStyle">系统提示:</td>
        </tr>
        <tr>
          <td class="TableBgStyle"><div align="center"><br>
  修改角色成功!<br>
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
  out.print("修改失败，信息如下："+e.getMessage());
  out.print("<a href='javascript:history.back();'>返回</a>");
}
%>

