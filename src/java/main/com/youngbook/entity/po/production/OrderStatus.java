package com.youngbook.entity.po.production;

import com.youngbook.common.Tree;
import com.youngbook.common.TreeOperator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class OrderStatus {

    /**
     * 预约、未付款
     */
    public static final int Appointment = 0;
    public static final int Preappointment = 22;

    /**
     * 取消预约
     *
     * Date: 2016-05-18 23:44:39
     * Author: leevits
     */
    public static final int AppointmentCancel = 21;

    /**
     * 已销售、已付款，已支付并确认
     */
    public static final int Saled = 1;

    /**
     * 作废
     */
    public static final int Cancel = 4;

    /**
     * 已兑付
     */
    public static final int Payback = 5;



    /**
     * 部分兑付
     *
     * Date: 2016-05-21 20:54:34
     * Author: leevits
     */
    public static final int PaybackSomePart = 8;

    public static final int AppointmentWaiting = 9;

    /**
     * 已返佣
     */
    public static final int Rebate = 10;

    /**
     * 预约超时
     */
    public static final int AppointmentTimeout = 6;


    /**
     * 已支付待确认
     */
    public static final int SaleAndWaiting = 7;


    /**
     * 财务一次审核
     */
    public static final int FinanceConfirm01 = 25;


    /**
     * 取消支付待确认
     *
     * Date: 2016-05-19 19:12:02
     * Author: leevits
     */
    public static final int SaleAndWaitingCancel = 14;



    /**
     * 已转让
     */
    public static final int Transfered = 11;


    public static final int Feedback1 = 23;
    public static final int Feedback2 = 24;


    /**
     * 老订单，状态待确认
     */
    public static final int WaitingForConfirmOfOldOrder = 99;





    public static void main(String [] args) {
        System.out.println(OrderStatus.getJsonFinanceConfirm());
    }

    public static String getJsonFinanceConfirm() {

        JSONArray jsonArray = new JSONArray();

        int [] statuses = {Saled};

        for (int s : statuses) {
            Tree tree = new Tree(String.valueOf(s), getStatusName(s));
            JSONObject jsonObject = TreeOperator.getJson4TreeMenu(tree);
            jsonArray.add(jsonObject);
        }

        return jsonArray.toString();

    }

    public static String getJsonSaleAndWaiting() {

        JSONArray jsonArray = new JSONArray();

        Tree treeSaleAndWaiting = new Tree(String.valueOf(SaleAndWaiting), getStatusName(SaleAndWaiting));
        JSONObject jsonObject = TreeOperator.getJson4TreeMenu(treeSaleAndWaiting);
        jsonArray.add(jsonObject);

        return jsonArray.toString();
    }

    public static String getJsonFinanceConfirm01() {

        JSONArray jsonArray = new JSONArray();

        Tree treefinanceConfirm01 = new Tree(String.valueOf(FinanceConfirm01), getStatusName(FinanceConfirm01));
        JSONObject jsonObject = TreeOperator.getJson4TreeMenu(treefinanceConfirm01);
        jsonArray.add(jsonObject);

        return jsonArray.toString();
    }

    public static String getJsonAppointment() {

        JSONArray jsonArray = new JSONArray();

        Tree treeAppointment = new Tree(String.valueOf(Appointment), getStatusName(Appointment));
        JSONObject jsonObject = TreeOperator.getJson4TreeMenu(treeAppointment);
        jsonArray.add(jsonObject);

        return jsonArray.toString();
    }


    public static String getJsonFeedback1() {

        JSONArray jsonArray = new JSONArray();

        int [] statuses = {Feedback1};

        for (int s : statuses) {
            Tree tree = new Tree(String.valueOf(s), getStatusName(s));
            JSONObject jsonObject = TreeOperator.getJson4TreeMenu(tree);
            jsonArray.add(jsonObject);
        }

        return jsonArray.toString();
    }

    public static String getJsonFeedback2() {

        JSONArray jsonArray = new JSONArray();

        int [] statuses = {Feedback2};

        for (int s : statuses) {
            Tree tree = new Tree(String.valueOf(s), getStatusName(s));
            JSONObject jsonObject = TreeOperator.getJson4TreeMenu(tree);
            jsonArray.add(jsonObject);
        }

        return jsonArray.toString();
    }

    public static String getJsonPayback() {

        JSONArray jsonArray = new JSONArray();

        Tree treePayback = new Tree(String.valueOf(Payback), getStatusName(Payback));
        JSONObject jsonPayback= TreeOperator.getJson4TreeMenu(treePayback);
        jsonArray.add(jsonPayback);

        Tree treePaybackSomePart = new Tree(String.valueOf(PaybackSomePart), getStatusName(PaybackSomePart));
        JSONObject jsonPaybackSomePart= TreeOperator.getJson4TreeMenu(treePaybackSomePart);
        jsonArray.add(jsonPaybackSomePart);


        return jsonArray.toString();
    }

    public static String getJsonTransfer() {

        JSONArray jsonArray = new JSONArray();

        Tree treeSaled = new Tree(String.valueOf(Saled), getStatusName(Saled));
        JSONObject jsonObjectSaled = TreeOperator.getJson4TreeMenu(treeSaled);
        jsonArray.add(jsonObjectSaled);


        Tree treeTransfered = new Tree(String.valueOf(Transfered), getStatusName(Transfered));
        JSONObject jsonTransfered = TreeOperator.getJson4TreeMenu(treeTransfered);
        jsonArray.add(jsonTransfered);

        return jsonArray.toString();
    }

    /**
     * 获取状态中文名
     *
     * @param status
     * @return
     */
    public static String getStatusName(int status) {
        String name = "";
        switch (status) {
            case Appointment: name = "预约"; break;
            case Saled: name = "已支付"; break;
            case Cancel: name = "已作废"; break;
            case Payback: name = "已兑付"; break;
            case PaybackSomePart: name = "部分兑付"; break;
            case AppointmentTimeout: name = "超时"; break;
            case AppointmentCancel: name = "取消预约"; break;
            case SaleAndWaiting: name = "打款"; break;
            case Rebate: name = "已返佣"; break;
            case Transfered: name ="已转让";break;
            case WaitingForConfirmOfOldOrder: name ="老订单待确认";break;
            case SaleAndWaitingCancel: name ="支付待确认取消";break;
            case FinanceConfirm01: name ="财务一次审核";break;
            case Preappointment: name ="预约申请";break;
            case Feedback1: name ="第一次回访";break;
            case Feedback2: name ="第二次回访";break;
            case AppointmentWaiting: name = "预约待确认"; break;
            default: name = "未知状态";break;
        }
        return name;
    }
}

