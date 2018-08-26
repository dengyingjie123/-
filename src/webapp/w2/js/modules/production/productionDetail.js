/**
 * Created by zhouhaihong on 2015/9/30.
 */
/**
 * Created by 刘雪冬
 * Date 2015-7-9
 */


// 总行数
var totalRows       = 0;
// 每页显示行数
var displayRows     = 5;
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
//easyPieChart();


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


/**
 * 发起请求
 * @param pageClean
 */
function load(pageClean) {
    var id = fweb.getQueryString( 'productionWVO.id');
    // 加载时是否将页数清空，跳转到第一页
    if(pageClean) {
        params['page'] = 1;
        currentPage = 1;
    }
    // 组装请求参数到 URL
    var url = "/core/w2/production/orderQuery?productionWVO.id="+id+"&";
    for(var param in params) {
        url += (param + "=" + params[param] + "&");
    }
    // 发起 POST 请求
    fweb.post(url.substr(0, url.length - 1), {}, function(response) {
        // 设置分页
        totalRows = response.returnValue['total'];
        pagination();
        // 组装 HTML
        var rows = response.returnValue['rows'];
        var html = "";

        if(rows.length <= 0){
            html = "<tr><td colspan='3' align='center'>无数据</td></tr>";
        }else{
            for(var i = 0; i < rows.length; i ++) {
                var name = rows[i]['customerName'];
                html += "<tr height='30'>";
                html += "<td align='left'>" + name + "</td>";
                html += "<td align='center'><strong>￥<span class='money'>" + rows[i]['money'] + "</span></strong></td>";
                var time = rows[i]['createTime'];
                html += "<td align='center'>" + time.substring(0, time.length - 2) + "</td>";
                html += "</tr>";
            }
        }
        $('#orderList').html(html);

        // 进度转圈动画
       /* $('.chart').easyPieChart({
            size: 50,
            lineWidth: 3,
            barColor: '#c5acd2',
            trackColor: '#dddddd',
            scaleColor: '#ffffff',
            onStep: function (from, to, percent) {
                $(this.el).find('.percent').text(Math.round(percent));
            }
        });*/
    });
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

function formatMoney() {
    var investMoney = $('#investMeney').val();
    if (parseInt(investMoney) > 0 || parseInt(investMoney) < 0) {
        $('#investMeney').val(parseInt(investMoney));
    } else {
        $('#investMeney').val(0);
    }
    jsInvest();
}

function investMoney() {
    var verifyMoney = document.getElementById("verifyMoney");
    verifyMoney.style.visibility = "hidden";
}

function invest(minMoney,restInvestMoney,incomeType) {
    if(incomeType == 0) {
        // 提交对按钮进行控制
        $('#investSubmmitButtons').attr('disabled','disabled');
        $('#investSubmmitButtons').val("正在投资");
        if (jsInvest()) {
            var productionWVOId = $('#productionWVOId').val();
            var investMeney = $('#investMeney').val();
            if (parseInt(investMeney) < minMoney) {
                popStatus(4, "金额小于起投金额", 2);
                $('#investSubmmitButtons').removeAttr('disabled');
                $('#investSubmmitButtons').val("投资");
                return;
            }
            // 判断投资的金额是否大于可投金额
            if(parseInt(investMeney) > parseInt(restInvestMoney)){
                popStatus(4, "金额大于剩余可投金额", 2);
                $('#investSubmmitButtons').removeAttr('disabled');
                $('#investSubmmitButtons').val("投资");
                return;
            }
            $('#investForm').submit();
        }
    } else {
        openAppointmentWindow();
    }

}
function jsInvest() {
    var investMoney = $('#investMeney').val();
    var availableMoney = $('#AvailableMoney').val();
    var productionWVOSizeStart = $('#productionWVOSizeStart').val();
    var productionWVOSizeStop = $('#productionWVOSizeStop').val();
    var verifyMoney = document.getElementById("verifyMoney");
    if(investMoney == "" || investMoney == undefined){
        popStatus(2, "请输入投资金额", 2);
        $('#investSubmmitButtons').removeAttr('disabled');
        $('#investSubmmitButtons').val("投资");
        return false;
    }
    if (parseInt(investMoney) < parseInt(productionWVOSizeStart)) {
        verifyMoney.style.visibility = "visible";
        verifyMoney.innerHTML = "金额小于起投金额";

        $('#investSubmmitButtons').removeAttr('disabled');
        $('#investSubmmitButtons').val("投资");
        return false;
    }
    if (investMoney == '' || investMoney == null) {
        $('#investMeney').val(0);
        return false;
    }
    if (parseInt(investMoney) > parseInt(productionWVOSizeStop)) {
        verifyMoney.style.visibility = "visible";
        verifyMoney.innerHTML = "金额超过最大投资金额";
        $('#investSubmmitButtons').removeAttr('disabled');
        $('#investSubmmitButtons').val("投资");
        return false;
    }
    return true;
}

// 打开合格投资者确认窗口
function openAgreementWindow() {
    $('.simu_wrap').show('slow');
}

// 打开预约窗口
function openAppointmentWindow() {
    $('.customer-phone').show('slow');
}

// 关闭合格投资者确认窗口，如果勾选了“记住选择”，则添加 cookie，使其不再弹窗
function closeAgreementWindow() {
    if(document.getElementById('select_check').checked == true) {
        setCookie('isInvestor', 'true');
    }
    $('.simu_wrap').hide('slow');
}

// 关闭预约窗口
function closeAppointmentWindow() {
    $('.customer-phone').hide('slow');
}