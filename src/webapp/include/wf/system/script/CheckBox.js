/********************************************************************/

//通过一个按钮，选择所有同样前缀名的复选框
function checkAll(form, selectedStatus, CheckBoxName) {
  for (var i = 0; i < form.elements.length; i++) {
    var element = form.elements[i];
    if ((element.name.indexOf(CheckBoxName) >= 0 ||
			  element.name.indexOf("SELECTALL") >= 0) && element.disabled == false) {
      element.checked = selectedStatus;
    }
  }
}

//检查是否至少选择了一个复选框
function isCheckedAtLeastOne(form, CheckBoxName) {
  for (var i = 0; i < form.elements.length; i++) {
    var element = form.elements[i]
    if (element.name.indexOf(CheckBoxName) >= 0 &&
			  element.name.indexOf("SELECTALL") < 0) {
      if (element.checked) {
				return true
      }
    }
  }
  alert("输入错误，请做出最少一项选择！");
	return false
}

//检查是否没有选择任何复选框
function isCheckedEmpty(form, CheckBoxName) {
  for (var i = 0; i < form.elements.length; i++) {
    var element = form.elements[i]
    if (element.name.indexOf(CheckBoxName) >= 0 &&
			  element.name.indexOf("SELECTALL") < 0) {
      if (element.checked) {
        return false
      }
    }
  }
  return true
}

//检查是否选择了唯一的复选框
function isCheckedUnique(form, CheckBoxName) {
  var selectedCount = 0
  for (var i = 0; i < form.elements.length; i++) {
    var element = form.elements[i]
    if (element.name.indexOf(CheckBoxName) >= 0 &&
			  element.name.indexOf("SELECTALL") < 0) {
      if (element.checked) {
        selectedCount = selectedCount + 1
      }
    }
  }
  if (selectedCount != 1) {
    alert("输入错误，请做出唯一一项选择！");
    return false
  }
  return true
}
/********************************************************************/