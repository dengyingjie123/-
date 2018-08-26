<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.production.OrderPO" %>
<%@ page import="com.youngbook.entity.wvo.production.ProductionWVO" %>
<%@ page import="com.youngbook.entity.po.info.LegalAgreementPO" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerCertificatePO" %>
<%@ page import="com.youngbook.entity.po.system.CodeType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // 判断用户是否登陆
    CustomerPersonalPO loginUser = null;
    if (request.getSession().getAttribute("loginUser") != null) {
        loginUser = (CustomerPersonalPO) request.getSession().getAttribute("loginUser");
    } else {
        String url = Config.getWebRoot() + Config.Web_URL_Login;
        response.sendRedirect(url);
        return;
    }
    LegalAgreementPO legalAgreement = (LegalAgreementPO) request.getAttribute("legalAgreement");
    ProductionWVO productionWVO = null;
    productionWVO = (ProductionWVO) request.getAttribute("productionWVO");
    double investMoney = Double.valueOf(request.getAttribute("investMoney").toString());
    OrderPO order = (OrderPO) request.getAttribute("order");
%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv=“X-UA-Compatible” content=“IE=8″>
    <title>点金派</title>
    <link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/member.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/index/index.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/production/production.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/production/productionPay.css" rel="stylesheet" type="text/css"/>
    <script src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/fweb.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/js/bootstrap.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/framework/jquery.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/framework/fw.js"></script>
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
            <a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex"><img src="<%=Config.getWebRoot()%>/w2/img/logo_Assistor.png" alt="开普乐"/></a>
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
            <div class="ht">产品信息</div>
            <div class="content" style="padding-left: 80px;">
                <ul class="fn-clear">
                    <li class="c1">
                        <span>产品名称</span>
                        <p><%=productionWVO.getWebsiteDisplayName()%></p>
                    </li>
                    <li class="c4">
                        <span>收益方式</span>
                        <p><%=productionWVO.getInterestType()%></p>
                    </li>
                    <li class="c2">
                        <span>预期年化收益率</span>
                        <p class="fz-production"><%=productionWVO.getExpectedYield()%>%</p>
                    </li>
                    <li class="c3">
                        <span>投资期限</span>
                        <p style="font-weight:bold;"><%=productionWVO.getInvestTermView()%></p>
                    </li>
                    <li class="c5">
                        <span>投资金额</span>

                        <div>
                            <p class="fz-production"><%
                                NumberFormat nf = new DecimalFormat("###,###.00");
                                String testStr = nf.format(investMoney);
                                out.print(testStr);
                                %>元</span></p>
                            <input type="hidden" id="compositionId" value="<%=productionWVO.getCompositionId()%>"/>
                        </div>
                    </li>
                </ul>
            </div>

        </div>

        <div class="pay-pass">
            <div class="ht">确认支付</div>
            <div class="content"  style="padding-left: 80px;">
                <form class="form-horizontal" method="post" id="productionPayForm" name="productionPayForm" action="<%=Config.getWebRoot()%>/w2/production/appointmentOrPay4Web">
                    <!-- 富友支付 -->
                    <input type="hidden" id="mchnt_cd" name="mchnt_cd"/>
                    <input type="hidden" id="order_id" name="order_id"/>
                    <input type="hidden" id="order_amt" name="order_amt"/>
                    <input type="hidden" id="order_pay_type" name="order_pay_type"/>
                    <input type="hidden" id="page_notify_url" name="page_notify_url"/>
                    <input type="hidden" id="back_notify_url" name="back_notify_url"/>
                    <input type="hidden" id="order_valid_time" name="order_valid_time"/>
                    <input type="hidden" id="iss_ins_cd" name="iss_ins_cd"/>
                    <input type="hidden" id="goods_name" name="goods_name"/>
                    <input type="hidden" id="goods_display_url" name="goods_display_url"/>
                    <input type="hidden" id="rem" name="rem"/>
                    <input type="hidden" id="ver" name="ver"/>
                    <input type="hidden" id="md5" name="md5"/>


                    <input type="hidden" value="<%=productionWVO.getId()%>" id="productionId" name="order.productionId"/>
                    <input type="hidden" value="<%=investMoney%>" id="money" name="order.money"/>
                    <input type="hidden" value="<%=productionWVO.getCompositionId()%>" id="productionCompositionId" name="order.productionCompositionId"/>
                    <input type="hidden" value="" id="salemanId"/>
                    <input type="hidden" value="" id="accountId" name="order.accountId"/>
                    <input type="hidden" id="customerId" name="order.customerId" value="<%=loginUser.getId()%>"/>
                    <input type="hidden" value="<%=Config.getSystemConfig("web.default.operatorId")%>" id="operatorId"/>
                    <%-- 订单id，判断是否已有的订单 --%>
                    <input type="hidden" value="<%=order == null ? "" : order.getId()%>" id="orderId" name="orderId"/>
                    <table cellpadding="8" style="font-size:16px; margin-left:15px; color:#000000" border="0">
                        <tr>
                            <td class="td-right">投资者姓名</td>
                            <td colspan="2"><input type="text" class="form-control" id="customerName" name="customerName" value="<%=loginUser.getName()%>" readonly="readonly"></td>
                            <td style="margin-top: 8px"></td>
                            <td rowspan="9" valign="bottom" style="color:#ff3f3f;">
                                <img src="<%=Config.getWebRoot()%>/w2/img/bg_payflow.png" style="margin-bottom: 50px;" width="334" height="360">
                            </td>
                        </tr>
                        <%
                            CustomerCertificatePO CustomerCertificate = (CustomerCertificatePO)request.getAttribute("customerCertificate");
                        %>
                        <tr>
                            <td class="td-right">投资者证件号</td>
                            <td colspan="2"><input type="text" class="form-control" id="customerCertificateNumber" name="customerCertificateNumber" value="<%=CustomerCertificate.getNumber()%>" readonly="readonly"></td>
                            <td style="margin-top: 8px"></td>
                        </tr>

                        <tr>
                            <td class="td-right">投资者手机号</td>
                            <td colspan="2"><input type="text" class="form-control" id="customerMobile" name="customerMobile" value="<%=loginUser.getMobile()%>" readonly="readonly"></td>
                            <td style="margin-top: 8px"></td>
                        </tr>

                        <tr>
                            <td class="td-right">推荐码</td>
                            <td colspan="2"><input type="text" class="form-control" id="referralCode" name="order.referralCode" style="display: inline;">&nbsp;&nbsp;&nbsp;&nbsp;<label >（可选）</label></td>
                            <td style="margin-top: 8px"></td>
                        </tr>

                        <tr>
                            <td class="td-right">交易密码</td>
                            <td colspan="3"><input type="password" maxlength="6" class="form-control" id="businessPwd" name="businessPwd">
                            </td>
                        </tr>

                        <tr>
                            <td class="td-right"> 验证码</td>
                            <td colspan="3">
                                <table>
                                 <tr>
                                     <td>
                                <input type="text" class="form-control" style="width:150px;" id="captcha" name="captcha">
                                     </td>
                                   <td>
                                       <img id="codeId" onClick="javascript:onClickRefresh(this,5)" src="<%=Config.getWebRoot()%>/w2/system/getCaptcha.action?u=<%=CodeType.PRODUCTION_PAY%>" style="background:url(<%=Config.getWebRoot()%>/w2/img/code.gif);width:100px;height:32px; border:0px;">

                                   </td>
                                     <td>
                                     <input type="hidden" id="u" name="u" value="<%=CodeType.PRODUCTION_PAY%>"/>
                             <a href="javascript:void(0);"
                                   onclick="javascript:refreshImg('#codeId', <%=CodeType.PRODUCTION_PAY%>);">&nbsp;&nbsp;&nbsp;&nbsp;换一张</a>&nbsp;&nbsp;<span
                                    id="CodeTip" style="color: #e74c3c;"></span>
                                     </td>
                                 </tr>
                                </table>
                        </tr>
                        <tr>
                            <td></td>
                            <td colspan="3">
                                <input name="Fruit" type="checkbox" id="identity" onClick="onClickIdentity(identityMessage)"/>
                                <label>我已确认本人身份</label>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td colspan="3">
                                <input name="Fruit" type="checkbox" id="agree" onClick="onClickIMAgreement()"/>
                                <label>我已经阅读并同意<a href="<%=Config.getWebRoot()%>/w2/modules/article/legalAgreement.jsp" target="_blank" title="点击查看《风险提示书》" style="text-decoration:underline;color:#0000ff">《风险提示书》</a>和
                                    <a href="<%=Config.getWebRoot()%>/w2//modules/about/productionAssignmentAgreement.jsp" target="_blank" title="点击查看《产品收益权转让及服务协议》" style="text-decoration:underline;color:#0000ff">《产品收益权转让及服务协议》</a>
                                </label>
                                <input type="hidden" name="legalAgreement.name" id="legalAgreementId"
                                       value="<%=legalAgreement.getName()%>">
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td><input type="button" id="PaymentButton" class="mybtns btns-mysubmit " value="支付" style="width: 260px;height:36px; font-size: 18px;cursor: pointer" onClick="appointmentOrPaySubmit()" title="点击支付"></td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </div>

    <div style="height:60px;"></div>

    <jsp:include page="/w2/bottom.jsp"/>
</div>

</body>
</html>