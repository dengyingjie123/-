<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015-5-18
  Time: 下午 10:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType='text/html; charset=utf-8' language='java' errorPage='' import='com.youngbook.common.config.*' %>
<%
    String token = request.getParameter("token");
%>
<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>
<html xmlns='http://www.w3.org/1999/xhtml'>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset=utf-8'/>
    <title>Untitled Document</title>
</head>
<body>
<div class='easyui-layout' fit='true'>
    <div region='center' border='false' style='padding:10px;background:#fff;border:0px solid #ccc;'>
        <form id='formFinanceBizTripExpenseWFATable<%=token %>' name='formFinanceBizTripExpenseWFATable' action='' method='post'>
            <table border='0' cellspacing='5' cellpadding='0'>
                <tr>
                <td align="right">公司名称：</td>
                <td colspan="4"><input type="text" class="easyui-combotree" data-options="required:'true'" id="controlString1<%=token %>"
                                       name="financeBizTripExpenseWFAVO.controlString1" style="width:400px"/></td>
                </tr>
                <tr>
                    <td align="right">部门名称：</td>

                    <td colspan="4"><input type="text" class="easyui-combotree" data-options="required:'true'" id="controlString2<%=token %>"
                                           name="financeBizTripExpenseWFAVO.controlString2" style="width:400px"/></td>
                </tr>
                <tr>
                    <td align='right'>出差人姓名</td>
                    <td colspan="4"><input type='text' id='userNames<%=token %>' class='easyui-validatebox'data-options="required:'true'"  name='financeBizTripExpenseWFA.userNames'
                               style='width:400px'/></td>

                </tr>
                <tr>
                    <td align='right'>报销人</td>
                    <td><input type='text' id='reimburseName<%=token %>'  class='easyui-validatebox' data-options="required:'true'" name='reimburseName' readonly="false"
                               style='width:200px'/></td>
                    <td></td>
                    <td align='right'>总金额</td>
                    <td><input type='text' id='money<%=token %>'
                               name='financeBizTripExpenseWFA.money' readonly="readonly" validType="FloatOrCurrency"  style='width:200px'/>
                    </td>

                </tr>
                <tr>
                    <td align='right'>报销日期</td>
                    <td><input class='easyui-datetimebox' type='text' id='time<%=token %>'
                               name='financeBizTripExpenseWFA.time' data-options='required:true' editable='false'
                               style='width:200px'/></td>
                    <td></td>
                    <td align="right">附件张数</td>
                    <td>
                        <input type="text" id="accessoryNumber<%=token %>" name="financeBizTripExpenseWFA.accessoryNumber"   style="width:200px"/>张
                    </td>
                </tr>
                <tr>
                    <td align='right'>事由</td>
                    <td colspan="4"><textarea class='easyui-validatebox' type='text' id='comment<%=token %>'
                                              name='financeBizTripExpenseWFA.comment' data-options='required:true'
                                              style='width:460px; height:25px;'></textarea></td>
                </tr>
                <!-- 附件上传开始 -->
                <tr id="uploadTR<%=token%>">
                    <td align="right">附件上传</td>
                    <td><a id="btnUpload<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
                           href="javascript:void(0)">正在查询上传记录...</a></td>
                </tr>
            </table>
            <div style='height: 300px' id="financeBizTripDIV<%=token%>">
                <div class='easyui-tabs' fit='true' border='true' style='overflow:auto;'>
                    <div title='报销项目' style='padding:5px'>
                        <table id='financeBizTripExpenseItemTable<%=token%>'></table>
                    </div>
                </div>
            </div>
            <input type='hidden' id='sid<%=token %>' name='financeBizTripExpenseWFA.sid' style='width:200px'/>
            <input type='hidden' id='id<%=token %>' name='financeBizTripExpenseWFA.id' style='width:200px'/>
            <input type='hidden' id='operatorId<%=token %>' name='financeBizTripExpenseWFA.operatorId'/>
            <input type='hidden' id='orgId<%=token %>' name='financeBizTripExpenseWFA.orgId' style='width:200px'/>
            <input type='hidden' id='userId<%=token %>' name='financeBizTripExpenseWFA.userId' style='width:200px'/>
            <input type='hidden' id='reimburseId<%=token %>' name='financeBizTripExpenseWFA.reimburseId'
                   style='width:200px'/>
            <input type='hidden' id='reimburseTime<%=token %>' name='financeBizTripExpenseWFA.reimburseTime'
                   style='width:200px'/>
            <input type='hidden' id='orgName<%=token %>' name='orgName' readonly="readonly"
                                               style='width:200px'/>
            <input type="hidden"  id="controlString3<%=token %>"
                   name="financeBizTripExpenseWFAVO.controlString3" style="width:400px"/>
        </form>

    </div>
    <div region='south' border='false' style='text-align:right;padding:5px;background:#F4F4F4'>
        <a id='btnFinanceBizTripExpenseWFASubmit<%=token %>' class='easyui-linkbutton' iconCls='icon-ok'
           href='javascript:void(0)'>确定</a>
        <a id='btnFinanceBizTripExpenseWFASubmit_start<%=token %>' class='easyui-linkbutton' iconCls='icon-ok'
           href='javascript:void(0)'>业务审批</a>
        <a id='btnFinanceBizTripExpenseWFASubmit_applay<%=token %>' class='easyui-linkbutton' iconCls='icon-ok'
           href='javascript:void(0)'>业务申请</a>
        <a class='easyui-linkbutton' iconCls='icon-cancel' href='javascript:void(0)'
           onClick="fwCloseWindow('financeBizTripExpenseWFAWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>