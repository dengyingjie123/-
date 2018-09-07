function getNowTime(){
 now = new Date();
 date = now.getDate();
 month = now.getMonth()+1;
 year = now.getYear();
 hour = now.getHours();
 minute = now.getMinutes();
 second = now.getSeconds();
 strdate = year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
 return strdate;
}

function Trim(str) {
  return str.replace(/^\s*/g,"").replace(/\s*$/g,"");
}

function getQuery(name) {
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r!=null) return unescape(r[2]); return null;
}