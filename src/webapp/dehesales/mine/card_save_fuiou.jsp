<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.KVObjects" %>
<%@ page import="com.youngbook.common.KVObject" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.mchange.v1.util.StringTokenizerUtils" %>
<%@ page import="com.youngbook.common.utils.StringUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div data-page="card-save" class="page">
    <div class="navbar">
        <div class="navbar-inner navbar-ph">
            <div class="left"><a href="#" class="back link"> <i class="icon-white icon-back"></i><span></span></a></div>
            <div class="center sliding">银行卡信息维护</div>
        </div>
    </div>

    <div class="page-content">


        <div class="content-block">
            为了保证投资者合法权益，确保客户资金同卡进出，系统仅支持一个客户绑定一张银行卡。<br>
            若需要更改已绑定的银行卡，则需要提供如下资料：<br>
            1. 提供身份证正反面、新银行卡照片<br>
            2. 提供本人与身份证、新银行卡的合照<br>
            3. 若是因原银行卡丢失换卡，需提供原银行卡的挂失证明或原银行卡的交易流水；若是因不支持银行卡快捷支付或其他情况换卡，需提供原银行卡照片或原银行卡的交易流水<br>
            如上资料发送至邮箱：service@kepler.studio，我们会尽快审核处理。
        </div>

        <div class="content-block">
            &nbsp;
        </div>

    </div>
</div>