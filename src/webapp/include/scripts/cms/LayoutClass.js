var LayoutClass = function(token) {

    function initAll(){
        initDepartmentTreeList();
        // loadDrag();
        onClickColumnOption();
    }

    //初始化部门下拉列表
    function initDepartmentTreeList(){
        var url = WEB_ROOT+"/system/Department_list.action";
        var deptTreeID ='#departmentTree'+token;
        //fw.combotreeLoadWithCheck('#departmentTree'+token, url, null, null, null);
        var tree = $(deptTreeID).combotree('tree');

        fw.treeLoad(tree,url,null, null, null);
        $(deptTreeID).combotree({
            onSelect:function(){
                var departmentId = tree.tree('getSelected').id;
                // var departmentId = $(deptTreeID).combobox("getValue");
                if(departmentId!=null){
                    // alert(departmentId+" : "+tree.tree('getSelected').text);
                    loadColumnOption(departmentId)
                }
            }
        });

    }
    //加载某个部门的栏目列表
    function loadColumnOption(departmentId){
        var columnTreeId="columnOption"+token;
        var url = WEB_ROOT+"/cms/Column_list.action?departmentId="+departmentId;
        var columnData;
        fw.post(url, null, function(data) {
            //fw.alertReturnValue(data);
            for(var i=0;i<data.length;i++){
                var table = $('#columnListTable'+token);
                var row = $("<tr></tr>");
                var td = $("<td></td>");
                td.append($("<div class='item'>"+data[i].Name+"</div>"));
                row.append(td);
                table.append(row);
                // alert(data[i].Name);
            }
            loadDrag()
        }, function () {});

    }
    function onClickColumnOption() {
        $('#columnOption'+token).tree({onSelect:function(node) {
            alert(node.text);
            alert(this);
            var $dropDiv=$('<div></div>');
            this.closest("body").append($dropDiv);
            $dropDiv.html(node.text);
            //dropModal();
        }});
    }

    //
    function loadDrag(){
        $('.left .item').draggable({
            revert:true,
            proxy:'clone'
        });
        $('.right td.drop').droppable({
            onDragEnter:function(){
                $(this).addClass('over');
            },
            onDragLeave:function(){
                $(this).removeClass('over');
            },
            onDrop:function(e,source){
                $(this).removeClass('over');
                if ($(source).hasClass('assigned')){
                    $(this).append(source);
                } else {
                    var c = $(source).clone().addClass('assigned');
                    $(this).empty().append(c);
                    c.draggable({
                        revert:true
                    });
                }
            }
        });
        $('.left').droppable({
            accept:'.assigned',
            onDragEnter:function(e,source){
                $(source).addClass('trash');
            },
            onDragLeave:function(e,source){
                $(source).removeClass('trash');
            },
            onDrop:function(e,source){
                $(source).remove();
            }
        });

    }


    return{
        initModule:function(){
            return initAll();
        }
    };
};

