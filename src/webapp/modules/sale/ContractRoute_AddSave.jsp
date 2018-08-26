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
        <form id="formAddContractRoute<%=token %>" name="formAddContractRoute" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">产品</td>
                    <td colspan="3"><input type="text" name="production.name" rows="2" class="easyui-validatebox"
                                              id="production<%=token %>" style="width:200px;"
                                              required="true" missingmessage="必须填写" readonly="readonly"/></td>
                    <td><a href="javascript:void(0)" id="btnProduction<%=token %>" class="easyui-linkbutton"
                           plain="true" iconCls="icon-search"></a></td>
                </tr>
                <tr>
                    <td align="right">合同号</td>
                    <td colspan="3"><input  type="text" name="contractRoute.contractNo" rows="2" class="easyui-validatebox"
                                              id="contractNo<%=token %>" style="width:200px;"
                                              required="true" missingmessage="必须填写"/></td>
                </tr>
                <tr>
                    <td align="right">第几本</td>
                    <td colspan="3"><input  type="text" name="contractRoute.numberId" rows="2" class="easyui-validatebox"
                                              id="numberId<%=token %>" style="width:200px;"
                                              required="true" missingmessage="必须填写"/></td>
                </tr>
            </table>

            <input  type="hidden"  id="productionId<%=token %>" name="contractRoute.productionId" readonly="true"   style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnAddContractSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('AddContractWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>