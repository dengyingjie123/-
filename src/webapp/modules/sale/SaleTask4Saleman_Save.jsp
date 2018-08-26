<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/22
  Time: 9:49
  To change this template use File | Settings | File Templates.
  销售产品队员分配
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
        <form id='formSaleTask4Saleman<%=token %>' name='formSaleTask4Saleman' action='' method='post'>
            <table border='0' cellspacing='5' cellpadding='0'>

                <tr>
                    <td align="right">销售员</td>
                    <td><input type="text" id="salemanName<%=token %>" class="easyui-validatebox"
                               data-options="required:'true'" name="saleTask4SalemanVO.salemanName"
                               style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">分配金额</td>
                    <td><input class="easyui-validatebox" data-options="required:'true',validType:'currency'"
                               type="text" id="SalemantaskMoney<%=token %>" name="saleTask4Saleman.taskMoney"
                               style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">开始日期</td>
                    <td><input class="easyui-datebox" data-options="required:'true'" type="text"
                               id="startTime<%=token %>" editable="false" name="saleTask4Saleman.startTime" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">结束日期</td>
                    <td><input class="easyui-datebox" data-options="required:'true'" type="text"
                               id="endTime<%=token %>"  editable="false" name="saleTask4Saleman.endTime" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">待售金额</td>
                    <td><input readonly="readonly" class="easyui-validatebox"
                               type="text" id="waitingMoney<%=token %>" name="saleTask4Saleman.waitingMoney"
                               style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">预约金额</td>
                    <td><input  readonly="readonly" class="easyui-validatebox"
                               type="text" id="appointmengMoney<%=token %>" name="saleTask4Saleman.appointmengMoney"
                               style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">打款金额</td>
                    <td><input  readonly="readonly" class="easyui-validatebox"
                               type="text" id="soldMoney<%=token %>" name="saleTask4Saleman.soldMoney"
                               style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">累计取消金额</td>
                    <td><input readonly="readonly" class="easyui-validatebox"
                               type="text" id="totoalCancelMoney<%=token %>" name="saleTask4Saleman.totoalCancelMoney"
                               style="width:200px"/></td>
                </tr>
            </table>
            <input type='hidden' id='sid<%=token %>' name='saleTask4Saleman.sid' style='width:200px'/>
            <input type='hidden' id='id<%=token %>' name='saleTask4Saleman.id' style='width:200px'/>
            <input type='hidden' id='state<%=token %>' name='saleTask4Saleman.state' style='width:200px'/>
            <input type='hidden' id='operatorId<%=token %>' name='saleTask4Saleman.operatorId' style='width:200px'/>
            <input type="hidden" id="saleGroup<%=token %>" name="saleTask4Saleman.saleGroupId" style="width:200px"/>
            <input type="hidden" id="production<%=token %>" name="saleTask4Saleman.productionId" style="width:200px"/>
            <input type="hidden" id="salemanId<%=token %>" name="saleTask4Saleman.salemanId" style="width:200px"/>
        </form>
    </div>
    <div region='south' border='false' style='text-align:right;padding:5px;background:#F4F4F4'>
        <a id='btnSaleTask4SalemanSubmit<%=token %>' class='easyui-linkbutton' iconCls='icon-ok'
           href='javascript:void(0)'>确定</a>
        <a class='easyui-linkbutton' iconCls='icon-cancel' href='javascript:void(0)'
           onClick="fwCloseWindow('saleTask4SalemanWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
