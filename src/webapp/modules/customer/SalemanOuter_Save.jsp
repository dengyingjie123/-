<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

</head>
<html>
<head>
</head>
<body>
<form id="formSalemanOuter<%=token%>" name="formSalemanOuter" action="" method="post">
    <div id="updateTabs<%=token%>" class="easyui-tabs">
        <div title="基本信息" style="padding:10px;background:#fff;border:0px solid #ccc;">
            <div style="height: 243px" align="center">
                <table border="0" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right">名字：</td>
                        <td><input class="easyui-validatebox" type="text" id="name<%=token %>" name="user.name" required="true" missingMessage="必须填写"  style="width:150px"/></td>
                        <td align="right">性别：</td>
                        <td><input class="easyui-combotree" type="text" id="genderName<%=token %>" name="user.gender" required="true" missingmessage="必须填写"  style="width:150px"/></td>
                    </tr>
                    <tr>
                        <td align="right">电话：</td>
                        <td><input class="easyui-validatebox" type="text" id="mobile<%=token %>" name="user.mobile" required="true"  invalidMessage="请输入11位手机号" validType="mobile"  style="width:150px"/></td>
                        <td align="right">岗位：</td>
                        <td><input class="easyui-combotree" type="text" id="positionTypeId<%=token%>" name="user.positionTypeId" style="width: 150px"/></td>
                    </tr>
                    <tr>
                        <td align="right">公司：</td>
                        <td colspan="3"><input class="easyui-combotree" type="text" id="company<%=token%>" name="company" required="true" missingmessage="必须填写"  style="width: 370px"/></td>
                    </tr>
                    <tr>
                        <td align="right">地址：</td>
                        <td colspan="3"><textarea class="easyui-validatebox" type="text" id="address<%=token %>" name="user.address" style="width:370px"/></td>
                    </tr>
                    <tr>
                        <td align="right">邮箱：</td>
                        <td><input class="easyui-validatebox" type="text" id="email<%=token %>" name="user.email" validType="email" style="width:150px"/></td>
                        <td align="right">身份证：</td>
                        <td><input class="easyui-validatebox" type="text" id="idnumber<%=token %>" name="user.idnumber" validType="idcard" style="width:150px"/></td>
                    </tr>
                    <tr>
                        <td align="right">生日：</td>
                        <td><input class="easyui-datebox" type="text" id="birthday<%=token %>" name="user.birthday" editable="false" style="width:150px"/></td>
                        <td align="right">状态：</td>
                        <td><input class="easyui-combotree" type="text" id="status<%=token %>" name="user.status" required="true" missingmessage="必须填写"  style="width:150px"/></td>
                    </tr>
                    <tr>
                        <td align="right">入职时间：</td>
                        <td><input class="easyui-datebox" type="text" id="jointime<%=token %>" name="user.jointime" editable="false" style="width:150px"/></td>
                        <td align="right">离职时间：</td>
                        <td><input class="easyui-datebox" type="text" id="leftTime<%=token %>" name="user.leftTime" editable="false" style="width:150px"/></td>
                    </tr>
                    <tr>
                        <td align="right">行业：</td>
                        <td><input class="easyui-combotree" type="text" id="industry<%=token %>" name="user.industry" editable="false" style="width:150px"/></td>
                        <td align="right">销售类别：</td>
                        <td><input class="easyui-combotree" type="text" id="userType<%=token %>" name="user.userType" editable="false" style="width:150px"/></td>
                    </tr>
                </table>
                <input type="hidden" id="editType<%=token %>" name="editType" readonly="readonly"/>
                <input type="hidden" id="sid<%=token %>" name="user.sid" readonly="readonly"/>
                <input type="hidden" id="id<%=token %>" name="user.id" readonly="readonly"/>
                <input type="hidden" id="state<%=token %>" name="user.state" readonly="readonly"/>
                <input type="hidden" id="operatorId<%=token %>" name="user.operatorId" readonly="readonly"/>
                <input type="hidden" id="operateTime<%=token %>" name="user.operateTime" readonly="readonly"/>
                <input type="hidden" id="staffCode<%=token %>" name="user.staffCode" readonly="readonly"/>
                <input type="hidden" id="saleLevel<%=token %>" name="user.saleLevel" readonly="readonly"/>

            </div>
        </div>
        <div title="密码信息" style="padding:10px;background:#fff;border:0px solid #ccc;">
            <div style="height: 243px" align="center">
                <table border="0" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right">密码：</td>
                        <td><input class="easyui-validatebox" type="password" id="password<%=token %>" name="user.password" validType="equalTo['password2<%=token %>']" style="width:150px"/></td>
                    </tr>
                    <tr>
                        <td align="right">重复密码：</td>
                        <td><input class="easyui-validatebox" type="password" id="password2<%=token %>" validType="equalTo['password<%=token %>']"  style="width:150px"/></td>
                    </tr>
                </table>
            </div>
        </div>

    </div>
</form>
<div region="south" border="false" style="text-align:right;padding:6px;background:#F4F4F4">
    <a id="btnSalemanOuterSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('SalemanOuterWindow<%=token%>')">取消</a>
</div>
</body>
</html>

