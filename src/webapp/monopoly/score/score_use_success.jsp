<%@ page import="com.youngbook.entity.po.cms.ArticlePO" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerScorePO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    CustomerScorePO customerScorePO = (CustomerScorePO)request.getAttribute("customerScorePO");
%>
<div data-page="score-use-success" class="page">
    <div class="navbar">
        <div class="navbar-inner navbar-ph">
            <div class="left"><a href="#" class="back link"> <i class="icon-white icon-back"></i><span></span></a></div>
            <div class="center sliding">返回信息</div>
        </div>
    </div>
    <div class="page-content">

        <div class="item-content production-detail-content">
            成功使用积分<%=customerScorePO.getScore()%>
        </div>



        <div class="content-block">
            &nbsp;
        </div>


    </div>
</div>