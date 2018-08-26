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
    String root=Config.getWebRoot();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Untitled Document</title>
</head>

<body>
<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
        <form id="formProductionDetail<%=token %>" name="formProductionDetail" action="" method="post">
            <table width="100%" border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">所属项目</td>
                    <td><input type="text" id="projectId<%=token %>" name="production.projectName"  class="easyui-validatebox" readonly="readonly" style="width:160px"/></td>
                    <td align="right">名称</td>
                    <td colspan="3"><input  type="text" id="name<%=token %>" class="easyui-validatebox" name="production.name" readonly="readonly" validType="maxLength[40]" style="width:400px"/></td>
                    <td align="right">配额</td>
                    <td><input class="easyui-validatebox"  type="text" id="size<%=token %>" name="production.size" readonly="readonly" style="width:130px"/></td>
                </tr>
                <tr>
                    <td align="right">开始时间</td>
                    <td><input type="text" class="easyui-datebox" id="startTime<%=token %>" name="production.startTime" readonly="readonly" style="width:160px"/></td>
                    <td align="right">结束时间</td>
                    <td><input class="easyui-datebox" type="text" id="stopTime<%=token %>" name="production.stopTime" readonly="readonly" style="width:160px"/></td>
                    <td align="right">起息日期</td>
                    <td><input type="text" id="valueDate<%=token %>"  class="easyui-datebox" name="production.valueDate" readonly="readonly" style="width:160px"/></td>
                    <td align="right">到期日期</td>
                    <td><input type="text" id="expiringDate<%=token %>"  class="easyui-datebox" name="production.expiringDate" readonly="readonly" style="width:160px"/></td>
                </tr>
                <tr>
                    <td align="right">付息日期</td>
                    <td><input type="text" id="interestDate<%=token %>"  class="easyui-datebox" readonly="readonly" name="production.interestDate" style="width:160px"/></td>
                    <td align="right">预约金额</td>
                    <td><input type="text" id="appointmentMoney<%=token %>" name="production.appointmentMoney" style="width:160px" readonly="true"/></td>
                    <td align="right">销售金额</td>
                    <td><input type="text" id="saleMoney<%=token %>" name="production.saleMoney"  style="width:160px" readonly="true"/></td>
                    <td align="right">状态</td>
                    <td><input type="text" id="status<%=token %>"  class="easyui-combotree"  data-options="url:'<%=root %>/modules/production/Production_Status.jsp',method:'get',required:true" name="production.status" readonly="readonly" style="width:160px"/></td>
                </tr>
                <tr>
                  <td align="right">产品编号</td>
                  <td><input type="text" id="<%=token %>" name="production.productId" style="width:160px" readonly="true"/></td>
                  <td align="right">兑付本金</td>
                  <td><input type="text" id="<%=token %>" name="production.paymentMoney" style="width:160px" readonly="true"/></td>
                  <td align="right">兑付收益</td>
                  <td><input type="text" id="<%=token %>" name="production.paymentProfitMoney" style="width:160px" readonly="true"/></td>
                  <td align="right">进度</td>
                  <td>预约：100% &nbsp; 销售：100% &nbsp;<br/> 兑付：100%</td>
                </tr>
            </table>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <%--<a id="btnProductionSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>--%>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('ProductionDetailWindow<%=token%>')">返回</a>
    </div>
</div>
</body>
</html>