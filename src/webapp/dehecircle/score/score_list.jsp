<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.NumberUtils" %>
<%@ page import="com.youngbook.entity.vo.customer.CustomerScoreVO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%
    String type = com.youngbook.common.utils.HttpUtils.getParameter(request, "type");
    String loginCustomerId = Config.getLoginCustomerInSession(request).getId();

    CustomerScoreVO customerScoreVO = HttpUtils.getAttribute("customerScoreVO", CustomerScoreVO.class, request);
%>

<div data-page="score-list" class="page">

    <div class="navbar">
        <div class="navbar-inner navbar-ph">
            <div class="left"><a href="#" class="back link"> <i class="icon-white icon-back"></i><span></span></a></div>
            <div class="center sliding">我的积分</div>
            <div class="right">
            </div>
        </div>
    </div>

    <div class="page-content hide-bars-on-scroll">

        <div class="content-block">
            尊敬的客户，您当前的可用积分为<%=customerScoreVO.getAvailableScore()%>分。<br><br>
            <img src="<%=Config.getWebDehecircle()%>/include/img/scroe_vipcard_<%=customerScoreVO.getScoreLevel()%>.png" style="width:100%; height:100%" />
        </div>

        <div class="content-block-title">积分详情</div>
        <div class="list-block">
            <ul>
                <li><a href="<%=Config.getWebRoot()%>/api/dehecircle/loadPage_score_add_list?customerId=<%=customerScoreVO.getCustomerId()%>" class="item-link">
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">shopping_cart</i></div>
                        <div class="item-inner">
                            <div class="item-title">获得的积分</div>
                            <div class="item-after"><%=customerScoreVO.getTotalScore()%></div>
                        </div>
                    </div></a>
                </li>
                <li><a href="<%=Config.getWebRoot()%>/api/dehecircle/loadPage_score_use_list?customerId=<%=customerScoreVO.getCustomerId()%>" class="item-link">
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">shopping_cart</i></div>
                        <div class="item-inner">
                            <div class="item-title">使用的积分</div>
                            <div class="item-after"><%=customerScoreVO.getUsedScore()%></div>
                        </div>
                    </div></a>
                </li>
            </ul>
        </div>


        <div class="content-block">
            &nbsp;
        </div>

    </div>
</div>