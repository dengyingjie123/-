<jsp:include page="../../checkLogin.jsp"></jsp:include>
<%
/**
 * 程序：李扬
 * 时间：2004-10-27
 * 说明：根据获得的工作流编号、节点编号对节点进行权限配置，
 *      可以增加角色和用户
 */
%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="org.jdom.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>工作流节点权限配置</title>
<script src="../../script/CheckText.js"></script>
<script language="javascript">
function submitForm(form) {
  //TValue=TValue.replace(/(^\s*)|(\s*$)/g, "");
    for (i = 0; i < document.form.RoleTo.length; i++) {
    //选中用户组
      document.form.RoleTo[i].selected = true;
    }
    for (i = 0; i < document.form.UserTo.length; i++) {
    //选中用户
      values = document.form.UserTo[i].value;
      values = values.replace(/(^\s*)|(\s*$)/g, "");
      document.form.UserTo[i].value = values;
      document.form.UserTo[i].selected = true;
    }
    form.action = "Node_QX_Done.jsp";
    form.submit();
}
function addRole() {
  var isHave = 0;
  if (document.form.RoleFrom.selectedIndex == -1) {
    window.alert("请选择所要添加的角色");
  }
  else {
  if (isHave == 0) {
    //添加角色
    document.form.RoleTo.options[document.form.RoleTo.length] =
      new Option(document.form.RoleFrom[document.form.RoleFrom.selectedIndex].text,
      document.form.RoleFrom[document.form.RoleFrom.selectedIndex].value);
    //删除可选角色列表中以选的角色
      document.form.RoleFrom.remove(document.form.RoleFrom.selectedIndex);
    }
  }
}
function removeRole() {
  if (document.form.RoleTo.selectedIndex == -1) {
    window.alert("请选择删除的角色");
  }
  else {
    document.form.RoleFrom.options[document.form.RoleFrom.length] =
      new Option(document.form.RoleTo[document.form.RoleTo.selectedIndex].text,
      document.form.RoleTo[document.form.RoleTo.selectedIndex].value);
    document.form.RoleTo.remove(document.form.RoleTo.selectedIndex);
  }
}
function addUser() {
  var isHave = 0;
  if (document.form.UserFrom.selectedIndex == -1) {
    window.alert("请选择所要添加的用户");
  }
  else {
  if (isHave == 0) {

    document.form.UserTo.options[document.form.UserTo.length] =
      new Option(document.form.UserFrom[document.form.UserFrom.selectedIndex].text,
      document.form.UserFrom[document.form.UserFrom.selectedIndex].value);

      document.form.UserFrom.remove(document.form.UserFrom.selectedIndex);
    }
  }
}
function removeUser() {
  if (document.form.UserTo.selectedIndex == -1) {
    window.alert("请选择所要删除的用户");
  }
  else {
    document.form.UserFrom.options[document.form.UserFrom.length] =
      new Option(document.form.UserTo[document.form.UserTo.selectedIndex].text,
      document.form.UserTo[document.form.UserTo.selectedIndex].value);
    document.form.UserTo.remove(document.form.UserTo.selectedIndex);
  }
}
</script>
<link href="../../style/common.css" rel="stylesheet" type="text/css">
</head>
<body>
<%
Connection conn = null;
try {
//获得数据库连接
conn = Tools.getDBConn();
//获得工作流编号
String strWorkflowID = request.getParameter("WorkflowID");
//获得节点编号
String strNodeID = request.getParameter("ID");
//已选角色组ID
String[] strRoleSeled = null;
//已选角色组名称
String[] strRoleNameSeled = null;
//已选用户ID
String[] strUserSeled = null;
//已选用户真实性名
String[] strTrueNameSeled = null;
//判断工作流编号和节点编号是否合法
if (strWorkflowID != null && !strWorkflowID.equals("") &&
    strNodeID != null && !strNodeID.equals("")) {
    //实例化Participant类，用于保存元数据库中的配置信息
    ProcessInfo pi = new ProcessInfo();
    //设置工作流编号
    pi.setID(Integer.parseInt(strWorkflowID));
    List listPI = new ArrayList();
    //根据工作流编号查找Participant类
    listPI = pi.searchObject();
    if (listPI != null && listPI.size() >0) {
      pi = (ProcessInfo)listPI.get(0);
    }
    String strWorkflowName = pi.getName();  //工作流名称
    String strInfo = pi.getInfo();  //
    String strCreateDate = pi.getCreateDate();  //创建日期
    //根据工作流编号和节点编号查找节点
    Node node = Node.searchNodeObject(Integer.parseInt(strWorkflowID), Integer.parseInt(strNodeID));
    String strNodeName = node.getName();  //节点名称
    String strNodeStatu = node.getStatu();  //接单状态
    int intNodeType = node.getType();  //节点类型
    String strNodeType = new String();
    switch (intNodeType) {
      case 0: strNodeType = "开始节点";
        break;
      case 1: strNodeType = "中间节点";
        break;
      case 2:  strNodeType = "结束节点";
        break;
      default: strNodeType = "未知类型，请注意！";
    }

    Participant pt = new Participant();
    //设置工作流编号
    pt.setWorkflowID(Integer.parseInt(strWorkflowID));
    //设置节点编号
    pt.setNodeID(Integer.parseInt(strNodeID));
    //根据工作流编号和节点编号查找参与者
    List listResult = pt.query();
    if (listResult != null && listResult.size() > 0) {
      pt = (Participant)listResult.get(0);
      //获得数据库中角色信息
      String strRoleID = pt.getRoleID();
      //将该角色信息转换成数组形式
      if (strRoleID!=null&&!strRoleID.equals("")){
        strRoleSeled = strRoleID.split("\\|");
      }
      //创建角色名数组
      if (strRoleSeled != null && strRoleSeled.length > 0) {
        strRoleNameSeled = new String[strRoleSeled.length];
      }
      //获得数据库中用户信息
      String strUserList=pt.getUserList();
      //将该用户信息转换成数组形式
      if (strUserList != null&&!strUserList.equals("")){
        strUserSeled = strUserList.split("\\|");
      }
      //创建用户名数组
      if (strUserSeled != null && strUserSeled.length > 0) {
        strTrueNameSeled = new String[strUserSeled.length];
      }
    }
%>

<form action="" method="post" name="form" id="form">
<p align="center">
		<font style="font-size:12pt "><strong>节点权限配置</strong></font>
	</p>
        <div align="center">
          <table width="80%"  border="0" cellpadding="0" cellspacing="0" class="TableBorderStyle" id="bg">
  		</tr>
            <tr>
              <td class="TableINBgStyle"><table width="100%"  border="0" cellpadding="0" cellspacing="1">
                <tr class="TableBgStyle">
                  <td width="12%" height="25" class="TableBgStyle"><div align="right">工作流编号：</div></td>
                  <td width="8%" class="TableTdBgStyle"><div align="center"><%=strWorkflowID%>
                        <input type="hidden" name="WorkflowID" id="WorkflowID" value="<%=strWorkflowID%>" />
                  </div></td>
                  <td width="12%" class="TableTdBgBlueStyle"><div align="right">工作流名称:&nbsp; </div></td>
                  <td width="14%" class="TableTdBgStyle"><div align="center"><%=strWorkflowName%></div></td>
                  <td width="12%" class="TableBgStyle"><div align="right">工作流说明:&nbsp; </div></td>
                  <td width="12%" class="TableTdBgStyle"><div align="center"><%=strInfo%></div></td>
                  <td width="12%"><div align="right">创建日期:&nbsp; <BR>
                  </div></td>
                  <td width="12%" class="TableTdBgStyle"><div align="center"><%=strCreateDate%></div></td>
                </tr>
                <tr class="TableBgStyle">
                  <td class="TableBgStyle" height="25"><div align="right">节点编号:&nbsp; </div></td>
                  <td class="TableTdBgStyle"><div align="center"><%=strNodeID%>
                        <input type="hidden" name="NodeID" id="NodeID" value="<%=strNodeID%>" />
                  </div></td>
                  <td class="TableBgStyle"><div align="right">节点名称:&nbsp; </div></td>
                  <td class="TableBgStyle">
                  <div align="center"><%=strNodeName%></div></td>
                  <td class="TableBgStyle"><div align="right">节点状态:&nbsp; </div></td>
                  <td class="TableTdBgStyle"><div align="center"><%=strNodeStatu%></div></td>
                  <td class="TableBgStyle"><div align="right">节点类型:&nbsp; </div></td>
                  <td class="TableTdBgStyle"><div align="center"><%=strNodeType%></div></td>
                </tr>
                <tr>
                  <td colspan="4" class="TableTdBgStyle"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td height="20" colspan="3" class="DateListTableHeadStyle"><div align="center">角 色 组</div></td>
                    </tr>
                      <tr class="TableBgStyle">
                        <td><div align="center">
                          <select name="RoleFrom" size="20" class="TextFieldStyle" id="RoleFrom" style="width:120pt">
                            <%

                            Role role = new Role();
                            List listRole = new ArrayList();
                            //查处数据库中所有角色信息，供用户配置
                            listRole = role.query();
                            Iterator itRole = listRole.iterator();
                            while (itRole.hasNext()) {
                              boolean boolIsIn = false;  //标示该角色是否已被选中
                              role = (Role)itRole.next();
                              int intID = role.getID();  //角色编号
                              String strID = String.valueOf(intID);
                              String strRoleName = role.getRoleName();  //记录角色名称
                              //循环判断该角色是否已被选中
                              for (int i = 1;strRoleSeled!=null&&i < strRoleSeled.length; i++) {
                                if (strID.equals(strRoleSeled[i])) {
                                  strRoleNameSeled[i] = strRoleName;  //纪录被选中的角色名称，用于在已选中框里显示
                                  boolIsIn = true;
                                  break;
                                }
                              }
                              if (!boolIsIn) {
                                %>
                                <option value="<%=intID%>"><%=strRoleName%></option>
                                <%
                                }
                              }
                              %>
                          </select>
                        </div></td>
                        <td valign="middle"><div align="center">
                            <input name="Button" type="button" class="ButtonStyle" style="width:60px " onClick="addRole()" value="--->">
                            <br>
                            <br>
                            <br>
                            <input name="Button" type="button" class="ButtonStyle" style="width:60px " onClick="removeRole()" value="<---">
                          </div>
                            <div align="center"> </div></td>
                        <td><div align="center">
                            <select name="RoleTo" size="20" multiple class="TextFieldStyle" id="RoleTo" style="width:120pt">
                              <%
                              //循环显示已选中的角色信息
                              for (int i = 1; strRoleSeled!=null&&i < strRoleSeled.length; i++) {
                                %>
                                <option value="<%=strRoleSeled[i]%>"><%=strRoleNameSeled[i]%></option>
                                <%
                                }
                                %>
                          </select>
                        </div></td>
                    </tr>
                  </table></td>
                  <td colspan="4" class="TableTdBgStyle"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td height="20" colspan="3" class="DateListTableHeadStyle"><div align="center">用 户</div></td>
                    </tr>
                      <tr class="TableBgStyle">
                        <td><div align="center">
                            <select name="UserFrom" size="20" class="TextFieldStyle" id="UserFrom" style="width:120pt">
                              <%
              //从general-config.xml中获得用户表名
              String strUserTable = CommonInfo.getUserTable();
              //获得用户列名
              String strUserName = CommonInfo.getUserName();
              //获得用户真实性名的列名
              String strTrueName = CommonInfo.getTrueName();
              //从general-config.xml中获得数据异常处理
              if (strUserTable == null || strUserTable.equals("") ||
                  strUserName == null || strUserName.equals("") ||
                  strTrueName == null || strTrueName.equals("")) {
                throw new Exception("数据库配置错误！请重新配置！");
              }
              //组织SQL语句
              StringBuffer sbSQL = new StringBuffer();
              sbSQL.append("SELECT ");
              sbSQL.append(strUserName);
              sbSQL.append(",");
              sbSQL.append(strTrueName);
              sbSQL.append(" FROM ");
              sbSQL.append(strUserTable);

              Statement statement = conn.createStatement();
              ResultSet resultset = statement.executeQuery(sbSQL.toString());
              while (resultset.next()) {
              //循环获得用户信息
                boolean boolIsIn = false;  //标示该用户是否存在以选中的列表之中，如果存在将不再可选列比表中显示
                String strUserName_temp = resultset.getString(strUserName).trim();  //用户ID
                String strTrueName_temp = resultset.getString(strTrueName).trim();  //用户真实姓名
                for (int i = 0; strUserSeled != null && i < strUserSeled.length; i++) {
                  if (strUserName_temp.equals(strUserSeled[i])) {
                    strTrueNameSeled[i] = strTrueName_temp;  //保存用户真实性名，用于在已选列表中显示
                    boolIsIn = true;
                    break;
                  }
                }
                if (!boolIsIn) {
              %>
                              <option value="<%=strUserName_temp%>"><%=strUserName_temp+"("+strTrueName_temp+")"%></option>
                              <%
                }
              }
              %>
                            </select>
                        </div></td>
                        <td valign="middle"><div align="center">
                            <input name="Button" type="button" class="ButtonStyle" style="width:60px " onClick="addUser()" value="--->">
                            <br>
                            <br>
                            <br>
                            <input name="Button" type="button" class="ButtonStyle" style="width:60px " onClick="removeUser()" value="<---">
                          </div>
                            <div align="center"> </div></td>
                        <td><div align="center">
                          <select name="UserTo" size="20" multiple class="TextFieldStyle" id="UserTo" style="width:120pt">
                            <%
                            //循环显示已选中用户
                            for (int i = 0; strUserSeled != null && i < strUserSeled.length; i++) {
                              if (!strUserSeled[i].equals("") && strTrueNameSeled != null) {
                                %>
                                <option value="<%=strUserSeled[i]%>"><%=strUserSeled[i]+"("+strTrueNameSeled[i]+")"%></option>
                                <%
                                }
                              }
                              %>
                          </select>
                        </div></td>
                    </tr>
                  </table></td>
                </tr>
              </table></td>
            </tr>
          </table>
          <br>
            <input name="Button" type="button" class="ButtonStyle" style="width:60pt " onclick="submitForm(this.form)" value="确定">
            <input name="Reset" type="reset" class="ButtonStyle" style="width:60pt " value="重写">
        </div>
      </form>
    <br>
</body>
</html>
<%
  }
}
catch (Exception e) {
  //异常处理
  out.println(e.getMessage());
  out.println("<a href = 'Node_SelectWorkflow.jsp'>返回</a>");
}
finally {
  //释放数据库资源
  if (conn != null) {
    conn.close();
  }
}
%>
