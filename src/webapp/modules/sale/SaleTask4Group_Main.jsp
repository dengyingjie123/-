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
<html>
<head>
    <title></title>
</head>
<body>
<div style='padding:5px;'>
    <div class='easyui-panel' title='查询' iconCls='icon-search'>
        <table border='0' cellpadding='3' cellspacing='0'>
            <tr>
                <td>产品名称</td>
                <td>
                    <input type="text" class="easyui-comtreebox" name="saleTask4GroupVO.productionName"
                           id="search_productionName<%=token%>" style="width:150px;"/>
                </td>
                <td>销售组名称</td>
                <td>
                    <input type="text" class="easyui-comtreebox" name="saleTask4GroupVO.saleGroupName"
                           id="search_saleGroupName<%=token%>" style="width:150px;"/>
                </td>
                <td>分配金额</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="saleTask4Group.taskMoney"
                           id="search_TaskMoney<%=token%>" style="width:150px;"/>
                </td>
                <td>待售金额</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="saleTask4Group.waitingMoney"
                           id="search_WaitingMoney<%=token%>" style="width:150px;"/>
                </td>
            </tr>
            <tr>
                <td>预约金额</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="saleTask4Group.appointmengMoney"
                           id="search_AppointmengMoney<%=token%>" style="width:150px;"/>
                </td>
                <td>打款金额</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="saleTask4Group.soldMoney"
                           id="search_SoldMoney<%=token%>" style="width:150px;"/>
                </td>
                <td>累计取消金额</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="saleTask4Group.totoalCancelMoney"
                           id="search_TotoalCancelMoney<%=token%>" style="width:150px;"/>
                </td>
            </tr>
            <tr>
                <td>
                    开始开始日期
                </td>
                <td>
                    <input type="text" class="easyui-datebox" name="search_StartTime_Start"
                           id="search_StartTime_Start<%=token%>" editable="false" style="width:150px;"/>

                </td>
                <td>
                    结束开始日期
                </td>
                <td>
                    <input type="text" class="easyui-datebox" name="search_StartTime_End"
                           id="search_StartTime_End<%=token%>" editable="false" style="width:150px;"/>
                </td>
                <td>
                    开始结束日期
                </td>
                <td>
                    <input type="text" class="easyui-datebox" name="search_EndTime_Start"
                           id="search_EndTime_Start<%=token%>" editable="false" style="width:150px;"/>

                </td>
                <td>
                    结束结束日期
                </td>
                <td>
                    <input type="text" class="easyui-datebox" name="search_EndTime_End"
                           id="search_EndTime_End<%=token%>" editable="false" style="width:150px;"/>
                </td>

            </tr>
            <td>
                <a id='btnSaleTask4GroupSearchSubmit<%=token %>' href='javascript:void(0)' class='easyui-linkbutton'
                   iconCls='icon-search'>查询</a>
            </td>
            <td>
                <a id='btnSaleTask4GroupSearchReset<%=token %>' href='javascript:void(0)' class='easyui-linkbutton'
                   iconCls='icon-cut'>重置</a>
            </td>
            </tr>
        </table>
    </div>
    <br>
    <table id='saleTask4GroupTable<%=token%>'></table>
</div>
</body>
</html>
