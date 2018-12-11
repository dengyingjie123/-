/**
 * @author： 徐明煜
 * Date: 11/28/18
 * Time: 14:40
 * 返回特定销售人员客户存量
 *
 */
var SpecificSalemanClass = function (token) {

    /**
     * 初始化主页面控件
     * @author 徐明煜
     * @param name
     */
    return{
        initAll:function () {
            var name = null
            initSpecificSalemanStockTable(name);
        }
    }


    /**
    * @description 方法实现说明
    * @author 徐明煜
    * @date 2018/12/4 14:17
    * @param name
    * @return
    * @throws
    */
    function initSpecificSalemanStockTable(name) {
        var strTableId = 'specificSalemanStockTable' + token;
        var url = WEB_ROOT + "/sale/report/Saleman_specficSalemanStockReport.action?selectName="+ name;

        $('#' + strTableId).datagrid({
            title: '客户存量',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            rownumbers: true,
            singleSelect: false,
            pagination: true,
            pageList: [10,20,50,100],
            pageSize: 10,
            rownumbers: true,//是否显示行号
            remoteSort: true,//是否从数据库排序
            loadFilter: function (data) {
                try {
                    data = fw.dealReturnObject(data);
                    return data;
                    console.log(data);
                }
                catch (e) {
                }
            },
            frozenColumns:[
                [  // 固定列，没有滚动条
                    {field: 'ck', checkbox: true},
                    {field: 'sid', title: '序号', hidden: true},
                    {field: 'id', title: '编号', hidden: true},
                ]
            ],
            columns:[
                [
                {field: 'orderNum', title: '订单编号', align: "center"},
                {field: 'customerName', title: '客户姓名', align: "center"},
                {field: 'productionName', title: '产品名', align: "center"},
                {field: 'money', title: '金额', align: "center",
                    formatter:function(value,row,index){
                        return fw.formatMoney(row['money'])
                    }
                    },
                {field: 'paymentPlanLastTime', title: '最近计划兑付时间', align: "center"},
                {field: 'payTime', title: '支付时间', align: "center"}
                ]
            ],
            toolbar:[{
                iconCls: 'icon-print', id: 'btnExport'+token, text: '导出'
            }],
            onLoadSuccess: function () {
                onClickSearchByname();
                onClickExportSpecificSalemanStock();
            }
        });
    }


    /**
    * @description 方法实现说明
    * @author 徐明煜
    * @date 2018/12/4 14:17
     * @param null
    * @return
    * @throws
    */
    function onClickExportSpecificSalemanStock() {

        var buttonId = "btnExport" + token;
        fw.bindOnClick(buttonId, function (process) {
            var selectName = $('#searchSaleman' + token).val();
            if(selectName == ""){
                fw.alert("提示", "搜索不能为空")
            }else{
                window.open(WEB_ROOT + "/sale/report/Saleman_exportSalemanStock.action?selectName="+ selectName);
            }
        });
    }


    /**
    * @description 方法实现说明
    * @author 徐明煜
    * @date 2018/12/4 14:17
     * @param null
    * @return
    * @throws
    */
    function  onClickSearchByname() {

        var buttonId = "btnSearch" + token;
        fw.bindOnClick(buttonId, function (process) {
            var selectName = $('#searchSaleman' + token).val();
            if(selectName == ""){
                fw.alert("提示", "搜索不能为空");
            }else {
                initSpecificSalemanStockTable(selectName);
            }
        });
    }
}