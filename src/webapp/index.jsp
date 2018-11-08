<%@ page contentType="text/html;charset=UTF-8" import="com.youngbook.common.config.*"
         language="java" %>
<%
    String isUpdatePassword = (String) request.getAttribute("isUpdatePassword");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><%=Config.APP_NAME%></title>
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/framework/themes/gray/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/framework/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/style/default.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/style/buttons.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/framework/fullcalendar-2.2.2/fullcalendar.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/framework/fullcalendar-2.2.2/fullcalendar.print.css" media='print'>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/jquery.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/JSON-to-Table-1.0.0.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/third-party/accounting.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/easyloader.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/scripts/system/ConfigClass.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/scripts/boot.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/hashMap.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/frameworkplus.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/treeplus.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/md5.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/functions.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/validator.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/highcharts4/js/highcharts.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/fullcalendar-2.2.2/lib/moment.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/fullcalendar-2.2.2/fullcalendar.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/fullcalendar-2.2.2/lang/zh-cn.js"></script>

    <script type="text/javascript">
        var hmMenu = null;
        $(document).ready(function() {
            //alert('Hello');
            hmMenu = new Map();
            initSystem();

            /**
             * 检测浏览器
             */
            var thisBroswer = isBroswer();

            console.log(thisBroswer);

            if (thisBroswer['name'] != 'chrome' && thisBroswer['name'] != 'mozilla') {
                fw.alert('提示', '推荐使用chrome内核浏览器');
            }
        });
    </script>
</head>
<body class="easyui-layout">

<!-- header area begin -->
<div region="north" split="false" style="height:52px;padding:2px;vertical-align:text-top;background-image: url('include/images/headerbg.jpg')">
    <div id="header">
        <table width="100%">
            <tr>
                <td><img src="include/images/system_name.png" width="150" height="40" /></td>
                <td align="right">
                    <table>
                        <tr>
                        	<td>部门：</td>
                        	<td><a href="javascript:void(0)" id="btnLoginDepartment" class="easyui-menubutton" data-options="menu:'#loginDepartmentMenu'">正在加载</a><div id="loginDepartmentMenu" style="width:300px;"></div></td>
                            <td> | </td>
                            <td>当前用户：</td>
                            <td>
                                <a href="javascript:void(0)" id="btnLoginUser" class="easyui-menubutton" data-options="menu:'#mm'">正在加载</a>
                                <div id="mm" style="width:100px;">
                                    <div id="btnLoginUserLogout" data-options="iconCls:'icon-ok'" onClick="onClickLogoutUser()">注销</div>
                                    <!-- <div id="btnUpdatePassword" data-options="iconCls:'icon-ok'" onClick="onUpdatePassword(true,'')">修改密码</div> -->
                                </div>
                            </td>
                            <td>推荐码：</td>
                            <td id="referralCode">正在加载</td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>
</div>
<!-- header area end -->

<!-- menu area begin -->
<div id="menubar" region="west" split="true" title="系统菜单" style="width:200px;padding:5px;">
    <ul id="systemMenu">正在加载，请稍候……</ul>
</div>
<!-- menu area end -->


<!-- workspace begin -->
<div id="content" region="center" style="overflow:auto;padding:0px;">
        <div id="contentTabs" class="easyui-tabs" fit="true" border="false" style="overflow:auto;">
        </div>
</div>
<!-- workspace end -->

<div id="windowsArea"></div>
</body>


</html>