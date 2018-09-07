/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-1-19
 * Time: 下午10:07
 * To change this template use File | Settings | File Templates.
 */
var WEB_ROOT = "/core";

/**
 * null或undefined判断
 */
function checkNull(value) {
    var result = false;
    if (value != null && String(value) != "null" && String(value) != "undefined") {
        result = true;
    }
    return result;
}

/**
 * 打开来电显示窗体
 */
function call(callnumber){
    var obj = window;
    obj.callnumber = callnumber;
    window.open(WEB_ROOT+"/modules/callcenter/CallCenter_IncomingCallDisplay.jsp","","toolbar=no,status=no,resizable=yes,width=1200px,height=650px,alwaysRaised=yes,location =no,menubar=no,resizable=no,z-look=yes");
}