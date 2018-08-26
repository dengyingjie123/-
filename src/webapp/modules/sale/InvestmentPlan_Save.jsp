<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/4/2
  Time: 14:32
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
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Untitled Document</title>
</head>

<body>
<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
        <form id="formInvestmentPlan<%=token %>" name="formInvestmentPlan" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right" style="width:80px">名称</td>
                    <td colspan="3"><input class="easyui-validatebox" type="text" id="name<%=token %>"
                                           name="investmentPlan.name" required="true" missingmessage="必须填写"
                                           style="width:500px"/></td>
                </tr>
                <tr>
                    <td align="right">类型编号</td>
                    <td colspan="3"><input class="easyui-validatebox" type="text" id="typeId<%=token %>"
                                           name="investmentPlan.typeId" required="true" missingmessage="必须填写"
                                           style="width:500px"/></td>
                </tr>
                <tr>
                    <td align="right" valign="top">描述</td>
                    <td colspan="3"><textarea id="description<%=token %>" name="investmentPlan.description"
                                              style="width: 450px;height: 70px"/></td>
                </tr>
                <tr>
                    <td align="right">最小投资额</td>
                    <td><input class="easyui-validatebox" type="text" id="investMoneyMin<%=token %>"
                               name="investmentPlan.investMoneyMin" data-options="required:true"
                               invalidMessage="请输入大于零的整数" validType="FloatOrCurrency" style="width:180px"/>元
                    </td>
                    <td align="right">最短投资期限</td>
                    <td><input class="easyui-validatebox" type="text" id="investTimeMin<%=token %>"
                               name="investmentPlan.investTimeMin"
                               data-options="required:true,validType:'biggerThenZero'" invalidMessage="请输入大于零的整数"
                               style="width:180px"/>天
                    </td>
                </tr>
                <tr>
                    <td align="right">最大投资额</td>
                    <td><input class="easyui-validatebox" type="text" id="investMoneyMax<%=token %>"
                               validType="FloatOrCurrency" name="investmentPlan.investMoneyMax"
                               data-options="required:true,validType:'biggerThenZero'" invalidMessage="请输入大于零的整数"
                               style="width:180px"/>元
                    </td>
                    <td align="right">最长投资期限</td>
                    <td><input class="easyui-validatebox" type="text" id="investTimeMax<%=token %>"
                               name="investmentPlan.investTimeMax"
                               data-options="required:true,validType:'biggerThenZero'" invalidMessage="请输入大于零的整数"
                               style="width:180px"/>天
                    </td>
                </tr>

                <tr>
                    <td align="right">最小回报率</td>
                    <td><input class="easyui-validatebox" type="text" id="returnRateMin<%=token %>"
                               name="investmentPlan.returnRateMin" data-options="required:true,validType:'intOrFloat'"
                               missingmessage="必须填写" style="width:180px"/>%
                    </td>
                    <td align="right">最大回报率</td>
                    <td><input class="easyui-validatebox" type="text" id="returnRateMax<%=token %>"
                               name="investmentPlan.returnRateMax" data-options="required:true,validType:'intOrFloat'"
                               missingmessage="必须填写" style="width:180px"/>%
                    </td>
                </tr>
                <tr>
                    <td align="right">计划开始时间</td>
                    <td><input type="text" class="easyui-datetimebox" id="planStartTime<%=token %>"
                               name="investmentPlan.planStartTime" style="width:180px" editable="false" required="true"
                               missingmessage="必须填写"/></td>
                    <td align="right">计划结束时间</td>
                    <td><input type="text" class="easyui-datetimebox" id="planTimeStop<%=token %>"
                               name="investmentPlan.planTimeStop" style="width:180px" editable="false" required="true"
                               missingmessage="必须填写"/></td>
                </tr>
                <tr>
                    <td align="right">网站显示投资期限</td>
                    <td>
                        <input type="text" class="easyui-validatebox" id="investTermView<%=token %>"
                               name="investmentPlan.investTermView" style="width:180px" style="width:180px"
                               required="true"
                               missingmessage="必须填写"/></td>
                    <td align="right">网站投资期限范围查询</td>
                    <td>
                        <input type="text" class="easyui-validatebox" id="investTerm<%=token %>"
                               name="investmentPlan.investTerm" data-options="required:true,validType:'biggerThenZero'"
                               invalidMessage="请输入大于零的整数"/>
                    </td>
                </tr>

                <tr>
                    <td align="right">创建人</td>
                    <td><input class="easyui-validatebox" type="text" id="operatorName<%=token %>"
                               name="investmentPlan.operatorName" style="width:180px" readonly="true"/></td>
                    <td align="right">创建时间</td>
                    <td><input type="text" class="easyui-datetimebox" id="createTime<%=token %>"
                               name="investmentPlan.createTime" style="width:180px" required="true"
                               missingmessage="必须填写" editable="false"/></td>
                </tr>
                <tr>
                    <td align="right">审核人</td>
                    <td><input class="easyui-validatebox" type="text" id="operatorName<%=token %>"
                               name="investmentPlan.operatorName" style="width:180px" readonly="true"/></td>
                    <td align="right">审核时间</td>
                    <td><input type="text" class="easyui-datetimebox" id="checkTime<%=token %>"
                               name="investmentPlan.checkTime" style="width:180px" editable="false" required="true"
                               missingmessage="必须填写"/></td>
                </tr>
                <tr>
                    <td align="right">发布人</td>
                    <td><input class="easyui-validatebox" type="text" id="operatorName<%=token %>"
                               name="investmentPlan.operatorName" style="width:180px" readonly="true"/></td>
                    <td align="right">发布时间</td>
                    <td><input type="text" class="easyui-datetimebox" id="publishTime<%=token %>"
                               name="investmentPlan.publishTime" style="width:180px" editable="false" required="true"
                               missingmessage="必须填写"/></td>
                </tr>
                <%--<tr>--%>
                <%--<td align="right">操作人</td>--%>
                <%--<td><input type="text" id="operatorName<%=token %>" name="investmentPlan.operatorName" disabled="disabled" style="width:200px"/></td>--%>
                <%--</tr>--%>
            </table>
            <input type="hidden" id="operatorId<%=token %>" name="investmentPlan.operatorId" style="width:200px"/>
            <input type="hidden" id="sid<%=token %>" name="investmentPlan.sid" style="width:200px"/>
            <input type="hidden" id="id<%=token %>" name="investmentPlan.id" style="width:200px"/>
            <input type="hidden" id="operateTime<%=token %>" name="investmentPlan.operateTime" style="width:200px"/>
            <input type="hidden" id="state<%=token %>" name="investmentPlan.state" style="width:200px"/>
            <input type="hidden" id="creatorId<%=token %>" name="investmentPlan.creatorId" style="width:200px"/>
            <input type="hidden" id="checkerId<%=token %>" name="investmentPlan.checkerId" style="width:200px"/>
            <input type="hidden" id="publisherId<%=token %>" name="investmentPlan.publisherId" style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnInvestmentPlanSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('InvestmentPlanWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
