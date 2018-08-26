<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token");
    String idChange = request.getParameter("idChange");
    String idChange2 = request.getParameter("idChange2");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

</head>
<html>
<head>
</head>
<body class="easyui-layout">


<div data-options="region:'north'">
    <div id="updateTabs<%=token%>" class="easyui-tabs" style="width:560px;height:250px;">
        <div title="基本信息">
            <form id="userUpdate<%=token%>"  action="" method="post">
                <table>
                    <tr>
                        <td>名字</td><td><input class="easyui-validatebox" type="text" id="name<%=token %>" name="user.name" required="true" missingMessage="必须填写"  style="width:200px"/></td>
                        <td>性别</td><td><input class="easyui-combotree" type="text" id="gender<%=token %>" name="user.gender" required="true" missingmessage="必须填写"  style="width:200px"/></td>
                    </tr>
                    <tr>
                        <td>电话</td><td><input class="easyui-validatebox" type="text" id="mobile<%=token %>" name="user.mobile" required="true" missingmessage="必须填写" validType="mobile" onblur="checkMobile(value)" style="width:200px"/></td>
                        <td>地址</td><td><input class="easyui-validatebox" type="text" id="address<%=token %>" name="user.address" style="width:200px"/></td>
                    </tr>
                    <tr>
                        <td>邮箱</td>
                        <td><input class="easyui-validatebox" type="text" id="email<%=token %>" name="user.email" validType="email" style="width:200px"/></td>
                        <td>身份号码</td>
                        <td><input class="easyui-validatebox" type="text" id="address<%=token %>" name="user.idnumber" validType="idcard" style="width:200px"/></td>
                    </tr>
                    <tr>
                        <td>生日</td><td><input class="easyui-datebox" type="text" id="address<%=token %>" name="user.birthday" editable="false" style="width:200px"/></td>
                        <td>入职日期</td><td><input class="easyui-datebox" type="text" id="address<%=token %>" name="user.jointime" editable="false" style="width:200px"/></td>
                    </tr>
                </table>
                <input name="user.id" type="hidden" id="id<%=token %>" readonly="readonly" style="width:200px"/>
                <input name="user.password" type="hidden" id="id<%=token %>" readonly="readonly" style="width:200px"/>
            </form>
        </div>
        <div title="密码信息" style="overflow:auto;padding:20px;">
            <form id="userUpdatePwd<%=token%>"  action="" method="post">
                <input name="user.id" type="hidden" id="id<%=token %>" readonly="readonly" style="width:200px"/>
                <input name="user.name" type="hidden" id="id<%=token %>" readonly="readonly" style="width:200px"/>
                <input name="user.gender" type="hidden" id="id<%=token %>" readonly="readonly" style="width:200px"/>
                <input name="user.mobile" type="hidden" id="id<%=token %>" readonly="readonly" style="width:200px"/>
                <input name="user.email" type="hidden" id="id<%=token %>" readonly="readonly" style="width:200px"/>
                <input name="user.address" type="hidden" id="id<%=token %>" readonly="readonly" style="width:200px"/>
                <input name="user.birthday" type="hidden" id="id<%=token %>" readonly="readonly" style="width:200px"/>
                <input name="user.jointime" type="hidden" id="id<%=token %>" readonly="readonly" style="width:200px"/>
                <input name="user.idnumber" type="hidden" id="id<%=token %>" readonly="readonly" style="width:200px"/>
                <table>
                    <tr>
                        <td align="right">密码</td>
                        <td><input class="easyui-validatebox" type="password" id="password<%=token %>" name="user.password" validType="equalTo['password2<%=token %>']" style="width:200px"/></td>
                    </tr>

                    <tr>
                        <td align="right">重复密码</td>
                        <td><input class="easyui-validatebox" type="password" id="password2<%=token %>" validType="equalTo['password<%=token %>']"  style="width:200px"/></td>
                    </tr>
                </table>
            </form>
        </div>
<!--
        <div  title="部门信息"  style="padding:20px,display:hidden;" >
            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td >
                        部门信息
                    </td>
                    <td>
                        岗位信息
                    </td>
                    <td>
                        人员信息
                    </td>
                </tr>
                <tr>
                    <td>
                        <ul id="department<%=token%>" class="easyui-tree"></ul>
                    </td>
                    <td>
                        <ul id="jobOption<%=token%>" class="easyui-tree"></ul>
                    </td>
                    <td></td>
                </tr>
            </table>
        </div>
        -->

    </div>
<%--
 <script type="text/javascript">
         alert(idChange);
         alert(idChange2);
     </script>
 --%>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>