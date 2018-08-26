<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/22
  Time: 9:15
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
        <form id='formSaleTask4Group<%=token %>' name='formSaleTask4Group' action='' method='post'>
            <table border='0' cellspacing='5' cellpadding='0'>
                <tr>
                    <td align="right">产品名称</td>
                    <td><input  type="text" data-options="required:'true'"  class="easyui-validatebox" id="productionName<%=token %>"  name="saleTask4GroupVO.productionName"       style="width:200px"/></td>

                    <td align="right">销售组名称</td>
                    <td><input  type="text" id="saleGroupName<%=token %>" data-options="required:'true'" class="easyui-validatebox" name="saleTask4GroupVO.saleGroupName"       style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">开始日期</td>
                    <td><input  type="text" editable='false'data-options="required:'true'" class="easyui-datebox" id="startTime<%=token %>" name="saleTask4Group.startTime"       style="width:200px"/></td>
                    <td align="right">结束日期</td>
                    <td><input  type="text" editable='false' data-options="required:'true'" class="easyui-datebox"   id="endTime<%=token %>" name="saleTask4Group.endTime"       style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">分配金额</td>
                    <td><input  type="text" data-options="required:'true',validType:'FloatOrCurrency'" class="easyui-validatebox" id="taskMoney<%=token %>" name="saleTask4Group.taskMoney"       style="width:200px"/></td>

                    <td align="right">待售金额</td>
                    <td><input readonly="readonly" type="text"     id="waitingMoney<%=token %>" name="saleTask4Group.waitingMoney"       style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">预约金额</td>
                    <td><input  readonly="readonly" type="text"  class="easyui-validatebox" id="appointmengMoney<%=token %>" name="saleTask4Group.appointmengMoney"       style="width:200px"/></td>

                    <td align="right">打款金额</td>
                    <td><input readonly="readonly" type="text"  class="easyui-validatebox" id="soldMoney<%=token %>" name="saleTask4Group.soldMoney"       style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">累计取消金额</td>
                    <td><input readonly="readonly" type="text"  class="easyui-validatebox" id="totoalCancelMoney<%=token %>" name="saleTask4Group.totoalCancelMoney"       style="width:200px"/></td>
                </tr>
            </table>
            <input  type='hidden' id='sid<%=token %>' name='saleTask4Group.sid' style='width:200px'/>
            <input  type='hidden' id='id<%=token %>' name='saleTask4Group.id' style='width:200px'/>
            <input  type='hidden' id='state<%=token %>' name='saleTask4Group.state' style='width:200px'/>
            <input  type='hidden' id='operatorId<%=token %>' name='saleTask4Group.operatorId' style='width:200px'/>
            <input  type='hidden' id='productionId<%=token %>' name='saleTask4Group.productionId' style='width:200px'/>
            <input  type='hidden' id='saleGroupId<%=token %>' name='saleTask4Group.saleGroupId' style='width:200px'/>
        </form>
        <br/>
        <div style='height: 300px'>
            <div class='easyui-tabs' fit='true' border='true' style='overflow:auto;'>
                <div title='销售人员分配列表'  style='padding:5px'>
                    <table id='saleTask4SalemanTable<%=token%>' ></table>
                </div>
            </div>
        </div>
    </div>
    <div region='south' border='false' style='text-align:right;padding:5px;background:#F4F4F4'>
        <a id='btnSaleTask4GroupSubmit<%=token %>' class='easyui-linkbutton' iconCls='icon-ok' href='javascript:void(0)'>确定</a>
        <a class='easyui-linkbutton' iconCls='icon-cancel' href='javascript:void(0)' onClick="fwCloseWindow('saleTask4GroupWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>