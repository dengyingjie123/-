<%@ page import="com.youngbook.entity.po.cms.ArticlePO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ArticlePO articlePO = (ArticlePO)request.getAttribute("articlePO");
%>
<div data-page="goods-detail" class="page">
    <div class="navbar">
        <div class="navbar-inner navbar-ph">
            <div class="left"><a href="#" class="back link"> <i class="icon-white icon-back"></i><span></span></a></div>
            <div class="center sliding"><%=articlePO.getTitle()%></div>
        </div>
    </div>
    <div class="page-content">

        <div class="content-block-title"><%=articlePO.getTitle()%></div>
        <div class="item-content production-detail-content">
            <%=articlePO.getContent()%>
        </div>



        <div class="content-block">
            <p class="buttons-row">
                <a data-id="<%=articlePO.getId()%>" data-score="<%=articlePO.getScore()%>" href="#" class="button button-raised button-fill color-01 btn-score-use">积分兑换</a>
            </p>
            <div class="row">

            </div>
        </div>


        <div class="content-block">
            &nbsp;
        </div>


    </div>
</div>