<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 10/18/14
  Time: 5:23 PM
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
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Untitled Document</title>
</head>

<body>
<div class="easyui-layout" fit="true">

    <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
        <form id="formCustomerCertificate<%=token %>" name="formCustomerCertificate" action="" method="post">
            <table width="100%" border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">名称</td>
                    <td><input type="text" id="name<%=token +1%>" class="easyui-combotree"
                               name="customerCertificate.name"  data-options="required:true"
                               style="width:200px" /></td>
                </tr>
                <tr>
                    <td align="right">号码</td>
                    <td><input type="text" class="easyui-validatebox" id="number<%=token %>"
                               name="customerCertificate.number" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">发证机构</td>
                    <td><input type="text" id="authenticationInstitution<%=token %>"
                               name="customerCertificate.authenticationInstitution" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">有效期类型</td>
                    <td><input type="text" id="isLongValidityDate<%=token%>"
                               name="customerCertificate.isLongValidityDate" class="easyui-combotree"
                               style="width:200px;" data-options="required:true"/></td>
                </tr>
                <tr>
                    <td align="right">有效期</td>
                    <td>
                        <input type="text" id="validityDate<%=token %>" class="easyui-datebox"
                               name="customerCertificate.validityDate" style="width:200px" missingmessage="必须填写"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">有效期开始时间</td>
                    <td><input type="text" id="validityDateStart<%=token %>" class="easyui-datebox"
                               name="customerCertificate.validityDateStart" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">有效期结束时间</td>
                    <td><input type="text" id="validityDateEnd<%=token %>" class="easyui-datebox"
                               name="customerCertificate.validityDateEnd" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">身份证附件上传</td>
                    <td><a id="btnUpload<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
                           href="javascript:void(0)">上传</a></td>
                </tr>

                <td><input type="hidden" id="customerId<%=token %>" class="easyui-validatebox"
                           name="customerCertificate.customerId" style="width:200px"/></td>
            </table>
            <input type="hidden" id="operatorId<%=token %>" name="customerCertificate.operatorId" style="width:200px"/>
            <input type="hidden" id="id<%=token %>" name="customerCertificate.id" style="width:200px"/>
            <input type="hidden" id="operateTime<%=token %>" name="customerCertificate.operateTime"
                   style="width:200px"/>
       </form>

    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnCustomerCertificateSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('CustomerCertificateWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>