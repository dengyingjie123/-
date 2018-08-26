/**
 * Created by 邓超
 * Date 2015-6-17
 */


// 第一次运行
loadAuthInfo();

// 认证信息
var authInfo = {};

//认证状态
var certificateStatus;
/**
 * 载入客户的认证信息
 */
function loadAuthInfo() {
    var url = '/core/w2/customer/getAuthenticationAndBankCardStatus';
    fweb.post(url, {}, function (data) {
        // 载入客户信息
        loadCustomerInfo();
        // 载入安全问题认证状态
        getQuestionAuth();
        // 载入最后一次登录时间
        getLastLoginTime();
        // 载入客户银行卡信息
        getBankCard();
        //判断是否实名和银行卡认证
        certificateStatus = data.returnValue;
        if (certificateStatus == 0) {
            setStatus('accountAuth', false);
            setOperation('accountAuth', '设置', '/core/w2/customer/getCustomerCertificate');
        } else if(certificateStatus == 1) {
            setStatus('accountAuth', false);
            setOperation('accountAuth', '绑定银行卡', "/core/w2/customer/getCustomerCertificate");
        } else {
            setStatus('accountAuth', true);
            setOperation('accountAuth', '修改', "/core/w2/customer/getCustomerCertificate");
        }
    })
}

/**
 * 载入客户的一般信息
 */
function loadCustomerInfo() {
    var url = '/core/w2/customer/infoSafly';
    fweb.post(url, {}, function (data) {
        //  fweb.alertJSON(data);
        // 手机信息
        var mobile = data.returnValue.rows[0].mobile;
        if (mobile != '') {
            $('#mobile').html(data.returnValue.rows[0].mobile);
            setStatus('mobile', true);
            setOperation('mobile', '修改', '/core/w2/modules/customer/mobileValidate.jsp');
        } else {
            setStatus('mobile', false);
            setOperation('mobile', '设置', '/core/w2/modules/customer/mobileValidate.jsp');
        }
       /* // 邮箱信息
        var email = data.returnValue.rows[0].email;
        if (email != '') {
            $('#email').html(data.returnValue.rows[0].email);
            // 检测是否已经认证
            var emailAuth = authInfo.emailStatus;
            if (emailAuth == 1) {
                setStatus('email', true);
                setOperation('email', '修改', '/core/w2/modules/customer/emailAuth.jsp');
            } else {
                setStatus('email', true);
                setOperation('email', '认证', '/core/w2/modules/customer/emailAuth.jsp');
            }
        } else {
            setStatus('email', false);
            setOperation('email', '设置', '/core/w2/modules/customer/emailAuth.jsp');
        }*/
        // 交易密码
        var transactionPassword = data.returnValue.rows[0].transactionPassword;
        if (transactionPassword == '1') {
            setStatus('transactionPassword', true);
            setOperation('transactionPassword', '修改', '/core/w2/customer/changeTransPassword');
        } else {
            setStatus('transactionPassword', false);
            setOperation('transactionPassword', '设置', '/core/w2/customer/changeTransPassword');
        }
    })
}

/**
 * 获取是否设置安全密保问题
 */
function getQuestionAuth() {
    var url = '/core/w2/customer/checkHasQuestions.action';
    fweb.post(url, {}, function (data) {
        if (data.returnValue.hasQuestions == 1) {
            setStatus('questions', true);
            setOperation('questions', '修改', '/core/w2/customer/requestProtectionQuestion');
        } else {
            setStatus('questions', false);
            setOperation('questions', '设置', '/core/w2/customer/requestProtectionQuestion');
        }
    })
}

/**
 * 获取最后一次登录时间
 */
function getLastLoginTime() {
    var url = '/core/w2/customer/getLastLoginTime.action';
    fweb.post(url, {}, function (data) {
        $('#lastLoginTime').html("上次登录时间: "+data.returnValue.lastTime);
    })
}

/**
 * 获取是否设添加银行卡
 */
function getBankCard() {
    var url = '/core/w2/customer/checkHasBankCard';
    fweb.post(url, {}, function (data) {
//        fweb.alertJSON(data);
        if (data.returnValue.hasBankCard == 1) {
            setStatus('bankCard', true);
            setOperation('bankCard', '管理', "/core/w2/customer/getBankCard");
        }else if(data.returnValue.hasBankCard==2){
            setStatus('bankCard', true);
            setOperation('bankCard', '管理', "/core/w2/customer/getBankCard");
        }
        else {
            setStatus('bankCard', false);
            setOperation('bankCard', '添加', "/core/w2/customer/toBankCard");
        }
    })
}

/**
 * 设置状态：改变账户管理的信息表格，第一列的值，指已设置或未设置
 * @param elementId 元素的 ID，这个元素是想改变的那一行的单元格 ID，结果改变的都是那一样的第一个单元格
 * @param status 传入 true 或者 false
 */
function setStatus(elementId, status) {
    var firstTd = document.getElementById(elementId).parentNode.getElementsByTagName('td')[0];
    if (status) {
        firstTd.innerHTML = "已设置";
    } else {
        firstTd.innerHTML = "未设置";
    }
}

/**
 * 设置操作：改变账户管理的信息表格，最后一列的值，支持添加多个操作
 * @param elementId
 * @param text
 * @param href
 */
function setOperation(elementId, text, href) {
    var parentNode = document.getElementById(elementId).parentNode;
    var childrenTds = parentNode.getElementsByTagName('td');
    childrenTds[childrenTds.length - 1].innerHTML = (childrenTds[childrenTds.length - 1].innerHTML + "<a href='" + href + "'>" + text + "</a>");
}