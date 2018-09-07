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
        <form id="formAdImage<%=token %>" name="formAdImage" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">

                <tr>
                    <td align="right">归类编号</td>
                    <td><input class="easyui-combobox" editable="false" type="text" id="catalogId<%=token %>"
                               name="adImage.catalogId" required="true" missingmessage="必须填写" style="width:380px"/></td>
                </tr>


                <tr>
                    <td align="right">名称</td>
                    <td><input class="easyui-validatebox" type="text" id="name<%=token %>" name="adImage.name"
                               required="true" missingmessage="必须填写" style="width:380px"/></td>
                </tr>
                <tr>
                    <td align="right">URL</td>
                    <td><input class="easyui-validatebox" type="text" id="url<%=token %>" name="adImage.url"
                               required="true" style="width:380px"/></td>
                </tr>


                <tr>
                    <td align="right">大小</td>
                    <td><input type="text" id="size<%=token %>" class="easyui-validatebox" name="adImage.size"
                               data-options="required:true,validType:'number'" invalidMessage="请输入整数"
                               style="width:380px"/></td>
                </tr>


                <tr>
                    <td align="right">宽</td>
                    <td><input type="text" id="width<%=token %>" name="adImage.width" class="easyui-validatebox"
                               data-options="required:true,validType:'number'" invalidMessage="请输入整数"
                               style="width:380px"/></td>
                </tr>


                <tr>
                    <td align="right">高</td>
                    <td><input type="text" id="height<%=token %>" name="adImage.height" class="easyui-validatebox"
                               data-options="required:true,validType:'number'" invalidMessage="请输入整数"
                               style="width:380px"/></td>
                </tr>


                <tr>
                    <td align="right">响应地址</td>
                    <td><input type="text" class="easyui-validatebox" id="responseURL<%=token %>"
                               name="adImage.responseURL"
                               style="width:380px"/></td>
                </tr>


                <tr>
                    <td align="right">排序</td>
                    <td><input type="text" id="orders<%=token %>" name="adImage.orders" class="easyui-validatebox"
                               data-options="required:true,validType:'number'" missingmessage="必须填写"
                               style="width:380px"/></td>
                </tr>


                <tr>
                    <td align="right">是否使用</td>
                    <td><input type="text" class="easyui-combotree" id="isAvaliable<%=token %>"
                               name="adImage.isAvaliable" required="true" missingmessage="必须填写" style="width:380px"/>
                    </td>
                </tr>

                <tr>
                    <td align="right">启用时间</td>
                    <td><input type="text" class="easyui-datetimebox" editable="false" id="startTime<%=token %>"
                               name="adImage.startTime" required="true" missingmessage="必须填写" style="width:380px"/></td>
                </tr>
                <tr>
                    <td align="right">停用时间</td>
                    <td><input type="text" class="easyui-datetimebox" editable="false" id="endTime<%=token %>"
                               name="adImage.endTime" required="true" missingmessage="必须填写" style="width:380px"/></td>
                </tr>
                <tr>
                    <td align="right">描述</td>
                    <td>
                        <textarea type="text" id="description<%=token %>" name="adImage.description"
                                  style="width:380px;height:100px;"></textarea>
                    </td>
                </tr>
            </table>
            <input type="hidden" id="sid<%=token %>" name="adImage.sid" style="width:200px"/>
            <input type="hidden" id="id<%=token %>" name="adImage.id" style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnAdImageSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('AdImageWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>