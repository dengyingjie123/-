/********************************************************************/

//ͨ��һ����ť��ѡ������ͬ��ǰ׺���ĸ�ѡ��
function checkAll(form, selectedStatus, CheckBoxName) {
  for (var i = 0; i < form.elements.length; i++) {
    var element = form.elements[i];
    if ((element.name.indexOf(CheckBoxName) >= 0 ||
			  element.name.indexOf("SELECTALL") >= 0) && element.disabled == false) {
      element.checked = selectedStatus;
    }
  }
}

//����Ƿ�����ѡ����һ����ѡ��
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
  alert("�����������������һ��ѡ��");
	return false
}

//����Ƿ�û��ѡ���κθ�ѡ��
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

//����Ƿ�ѡ����Ψһ�ĸ�ѡ��
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
    alert("�������������Ψһһ��ѡ��");
    return false
  }
  return true
}
/********************************************************************/