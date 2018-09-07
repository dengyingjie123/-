<jsp:include page="../checkLogin.jsp"></jsp:include>
<%
    /**
     * 程序：李扬
     * 时间：2004-10-28
     * 说明：增加角色页面
     *
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
<link href="../style/common.css" rel="stylesheet" type="text/css">

<script src="../script/CheckText.js"></script>
<script language="javascript">
    function submitForm(form) {
        //TValue=TValue.replace(/(^\s*)|(\s*$)/g, "");
        if (checkForm(form)) {
            for (i = 0; i < document.form.UserTo.length; i++) {
                //选中用户
                values = document.form.UserTo[i].value;
                values = values.replace(/(^\s*)|(\s*$)/g, "");
                document.form.UserTo[i].value = values;
                document.form.UserTo[i].selected = true;
            }
            form.action = "RoleAddDone.jsp";
            form.submit();
        }
    }
    function checkForm(form) {
        if (!checkFormItem(form.ID, "N", 8, 0 ,1, "角色编号"))
            return false;
        else if (!checkFormItem(form.RoleName, "C", 0, 0, 1, "角色名称"))
            return false;
        else
            return true;
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
<%
    Connection conn = null;
    try {
        conn = Tools.getDBConn();
        Role role = new Role();
%>
<body style="text-align: right">
<form name="form" method="post" action="">
    <p align="center">
        <font style="font-size:12pt "><strong>新增用户角色信息</strong></font>
    </p>
    <div align="center">
        <table width="50%"  border="0" align="center" border="0" cellpadding="0" cellspacing="1" class="TableINBgStyle">
            <tr class="TableTdBgStyle">
                <td colspan="2" width="46%" class="TableBgStyle"><div align="right">编&nbsp;&nbsp;&nbsp; 号：</div></td>
                <td colspan="2" class="TableBgStyle">
                    <input name="ID" type="text" class="TextStyle" id="ID" maxlength="8" value="<%=role.getMaxID(conn)%>"></td>
            </tr>
            <tr class="TableTdBgStyle">
                <td colspan="2" class="TableBgStyle"><div align="right">角色名称：</div></td>
                <td colspan="2" class="TableBgStyle">
                    <input name="RoleName" type="text" class="TextStyle" id="RoleName"></td>
            </tr>
            <tr class="TableTdBgStyle">
                <td colspan="2" class="TableBgStyle">
                    <div align="center">用户列表</div>
                    <div align="center">
                        <select name="UserFrom" multiple="multiple" size="20" class="TextFieldStyle" id="UserFrom" style="width:150pt">
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
                                sbSQL.append(" where state=0");

                                Statement statement = conn.createStatement();
                                ResultSet resultset = statement.executeQuery(sbSQL.toString());
                                while (resultset.next()) {
                                    //循环获得用户信息
                                    String strUserName_temp = resultset.getString(strUserName).trim();  //用户ID
                                    String strTrueName_temp = resultset.getString(strTrueName).trim();  //用户真实姓名
                            %>
                            <option value="<%=strUserName_temp%>"><%=strTrueName_temp+"("+strUserName_temp+")"%></option>
                            <%
                                }
                            %>
                        </select>
                    </div></td>
                <td width="48%" class="TableBgStyle"><div align="center"><br>
                    <input name="Button" type="button" class="ButtonStyle" style="width:55pt " onClick="addUser()" value="--->">
                    <br>
                    <br>
                    <br>
                    <br>
                    <input name="Button" type="button" class="ButtonStyle" style="width:55pt " onClick="removeUser()" value="<---">
                    <br>
                </div>                    <div align="center">
                </div></td>
                <td width="48%" class="TableBgStyle">
                    <div align="center">已选择的用户名</div>
                    <select name="UserTo" size="20" multiple class="TextFieldStyle" id="UserTo" style="width:150pt">
                    </select></td>
            </tr>
        </table></td>
        </tr>
        </table>
        <br>
        <input name="Button" type="button" class="ButtonStyle" style="width:60pt " onClick="submitForm(this.form)" value="增加">
        <input name="Reset" type="reset" class="ButtonStyle" style="width:60pt " value="重写">
    </div>
</form>
<br>
<br>
</div>
</body>
</html>
<%
    }
    catch (Exception e) {
        //异常处理
        throw e;
    }
    finally {
        //释放数据库资源
        if (conn != null) {
            conn.close();
        }
    }
%>
