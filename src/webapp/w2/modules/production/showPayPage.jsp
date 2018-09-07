<%--
  Created by IntelliJ IDEA.
  User: yux
  Date: 2016/9/6
  Time: 16:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.text.NumberFormat" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%
    double orderAmount = Double.valueOf(request.getAttribute("orderAmount").toString());
%>

<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv=“X-UA-Compatible” content=“IE=8″>
    <title>厚币通宝</title>
    <link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/member.css" rel="stylesheet" type="text/css"/>
    <%--<link href="<%=Config.getWebRoot()%>/w2/css/member.css" rel="stylesheet" type="text/css"/>--%>
    <link href="<%=Config.getWebRoot()%>/w2/css/index/index.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/production/production.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/production/productionPay.css" rel="stylesheet" type="text/css"/>

    <script src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/fweb.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/js/bootstrap.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/framework/HopeAlert.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/framework/layer/layer.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/modules/production/productionPay.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/include/extensions/md5.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/c.js"></script>
    <style type="text/css">
        a {text-decoration:underline;color: blue}
    </style>


    <script type="text/javascript">
        var identityMessage = "<%=Config.getWords("w.pay.identity.message")%>";
    </script>
</head>

<body>
<div id="container">

    <jsp:include page="/w2/top.jsp"/>
    <div style="width: 100%;background: #fafafa;" >
        <div id="nav" class="w1200" style="padding-top: 7px;padding-bottom: 7px;">
            <a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex"><img src="<%=Config.getWebRoot()%>/w2/img/logo_Assistor.png" alt="厚币财富"/></a>
        <span>
          <a id="param1" href="<%=Config.getWebRoot()%>/w2/index/ShowIndex" class="item" >首页</a>
        <a id="param2" href="<%=Config.getWebRoot()%>/w2/modules/production/productionList.jsp" class="item" style="color:#d28d2a">投资专区</a>

          <a id="param3" href="<%=Config.getWebRoot()%>/w2/modules/about.jsp" >新手指引</a> |
            <a id="param4" href="<%=Config.getWebRoot()%>/w2/customer/IndexShow" >我的账户</a>
        </span>
        </div>
    </div>

    <div class="position r" style="width: 1200px;">
        <a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex">首页</a>
        >
        <a href="#">投资产品</a>
    </div>

    <div class="r fn-clear" style="width: 1200px;">

        <div class="pay-intro">
            <div class="ht">订单信息</div>
            <div class="content" style="padding-left: 80px;">
                <ul class="fn-clear">
                   <li class="c5">
                        <span></span>

                        <p></p>
                    </li>
                    <li class="c5">
                        <span>订单号</span>

                        <p>${orderNo}</p>
                    </li>
                    <li class="c5">
                        <span>商品名称</span>

                        <p>${productName}</p>

                    </li>

                    <li class="c5">
                        <span>应付金额</span>

                        <div>
                            <p class="fz-production"><%
                                NumberFormat nf = new DecimalFormat("###,###.00");
                                String testStr = nf.format(orderAmount/100);
                                out.print(testStr);
                            %>元</span></p>
                        </div>
                    </li>
                    <li class="c5">
                        <span></span>

                        <p></p>
                    </li>
                </ul>
            </div>

        </div>

        <div class="pay-pass">
            <div class="ht">打款信息</div>
            <div class="content"  style="padding-left: 80px;">
                    <table cellpadding="8" style="font-size:16px; margin-left:15px; color:#000000" border="0">
                        <tr>
                            <td class="td-right">尊敬的客户：</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td class="td-right"></td>
                            <td><p style="color: black;font-size: 20px;">恭喜您成功预约产品，现需您将预约金额转入以下账户，并拨打客服热线联系我们即可！</p></td>
                        </tr>
                        <tr>
                            <td class="td-right">收款方名称</td>
                            <td colspan="2"><p style="font-size: 24px;color: #000000;"><%=Config.getSystemConfig("bank.pay.offline.name")%></p></td>
                            <td style="margin-top: 8px"></td>
                        </tr>
                        <tr>
                            <td class="td-right">收款方账号</td>
                            <td colspan="3"><p style="font-size: 24px;color: red;"><%=Config.getSystemConfig("bank.pay.offline.account")%></p>
                            </td>
                        </tr>
                        <tr>
                            <td class="td-right">客服热线</td>
                            <td colspan="3"><p style="font-size: 24px;">0755-85024000</p>
                            </td>
                        </tr>
                    </table>
            </div>
        </div>


    </div>
    <div style="height:60px;"></div>
    <jsp:include page="/w2/bottom.jsp"/>
</div>


</body>
</html>
