/**
 * Created by 邓超
 * Date 2015-4-24
 */


// 总行数
var totalRows = 0;
// 每页显示行数
var displayRows = 8;
// 当前页
var currentPage = 1;
// 要阵列的总页数
var pageCount = 0;
// 列阵页数
var list = [];
// 请求参数
var params = {};


// 问好语句
$("#sayHello").html(sayHello());
// 获取客户信息
loadInfo();
// 加载数据
setParam('page', 1);
setParam('rows', displayRows);
load(true);


/**
 * 添加请求参数
 * @param key
 * @param value
 */
function setParam(key, value) {
    if (key != "") {
        if (key == 'page') {
            if (value < 1) {
                value = 1;
            }
            currentPage = value;
        }
        if (key == "rows" && value < 1) {
            value = 1;
        }
        params[key] = value;
    }
}


/**
 * 发起请求
 * @param pageClean
 */
function load(pageClean) {
    // 加载时是否将页数清空，跳转到第一页
    if (pageClean) {
        params['page'] = 1;
        currentPage = 1;
    }
    // 组装请求参数到 URL
    var url = "/core/w2/customer/listMoneyLog?";
    for (var param in params) {
        url += (param + "=" + params[param] + "&");
    }
    // 发起 POST 请求
    fweb.post(url.substr(0, url.length - 1), {}, function (response) {
        // 设置分页
        totalRows = response.returnValue['total'];
        pagination();
        // 组装 HTML
        var rows = response.returnValue['rows'];
        var html = "";
        if (rows.length > 0) {
            var time = "";
            for (var i = 0; i < rows.length; i++) {
                time = rows[i]['operateTime'];
                html += "<tr>";
                html += "<td>" + time.substring(0,19) + "</td>"
                html += "<td align='center'>" + rows[i]['type'] + "</td>";
                html += "<td align='center'><span class='saleMoney'>" + rows[i]['content'] + "</span></td>";
                html += "<td align='center'>" + rows[i]['status'] + "</td>";
                html += "<td></td>";
                html += "</tr>";
            }
        } else {
            html += "<tr>";
            html += "<td align='center' colspan='5'>没有任何资金记录</td>"
            html += "</tr>";
        }

        $('#moneyLogList').html(html);
        // 转换货币格式
        $('.saleMoney').formatCurrency();
    });
}


/**
 * 跳转页面
 * @param type
 * @param num
 */
function pre() {
    turnPage('prefix', '');
}
function suf() {
    turnPage('suffix', '');
}
function turn(num) {
    turnPage('', num)
}
function turnPage(type, num) {
    // 往前
    if (type == "prefix") {
        if (currentPage <= 1) {
            currentPage = 1;
        } else {
            currentPage = currentPage - 1;
        }
        setParam("page", currentPage);
    }
    // 往后
    else if (type == "suffix") {
        if (currentPage >= pageCount) {
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
    var html = "<span id='pre'><a href='javascript:void(0);' aria-label='Previous' onclick='javascript:pre()'>上一页</a></span>";
    for (var i = 0; i < pageCount; i++) {
        html += "<span " + (currentPage == i + 1 ? "class='current'" : "class='number'") + "><a href='javascript:void(0);' onclick='javascript:turn(" + (i + 1) + ")'>" + (i + 1) + "</a></span>";
    }
    html += "<span id='next'><a href='javascript:void(0);' aria-label='Next' onclick='javascript:suf()'>下一页</a></span>";
    $('#pagination').html(html);
}



/**
 * 通过不用的时间点显示不同的问候语
 * @returns {*}
 */
function sayHello() {
    var date = new Date();
    var hour = date.getHours();
    if (hour > 4 && hour <= 10) {
        return "早上好！";
    } else if (hour > 10 && hour <= 12) {
        return "中午好！";
    } else if (hour > 12 && hour <= 18) {
        return "下午好！";
    } else if (hour > 18 && hour <= 23) {
        return "晚上好！";
    } else {
        return "您好！"
    }
}


/**
 * 获取客户信息
 */
function loadInfo() {
    var url = "/core/w2/customer/info.action";
    fweb.post(url, {}, function (response) {
        if (response.returnValue != undefined) {
            var data = response.returnValue.rows[0];
            $('#customerName').html(data.name);
            $('#totalMoney').html(parseInt(data.availableMoney) + parseInt(data.frozenMoney));
            $('#availableMoney').html(parseInt(data.availableMoney));
            $('#frozenMoney').html(parseInt(data.frozenMoney));
        }
        $('#totalMoney').formatCurrency();
        $('#availableMoney').formatCurrency();
        $('#frozenMoney').formatCurrency();
    });
};