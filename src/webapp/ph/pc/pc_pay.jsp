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
<%@ page import="com.youngbook.entity.po.production.OrderStatus" %>
<%
    OrderVO orderVO = (OrderVO)request.getAttribute("orderVO");

    if (orderVO == null) {
        out.println("订单查询失败");
        return;
    }

    if (NumberUtils.checkNumberIn(orderVO.getStatus(), OrderStatus.Saled, OrderStatus.Feedback1)) {
        out.println("订单查询失败，请检查订单状态");
    }

    List<KVPO> banks = new ArrayList<KVPO>();

    Connection conn = Config.getConnection();
    try {
        banks = Config.getBanks("fuiouPC", conn);
    }
    catch (Exception e) {
        throw e;
    }
    finally {
        Database.close(conn);
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
                    <td valign="middle">

                        <table border="0" align="center" cellpadding="10" cellspacing="0" style="background-color: #ffffff;">
                            <tr>
                                <td align="right"><span class="txt">客户名称</span></td>
                              <td align="left"><span class="txt"><%=orderVO.getCustomerName()%></span></td>
                            </tr>
                            <tr>
                                <td align="right"><span class="txt">证件号</span></td>
                              <td align="left"><span class="txt"><%=orderVO.getCustomerCertificateNumber()%></span></td>
                            </tr>
                            <tr>
                                <td align="right"><span class="txt">产品名称</span></td>
                              <td align="left"><span class="txt"><%=orderVO.getProductionName()%></span></td>
                            </tr>
                            <tr>
                                <td align="right"><span class="txt">预期年化收益</span></td>
                              <td align="left"><span class="txt"><%=orderVO.getExpectedYield() == 0 ? "浮动" : orderVO.getExpectedYield() + "%"%></span></td>
                            </tr>
                            <tr>
                                <td align="right"><span class="txt">投资金额</span></td>
                              <td align="left"><span class="txt"><%=MoneyUtils.format2String(orderVO.getMoney())%>元</span></td>
                            </tr>
                            <tr>
                                <td align="right"><span class="txt">支付银行</span></td>
                                <td align="left">
                                  <select name="iss_ins_cd" id="iss_ins_cd" class="txt">
                                        <%
                                            for (int i = 0; banks != null && i < banks.size(); i++) {
                                                KVPO bank = banks.get(i);

                                                KVObjects parameters = StringUtils.getUrlParameters(bank.getParameter());

                                                String bankCode = parameters.getItem("fuiouPC").toString();
                                        %>
                                        <option value="<%=bankCode%>"><%=bank.getV()%></option>
                                        <%
                                            }
                                        %>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">&nbsp;</td>
                              <td align="left"><input type="button" name="button" id="button" value="同意服务协议并支付" onclick="doSubmit()" class="txt" /></td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td></td>
                            </tr>
                        </table>


                    </td>
                </tr>
            </table>
<p>&nbsp;</p>
            <p>&nbsp;</p>
            <p>&nbsp;</p>

            <input name="mchnt_cd" type="hidden" id="mchnt_cd" value="0005840F0309341" width="600px" />
            <input type="hidden" name="order_amt" id="order_amt" width="300px" value="<%=NumberUtils.getMoney2Fen(orderVO.getMoney())%>" />
            <input type="hidden" name="order_pay_type" id="order_pay_type" value="B2C"/>
            <input name="page_notify_url" type="hidden" id="page_notify_url" value="<%=Config.getSystemConfig("fuiou.pay.pc.callback.front.url")%>" width="600px" />
            <input name="back_notify_url" type="hidden" id="back_notify_url" value="<%=Config.getSystemConfig("fuiou.pay.pc.callback.back.url")%>" width="600px" />
            <input name="order_valid_time" type="hidden" id="order_valid_time" value="" width="600px" />
            <input name="goods_name" type="hidden" id="goods_name" value="" width="600px" />
            <input name="ver" type="hidden" id="ver" value="1.0.1" width="600px" />
            <input type="hidden" name="goods_display_url" id="goods_display_url" width="600px" />
            <input type="hidden" name="rem" id="rem" />
            <input type="hidden" name="orderId" id="orderId" value="<%=orderVO.getId()%>"/>
            <input type="hidden" name="md5" id="md5"/>
            <input type="hidden" name="order_id" id="order_id" value=""/>
        </form>
    </div>
</div>




<p>&nbsp;</p>
</body>
</html>