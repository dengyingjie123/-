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

    List<ArticleVO> listArticleVO = (List<ArticleVO>)request.getAttribute("listArticleVO");
    List<ArticleVO> listArticleVONews_24 = (List<ArticleVO>)request.getAttribute("listArticleVONews_24");
    List<ArticleVO> listArticleVONews_fund = (List<ArticleVO>)request.getAttribute("listArticleVONews_fund");
    List<ArticleVO> listArticleVONews_stock = (List<ArticleVO>)request.getAttribute("listArticleVONews_stock");
    List<ArticleVO> listArticleVONews_money = (List<ArticleVO>)request.getAttribute("listArticleVONews_money");

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



            <!-- 24小时要闻资讯 开始 -->
            <div class="fund_list ">
                <p class="bcg_cr"></p>
                <div class="fund_tit">24小时要闻<i></i></div>
                <%
                    for (int i = 0; listArticleVONews_24 != null && i < listArticleVONews_24.size(); i++) {
                        ArticleVO articleVO = listArticleVONews_24.get(i);
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
            <!-- 24小时要闻资讯 结束 -->



            <!-- 基金资讯 开始 -->
            <div class="fund_list ">
                <p class="bcg_cr"></p>
                <div class="fund_tit">基金资讯<i></i></div>
                <%
                    for (int i = 0; listArticleVONews_fund != null && i < listArticleVONews_fund.size(); i++) {
                        ArticleVO articleVO = listArticleVONews_fund.get(i);
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
            <!-- 基金资讯 结束 -->



            <!-- 股票资讯 开始 -->
            <div class="fund_list ">
                <p class="bcg_cr"></p>
                <div class="fund_tit">股票资讯<i></i></div>
                <%
                    for (int i = 0; listArticleVONews_stock != null && i < listArticleVONews_stock.size(); i++) {
                        ArticleVO articleVO = listArticleVONews_stock.get(i);
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
            <!-- 股票资讯 结束 -->



            <!-- 投资理财 开始 -->
            <div class="fund_list ">
                <p class="bcg_cr"></p>
                <div class="fund_tit">投资理财<i></i></div>
                <%
                    for (int i = 0; listArticleVONews_money != null && i < listArticleVONews_money.size(); i++) {
                        ArticleVO articleVO = listArticleVONews_money.get(i);
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
            <!-- 投资理财 结束 -->

        </div>



        <div class="content-block">
            &nbsp;
        </div>

        <div class="content-block">
            &nbsp;
        </div>



    </div>
</div>