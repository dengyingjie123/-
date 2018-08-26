<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/4/7
  Time: 14:22
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
    <div border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
        <form id="formCustomerAuthenticationStatus<%=token %>" name="formCustomerAuthenticationStatus" action="" method="post">
            <table align="center" border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">客户名称：</td>
                    <td colspan="2" ><input class="easyui-validatebox" type="text" id="customerName<%=token %>" name="customerAuthenticationStatusVO.customerName" readonly="readonly" required="true" missingmessage="必须填写" style="width:100px"/></td>
                    <td><a href="javascript:void(0)" id="btnCheckCustomer<%=token %>" class="easyui-linkbutton" plain="true" iconCls="icon-search"></a></td>
                </tr>


                <tr>
                    <td align="right">手机：</td>
                    <td>
                        <select class="easyui-combobox" type="text" editable="false" id="mobile_Status<%=token %>" name="customerAuthenticationStatus.mobileStatus" style="width:70px;">
                            <option value="0">未认证</option>
                            <option value="1">已认证</option>
                        </select>
                    </td>
                    <td align="right">时间：</td>
                    <td><input class="easyui-datetimebox" type="text" id="mobileTime<%=token %>" name="customerAuthenticationStatus.mobileTime" editable="false" style="width:150px"/></td>
                </tr>

                <tr>
                    <td align="right">邮箱：</td>
                    <td>
                        <select class="easyui-combobox" type="text" editable="false" id="email_Status<%=token %>" name="customerAuthenticationStatus.emailStatus" style="width:70px">
                            <option value="0">未认证</option>
                            <option value="1">已认证</option>
                        </select>
                    </td>
                    <td align="right">时间：</td>
                    <td><input class="easyui-datetimebox" type="text" id="emailTime<%=token %>" name="customerAuthenticationStatus.emailTime" editable="false" style="width:150px"/></td>
                </tr>

                <tr>
                    <td align="right">账户：</td>
                    <td>
                        <select class="easyui-combobox" type="text" editable="false" id="account_Status<%=token %>" name="customerAuthenticationStatus.accountStatus" style="width:70px">
                            <option value="0">未认证</option>
                            <option value="1">已认证</option>
                        </select>
                    </td>
                    <td align="right">时间：</td>
                    <td><input class="easyui-datetimebox" type="text" id="accountTime<%=token %>" name="customerAuthenticationStatus.accountTime" editable="false" style="width:150px"/></td>
                </tr>

                <tr>
                    <td align="right">安全问题：</td>
                    <td>
                        <select class="easyui-combobox" type="text" editable="false" id="qa_Status<%=token %>" name="customerAuthenticationStatus.qaStatus" style="width:70px">
                            <option value="0">未认证</option>
                            <option value="1">已认证</option>
                        </select>
                    </td>
                    <td align="right">时间：</td>
                    <td><input class="easyui-datetimebox" type="text" id="qaTime<%=token %>" name="customerAuthenticationStatus.qaTime" editable="false" style="width:150px"/></td>
                </tr>

                <tr>
                    <td align="right">视频：</td>
                    <td>
                        <select class="easyui-combobox" type="text" editable="false" id="video_Status<%=token %>" name="customerAuthenticationStatus.videoStatus" style="width:70px">
                            <option value="0">未认证</option>
                            <option value="1">已认证</option>
                        </select>
                    </td>
                    <td align="right">时间：</td>
                    <td><input class="easyui-datetimebox" type="text" id="videoTime<%=token %>" name="customerAuthenticationStatus.videoTime" editable="false" style="width:150px"/></td>
                </tr>

                <tr>
                    <td align="right">现场：</td>
                    <td>
                        <select class="easyui-combobox" type="text" editable="false" id="face_Status<%=token %>" name="customerAuthenticationStatus.faceStatus" style="width:70px">
                            <option value="0">未认证</option>
                            <option value="1">已认证</option>
                        </select>
                    </td>
                    <td align="right">时间：</td>
                    <td><input class="easyui-datetimebox" type="text" id="faceTime<%=token %>" name="customerAuthenticationStatus.faceTime" editable="false" style="width:150px"/></td>
                </tr>
            </table>
            <input  type="hidden" id="sid<%=token %>" name="customerAuthenticationStatus.sid"    style="width:200px"/>
            <input  type="hidden" id="id<%=token %>" name="customerAuthenticationStatus.id"    style="width:200px"/>
            <input  type="hidden" id="state<%=token %>" name="customerAuthenticationStatus.state"    style="width:200px"/>
            <input  type="hidden" id="operatorId<%=token %>" name="customerAuthenticationStatus.operatorId"    style="width:200px"/>
            <input  type="hidden" id="operateTime<%=token %>" name="customerAuthenticationStatus.operateTime"    style="width:200px"/>
            <input  type="hidden" id="customerId<%=token %>" name="customerAuthenticationStatus.customerId"    style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnCustomerAuthenticationStatusSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('CustomerAuthenticationStatusWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
