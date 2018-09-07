/**
 * Created by 邓超
 * Date 2015-4-17
 * 一个针对网站的公共JS工具库
 */

/**
 * 日常工具库的各种方法
 */
var fweb = {

    /**
     * 格式化货币
     */
    toThousands : function(num) {
        return (num || 0).toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,');
    },

    /**
     * 发起一个 POST 请求
     * @param url
     * @param params
     * @param success
     * @param error
     */
    post: function (url, params, success, error) {
        $.ajax({
            type: "POST",
            url: url,
            processData: true,
            data: params,
            dataType: 'json',
            async: true,
            success: function (data) {
                if (fweb.checkIsNullObject(data)) {
                    alert("返回值错误");
                }
                success(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
//                fweb.alertJSON(XMLHttpRequest+"-"+textStatus+"-"+errorThrown);
                var message = textStatus + " " + errorThrown;
                error(message);
            }
        });
    },

    /**
     * 发起一个 GET 请求
     * @param url
     * @param params
     * @param success
     * @param error
     */
    get: function (url, params, success, error) {
        $.ajax({
            type: "GET",
            url: url,
            processData: true,
            data: params,
            dataType: 'json',
            success: success,
            error: error
        });
    },

    /**
     * 获取当前URL的指定参数
     * @param name
     * @returns {string}
     */
    getQueryString: function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) {
            return unescape(r[2]);
        } else {
            return "";
        }
    },

    /**
     * 计算指定时间的月差，参数的时间格式为：20150101
     * @param 1
     * @param date2
     * @returns {number}
     */
    getMonthDifference: function (date1, date2) {
        var year1 = date1.substr(0, 4);
        var year2 = date2.substr(0, 4);
        var month1 = date1.substr(4, 2);
        var month2 = date2.substr(4, 2);
        var len = (year2 - year1) * 12 + (month2 - month1);
        return len;
    },

    /**
     * 获取以 0000-00-00 格式的时间
     * @param date
     * @returns {string}
     */
    getDateText: function (date) {
        var Year = 0;
        var Month = 0;
        var Day = 0;
        var CurrentDate = "";
        Year = date.getFullYear();
        Month = date.getMonth() + 1;
        Day = date.getDate();
        CurrentDate = Year + "-";
        if (Month >= 10) {
            CurrentDate = CurrentDate + Month + "-";
        } else {
            CurrentDate = CurrentDate + "0" + Month + "-";
        }
        if (Day >= 10) {
            CurrentDate = CurrentDate + Day;
        } else {
            CurrentDate = CurrentDate + "0" + Day;
        }
        return CurrentDate;
    },

    /**
     * 获取以 0000-00-00 00:00:00 格式的时间
     * @param date
     * @returns {string}
     */
    getDateTimeText: function (date) {
        var Year = 0;
        var Month = 0;
        var Day = 0;
        var Hour = 0;
        var Minute = 0;
        var Second = 0;
        var CurrentDate = "";
        // 初始化时间
        Year = date.getFullYear();
        Month = date.getMonth() + 1;
        Day = date.getDate();
        Hour = date.getHours();
        Minute = date.getMinutes();
        Second = date.getSeconds();
        // 时间格式
        CurrentDate = Year + "-";
        if (Month >= 10) {
            CurrentDate = CurrentDate + Month + "-";
        } else {
            CurrentDate = CurrentDate + "0" + Month + "-";
        }
        if (Day >= 10) {
            CurrentDate = CurrentDate + Day;
        } else {
            CurrentDate = CurrentDate + "0" + Day;
        }
        if (Hour >= 10) {
            CurrentDate = CurrentDate + " " + Hour;
        } else {
            CurrentDate = CurrentDate + " 0" + Hour;
        }
        if (Minute >= 10) {
            CurrentDate = CurrentDate + ":" + Minute;
        } else {
            CurrentDate = CurrentDate + ":0" + Minute;
        }
        if (Second >= 10) {
            CurrentDate = CurrentDate + ":" + Second;
        } else {
            CurrentDate = CurrentDate + ":0" + Second;
        }
        return CurrentDate;
    },

    /**
     * 生成随机数
     * @returns {string}
     */
    getRandomNumber: function () {
        var today = new Date();
        var seed = today.getTime();
        seed = (seed * 9301 + 49297) % 233280;
        seed = seed / (233280.0);
        var number = seed * 5 + " ";
        number = number.replace(".", "").replace(" ", "");
        return number;
    },

    /**
     * 转换成浮点数
     * @param text
     * @returns {number}
     */
    getFloat: function (text) {
        if (this.checkIsTextEmpty(text)) {
            return 0.00;
        } else {
            var f = 0.00;
            try {
                f = parseFloat(text);
                return f;
            } catch (e) {
                return f;
            }
        }
    },

    /**
     * 获取 JSON 中某个属性的值
     * @param obj
     * @param key
     * @returns {*}
     */
    getJSONMemberValue: function (obj, key) {
        if (!this.checkIsNullObject(obj) && !this.checkIsNullObject(obj[key])) {
            return obj[key];
        }
        return null;
    },

    /**
     * 检测是否为空 JSON
     * @param json
     * @returns {*}
     */
    checkIsJsonEmpty: function (json) {
        return $.isEmptyObject(json);
    },

    /**
     * 判断字符是否为空
     * 参数为 null、""、undefined 都将认为是空
     */
    checkIsTextEmpty: function (text) {
        var value = $.trim(text);
        if (value != null && value != "undefined" && value != "") {
            return false;
        }
        return true;
    },

    /**
     * 过滤 SQL 关键字与敏感字符
     * @param text
     * @returns {string}
     */
    sqlReplace: function (text) {
        var newText = "";
        if (!this.checkIsTextEmpty(text)) {
            newText = text.replace("'", "''").replace("\"", "").replace("!", "！").replace("~", "").replace("$", "").replace("%", "").replace("^", "").replace("&", "").replace("*", "").replace("|", "").replace("--", "").replace("<", "").replace(">", "").replace("//", "").replace("delete", "").replace("drop", "").replace("select", "").replace("where", "").replace("from", "").replace("exec", "");
        }
        return newText;
    },

    /**
     * 检测是否为空对象
     * @param obj
     * @returns {boolean}
     */
    checkIsNullObject: function (obj) {
        if (obj != null && obj != "undefined") {
            return false;
        }
        return true;
    },

    /**
     * 获取当前时间
     * @returns {string}
     */
    getTimeNow: function () {
        var dt = new Date();
        var month = (dt.getMonth() + 1) < 10 ? '0' + (dt.getMonth() + 1) : (dt.getMonth() + 1);
        var date = dt.getDate() < 10 ? '0' + dt.getDate() : dt.getDate();
        var today = dt.getFullYear() + "-" + month + "-" + date + " " + dt.getHours() + ":" + dt.getMinutes() + ":" + dt.getSeconds();
        return today;
    },

    /**
     * 获取今天的日期
     * @returns {string}
     */
    getTimeToday: function () {
        var dt = new Date();
        var month = (dt.getMonth() + 1) < 10 ? '0' + (dt.getMonth() + 1) : (dt.getMonth() + 1);
        var date = dt.getDate() < 10 ? '0' + dt.getDate() : dt.getDate();
        var today = dt.getFullYear() + "-" + month + "-" + date;
        return today;
    },

    /**
     * 获取昨天的日期
     * @returns {string}
     */
    getTimeYesterday: function () {
        var now = new Date();
        var yesterday = new Date(now.getFullYear(), now.getMonth(), now.getDate() - 1);
        var month = (yesterday.getMonth() + 1) < 10 ? '0' + (yesterday.getMonth() + 1) : (yesterday.getMonth() + 1);
        var date = yesterday.getDate() < 10 ? '0' + yesterday.getDate() : yesterday.getDate();
        return yesterday.getFullYear() + "-" + month + "-" + date;
    },

    /**
     * 检测类型是否为 function
     * @param functionName
     * @returns {boolean}
     */
    checkIsFunction: function (functionName) {
        if (typeof(functionName) == 'function') {
            return true;
        }
        return false;
    },

    /**
     * 动态加载 JS 代码
     * @param url
     * @param callback
     *
     * url 可以是一个包含 Javascript 代码的 JSP 页面，或者是 Action，实现更加动态地获取 Javascript 的内容
     * 可以取代 easyUI 的 using 方法
     */
    loadScript: function (url, callback) {
        $.getScript(url, function () {
            if (this.checkIsFunction(callback)) {
                callback();
            }
        });
    },

    /**
     * 将 URL 进行编码
     * @param str
     * @returns {string}
     */
    urlEncode: function (str) {
        var ret = "";
        var strSpecial = "!\"#$%&’()*+,/:;<=>?[]^`{|}~%";
        var tt = "";
        for (var i = 0; i < str.length; i++) {
            var chr = str.charAt(i);
            var c = str2asc(chr);
            tt += chr + ":" + c + "n";
            if (parseInt("0x" + c) > 0x7f) {
                ret += "%" + c.slice(0, 2) + "%" + c.slice(-2);
            }
            else {
                if (chr == " ")
                    ret += "+";
                else if (strSpecial.indexOf(chr) != -1)
                    ret += "%" + c.toString(16);
                else
                    ret += chr;
            }
        }
        return ret;
    },

    /**
     * 保留指定位数的小数点
     * @param number
     * @param count
     */
    toFixed: function (number, count) {
        return number.toFixed(count);
    },

    /**
     * 删除特定的字符串
     * @param string
     * @param targetString
     */
    deleteString: function (string, replace) {
        var newString = "";
        for (var i = 0; i < string.length; i++) {
            if (string[i] != replace) {
                newString += string[i];
            }
        }
        return newString;
    },

    /**
     * 生成一个 UUID
     * @author 邓超
     * @returns {string}
     */
    genUUID: function () {
        var s = [];
        var hexDigits = "0123456789abcdef";
        for (var i = 0; i < 36; i++) {
            s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
        }
        s[14] = "4";
        s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);
        s[8] = s[13] = s[18] = s[23] = "-";
        var uuid = s.join("");
        return uuid;
    },

    /**
     * 弹出 JSON，以供调试
     * @param data
     */
    alertJSON: function (data) {
        alert(JSON.stringify(data));
    }

};
/**
 * 验证长度是否满足15位或18位
 * @param str
 * @returns {boolean}
 * @constructor
 */
function validateIdCard(idCard) {
    //15位和18位身份证号码的正则表达式
    var regIdCard = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;

    //如果通过该验证，说明身份证格式正确，但准确性还需计算
    if (regIdCard.test(idCard)) {
        if (idCard.length == 18) {
            var idCardWi = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); //将前17位加权因子保存在数组里
            var idCardY = new Array(1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2); //这是除以11后，可能产生的11位余数、验证码，也保存成数组
            var idCardWiSum = 0; //用来保存前17位各自乖以加权因子后的总和
            for (var i = 0; i < 17; i++) {
                idCardWiSum += idCard.substring(i, i + 1) * idCardWi[i];
            }

            var idCardMod = idCardWiSum % 11;//计算出校验码所在数组的位置
            var idCardLast = idCard.substring(17);//得到最后一位身份证号码

            //如果等于2，则说明校验码是10，身份证号码最后一位应该是X
            if (idCardMod == 2) {
                if (idCardLast == "X" || idCardLast == "x") {
                    return true;
                } else {
                    return false;
                }
            } else {
                //用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
                if (idCardLast == idCardY[idCardMod]) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    } else {
        return false;
    }
}