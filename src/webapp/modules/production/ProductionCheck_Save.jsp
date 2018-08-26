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
        <form id="formProductionCheck<%=token %>" name="formProductionCheck" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">
                <%-- 编写需要保存的表单--%>


                <tr>
                    <td align="right">产品名称</td>
                    <td colspan="5"><input type="text" id="productionId<%=token %>" required="true" missingmessage="必须填写"
                               name="productionCheck.productionName" style="width:440px"/></td>
                </tr>
                <tr>
                    <td align="right">审核内容1</td>
                    <td colspan="5"><textarea type="text" id="checker1Content<%=token %>" name="productionCheck.checker1Content"
                                  style="width:440px;height:80px;"></textarea></td>
                </tr>
                <tr>
                    <td align="right">审核人1</td>
                    <td><input type="text" id="checker1Id<%=token %>" name="productionCheck.checker3Name"
                               style="width:100px"/></td>

                    <td align="right">审核时间1</td>
                    <td><input type="text" class="easyui-datebox" editable="false" id="checker1Time<%=token %>"
                               name="productionCheck.checker1Time" style="width:100px"/></td>

                    <td align="right">审核标识1&nbsp;</td>
                    <td><select id="checker1Tag<%=token %>" name="productionCheck.checker1Tag" style="width:100px">
                        <option value="0">未通过</option>
                        <option value="1">通过</option>
                    </select>
                    </td>
                </tr>

                <tr>
                    <td align="right" >审核内容2</td>
                    <td colspan="5"><textarea type="text" id="checker2Content<%=token %>" name="productionCheck.checker2Content"
                                  style="width:440px;height:80px;">

                    </textarea></td>
                </tr>
                <tr>
                    <td align="right">审核人2</td>
                    <td><input type="text" id="checker2Id<%=token %>" name="productionCheck.checker3Name"
                               style="width:100px"/></td>

                    <td align="right">审核时间2</td>
                    <td><input type="text" class="easyui-datebox" editable="false" id="checker2Time<%=token %>"
                               name="productionCheck.checker2Time" style="width:100px"/></td>

                    <td align="right">审核标识2&nbsp;</td>
                    <td><select id="checker2Tag<%=token %>" name="productionCheck.checker2Tag" style="width:100px">
                         <option value="0" selected='selected'>未通过</option>
                        <option value="1">通过</option>
                    </select></td>
                </tr>

                <tr>
                    <td align="right" >审核内容3</td>
                    <td colspan="5"><textarea type="text" id="checker3Content<%=token %>" name="productionCheck.checker3Content"
                                  style="width:440px;height:80px;">

                    </textarea></td>
                </tr>
                <tr>
                    <td align="right">审核人3</td>
                    <td><input type="text" id="checker3Id<%=token %>" name="productionCheck.checker3Name"
                               style="width:100px"/></td>

                    <td align="right">审核时间3</td>
                    <td><input type="text" class="easyui-datebox" editable="false" id="checker3Time<%=token %>"
                               name="productionCheck.checker3Time" style="width:100px"/></td>

                    <td align="right">审核标识3&nbsp;</td>
                    <td><select name="productionCheck.checker3Tag" style="width:100px" id="checker3Tag<%=token%>">
                        <option value="0" selected='selected'>未通过</option>
                        <option value="1">通过</option>
                    </select>
                    </td>
                </tr>


            </table>
            <input type="hidden" id="sid<%=token %>" name="productionCheck.sid" style="width:100px"/>
            <input type="hidden" id="id<%=token %>" name="productionCheck.id" style="width:100px"/>
            <input type="hidden" id="operatorId<%=token %>" name="productionCheck.operatorId" style="width:100px"/>
            <input type="hidden" id="productionCheck.productionId<%=token%>" name="productionCheck.productionId" style="width:100px;"/>
             <input type="hidden" id="productionCheck.checker1Id<%=token%>" name="productionCheck.checker1Id" style="width:100px;"/>
             <input type="hidden" id="productionCheck.checker2Id<%=token%>" name="productionCheck.checker2Id" style="width:100px;"/>
             <input type="hidden" id="productionCheck.checker3Id<%=token%>" name="productionCheck.checker3Id" style="width:100px;"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnProductionCheckSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('ProductionCheckWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>