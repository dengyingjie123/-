/**
 * 页面上的效果
 */
$(function(){
	/**
	 * 控制 Tab 的切换
	 */
	$('.tabs a').mouseover(function() {
		$(this).siblings().removeClass('current');
		$(this).addClass('current');
		$(this).parent().next().find('ul').hide();
		$(this).parent().next().find('ul').eq($(this).index()).show();
	})
	/**
	 * 控制 Tab 的切换
	 */
	$('.u-tabs a').click(function() {
		$(this).siblings().removeClass('current');
		$(this).addClass('current');
		$(this).parent().next().find('.item').hide();
		$(this).parent().next().find('.item').eq($(this).index()).show();
	})
	/**
	 * 控制产品的进度条显示
	 */
	$('.progress span').width($('.progress span').attr('data'));
	//$('[data-toggle="tooltip"]').tooltip();
})


/**
 * 刷新验证码
 */
function refresh(a, u) {
        var img = a.firstChild;
        var num = new Date().getSeconds();
        img.src =  '/core/w2/system/getCaptcha.action?u=' + u + '&random=' + num;
}
function refreshImg(a, u) {
	var img  = $(a)[0];
	var num = new Date().getSeconds();
	img.src =  '/core/w2/system/getCaptcha.action?u=' + u + '&random=' + num;
}
/**
 * 姚章鹏
 * 验证码错误重新刷新
 * @param img
 * @param u
 */
function onClickRefresh(img, u) {
	var num = new Date().getSeconds();
	img.src =  '/core/w2/system/getCaptcha.action?u=' + u + '&random=' + num;
}
/**
 * 单元格隔行换色
 * @param obj
 */
function changeTrColor(obj){
    var _table=obj.parentNode;
    for (var i=0;i<_table.rows.length;i++){
        _table.rows[i].style.backgroundColor="";
    }
    obj.style.backgroundColor="#ffefe4";
}