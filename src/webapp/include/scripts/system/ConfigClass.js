var ConfigClass = function() {
    return {

        splitLetter:'★',  // 分割符

        iconClsAdded:"theme01-icon-106",
        iconClsWaitingAdd:"theme01-icon-002",
        iconClsDefault:"theme01-icon-108",

        datagridPageList: [15,30,60],
        datagridPageListType4Window: 1,
        datagridPageListType4Default: 0,

        buildTableTypeWithManagement:0,
        buildTableTypeSelection:1,

        buildTreeSystemIcon:function(tree) {
            $(tree).tree({
                url: WEB_ROOT+"/config_getSystemIcon.action",
                loadFilter: function(data){
                    //alert(rows);
//                return fw.treeDataConvert(rows);
                    try {
                        if (data.code == 100) {
                            var rows = fw.convert2Json(data.text);
                            return rows;
                        }
                        else {
                            fw.alert("错误",data.message + " 代码: " + data.code);
                        }
                    }
                    catch(e) {
                        fw.alert("错误","未知错误，信息："+e);
                    }
                }
            });
        }

    }
};