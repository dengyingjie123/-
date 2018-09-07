<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token");
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
        <form id="formTravel<%=token %>" name="formTravel" action="" method="post">
            <table width="100%" border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">金额规模</td>
                    <td><input class="easyui-validatebox"  editable="true" type="text" id="bxgm<%=token %>" name="travelExpensePO.bxgm"  required="true" missingmessage="必须填写"  style="width:200px"/></td>

                    <td align="right">经办人</td>
                    <td><input class="easyui-validatebox" type="text" id="jbr<%=token %>" name="travelExpensePO.jbr"  required="true" missingmessage="必须填写"  style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">预算部门</td>
                    <td><input class="easyui-validatebox"  editable="true" type="text" id="ysbm<%=token %>" name="travelExpensePO.ysbm"  required="true" missingmessage="必须填写"  style="width:200px"/></td>

                    <td align="right">出差事由</td>
                    <td><input class="easyui-validatebox" type="text" id="ccsy<%=token %>" name="travelExpensePO.ccsy"  required="true" missingmessage="必须填写"  style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">起始时间</td>
                    <td><input class="easyui-datetimebox"  editable="true" type="text" id="qssj<%=token %>" name="travelExpenseDetailPO.qssj"  required="true" missingmessage="必须填写"  style="width:200px"/></td>

                    <td align="right">出发地点</td>
                    <td><input class="easyui-validatebox" type="text" id="cfdd<%=token %>" name="travelExpenseDetailPO.cfdd"  required="true" missingmessage="必须填写"  style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">结束时间</td>
                    <td><input class="easyui-datetimebox"  editable="true" type="text" id="jssj<%=token %>" name="travelExpenseDetailPO.jssj"  required="true" missingmessage="必须填写"  style="width:200px"/></td>

                    <td align="right">结束地点</td>
                    <td><input class="easyui-validatebox" type="text" id="jsdd<%=token %>" name="travelExpenseDetailPO.jsdd"  required="true" missingmessage="必须填写"  style="width:200px"/></td>
                </tr>



                <tr>
                    <td align="right">飞机票费</td>
                    <td><input class="easyui-validatebox"  editable="true" type="text" id="jpf<%=token %>" name="travelExpenseDetailPO.jpf"  required="true" missingmessage="必须填写"  style="width:200px"/></td>

                    <td align="right">机票补助</td>
                    <td><input class="easyui-validatebox" type="text" id="jpbz<%=token %>" name="travelExpenseDetailPO.jpbz"  required="true" missingmessage="必须填写"  style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">车船票费</td>
                    <td><input class="easyui-validatebox"  editable="true" type="text" id="ccpf<%=token %>" name="travelExpenseDetailPO.ccpf"  required="true" missingmessage="必须填写"  style="width:200px"/></td>

                    <td align="right">住宿费</td>
                    <td><input class="easyui-validatebox" type="text" id="zsf<%=token %>" name="travelExpenseDetailPO.zsf"  required="true" missingmessage="必须填写"  style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">途中补贴人数</td>
                    <td><input class="easyui-validatebox"  editable="true" type="text" id="tzbtrs<%=token %>" name="travelExpenseDetailPO.tzbtrs"  required="true" missingmessage="必须填写"  style="width:200px"/></td>

                    <td align="right">途中补贴天数</td>
                    <td><input class="easyui-validatebox" type="text" id="tzbtts<%=token %>" name="travelExpenseDetailPO.tzbtts"  required="true" missingmessage="必须填写"  style="width:200px"/></td>
                </tr>





            </table>

        </form>
    </div>







    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnTravelSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('TravelExpenseWindow<%=token%>')">取消</a>

    </div>
</div>
</body>
</html>