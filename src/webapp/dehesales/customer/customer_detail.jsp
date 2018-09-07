<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.NumberUtils" %>
<%@ page import="com.youngbook.entity.vo.customer.CustomerPersonalVO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page import="com.youngbook.entity.vo.customer.CustomerAccountVO" %>
<%@ page import="com.youngbook.common.utils.StringUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%
    CustomerPersonalVO customerPersonalVO = (CustomerPersonalVO)request.getAttribute("customerPersonalVO");

    String editUrl = Config.getWebRoot() + "/dehesales/loadPage_deheSales_customer_save?customerId=" + customerPersonalVO.getId();


%>

<div data-page="customer-detail" class="page">

    <div class="navbar">
        <div class="navbar-inner navbar-ph">
            <div class="left"><a href="#" class="back link"> <i class="icon-white icon-back"></i><span></span></a></div>
            <div class="center">客户明细</div>
        </div>
    </div>

    <div class="page-content hide-bars-on-scroll">

        <div class="content-block-title">基本信息</div>
        <div class="list-block">
            <ul>
                <li><a href="<%=editUrl%>" class="link color-black">
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">face</i></div>
                        <div class="item-inner">
                            <div class="item-title">姓名</div>
                            <div class="item-after"><%=customerPersonalVO.getName()%></div>
                        </div>
                    </div>
                </a></li>
                <li><a href="<%=Config.getWebRoot()%>/dehesales/loadPage_deheSales_customer_save?customerId=<%=customerPersonalVO.getId()%>" class="link color-black">
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">call</i></div>
                        <div class="item-inner">
                            <div class="item-title">手机号</div>
                            <div class="item-after"><%=customerPersonalVO.getMobile()%></div>
                        </div>
                    </div>
                </a></li>
                <li><a href="<%=editUrl%>" class="link color-black">
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">folder_shared</i></div>
                        <div class="item-inner">
                            <div class="item-title">证件</div>
                            <div class="item-after"><%=customerPersonalVO.getIdCardNumber()%></div>
                        </div>
                    </div>
                </a></li>
                <li><a href="<%=editUrl%>" class="link color-black">
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">chat_bubble_outline</i></div>
                        <div class="item-inner">
                            <div class="item-title">备注</div>
                            <div class="item-after"><%=customerPersonalVO.getRemark()%></div>
                        </div>
                    </div>
                </a></li>
            </ul>
        </div>



        <div class="content-block-title">银行卡信息</div>
        <div class="list-block">
            <ul>
                <%
                    List<CustomerAccountVO> accountVOs = customerPersonalVO.getAccountVOs();
                    for (int i = 0; accountVOs != null && i < accountVOs.size(); i++) {
                        CustomerAccountVO customerAccountVO = accountVOs.get(i);

                %>
                <li><a href="#" class="link color-black">
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">credit_card</i></div>
                        <div class="item-inner">
                            <div class="item-title"><%=customerAccountVO.getBank()%></div>
                            <div class="item-after"><%=customerAccountVO.getNumber4Short()%></div>
                        </div>
                    </div>
                </a></li>
                <%
                    }
                %>
            </ul>
        </div>

        <div class="content-block">
            <p class="buttons-row">
                <a data-id="" href="<%=Config.getWebRoot()%>/dehesales/loadPage_deheSales_bank_save" class="button button-raised button-fill color-02 btn-customer-bank-add">添加新银行卡</a>
            </p>
        </div>


        <div class="content-block-title">订单信息</div>
        <div class="list-block">
            <ul>
                <li><a href="#" class="link color-black">
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">playlist_add</i></div>
                        <div class="item-inner">
                            <div class="item-title">预约订单</div>
                            <div class="item-after">2</div>
                        </div>
                    </div>
                </a></li>
                <li><a href="#" class="link color-black">
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">playlist_play</i></div>
                        <div class="item-inner">
                            <div class="item-title">打款订单</div>
                            <div class="item-after">3</div>
                        </div>
                    </div>
                </a></li>
                <li><a href="#" class="link color-black">
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">playlist_add_check</i></div>
                        <div class="item-inner">
                            <div class="item-title">已确认订单</div>
                            <div class="item-after">4</div>
                        </div>
                    </div>
                </a></li>
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