<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%
    CustomerPersonalPO customerPersonalPO = new CustomerPersonalPO();
    if (request.getAttribute("customerPersonalPO") != null) {
        customerPersonalPO = (CustomerPersonalPO)request.getAttribute("customerPersonalPO");
    }

%>
<div data-page="bank-save" class="page">

    <div class="navbar">
        <div class="navbar-inner navbar-ph">
            <div class="left"><a href="#" class="back link"> <i class="icon-white icon-back"></i><span></span></a></div>
            <div class="center">银行卡信息</div>
        </div>
    </div>

    <div class="page-content hide-bars-on-scroll">

        <div class="list-block">
            <ul>
                <li><a href="#" class="link color-black">
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">domain</i></div>
                        <div class="item-inner">
                            <div class="item-title">请选择开户行</div>
                            <div class="item-after"></div>
                        </div>
                    </div>
                </a></li>
            </ul>
        </div>

        <form class="list-block inset inputs-list" id="customer-save-form">
            <ul>
                <li>
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">credit_card</i></div>
                        <div class="item-inner">
                            <div class="item-input">
                                <input type="text" name="name" placeholder="卡号" value=""/>
                            </div>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">location_city</i></div>
                        <div class="item-inner">
                            <div class="item-input">
                                <input type="tel" name="mobile" placeholder="开户支行名称" value=""/>
                            </div>
                        </div>
                    </div>
                </li>
            </ul>
            <input type="hidden" name="id" value="<%=customerPersonalPO.getId()%>" />
        </form>


        <div class="content-block">
            <p class="buttons-row">
                <a data-id="" href="#" class="button button-raised button-fill color-02 btn-customer-bank-save">保存</a>
            </p>
        </div>

        <div class="content-block">
            &nbsp;
        </div>
        <div class="content-block">
            &nbsp;
        </div>

    </div>
</div>