/**
 * Created by Administrator on 2015/4/8.
 */
$.extend($.fn.validatebox.defaults.rules, {

    // 验证整数或小数
    intOrFloat: {
        validator: function (value) {
            return /^\d+(\.\d+)?$/i.test(value);
        },
        message: '请输入数字！'
    }
});
