// JavaScript Document
//
var formID = "form";
//Operator Field and TD
/*
 * 程序：李扬
 * 时间：2004-12-28
 * 说明：在form下新建一个隐藏域
 *       如果form中本来就包含于ID相同的域，则修改其值；
 */
function buildFormField(id, value,objOpener) {
	var form = objOpener.document.all[formID];
	if (typeof(form[id]) == "undefined") {
		var field = objOpener.document.createElement("input");
		field.name = id;
		field.id = id;
		field.type = "text";
		form.appendChild(field);
	}
	form[id].value = value;
}

function buildTdFromFormField(id, value) {
	var td = opener.document.all[id+"td"];
	if (Trim(value) == "") {
	  td.innerText = " ";
	}
	else {
	  td.innerText = value;
	}
}

/**
 * 程序：李扬
 * 时间：2005-10-25
 * 说明：从TD构造表单域
 */
function buildFormFieldFromTd(id,objOpener) {
  var form = objOpener.document.all[formID];
	var td = objOpener.document.all[id+"td"];
	if (typeof(form[id]) == "undefined") {
    var field = objOpener.document.createElement("input");
		field.type = "hidden";
		field.name = id;
		field.id = id;
		field.value = td.innerText;
		form.appendChild(field);
	}
	else {
	  form[id].value = td.innerText;
	}
}
//Call Common page
function openYj(id) {
  window.open("../Common/yj.jsp?id="+id,"YJ","width=550,height=300,left=250,top=250,scrollbars=auto");
}
function openBz(id) {
  window.open("../Common/bz.jsp?id="+id,"YJ","width=550,height=300,left=250,top=250,scrollbars=auto");
}
function openDdzx(id) {
  window.open("../Common/ddzx.jsp?id="+id,"DDZX","width=550,height=230,left=250,top=250,scrollbars=auto");
}
function openPz(pzrid, pzsjid, pzid) {
  window.open("../Common/pz.jsp?pzrid="+pzrid+"&pzsjid="+pzsjid+"&pzid="+pzid,"PZ","width=550,height=230,left=250,top=250,scrollbars=auto");
}
function openLz(workflowID, currNodeID) {
  window.open("../Common/lz.jsp?WorkflowID="+workflowID+"&CurrNodeID="+currNodeID,"LZ","width=550,height=300,left=250,top=250,scrollbars=auto");
}
function openZf(id) {
  window.open("../Common/zf.jsp","ZF","width=550,height=300,left=250,top=250,scrollbars=auto");
}

//Uitl
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