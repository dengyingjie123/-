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
        <form id="formContract<%=token %>" name="formContract" action="" method="post" enctype="multipart/form-data">
            <table width="100%" border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">名称</td>
                    <td><input type="text" id="name<%=token %>" class="easyui-validatebox" name="contract.name"
                               required="true" missingmessage="必须填写" style="width:200px"/></td>
                    <td align="right">状态编号</td>
                    <td><input class="easyui-combotree" editable="false" type="text" id="statusId<%=token %>"
                               name="contract.statusId" required="true" missingmessage="必须填写" style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">签订日期</td>
                    <td><input class="easyui-datebox" editable="false" type="text" id="signDate<%=token %>"
                               name="contract.signDate" style="width:200px"/></td>
                    <td align="right">订单号</td>
                    <td><input class="easyui-validatebox" readonly="true" type="text" id="orderNum<%=token %>"
                               name="oc.orderNum" editable="false" style="width:195px"/></td>
                    <td><img src="<%=Config.getWebRoot() %>/include/framework/themes/icons/search.png"
                             id="btnCheckOrder<%=token %>" href="javascript:void(0)"/></td>
                </tr>
                <tr>
                    <td align="right">合同号</td>
                    <td><input class="easyui-validatebox" type="text" id="Num<%=token %>" name="contract.Num"
                               style="width:195px"/></td>

                    <td align="right">说明</td>
                    <td><input type="text" id="description<%=token %>" name="contract.description" style="width:200px"/>
                    </td>

                </tr>


            </table>
            <input type="hidden" id="operatorId<%=token %>" name="contract.operatorId" style="width:200px"/>
            <input type="hidden" id="sid<%=token %>" name="contract.sid" style="width:200px"/>
            <input type="hidden" id="id<%=token %>" name="contract.id" style="width:200px"/>
            <input type="hidden" id="operateTime<%=token %>" name="contract.operateTime" style="width:200px"/>

        </form>
            <table>
                <tr>
                    <td>上传文件：</td>
                    <td></td>
                </tr>
                <tr>
                    <td align="right">添加合同附件</td>
                    <td><a id="btnUpload<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
                           href="javascript:void(0)">上传</a></td>
                </tr>
            </table>

    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnContractSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('ContractWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>