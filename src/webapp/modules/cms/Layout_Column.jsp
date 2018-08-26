<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token");
%>
<html>
<head>
    <title></title>
</head>
<body>
<div style="padding:10px; ">
    <table border="0" cellspacing="5" cellpadding="0">
        <tr>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td valign="top">
                <div id="columnInformationPanel" class="easyui-tabs" style="width:220px;height:535px">
                    <div id="columnInformation<%=token%>" class="leftPanel" title="栏目信息" style="padding:2px;">
                        <div id="leftColumn" class="easyui-panel" style="width:216;height:485px">
                            <input  class="easyui-combotree" id="departmentTree<%=token %>" editable="false"
                                    style="padding-top:20px;width:205px"/><br>
                            <br/>
                            <!-- <ul class="left" id="columnOption<%=token%>" style="border:solid 1px #E0ECFF;width:99%;height: 410px " class="easyui-tree">
                            </ul>-->
                            <div class="left">
                                <table id="columnListTable<%=token%>">
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </td>
            <td valign="top">
                <div id="layoutPanel" class="easyui-tabs" style="width:700px;height:535px">
                    <div id="layout<%=token%>" class="layout"  title="布局" style="padding:2px;">
                        <div class="right">
                            <table>
                                <tr>
                                    <td class="drop" style="width: 26%"></td>
                                    <td class="drop" style="width: 48%"></td>
                                    <td class="drop" style="width: 26%"></td>
                                </tr>
                                <tr>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                </tr>
                                <tr>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                </tr>
                                <tr>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                </tr>
                                <tr>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                </tr>
                                <tr>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                </tr>
                                <tr>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                </tr>
                                <tr>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                </tr>
                                <tr>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                </tr>
                                <tr>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                </tr>
                                <tr>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                </tr>
                                <tr>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                </tr>
                                <tr>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                </tr>
                                <tr>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                </tr>
                                <tr>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                </tr>
                                <tr>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                </tr>
                                <tr>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                </tr>
                                <tr>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                </tr>
                                <tr>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                </tr>
                                <tr>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                    <td class="drop" ></td>
                                </tr>
                            </table>
                        </div>
                        <div region="south" border="false" style="text-align:right;padding:1px 0;">
                            <a id="saveLayout<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >保存</a>
                            <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="">取消</a>
                        </div>
                    </div>
                </div>
            </td>
        </tr>
    </table>
</div>
<style type="text/css">

    .left table{
        background:#E0ECFF;
    }
    .left td{
        background:#eee;
    }

    .right table{
        width:690px;height:405px;
        background:#E0ECFF;
    }
    .right td{
        background:#fafafa;
        color:#444;
        text-align:center;
        padding:2px;
    }
    .right td{
        height:18px;
        background:#E0ECFF;
    }
    .right td.drop{
        background:#fafafa;
    }
    .right td.over{
        background:#FBEC88;
    }
    .item{
        text-align:center;
        border:1px solid #499B33;
        background:#fafafa;
        color:#444;
        width:100px;
    }
    .assigned{
        position: static;
        left: 23px;
        top: 20px;
        background:#fafafa;
    }
    .trash{
        background-color:red;
    }

</style>
</body>
</html>