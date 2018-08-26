<%--
  Created by IntelliJ IDEA.
  User: yux
  Date: 2016/6/12
  Time: 9:45
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
        <form id='formProductionCommission<%=token %>' name='formProductionCommission' action='' method='post'>
            <table border='0' cellspacing='5' cellpadding='0'>
                <tr>
                    <td align="right">返佣类型</td>
                    <td><input  type="text" id="commissionType<%=token %>" name="productionCommission.commissionType"       style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">返佣级别</td>
                    <td><input  type="text" id="commissionLevel<%=token %>" name="productionCommission.commissionLevel"       style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">返佣率</td>
                    <td><input  type="text" id="commissionRate<%=token %>" name="productionCommission.commissionRate"       style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">区域编号</td>
                    <td><input  type="text" id="areaCode<%=token %>" name="productionCommission.areaCode"       style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">生效时间</td>
                    <td><input class="easyui-datebox"  editable="false" type="text" id="effectieTime<%=token %>" name="productionCommission.effectieTime" style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">期限</td>
                    <td><input class="easyui-datebox"  editable="false" type="text" id="commissionTime<%=token %>" name="productionCommission.commissionTime" style="width:200px"/></td>
                </tr>
            </table>
            <input  type='hidden' id='sid<%=token %>' name='productionCommission.sid' style='width:200px'/>
            <input  type='hidden' id='id<%=token %>' name='productionCommission.id' style='width:200px'/>
            <input  type='hidden' id='state<%=token %>' name='productionCommission.state' style='width:200px'/>
            <input  type='hidden' id='operatorId<%=token %>' name='productionCommission.operatorId' style='width:200px'/>
            <input type="hidden" id='operatorTime<%=token %>' name='productionCommission.operatorTime' style='width:200px'/>
        </form>
    </div>
    <div region='south' border='false' style='text-align:right;padding:5px;background:#F4F4F4'>
        <a id='btnProductionCommissionSubmit<%=token %>' class='easyui-linkbutton' iconCls='icon-ok' href='javascript:void(0)'>确定</a>
        <a class='easyui-linkbutton' iconCls='icon-cancel' href='javascript:void(0)' onClick="fwCloseWindow('productionCommissionWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
