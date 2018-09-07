<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.youngbook.common.config.Config" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">  
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>德合在线</title>
    <link rel="stylesheet" type="text/css" href="<%=Config.getModernFrameworkRoot()%>/themes/gray/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getModernFrameworkRoot()%>/themes/mobile.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getModernFrameworkRoot()%>/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getModernFrameworkRoot()%>/themes/color.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot()%>/modern/css/public.css">
    <script type="text/javascript" src="<%=Config.getModernFrameworkRoot()%>/jquery.min.js"></script>
    <script type="text/javascript" src="<%=Config.getModernFrameworkRoot()%>/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=Config.getModernFrameworkRoot()%>/jquery.easyui.mobile.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/include/extensions/framework-modern.js"></script>
    <script type="text/javascript">
        function register() {
            fm.goto('../production/home.jsp');
        }

        function login() {
            fm.goto('login.jsp');
        }
    </script>
</head>
<body>
    <div class="easyui-navpanel">
        <div class="rgt-hd">
            <img alt="logo" src="<%=Config.getWebRoot()%>/modern/images/logo.png">
        </div>
        <div>&nbsp;</div>
        <div style="padding:0 20px">
            <div style="margin-bottom:10px">
                <input class="easyui-textbox" data-options="prompt:'手机号（必填）',iconCls:'icon-man'" style="width:100%;height:38px">
            </div>
            <div style="margin-bottom:10px">
                <input class="easyui-passwordbox" data-options="prompt:'密码（必填）'" style="width:100%;height:38px">
            </div>
            <div style="margin-bottom:10px">
                <input class="easyui-textbox" data-options="prompt:'姓名',iconCls:'icon-man'" style="width:100%;height:38px">
            </div>
            <div style="margin-bottom:10px">
                <input class="easyui-textbox" data-options="prompt:'机构',iconCls:'icon-man'" style="width:100%;height:38px">
            </div>
            <div style="text-align:center;margin-top:30px">
                <a href="#" class="easyui-linkbutton c1" style="width:60%;height:35px"><span style="font-size:16px" onclick="register();">提交</span></a>
                <a href="#" class="easyui-linkbutton c5" plain="true" outline="true" style="width:35%;height:35px" onclick="login();"><span style="font-size:16px">已有账号？</span></a>
            </div>
        </div>
    </div>
    <div id="modern-common-area"></div>
    <div id="ddd"></div>
    <input type="file" accept="image/*;capture=camera">
</body>    
</html>