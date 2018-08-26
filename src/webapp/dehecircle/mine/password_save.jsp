<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.KVObjects" %>
<%@ page import="com.youngbook.common.KVObject" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.mchange.v1.util.StringTokenizerUtils" %>
<%@ page import="com.youngbook.common.utils.StringUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    CustomerPersonalPO loginCustomer = Config.getLoginCustomerInSession(request);
    if (loginCustomer == null) {
        out.println("<script>window.location='"+Config.getWebDehecircle()+"/login.jsp'</script>");
        return;
    }
%>
<div data-page="password-save" class="page">

    <div class="page-content">

        <div class="header_bar2">
            <a href="#" class="back link"><div class="header_back"><span></span></div></a>
            <div class="header_new_title">密码修改</div>
        </div>

        <div class="content-block">

            <form id="form-mine-change-password" name="form_mine_change_password">
                <div class="list-block inputs-list">
                    <ul>
                        <li>
                            <div class="item-content">
                                <div class="item-media"><i class="icon material-icons">lock_outline</i></div>
                                <div class="item-inner">
                                    <div class="item-input">
                                        <input name="personal.password" type="password" placeholder="请输入新密码" value=""/>
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-media"><i class="icon material-icons">phone_iphone</i></div>
                                <div class="item-inner">
                                    <div class="item-input">
                                        <input name="mobile" type="text" placeholder="手机号" value="<%=loginCustomer.getMobile()%>" readonly/>
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-media"><i class="icon material-icons">sms</i></div>
                                <div class="item-inner">
                                    <div class="item-input">
                                        <input name="mobileCode" type="text" placeholder="请输入短信验证码" value=""/>
                                    </div>
                                </div>
                                <div class="item-inner">
                                    <div class="item-input">
                                        <a id="btn-mine-change-password-get-code" href="#" class="button">获取验证码</a>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>

                <div class="content-block"><a href="#" class="button button-fill button-raised btn-mine-change-password">确认修改</a></div>
                <div class="content-block">
                    <div class="list-block-label"></div>
                </div>
                <input name="checkCode" type="hidden" />
                <input name="personal.id" type="hidden" value="<%=loginCustomer.getId()%>" />
                <input id="_s" name="_s" type="hidden" value="4" />
            </form>


        </div>

        <div class="content-block">
            &nbsp;
        </div>

    </div>
</div>