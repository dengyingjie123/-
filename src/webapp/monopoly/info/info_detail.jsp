<%@ page import="com.youngbook.entity.po.cms.ArticlePO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ArticlePO articlePO = (ArticlePO)request.getAttribute("articlePO");
%>
<div data-page="info-detail" class="page">

    <div class="page-content">


        <div class="header_bar2">
            <a href="#" class="back link"><div class="header_back"><span></span></div></a>
            <div class="header_new_title">大富翁资讯</div>
        </div>
        <div class="Community_ct">
            <p class="tit"><%=articlePO.getTitle()%></p>
            <div class="look_time">
                <p class="time"><%=articlePO.getPublishedTime()%></p>
                <p class="browse_number"><i></i>&nbsp;</p>
            </div>
            <p class="Community_ct_text"><%=articlePO.getContent()%></p>
        </div>


        <%--<p class="bcg_cr"></p>--%>
        <%--<div class="more_info">--%>
            <%--<dl>--%>
                <%--<dt>相关资讯</dt>--%>
                <%--<dd><p>不懂点“心理学效应”小心掉进消费陷阱！不懂点不懂点</p></dd>--%>
                <%--<dd><p>不懂点“心理学效应”小心掉进消费陷阱！不懂点不懂点</p></dd>--%>

            <%--</dl>--%>

        <%--</div>--%>

        <div class="content-block">
            &nbsp;
        </div>

        <div class="content-block">
            &nbsp;
        </div>


    </div>
</div>