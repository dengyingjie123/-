/**
 * Created by zhouhaihong on 2015/9/29.
 * 登陆脚本
 */
var flag = false;
window.onload = function onLoginLoaded() {
    if (isPostBack == "False") {
        GetLastUser();
    }
}
function GetLastUser() {
    var id = "49BAC005-7D5B-4231-8CEA-16939BEACD67";
    var usr = GetCookie(id);
    if (usr != null) {
        document.getElementById('personalName').value = usr;
    }
}
//点击登录时触发客户端事件
function SetPwdAndChk() {
   /* //取用户名
    var usr = document.getElementById('personalName').value;

    //如果记住密码选项被选中
    if (document.getElementById('rememberPwd').checked == true) {
        //将最后一个用户信息写入到Cookie
        SetLastUser(usr);
        //取密码值
        var expdate = new Date();
        expdate.setTime(expdate.getTime() + 7 * (24 * 60 * 60 * 1000));
        //将用户名和密码写入到Cookie
    }
    else {
        //如果没有选中记住密码,则立即过期
        ResetCookie();
    }*/
}
function SetLastUser(usr) {
    var id = "49BAC005-7D5B-4231-8CEA-16939BEACD67";
    var expdate = new Date();
    //当前时间加上两周的时间
    expdate.setTime(expdate.getTime() + 7 * (24 * 60 * 60 * 1000));
    SetCookie(id, usr, expdate);
}

var oldValue = '';

//取Cookie的值
function GetCookie(name) {
    var arg = name + "=";
    var alen = arg.length;
    var clen = document.cookie.length;
    var i = 0;
    while (i < clen) {
        var j = i + alen;
        if (document.cookie.substring(i, j) == arg)
            return getCookieVal(j);
        i = document.cookie.indexOf(" ", i) + 1;
        if (i == 0) break;
    }
    return null;
}
var isPostBack = "False";
function getCookieVal(offset) {
    var endstr = document.cookie.indexOf(";", offset);
    if (endstr == -1)
        endstr = document.cookie.length;
    return unescape(document.cookie.substring(offset, endstr));
}
//写入到Cookie
function SetCookie(name, value, expires) {
    var argv = SetCookie.arguments;
    //本例中length = 3
    var argc = SetCookie.arguments.length;
    var expires = (argc > 2) ? argv[2] : null;
    var path = (argc > 3) ? argv[3] : null;
    var domain = (argc > 4) ? argv[4] : null;
    var secure = (argc > 5) ? argv[5] : false;
    document.cookie = name + "=" + escape(value) +
    ((expires == null) ? "" : ("; expires=" + expires.toGMTString())) +
    ((path == null) ? "" : ("; path=" + path)) +
    ((domain == null) ? "" : ("; domain=" + domain)) +
    ((secure == true) ? "; secure" : "");
}
function ResetCookie() {
    var usr = document.getElementById('personalName').value;
    var expdate = new Date();
    SetCookie(usr, null, expdate);
}
// 获取前一个地址
var prefixPath = document.referrer;
$('#prefixPath').val(prefixPath);

// 检查是否输入用户名
function checkLoginName() {
    $('#personalNameTip').text('');
    if ($('#personalName').val() == '') {
        $('#personalNameTip').text('请输入用户名');
        flag = false;
    }
    else {
        flag = true;
    }
}
// 检查是否输入密码
function checkPassword() {
    $('#personalPasswordTip').text('');
    if ($('#personalPassword').val() == '') {
        $('#personalPasswordTip').text('请输入密码');
        flag = false;
    }
    else {
        flag = true;
    }
}
// 检查是否输入验证码
function checkCaptcha() {

    if ($('#captcha').val() == '') {
        $('#codeTip').text('请输入验证码');
        flag = false;
    }
    else {
        flag = true;
        $('#codeTip').text('');
    }
}

//标记一个变量，判断键盘多次点击
var isClick = false;
function butOnClick() {
    if (event.keyCode == 13) {
        if (!isClick) {
            isClick = true;  //改为true if判断就进不去
            submitForm();
        }
    }
}

// 检测并提交表单
function submitForm() {
    checkLoginName();
    checkPassword();
    checkCaptcha();
    if (flag) {
        $('#login').attr('disabled', 'disabled');
        popStatus(3, "正在登录，请稍候", 1, null, null);
        SetPwdAndChk();
        var isOk = true;
        $('#personalPasswordTip').text('');
        if (isOk) {
            var password = fw.getMD5($('#personalPassword').val());
            var params = {
                'loginName': $("#personalName").val(),
                'password': password,
                'captcha': $('#captcha').val(),
                'u': $('#u').val(),
                'rememberPwd': $('#rememberPwd').val()
            }
            var url = "/core/w2/customer/LoginDo";
            fweb.post(url, params, function (data) {
                var message = data.message;
                var data = data.returnValue;
                if (data == "0") {
                    popStatus(4, "请重新输入用户名和密码", 1, null, null);
                    $('#login').removeAttr('disabled');
                    isClick = false;
                } else if (data == "2") {
                    popStatus(2, "验证码错误，请重新输入", 1, null, null);
                    $('#login').removeAttr('disabled');
                    isClick = false;
                    resetRefresh();
                } else if (data == "3") {
                    popStatus(2, "该用户未注册", 1, null, null);
                    $('#login').removeAttr('disabled');
                    isClick = false;
                } else if (data == "5") {
                    popStatus(2, "登录密码错误次数上限，请5分钟后登录", 1, null, null);
                    $('#login').removeAttr('disabled');
                    isClick = false;
                    resetRefresh();
                }
                else if (data == "1") {
                    popStatus(1, "登录成功，正在进入首页", 1, null, null);
//                        //判断用户的手机是否经认证
//                        window.location = "/core/w2/customer/getMobilesStatus";
                    window.location = "/core/w2/customer/IndexShow";
                } else if (data == '4') {
                    popStatus(2, "用户名和密码不匹配，请重新输入", 1, null, null);
                    $('#login').removeAttr('disabled');
                    isClick = false;
                }
                else {
                    popStatus(2, message, 1, null, null);
                    $('#login').removeAttr('disabled');
                    isClick = false;
                }
            });
        }
    }
    else {
        checkLoginName();
        checkPassword();
        checkCaptcha();
    }
}
function resetRefresh() {
    var codeId = document.getElementById('codeId');
    onClickRefresh(codeId, 1);
}