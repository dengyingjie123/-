<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%
    CustomerPersonalPO customerPersonalPO = new CustomerPersonalPO();
    if (request.getAttribute("customerPersonalPO") != null) {
        customerPersonalPO = (CustomerPersonalPO)request.getAttribute("customerPersonalPO");
    }

%>
<div data-page="customer-save" class="page">

    <div class="navbar">
        <div class="navbar-inner navbar-ph">
            <div class="left"><a href="#" class="back link"> <i class="icon-white icon-back"></i><span></span></a></div>
            <div class="center">客户基本信息</div>
        </div>
    </div>

    <div class="page-content hide-bars-on-scroll">

        <form class="list-block inset inputs-list" id="customer-save-form">
            <ul>
                <li>
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">person_outline</i></div>
                        <div class="item-inner">
                            <div class="item-input">
                                <input type="text" name="name" placeholder="姓名" value="<%=customerPersonalPO.getName()%>"/>
                            </div>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">call</i></div>
                        <div class="item-inner">
                            <div class="item-input">
                                <input type="tel" name="mobile" placeholder="手机" value="<%=customerPersonalPO.getMobile()%>"/>
                            </div>
                        </div>
                    </div>
                </li>
                <li class="align-top">
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">chat_bubble_outline</i></div>
                        <div class="item-inner">
                            <div class="item-input">
                                <textarea name="remark" class="resizable" placeholder="备注"><%=customerPersonalPO.getRemark()%></textarea>
                            </div>
                        </div>
                    </div>
                </li>
            </ul>
            <input type="hidden" name="id" value="<%=customerPersonalPO.getId()%>" />
        </form>


        <div class="content-block">
            <p class="buttons-row">
                <a data-id="" href="#" class="button button-raised button-fill color-02 btn-customer-save">保存</a>
                <a data-id="" href="#" class="button button-raised button-fill color-01 btn-customer-remove">删除</a>
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