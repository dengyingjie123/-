<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.youngbook.entity.vo.production.OrderVO" %>
<%@ page import="com.youngbook.common.utils.NumberUtils" %>
<%@ page import="com.youngbook.common.utils.MoneyUtils" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.Database" %>
<%@ page import="com.youngbook.entity.po.KVPO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.youngbook.common.KVObjects" %>
<%@ page import="com.youngbook.common.utils.StringUtils" %>
<%
String info = request.getParameter("info");

    if (StringUtils.isEmpty(info)) {
        info = request.getAttribute("info").toString();
    }

    if (StringUtils.isEmpty(info)) {
        info = "未知错误，请与管理员联系";
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>支付界面</title>
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
        function doSubmit() {

            var url = "<%=Config.getWebRoot()%>/pay/FuiouPay_getPCPayMd5";

            var formData = $('#formPCPaySubmit').serialize();
//            var orderId = fw.getFormValueByName("orderId");
//            var iss_ins_cd = fw.getFormValueByName("iss_ins_cd");
//            var goods_name = fw.getFormValueByName("goods_name");
//
//            var data = {};
//            data['orderId'] = orderId;
//            data['iss_ins_cd'] = iss_ins_cd;
//            data['goods_name'] = goods_name;

            console.log(formData);

            fw.post(url, formData, function(data){
                console.log(data);

                $('#order_id').val(data['order_id']);
                $('#md5').val(data['md5']);

                $('#formPCPaySubmit').submit();
            },null);


        }
    </script>
    <style type="text/css">
        <!--
        body {
            margin-left: 0px;
            margin-top: 0px;
            margin-right: 0px;
            margin-bottom: 0px;
            background:url(<%=Config.getWebPH()%>/include/img/pa_pay_bg.jpg) no-repeat;
        }

        .txt {
            font-size:15px;
        }
        -->
    </style>
</head>

<body>

<div style="width:100%">
    <div style="text-align:center; background:url(<%=Config.getWebPH()%>/include/img/pa_pay_bg.jpg);width:100%">
        <form id="formPCPaySubmit" name="formPCPaySubmit" method="post" action="<%=Config.getSystemConfig("fuiou.pay.pc.send.url")%>">
            <p>&nbsp;</p>
            <p>&nbsp;</p>
            <table border="0" align="center" cellpadding="0" cellspacing="0" style="background-color: #3E0090;">
                <tr>
                	<td><img src="<%=Config.getWebPH()%>/include/img/pc_pay_001.jpg" width="211" height="308"></td>
                    <td valign="middle" bgcolor="#FFFFFF">

                        <table width="300" border="0" align="center" cellpadding="10" cellspacing="0" style="background-color: #ffffff;">
                            <tr>
                                <td align="center"><span class="txt"><%=info%></span></td>
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




<p>&nbsp;</p>
</body>
</html>