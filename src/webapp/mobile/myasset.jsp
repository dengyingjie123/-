<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<jsp:include page="./include/_head.jsp"></jsp:include>
<style>
    body{
        font-size: 0.75em;
    }
</style>
<div data-role="page" id="myasset" data-add-back-btn="true">
    
    <!--当前页面执行的js-->
    <script type="text/javascript" charset="utf-8">
        $(function(){
            $('.gou-show-page-loading-msg').on( "click",function() {
                var $that=this;
                $.ajax({
                    type: "POST",
                    url: "test.json",
                    data: {username:$("#username").val(), content:$("#content").val()},
                    dataType: "json",
                    beforeSend:function(){
                    var $this = $that,
                            theme = $this.jqmData( "theme" ) || $.mobile.loader.prototype.options.theme,
                            msgText = $this.jqmData( "msgtext" ) || $.mobile.loader.prototype.options.text,
                            textVisible = $this.jqmData( "textvisible" ) || $.mobile.loader.prototype.options.textVisible,
                            textonly = !!$this.jqmData( "textonly" );
                    html = $this.jqmData( "html" ) || "";
                    $.mobile.loading( "show", {
                        text: msgText,
                        textVisible: textVisible,
                        theme: theme,
                        textonly: textonly,
                        html: html
                    });
                },
                success: function(data){
                    $.mobile.loading( "hide" );
                    var $ulli=$('<li class="ui-li-static ui-body-inherit ui-first-child ui-last-child"><div class="ui-grid-b"><p style="font-size: 1em;border-bottom: 1px solid #CCC;padding-left: 20px;padding-bottom: 10px;"><a href="productionDetail.jsp" style="text-decoration: none;">已购买项目名字测试</a></p></div><div class="ui-grid-b"><div class="ui-block-a" style="border-right: 1px solid #EEE;"><div class="ui-grid-a"><div style="border-bottom: solid 1px #EEE;padding: 5px;font-size: 0.75em;">进度:<img src="./images/index/circle.png"></div><div><p>规模:<span>1000万</span></p></div></div></div><div class="ui-block-b" style="text-align: center;border-right: 1px solid #EEE;"> <p><span>产品期限：</span></p><div>90天</div><p></p></div><div class="ui-block-c" style="text-align: center;"><p>  <span>预期收益：</span></p><div>20万-40万</div><p></p></div></div></li>');
                    $('.GouXianMuXinXi').append($ulli);
                    $('.GouXianMuXinXi').listview("refresh");
                }
            });
            });
                $('.time-show-page-loading-msg').on( "click",function() {
                    var $this = $( this ),
                        theme = $this.jqmData( "theme" ) || $.mobile.loader.prototype.options.theme,
                        msgText = $this.jqmData( "msgtext" ) || $.mobile.loader.prototype.options.text,
                        textVisible = $this.jqmData( "textvisible" ) || $.mobile.loader.prototype.options.textVisible,
                        textonly = !!$this.jqmData( "textonly" );
                        html = $this.jqmData( "html" ) || "";
                    $.mobile.loading( "show", {
                            text: msgText,
                            textVisible: textVisible,
                            theme: theme,
                            textonly: textonly,
                            html: html
                    });
                    setTimeout(function(){
                        $.mobile.loading( "hide" );
                        var $ulli=$('<li class="ui-li-static ui-body-inherit ui-first-child ui-last-child"><div class="ui-grid-b"><p style="font-size: 1em;border-bottom: 1px solid #CCC;padding-left: 20px;padding-bottom: 10px;"><a href="productionDetail.jsp" style="text-decoration: none;">将到期项目名字测试</a></p></div><div class="ui-grid-b"><div class="ui-block-a" style="border-right: 1px solid #EEE;"><div class="ui-grid-a"><div style="border-bottom: solid 1px #EEE;padding: 5px;font-size: 0.75em;">进度:<img src="/images/index/circle.png"></div><div><p>规模:<span>1000万</span></p></div></div></div><div class="ui-block-b" style="text-align: center;border-right: 1px solid #EEE;"> <p><span>产品期限：</span></p><div>90天</div><p></p></div><div class="ui-block-c" style="text-align: center;"><p>  <span>预期收益：</span></p><div>20万-40万</div><p></p></div></div></li>');
                        $('.TimeXianMuXinXi').append($ulli);
                        $('.TimeXianMuXinXi').listview("refresh");
                    },2000);

                });
            $(".knob").knob();
            $('.knob').closest('.ui-body-inherit').removeClass('ui-input-text ui-body-inherit ui-corner-all ui-shadow-inset').css({'display':'inline-block','margin-left':'0.3em'});
            $('#topHeadText').html('我的资产');
        });

    </script>
	<jsp:include page="./include/_topNav.jsp"></jsp:include>
	<!-- 首页主要页面加载区 -->
	<div role="main" class="ui-content">
		<div style="border: 1px solid #EEE;">

            <div class="ui-grid-a" style="text-align:center;margin-top:1.8em;padding-bottom:1.8em;font-size:3em;color:red;border-bottom: 1px solid #EEE;">
                今日收益:
                <span style="font-size:1.5em;">${customerEarningVO.dayEarning}</span>
            </div><!-- 定义头部基本说明 -->
            
            <div class="ui-grid-a">
                <div class="ui-block-a" style="border-right: 1px solid #EEE;text-align: center;padding: 0.75em 0em;font-size:3em;">
                    平均：<span>${customerEarningVO.average}%</span>
                </div>
                <div class="ui-block-b" style="text-align: center;padding: 0.75em 0em;font-size:3em;">
                    总<span>${customerEarningVO.totalAssets}</span>万
                </div>
            </div>
            <!--定义项目列表-->
            <div style="padding:0.75em;border-top: 1px solid #EEE;">
                <ul id="myTab" class="nav nav-pills" role="tablist">
                  <li class="active" style="width:50%;">
                      <a href="#buy" role="tab" data-toggle="tab" style="font-weight:100;font-size:1.4em;">已购买</a>
                  </li>
                  <li style="width:50%;margin-left:0px;">
                      <a href="#profile" role="tab" data-toggle="tab" style="font-weight:100;font-size:1.4em;">将到期</a>
                  </li>
                </ul>
                <div id="myTabContent" class="tab-content">
                  <div class="tab-pane fade active in" id="buy">
                        <ul data-role="listview" class="ui-listview GouXianMuXinXi" style="margin:0px;">
                        <s:iterator value="products">
                            <li class="ui-li-static ui-body-inherit">
                                <div class="ui-grid-b">
                                    <p style="font-size: 2em;border-bottom: 1px solid #CCC;padding-bottom: 10px;">
                                        <a href="mobile/Index_product.action?sid=<s:property value="sid"/>" style="text-decoration: none;" class="ProductTitle" data-ajax="false">
                                            <s:property value="name"/>
                                        </a>
                                    </p>
                                </div>
                                <div class="ui-grid-b">
                                    <div class="ui-block-a" style="border-right: 1px solid #EEE;">
                                        <div class="ui-grid-a">
                                            <div style="border-bottom: solid 1px #EEE;padding: 5px;font-size: 2em;">
                                                进度:<input class="knob" data-width="25" data-height="25" data-displayinput="false" data-thickness=.3 data-readOnly="true" value="<s:property value="progress"/>"/>
                                            </div>
                                            <div>
                                                <p style="font-size:2em;">
                                                    规模:<span><s:property value="size"/>万</span>
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="ui-block-b" style="text-align: center;border-right: 1px solid #EEE;">
                                        <p style="font-size:2em;">
                                            产品期限：<span style="display: block;"><s:property value="timeLimit"/></span>
                                        </p>
                                        <p></p>
                                    </div>
                                    <div class="ui-block-c" style="text-align: center;">
                                        <p style="font-size:2em;">
                                            预期收益：<span style="display: block;"><s:property value="lowestYield"/>-<s:property value="highestYield"/></span>
                                        </p>
                                        <p></p>
                                    </div>
                                </div>
                            </li>
                        </s:iterator>

                    </ul>
                    <div style="text-align:center;width:100%;margin-top:30px;">
                        <button class="ui-btn ui-icon-refresh ui-btn-icon-left gou-show-page-loading-msg " data-textonly="false" data-textvisible="true" data-msgtext="正在加载" data-inline="true">加载更多</button>
                    </div>
                  </div>
                  <div class="tab-pane fade " id="profile">
                    <ul data-role="listview" class="ui-listview TimeXianMuXinXi" style="margin:0px;">
                        <s:iterator value="willExpireProducts">
                            <li class="ui-li-static ui-body-inherit">
                                <div class="ui-grid-b">
                                    <p style="font-size: 2em;border-bottom: 1px solid #CCC;padding-bottom: 10px;">
                                        <a href="mobile/Index_product.action?sid=<s:property value="sid"/>" style="text-decoration: none;" class="ProductTitle" data-ajax="false">
                                            <s:property value="name"/>
                                        </a>
                                    </p>
                                </div>
                                <div class="ui-grid-b">
                                    <div class="ui-block-a" style="border-right: 1px solid #EEE;">
                                        <div class="ui-grid-a">
                                            <div style="border-bottom: solid 1px #EEE;padding: 5px;font-size: 2em;">
                                                进度:<input class="knob" data-width="25" data-height="25" data-displayinput="false" data-thickness=.3 data-readOnly="true" value="<s:property value="progress"/>"/>
                                            </div>
                                            <div>
                                                <p style="font-size:2em;">
                                                    规模:<span><s:property value="size"/>万</span>
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="ui-block-b" style="text-align: center;border-right: 1px solid #EEE;">
                                        <p style="font-size:2em;">
                                            产品期限：<span style="display: block;"><s:property value="timeLimit"/></span>
                                        </p>
                                        <p></p>
                                    </div>
                                    <div class="ui-block-c" style="text-align: center;">
                                        <p style="font-size:2em;">
                                            预期收益：<span style="display: block;"><s:property value="lowestYield"/>-<s:property value="highestYield"/></span>
                                        </p>
                                        <p></p>
                                    </div>
                                </div>
                            </li>
                        </s:iterator>
                    </ul>

                    <div style="text-align:center;width:100%;margin-top:30px;">
                        <button class="ui-btn ui-icon-refresh ui-btn-icon-left time-show-page-loading-msg" data-textonly="false" data-textvisible="true" data-msgtext="正在加载" data-inline="true">加载更多</button>
                    </div>

                  </div>
                </div> 
            </div>
	   </div>
    </div>
	<jsp:include page="./include/_panel.jsp"></jsp:include>
</div>