<%
/**
 * 程序：李扬
 * 时间：2004-10-28
 * 说明：增加角色成功页
 */
%>
<jsp:include page="../checkLogin.jsp"></jsp:include>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.Iterator" %>
<%@ page import="java.sql.*" %>


<%
try {
  Role role = new Role();
  String strID = request.getParameter("ID"); //获得角色编号
  String strRoleName = request.getParameter("RoleName");  //获得角色名称
  //检查角色编号和角色名的合法性
  if (strID == null && strID.equals("") ||
      strRoleName == null && strRoleName.equals("")) {
    throw new Exception("执行RoleAddDone.jsp页面发生异常，无法获得角色组编号或角色组名称！");
  }
  String[] strUser = request.getParameterValues("UserTo");  //获得用户
  StringBuffer sbUserList = new StringBuffer();
  //将用户从数组形式变为|XXX|XXX|XXX|
  for (int i = 0; strUser != null && i < strUser.length; i++) {
    sbUserList.append("|");
    sbUserList.append(Tools.toUTF8(strUser[i]));
  }
  //添加最后一个“|”
  if (sbUserList.length() > 0) {
    sbUserList.append("|");
  }
  //设置角色编号
  role.setID(Integer.parseInt(strID));
  //设置角色名称
  role.setRoleName(Tools.toUTF8(strRoleName));
  //设置该角色包含的用户
  role.setUserList(sbUserList.toString());
  int intResult = 0;
  //在数据库中插入该角色
  intResult = role.Insert();
  if (intResult == 1) {
%>
<html>
<head>
<title>添加角色成功</title>
<link href="../style/common.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></head>
<body>
<link href="../style/common.css" rel="stylesheet" type="text/css">
<table width="300"  border="0" align="center" cellpadding="0" cellspacing="0" id="bg">
  <tr>
    <td><table width="100%"  border="0" cellpadding="0" cellspacing="1" class="TableBorderStyle">
        <tr>
          <td height="23" class="TableTdStyle">系统提示:</td>
        </tr>
        <tr>
          <td class="TableBgStyle"><div align="center"><br>
  添加角色成功!<br>
              <br>
              [ <a href="ListRole.jsp">继续</a> ] <br>
              <br>
          </div></td>
        </tr>
    </table></td>
  </tr>
</table>
</body>

</html>
<%
  }

}
catch (Exception e) {
  //异常处理
 out.print("增加失败，信息如下："+e.getMessage());
 out.print("<a href='javascript:history.back();'>返回</a>");
}
%>
