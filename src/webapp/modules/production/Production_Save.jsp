\\<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" import="com.youngbook.common.config.*" %>
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
                    <td colspan="3"><input type="text" id="productHomeId<%=token %>" class="easyui-combotree" required="true" missingmessage="必须填写" editable="false" name="production.productHomeId" style="width:500px"/>
                    </td>
                    <td align="right">产品编号</td>
                    <td><input type="text" id="id<%=token %>" class="easyui-validatebox" name="production.id"  readonly="readonly"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">融资机构</td>
                    <td colspan="5"><input type="text" id="financeInstitutionId<%=token %>" class="easyui-combotree" editable="false" name="production.financeInstitution" style="width:500px"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">名称</td>
                    <td colspan="5"><input type="text" id="name<%=token %>" class="easyui-validatebox" name="production.name" required="true" missingmessage="必须填写" validType="maxLength[40]" style="width:500px"/></td>
                    <td align="right">配额</td>
                    <td><input class="easyui-validatebox" type="text" id="size<%=token %>" name="production.size" required="true" missingmessage="必须填写" validType="FloatOrCurrency" style="width:120px"/>&nbsp;&nbsp;元
                    </td>
                </tr>
                <tr>
                    <td align="right">募集开始时间</td>
                    <td><input type="text" class="easyui-datebox" required="true" missingmessage="必须填写" editable="false" id="startTime<%=token %>" name="production.startTime" style="width:120px"/></td>
                    <td align="right">募集结束时间</td>
                    <td><input class="easyui-datebox" required="true" missingmessage="必须填写" editable="false" type="text" id="stopTime<%=token %>" name="production.stopTime" style="width:120px"/></td>
                    <td align="right">起息日</td>
                    <td>
                        <input type="text" id="valueDate<%=token %>" class="easyui-datebox" editable="false" name="production.valueDate" style="width:100px"/>
                        <a id="btnRemoveValueDate<%=token %>" href="javascript:void(0)">清空</a>
                    </td>
                    <td align="right">到期日</td>
                    <td><input type="text" id="expiringDate<%=token %>" class="easyui-datebox" editable="false" name="production.expiringDate" style="width:120px"/></td>
                </tr>
                <tr>
                    <td align="right">付息日</td>
                    <td><input type="text" id="interestDate<%=token %>" class="easyui-datebox" editable="false" name="production.interestDate" style="width:120px"/></td>
                    <td align="right">预约金额</td>
                    <td><input type="text" id="appointmentMoney<%=token %>" name="production.appointmentMoney" style="width:80px" readonly="true"/>（系统自动计算）</td>
                    <td align="right">销售金额</td>
                    <td><input type="text" id="saleMoney<%=token %>" name="production.saleMoney" style="width:80px" readonly="true"/>（系统自动计算）</td>
                    <td align="right">状态</td>
                    <td><input type="text" id="status<%=token %>" class="easyui-combotree" data-options="url:'<%=root %>/modules/production/Production_Status.jsp',method:'get',required:true" name="production.status" required="true" missingmessage="必须选择" style="width:120px"/></td>
                </tr>
                <tr>
                    <td align="right">排序</td>
                    <td><input type="text" class="easyui-validatebox" id="orders<%=token %>"  data-options="validType:'biggerThenZero'" invalidMessage="请输入大于零的整数" name="production.orders" style="width:120px"/></td>
                    <td align="right">付息周期</td>
                    <td><input type="text" class="easyui-validatebox" id="interestCycle<%=token %>" data-options="required:true,validType:'biggerThenZero'" invalidMessage="请输入大于零的整数" name="production.interestCycle" style="width:120px"/>
                    </td>
                    <td align="right">付息单位</td>
                    <td><input type="text" class="easyui-combotree" data-options="required:true" id="interestUnit<%=token %>" name="production.interestUnit" style="width:120px"/></td>
                    <td align="right">付息期数</td>
                    <td><input type="text" class="easyui-validatebox" id="interestTimes<%=token %>"  data-options="required:true,validType:'biggerThenZero'" invalidMessage="请输入大于零的整数" name="production.interestTimes" style="width:120px"/>&nbsp;&nbsp;期
                    </td>
                </tr>
                <tr>
                    <td align="right">合同一式</td>
                    <td><input type="text" class="easyui-validatebox" id="contractCopies<%=token %>" data-options="required:true,validType:'biggerThenZero'" invalidMessage="请输入大于零的整数" name="production.contractCopies" style="width:120px"/>&nbsp;&nbsp;份
                    </td>
                    <td align="right">产品分期编号</td>
                    <td><input type="text" id="productionNo<%=token %>" name="production.productionNo" style="width:120px" readonly="true"/></td>
                    <td align="right">兑付本金</td>
                    <td><input type="text" id="paymentMoney<%=token %>" name="production.paymentMoney" style="width:120px" readonly="true"/></td>
                    <td align="right">兑付收益</td>
                    <td><input type="text" id="paymentProfitMoney<%=token %>" name="production.paymentProfitMoney" style="width:120px" readonly="true"/></td>
                </tr>
                <tr>
                    <td align="right">总成本</td>
                    <td><input type="text" class="easyui-validatebox" id="totalCost<%=token %>" data-options="required:true,validType:'number'" invalidMessage="请输入大于零的整数" name="production.totalCost" style="width:120px"/>&nbsp;%
                    </td>
                    <td align="right">万小宝账户代码</td>
                    <td><input type="text" class="easyui-validatebox" id="allinpayCircle_ProductCodeCashAcct<%=token %>" data-options="validType:'number'" invalidMessage="请输入大于零的整数" name="production.allinpayCircle_ProductCodeCashAcct" style="width:120px"/></td>
                    <td align="right">&nbsp;</td>
                    <td>&nbsp;</td>
                    <td align="right">&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td align="right">产品概要</td>
                    <td colspan="5"><input type="text" id="productionDescription<%=token %>" class="easyui-validatebox" required="true" missingmessage="必须填写" editable="false" name="production.productionDescription" style="width:600px"/>
                    </td>
                    <td align="right">展示类型</td>
                    <td colspan="2"><input type="text" id="displayType<%=token %>2" name="production.displayType" style="width:120px"/></td>
                </tr>
                <tr>
                    <td align="right">付息方式说明</td>
                    <td colspan="5"><input type="text" id="interestPaymentDescription<%=token %>" class="easyui-validatebox" name="production.interestPaymentDescription" style="width:600px"/></td>
                    <td align="right">折标费率</td>
                    <td colspan="2" align="left" style="color: red"><input type="text" id="discountRate<%=token %>2" name="production.discountRate" style="width:120px"/></td>
                </tr>
                <tr>
                    <td align="right"></td>
                    <td colspan="5"></td>
                    <td colspan="3" align="left" style="color: red">说明：付款后第二天起息的产品，清空起息日</td>
                </tr>
            </table>
      <input type="hidden" id="operatorId<%=token %>" name="production.operatorId" />
            <input type="hidden" id="sid<%=token %>" name="production.sid" />
            <input type="hidden" id="operateTime<%=token %>" name="production.operateTime" />
            <input type="hidden" id="state<%=token %>" name="production.state"/>

            <div style="width: 100%; height:260px;" id="productionTable<%=token%>">
                <div id="contentTabs" class="easyui-tabs" fit="true" border="true" style="overflow:auto;">
                    <div title="产品构成管理" style="padding:5px ;">
                        <table id="ProductionCompositionTable<%=token%>" style=""></table>
                    </div>
                    <div title="产品信息" style="padding:5px ;">
                        <table border="0" id="ProductionInfoTable_Description<%=token%>" style="width: 550px; height:430px;margin: 0 auto;">
                            <tr>
                                <td align="right">网站显示名称</td>
                                <td colspan="3"><input type="text" class="easyui-vaildatebox" id="websiteDisplayName<%=token%>" name="production.websiteDisplayName" style="width: 300px"/></td>
                            </tr>
                            <tr>
                                <td align="right">网站显示投资期限：</td>
                                <td colspan="3"><input type="text" class="easyui-validatebox" id="InvestTermView<%=token%>" name="production.investTermView" style="width: 300px"/></td>
                            </tr>
                            <tr>
                                <td align="right">网站显示期限范围：</td>
                                <td colspan="3"><input type="text" class="easyui-validatebox" id="InvestTerm<%=token%>" name="production.investTerm" data-options="validType:'number'" invalidMessage="请输入大于等于零的整数" style="width: 300px"/>天
                                </td>
                            </tr>
                            <tr>
                                <td align="right">产品介绍一：</td>
                                <td>
                                    <a  id="btnProductionDes1<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">编辑</a>
                                </td>
                                <td align="right">产品介绍二：</td>
                                <td>
                                    <a  id="btnProductionDes2<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">编辑</a>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">产品介绍三：</td>
                                <td>
                                    <a id="btnProductionDes3<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">编辑</a>
                                </td>
                                <td align="right">产品介绍四：</td>
                                <td>
                                    <a id="btnProductionDes4<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">编辑</a>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">产品介绍五：</td>
                                <td>
                                    <a id="btnProductionDes5<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">编辑</a>
                                </td>
                                <td align="right">产品介绍六：</td>
                                <td>
                                    <a id="btnProductionDes6<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">编辑</a>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">产品介绍七：</td>
                                <td>
                                    <a  id="btnProductionDes7<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">编辑</a>
                                </td>
                                <td align="right">产品介绍八：</td>
                                <td>
                                    <a  id="btnProductionDes8<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">编辑</a>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">产品介绍九：</td>
                                <td>
                                    <a  id="btnProductionDes9<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">编辑</a>
                                </td>
                                <td align="right">产品介绍十：</td>
                                <td>
                                    <a  id="btnProductionDes10<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">编辑</a>
                                </td>
                            </tr>

                        </table>
                        <input type="hidden" id="sid<%=token %>" name="productionInfo.sid" style="width:200px"/>
                        <input type="hidden" id="id<%=token %>" name="productionInfo.id" style="width:200px"/>
                        <input type="hidden" id="state<%=token %>" name="productionInfo.state" style="width:200px"/>
                        <input type="hidden" id="productionId<%=token %>" name="productionInfo.productionId" style="width:200px"/>
                    </div>
                </div>
            </div>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnProductionSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('ProductionWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>