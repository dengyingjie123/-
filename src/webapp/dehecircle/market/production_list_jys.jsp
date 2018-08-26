<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.youngbook.common.utils.NumberUtils" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page import="com.youngbook.entity.vo.production.ProductionVO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.youngbook.common.utils.MoneyUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%
    String type = com.youngbook.common.utils.HttpUtils.getParameter(request, "type");

    /**
     * 私募产品
     */
    List<ProductionVO> listProductionVO_SM = null;
    if (request.getAttribute("productions1") != null) {
        listProductionVO_SM = (List<ProductionVO>)request.getAttribute("productions1");
    }

    /**
     * 交易所产品
     */
    List<ProductionVO> listProductionVO_JYS = null;
    if (request.getAttribute("productions5") != null) {
        listProductionVO_JYS = (List<ProductionVO>)request.getAttribute("productions5");
    }

%>

<div data-page="production-list-jys" class="page">

    <div class="page-content bd-btn-body">

        <div class="financing">
            <div class="search_head">
                <%--<input class="lc_search" type="text" name="" id="" value="" />--%>
                <%--<i></i>--%>
                市场
            </div>
            <div class="common_nav lc_list clearfix">
                <a href="<%=Config.getWebRoot()%>/api/dehecircle/loadPage_production_list">
                    <img src="<%=Config.getWebDehecircle()%>/include/img/lc_icon1.png" /> 私募(<%=listProductionVO_SM.size()%>)
                </a>
                <a href="#">
                    <img src="<%=Config.getWebDehecircle()%>/include/img/lc_icon2.png" /> 公募(0)
                </a>
                <a href="<%=Config.getWebRoot()%>/api/dehecircle/loadPage_production_list_JYS">
                    <img src="<%=Config.getWebDehecircle()%>/include/img/lc_icon3.png" /> 交易所(<%=listProductionVO_JYS.size()%>)
                </a>

                </a>
                <a href="#" class="lc_ct_dsp">
                    <img src="<%=Config.getWebDehecircle()%>/include/img/lc_icon4.png" /> 保险(0)
                </a>


                <a class="btn_production_more" href="#">
                    <img class="more_ing" src="<%=Config.getWebDehecircle()%>/include/img/lc_icon5.png" /> 更多
                </a>

            </div>
            <div class="fund_list">
                <p class="bcg_cr"></p>
                <%
                    for (int i = 0; listProductionVO_JYS != null && i < listProductionVO_JYS.size(); i++) {
                        ProductionVO productionVO = listProductionVO_JYS.get(i);

                %>
                <a href="<%=Config.getWebRoot()%>/api/dehecircle/loadPage_production_detail?productionId=<%=productionVO.getId()%>&r=<%=NumberUtils.randomNumbers(5)%>" class="link">
                <div class="fund_ct ">
                    <div class="fund_list_tit"><%=productionVO.getWebsiteDisplayName()%><span class="hot_tj">热销产品</span></div>
                    <div class="tip clearfix"> <i><%=productionVO.getProjectTypeName()%></i></div>
                    <ul class="fund_list_ct clearfix">
                        <li>
                            <p class="annualized"><%=Config.getProductionExpectedYieldName(productionVO.getMaxExpectedYield())%></p>
                            <p>预期年化收益率</p>
                        </li>
                        <li>
                            <p><%=productionVO.getInvestTermView()%></p>
                            <p>投资期限</p>
                        </li>
                        <li>
                            <p><%=MoneyUtils.format2String(productionVO.getMinSizeStart() / 10000)%></p>
                            <p>起投金额（万元）</p>
                        </li>
                    </ul>
                </div>
                </a>
                <%
                    }
                %>
            </div>

        </div>


        <%--<div class="content-block">--%>
            <%--<a href="#"  class="link btn-test">测试</a>--%>
        <%--</div>--%>
        <div class="content-block">
            &nbsp;
        </div>

    </div>
</div>