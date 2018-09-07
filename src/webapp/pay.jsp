<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.IdUtils" %>
<%@ page import="com.youngbook.entity.po.KVPO" %>
<%@ page import="com.youngbook.common.Database" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.youngbook.common.utils.StringUtils" %>
<%@ page import="com.youngbook.common.KVObjects" %>
<%
    String orderNO = IdUtils.getNewLongIdString();

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
<title>Untitled Document</title>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/jquery.min.js"></script>
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
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/validator.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/highcharts4/js/highcharts.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/fullcalendar-2.2.2/lib/moment.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/fullcalendar-2.2.2/fullcalendar.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/fullcalendar-2.2.2/lang/zh-cn.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/scripts/callcenter/callcenter.js"></script>
    <script type="text/javascript">


        function submitForm() {

            var mchnt_cd = fw.getFormValueByName("mchnt_cd");
            var order_id = fw.getFormValueByName("order_id");
            var order_amt = fw.getFormValueByName("order_amt_yuan") * 100;
            var order_pay_type = fw.getFormValueByName("order_pay_type");
            var page_notify_url = fw.getFormValueByName("page_notify_url");
            var back_notify_url = fw.getFormValueByName("back_notify_url");
            var order_valid_time = fw.getFormValueByName("order_valid_time");
            var iss_ins_cd = $("#iss_ins_cd").val();
            var goods_name = fw.getFormValueByName("goods_name");
            var goods_display_url = fw.getFormValueByName("goods_display_url");
            var rem = fw.getFormValueByName("rem");
            var ver = fw.getFormValueByName("ver");
            var formValue = mchnt_cd+"|"  +order_id+"|"+order_amt+"|"+order_pay_type+"|"+page_notify_url+"|"+back_notify_url+"|"+order_valid_time+"|"+iss_ins_cd+"|"+goods_name+"|"+goods_display_url+"|"+rem+"|"+ver;

            var getMd5 = WEB_ROOT + "/system/getMd54PC.action";

            console.log(formValue);

            fw.post(getMd5, "formValue="+formValue, function(data){
                if (!fw.checkIsTextEmpty(data['md5'])) {
                    $('#md5').val(data['md5']);
                    // alert(data['formValue']);
                    $('#order_amt').val(order_amt);
                    $('#form').submit();
                }
            },null);


        }
    </script>
</head>

<body>
<form id="form" name="form" method="post" action="https://pay.fuiou.com/smpGate.do">
    <table width="100%" border="1">
        <tr>
            <td>交易金额*</td>
            <td><label>
                <input type="text" name="order_amt_yuan" id="order_amt_yuan" width="300px" value="10" />
            元</label></td>
        </tr>
        <tr>
            <td>支付类型*</td>
            <td><label>
                <input name="order_pay_type" type="text" id="order_pay_type" value="B2B" width="300px" />
            </label></td>
        </tr>
        <tr>
            <td>银行代码*</td>
            <td>

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
            <td>&nbsp;</td>
            <td>
            	<input type="button" name="button" id="button" value="提交" onclick="submitForm()" />
                <input type="reset" name="button2" id="button2" value="重置" />
            </td>
        </tr>
    </table>
    <input name="mchnt_cd" type="hidden" id="mchnt_cd" value="0005840F0309341" width="600px" />
    <input type="hidden" name="order_id" id="order_id"  width="600px" value="<%=orderNO%>"/>
    <input name="page_notify_url" type="hidden" id="page_notify_url" value="http://www.dianjinpai.com/core/payback.jsp" width="600px" />
    <input name="back_notify_url" type="hidden" id="back_notify_url" value="http://www.dianjinpai.com/core/payback.jsp" width="600px" />
    <input name="order_valid_time" type="hidden" id="order_valid_time" value="10m" width="600px" />
    <input name="goods_name" type="hidden" id="goods_name" value="" width="600px" />
    <input name="ver" type="hidden" id="ver" value="1.0.1" width="600px" />
    <input type="hidden" name="goods_display_url" id="goods_display_url" width="600px" />
    <input type="hidden" name="rem" id="rem" width="600px" />
    <input type="hidden" name="md5" id="md5" width="600px" />
    <input type="hidden" name="order_amt" id="order_amt" width="300px" value="" />
</form>
</body>
</html>