/**
 * Created by 邓超
 * Date 2015-4-24
 */


// 总行数
var totalRows       = 0;
// 每页显示行数
var displayRows     = 8;
// 当前页
var currentPage     = 1;
// 要阵列的总页数
var pageCount       = 0;
// 列阵页数
var list            = [];
// 请求参数
var params          = {};


// 问好语句
$("#sayHello").html(sayHello());
// 第一次加载数据
setParam('page', 1);
setParam('rows', displayRows);
load(true);
// 第一次获取认证状态
getAuthStatus();
// 第一次进度转圈动画
easyPieChart();
// 获取最后一次登录时间
getLastLoginTime();


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
    // 加载时是否将页数清空，跳转到第一页
    if(pageClean) {
        params['page'] = 1;
        currentPage = 1;
    }
    // 组装请求参数到 URL
    var url = "/core/w2/production/mutilQuery?";
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
        for(var i = 0; i < rows.length; i ++) {
            //判断最大收益率是都等于最小收益率
            var ExpectedYield = "";
            if( rows[i]['maxExpectedYield']  ==  rows[i]['minExpectedYield']){
                ExpectedYield=  "<td align='center'><span style='color: red'>" +rows[i]['maxExpectedYield']+ "<span style='font-size:15px;'>%</span></span></td>";
            }//不相等
            else{
                ExpectedYield=  "<td align='center'><span style='color: red'>" +rows[i]['minExpectedYield']+ "</span><sapn style='font-size:15px;'>%</sapn> - " +rows[i]['maxExpectedYield']+ "<span style='font-size:15px;'>%</span></span></td>";
            }
            html += "<tr class='otherLine' onmousemove='changeTrColor(this)'>";
            html += "<td otherLine style='font-size:15px;'><a style='text-decoration: none; color: black;' href='/core/w2/production/productionDetail?productionWVO.id=" + rows[i]['id'] + "' target='_blank' class='t' style='text-decoration:none;color:#000000'>" + rows[i]['websiteDisplayName'] + "</a></td>"
            html += ExpectedYield;
            html += "<td align='center' style='font-size:15px;'>￥<span class='saleMoney'>" + rows[i]['size'] + "</span></td>";
            html += "<td align='center' style='font-size:15px;'>" + rows[i]['investTermView'] + "</td>";
            //计算销售进度

            //获取销售金额
            var saleMoney = rows[i]['saleMoney'];
            //获取配额
            var size = rows[i]['size'];
            //计算进度<span class='percent'>" +process + "</span>
            var process = parseInt(saleMoney) / parseInt(size)* 100;

            html += "<td align='center' style='font-size:15px;'>"+process.toFixed(2)+"%</td>";
            if(parseFloat(process.toFixed(2))>100 || rows[i]['stopTimeDay'] == "已结束") {
                html += "<td align='center'><span class='font-14'>已结束</span></td>";
                html += "<td width='160'><!--<button class='btns-over'>已售罄</button>-->已结束</td>";
            }else{
                html += "<td align='center'><span class='font-14'>" + rows[i]['stopTimeDay'] + "</span></td>";
                html += "<td align='center' style='font-size:15px;'><a href='/core/w2/production/productionDetail?productionWVO.id=" + rows[i]['id'] + "' class='a'>" +
                    "<button class='btn-352pk''>立即投资</button></a></td>";
            }


            html += "</tr>";
        }
        $('#productionList').html(html);
        // 转换货币格式
        $('.saleMoney').formatCurrency();
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
 * 获取客户的认证状态
 */
function getAuthStatus() {
    var html = "<img src='/core/w2/img/customer_assistor.png' style='width: 60px;height: 30px' backgtitle='您是 VIP 会员'>&nbsp;&nbsp;&nbsp;&nbsp;";
    var url = "/core/w2/customer/getAuthStatus";
    fweb.post(url, {}, function(data) {
        var response = data.returnValue;
        // 手机状态
        if(response.mobileStatus == 1) {
            html += '<a href="#" data-toggle="tooltip" data-placement="top" title="已验证手机"><img src="/core/w2/img/customer_mob2.png" style="width: 30px;height: 30px"/></a>&nbsp;&nbsp;&nbsp;&nbsp;';
        } else {
            html += '<a href="/core/w2/modules/customer/mobileValidate.jsp" data-toggle="tooltip" data-placement="top" title="请验证手机"><img src="/core/w2/img/customer_mob.png" style="width: 30px;height: 30px"/></a>&nbsp;&nbsp;&nbsp;&nbsp;'
        }
        // 身份状态
        if(response.accountStatus == 1) {
            html += '<a href="#"  data-toggle="tooltip" data-placement="top" title="已验证身份"><img src="/core/w2/img/customer_auth.png" style="width: 30px;height: 30px"/></a>&nbsp;&nbsp;&nbsp;&nbsp;';
        } else {
            html += '<a  href="/core/w2/customer/getCustomerCertificate" data-toggle="tooltip" data-placement="top" title="请验证身份"><img src="/core/w2/img/customer_auth2.png" style="width: 30px;height: 30px"/></a>&nbsp;&nbsp;&nbsp;&nbsp;';
        }
        // 邮件状态
//        if(response.emailStatus == 1) {
//            html += '<a  href="#" data-toggle="tooltip" data-placement="top" title="已验证邮箱"><img src="/core/w2/img/customer_em2.png" style="width: 30px;height: 30px"/></a>&nbsp;&nbsp;&nbsp;&nbsp;';
//        } else {
//            html += '<a href="/core/w2/modules/customer/emailAuth.jsp" data-toggle="tooltip" data-placement="top" title="请验证邮箱"><img src="/core/w2/img/customer_em.png" style="width: 30px;height: 30px"/></a>&nbsp;&nbsp;&nbsp;&nbsp;';
//        }
        $('#customerAuthStatus').html(html);
    })
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
 * 获取最后一次登录时间
 */
function getLastLoginTime() {
    var url = '/core/w2/customer/getLastLoginTime.action';
    fweb.post(url, {}, function(data){
        $('#lastLoginTime').html(data.returnValue.lastTime);
    })
}