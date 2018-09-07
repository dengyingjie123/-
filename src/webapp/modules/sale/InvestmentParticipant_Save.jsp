<%--投资参与者--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>投资参与者</title>
</head>

<body>
<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
        <form id="formInvestmentParticipant<%=token %>" name="formInvestmentParticipant" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">


                <tr>
                    <td align="right">投资计划名称</td>
                    <td><input type="text" id="investmentId<%=token %>" name="investmentParticipant.investmentplanName"
                               style="width:200px"/></td>
                </tr>


                <tr>
                    <td align="right">客户名称</td>
                    <td><input type="text" id="customerName<%=token %>" name="investmentParticipant.customerName"
                               style="width:200px"/></td>
                </tr>


                <tr>
                    <td align="right">参与状态</td>
                    <td><input  editable="false" type="text" id="joinStatus<%=token %>"
                               name="investmentParticipant.kv_StatusName" style="width:200px"/></td>
                </tr>


                <tr>
                    <td align="right">参与类型</td>
                    <td><input  editable="false" type="text" id="joinType<%=token %>"
                               name="investmentParticipant.kv_TypeName" style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">参与金额</td>
                    <td><input type="text" id="joinMoney<%=token %>" name="investmentParticipant.joinMoney"
                               style="width:200px"/></td>
                </tr>


                <tr>
                    <td align="right">参与时间</td>
                    <td><input class="easyui-datetimebox" editable="false" readonly="readonly" type="text" id="joinTime<%=token %>"
                               name="investmentParticipant.joinTime" style="width:200px"/></td>
                </tr>


                <tr>
                    <td align="right">预约时间</td>
                    <td><input class="easyui-datetimebox" editable="false" type="text" id="appointmentTime<%=token %>"
                               name="investmentParticipant.appointmentTime" readonly="readonly" style="width:200px"/></td>
                </tr>


                <tr>
                    <td align="right">退出时间</td>
                    <td><input class="easyui-datetimebox" editable="false" type="text" id="quitTime<%=token %>"
                               name="investmentParticipant.quitTime" style="width:200px" readonly="readonly"/></td>
                </tr>
                <tr>
                    <td align="right">操作時間</td>
                    <td><input type="text" id="operateTime<%=token %>" name="investmentParticipant.operateTime"
                               style="width:200px" readonly="readonly"/></td>
                </tr>

                <tr>
                    <td align="right">操作员</td>
                    <td><input type="text" id="OperatorName<%=token %>"  readonly="readonly" name="investmentParticipant.operatorName"
                               style="width:200px"/></td>
                </tr>
            </table>
            <input type="hidden" id="sid<%=token %>" name="investmentParticipant.sid" style="width:200px"/>
            <input type="hidden" id="id<%=token %>" name="investmentParticipant.id" style="width:200px"/>
            <input type="hidden" id="operatorId<%=token%>" name="investmentParticipant.operatorId"
                   style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <%--<a id="btnInvestmentParticipantSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"--%>
           <%--href="javascript:void(0)">确定</a>--%>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('InvestmentParticipantWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>