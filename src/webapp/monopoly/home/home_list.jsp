<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.NumberUtils" %>
<%@ page import="com.youngbook.entity.vo.cms.ArticleVO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.youngbook.entity.vo.production.ProductionVO" %>
<%@ page import="com.youngbook.service.production.ProductionService" %>
<%@ page import="com.youngbook.common.utils.MoneyUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%
    String type = com.youngbook.common.utils.HttpUtils.getParameter(request, "type");
    String loginCustomerId = Config.getLoginCustomerInSession(request).getId();

    List<ArticleVO> listArticleVO = (List<ArticleVO>)request.getAttribute("listArticleVO");
    List<ArticleVO> listArticleVONews = (List<ArticleVO>)request.getAttribute("listArticleVONews");

    List<ProductionVO> listProductionVO_SM = (List<ProductionVO>)request.getAttribute("productions1");
%>

<div data-page="home-list" class="page">

    <div class="page-content">

        <div class="main_ct">
            <div class="tit">大富翁</div>

            <div class="swp_ct bananer_img">
                <!-- Swiper -->
                <div class="swiper-container ">
                    <div class="swiper-wrapper">
                        <div class="swiper-slide"><img class="banenr1" src="<%=Config.getWebDehecircle()%>/include/img/banner01.png"> </div>
                        <div class="swiper-slide"><img class="banenr1" src="<%=Config.getWebDehecircle()%>/include/img/banner02.png"> </div>

                    </div>
                    <!-- Add Pagination -->
                    <div class="swiper-pagination"></div>
                    <!-- Add Arrows -->

                </div>
            </div>


            <div class="fund_list">
                <p class="bcg_cr"></p>
                <div class="fund_tit">私募基金<i></i></div>
                <%
                    for (int i = 0; listProductionVO_SM != null && i < listProductionVO_SM.size(); i++) {
                        ProductionVO productionVO = listProductionVO_SM.get(i);
                %>
                <a href="<%=Config.getWebRoot()%>/api/dehecircle/loadPage_production_detail?productionId=<%=productionVO.getId()%>&r=<%=NumberUtils.randomNumbers(5)%>" class="link">
                <div class="fund_ct">
                    <div class="fund_list_tit"><%=productionVO.getWebsiteDisplayName()%><i></i></div>
                    <ul class="fund_list_ct clearfix">
                        <li>
                            <p class="annualized"><%=Config.getProductionExpectedYieldName(productionVO.getMaxExpectedYield())%></p>
                            <p>最高预期年化收益</p>
                        </li>
                        <li>
                            <p><%=productionVO.getInvestTermView()%></p>
                            <p>投资期限</p>
                        </li>
                        <li>
                            <p><%=MoneyUtils.format2String(productionVO.getMinSizeStart() / 10000 )%></p>
                            <p>起投金额(万）</p>
                        </li>
                    </ul>
                </div></a>
                <%
                    }
                %>
            </div>


            <div class="fund_list ">
                <p class="bcg_cr"></p>
                <div class="fund_tit">大富翁资讯<i></i></div>
                <%
                    for (int i = 0; listArticleVONews != null && i < listArticleVONews.size(); i++) {
                        ArticleVO articleVO = listArticleVONews.get(i);
                %>
                <div data-id="<%=articleVO.getId()%>" class="fund_ct mr_20 clearfix btn-production-detail">
                    <div data-id="<%=articleVO.getId()%>" class="community_lf btn-production-detail">
                        <p data-id="<%=articleVO.getId()%>" class="cnt btn-production-detail">
                            <%=articleVO.getTitle()%>
                        </p>
                        <p class="tips">金融市场</p>
                        <%--<p class="comment">0 评论</p>--%>
                    </div>
                    <div data-id="<%=articleVO.getId()%>" class="community_rt btn-production-detail">
                        <img  data-id="<%=articleVO.getId()%>" src="<%=articleVO.getImage()%>"  class="btn-production-detail" />
                    </div>
                </div>
                <%
                    }
                %>
            </div>

        </div>



        <div class="content-block">
            &nbsp;
        </div>

        <div class="content-block">
            &nbsp;
        </div>



    </div>
</div>