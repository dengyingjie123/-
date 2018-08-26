<%@ page import="com.youngbook.common.config.Config" %><%--
  Created by IntelliJ IDEA.
  User: Lee
  Date: 12/5/2017
  Time: 11:07 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>德恒普惠-支付界面</title>
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/framework/themes/gray/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/framework/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/style/default.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/style/buttons.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/framework/fullcalendar-2.2.2/fullcalendar.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/framework/fullcalendar-2.2.2/fullcalendar.print.css" media='print'>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/jquery.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/third-party/accounting.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/easyloader.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/hashMap.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/frameworkplus.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/treeplus.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/md5.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/validator.js"></script>
    <script type="text/javascript">
        function submit() {

            $('#formPCPay').submit();
        }
    </script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <style type="text/css">
        <!--
        body {
            margin-left: 0px;
            margin-top: 0px;
            margin-right: 0px;
            margin-bottom: 0px;
            background:url(../include/img/pa_pay_bg.jpg) no-repeat;
        }

        .txt {
            font-size:20px;
        }
        -->
    </style>
</head>

<body>
<div style="width:100%">
    <div style="text-align:center; background:url(../include/img/pa_pay_bg.jpg);width:100%">
        <form id="formPCPay" name="formPCPay" method="post" action="<%=Config.getWebRoot()%>/production/loadOrderVO" target="_blank">
            <p>&nbsp;</p>
            <p>&nbsp;</p>
            <table border="0" align="center" cellpadding="0" cellspacing="0" style="background-color: #ffffff;">
                <tr>
                	<td><img src="../include/img/pc_pay_001.jpg" width="211" height="308"></td>
                    <td valign="middle">

                        <table border="0" align="center" cellpadding="10" cellspacing="0" style="background-color: #ffffff;">
                            <tr>
                                  <td>&nbsp;</td>
                                  <td valign="top"><span class="txt">欢迎使用支付码支付</span></td>
                            </tr>
                            <tr>
                                <td valign="bottom"><span class="txt">手机号</span></td>
                              <td valign="bottom"><input type="text" name="mobile" id="mobile" value="" class="txt"></td>
                            </tr>
                            <tr>
                                <td><span class="txt">支付码</span></td>
                                <td><input type="text" name="payCode" id="payCode" value="" class="txt"></td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td valign="top"><input type="button" name="button" id="button" value="    确定    " class="txt" onClick="submit()"></td>
                            </tr>
                            
                        </table>

                    </td>
                </tr>
            </table>
<p>&nbsp;</p>
            <p>&nbsp;</p>
            <p>&nbsp;</p>
        </form>
    </div>
</div>
</body>
</html>
