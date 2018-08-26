<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token");
    String idChange = request.getParameter("idChange");
    String idChange2 = request.getParameter("idChange2");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

</head>
<html>
<head>
</head>
<body class="easyui-layout">
<div  class="easyui-layout" fit="true" data-options="region:'north'">
    <div id="updateTabs<%=token%>" class="easyui-tabs" style="width:585px;height:250px;">
        <div id="salesmanPassword<%=token%>" title="销售属性" style="padding:20px;">
            <form id="baseInfoEidt<%=token%>"  action="" method="post">
                <table border="0">
                    <%--<tr>--%>
                        <%--<td align="right">名字：</td><td><input class="easyui-validatebox" disabled="disabled" type="text" id="name<%=token %>" name="salesmanVO.userName" style="width:190px"/></td>--%>
                        <%--<td align="right">性别：</td><td><input class="easyui-combotree" disabled="disabled" type="text" editable="false" id="gender<%=token %>" name="salesmanVO.gender" style="width:200px"/></td>--%>
                    <%--</tr>--%>
                    <tr>
                        <td align="right">销售级别：</td>
                        <td><input class="easyui-combotree" editable="false" type="text" id="Sales_level<%=token %>"
                                   name="salesman.sales_levelId" style="width:200px"/></td>
                    </tr>
                    <%--<tr>--%>
                        <%--<td align="right">电话</td><td><input class="easyui-validatebox" validType="mobile" type="text" id="mobile<%=token %>" name="user.mobile" required="true" invalidMessage="请输入11位手机号"  style="width:190px"/></td>--%>
                        <%--<td align="right">公司职位</td><td><input class="easyui-validatebox" type="text" id="position<%=token %>" name="user.position"  style="width:200px"/></td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td align="right">邮箱</td><td><input class="easyui-validatebox" validType="email" type="text" id="email<%=token %>" name="user.email"  style="width:190px"/></td>--%>
                        <%--<td align="right">身份号码</td><td><input class="easyui-validatebox" validType="idcard" type="text" id="idnumber<%=token %>" name="user.idnumber" style="width:200px"/></td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td align="right">生日</td><td><input class="easyui-datebox" editable="false" type="text" id="birthday<%=token %>" name="user.birthday" style="width:190px"/></td>--%>
                        <%--<td align="right">入职日期</td><td><input class="easyui-datebox" editable="false" type="text" id="jointime<%=token %>" name="user.jointime" required="true" missingmessage="必须填写"  style="width:200px"/></td>--%>
                    <%--</tr>--%>
                    <%--<tr >--%>
                        <%--<td align="right">地址</td><td colspan="3"><input class="easyui-validatebox" type="text" id="address<%=token %>" name="user.address"   style="width:460px"/></td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td align="right">公司</td><td><input class="easyui-combotree" type="text" id="companyCode<%=token %>" name="salesmanPO.companyCode" required="true" missingmessage="必须填写" style="width:200px"/></td>--%>
                        <%--<td align="right">地区</td><td><input class="easyui-combotree" type="text" id="regionCode<%=token %>" name="salesmanPO.regionCode" required="true" missingmessage="必须填写" style="width:200px"/></td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td align="right">编号</td><td><input class="easyui-validatebox" type="text" id="numberCode<%=token %>" name="salesmanPO.numberCode" readonly="readonly" style="width:200px"/></td>--%>
                        <%--&lt;%&ndash;<td align="right">工号</td><td><input type="text" id="<%=token %>" name="" style="width:200px"/></td>&ndash;%&gt;--%>
                    <%--</tr>--%>
                </table>
                <input name="salesman.id" type="hidden" id="id<%=token %>" readonly="readonly" style="width:200px"/>
                <input name="salesman.sid" type="hidden" id="sid<%=token %>" readonly="readonly" style="width:200px"/>
                <input name="salesman.operatorId" type="hidden" id="operatorId<%=token %>" readonly="readonly" style="width:200px"/>
                <input name="salesman.operateTime" type="hidden" id="operateTime<%=token %>" readonly="readonly" style="width:200px"/>
                <input name="salesman.state" type="hidden" id="state<%=token %>" readonly="readonly" style="width:200px"/>
                <input name="salesman.userId" type="hidden" id="userId<%=token %>" readonly="readonly" style="width:200px"/>
            </form>
        </div>
        <%--<div id="salesmanPassword<%=token%>" title="密码信息" style="overflow:auto;padding:20px;">--%>
            <%--<form id="userUpdatePwd<%=token%>"  action="" method="post">--%>
                <%--<input name="user.id" type="hidden" id="uid<%=token %>" readonly="readonly" style="width:200px"/>--%>
                <%--<input name="user.sid" type="hidden" id="usid<%=token %>" readonly="readonly" style="width:200px"/>--%>
                <%--<input name="user.name" type="hidden" id="uname<%=token %>" readonly="readonly" style="width:200px"/>--%>
                <%--<input name="user.gender" type="hidden" id="ugender<%=token %>" readonly="readonly" style="width:200px"/>--%>
                <%--<input name="user.mobile" type="hidden" id="umobile<%=token %>" readonly="readonly" style="width:200px"/>--%>
                <%--<input name="user.email" type="hidden" id="uemail<%=token %>" readonly="readonly" style="width:200px"/>--%>
                <%--<input name="user.address" type="hidden" id="uaddress<%=token %>" readonly="readonly" style="width:200px"/>--%>
                <%--<input name="user.birthday" type="hidden" id="ubirthday<%=token %>" readonly="readonly" style="width:200px"/>--%>
                <%--<input name="user.jointime" type="hidden" id="ujointime<%=token %>" readonly="readonly" style="width:200px"/>--%>
                <%--<input name="user.idnumber" type="hidden" id="uidnumber<%=token %>" readonly="readonly" style="width:200px"/>--%>
                <%--<input name="user.operatorId" type="hidden" id="uoperatorId<%=token %>" readonly="readonly" style="width:200px"/>--%>
                <%--<input name="user.operatorId" type="hidden" id="uoperatorId<%=token %>" readonly="readonly" style="width:200px"/>--%>
                <%--<input name="user.operateTime" type="hidden" id="uoperateTime<%=token %>" readonly="readonly" style="width:200px"/>--%>
                <%--<input name="user.position" type="hidden" id="uposition<%=token %>" readonly="readonly" style="width:200px"/>--%>
                <%--<table>--%>
                    <%--<tr>--%>
                        <%--<td align="right">密码</td>--%>
                        <%--<td><input class="easyui-validatebox" type="password" id="password<%=token %>" name="user.password"  validType="equalTo['password2<%=token %>']"  style="width:200px"/></td>--%>
                    <%--</tr>--%>
                    <%--<!--validType="equalTo['password2<%=token %>']"-->--%>
                    <%--<tr>--%>
                        <%--<td align="right">重复密码</td>--%>
                        <%--<td><input class="easyui-validatebox" type="password" id="password2<%=token %>" validType="equalTo['password<%=token %>']"  style="width:200px"/></td>--%>
                    <%--</tr>--%>
                <%--</table>--%>
            <%--</form>--%>
        <%--</div>--%>
        <%--<div id="salesmanLevel<%=token%>"  title="销售人员属性"  style="padding:20px ;" >--%>
            <%--<form id="salesmanLevelEdit<%=token%>"  action="" method="post">--%>
                <%--<table>--%>
                    <%--<tr>--%>
                        <%--<td align="right">销售级别</td>--%>
                        <%--<td><input class="easyui-combotree" editable="false" type="text" id="sSales_level<%=token %>"--%>
                                   <%--name="salesmanPO.sales_levelId" style="width:200px"/></td>--%>
                    <%--</tr>--%>
                <%--</table>--%>
                <%--<input name="salesmanPO.id" type="hidden" id="sId<%=token %>" readonly="readonly" style="width:200px"/>--%>
                <%--<input name="salesmanPO.sid" type="hidden" id="sSid<%=token %>" readonly="readonly" style="width:200px"/>--%>
                <%--<input name="salesmanPO.state" type="hidden" id="sState<%=token %>" readonly="readonly" style="width:200px"/>--%>
                <%--<input name="salesmanPO.operatorId" type="hidden" id="sOperatorId<%=token %>" readonly="readonly" style="width:200px"/>--%>
                <%--<input name="salesmanPO.operateTime" type="hidden" id="sOperateTime<%=token %>" readonly="readonly" style="width:200px"/>--%>
                <%--<input name="salesmanPO.userId" type="hidden" id="sUserId<%=token %>"  style="width:200px"/>--%>
            <%--</form>--%>
        <%--</div>--%>
    </div>
    <%--
     <script type="text/javascript">
             alert(idChange);
             alert(idChange2);
         </script>
     --%>

    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="salesmanSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('<%=idChange2 + token %>')">取消</a>
    </div>
</div>
</body>
</html>