package com.youngbook.entity.po.sale.contract;

import com.youngbook.common.Tree;
import com.youngbook.common.TreeOperator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 创建人：zhouhaihong
 * 创建时间：2015/12/22
 * 描述：
 * ContractRouteListStatus: 销售合同流转记录状态常量
 */
public class ContractRouteListStatus {

    /**
     * 状态为申请
     */
    public static final int ACTIONTYPE_APPLY = 1;
    /**
     * 审批通过
     */
    public static final int ACTIONTYPE_CHECK_OK = 2;

    /**
     * 审批不通过
     */
    public static final int ACTIONTYPE_CHECK_NO = 3;

    /**
     * 等待调配
     */
    public static final int ACTIONTYPE_WAITSEND = 4;
    /**
     * 合同寄送
     */
    public static final int ACTIONTYPE_SENDING = 5;
    /**
     * 合同签收
     */
    public static final int ACTIONTYPE_SIGN = 6;

    /**
     * 合同领用
     */
    public static final int ACTIONTYPE_DISTRIBUTECONTRACT = 7;
    /**
     * 合同签约
     */
    public static final int ACTIONTYPE_SING_OK = 8;
    /**
     * 合同异常
     */
    public static final int ACTIONTYPE_EXCEPTION = 9;
    /**
     * 调配管理员
     */
    public static final int ACTIONTYPE_SENDMANAGER=10;
    /**
     * 合同状态确认
     */
    public static final int ACTIONTYPE_CONFIRMCONTRACT = 11;

    /**
     * 归档状态确认
     */
    public static final int ACTIONYUPE_CONFIRMARCHIVECONTRACT = 12;
    /**
     *合同归档
     */
    public static final int ACTIONTYPE_ARCHIVECONTRACT = 13;

    /**
     * 空白销售合同管理员
     */
    public static final int ACTIONTYPE_BLANKCONTRACTMANAGER = 14;
    /**
     * 异常销售管理员
     */
    public static final int ACTIONTYPE_EXCEPTIONMANAGER = 15;

    /**
     * 签约合同管理员
     */
    public static final int ACTIONTYPE_SING_OK_MANAGER = 16;
    /**
     * 归档合同管理员
     */
    public static final int ACTIONTYPE_ARCHIVEMANAGER = 17;
    /**
     * 合同正常
     */
    public static final int ACTIONTYPE_AVERAGE = 18;
    /**
     * 取消签约
     */
    public static final int ACTIONTYPE_CANCELSING = 19;

    /**
     * 移交总部管理员
     */
    public static final int ACTIONTYPE_TOTALMANAGEER = 20;
    /**
     * 删除销售合同申请
     */
    public static final int  ACTIONTYPE_DELETEAPPLICATIONCONTRACR= 21;

    /**
     * 获取树的 JSON
     * @return
     */
    public static String getStatusTreeJson() {

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = null;
        Tree tree = null;

        tree = new Tree(String.valueOf(ACTIONTYPE_APPLY), getName(ACTIONTYPE_APPLY));
        jsonObject = TreeOperator.getJson4TreeMenu(tree);
        jsonArray.add(jsonObject);

        tree = new Tree(String.valueOf(ACTIONTYPE_CHECK_OK), getName(ACTIONTYPE_CHECK_OK));
        jsonObject = TreeOperator.getJson4TreeMenu(tree);
        jsonArray.add(jsonObject);

        tree = new Tree(String.valueOf(ACTIONTYPE_CHECK_NO), getName(ACTIONTYPE_CHECK_NO));
        jsonObject = TreeOperator.getJson4TreeMenu(tree);
        jsonArray.add(jsonObject);

        tree = new Tree(String.valueOf(ACTIONTYPE_WAITSEND), getName(ACTIONTYPE_WAITSEND));
        jsonObject = TreeOperator.getJson4TreeMenu(tree);
        jsonArray.add(jsonObject);

        tree = new Tree(String.valueOf(ACTIONTYPE_SENDING), getName(ACTIONTYPE_SENDING));
        jsonObject = TreeOperator.getJson4TreeMenu(tree);
        jsonArray.add(jsonObject);

        tree = new Tree(String.valueOf(ACTIONTYPE_SIGN), getName(ACTIONTYPE_SIGN));
        jsonObject = TreeOperator.getJson4TreeMenu(tree);
        jsonArray.add(jsonObject);

        tree = new Tree(String.valueOf(ACTIONTYPE_DISTRIBUTECONTRACT), getName(ACTIONTYPE_DISTRIBUTECONTRACT));
        jsonObject = TreeOperator.getJson4TreeMenu(tree);
        jsonArray.add(jsonObject);

        tree = new Tree(String.valueOf(ACTIONTYPE_SING_OK), getName(ACTIONTYPE_SING_OK));
        jsonObject = TreeOperator.getJson4TreeMenu(tree);
        jsonArray.add(jsonObject);

        tree = new Tree(String.valueOf(ACTIONTYPE_EXCEPTION), getName(ACTIONTYPE_EXCEPTION));
        jsonObject = TreeOperator.getJson4TreeMenu(tree);
        jsonArray.add(jsonObject);

        tree = new Tree(String.valueOf(ACTIONTYPE_SENDMANAGER), getName(ACTIONTYPE_SENDMANAGER));
        jsonObject = TreeOperator.getJson4TreeMenu(tree);
        jsonArray.add(jsonObject);

        tree = new Tree(String.valueOf(ACTIONTYPE_CONFIRMCONTRACT), getName(ACTIONTYPE_CONFIRMCONTRACT));
        jsonObject = TreeOperator.getJson4TreeMenu(tree);
        jsonArray.add(jsonObject);

        tree = new Tree(String.valueOf(ACTIONYUPE_CONFIRMARCHIVECONTRACT), getName(ACTIONYUPE_CONFIRMARCHIVECONTRACT));
        jsonObject = TreeOperator.getJson4TreeMenu(tree);
        jsonArray.add(jsonObject);

        tree = new Tree(String.valueOf(ACTIONTYPE_ARCHIVECONTRACT), getName(ACTIONTYPE_ARCHIVECONTRACT));
        jsonObject = TreeOperator.getJson4TreeMenu(tree);
        jsonArray.add(jsonObject);

        tree = new Tree(String.valueOf(ACTIONTYPE_BLANKCONTRACTMANAGER), getName(ACTIONTYPE_BLANKCONTRACTMANAGER));
        jsonObject = TreeOperator.getJson4TreeMenu(tree);
        jsonArray.add(jsonObject);

        tree = new Tree(String.valueOf(ACTIONTYPE_EXCEPTIONMANAGER), getName(ACTIONTYPE_EXCEPTIONMANAGER));
        jsonObject = TreeOperator.getJson4TreeMenu(tree);
        jsonArray.add(jsonObject);

        tree = new Tree(String.valueOf(ACTIONTYPE_SING_OK_MANAGER), getName(ACTIONTYPE_SING_OK_MANAGER));
        jsonObject = TreeOperator.getJson4TreeMenu(tree);
        jsonArray.add(jsonObject);

        tree = new Tree(String.valueOf(ACTIONTYPE_ARCHIVEMANAGER), getName(ACTIONTYPE_ARCHIVEMANAGER));
        jsonObject = TreeOperator.getJson4TreeMenu(tree);
        jsonArray.add(jsonObject);

        tree = new Tree(String.valueOf(ACTIONTYPE_AVERAGE), getName(ACTIONTYPE_AVERAGE));
        jsonObject = TreeOperator.getJson4TreeMenu(tree);
        jsonArray.add(jsonObject);

        tree = new Tree(String.valueOf(ACTIONTYPE_CANCELSING), getName(ACTIONTYPE_CANCELSING));
        jsonObject = TreeOperator.getJson4TreeMenu(tree);
        jsonArray.add(jsonObject);

        tree = new Tree(String.valueOf(ACTIONTYPE_TOTALMANAGEER), getName(ACTIONTYPE_TOTALMANAGEER));
        jsonObject = TreeOperator.getJson4TreeMenu(tree);
        jsonArray.add(jsonObject);

        tree = new Tree(String.valueOf(ACTIONTYPE_DELETEAPPLICATIONCONTRACR), getName(ACTIONTYPE_DELETEAPPLICATIONCONTRACR));
        jsonObject = TreeOperator.getJson4TreeMenu(tree);
        jsonArray.add(jsonObject);

        return jsonArray.toString();

    }

    /**
     * 根据状态获取中文名
     * @param status
     * @return
     */
    public static String getName(Integer status) {
        String name = "";
        switch (status) {
            case 1:
                name = "申请销售合同";
                break;
            case 2:
                name = "审批申请通过";
                break;
            case 3:
                name = "审批申请不通过";
                break;
            case 4:
                name = "设置等待调配";
                break;
            case 5:
                name = "设置合同寄送";
                break;
            case 6:
                name = "设置合同签收";
                break;
            case 7:
                name = "设置合同分配";
                break;
            case 8:
                name = "设置合同签约";
                break;
            case 9:
                name = "设置合同遗失";
                break;
            case 10:
                name = "移交调配管理员";
                break;
            case 11:
                name = "合同状态确认";
                break;
            case 12:
                name = "合同归档确认";
                break;
            case 13:
                name = "设置合同归档";
                break;
            case 14:
                name = "移交空白管理员";
                break;
            case 15:
                name = "移交异常管理员";
                break;
            case 16:
                name = "移交签约管理员";
                break;
            case 17:
                name = "移交归档管理员";
                break;
            case 18:
                name = "设置合同正常";
                break;
            case 19:
                name = "取消签约销售合同";
                break;
            case 20:
                name = "移交总部修改财富中心";
                break;
            case 21:
                name = "删除销售合同申请";
                break;
            default:
                name = "未知状态";
        }

        return name;
    }

}
