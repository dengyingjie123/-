<%--<%@ page import="com.youngbook.common.config.Config" %>--%>
<%--<%@ page import="com.youngbook.dao.MySQLDao" %>--%>
<%--<%@ page import="com.youngbook.entity.po.cms.ArticlePO" %>--%>
<%--<%@ page import="com.youngbook.entity.po.customer.CustomerMoneyPO" %>--%>
<%--<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>--%>
<%--<%@ page import="com.youngbook.entity.wvo.production.ProductionWVO" %>--%>
<%--<%@ page import="com.youngbook.entity.po.production.ProductionInfoPO" %>--%>
<%--<%@ page import="java.util.List" %>--%>
<%--<%@ page import="com.youngbook.common.KVObject" %>--%>
<%--<%@ page import="java.util.ArrayList" %>--%>
<%--<%@ page import="com.youngbook.common.utils.StringUtils" %>--%>
<%--<%@ page import="com.youngbook.common.utils.MoneyUtils" %>--%>
<%--<%@ page import="com.youngbook.common.Money" %>--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<%--%>
    <%--// 判断用户是否登陆--%>
    <%--CustomerPersonalPO loginUser = null;--%>
    <%--if (request.getSession().getAttribute("loginUser") != null) {--%>
        <%--loginUser = (CustomerPersonalPO) request.getSession().getAttribute("loginUser");--%>
    <%--}--%>
<%--%>--%>
<%--<%--%>
    <%--ProductionWVO productionWVO = (ProductionWVO) request.getAttribute("productionWVO");--%>
    <%--ProductionInfoPO  productionInfo = (ProductionInfoPO) request.getAttribute("productionInfo");--%>

<%--%>--%>
<%--<!DOCTYPE html>--%>
<%--<html>--%>
<%--<html>--%>
<%--<head>--%>
    <%--<meta http-equiv="Content-Type" content="text/html; charset=utf-8">--%>
    <%--<meta http-equiv=“X-UA-Compatible” content=“IE=8″>--%>
    <%--<title>点金派</title>--%>
    <%--<link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>--%>
    <%--<link href="<%=Config.getWebRoot()%>/w2/css/index/index.css" rel="stylesheet" type="text/css"/>--%>
    <%--<link href="<%=Config.getWebRoot()%>/w2/css/production/productionDetail.css" rel="stylesheet" type="text/css"/>--%>
    <%--<link href="<%=Config.getWebRoot()%>/w2/css/production/production.css" rel="stylesheet" type="text/css"/>--%>
    <%--<link href="<%=Config.getWebRoot()%>/w2/css/member.css" rel="stylesheet" type="text/css"/>--%>

    <%--<script src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>--%>
    <%--<script src="<%=Config.getWebRoot()%>/w2/dist/js/bootstrap.min.js"></script>--%>
    <%--<script src="<%=Config.getWebRoot()%>/w2/js/common/c.js"></script>--%>
    <%--<script src="<%=Config.getWebRoot()%>/w2/js/common/cookie.js"></script>--%>
    <%--<script src="<%=Config.getWebRoot()%>/include/extensions/frameworkplus.js"></script>--%>
    <%--<script src="<%=Config.getWebRoot()%>/w2/js/common/fweb.js"></script>--%>

    <%--<script src="<%=Config.getWebRoot()%>/w2/dist/plus/formatCurrency/jquery.formatCurrency.js"></script>--%>
    <%--<script src="<%=Config.getWebRoot()%>/w2/js/modules/production/productionDetail.js"></script>--%>

    <%--<script src="<%=Config.getWebRoot()%>/w2/js/framework/HopeAlert.js"></script>--%>
<%--</head>--%>

<%--<body>--%>
<%--<div id="container">--%>
    <%--<jsp:include page="/w2/top.jsp"/>--%>
    <%--&lt;%&ndash;导航栏&ndash;%&gt;--%>
    <%--<div style="width: 100%;background: #fafafa;">--%>
        <%--<div id="nav" class="w1200" style="padding-top: 7px;padding-bottom: 7px;">--%>
            <%--<a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex"><img--%>
                    <%--src="<%=Config.getWebRoot()%>/w2/img/logo_Assistor.png" alt="开普乐"/></a>--%>
        <%--<span>--%>
          <%--<a id="param1" href="<%=Config.getWebRoot()%>/w2/index/ShowIndex" class="item">首页</a>--%>
        <%--<a id="param2" href="<%=Config.getWebRoot()%>/w2/modules/production/productionList.jsp" class="item"--%>
           <%--style="color:#d28d2a">投资专区</a>--%>

          <%--<a id="param3" href="<%=Config.getWebRoot()%>/w2/modules/about.jsp">新手指引</a> |--%>
            <%--<a id="param4" href="<%=Config.getWebRoot()%>/w2/customer/IndexShow">我的账户</a>--%>
        <%--</span>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>


<%--<div class="position r">--%>
    <%--<a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex">首页</a> >--%>
    <%--<a href="<%=Config.getWebRoot()%>/w2/modules/production/productionList.jsp">投资产品</a>--%>
    <%--> <%=productionWVO.getWebsiteDisplayName()%>--%>
<%--</div>--%>

<%--<div class="r fn-clear">--%>
    <%--<div class="fn-left w800">--%>
        <%--<div class="h-intro">--%>
            <%--<div class="ht">&lt;%&ndash;项目名称&ndash;%&gt; <%=productionWVO.getWebsiteDisplayName()%>--%>
            <%--</div>--%>
            <%--<div class="content">--%>
                <%--<ul class="fn-clear">--%>
                    <%--<li class="c1"><span>产品总金额</span><strong class="money"><%=MoneyUtils.format2String(productionWVO.getSize())%></strong>&nbsp;&nbsp;元</li>--%>


                    <%--<%--%>
                        <%--if(true) {--%>
                    <%--%>--%>
                    <%--<li class="c2"><span>预期年化收益率</span>--%>

                        <%--<div><b><strong class="fz-primary"><%--%>
                            <%--String max = String.valueOf(productionWVO.getMaxExpectedYield());--%>
                            <%--//截取小数点后的数字.--%>
                            <%--String las = max.substring(max.lastIndexOf("."));--%>
                            <%--String baifenhao = "<b>" + las + "%</b>";--%>
                            <%--//最大收益率是否与最小收益率相同--%>
                            <%--if (productionWVO.getMaxExpectedYield() == productionWVO.getMinExpectedYield()) {--%>
                                <%--out.print( max.substring(0, max.lastIndexOf(".")) + baifenhao);--%>
                            <%--} else {--%>
                                <%--out.print(productionWVO.getMinExpectedYield() + "%</strong> - <strong class='fz-primary'>" + productionWVO.getMaxExpectedYield() + "%");--%>
                            <%--}--%>

                        <%--%></strong></b></div>--%>
                    <%--</li>--%>
                    <%--<%--%>
                        <%--}--%>
                    <%--%>--%>

                    <%--<li class="c3"><span>投资期限</span>--%>

                        <%--<div><strong>&lt;%&ndash;投资期限&ndash;%&gt; <%=productionWVO.getInvestTermView()%>--%>
                        <%--</strong></div>--%>
                    <%--</li>--%>

                    <%--<%--%>
                        <%--if(true) {--%>
                    <%--%>--%>
                    <%--<li class="c4">--%>
                        <%--<span>起息日</span>--%>
                        <%--&lt;%&ndash;<%=productionWVO.getInterestDate()%>&ndash;%&gt;--%>
                        <%--购买产品后第二天--%>
                    <%--</li>--%>
                    <%--<%--%>
                        <%--}--%>
                    <%--%>--%>
                <%--</ul>--%>
            <%--</div>--%>

            <%--<%--%>
                <%--if(productionWVO.getIncomeType() == 0) {--%>
            <%--%>--%>
            <%--<div class="h-foot" style="height: 40px;">购买进度--%>
                <%--<div class="progress" style="margin: 15px 0px 0px 0px">--%>
                    <%--<span data="<%=productionWVO.getSaleMoney()/productionWVO.getSize()*100%>"></span>--%>
                <%--</div>--%>
                    <%--<span>--%>
                        <%--<%--%>
                            <%--double v = productionWVO.getSaleMoney() / productionWVO.getSize() * 100;--%>

                            <%--out.println(new java.text.DecimalFormat("######0.00").format(v));--%>
                        <%--%>%--%>
                    <%--</span>--%>
                <%--，剩余时间：<%--%>
                    <%--if (productionWVO.getSaleMoney() / productionWVO.getSize() * 100 < 100) {--%>
                        <%--out.write(productionWVO.getStopTimeDay());--%>
                    <%--} else {--%>
                        <%--out.write("已结束");--%>
                    <%--}--%>
                <%--%>--%>
            <%--</div>--%>
            <%--<%--%>
                <%--} else {--%>
            <%--%>--%>
            <%--<div class="h-foot" style="height: 20px;">募集时间：<%--%>
                    <%--if (productionWVO.getSaleMoney() / productionWVO.getSize() * 100 < 100) {--%>
                        <%--out.write(productionWVO.getStopTimeDay());--%>
                    <%--} else {--%>
                        <%--out.write("已结束");--%>
                    <%--}--%>
                <%--%>--%>
            <%--</div>--%>
            <%--<%--%>
                <%--}--%>
            <%--%>--%>


        <%--</div>--%>

        <%--<%--%>
            <%--// 设置标题和内容--%>
            <%--List<KVObject> titleAndContents = new ArrayList<KVObject>();--%>
            <%--String yes = Config.getSystemVariable("system.kv.is_avaliable.yes");--%>
            <%--if (StringUtils.checkEquals(productionInfo.getIsDisplay1(), yes)) {--%>
                <%--KVObject titleAndContent = new KVObject(productionInfo.getTitle1(), productionInfo.getContent1());--%>
                <%--titleAndContents.add(titleAndContent);--%>
            <%--}--%>

            <%--if (StringUtils.checkEquals(productionInfo.getIsDisplay2(), yes)) {--%>
                <%--KVObject titleAndContent = new KVObject(productionInfo.getTitle2(), productionInfo.getContent2());--%>
                <%--titleAndContents.add(titleAndContent);--%>
            <%--}--%>

            <%--if (StringUtils.checkEquals(productionInfo.getIsDisplay3(), yes)) {--%>
                <%--KVObject titleAndContent = new KVObject(productionInfo.getTitle3(), productionInfo.getContent3());--%>
                <%--titleAndContents.add(titleAndContent);--%>
            <%--}--%>

            <%--if (StringUtils.checkEquals(productionInfo.getIsDisplay4(), yes)) {--%>
                <%--KVObject titleAndContent = new KVObject(productionInfo.getTitle4(), productionInfo.getContent4());--%>
                <%--titleAndContents.add(titleAndContent);--%>
            <%--}--%>

            <%--if (StringUtils.checkEquals(productionInfo.getIsDisplay5(), yes)) {--%>
                <%--KVObject titleAndContent = new KVObject(productionInfo.getTitle5(), productionInfo.getContent5());--%>
                <%--titleAndContents.add(titleAndContent);--%>
            <%--}--%>



        <%--%>--%>

        <%--<div class="detail">--%>
            <%--<div class="u-tabs fn-clear">--%>
                <%--<%--%>
                    <%--for (int i = 0; i < titleAndContents.size(); i++) {--%>
                        <%--KVObject titleAndContent = titleAndContents.get(i);--%>
                        <%--%>--%>
                <%--<a href="javascript:void(0);" class="<%=(i == 0? "current c1" : "")%>" style="color:#daa25b; font-size:14px"><%=titleAndContent.getKey().toString()%></a>--%>
                <%--<%--%>
                    <%--}--%>
                <%--%>--%>

                <%--<%--%>
                    <%--if(true) {--%>
                <%--%>--%>
                <%--<a href="javascript:void(0);" class="<%=titleAndContents.size()==0?"current c1":""%>" style="color:#daa25b;">投资记录</a>--%>
                <%--<%--%>
                    <%--}--%>
                <%--%>--%>
            <%--</div>--%>
            <%--<div class="tabs-wrap">--%>

                <%--<%--%>
                    <%--for (int i = 0; i < titleAndContents.size(); i++) {--%>
                        <%--KVObject titleAndContent = titleAndContents.get(i);--%>
                <%--%>--%>
                <%--<div class="item"  style="<%=(i == 0 ? "display:block" : "")%>">--%>
                    <%--<span><%=titleAndContent.getValue().toString()%></span>--%>
                <%--</div>--%>
                <%--<%--%>
                    <%--}--%>
                <%--%>--%>

                <%--<div class="item"   style="<%=titleAndContents.size()==0?"display:block":""%>">--%>
                    <%--<table>--%>
                        <%--<tr>--%>
                            <%--<td width="600">客户名称</td>--%>
                            <%--<td width="200" align="center">投资金额</td>--%>
                            <%--<td width="200" align="center">购买时间</td>--%>
                        <%--</tr>--%>
                        <%--<tbody id="orderList">--%>
                        <%--<!-- 投资记录内容 -->--%>
                        <%--</tbody>--%>
                    <%--</table>--%>
                    <%--<div class="pagelist">--%>
                        <%--<nav>--%>
                            <%--<ul class="pagination" id="pagination">--%>
                                <%--<li><a href='javascript:void(0);' aria-label='Previous' style="color:#000000;"><span--%>
                                        <%--aria-hidden='true'>上一页</span></a>--%>
                                <%--</li>--%>
                                <%--<li class='active'><a href='javascript:void(0);' style="color:#000000;">1</a></li>--%>
                                <%--<li><a href='javascript:void(0);' aria-label='Next' style="color:#000000;"><span--%>
                                        <%--aria-hidden='true'>下一页</span></a></li>--%>
                            <%--</ul>--%>
                        <%--</nav>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>

        <%--</div>--%>

    <%--</div>--%>

    <%--<div class="fn-right w300">--%>
        <%--<div class="user-investment">--%>
            <%--<div class="ht">--%>
                <%--&lt;%&ndash;<span style="width:250px;">&ndash;%&gt;--%>
                <%--&lt;%&ndash;<img src="<%=Config.getWebRoot()%>/w2/img/flag_account.gif"&ndash;%&gt;--%>
                <%--&lt;%&ndash;style="vertical-align:text-bottom;"/>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<a href="{{ loginInfoModel.href }}"&ndash;%&gt;--%>
                <%--&lt;%&ndash;style=" font-size:16px; font-weight:bold;"><%=loginUser.getLoginName()%>&ndash;%&gt;--%>
                <%--&lt;%&ndash;</a></span>&ndash;%&gt;--%>

                <%--<ul>--%>
                    <%--<li>--%>
                        <%--<span style="font-size: 20px;font-weight:bold;">产品管理详情</span>--%>
                    <%--</li>--%>
                    <%--<li style="display: none">产品管理总金额：<b><span class="money"><%=MoneyUtils.format2String(productionWVO.getSaleMoney())%></span></b> 元--%>
                    <%--</li>--%>
                    <%--<li>剩余可投金额：<b><span class="money"><%=MoneyUtils.format2String(productionWVO.getSize() - productionWVO.getSaleMoney())%></span></b> 元--%>
                    <%--</li>--%>
                <%--</ul>--%>
            <%--</div>--%>

            <%--<!-- 登陆用户信息 开始 -->--%>
            <%--<%--%>
                <%--CustomerMoneyPO money = new CustomerMoneyPO();--%>
                <%--money.setCustomerId(loginUser.getId());--%>
                <%--money.setState(Config.STATE_CURRENT);--%>
                <%--money = MySQLDao.load(money, CustomerMoneyPO.class);--%>
            <%--%>--%>
            <%--<form id="investForm" name="investForm" method="post" action="goPay">--%>
                <%--<div style="padding-right:30px;" class="ht">--%>
                    <%--<input type="hidden" id="AvailableMoney" name="AvailableMoney"--%>
                           <%--value="<%=money.getAvailableMoney()%>">--%>
                    <%--<input type="hidden" id="productionWVOId" name="productionWVOId"--%>
                           <%--value="<%=productionWVO.getId()%>">--%>
                    <%--<input type="hidden" id="productionWVOSizeStart" name="productionWVO.sizeStart"--%>
                           <%--value="<%=productionWVO.getSizeStart()%>">--%>
                    <%--<input type="hidden" id="productionWVOSizeStop" name="productionWVO.sizeStop"--%>
                           <%--value="<%=productionWVO.getSizeStop()%>">--%>

                    <%--<%--%>
                        <%--if (!productionWVO.getStopTimeDay().equals("已结束")) {--%>
                    <%--%>--%>

                        <%--<!-- 以下的内容按产品类型区分：开始 -->--%>

                        <%--<%--%>
                            <%--if(true) {--%>
                        <%--%>--%>
                            <%--<table>--%>
                                <%--<tr>--%>
                                    <%--<td colspan="2"></td>--%>
                                <%--</tr>--%>
                                <%--<tr>--%>
                                    <%--<td></td>--%>
                                    <%--<td>--%>
                                        <%--<input type="button" class="mybtns btns-myprimary" style="width: 240px;height:40px;font-size:18px;" id="investSubmmitButtons" value="立 即 预 约" onclick="invest('<%=productionWVO.getSizeStart()%>','<%=productionWVO.getSize() - productionWVO.getSaleMoney()%>', '');">--%>
                                    <%--</td>--%>
                                <%--</tr>--%>
                                <%--<tr>--%>
                                    <%--<td colspan="2"><span style="font-weight:bold;visibility:visible;color: #e74c3c;height:10px; " id="verifyMoney"></span></td>--%>
                                <%--</tr>--%>
                            <%--</table>--%>
                        <%--<%--%>
                            <%--} else {--%>
                        <%--%>--%>
                            <%--<table>--%>
                                <%--<tr>--%>
                                    <%--<td colspan="2"><span style="font-size:10px;visibility:visible;color:#000000;height:10px;" >请填写投资金额，最大投资金额：<span class="money"><%=productionWVO.getSizeStop()%>元</span></span><br/></td>--%>
                                <%--</tr>--%>
                                <%--<tr>--%>
                                    <%--<td><input type="text" class="myinput-control" MAXLENGTH="8" onblur="formatMoney();" onclick="investMoney()" placeholder="起投金额<%=productionWVO.getSizeStart()%>" id="investMeney" name="investMeney"></td>--%>
                                    <%--<td>--%>
                                        <%--<%--%>
                                            <%--if (productionWVO.getSaleMoney() / productionWVO.getSize() * 100 < 100) {--%>
                                        <%--%>--%>
                                            <%--<input type="button" class="mybtns btns-myprimary" style="width: 60px;height:30px;" id="investSubmmitButtons" value="投资" onclick="invest('<%=productionWVO.getSizeStart()%>','<%=productionWVO.getSize() - productionWVO.getSaleMoney()%>', '');">--%>
                                        <%--<% } else {%>--%>
                                            <%--<input type="button" class='btns-over' value="已结束" style="width:60px; height:30px;"/>--%>
                                        <%--<% }%>--%>
                                    <%--</td>--%>
                                <%--</tr>--%>
                                <%--<tr>--%>
                                    <%--<td colspan="2"><span style="font-weight:bold;visibility:visible;color: #e74c3c;height:10px; " id="verifyMoney"></span></td>--%>
                                <%--</tr>--%>
                            <%--</table>--%>
                        <%--<%--%>
                            <%--}--%>
                        <%--%>--%>

                        <%--<!-- 以上的内容按产品类型区分：结束 -->--%>

                    <%--<%--%>
                        <%--}--%>
                    <%--%>--%>
                <%--</div>--%>

            <%--</form>--%>
        <%--</div>--%>

    <%--</div>--%>

<%--</div>--%>
<%--<div style="height: 50px;">--%>

    <%--<jsp:include page="/w2/bottom.jsp"/>--%>

<%--</div>--%>

<%--<div class="customer-phone" hidden="hidden">--%>
    <%--<div class="phone-hd">--%>
        <%--<h3 class="title">预约</h3><i><a href="javascript:;"><img src="<%=Config.getWebRoot()%>/w2/img/x.png" onclick="javascript:closeAppointmentWindow()" /></a></i></div>--%>
    <%--<div class="phone-text">--%>
        <%--<label for="" class="input_label">咨询专线：0755-85024000</label>--%>
        <%--<span class="tip-text">由开普乐专业的理财顾问为您服务！</span>--%>
    <%--</div>--%>
<%--</div>--%>

<%--<div class="simu_wrap" hidden="hidden">--%>
    <%--<div class="simu_text_1 clearfix">--%>
        <%--<div class="simu_text_wrap">--%>
            <%--<div class="dialog_hd clearfix">--%>
                <%--<div class="logo"></div>--%>
                <%--<div class="logo_txt">合格投资者认定</div>--%>
            <%--</div>--%>
            <%--<div class="simu_text_bd">--%>
                <%--<div class="p_wrap">--%>
                    <%--<p>&nbsp;&nbsp;&nbsp;&nbsp;根据《私募投资基金监督管理暂行办法》第四章第十四条规定：“私募基金管理人、私募基金销售机构不得向合格投资者之外的单位和个人募集资金，不得通过报刊、电台、电视、互联网等公众传播媒体或者讲座、报告会、分析会和布告、传单、手机短信、微信、博客和电子邮件等方式，向不特定对象宣传推介。</p>--%>
                    <%--<p>&nbsp;&nbsp;&nbsp;&nbsp;点金派谨遵《私募投资基金监督管理暂行办法》之规定，只向特定的合格投资者宣传推介相关私募投资基金产品。</p>--%>
                    <%--<p>&nbsp;&nbsp;&nbsp;&nbsp;阁下如有意进行私募投资基金投资且满足《私募投资基金监督管理暂行办法》关于“合规投资者”标准之规定，即具备相应风险识别能力和风险承担能力，投资于单只私募基金的金额不低于100 万元，且个人金融类资产不低于300万元或者最近三年个人年均收入不低于50万元人民币。请阁下详细阅读本提示，并确认自己是特定的合规投资者。</p>--%>
                <%--</div>--%>
                <%--<div class="btn_wrap">--%>
                    <%--<a class="btn-know" href="javascript:;" id="iknow" onclick="javascript:closeAgreementWindow()">我是合格投资者</a>--%>
                    <%--<label><input class="cb" checked="" id="select_check" type="checkbox"><span class="txt">记住选择，不再提示</span></label>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>

<%--</body>--%>
<%--<script language="JavaScript">--%>

    <%--$('.money').formatCurrency();--%>

    <%--// 如果是浮动收益，弹出合格投资者确认窗口--%>
    <%--<%--%>
        <%--if(productionWVO.getIncomeType() == 1) {--%>
    <%--%>--%>
            <%--if(getCookie('isInvestor') != 'true') {--%>
                <%--openAgreementWindow();--%>
            <%--}--%>
    <%--<%--%>
        <%--}--%>
    <%--%>--%>

<%--</script>--%>
<%--</html>--%>