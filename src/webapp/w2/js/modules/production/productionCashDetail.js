/**
 * Created by 方斌杰
 * Date 2015-7-26
 */


// 总行数
var totalRows       = 0;
// 每页显示行数
var displayRows     = 50;
// 当前页
var currentPage     = 1;
// 要阵列的总页数
var pageCount       = 0;
// 列阵页数
var list            = [];
// 请求参数
var params          = {};


// 第一次加载数据
setParam('page', 1);
setParam('rows', displayRows);
load(true);
// 第一次进度转圈动画
easyPieChart();


/**
 * 添加请求参数
 * @param key
 * @param value
 */
function setParam(key, value) {
    if (key != "") {
        if(key == 'page') {
            if(value < 1) {
                value = 1;
            }
            currentPage = value;
        }
        if(key == "rows" && value < 1) {value = 1;}
        params[key] = value;
    }
}


function loadProduction(pageClean) {
    var id = fweb.getQueryString('productionWVO.id');
    var orderId= fweb.getQueryString('orderId');
    var customerId = fweb.getQueryString( 'loginUser.id');
    // 加载时是否将页数清空，跳转到第一页
    if(pageClean) {
        params['page'] = 1;
        currentPage = 1;
    }
    // 组装请求参数到 URL
    //var url = "/core/w2/production/orderQuery?productionWVO.id="+id+"&";
    var url = "/core/w2/production/orderQueryCash?productionWVO.id="+id+"&orderId="+orderId+"&";
    for(var param in params) {
        url += (param + "=" + params[param] + "&");
    }
    // 发起 POST 请求
    fweb.post(url.substr(0, url.length - 1), {}, function(response) {
        // 设置分页
        totalRows = response.returnValue['total'];
        //pagination();
        // 组装 HTML
        var rows = response.returnValue['rows'];
        var html = "";

        if(rows.length <= 0){
            html = "<tr><td colspan='7' align='center'>无数据</td></tr>";
        }else{
            for(var i = 0; i < rows.length; i ++) {

                html += "<tr height='30'>";

                var orderNum = rows[i]['orderNum'];
                html += "<td align='center'>" + orderNum + "</td>";

                var name = rows[i]['customerName'];
                //name = name.substring(0,1)+"**"+name.substring(name.length-1,name.length);
                html += "<td align='center'>" + name + "</td>";


                html += "<td align='center'><strong>￥<span class='money'>" + rows[i]['money'] + "</span></strong></td>";

                var status = rows[i]['status'];
                html += "<td align='center'>" + status + "</td>";

                var customerAttribute = rows[i]['customerAttribute'];
                html += "<td align='center'>" + customerAttribute + "</td>";

                var referralCode = rows[i]['referralCode'];
                html += "<td align='center'>" + referralCode + "</td>";

                var createTime = rows[i]['createTime'];
                html += "<td align='center'>" + createTime.substring(0,10) + "</td>";
                html += "</tr>";
            }
        }
        $('#productionCashList').html(html);

            // 转换货币格式
            $('.money').formatCurrency();
            // 进度转圈动画
            $('.chart').easyPieChart({
                size: 50,
                lineWidth: 3,
                barColor: '#c5acd2',
                trackColor: '#dddddd',
                scaleColor: '#ffffff',
                onStep: function (from, to, percent) {
                    $(this.el).find('.percent').text(Math.round(percent));
                }
            });
        });
}


function loadPaymentPlan(pageClean) {
    var id = fweb.getQueryString( 'productionWVO.id');
    var customerId = fweb.getQueryString( 'loginUser.id');
    var orderId= fweb.getQueryString('orderId');
    // 加载时是否将页数清空，跳转到第一页
    if(pageClean) {
        params['page'] = 1;
        currentPage = 1;
    }
    // 组装请求参数到 URL
    //var url = "/core/w2/production/orderQuery?productionWVO.id="+id+"&";
    var url = "/core/w2/production/paymentplanOrder?productionWVO.id="+id+"&orderId="+orderId+"&";
    for(var param in params) {
        url += (param + "=" + params[param] + "&");
    }
    // 发起 POST 请求
    fweb.post(url.substr(0, url.length - 1), {}, function(response) {
        // 设置分页
        totalRows = response.returnValue['total'];
        //pagination();
        // 组装 HTML
        var rows = response.returnValue['rows'];
        var html = "";

        if(rows.length <= 0){
            html = "<tr  height='30'><td colspan='8' align='center'>无数据</td></tr>";
        }else{
            for(var i = 0; i < rows.length; i ++) {

                html += "<tr height='30'>";

                var name = rows[i]['customerName'];
                //name = name.substring(0,1)+"**"+name.substring(name.length-1,name.length);
                html += "<td align='center'>" + name + "</td>";

                var type = rows[i]['type'];
                html += "<td align='center'>" + type + "</td>";

                var orderName = rows[i]['orderName'];
                html += "<td align='center'>" + orderName + "</td>";

                var paymentTime = rows[i]['paymentTime'];
                html += "<td align='center'>" + paymentTime.substring(0,10) + "</td>";

                var totalInstallment = rows[i]['totalInstallment'];
                html += "<td align='center'>" + totalInstallment + "</td>";

                var currentInstallment = rows[i]['currentInstallment'];
                html += "<td align='center'>" + currentInstallment + "</td>";
                var statusName = rows[i]['statusName'];
                html += "<td align='center'>" + statusName + "</td>";

                var totalPaymentMoney = rows[i]['totalPaymentMoney'];
                html += "<td align='center'>" + fweb.toFixed(totalPaymentMoney, 2) + "元</td>";

                var totalProfitMoney = rows[i]['totalProfitMoney'];
                html += "<td align='center'>" + fweb.toFixed(totalProfitMoney, 2) + "元</td>";

                //var state = rows[i]['state'];
                //html += "<td align='center'>" + state + "</td>";
                html += "</tr>";
            }
        }
        $('#paymentplanOrderList').html(html);

        // 转换货币格式
        $('.money').formatCurrency();
        // 进度转圈动画
        $('.chart').easyPieChart({
            size: 50,
            lineWidth: 3,
            barColor: '#c5acd2',
            trackColor: '#dddddd',
            scaleColor: '#ffffff',
            onStep: function (from, to, percent) {
                $(this.el).find('.percent').text(Math.round(percent));
            }
        });
    });
}
/**
 * 发起请求
 * @param pageClean
 */
function load(pageClean) {

    //加载产品信息跟订单信息
    loadProduction(pageClean);

    //加载兑付信息
    loadPaymentPlan(pageClean);

    //var id = fweb.getQueryString( 'productionWVO.id');
    //var customerId = fweb.getQueryString( 'loginUser.id');
    //// 加载时是否将页数清空，跳转到第一页
    //if(pageClean) {
    //    params['page'] = 1;
    //    currentPage = 1;
    //}
    //// 组装请求参数到 URL
    ////var url = "/core/w2/production/orderQuery?productionWVO.id="+id+"&";
    //var url = "/core/w2/production/orderQueryCash?productionWVO.id="+id+"&";
    //for(var param in params) {
    //    url += (param + "=" + params[param] + "&");
    //}
    //// 发起 POST 请求
    //fweb.post(url.substr(0, url.length - 1), {}, function(response) {
    //    // 设置分页
    //    totalRows = response.returnValue['total'];
    //    pagination();
    //    // 组装 HTML
    //    var rows = response.returnValue['rows'];
    //    var html = "";
    //
    //    if(rows.length <= 0){
    //        html = "<tr><td colspan='8' align='center'>无数据</td></tr>";
    //    }else{
    //        for(var i = 0; i < rows.length; i ++) {
    //
    //            html += "<tr height='30'>";
    //
    //            var orderNum = rows[i]['orderNum'];
    //            html += "<td align='center'>" + orderNum + "</td>";
    //
    //            var name = rows[i]['customerName'];
    //            name = name.substring(0,1)+"**"+name.substring(name.length-1,name.length);
    //            html += "<td align='left'>" + name + "</td>";
    //
    //            var productionName = rows[i]['productionName'];
    //            html += "<td align='center'>" + productionName + "</td>";
    //
    //            html += "<td align='center'><strong>￥<span class='money'>" + rows[i]['money'] + "</span></strong></td>";
    //
    //            var status = rows[i]['status'];
    //            html += "<td align='center'>" + status + "</td>";
    //
    //            var customerAttribute = rows[i]['customerAttribute'];
    //            html += "<td align='center'>" + customerAttribute + "</td>";
    //
    //            var referee = rows[i]['referee'];
    //            html += "<td align='center'>" + referee + "</td>";
    //
    //            var time = rows[i]['createTime'];
    //            html += "<td align='center'>" + time.substring(0, time.length - 2) + "</td>";
    //            html += "</tr>";
    //        }
    //    }
    //    $('#productionCashList').html(html);




        //var urlPaymentPlan = "/core/w2/production/paymentplanOrder?productionWVO.id="+id+"&";
        //for(var param in params) {
        //    urlPaymentPlan += (param + "=" + params[param] + "&");
        //}
        //// 发起 POST 请求
        //fweb.post(urlPaymentPlan.substr(0, urlPaymentPlan.length - 1), {}, function(response) {
        //    // 设置分页
        //    totalRows = response.returnValue['total'];
        //    pagination();
        //    // 组装 HTML
        //    var rows = response.returnValue['rows'];
        //    var html = "";
        //
        //    if(rows.length <= 0){
        //        html = "<tr><td colspan='8' align='center'>无数据</td></tr>";
        //    }else{
        //        for(var i = 0; i < rows.length; i ++) {
        //
        //            html += "<tr height='30'>";
        //
        //            var orderNum = rows[i]['orderNum'];
        //            html += "<td align='center'>" + orderNum + "</td>";
        //
        //            var name = rows[i]['customerName'];
        //            name = name.substring(0,1)+"**"+name.substring(name.length-1,name.length);
        //            html += "<td align='left'>" + name + "</td>";
        //
        //            var productionName = rows[i]['productionName'];
        //            html += "<td align='center'>" + productionName + "</td>";
        //
        //            html += "<td align='center'><strong>￥<span class='money'>" + rows[i]['money'] + "</span></strong></td>";
        //
        //            var status = rows[i]['status'];
        //            html += "<td align='center'>" + status + "</td>";
        //
        //            var customerAttribute = rows[i]['customerAttribute'];
        //            html += "<td align='center'>" + customerAttribute + "</td>";
        //
        //            var referee = rows[i]['referee'];
        //            html += "<td align='center'>" + referee + "</td>";
        //
        //            var time = rows[i]['createTime'];
        //            html += "<td align='center'>" + time.substring(0, time.length - 2) + "</td>";
        //            html += "</tr>";
        //        }
        //    }
        //    $('#paymentplanOrderList').html(html);


        //    // 转换货币格式
        //    $('.money').formatCurrency();
        //    // 进度转圈动画
        //    $('.chart').easyPieChart({
        //        size: 50,
        //        lineWidth: 3,
        //        barColor: '#c5acd2',
        //        trackColor: '#dddddd',
        //        scaleColor: '#ffffff',
        //        onStep: function (from, to, percent) {
        //            $(this.el).find('.percent').text(Math.round(percent));
        //        }
        //    });
        //});
}

/**
 * 当点击后样式发生变化（设为当前的）
 * @param element
 */
function current(element) {
    var id = element.id;
    var parent = element.parentNode;
    var hrefs = parent.getElementsByTagName("a");
    for(var i = 0; i < hrefs.length; i ++) {
        var href = hrefs[i];
        var hrefId = href.id;
        if(id == hrefId) {
            href.setAttribute("class", "current");
        } else {
            href.setAttribute("class", "");
        }
    }
}


/**
 * 设置起投金额
 * @param min
 * @param max
 * @param element
 */
function investMoney(min, max, element) {
    setParam('minMoney', min);
    setParam('maxMoney', max);
    current(element);
    load(true);
}


/**
 * 设置投资期限
 * @param min
 * @param max
 * @param element
 */
function expiringDay(min, max, element) {
    setParam('minExpiringDay', min);
    setParam('maxExpiringDay', max);
    current(element);
    load(true);
}


/**
 * 设置收益率
 * @param min
 * @param max
 * @param element
 */
function expectedYield(min, max, element) {
    setParam('minExpectedYield', min);
    setParam('maxExpectedYield', max);
    current(element);
    load(true);
}


/**
 * 进度转圈动画
 */
function easyPieChart() {
    $(function () {
        $('.chart').easyPieChart({
            size: 50,
            lineWidth: 3,
            barColor: '#c5acd2',
            trackColor: '#dddddd',
            scaleColor: '#ffffff',
            onStep: function (from, to, percent) {
                $(this.el).find('.percent').text(Math.round(percent));
            }
        });
    });
}


/**
 * 跳转页面
 * @param type
 * @param num
 */
function pre(){ turnPage('prefix', ''); }
function suf(){ turnPage('suffix', ''); }
function turn(num) { turnPage('', num) }
function turnPage(type, num) {
    // 往前
    if(type == "prefix"){
        if(currentPage <= 1) {
            currentPage = 1;
        } else {
            currentPage = currentPage - 1;
        }
        setParam("page", currentPage);
    }
    // 往后
    else if(type == "suffix") {
        if(currentPage >= pageCount) {
            currentPage = pageCount;
        } else {
            currentPage = currentPage + 1;
        }
        setParam("page", currentPage);
    }
    // 跳转指定页面
    else {
        currentPage = num;
        setParam("page", num);
    }
    load(false);
}


/**
 * 执行分页
 */
function pagination() {
    if (totalRows % displayRows == 0) {
        pageCount = parseInt(totalRows / displayRows);
    } else {
        pageCount = parseInt(totalRows / displayRows) + 1;
    }
    // 组装分页 HTML
    var html = "<li><a href='javascript:void(0);' aria-label='Previous' onclick='javascript:pre()'><span aria-hidden='true'>上一页</span></a></li>";
    for(var i = 0; i < pageCount; i ++) {
        html += "<li" + (currentPage == i + 1 ? " class='active' " : "") + "><a href='javascript:void(0);' onclick='javascript:turn(" + (i + 1) + ")'>" + (i + 1) + "</a></li>";
    }
    html += "<li><a href='javascript:void(0);' aria-label='Next' onclick='javascript:suf()'><span aria-hidden='true'>下一页</span></a></li>";
    $('#pagination').html(html);
}