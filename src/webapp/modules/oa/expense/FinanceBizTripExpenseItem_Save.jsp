<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015-5-18
  Time: 下午 11:40
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
        <form id='formFinanceBizTripExpenseItem<%=token %>' name='formFinanceBizTripExpenseItem' action=''
              method='post'>
            <table border='0' cellspacing='5' cellpadding='0'>
                <tr>
                    <td align='right'>开始时间</td>
                    <td><input type="text" data-options="required:true" class='easyui-datetimebox'
                               id='startTime<%=token %>' name='financeBizTripExpenseItem.startTime' editable='false'
                               style='width:200px'/></td>
                </tr>
                <tr>
                    <td align='right'>结束时间</td>
                    <td><input type="text" data-options="required:true" class='easyui-datetimebox'
                               id='endTime<%=token %>' name='financeBizTripExpenseItem.endTime' editable='false'
                               style='width:200px'/></td>
                </tr>
                <tr>
                    <td align='right'>起始地</td>
                    <td><input type="text" data-options="required:true" class='easyui-validatebox'
                               id='startAddress<%=token %>' name='financeBizTripExpenseItem.startAddress'
                               style='width:200px'/></td>
                </tr>
                <tr>
                    <td align='right'>结束地</td>
                    <td><input type="text" data-options="required:true" class='easyui-validatebox'
                               id='endAddress<%=token %>' name='financeBizTripExpenseItem.endAddress'
                               style='width:200px'/></td>
                </tr>
                <tr>
                    <td align='right'>过路费</td>
                    <td><input type="text" class='easyui-validatebox' data-options="validType:'currency'"
                               id='roadFee<%=token %>' name='financeBizTripExpenseItem.roadFee'
                               style='width:200px'/>元
                    </td>
                </tr>
                <tr>
                    <td align='right'>飞机票费</td>
                    <td><input type="text" class='easyui-validatebox' data-options="validType:'currency'"
                               id='airplaneFee<%=token %>' name='financeBizTripExpenseItem.airplaneFee'
                               style='width:200px'/>元
                    </td>
                </tr>
                <tr>
                    <td align='right'>火车票费</td>
                    <td><input type="text" class='easyui-validatebox' data-options="validType:'currency'"
                               id='trainFee<%=token %>' name='financeBizTripExpenseItem.trainFee' style='width:200px'/>元
                    </td>
                </tr>
                <tr>
                    <td align='right'>汽车票费</td>
                    <td><input type="text" class='easyui-validatebox' data-options="validType:'currency'"
                               id='busFee<%=token %>' name='financeBizTripExpenseItem.busFee' style='width:200px'/>元</td>
                </tr>
                <tr>
                    <td align='right'>伙食补贴</td>
                    <td><input type="text" class='easyui-validatebox' data-options="validType:'currency'"
                               id='foodFee<%=token %>' name='financeBizTripExpenseItem.foodFee' style='width:200px'/>元
                    </td>
                </tr>
                <tr>
                    <td align='right'>住宿费</td>
                    <td><input type="text" class='easyui-validatebox' data-options="validType:'currency'"
                               id='liveFee<%=token %>' name='financeBizTripExpenseItem.liveFee' style='width:200px'/>元
                    </td>
                </tr>
                <tr>
                    <td align='right'>其他</td>
                    <td><input type="text" class='easyui-validatebox' data-options="validType:'currency'"
                               id='otherFee<%=token %>' name='financeBizTripExpenseItem.otherFee' style='width:200px'/>元
                    </td>
                </tr>
                <tr>
                    <td align='right'>合计</td>
                    <td><input type="text" class='easyui-validatebox' readonly="readonly" id='totalFee<%=token %>'
                               name='financeBizTripExpenseItem.totalFee' style='width:200px'/>元</td>
                </tr>
                <tr>
                    <td align='right'>备注</td>
                    <td><input type="text" class='easyui-validatebox' id='comment<%=token %>'
                               name='financeBizTripExpenseItem.comment' style='width:200px'/></td>
                </tr>
            </table>
            <input type='hidden' id='sid<%=token %>' name='financeBizTripExpenseItem.sid' style='width:200px'/>
            <input type='hidden' id='id<%=token %>' name='financeBizTripExpenseItem.id' style='width:200px'/>
            <input type='hidden' id='state<%=token %>' name='financeBizTripExpenseItem.state' style='width:200px'/>
            <input type='hidden' id='operatorId<%=token %>' name='financeBizTripExpenseItem.operatorId'
                   style='width:200px'/>
            <input type="hidden" class='easyui-validatebox' id='expenseId<%=token %>'
                   name='financeBizTripExpenseItem.expenseId' style='width:200px'/>
        </form>
    </div>
    <div region='south' border='false' style='text-align:right;padding:5px;background:#F4F4F4'>
        <a id='btnFinanceBizTripExpenseItemSubmit<%=token %>' class='easyui-linkbutton' iconCls='icon-ok'
           href='javascript:void(0)'>确定</a>
        <a class='easyui-linkbutton' iconCls='icon-cancel' href='javascript:void(0)'
           onClick="fwCloseWindow('financeBizTripExpenseItemWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>