var treePlus = (function() {

    function removeOtherNodes(tree, includeNode) {
        try {
            var parent = $(tree).tree('getParent',includeNode.target);
            if (!fw.checkIsNullObject(parent)) {
                var children = $(tree).tree('getChildren',parent.target);
//                alert();
                $(children).each(function() {
                    if (this.id != includeNode.id) {
                        $(tree).tree('remove', this.target);
                        //alert("TREE : "+ JSON.stringify($(tree).tree('options').data));
                    }
                });
                return removeOtherNodes(tree,parent);
            }
            else {
                return tree;
            }

        }
        catch (e) {
            //alert(e);
        }
    }


    function getSubTree(tree, includeNode, subTreeNodesArray) {
        subTreeNodesArray.push(includeNode);
        var parent = $(tree).tree('getParent',includeNode.target);
        if (!fw.checkIsNullObject(parent)) {
            return getSubTree(tree,parent,subTreeNodesArray);
        }
        else {
            return subTreeNodesArray;
        }
    }

    return {
        getSubTree:function(tree, includeNodes) {
            var subTree;
//            $(includeNodes).each(function() {
//                //alert(JSON.stringify(this));
//                subTree = removeOtherNodes(tree,this);
//            });

//            var subTreeNodesArray = [];
//            $(includeNodes).each(function() {
//                subTreeNodesArray = getSubTree(tree,this,subTreeNodesArray);
//            });

//            alert(subTreeNodesArray);
//            alert(subTreeNodesArray.length);

            $(includeNodes).each(function() {
                $(tree).tree('check',this.target);
            });

            var uncheckNodes = $(tree).tree('getChecked', 'unchecked');
            //alert(uncheckNodes.length);
            while (uncheckNodes.length>0) {
                $(tree).tree('remove',uncheckNodes[0].target);
                uncheckNodes = $(tree).tree('getChecked', 'unchecked');
            }

            treePlus.uncheckAll(tree);
            return subTree;
        },
        uncheckAll:function(tree) {
            var checkNodes = $(tree).tree('getChecked');
            $(checkNodes).each(function() {
                $(tree).tree('uncheck',this.target);
            });
        }
    }
})();