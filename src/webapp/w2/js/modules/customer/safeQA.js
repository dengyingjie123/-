/**
 * Created by 邓超
 * Date 2015-5-26
 */

function validateSelect(element) {
    var selects = [document.getElementById('question1'), document.getElementById('question2'), document.getElementById('question3')];
    for(var i = 0; i < selects.length; i ++) {
        var select = selects[i];
        if(select.id == element.id || element.value == 0) {
            continue;
        }
        if(select.value == element.value) {
            popStatus(4, '请勿选择同一个问题', 5);
            element.value = 0;
            return false;
        }
    }
    return true;
}

function submitSelect() {
    var selects = document.getElementsByName('question');
    for(var i = 0; i < selects.length; i ++) {
        var select = selects[i];
        if(!validateSelect(select)) {
            return false;
        }
    }
    var answer1 = $('#answer1').val();
    var answer2 = $('#answer2').val();
    var answer3 = $('#answer3').val();
    if(answer1 == '' || answer2 == '' || answer3 == '') {
        popStatus(4, '请将答案输入完整', 5);
        return false;
    }
    $('#protectionQuestionForm').submit();
}