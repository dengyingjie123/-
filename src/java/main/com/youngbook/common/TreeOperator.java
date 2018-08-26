package com.youngbook.common;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形操作类
 * User: Lee
 * Date: 14-4-1
 * Time: 上午11:48
 */
public class TreeOperator {
    /**
     * 测试方法
     * @param args
     */
    public static void main(String[] args) {
        Tree t1 = new Tree("1","N1", "1", null);
        Tree t2 = new Tree("2","N2",  "0", null);
        Tree t3 = new Tree("3","N3",  "1", null);
        Tree t4 = new Tree("4","N4",  "1", null);
        Tree t5 = new Tree("5","N5",  "3", null);
        Tree t6 = new Tree("6","N6",  "5", null);
        Tree t7 = new Tree("7","N7",  "0", null);
        Tree t8 = new Tree("8","N8",  "9", null);
        Tree t9 = new Tree("9","N9",  "4", null);

        Tree forest= TreeOperator.createForest();
        TreeOperator.add(forest,t1);
        TreeOperator.add(forest,t2);
        TreeOperator.add(forest,t3);
        TreeOperator.add(forest,t4);
        TreeOperator.add(forest,t5);
        TreeOperator.add(forest,t6);
        TreeOperator.add(forest,t7);
        TreeOperator.add(forest,t8);
        TreeOperator.add(forest,t9);

        TreeOperator.printForest(forest, 0);
//        JSONObject json = TreeOperator.getJson4Tree(forest);
//        System.out.println(json);

//        List<Tree> children = new ArrayList<Tree>();
//        TreeOperator.getChildren(t3, children);

//        System.out.println(children.size());
//        System.out.println(children.size());

        Tree leaf = TreeOperator.find(forest, "9");

        TreeOperator.printForest(leaf, 0);
    }

    /**
     * 获得包括自身的所有孩子，并保存到children的list中
     * @param forest
     * @param children
     */
    private static void buildChildren(Tree forest, List<Tree> children) {

        if (forest != null) {
            children.add(forest);

            for (int i = 0; forest.getChildren() != null && i < forest.getChildren().size(); i++) {
                Tree tree = forest.getChildren().get(i);
                buildChildren(tree, children);
            }
        }

    }

    public static List<Tree> getChildren(Tree forest) {
        List<Tree> list = new ArrayList<Tree>();
        buildChildren(forest, list);
        return list;
    }

    public static Tree find(Tree forest, String id) {
        Tree leaf = null;
        if (forest.getId().equals(id)) {
            leaf = forest;
        }
        else {
            for (int i = 0; i < forest.getChildren().size(); i++) {
                leaf = find(forest.getChildren().get(i), id);
                if (leaf != null) {
                    return leaf;
                }
            }
        }

        return leaf;
    }

    public static JSONObject getJson4Tree(Tree forest) {

        JSONObject json = new JSONObject();
        if (forest.getData() != null) {
            json = forest.getData().toJsonObject4Tree();
        }
        if (forest.getChildren().size() > 0) {
            JSONArray array = new JSONArray();
            for(Tree tree : forest.getChildren()) {
                JSONObject tempJson = TreeOperator.getJson4Tree(tree);
                array.add(tempJson);
            }
            json.element("children",array);
        }
        return json;
    }

    public static JSONObject getJson4TreeMenu(Tree forest) {
        JSONObject json = new JSONObject();
        json.element("id",forest.getId());
        json.element("text",forest.getName());
        if (forest.getChildren().size() > 0) {
            JSONArray array = new JSONArray();
            for(Tree tree : forest.getChildren()) {
                JSONObject tempJson = TreeOperator.getJson4Tree(tree);
                array.add(tempJson);
            }
            json.put("children",array);
        }
        return json;
    }

    /**
     * 打印树
     * @param forest 森林
     * @param level 当前打印层级，用于控制打印空格
     */
    public static void printForest(Tree forest, int level) {
        if (forest != null) {
            for(int i = 0; i < level; i++) {
                System.out.print("   ");
            }
            System.out.print(forest.getId());
            System.out.println();
            for (Tree child : forest.getChildren()) {
                printForest(child, level + 1);
            }
        }
    }




    /**
     * 新建一个森林
     * @return 森林
     */
    public static Tree createForest() {
        Tree forest = new Tree("-1ROOT","ROOT","-1",null);

        return forest;
    }

    /**
     * 将树插入森林
     * @param forest 森林
     * @param tree 待插入的树
     */
    public static void add(Tree forest, Tree tree) {
        buildParent(forest,tree);
        buildChild(forest, tree);
    }

    /**
     * 构建父级
     * @param forest 父级
     * @param tree
     */
    public static void buildParent(Tree forest, Tree tree) {
        Tree parent = findParent(forest,tree);
        if (parent != null) {
            parent.getChildren().add(tree);
            tree.setParent(parent);
        }
        else {
            forest.getChildren().add(tree);
            tree.setParent(forest);
        }
    }

    public static Tree findParent(Tree parent, Tree tree) {

        if (parent.getId().equals(tree.getParentId())) {
            return parent;
        }
        else {
            if (parent.getChildren().size() > 0) {
                for (Tree temp : parent.getChildren()) {
                    Tree newParent = findParent(temp, tree);
                    if (newParent != null) {
                        return newParent;
                    }
                }
            }
        }
        return null;
    }

    public static void buildChild(Tree forest, Tree tree) {
        Tree child = findChild(forest,tree);
        List<Tree> children = new ArrayList<Tree>();
        while (child != null) {
            remove(child);
            children.add(child);
            child = findChild(forest, tree);
        }

        for(Tree tempChild : children) {
            tree.getChildren().add(tempChild);
            tempChild.setParent(tree);
        }
    }

    public static Tree findChild(Tree child, Tree tree) {

        if (child.getParentId().equals(tree.getId())) {
            return child;
        }
        else {
            if (child.getChildren().size() > 0) {
                for(Tree tempChild : child.getChildren()) {
                    Tree newChild = findChild(tempChild,tree);
                    if (newChild != null) {
                        return newChild;
                    }
                }
            }
        }
        return null;
    }

    public static void remove(Tree tree) {
        Tree parent = tree.getParent();
        if (parent != null && parent.getChildren().contains(tree)) {
            boolean isOk = parent.getChildren().remove(tree);

            //System.out.println("Remove : " + tree.toString() + " OK:" + isOk);
        }
    }
}
