<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 11/5/14
  Time: 10:57 AM
  To change this template use File | Settings | File Templates.
--%>
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
            <input type="hidden" id="name<%=token %>"  name="institution.name" style="width:180px"/>
            <input type="hidden" id="type<%=token %>" name="institution.type"  style="width:180px"/>
            <input type="hidden" id="legalPerson<%=token %>" name="institution.legalPerson" style="width:180px"/>
            <input type="hidden" id="registeredCapital<%=token %>" name="institution.registeredCapital" style="width:150px"/>
            <input type="hidden" id="mobile<%=token %>" name="institution.mobile" style="width:180px"/>
            <input type="hidden" id="mobile2<%=token %>"  name="institution.mobile2" style="width:180px"/>
            <input type="hidden" id="phone<%=token %>"  name="institution.phone" style="width:180px"/>
            <input type="hidden" id="phone2<%=token %>" name="institution.phone2" style="width:180px"/>
            <input type="hidden" id="address<%=token %>" name="institution.address" style="width:180px"/>
            <input type="hidden" id="postNo<%=token %>" name="institution.postNo"  style="width:180px"/>
            <input type="hidden" id="email<%=token %>" name="institution.email" style="width:180px"/>
            <input type="hidden" id="email2<%=token %>" name="institution.email2" style="width:180px"/>
            <input type="hidden" id="creditRateId<%=token %>" name="institution.creditRateId" style="width:180px"/>
            <input type="hidden" id="careerId<%=token %>" name="institution.careerId" style="width:180px"/>
            <input type="hidden" id="staffSizeId<%=token %>" name="institution.staffSizeId" style="width:180px"/>
            <input type="hidden" id="institutionNumber<%=token %>" name="institution.institutionNumber"style="width:180px"/>
            <input type="hidden" id="creatTime<%=token %>" name="institution.creatTime"style="width:180px"/>
            <input type="hidden" id="customerTypeId<%=token %>" name="institution.customerTypeId" style="width:180px"/>
            <input type="hidden" id="customerSourceId<%=token %>" name="institution.customerSourceId"  style="width:180px"/>
            <input type="hidden" id="relationshipLevelId<%=token %>" name="institution.relationshipLevelId" style="width:180px"/>
            <table>
                <tr>
                    <td align="right">原来密码</td>
                    <td><input class="easyui-validatebox" type="password" id="beforePassword<%=token %>" name="institution.beforePassword" style="width:200px"/><span id="errorMessage<%=token%>"></span></td>
                </tr>
                <tr>
                    <td align="right">新的密码</td>
                    <td><input class="easyui-validatebox" type="password" id="password<%=token %>" name="institution.password" validType="equalTo['password2<%=token %>']" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">重复密码</td>
                    <td><input class="easyui-validatebox" type="password" id="password2<%=token %>" validType="equalTo['password<%=token %>']"  style="width:200px"/></td>
                </tr>
            </table>
            <input  type="hidden" id="operatorId<%=token %>" name="institution.operatorId"  readonly="readonly" style="width:180px"/>
            <input  type="hidden" id="sid<%=token %>" name="institution.sid"  readonly="readonly" style="width:180px"/>
            <input  type="hidden" id="id<%=token %>" name="institution.id" readonly="readonly" style="width:180px"/>
            <input  type="hidden" id="operateTime<%=token %>" name="institution.operateTime"  readonly="readonly" style="width:180px"/>
            <input  type="hidden" id="state<%=token %>" name="institution.state" style="width:180px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnPasswordSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('PasswordWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>