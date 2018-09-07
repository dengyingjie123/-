<%@ page import="com.youngbook.common.config.Config" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="info.jsp" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page import="com.youngbook.entity.wvo.production.ProductionWVO" %>
<%@ page import="com.youngbook.service.production.ProductionService" %>
<%@ page import="java.util.List" %>
<%@ page import="com.youngbook.common.utils.TimeUtils" %>
<%@ page import="com.youngbook.entity.po.production.ProductionStatus" %>
<%@ page import="com.youngbook.entity.po.production.ProductionIncomeType" %>

<%
    // 判断用户是否登陆
    CustomerPersonalPO loginUser = null;
    if (request.getSession().getAttribute("loginUser") != null) {
        loginUser = (CustomerPersonalPO) request.getSession().getAttribute("loginUser");
    }
    List<ProductionWVO> productionList = (List<ProductionWVO>) request.getAttribute("productionList");
%>

<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- 百度统计：必须放在 html 和 head 之间，且必须在所有 js 代码的最前面 -->
    <script src="<%=Config.getWebRoot()%>/w2/js/common/baidutongji.js"></script>
    <script type="text/javascript">
        /*<![CDATA[*/
        window.global = {};
        global.isDebug = false;
        global.csrf = {"name": "g_tk", "value": "30a67bdd2ff2fcebf3cff07e9de605fb2de5e6c6"};
        global.timeDelta = 1444920034 - parseInt(new Date() / 1000, 10);
        global.qUrl = "/quan"
        /*]]>*/
    </script>
    <script>
        var _hmt = [];
        _hmt.push(['_setCustomVar', 2, 'login', 0, 2]);
    </script>

    <title>点金派</title>

    <%--图片切换_--%>
    <link href="<%=Config.getWebRoot()%>/w2/dist/css/bootstrap-combined.min.css" rel="stylesheet" media="screen">
    <link rel="stylesheet" href="<%=Config.getWebRoot()%>/w2/dist/css/bootstrap-theme.min.css"/>

    <link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/index/index.css" rel="stylesheet" type="text/css"/>
    <script language="javascript" src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/js/bootstrap.min.js"></script>
    <script language="javascript"
            src="<%=Config.getWebRoot()%>/w2/dist/plus/easypiechart/jquery.easypiechart.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/html5/excanvas.compiled.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/fweb.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/modules/index.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/c.js"></script>

</head>

<body>
<div id="container">

    <jsp:include page="/w2/top.jsp"/>

    <%--导航栏--%>
    <div style="width: 100%;background: #fafafa;">
        <div id="nav" class="w1200" style="padding-top: 7px;padding-bottom: 7px;">
            <a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex">
                <img src="<%=Config.getWebRoot()%>/w2/img/logo_Assistor.png" alt="开普乐"/>
            </a>
        <span>
          <a id="param1" href="<%=Config.getWebRoot()%>/w2/index/ShowIndex"
             style="color: #d28d2a;padding-bottom: 29px;border-bottom: 2px solid #d28d2a;">首页</a>
         <a id="param2" href="<%=Config.getWebRoot()%>/w2/modules/production/productionList.jsp">投资专区</a>
          <a id="param3" href="<%=Config.getWebRoot()%>/w2/modules/about.jsp">新手指引</a> |
            <a id="param4" href="<%=Config.getWebRoot()%>/w2/customer/IndexShow">我的账户</a>
        </span>
        </div>
    </div>


    <div class="row-fluid" style="min-width:1200px;">
        <div class="span12">
            <div class="carousel slide" id="carousel-884809" data-ride="carousel" style="margin-bottom: 0px;">
                <div class="carousel-inner">
                    <div class="item active">
                        <img alt="" src="<%=Config.getWebRoot()%>/w2/img/1.png"  />
                    </div>
                    <div class="item">
                        <img alt="" src="<%=Config.getWebRoot()%>/w2/img/2.png"  />
                    </div>
                    <div class="item">
                        <img alt="" src="<%=Config.getWebRoot()%>/w2/img/3.jpg"  />
                    </div>

                </div>
                <%--左右滑动图片--%>
                <a data-slide="prev" href="#carousel-884809" class="left carousel-control" style="border: 3px solid #222222">‹</a>
                <a data-slide="next" href="#carousel-884809" class="right carousel-control" style="border: 3px solid #222222" id="next-onclick"    >›</a>
            </div>
        </div>
    </div>

    <div class="num_mod J_mod">
        <div class="num_group">
            <div class="n_act J_choose">
            </div>
            <div class="n_act J_choose">
            </div>
            <div class="n_act J_choose">
            </div>
        </div>
    </div>


    <div style="width: 100%;background: white">
        <div id="activities" class="w1200">
            <table class="act-items">
                <tr>
                    <td><span class="activityType"><img src="<%=Config.getWebRoot()%>/w2/img/sywj.png"/>&nbsp;&nbsp;收益稳健</span></td>
                    <td><span class="activityDetail">优选项目坚守价值投资，<br/>预期年化收益 6%—10%</span></td>
                    <td><span class="activityType"><img src="<%=Config.getWebRoot()%>/w2/img/slcq.png"/>&nbsp;&nbsp;实力超群</span></td>
                    <td><span class="activityDetail">股东背景实力雄厚，<br/>资深投行高管强势运作</span></td>
                    <td><span class="activityType"><img src="<%=Config.getWebRoot()%>/w2/img/aqbz.png"/>&nbsp;&nbsp;安全保障</span></td>
                    <td><span class="activityDetail">银行级风控360°护航，<br/>IT技术铸造铜墙铁壁防护</span></td>
                </tr>
            </table>
        </div>
    </div>

    <div id="investmentPlans" class="w1200" style="display:<%=productionList.size() == 0 ? "none" : "" %>">
        <%
            //显示多少产品
            int displayCount=0;
            for (int i = 0; i < productionList.size(); i++) {

                ProductionWVO production = productionList.get(i);
                // 剩余时间
                long remainSeconds = production.getRemainSeconds();

                // 是否售罄
                boolean isSoldOut = production.getStatus() == ProductionStatus.Sold ? true : false;

                if(!isSoldOut&&remainSeconds>0){
                    int countCount = displayCount % 3;
                    displayCount++;
        %>
        <div class="plan-control">
            <div class="planMain-control" onmouseout="factorout('factorId<%=i%>')" onmouseover="factorover('factorId<%=i%>')" style="background: url(<%=Config.getWebRoot()%>/w2//img/bg_color_<%=countCount%>.gif);">
                <br/>
                <span class="production-name"><%=productionList.get(i).getName()%></span>
                <p class="type-p"><%=productionList.get(i).getProductionDescription()%></p>
                <ul class="static" id='factorId<%=i%>'>
                    <li><span class="lab">收益系数</span><span class="rate rate<%=productionList.get(i).getIncomeFactor()%>"></span></li>
                    <li><span class="lab">安全系数</span><span class="rate rate<%=productionList.get(i).getSafeFactor()%>"></span></li>
                </ul>
            </div>
            <div id="planDetail1">
                <table>
                    <tr>
                        <td class="plan-yield" colspan="3">

                        </td>
                        <td colspan="2" class="plan-yield">

                            <%
                                if(production.getIncomeType() == 0) {
                            %>
                            购买进度
                            <span style="font-weight: bold">
                                <%
                                    double v = productionList.get(i).getSaleMoney() / productionList.get(i).getSize() * 100;
                                    out.println(new java.text.DecimalFormat("######0.00").format(v));
                                %>%
                            </span>
                            <%
                                }
                            %>


                        </td>
                    </tr>
                    <tr>
                        <td colspan="5" class="plan-line"><img src="<%=Config.getWebRoot()%>/w2/img/line.jpg"
                                                               width="348"/></td>
                    </tr>
                    <tr>

                        <%
                            if(true) {
                        %>

                        <td class="plan-expre_total" align="center">

                            <%
                                //组装 html
                                String temp = "<space class='font-pink-26'><b>";
                                String max = String.valueOf(productionList.get(i).getMaxExpectedYield());
                                //截取小数点后的数字.
                                String las = max.substring(max.lastIndexOf("."));
                                String baifenhao = "<span>" + las + "%</span>";
                                if (productionList.get(i).getMaxExpectedYield() == productionList.get(i).getMinExpectedYield()) {

                                    out.print(temp + max.substring(0, max.lastIndexOf(".")) + baifenhao);
                                } else {

                                    String min = String.valueOf(productionList.get(i).getMinExpectedYield());
                                    //截取小数点后的数字.
                                    String minlas = min.substring(max.lastIndexOf("."));
                                    String minbof = "<span>" + minlas + "%</span>";
                                    out.print(temp + min.substring(0, min.lastIndexOf(".")) + minbof + "</space> - " + temp + max.substring(0, max.lastIndexOf(".")) + baifenhao + "");
                                }
                            %>
                            </b> </space>
                            <br/><br/>
                            <p>预期年化收益率</p>
                        </td>
                        <td valign="bottom"><p>|</p></td>

                        <%
                            }
                        %>

                        <td class="plan-expre_total" align="center">

                                        <span class="font-black-14px-bold">
                                            <%=productionList.get(i).getInvestTermView()%></span>
                            <br/><br/>

                            <p> 投资期限</p>
                        </td>
                        <td valign="bottom"><p>|</p></td>
                        <td class="plan-expre_total" align="center">
                                        <span class="font-black-14px-bold">
                                            <%=new java.text.DecimalFormat("###,###.##").format(productionList.get(i).getSize())%>
                                        </span>
                            <span class="font-black-14" style="font-size: 14px;"> 元</span>
                            <br/><br/>

                            <p>产品总额</p>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="5" class="plan-yield-btn" align="center">
                            <%
                                String btnSoldName = "立即投资";
                                if (isSoldOut) {
                                    btnSoldName = "已售罄";
                                }
                                if (remainSeconds <= 0) {
                                    btnSoldName = "已结束";
                                }
                                if(true) {
                                    btnSoldName = "立即预约";
                                }
                                if (remainSeconds > 0 && !isSoldOut) {
                            %>
                            <input type="button" class="btn-352pk" style="width: 352px;" value="<%=btnSoldName%>" onclick="window.location.href='/core/w2/production/productionDetail?productionWVO.id=<%=productionList.get(i).getId()%>'"/>
                        </td>
                            <% } else { %>
                                <input type="button" class='btns-over' value="<%=btnSoldName%>" style="width:352px;"/>
                            <% } %>
                    </tr>
                </table>
            </div>
        </div>
        <%}} %>

        <%

               int  blankSpaceCount = displayCount % 3 == 0 ? 0 : (3 - displayCount % 3);
        %>

        <% for (int j = 0; j < blankSpaceCount; j++) { %>
        <div class="plan-control" style="background: #DDDDDD">
            <div class="panel_img">
                <img src="<%=Config.getWebRoot()%>/w2/img/jqqd.png">
            </div>
            <div class="panel-font">
                敬请期待
            </div>
        </div>
        <% } %>

    </div>


    <div style="width: 100%;background: #fafafa;clear: both;height: 350px;display: none;">
        <div class="w1200" style="padding-top: 7px;padding-bottom: 7px;height: 70px;margin: 0 auto;">
            <label style=" font-size: 24px;color: #ff6900;padding-top: 20px;padding-bottom: 20px;">
                专业理财师为你服务</label>
            <table width="1200" height="" cellpadding="0" cellspacing="0">
                <tr>
                    <td style="border:1px #e4e4e4 solid">
                        <table style="margin:30px 20px;font-size: 18px;" cellspacing="5" cellpadding="4">
                            <tr>
                                <td><img src="<%=Config.getWebRoot()%>/w2//img/bg_header.png"/></td>
                                <td>
                                    <div style="height:117px;">
                                  <span class="lcs_name">
                                    某某某
                                    <img src="<%=Config.getWebRoot()%>/w2//img/smallstar.png" valign="middle"/>
                                    <img src="<%=Config.getWebRoot()%>/w2//img/smallstar.png"/>
                                    <img src="<%=Config.getWebRoot()%>/w2//img/smallstar.png"/>
                                  </span>

                                        <div class="lcs_info">
                                            <li><strong>972 位</strong>

                                                <p class="p-12px">服务客户</p></li>
                                            <li>
                                                <strong class="p-12px-strong">5,216 万</strong>

                                                <p class="p-12pk">投资总额</p></li>
                                        </div>
                                        <p class="tip">共为客户赚取 <strong>3,300,476</strong> 元</p>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <input type="button" class="btn-352pk" style="width: 352px; margin-top: 20px;"
                                           value="申请为我服务"
                                           onclick="window.location.href='/core/w2/customer/IndexShow'"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td style="border:1px #e4e4e4 solid">
                        <table style="margin:30px 20px;font-size: 18px;" cellspacing="5" cellpadding="4">
                            <tr>
                                <td><img src="<%=Config.getWebRoot()%>/w2//img/bg_header.png"/></td>
                                <td>
                                    <div style="height:117px;">
                                  <span class="lcs_name">
                                    某某某
                                    <img src="<%=Config.getWebRoot()%>/w2//img/smallstar.png" valign="middle"/>
                                    <img src="<%=Config.getWebRoot()%>/w2//img/smallstar.png"/>
                                    <img src="<%=Config.getWebRoot()%>/w2//img/smallstar.png"/>
                                  </span>

                                        <div class="lcs_info">
                                            <li><strong>972 位</strong>

                                                <p class="p-12px">服务客户</p></li>
                                            <li>
                                                <strong class="p-12px-strong">5,216 万</strong>

                                                <p class="p-12pk">投资总额</p></li>
                                        </div>
                                        <p class="tip">共为客户赚取 <strong>3,300,476</strong> 元</p>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <input type="button" class="btn-352pk" style="width: 352px; margin-top: 20px;"
                                           value="申请为我服务"
                                           onclick="window.location.href='/core/w2/customer/IndexShow'"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td style="border:1px #e4e4e4 solid">
                        <table style="margin:30px 20px;font-size: 18px;" cellspacing="5" cellpadding="4">
                            <tr>
                                <td><img src="<%=Config.getWebRoot()%>/w2//img/bg_header.png"/></td>
                                <td>
                                    <div style="height:117px;">
                                  <span class="lcs_name">
                                    某某某
                                    <img src="<%=Config.getWebRoot()%>/w2//img/smallstar.png" valign="middle"/>
                                    <img src="<%=Config.getWebRoot()%>/w2//img/smallstar.png"/>
                                    <img src="<%=Config.getWebRoot()%>/w2//img/smallstar.png"/>
                                  </span>

                                        <div class="lcs_info">
                                            <li><strong>972 位</strong>

                                                <p class="p-12px">服务客户</p>
                                            </li>
                                            <li>
                                                <strong class="p-12px-strong">5,216 万</strong>

                                                <p class="p-12pk">投资总额</p>
                                            </li>
                                        </div>
                                        <p class="tip">共为客户赚取 <strong>3,300,476</strong> 元</p>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <input type="button" class="btn-352pk" style="width: 352px; margin-top: 20px;"
                                           value="申请为我服务"
                                           onclick="window.location.href='/core/w2/customer/IndexShow'"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
    </div>

    <div style="width: 100%;clear: both;height: 10px;">
    </div>

    <div id="productions" class="w1000" style="display: none">

        <div id="tableTitle">推荐列表<span><a href="#">更多产品</a></span></div>
        <div id="tableDetail">
            <table class="w1000" cellpadding="0" cellspacing="0">
                <tr id="firstLine">
                    <td>产品详情</td>
                    <td>预期收益率</td>
                    <td>金额（元）</td>
                    <td>投资期限</td>
                    <td>购买进度</td>
                    <td>操作</td>
                </tr>
                <tbody id="puductionListID">
                <%--js加载产品列表--%>
                </tbody>
            </table>
        </div>

    </div>
    <div class="index_div">
        <div class="scaif">
        <a  class="scaif_btn" href="<%=Config.getWebRoot()%>/w2/customer/IndexShow"><img
                src="<%=Config.getWebRoot()%>/w2/img/index_gg_btn.png"></a>
        </div>
    </div>
    <div class="index_div2">
        <div class="index_div2_top_img">
            <img src="<%=Config.getWebRoot()%>/w2/img/index-best.png">
        </div>
        <div>
        <div class="index_HZ">
            <div style="width:100%;">
                <table style="width:1200px; margin: 0 auto;" cellpadding="10">
                    <tr>
                        <td class="indx_hz_text">合作伙伴</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td class="indx_hz_btn" align="right"> </td>
                    </tr>
                    <tr>
                        <td>
                            <a href="http://www.dachengnet.com" target="_blank"><img src="<%=Config.getWebRoot()%>/w2/img/hz-1.png"></a>
                        </td>
                        <td width="">
                            <a href="http://www.boc.cn" target="_blank"><img src="<%=Config.getWebRoot()%>/w2/img/icon_zgbank.png"></a>
                        </td>
                        <td width="">
                            <a href="http://bank.pingan.com" target="_blank"><img src="<%=Config.getWebRoot()%>/w2/img/icon_pabank.png"></a>
                        </td>
                        <td width="">
                            <a href="http://www.cr6868.com" target="_blank"><img src="<%=Config.getWebRoot()%>/w2/img/hz-2.png"></a>
                        </td>
                    </tr>
                </table>

            </div>
        </div>

        <div style="margin-top: -29px;">
            <jsp:include page="/w2/bottom.jsp"/>
        </div>
    </div>
    </div>

</body>
</html>