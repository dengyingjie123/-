<%--
  Created by IntelliJ IDEA.
  User: zhouhaihong
  Date: 2015/12/24
  Time: 17:07
  To change this template use File | Settings | File Templates.
 销售合同调配资料填写页
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String token = request.getParameter("token");
%>
<html>
<head>
    <title>销售合同调寄送</title>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
        <form id="formContractRouteListPO<%=token %>" name="formContractRouteListPO" action="" method="post">
            <div id="send<%=token%>">
                <table border="0" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right">合同号</td>
                        <td>  <textarea id="contractNO<%=token%>" name="contractPO.contractNo"
                                        style="width:400px;height:100px;">

                      </textarea>
                        </td>
                    </tr>
                    <tr>
                        <td>快递公司</td>
                        <td><input class="easyui-validatebox" type="text" id="sendExpress<%=token %>"
                                   name="contractRouteListPO.sendExpress" required="true"
                                   style="width:400px"/></td>
                    </tr>
                    <tr>
                        <td align="right">快递单号</td>
                        <td colspan="3">
                            <input class="easyui-validatebox" type="text" id="sendExpressId<%=token %>"
                                   name="contractRouteListPO.sendExpressId" style="width:400px" required="true"/>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="receive<%=token%>">
                <table border="0" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right">合同号</td>
                        <td>  <textarea id="contractNO<%=token%>" name="contractPO.contractNo"
                                        style="width:400px;height:100px;">
                      </textarea>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="updateDepartment<%=token%>">
                <table border="0" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right">合同号</td>
                        <td>
                            <textarea id="contractNO<%=token%>" name="contractPO.contractNo"
                                        style="width:400px;height:100px;">
                            </textarea>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            财富中心
                        </td>
                        <td>
                            <input type="text" class="eastui-combotree" name="contractRouteListPO.departmentId" id="departmentId<%=token%>"  style="width:400px" />
                            <input type="hidden" name="contractRouteListPO.departmentName" id="departmentName<%=token%>"  style="width:400px" />
                        </td>
                    </tr>
                </table>
            </div>


            <div id="updateRouteListState<%=token%>">
                <table border="0" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right">合同号</td>
                        <td>
                            <textarea id="contractNO<%=token%>" name="contractPO.contractNo"
                                      style="width:400px;height:100px;">
                            </textarea>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            流转状态
                        </td>
                        <td>
                            <input type="text" class="eastui-combotree" name="routeListStatus" id="routeListStatus<%=token%>"  style="width:400px" />
                        </td>
                    </tr>
                </table>
            </div>

        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnContractRouteListSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('ContractRouteListWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
