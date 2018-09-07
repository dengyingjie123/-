<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 11/5/14
  Time: 9:19 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Untitled Document</title>
</head>

<body>
<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
        <form id="formPassword<%=token %>" name="formPassword" action="" method="post" >
            <input type="hidden" id="name<%=token %>" name="personal.name" style="width:180px"/>
            <input type="hidden" id="sex<%=token %>" name="personal.sex" style="width:180px"/>
            <input type="hidden" id="nationId<%=token %>" name="personal.nationId" style="width:180px"/>
            <input type="hidden" id="birthday<%=token %>" name="personal.birthday" style="width:180px"/>
            <input type="hidden" id="mobile<%=token %>"  name="personal.mobile" style="width:180px"/>
            <input type="hidden" id="mobile2<%=token %>" name="personal.mobile2"style="width:180px"/>
            <input type="hidden" id="phone<%=token %>" name="personal.phone" style="width:180px"/>
            <input type="hidden" id="phone2<%=token %>" name="personal.phone2" style="width:180px"/>
            <input type="hidden" id="postNo<%=token %>" name="personal.postNo"style="width:180px"/>
            <input type="hidden"  id="email<%=token %>" name="personal.email" style="width:180px"/>
            <input type="hidden" id="email2<%=token %>" name="personal.email2"  style="width:180px"/>
            <input type="hidden" id="customerSourceId<%=token %>" name="personal.customerSourceId"style="width:180px"/>
            <input type="hidden" id="customerTypeId<%=token %>" name="personal.customerTypeId" style="width:180px"/>
            <input type="hidden" id="relationshipLevelId<%=token %>" name="personal.relationshipLevelId" style="width:180px"/>
            <input type="hidden" id="careerId<%=token %>" name="personal.careerId" style="width:180px"/>
            <input type="hidden" id="creditRateId<%=token %>" name="personal.creditRateId"  style="width:180px"/>
            <input type="hidden" id="personalNumber<%=token %>" name="personal.personalNumber" style="width:180px"/>
            <input type="hidden" id="creatTime<%=token %>" name="personal.creatTime"  style="width:180px"/>
            <input type="hidden" id="address<%=token %>" class="easyui-validatebox" name="personal.address" style="width:430px"/>
            <table>
                <tr>
                    <td align="right">原来密码</td>
                    <td><input class="easyui-validatebox" type="password" id="beforePassword<%=token %>" name="personal.beforePassword" style="width:200px"/><span id="errorMessage<%=token%>"></span></td>
                </tr>
                <tr>
                    <td align="right">新的密码</td>
                    <td><input class="easyui-validatebox" type="password" id="password<%=token %>" name="personal.password" validType="equalTo['password2<%=token %>']" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">重复密码</td>
                    <td><input class="easyui-validatebox" type="password" id="password2<%=token %>" validType="equalTo['password<%=token %>']"  style="width:200px"/></td>
                </tr>
            </table>
            <input  type="hidden" id="operatorId<%=token %>" name="personal.operatorId"  readonly="readonly" style="width:180px"/>
            <input  type="hidden" id="sid<%=token %>" name="personal.sid"  readonly="readonly" style="width:180px"/>
            <input  type="hidden" id="id<%=token %>" name="personal.id" readonly="readonly" style="width:180px"/>
            <input  type="hidden" id="operateTime<%=token %>" name="personal.operateTime"  readonly="readonly" style="width:180px"/>
            <input  type="hidden" id="state<%=token %>" name="personal.state" style="width:180px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnPasswordSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('PasswordWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>