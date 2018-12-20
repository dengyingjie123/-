<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ToolbarPO" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ButtonPO" %>
<%
    String token = request.getParameter("token");
%>


<%
    // 创建不需要权限控制的按钮
    ButtonPO btnCustomerAccountAdd = new ButtonPO("btnCustomerAccountAdd" + token, "添加", "icon-add");
    ButtonPO btnCustomerAccountEdit = new ButtonPO("btnCustomerAccountEdit" + token, "修改", "icon-edit", "客户管理_银行账号修改");
    ButtonPO btnCustomerAccountDelete = new ButtonPO("btnCustomerAccountDelete" + token, "删除", "icon-cut","客户管理_银行账号删除");
    ButtonPO btnAccountCheck = new ButtonPO("btnAccountCheck" + token, "审核", "icon-edit");
// 创建Toolbar，并受权限控制
    ToolbarPO toolbarOfAccount = ToolbarPO.getInstance(request);
// 直接添加按钮，后台程序会自动判断，有权限则添加，没有权限则不添加
    toolbarOfAccount.addButton(btnCustomerAccountAdd);
    toolbarOfAccount.addButton(btnCustomerAccountEdit);
    toolbarOfAccount.addButton(btnCustomerAccountDelete);
//    toolbarOfAccount.addButton(btnAccountCheck);
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Untitled Document</title>
</head>

<body>

<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:0px;background:#fff;border:0px solid #ccc;">


        <!--------------------------------------------------------------------------------
             客户管理基本信息部分
            -------------------------------------------------------------------------------->
        <div id="p" class="easyui-panel" title="基本信息" style="height:250px;padding:10px;">

            <form id="formCustomerPersonal<%=token %>" name="formCustomerPersonal" action="" method="post">

                <table width="100%" border="0" cellpadding="5">
                    <tr>
                        <td align="right">姓名</td>
                        <td><input type="text" id="name<%=token %>" class="easyui-validatebox" name="personal.name" validtype="maxLength[20]" required="true" missingmessage="必须填写" style="width:180px"/></td>
                        <td align="right">用户名</td>
                        <td><input type="text" id="loginName<%=token %>" class="easyui-validatebox" name="personal.loginName" validtype="maxLength[20]" style="width:180px"/></td>
                        <td align="right">性别</td>
                        <td><input class="easyui-combotree" editable="false" type="text" id="sex<%=token %>" name="personal.sex" style="width:180px"/></td>
                    </tr>
                    <tr>
                        <td align="right">出生日期</td>
                        <td><input class="easyui-datebox" editable="false" type="text" id="birthday<%=token %>"
                                   name="personal.birthday" style="width:180px"/></td>
                        <td align="right">移动电话</td>
                        <td><input type="text" id="mobile<%=token %>" class="easyui-validatebox" name="personal.mobile"  style="width:180px"/>
                            <input type="hidden" id="mobileNotMasked<%=token %>" name="personal.mobileNotMasked"/>
                        </td>
                        <td align="right">移动电话2</td>
                        <td><input type="text" id="mobile2<%=token %>" class="easyui-validatebox" name="personal.mobile2" validtype="mobile" invalidmessage="仅支持13、14、15、17、18开头的11位手机号码" style="width:180px"/></td>
                    </tr>
                    <tr>
                        <td align="right">固定电话</td>
                        <td><input type="text" id="phone<%=token %>" name="personal.phone" class="easyui-validatebox" validtype="phone" style="width:180px"/></td>
                        <td align="right">客户分类</td>
                        <td><input class="easyui-combotree" editable="false" type="text" id="customerCatalogId<%=token %>" name="personal.customerCatalogId" style="width:180px"/></td>
                        <td align="right">默认推荐码</td>
                        <td><input class="easyui-combotree" type="text" id="referralCode<%=token %>" name="referralCode" editable="true" style="width:245px"/></td>
                    </tr>
                    <tr>
                        <td align="right">电子邮箱</td>
                        <td><input type="text" class="easyui-validatebox" id="email<%=token %>" name="personal.email" validtype="email" style="width:180px"/></td>
                        <td align="right">渠道类型</td>
                        <td><input class="easyui-combotree" editable="false" type="text" id="customerChannelTypeId<%=token %>" name="personal.customerChannelTypeId" style="width:180px"/></td>
                        <td align="right">客户来源</td>
                        <td><input class="easyui-combotree" editable="false" type="text" id="customerSourceId<%=token %>" name="personal.customerSourceId" style="width:180px"/></td>
                    </tr>
                    <tr>
                        <td align="right">客户种类</td>
                        <td><input class="easyui-combotree" editable="false" type="text" id="customerTypeId<%=token %>" name="personal.customerTypeId" style="width:180px"/></td>
                        <td align="right">关系等级</td>
                        <td><input class="easyui-combotree" editable="false" type="text" id="relationshipLevelId<%=token %>" name="personal.relationshipLevelId" style="width:180px"/></td>
                        <td align="right">从事职业</td>
                        <td><input class="easyui-combotree" editable="false" type="text" id="careerId<%=token %>" name="personal.careerId" style="width:180px"/></td>
                    </tr>
                    <tr>
                        <td align="right">信用等级</td>
                        <td><input class="easyui-combotree" editable="false" type="text" id="creditRateId<%=token %>" name="personal.creditRateId" style="width:180px"/></td>
                        <td align="right">个人客户编号</td>
                        <td><input editable="false" type="text" id="personalNumber<%=token %>"
                                   name="personal.personalNumber" readonly="true" style="width:180px"/></td>
                        <td align="right">创建时间</td>
                        <td><input editable="false" type="text" id="createTime<%=token %>" name="personal.createTime" readonly="true" style="width:180px"/></td>
                    </tr>
                    <tr>
                        <td align="right">家庭地址</td>
                        <td colspan="3"><input type="text" id="homeAddress<%=token %>" name="personal.homeAddress" maxlength="200" style="width:430px"/></td>
                        <td align="right">邮编</td>
                        <td><input type="text" class="easyui-validatebox" id="postNo<%=token %>" name="personal.postNo" validtype="zip" style="width:180px"/></td>
                    </tr>
                    <tr>
                        <td align="right">工作地址</td>
                        <td colspan="3"><input type="text" id="workAddress<%=token %>" name="personal.workAddress" maxlength="200" style="width:430px"/></td>
                        <td align="right" valign="top">国籍</td>
                        <td><input type="text" id="nationId<%=token %>" name="personal.nationId" style="width:180px"/></td>
                    </tr>
                    <tr>
                        <td align="right">身份证地址</td>
                        <td colspan="3"><input type="text" id="identityCardAddress<%=token %>" class="easyui-validatebox" name="personal.identityCardAddress" validtype="maxLength[200]" missingmessage="必须填写" style="width:430px"/></td>
                        <td align="right">备注</td>
                        <td><textarea rows="1" type="text" id="remark<%=token %>" name="personal.remark" validtype="maxLength[200]" style="width:180px"/></td>
                    </tr>
                    <tr>
                        <td align="right">通联金融圈编号</td>
                        <td><input type="text" class="easyui-validatebox" id="allinpayCircle_SignNum<%=token %>" name="personal.allinpayCircle_SignNum" style="width:180px" readonly/></td>
                        <td align="right">客户编号</td>
                        <td><input type="text" class="easyui-validatebox" id="id<%=token %>" name="personal.id" style="width:180px" readonly/></td>
                        <td align="right">&nbsp;</td>
                        <td>&nbsp;</td>
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
                <input type="hidden" id="transactionPassword<%=token%>" name="personal.transactionPassword" style="width:180px" />
                <input type="hidden" id="gesturePassword<%=token%>" name="personal.gesturePassword" style="width:180px" />
                <input type="hidden" id="gesturePasswordStatus<%=token%>" name="personal.gesturePasswordStatus" style="width:180px" />
                <input type="hidden" id="referralCode<%=token%>" name="personal.referralCode" style="width:180px" />
                <input type="hidden" id="confirmInvestor<%=token%>" name="personal.confirmInvestor" style="width:180px" />
                <input type="hidden" id="phone2<%=token %>" name="personal.phone2" validtype="phone" style="width:180px"/>
                <input type="hidden" id="email2<%=token %>" name="personal.email2" validtype="email" style="width:180px"/>

            </form>
        </div><!-- 客户管理基本信息部分 结束 -->
        <br>


        <div style="height:200px">
            <div id="contentTabs" class="easyui-tabs" fit="true" border="true" style="overflow:auto;">
                <div title="账户管理" style="padding:5px ;">
                    <table id="CustomerAccountTable<%=token%>"  data-options="toolbar:customerAccountToolbar">
                        <script type="text/javascript">
                            var customerAccountToolbar = <%=toolbarOfAccount.toJsonObject().getJSONArray("buttons").toString()%>
                        </script>
                    </table>
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
        <table width="100%">
            <tr>
                <td width="50%" align="left">
                    <a id="btnCustomerPersonalDialNew<%=token %>" class="easyui-linkbutton"
                       href="javascript:void(0)">呼叫</a><%--HOPEWEALTH-1276--%>
                </td>
                <td>
                    <a id="btnCustomerPersonalSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
                       href="javascript:void(0)">确定</a>
                    <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
                       onClick="fwCloseWindow('CustomerPersonalWindow<%=token%>')" >取消</a>
                </td>
            </tr>
        </table>

    </div>
</div>
</body>
</html>