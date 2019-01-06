<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.NumberUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>

<div data-page="more-list" class="page">

    <div class="page-content hide-bars-on-scroll">

        <div class="financing">
            <div class="search_head">
                <%--<input class="lc_search" type="text" name="" id="" value="" />--%>
                <%--<i></i>--%>
                发现
            </div>

        </div>

        <a href="<%=Config.getWebMonopoly()%>/more/company_info.jsp" class="link color-black">
            <div class="card ks-facebook-card">
                <div class="card-content">
                    <div class="card-content-inner">
                        <p class="color-gray">大富翁介绍</p>
                        <p>大富翁是深圳开普乐信息服务有限公司针对国内市场，提供综合的信息内容汇聚平台。公司依托最先进安全的计算机技术，构建完备的信息咨询、信息挖掘和大数据信息处理的高科技平台，致力为客户提供全面、准确及时的科技、金融和生活资讯，以及为客户提供个性化的的信息服务。</p>
                        <p>更多</p>
                    </div>
                </div>
            </div>
        </a>

        <div class="content-block-title">更多信息</div>
        <div class="list-block">
            <ul>
                <li><a href="#" class="link color-black check-version">
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">sync</i></div>
                        <div class="item-inner">
                            <div class="item-title">检查更新</div>
                            <div class="item-after"><%//Config.getSystemConfig("ph.version")%></div>
                        </div>
                    </div>
                </li></a>
                <%--<a href="<%=Config.getWebRootCircle()%>/mine/mine_change_mobile.jsp" class="link-black">--%>
                <li>
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">phone</i></div>
                        <div class="item-inner">
                            <div class="item-title">联系客服</div>
                            <div class="item-after"><%=Config.getSystemConfig("monopoly.service.mobile")%></div>
                        </div>
                    </div>
                </li>
                <%--<li><a href="<%=Config.getWebPH()%>/more/help_list.jsp" class="link color-black">--%>
                    <%--<div class="item-content">--%>
                        <%--<div class="item-media"><i class="icon material-icons">help_outline</i></div>--%>
                        <%--<div class="item-inner">--%>
                            <%--<div class="item-title">系统帮助</div>--%>
                        <%--</div>--%>
                    <%--</div></a>--%>
                <%--</li>--%>
            </ul>
        </div>

        <div class="content-block">
            &nbsp;
        </div>
        <div class="content-block">
            &nbsp;
        </div>

    </div>
</div>