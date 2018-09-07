<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.NumberUtils" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerScorePO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%
    List<CustomerScorePO> listCustomerScorePO = (List<CustomerScorePO>)request.getAttribute("listCustomerScorePO");
%>

<div data-page="score-add-list" class="page">

    <div class="navbar">
        <div class="navbar-inner navbar-ph">
            <div class="left"><a href="#" class="back link"> <i class="icon-white icon-back"></i><span></span></a></div>
            <div class="center sliding">使用的积分</div>
            <div class="right">
            </div>
        </div>
    </div>

    <div class="page-content hide-bars-on-scroll">


        <div class="list-block">
            <ul>
                <%
                    for (int i = 0; listCustomerScorePO != null && i < listCustomerScorePO.size(); i++) {
                        CustomerScorePO customerScorePO = listCustomerScorePO.get(i);

                %>
                <li><a href="<%=Config.getWebRoot()%>/api/dehecircle/loadPage_score_detail?customerScoreId=<%=customerScorePO.getId()%>" class="item-link">
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">shopping_cart</i></div>
                        <div class="item-inner">
                            <div class="item-title"><%=customerScorePO.getComment()%></div>
                            <div class="item-after"><%=customerScorePO.getScore()%></div>
                        </div>
                    </div></a>
                </li>
                <%
                    }
                %>
            </ul>
        </div>


        <div class="content-block">
            &nbsp;
        </div>

    </div>
</div>