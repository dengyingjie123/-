<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ButtonPO" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ToolbarPO" %>
<%
    /**
     * 系统管理
     */
    String token = request.getParameter("token");
%><%
    // 创建需要权限控制的按钮
    ButtonPO btnAdd = new ButtonPO("btnCodeAdd" + token, "添加", "icon-add", "系统管理-验证码-新增");
    ButtonPO btnEdit = new ButtonPO("btnCodeEdit" + token, "修改", "icon-edit","系统管理-验证码-修改");
    ButtonPO btnDel = new ButtonPO("btnCodeDelete" + token, "删除", "icon-cut","系统管理-验证码-删除");
    // 创建Toolbar，并受权限控制
    ToolbarPO toolbar = ToolbarPO.getInstance(request);
    // 直接添加按钮，后台程序会自动判断，有权限则添加，没有权限则不添加
    toolbar.addButton(btnAdd);
    toolbar.addButton(btnEdit);
    toolbar.addButton(btnDel);
%>

<html>
<head>
    <title></title>
</head>
<body>
<div style="padding:5px;">

    <div class="easyui-panel" title="查询" iconCls="icon-search">
        <table border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td>有效时间（开始时间）</td>
                <td><input type="text" class="easyui-datebox" id="search_AvailableTime_Start<%=token %>"
                           style="width:100px;" editable="false"/></td>
                <td>有效时间（结束时间）</td>
                <td><input type="text" class="easyui-datebox" id="search_AvailableTime_End<%=token %>"
                           style="width:100px;" editable="false"/></td>

                <td>使用时间（开始时间）</td>
                <td><input type="text" class="easyui-datebox" id="search_UsedTime_Start<%=token %>" style="width:100px;"
                           editable="false"/></td>
                <td>使用时间（结束时间）</td>
                <td><input type="text" class="easyui-datebox" id="search_UsedTime_End<%=token %>" style="width:100px;"
                           editable="false"/></td>
            </tr>
            <tr>
                <td>失效时间（开始时间）</td>
                <td><input type="text" class="easyui-datebox" id="search_ExpiredTime_Start<%=token %>"
                           style="width:100px;" editable="false"/></td>
                <td>失效时间（结束时间）</td>
                <td><input type="text" class="easyui-datebox" id="search_ExpiredTime_End<%=token %>" style="width:100px;"
                           editable="false"/></td>

                <td  align="right">使用用户名称</td>
                <td><input type="text" id="search_userName<%=token %>" name="code.UserName" style="width:100px;"/></td>
                <td  align="right">使用者IP</td>
                <td><input  type="text" id="search_IP<%=token %>" name="code.IP" style="width:100px;"/></td>

            </tr>
            <tr>
                <td align="right">
                    类型
                </td>
                <td>
                    <select  id="search_Type<%=token%>">
                        <option></option>
                        <option value="0">类型一</option>
                        <option value="1">类型二</option>
                    </select>
                </td>
                <td>
                    <a id="btnCodeSearchSubmit<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnCodeSearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="CodeTable<%=token%>" data-options="toolbar:toolbar"></table>
    <script type="text/javascript">
        var toolbar = <%=toolbar.toJsonObject().getJSONArray("buttons").toString()%>
    </script>
</div>
</body>
</html>