<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 15-1-19
  Time: 下午5:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" import="com.youngbook.common.config.*" language="java" %>
<html>
<head>
    <title></title>
</head>
<body><!--class="easyui-layout" fit="true"-->

<!--header-->
<div id="callcentermain_title" region="north" split="false" style="height:50px;padding:2px;vertical-align:text-top;background-image: url('/include/images/headerbg.jpg')">
    <div id="callcenter_main_header">
        <table width="100%">
            <tr>
                <td><img src="/include/images/system_name.png" /></td>
                <td align="right">
                    <table>
                        <tr>
                        	<td>
                                <a id="callcentermain_incomingcallsregister"><img src="/include/images/callcenter/incomingCallsRegister_48.png"></a>
                            </td>
                            <td>当前用户：</td>
                            <td>
                                <a href="javascript:void(0)" id="btnLoginUser" class="easyui-menubutton" data-options="menu:'#mm'">正在加载</a>
                                <div id="mm" style="width:100px;">
                                    <div id="btnLoginUserLogout" data-options="iconCls:'icon-ok'" onClick="onClickLogoutUser()">注销</div>
                                </div>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>
</div>

<div>
<!--header-->
    <div class="easyui-panel">

    </div>
    <div class="easyui-panel">
        <table>
        </table>
    </div>
</div>

<style type="text/css" >
    .controlpanel_td
    {
        height:60px;
        width:120px;
        padding:2px;
    }
    .controlpanel_td
    {
        height:55px;
        width:100px;
    }
    .menu_button
    {
        width:135px;
        hegith:40px;
        margin:1px 2px 1px 2px;
        text-align:center;
    }
    .numberkeyboard_td
    {
        width:85px;
        height:40px;
        padding:1px;
        border:1px solid silver;
        text-align:center;
    }
    .numberkeyboard_button
    {
        width:80px;
        height:40px;
        font-size: 15pt;
    }
</style>

</body>
</html>