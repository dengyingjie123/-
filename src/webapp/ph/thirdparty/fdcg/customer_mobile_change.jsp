<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.vo.customer.CustomerPersonalVO" %>
<%@ page import="com.youngbook.common.fdcg.FdcgCommon" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div data-page="fdcg-customer-mobile-change" class="page">
    <div class="navbar">
        <div class="navbar-inner navbar-ph">
            <div class="left"><a href="#" class="back link"> <i class="icon-white icon-back"></i><span></span></a></div>
            <div class="center sliding">更改存管手机号</div>
        </div>
    </div>

    <div class="page-content">



        <div class="list-block">
            <ul>
                <li>
                    <div class="item-content">
                        <div class="item-media"><i class="icon material-icons">phone</i></div>
                        <div class="item-inner">
                            <div class="item-input">
                                <input type="text" name="newPhone" id="newPhone" placeholder="新手机号（必填）" value=""/>
                            </div>
                        </div>
                    </div>
                </li>
            </ul>

        </div>
        <input type="hidden" id="customerId" name="customerId" value=""/>
        <input type="hidden" id="id" name="id" value=""/>





        <div class="content-block">
            <p class="buttons-row">
                <%--<a href="#" class="button button-raised button-fill btn-card-delete">解绑</a>--%>
                <a href="#" class="button button-raised button-fill btn-fdcg-customer-mobile-change">下一步</a>
            </p>
        </div>

        <form id="form-customer-mobile-change" name="form1" method="post" action="<%=FdcgCommon.getApiUrl("thirdparty.fdcg.api.phone.update")%>">
            <input type="hidden" id="reqData" name="reqData" value=""/>
        </form>


        <div class="content-block">
            &nbsp;
        </div>

        <div class="content-block">
            &nbsp;
        </div>

    </div>
</div>