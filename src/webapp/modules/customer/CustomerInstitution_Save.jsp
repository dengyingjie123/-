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
        <form id="formCustomerInstitution<%=token %>" name="formCustomerInstitution" action="" method="post" >
            <table width="100%" border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">姓名</td>
                    <td><input  type="text" id="name<%=token %>" class="easyui-validatebox" name="institution.name"  required="true" missingmessage="必须填写"  style="width:180px"/></td>
                    <td align="right">性质</td>
                    <td><input class="easyui-combotree"  editable="false" type="text" id="type<%=token %>" name="institution.type"  required="true" missingmessage="必须填写"  style="width:180px"/></td>
                    <td align="right">法人</td>
                    <td><input type="text" id="legalPerson<%=token %>" name="institution.legalPerson" style="width:180px"/></td>
                </tr>
                <tr>
                    <td align="right">注册资本</td>
                    <td><input  class="easyui-validatebox" type="text" id="registeredCapital<%=token %>" name="institution.registeredCapital" validType="intOrFloat" style="width:150px"/>&nbsp;万元</td>
                    <td align="right">移动电话</td>
                    <td><input type="text" id="mobile<%=token %>" class="easyui-validatebox" name="institution.mobile" validType="mobile" invalidMessage="请输入11位的手机号码(如130，135，138等开头号码)" style="width:180px"/></td>
                    <td align="right">移动电话2</td>
                    <td><input type="text" id="mobile2<%=token %>" class="easyui-validatebox" name="institution.mobile2" validType="mobile"  invalidMessage="请输入11位的手机号码(如130，135，138等开头号码)"  style="width:180px"/></td>
                </tr>
                <tr>
                    <td align="right">固定电话</td>
                    <td><input type="text" id="phone<%=token %>" class="easyui-validatebox" name="institution.phone" validType="phone" missingmessage="必须填写"  style="width:180px"/></td>
                    <td align="right">固定电话2</td>
                    <td><input type="text" id="phone2<%=token %>" name="institution.phone2"  class="easyui-validatebox" validType="phone" style="width:180px"/></td>
                    <td align="right">注册地址</td>
                    <td><input type="text" id="address<%=token %>" class="easyui-validatebox" name="institution.address" validType="address" style="width:180px"/></td>
                </tr>
                <tr style="display: none">
                    <td align="right">移动电话3</td>
                    <td><input  type="text" id="mobile3<%=token %>" name="institution.mobile3"  class="easyui-validatebox" validType="mobile"  invalidMessage="请输入11位的手机号码" style="width:180px"/></td>
                    <td align="right">固定电话3</td>
                    <td><input  type="text" id="phone3<%=token %>" name="institution.phone3" class="easyui-validatebox" validType="phone"   style="width:180px"/></td>
                </tr>
                <tr style="display: none">
                    <td align="right">移动电话4</td>
                    <td><input type="text" id="mobile4<%=token %>" name="institution.mobile4" class="easyui-validatebox" validType="mobile"  style="width:180px"/></td>
                    <td align="right">移动电话5</td>
                    <td><input  type="text" id="mobile5<%=token %>" name="institution.mobile5" class="easyui-validatebox" validType="mobile" style="width:180px"/></td>
                </tr>
                <tr>
                    <td align="right">邮编</td>
                    <td><input type="text" id="postNo<%=token %>" name="institution.postNo" class="easyui-validatebox" validType="zip" invalidMessage="邮政编码格式不正确，如(515133)" style="width:180px"/></td>
                    <td align="right">电子邮箱</td>
                    <td><input type="text" id="email<%=token %>" name="institution.email" class="easyui-validatebox" validType="email" style="width:180px"/></td>
                    <td align="right">电子邮箱2</td>
                    <td><input  type="text" id="email2<%=token %>" name="institution.email2" class="easyui-validatebox" validType="email" style="width:180px"/></td>
                </tr>
                <tr>
                    <td align="right">客户种类</td>
                    <td><input class="easyui-combotree"  editable="false" type="text" id="customerTypeId<%=token %>" name="institution.customerTypeId"  style="width:180px"/></td>
                    <td align="right">客户来源</td>
                    <td><input class="easyui-combotree"  editable="false" type="text" id="customerSourceId<%=token %>" name="institution.customerSourceId" style="width:180px"/></td>
                    <td align="right">关系等级</td>
                    <td><input class="easyui-combotree"  editable="false" type="text" id="relationshipLevelId<%=token %>" name="institution.relationshipLevelId" style="width:180px"/></td>
                </tr>
                <tr style="display: none">
                    <td align="right">电子邮箱3</td>
                    <td><input type="text" id="email3<%=token %>" name="institution.email3"  class="easyui-validatebox" validType="email" style="width:180px"/></td>
                    <td align="right">电子邮箱4</td>
                    <td><input  type="text" id="email4<%=token %>" name="institution.email4"  class="easyui-validatebox" validType="email" style="width:180px"/></td>
                </tr>
                <tr>
                    <td align="right" style="display: none">电子邮箱5</td>
                    <td  style="display: none"><input type="text" id="email5<%=token %>"  class="easyui-validatebox" validType="email"  name="institution.email5" style="width:180px"/></td>
                </tr>
                <tr>
                    <td align="right">信用等级</td>
                    <td><input class="easyui-combotree"  editable="false" type="text" id="creditRateId<%=token %>" name="institution.creditRateId" style="width:180px"/></td>
                    <td align="right">所属行业</td>
                    <td><input class="easyui-combotree"  editable="false" type="text" id="careerId<%=token %>" name="institution.careerId" style="width:180px"/></td>
                    <td align="right">人员规模</td>
                    <td><input class="easyui-combotree"  editable="false" type="text" id="staffSizeId<%=token %>" name="institution.staffSizeId" style="width:180px"/></td>
                </tr>
                <tr>
                    <td align="right">机构客户编号</td>
                    <td><input  editable="false" type="text" id="institutionNumber<%=token %>" name="institution.institutionNumber" readonly="true" style="width:180px"/></td>
                    <td align="right">创建时间</td>
                    <td><input  editable="false" type="text" id="creatTime<%=token %>" name="institution.creatTime"  readonly="true" style="width:180px"/></td>
                </tr>
            </table>
            <input  type="hidden" id="operatorId<%=token %>" name="institution.operatorId" style="width:180px"/>
            <input  type="hidden" id="sid<%=token %>" name="institution.sid"    style="width:180px"/>
            <input  type="hidden" id="id<%=token %>" name="institution.id"    style="width:180px"/>
            <input  type="hidden" id="operateTime<%=token %>" name="institution.operateTime"  style="width:180px"/>
            <input  type="hidden" id="state<%=token %>" name="institution.state" style="width:180px"/>
            <input  type="hidden" id="password<%=token %>" name="institution.password" style="width:180px"/>
        </form>
        <div style="height:280px">
            <div id="contentTabs" class="easyui-tabs" fit="true" border="true" style="overflow:auto;">
                <div  title="账户管理"  style="padding:5px ;" >
                    <table id="CustomerAccountTable<%=token%>"></table>
                </div>
                <div  title="证件管理"  style="padding:5px ;" >
                    <table id="CustomerCertificateTable<%=token%>"></table>
                </div>
                <%-- JIRA: HOPEWEALTH-1224 在机构客户页面中增加产品信息列表 --%>
                <div  title="产品信息"  style="padding:5px;">
                    <table id="CustomerProductionTable<%=token%>"></table>
                </div>
            </div>
        </div>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnCustomerInstitutionSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('CustomerInstitutionWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>