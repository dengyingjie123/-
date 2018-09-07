<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.NumberUtils" %>
<%@ page import="com.youngbook.entity.vo.production.ProductionVO" %>
<%@ page import="com.youngbook.common.utils.MoneyUtils" %>
<%@ page import="com.youngbook.entity.vo.cms.ArticleVO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ProductionVO productionVO = (ProductionVO)request.getAttribute("productionVO");
    ArticleVO articleVO = (ArticleVO)request.getAttribute("articleVO");

    if (articleVO == null) {
        out.write("无法获得产品描述");
        return;
    }
%>
<div data-page="production-detail" class="page">
    <%--<div class="navbar">--%>
        <%--<div class="navbar-inner navbar-ph-bk">--%>
            <%--<div class="left"><a href="#" class="back link"> <i class="icon-white icon-back"></i><span></span></a></div>--%>
            <%--<div class="center sliding">888</div>--%>
        <%--</div>--%>
    <%--</div>--%>
    <div class="page-content">

        <div class="header_bar">
            <a href="#" class="back link"><div class="header_back"><span></span></div></a>
            <div class="header_new_title"><%=productionVO.getWebsiteDisplayName()%></div>
            <%--<div class="header_icon_new"><span></span></div>--%>
            <div class="profit_ct">
                <p class="profit_count"><%=Config.getProductionExpectedYieldName(productionVO.getMaxExpectedYield())%></p>
                <p class="profit_txt">最高预期年化收益</p>
                <div class="year_Profit_ct"></div>
                <ul class="year_Profit clearfix">
                    <li>
                        <p><%=productionVO.getInvestTermView()%></p>
                        <p>投资期限</p><i></i></li>
                    <li>
                        <p><%=MoneyUtils.format2String(productionVO.getMinSizeStart()/10000)%></p>
                        <p>起投金额（万）</p><i></i></li>
                    <li>
                        <p>在售</p>
                        <p>当前状态</p><i></i>
                    </li>
                </ul>
            </div>
        </div>
        <div class="Project_process">
            <ul class="clearfix">
                <li>产品摘要<i class="Lower_border"></i></li>
                <%--<li>产品预约</li>--%>
            </ul>
            <%--<button class="Investment_btn">已投资</button>--%>
        </div>

        <div class="content-block">
            <%=articleVO.getContent()%>
        </div>


        <div class="content-block">
            &nbsp;
        </div>

    </div>
</div>