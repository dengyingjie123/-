/**
* Created by Jepson on 2015/6/5.
*/
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
        <form id='formBorrowMoney<%=token %>' name='formborrowMoney' action='' method='post'>
            <table border='0' cellspacing='5' cellpadding='0'>
                <tr>
                    <td align="right">公司名称：</td>
                    <td ><input type="text" class="easyui-combotree" data-options="required:'true'" id="controlString1<%=token %>"
                               name="borrowMoneyVO.controlString1" style="width:300px"/></td>
                </tr>
                <tr>
                    <td align="right">部门名称：</td>
                    <td ><input type="text" class="easyui-combotree" data-options="required:'true'" id="controlString2<%=token %>"
                               name="borrowMoneyVO.controlString2" style="width:300px"/></td>
                </tr>
                <tr>
                    <td align="right">申请人：</td>
                    <td><input type="text"  class='easyui-validatebox' data-options='required:true' id="applicantName<%=token %>" name="borrowMoney.applicantName"
                                style='width:300px'/></td>

                </tr>
                <tr>
                    <td align='right'>申请时间：</td>
                    <td><input type='text'  id='applicationTime<%=token %>'
                               name='borrowMoney.applicationTime' readonly="readonly" style='width:300px'/>
                       </td>
                </tr>
                <tr>
                    <td align="right">申请金额</td>
                    <td><input type="text" id="money<%=token %>" name="borrowMoney.money"
                               class='easyui-validatebox' data-options='required:true' validType="FloatOrCurrency"   style='width:300px'/> 元</td>

                </tr>
                <tr>
                    <td align="right">申请用途：</td>
                    <td ><textarea type="text" id="applicationPurpose<%=token %>" name="borrowMoney.applicationPurpose"
                                              class='easyui-validatebox' data-options='required:true'
                                              style='resize:none;width:300px;height:50px'/></td>
                </tr>
            </table>
            <input type='hidden' id='sid<%=token %>' name='borrowMoney.sid' style='width:200px'/>
            <input type='hidden' id='id<%=token %>' name='borrowMoney.id' style='width:200px'/>
            <input type='hidden' id='state<%=token %>' name='borrowMoney.state' style='width:200px'/>
            <input type='hidden' id='operatorId<%=token %>' name='borrowMoney.operatorId' style='width:200px'/>
            <input type='hidden' id='applicantId<%=token %>' name='borrowMoney.applicantId' style='width:200px'/>
        </form>

    </div>
    <div region='south' border='false' style='text-align:right;padding:5px;background:#F4F4F4'>
        <a id='btnBorrowMoneySubmit<%=token %>' class='easyui-linkbutton' iconCls='icon-ok'
           href='javascript:void(0)'>确定</a>
        <a id='btnBorrowMoneySubmit_start<%=token %>' class='easyui-linkbutton'
           iconCls='icon-ok' href='javascript:void(0)'>业务审批</a>
        <a id='btnBorrowMoneySubmit_applay<%=token %>' class='easyui-linkbutton'
           iconCls='icon-ok' href='javascript:void(0)'>业务申请</a>
        <a class='easyui-linkbutton' iconCls='icon-cancel' href='javascript:void(0)'
           onClick="fwCloseWindow('borrowMoneyWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
