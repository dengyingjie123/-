
/*
 * JS 的 Cookie 操作工具
 */

// 添加 Cookie
function setCookie(key, value) {
    if(key.size == 0 || value.size == 0) {
        alert('参数不完整');
        return ;
    }
    document.cookie  = key + '=' + value;
}

// 读取 Cookie
function getCookie(c_name) {
    if (document.cookie.length > 0) {
        var c_start = document.cookie.indexOf(c_name + "=");
        if (c_start != -1){
            c_start = c_start + c_name.length+1;
            c_end = document.cookie.indexOf(";",c_start);
            if (c_end == -1) {
                c_end = document.cookie.length
            }
            return unescape(document.cookie.substring(c_start, c_end));
        }
    }
    return ""
}