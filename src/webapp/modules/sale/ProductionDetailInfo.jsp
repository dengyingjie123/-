<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 10/15/14
  Time: 1:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token");
    String root = Config.getWebRoot();
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
        <form id="formProduction<%=token %>" name="formProduction" action="" method="post">
            <table width="100%" border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">所属产品</td>
                    <td><input type="text" id="productHomeId<%=token %>" class="easyui-combotree" required="true"
                               missingmessage="必须填写" editable="false" name="production.productHomeId" style="width:120px"/>
                    </td>
                    <td align="right">名称</td>
                    <td colspan="3"><input type="text" id="name<%=token %>" class="easyui-validatebox"
                                           name="production.name" required="true" missingmessage="必须填写"
                                           validType="maxLength[40]" style="width:380px"/></td>
                    <td align="right">配额</td>
                    <td><input class="easyui-validatebox" type="text" id="size<%=token %>"
                               name="production.size" required="true" missingmessage="必须填写"
                               validType="FloatOrCurrency" style="width:120px"/>&nbsp;&nbsp;元
                    </td>
                </tr>
                <tr>
                    <td align="right">开始时间</td>
                    <td><input type="text" class="easyui-datebox" required="true"
                               missingmessage="必须填写" editable="false" id="startTime<%=token %>"
                               name="production.startTime" style="width:120px"/></td>
                    <td align="right">结束时间</td>
                    <td><input class="easyui-datebox" required="true"
                               missingmessage="必须填写" editable="false" type="text" id="stopTime<%=token %>"
                               name="production.stopTime" style="width:120px"/></td>
                    <td align="right">起息日</td>
                    <td><input type="text" id="valueDate<%=token %>" class="easyui-datebox" required="true"
                               missingmessage="必须填写" editable="false" name="production.valueDate" style="width:120px"/>
                    </td>
                    <td align="right">到期日</td>
                    <td><input type="text" id="expiringDate<%=token %>" class="easyui-datebox" required="true"
                               missingmessage="必须填写" editable="false" name="production.expiringDate"
                               style="width:120px"/></td>
                </tr>

                <tr>
                    <td align="right">付息日</td>
                    <td><input type="text" id="interestDate<%=token %>" class="easyui-datebox" required="true"
                               missingmessage="必须填写" editable="false" name="production.interestDate"
                               style="width:120px"/></td>
                    <td align="right">预约金额</td>
                    <td><input type="text" id="appointmentMoney<%=token %>" name="production.appointmentMoney"
                               style="width:120px" readonly="true"/></td>
                    <td align="right">销售金额</td>
                    <td><input type="text" id="saleMoney<%=token %>" name="production.saleMoney" style="width:120px"
                               readonly="true"/></td>
                    <td align="right">状态</td>
                    <td><input type="text" id="status<%=token %>" class="easyui-combotree"
                               data-options="url:'<%=root %>/modules/production/Production_Status.jsp',method:'get',required:true"
                               name="production.status" required="true" missingmessage="必须选择" style="width:120px"/></td>
                </tr>

                <tr>
                    <td align="right">付息类型</td>
                    <td><input type="text" class="easyui-combotree" data-options="required:true"
                               class="easyui-combotree"
                               id="interestType<%=token %>" name="production.interestType" style="width:120px"/></td>
                    <td align="right">付息周期</td>
                    <td><input type="text" class="easyui-validatebox" id="interestCycle<%=token %>"
                               data-options="required:true,validType:'biggerThenZero'" invalidMessage="请输入大于零的整数"
                               name="production.interestCycle" style="width:120px"/>
                    </td>
                    <td align="right">付息单位</td>
                    <td><input type="text" class="easyui-combotree" data-options="required:true"
                               class="easyui-combotree"
                               id="interestUnit<%=token %>" name="production.interestUnit" style="width:120px"/>
                    </td>
                    <td align="right">付息期数</td>
                    <td><input type="text" class="easyui-validatebox" id="interestTimes<%=token %>"
                               data-options="required:true,validType:'biggerThenZero'" invalidMessage="请输入大于零的整数"
                               name="production.interestTimes" style="width:120px"/>&nbsp;&nbsp;期
                    </td>

                </tr>
                <tr>
                    <td align="right">合同一式</td>
                    <td><input type="text" class="easyui-validatebox" id="contractCopies<%=token %>"
                               data-options="required:true,validType:'biggerThenZero'" invalidMessage="请输入大于零的整数"
                               name="production.contractCopies" style="width:120px"/>&nbsp;&nbsp;份
                    </td>
                    <td align="right">产品编号</td>
                    <td><input type="text" id="productId<%=token %>" name="production.productId" style="width:120px"
                               readonly="true"/></td>
                    <td align="right">兑付本金</td>
                    <td><input type="text" id="productId<%=token %>" name="production.paymentMoney" style="width:120px"
                               readonly="true"/></td>
                    <td align="right">兑付收益</td>
                    <td><input type="text" id="productId<%=token %>" name="production.paymentProfitMoney"
                               style="width:120px" readonly="true"/></td>

                </tr>
                <tr>
                    <td align="right"><span id="scheduleId<%=token%>">进度</span></td>
                    <td><span id="appointmentId<%=token%>">预约：
                        <input type="text" id="appointmentProcessId<%=token %>"
                               style="width:30px;border: 0px;" name="production.appointmentProcess" readonly="true"/>
                        &nbsp; 销售：
                        <input type="text" id="saleProcessId<%=token %>" name="production.saleProcess"
                               style="width:30px;border: 0px;" readonly="true"/> &nbsp;<br/>
                        兑付：<input type="text" id="paymentProcessId<%=token %>" name="production.paymentProcess"
                                  style="width:30px;border: 0px;" readonly="true"/>
                        </span>
                    </td>
                </tr>
            </table>
            <input type="hidden" id="operatorId<%=token %>" name="production.operatorId" style="width:200px"/>
            <input type="hidden" id="sid<%=token %>" name="production.sid" style="width:200px"/>
            <input type="hidden" id="id<%=token %>" name="production.id" style="width:200px"/>
            <input type="hidden" id="operateTime<%=token %>" name="production.operateTime" style="width:200px"/>
            <input type="hidden" id="state<%=token %>" name="production.state" style="width:200px"/>

            <div style="width: 100%" id="productionTable<%=token%>">
                <div id="contentTabs" class="easyui-tabs" fit="true" border="true" style="overflow:auto;">
                    <div title="产品构成管理" style="padding:5px ;">
                        <table id="ProductioncompositionTable<%=token%>" style="height: 320px"></table>
                    </div>
                    <div title="产品信息" style="padding:5px ;">
                        <table border="0" id="ProductionInfoTable_Description<%=token%>"
                               style="height: 340px;width: 100%">
                            <tr>
                                <td align="right">网站显示名称</td>
                                <td><input type="text" class="easyui-vaildatebox" id="websiteDisplayName<%=token%>"
                                           name="production.websiteDisplayName" style="width: 300px"/></td>
                            </tr>
                            <tr>
                                <td align="right">网站显示投资期限：</td>
                                <td><input type="text" class="easyui-validatebox" id="InvestTermView<%=token%>"
                                           name="production.investTermView" data-options="required:true"
                                           style="width: 300px"/></td>
                            </tr>
                            <tr>
                                <td align="right">网站显示期限范围查询：</td>
                                <td><input type="text" class="easyui-validatebox" id="InvestTerm<%=token%>"
                                           name="production.investTerm"
                                           data-options="required:true,validType:'number'"
                                           invalidMessage="请输入大于等于零的整数" style="width: 300px"/>天
                                </td>
                            </tr>
                            <tr>
                                <td align="right" valign="top">产品描述</td>
                                <td><textarea id="description<%=token %>" name="productionInfo.description"
                                              style="width:700px"></textarea>
                                </td>
                            </tr>
                        </table>
                        <input type="hidden" id="sid<%=token %>" name="productionInfo.sid" style="width:200px"/>
                        <input type="hidden" id="id<%=token %>" name="productionInfo.id" style="width:200px"/>
                        <input type="hidden" id="state<%=token %>" name="productionInfo.state" style="width:200px"/>
                        <input type="hidden" id="productionId<%=token %>" name="productionInfo.productionId"
                               style="width:200px"/>
                    </div>
                </div>
            </div>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('ProductionWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>