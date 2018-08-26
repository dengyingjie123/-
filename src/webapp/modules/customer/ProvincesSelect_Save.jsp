<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <title></title>
</head>

<body>
<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
        <form id="formProvincesSelectClass<%=token %>" name="formContract" action="" method="post">
            <table width="100%" border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">
                        省份（直辖市）:
                        </td>
                    <td>
                        <select id="Province<%=token%>" style="width:150px;">
                        <option selected="selected" value="0">-请选择-</option>
                        <option value="1">北京</option>
                        <option value="2">上海</option>
                        <option value="3">天津</option>
                        <option value="4">重庆</option>
                        <option value="5">河北</option>
                        <option value="6">山西</option>
                        <option value="7">内蒙古</option>
                        <option value="8">辽宁</option>
                        <option value="9">吉林</option>
                        <option value="10">黑龙江</option>
                        <option value="11">江苏</option>
                        <option value="12">浙江</option>
                        <option value="13">安徽</option>
                        <option value="14">福建</option>
                        <option value="15">江西</option>
                        <option value="16">山东</option>
                        <option value="17">河南</option>
                        <option value="18">湖北</option>
                        <option value="19">湖南</option>
                        <option value="20">广东</option>
                        <option value="21">广西</option>
                        <option value="22">海南</option>
                        <option value="23">四川</option>
                        <option value="24">贵州</option>
                        <option value="25">云南</option>
                        <option value="26">西藏</option>
                        <option value="27">陕西</option>
                        <option value="28">甘肃</option>
                        <option value="29">宁夏</option>
                        <option value="30">青海</option>
                        <option value="31">新疆</option>
                        <option value="32">香港</option>
                        <option value="33">澳门</option>
                        <option value="34">台湾</option>
                    </select>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        城市(区):
                        </td><td>
                    <select id="City<%=token%>" style="width:150px;">
                        <option value='-1' selected="selected">-请选择-</option>
                    </select>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnProvincesSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('ProvincesSelectWindow<%=token%>')">取消</a>
    </div>
</div>

</body>
</html>
