<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 10/10/14
  Time: 10:56 AM
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
        <form id="formCustomerPersonal<%=token %>" name="formCustomerPersonal" action="" method="post">
            <table width="100%" border="0" cellpadding="5">
                <tr>
                    <td align="right">姓名</td>
                    <td><input type="text" id="name<%=token %>" class="easyui-validatebox" name="personal.name"
                               validtype="maxLength[20]" required="true" missingmessage="必须填写" style="width:180px"/></td>
                    <td align="right">用户名</td>
                    <td><input type="text" id="loginName<%=token %>" class="easyui-validatebox"
                               name="personal.loginName" validtype="maxLength[20]" required="true" missingmessage="必须填写"
                               style="width:180px"/></td>
                    <td align="right">性别</td>
                    <td><input class="easyui-combotree" editable="false" type="text" id="sex<%=token %>"
                               name="personal.sex" required="true" missingmessage="必须填写" style="width:80px"/></td>
                    <td align="right">国籍</td>
                    <td><input type="text" id="nationId<%=token %>" name="personal.nationId" style="width:80px"/></td>
                </tr>
                <tr>
                    <td align="right">出生日期</td>
                    <td><input class="easyui-datebox" editable="false" type="text" id="birthday<%=token %>"
                               name="personal.birthday" style="width:180px"/></td>
                    <td align="right">移动电话</td>
                    <td><input type="text" id="mobile<%=token %>" class="easyui-validatebox" name="personal.mobile"
                               validtype="mobile" required="true" invalidmessage="仅支持13、14、15、17、18开头的11位手机号码" style="width:180px"/></td>
                    <td align="right">移动电话2</td>
                    <td colspan="3"><input type="text" id="mobile2<%=token %>" class="easyui-validatebox" name="personal.mobile2"
                                           validtype="mobile" invalidmessage="仅支持13、14、15、17、18开头的11位手机号码" style="width:180px"/></td>
                </tr>
                <tr>
                    <td align="right">固定电话</td>
                    <td><input type="text" id="phone<%=token %>" name="personal.phone" class="easyui-validatebox"
                               validtype="phone" style="width:180px"/></td>
                    <td align="right">固定电话2</td>
                    <td><input type="text" id="phone2<%=token %>" name="personal.phone2" class="easyui-validatebox"
                               validtype="phone" style="width:180px"/></td>
                    <td align="right">邮编</td>
                    <td colspan="3"><input type="text" class="easyui-validatebox" id="postNo<%=token %>" name="personal.postNo"
                                           validtype="zip" style="width:180px"/></td>
                </tr>
                <tr>
                    <td align="right">电子邮箱</td>
                    <td><input type="text" class="easyui-validatebox" id="email<%=token %>" name="personal.email"
                               validtype="email" style="width:180px"/></td>
                    <td align="right">电子邮箱2</td>
                    <td><input type="text" class="easyui-validatebox" id="email2<%=token %>" name="personal.email2"
                               validtype="email" style="width:180px"/></td>
                    <td align="right">客户来源</td>
                    <td colspan="3"><input class="easyui-combotree" editable="false" type="text" id="customerSourceId<%=token %>"
                                           name="personal.customerSourceId" required="true" missingmessage="必须填写"
                                           style="width:180px"/></td>
                </tr>
                <tr>
                    <td align="right">客户种类</td>
                    <td><input class="easyui-combotree" editable="false" type="text" id="customerTypeId<%=token %>"
                               name="personal.customerTypeId" required="true" missingmessage="必须填写"
                               style="width:180px"/></td>
                    <td align="right">关系等级</td>
                    <td><input class="easyui-combotree" editable="false" type="text" id="relationshipLevelId<%=token %>"
                               name="personal.relationshipLevelId" required="true" missingmessage="必须填写"
                               style="width:180px"/></td>
                    <td align="right">从事职业</td>
                    <td colspan="3"><input class="easyui-combotree" editable="false" type="text" id="careerId<%=token %>"
                                           name="personal.careerId" required="true" missingmessage="必须填写" style="width:180px"/></td>
                </tr>
                <tr>
                    <td align="right">信用等级</td>
                    <td><input class="easyui-combotree" editable="false" type="text" id="creditRateId<%=token %>"
                               name="personal.creditRateId" required="true" missingmessage="必须填写" style="width:180px"/></td>
                    <td align="right">个人客户编号</td>
                    <td><input editable="false" type="text" id="personalNumber<%=token %>"
                               name="personal.personalNumber" readonly="true" style="width:180px"/></td>
                    <td align="right">创建时间</td>
                    <td colspan="3"><input editable="false" type="text" id="creatTime<%=token %>" name="personal.creatTime"
                                           readonly="true" style="width:180px"/></td>
                </tr>
                <tr>
                    <td align="right">家庭地址</td>
                    <td colspan="3"><input type="text" id="homeAddress<%=token %>" name="personal.homeAddress"
                                           maxlength="200" style="width:430px"/></td>
                    <td rowspan="2" align="right" valign="top">备注</td>
                    <td colspan="3" rowspan="2"><textarea rows="2" type="text" id="remark<%=token %>" name="personal.remark"
                                                          validtype="maxLength[200]" style="width:180px"/></td>
                </tr>
                <tr>
                    <td align="right">工作地址</td>
                    <td colspan="3"><input type="text" id="workAddress<%=token %>" name="personal.workAddress"
                                           maxlength="200" style="width:430px"/></td>
                </tr>
                <tr>
                    <td align="right">身份证地址</td>
                    <td colspan="3"><input type="text" id="identityCardAddress<%=token %>" class="easyui-validatebox"
                                           name="personal.identityCardAddress" validtype="maxLength[200]"
                                           required="true" missingmessage="必须填写" style="width:430px"/></td>
                    <td>&nbsp;</td>
                    <td colspan="3">&nbsp;</td>
                </tr>
            </table>
            <input type="hidden" id="mobile3<%=token %>" validType="mobile" required="true" invalidMessage="请输入11位手机号" name="personal.mobile3" style="width:180px"/>
            <input type="hidden" id="phone3<%=token %>" validType="mobile" required="true" invalidMessage="请输入11位手机号" name="personal.phone3" style="width:180px"/>
            <input type="hidden" id="mobile5<%=token %>" validType="mobile" required="true" invalidMessage="请输入11位手机号" name="personal.mobile5" style="width:180px"/>
            <input type="hidden" id="mobile4<%=token %>" validType="mobile" required="true" invalidMessage="请输入11位手机号" name="personal.mobile4" style="width:180px"/>
            <input type="hidden" id="email3<%=token %>" name="personal.email3" style="width:180px"/>
            <input type="hidden" id="email4<%=token %>" name="personal.email4" style="width:180px"/>
            <input type="hidden" id="email5<%=token %>" name="personal.email5" style="width:180px"/>
            <input type="hidden" id="operatorId<%=token %>" name="personal.operatorId" style="width:180px"/>
            <input type="hidden" id="sid<%=token %>" name="personal.sid" style="width:180px"/>
            <input type="hidden" id="id<%=token %>" name="personal.id" style="width:180px"/>
            <input type="hidden" id="operateTime<%=token %>" name="personal.operateTime" style="width:180px"/>
            <input type="hidden" id="state<%=token %>" name="personal.state" style="width:180px"/>
            <input type="hidden" id="password<%=token%>" name="personal.password" style="width:180px"/>
        </form>
        <div style="height:200px">
            <div id="contentTabs" class="easyui-tabs" fit="true" border="true" style="overflow:auto;">
                <div title="账户管理" style="padding:5px ;">
                    <table id="CustomerAccountTable<%=token%>"></table>
                </div>
                <div title="证件管理" style="padding:5px ;">
                    <table id="CustomerCertificateTable<%=token%>"></table>
                </div>
                <div title="回访管理" style="padding:5px;">
                    <table id="CustomerFeedbackTable<%=token%>"></table>
                </div>
                <div title="产品信息" style="padding:5px;">
                    <table id="CustomerProductionTable<%=token%>"></table>
                </div>
            </div>
        </div>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('CustomerPersonalWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
