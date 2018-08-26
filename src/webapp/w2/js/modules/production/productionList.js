/**
 * Created by 邓超
 * Date 2015-5-7
 */

////初始化进度条
//document.addEventListener("DOMContentLoaded", function(event) {
//
//    var g1 = new JustGage({
//    id: "g1",
//    value: 20,
//    label: "购买进度",
//    min: 0,
//    max: 100,
//    showMinMax:false,
//    decimals: 0,
//    gaugeWidthScale: 0.4
//    });
//    });


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

        var lists=new Array();
        for(var i = 0; i < rows.length; i ++) {
            var ExpectedYield = "";
            if (rows[i]['maxExpectedYield'] == rows[i]['minExpectedYield']) {
                ExpectedYield = rows[i]['maxExpectedYield'] + "%";
            }//不相等
            else {
                ExpectedYield = rows[i]['minExpectedYield'] + "%" + "-" + rows[i]['maxExpectedYield'] + "%";
            }

            //获取销售金额
            var saleMoney = rows[i]['saleMoney'];
            //获取配额
            var size = rows[i]['size'];
            //计算进度
            var process = parseInt(saleMoney) / parseInt(size) * 100;

            var  surplusSize=parseInt(size)-parseInt(saleMoney);

            //投资期限
            var InvestTermView = rows[i]['investTermView'] == null ? "&nbsp;" : rows[i]['investTermView'];

            html += "<div class='prod-Panel'> "
            html += "         <div class='prodinfo'> "
            html += "             <div class='prod-left'> "
            html += "                 <span><a href='/core/w2/production/productionDetail?productionWVO.id=" + rows[i]['id'] + "' target='_blank' class='t'>" + rows[i]['websiteDisplayName'] + "</a></span> "
            html += "                 <p>产品总额：<span style='color: #fa6b0d;font-size: 18px;' class='productionTotalMoney'>" + rows[i]['size'] + "</span> 万</p> "
            html += "             </div> "
            html += "             <div class='prod-middle'> "
            html += "                 <ul> "
            html += "                     <li> "
            html += "                        <span style='color: #ff3f3f;font-size: 30px;'>" + ExpectedYield + "</span> "
            html += "                         <p>预期年化收益率</p> "
            html += "                     </li> "
            html += "                     <li> "
            html += "                        <span>" + InvestTermView + "</span> "
            html += "                         <p>投资期限</p> "
            html += "                     </li> "
            html += "                     <li> "
            html += "                        <span class='beginMoney'>"+rows[i]['sizeStart']+"元</span> "
            html += "                         <p>起投金额</p> "
            html += "                     </li> "
            html += "                     <li> "
            html += "<span class='f18p'>" + rows[i]['stopTimeDay'] + "</span> "
            html += "                         <p  class='last'>剩余时间</p> "
            html += "                     </li> "
            html += "                 </ul> "
            html += "             </div> "
            html += "             <div class='prod-right'> "
            html += "                     <div id='g"+i+"' class='progress_01'  style='margin-top: -10px;'></div> "

            html += "                 <div class='btn-buy'> "
            html += "                     <p> 剩余可投：<span class='surplusSize'>"+surplusSize+"</span></p> "
            if (parseFloat(process) >= 100 || rows[i]['stopTimeDay'] == '已结束') {
                html += "<button class='btns-over'>已结束</button>";
            } else {
                html += "<a href='/core/w2/production/productionDetail?productionWVO.id=" + rows[i]['id'] + "'class='btns-myprimary btn-buy120px' >立即投资 </a>";
            }
            html += "             </div> "
            html += "             </div> "
            html += " "
            html += "         </div> "
            html += "        </div> "

            var arr=new Array("g"+i,process);
            lists[i]=arr;
 }
        $('#productionList').html(html);
        // 转换货币格式
        $('.saleMoney').formatCurrency();
        $('.productionTotalMoney').formatCurrency();
        $('.beginMoney').formatCurrency();
        $('.surplusSize').formatCurrency();
        //初始化进度条
        $.each(lists,function(i,item){
            new JustGage({
                id:item[0],
                value: item[1],
                label: "购买进度",
                min: 0,
                max: 100,
                showMinMax: false,
                decimals: 0,
                gaugeWidthScale: 0.4
            });
        });
        // 进度转圈动画
//        $('.chart').easyPieChart({
//            size: 50,
//            lineWidth: 3,
//            barColor: '#c5acd2',
//            trackColor: '#dddddd',
//            scaleColor: '#ffffff',
//            onStep: function (from, to, percent) {
//                $(this.el).find('.percent').text(Math.round(percent));
//            }
//        });
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
function investType(min, element) {
    setParam('productType', min);
    current(element);
    load(true);
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