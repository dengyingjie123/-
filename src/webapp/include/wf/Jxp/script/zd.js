// JavaScript Document
////////////////////////////////
var arrayID = new Array();
function modifyTD(id, value) {
  isIn = false;
  for (i = 0; i < arrayID.length; i++) {
	  if (arrayID[i] == id) {
		  isIn = true;
			break;
		}
	}
	if (!isIn) {
    arrayID[arrayID.length] = id;
	}
  var td = document.all[id+"td"];
	if (Trim(value) == "") {
	  td.innerText = " ";
	}
	else {
	  if (Trim(td.innerText) == "") {
		  td.innerText = value;
		}
		else {
	    td.innerText += '\n'+ value;
		}
	}
}

/*
 * 程序：李扬
 * 时间：2004-12-28
 * 说明：在form下新建一个隐藏域
 *       如果form中本来就包含于ID相同的域，则修改其值；
 */
function buildOneField(id, value) {
	var form = document.form;
	if (typeof(document.form[id]) == "undefined") {
		var field = document.createElement("input");
		field.name = id;
		field.id = id;
		field.type = "hidden";
		form.appendChild(field);
	}
	document.form[id].value = value;
}

function buildField(id) {
  var form = document.form;
	var td = document.all[id+"td"];
	if (typeof(document.form[id]) == "undefined") {
    var field = document.createElement("input");
		field.type = "hidden";
		field.name = id;
		field.id = id;
		field.value = td.innerText;
		form.appendChild(field);
	}
	else {
	  document.form[id].value = td.innerText;
	}
}
//?????
function sp(id, isPZ) {
  if (typeof(document.form[id]) == "undefined") {
		var form = document.form;
		var field = document.createElement("input");
		field.name = id;
		field.id = id;
		field.type = "hidden";
		form.appendChild(field);
	}
	
  if (isPZ) {
	  //??
		document.form[id].value = "1";
	}
	else {
	//??
	  document.form[id].value = "0";
	}
}
function removeField(id) {
	var child = document.getElementById(id);
		if (child != null) {
			var parent = child.parentNode;
			if (parent != null) {
				parent.removeChild(child);
			}
			else {
				window.alert("无法找到对应ID的父节点");
			}
		}
		else {
			window.alert("无法找到对应节点");
		}
}
function addAllField() {
	for (i = 0; i < arrayID.length; i++) {
	  buildField(arrayID[i]);
  }
}
function removeAllField() {
	for (i = 0; i < arrayID.length; i++) {
	  removeField(arrayID[i]);
  }
}
function submitForm(form) {
  addAllField();
	form.submit();
}
function callPZTFDSJ(id) {
  var startTime = document.all.SQTDSJtd.innerText;
	var endTime = document.all.SQFDSJtd.innerText;
  window.open("pztfdsj.jsp?ID="+id+"&start="+startTime+"&end="+endTime,"","width=550,height=300,scrollbars=auto");
}

function modifyYJ(id) {
  window.open("yj.jsp?ID="+id,"","width=550,height=300");
}

function buildTime(id, value) {
//  document.form[id].value = value;
	buildOneField(id,value);
	id += "td";
	var td = document.all[id];
	td.innerText = value;
}

function resetTD(id) {
  var td = document.all[id+"td"];
	td.innerText = "";
}
//////////////////////////////////////// 
function callTZ(jxpID) {
  window.open("tz.jsp?ID="+jxpID,"","width=550,height=300");
}
function callTD(jxpID) {
  window.open("td.jsp?ID="+jxpID,"","width=550,height=300");
}
function callKG(jxpID) {
  window.open("kg.jsp?ID="+jxpID,"","width=550,height=300");
}
function callWG(jxpID) {
  window.open("wg.jsp?ID="+jxpID,"","width=550,height=300");
}
function callFD(jxpID) {
  window.open("fd.jsp?ID="+jxpID,"","width=550,height=300");
}
function callYQ(jxpID) {
  window.open("yq.jsp?ID="+jxpID,"","width=550,height=300");
}
function callBZ(jxpID) {
  window.open("bz.jsp?ID="+jxpID,"","width=550,height=300");
}
function modifyJXP() {
  var form = document.form;
	var YWID = form.YWID.value;
  window.open("modifyJxp.jsp?YWID="+YWID,"","width=600,height=450,scrollbars=yes");
}