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
            form.action = "RoleModDone.jsp";
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
        conn = Tools.getDBConn();  //获得数据库连接
        Role role = new Role();
        List listRole = new ArrayList();
        String strID = new String();
        String strRoleName = new String();
        String strUserList = new String();
        strID = request.getParameter("ID");  //获得角色编号
        if (strID == null || strID.equals("")) {
            throw new Exception("执行RoleModify.jsp页面发成异常，无法获得角色编号！");
        }
        role.setID(Integer.parseInt(strID));  //设置角色编号
        //根据角色编号查找角色
        listRole = role.query();
        if (listRole != null && listRole.size() > 0) {
            role = (Role)listRole.get(0);
        }
        strRoleName = role.getRoleName();  //获得角色名称
        strUserList = role.getUserList();  //获得角色所包含的用户
        String[] strUser = null;
        //将用户从数组形式变为|XXX|XXX|XX|
        if (strUserList != null && !strUserList.equals("")) {
            strUser = strUserList.split("\\|");
        }
        String[] strTrueName = null;
        //创建用户真实姓名数组
        if (strUser != null && strUser.length > 0) {
            strTrueName = new String[strUser.length];
        }
%>
<body style="text-align: right">
<form name="form" method="post" action="">
    <p align="center">
        <font style="font-size:12pt "><strong>修改角色信息</strong></font>
    </p>
    <div align="center">
        <table width="50%"  border="0" align="center" cellpadding="1" cellspacing="1" class="TableINBgStyle">
            <tr class="TableTdBgStyle">
                <td width="46%" class="TableBgStyle"><div align="right">编&nbsp;&nbsp;&nbsp; 号：</div></td>
                <td colspan="2" class="TableBgStyle">
                    <input name="ID" type="text" class="TextReadOnlyStyle" id="ID" value="<%=strID%>" maxlength="8" readonly="true"></td>
            </tr>
            <tr class="TableTdBgStyle">
                <td class="TableBgStyle"><div align="right">角色名称：</div></td>
                <td colspan="2" class="TableBgStyle">
                    <input name="RoleName" type="text" class="TextStyle" id="RoleName" value="<%=strRoleName%>"></td>
            </tr>
            <tr class="TableBgStyle">
                <td><div align="center">用户列表</div>
                    <div align="center">
                        <select name="UserFrom" size="20" multiple="multiple" class="TextFieldStyle" id="UserFrom" style="width:150pt">
                            <%
                                //从general-config.xml中获得用户表名
                                String strUserTableSQL = CommonInfo.getUserTable();
                                //获得用户列名
                                String strUserNameSQL = CommonInfo.getUserName();
                                //获得用户真实性名的列名
                                String strTrueNameSQL = CommonInfo.getTrueName();
                                //从general-config.xml中获得数据异常处理
                                if (strUserTableSQL == null || strUserTableSQL.equals("") ||
                                        strUserNameSQL == null || strUserNameSQL.equals("") ||
                                        strTrueNameSQL == null || strTrueNameSQL.equals("")) {
                                    throw new Exception("数据库配置错误！请重新配置！");
                                }
                                //组织SQL语句
                                StringBuffer sbSQL = new StringBuffer();
                                sbSQL.append("SELECT ");
                                sbSQL.append(strUserNameSQL);
                                sbSQL.append(",");
                                sbSQL.append(strTrueNameSQL);
                                sbSQL.append(" FROM ");
                                sbSQL.append(strUserTableSQL);
                                sbSQL.append(" where state=0");

                                Statement statement = conn.createStatement();
                                //执行数据库查询
                                ResultSet resultset = statement.executeQuery(sbSQL.toString());
                                while (resultset.next()) {
                                    //循环获得用户信息
                                    boolean boolIsIn = false;  //标示角色是否已被选中
                                    String strUserName_temp = resultset.getString(strUserNameSQL).trim();  //用户ID
                                    String strTrueName_temp = resultset.getString(strTrueNameSQL).trim();  //用户真实姓名
                                    //检查该用户是否被选中
                                    for (int i = 1; strUser != null && i < strUser.length; i++) {
                                        if (strUserName_temp.equals(strUser[i])) {
                                            if (strTrueName != null && i < strTrueName.length) {
                                                strTrueName[i] = strTrueName_temp;  //保存该选中用户的真实姓名
                                            }
                                            boolIsIn = true;
                                            break;
                                        }
                                    }
                                    if (!boolIsIn) {
                            %>
                            <option value="<%=strUserName_temp%>"><%=strTrueName_temp+"("+strUserName_temp+")"%></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div></td>
                <td width="6%"><div align="center">
                    <input name="Button" type="button" class="ButtonStyle" style="width:55pt " onClick="addUser()" value="--->">
                    <br>
                    <br>
                    <br>
                    <input name="Button" type="button" class="ButtonStyle" style="width:55pt " onClick="removeUser()" value="<---">
                </div></td>
                <td width="48%"><div align="center">已选择的用户名</div>
                    <div align="center">
                        <select name="UserTo" size="20" multiple class="TextFieldStyle" id="UserTo" style="width:150pt">
                            <%
                                //循环显示已选中的用户
                                for (int i = 1; strUser != null && i < strUser.length; i++) {
                            %>
                            <option value="<%=strUser[i]%>"><%=strTrueName[i]+"("+strUser[i]+")"%></option>
                            <%
                                }
                            %>
                        </select>
                    </div></td>
            </tr>
        </table></td>
        </tr>
        </table>
        <br>
        <input name="Button" type="button" class="ButtonStyle" style="width:60pt " onClick="submitForm(this.form)" value="保存">
        <input name="Reset" type="reset" class="ButtonStyle" style="width:60pt " value="重写">
    </div>
</form>
<br>
</div>
</body>
</html>
<%
    }
    catch (Exception e) {
        //异常处理
        e.printStackTrace();
    }
    finally {
        //释放数据库资源
        if (conn != null) {
            conn.close();
        }
    }
%>
