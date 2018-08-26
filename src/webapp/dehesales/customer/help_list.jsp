<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.NumberUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>

<div data-page="help_pay" class="page">
    <div class="navbar">
        <div class="navbar-inner navbar-ph">
            <div class="left"><a href="#" class="back link"> <i class="icon-white icon-back"></i><span></span></a></div>
            <div class="center sliding">帮助</div>
            <div class="right">
            </div>
        </div>
    </div>

    <div class="page-content hide-bars-on-scroll">

        <div class="list-block">
            <ul>
                <li><a href="<%=Config.getWebPH()%>/more/help_bankcard.jsp" class="link color-black">
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">help_outline</i></div>
                        <div class="item-inner">
                            <div class="item-title">绑定银行卡</div>
                        </div>
                    </div></a>
                </li>
                <li><a href="<%=Config.getWebPH()%>/more/help_pay_mobile.jsp" class="link color-black">
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">help_outline</i></div>
                        <div class="item-inner">
                            <div class="item-title">手机支付</div>
                        </div>
                    </div></a>
                </li>
                <li><li><a href="<%=Config.getWebPH()%>/more/help_pay_pc.jsp" class="link color-black">
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">help_outline</i></div>
                        <div class="item-inner">
                            <div class="item-title">PC网银支付</div>
                        </div>
                    </div></a>
                </li>
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