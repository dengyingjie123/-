package com.youngbook.common.utils;

import com.youngbook.common.KVObject;
import com.youngbook.common.MyException;
import com.youngbook.common.ReturnObject;
import com.youngbook.common.ReturnObjectCode;
import com.youngbook.common.config.Config;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.production.OrderPO;
import com.youngbook.entity.po.production.ProductionPO;
import com.youngbook.entity.po.production.ProductionCompositionPO;
import com.youngbook.entity.po.production.ProjectPO;
import com.youngbook.entity.po.sale.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Console;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MoneyUtils {

    public static void main(String[] args) throws Exception {

        System.out.println(MoneyUtils.format2Fen(1.23));
    }


    /**
     * 计算兑付金额
     *
     * 增加注释，说明各个参数的含义，以及A类的计算公式说明
     *
     * @param money
     * @param profitRate
     * @param interestDate
     * @param tempInterestDate
     * @param duration
     * @param type
     * @return
     * @throws Exception
     */
    public static double calculateProfit(double money, double profitRate, String interestDate, String tempInterestDate, int duration, int type) throws Exception{

        int DAYS_OF_YEAR = 365;
        int DAYS_OF_YEAR_A = 360;
        int MONTH_OF_YEAR = 12;




        double profit = 0.00;




        // 按天计息
        if (type == 0) {
            profit = money * profitRate * duration / DAYS_OF_YEAR;
        }
        // 按月计息
        else if (type == 1) {
            profit = money * profitRate * duration / MONTH_OF_YEAR;
        }
        // 按年计算
        else if (type == 2) {
            profit = money * profitRate * duration;
        }
        // 按天(A类)计算
        else if (type == 3) {
            /**
             * 计算计息时长
             */
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date tempInterest = format.parse(tempInterestDate);
            Date interest = format.parse(interestDate);




            /**
             * 得到毫秒，换算为天
             */
            long temp = tempInterest.getTime() - interest.getTime();
            long cycle = temp/1000/60/60/24;
            // todo : gouxilin 使用TimeUtils.getDayDifference()方法计算天数





            /**
             * 计算兑付利息
             */
            profit = money * profitRate * cycle / DAYS_OF_YEAR_A;
        }




        profit = MoneyUtils.handleDouble(profit, 2);




        return profit;
    }


    public static int getMoney2Fen(double money) {
        return (int)Math.round(money * 100);
    }


    /**
     * 计算佣金
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年4月20日
     *
     * @param money
     * @param level
     * @param areaCode
     * @param needCorrection
     * @param needCustomerTypeCorrection
     * @param conn
     * @return
     * @throws Exception
     */
    public static double calcCommissionMoney(Double money, String level, String areaCode, int needCorrection, int needCustomerTypeCorrection, Connection conn) throws Exception {
        double rate = MoneyUtils.calcCommissionRate(level, areaCode, needCorrection, needCustomerTypeCorrection, conn);
        return money * rate / 100;
    }

    /**
     * HOPEWEALTH-1319
     * 根据传入的commissionLevel和相关条件，从多个表中计算返佣值
     *
     * @param level
     * @param areaCode
     * @param needCorrection
     * @param needCustomerTypeCorrection
     * @param conn
     * @return
     * @throws Exception
     */

    public static double calcCommissionRate(String level, String areaCode, int needCorrection, int needCustomerTypeCorrection, Connection conn) throws Exception {

        double commissionRate = 0.0;

        // 无修正
        if (needCorrection == 0 && needCustomerTypeCorrection == 0) {
            ProductionCommissionPO po = getCommissionByLevel(level, areaCode, conn);
            if (po != null && po.getCommissionRate() != Double.MAX_VALUE) {
                commissionRate += po.getCommissionRate();
            }
        }

        // 需要增加返佣修正
        if (needCorrection != 0) {
            CommissionCorrectionPO po  = getCorrectionByLevel(level, areaCode, conn);
            if (po != null && po.getCorrectionValue() != Double.MAX_VALUE) {
                commissionRate += po.getCorrectionValue();
            }
        }

        // 需要增加客户类型修正
        if (needCustomerTypeCorrection != 0) {
            CustomerTypeCommissionCorrectionPO po = getCustomerTypeCorrectionByLevel(level, areaCode, conn);
            if (po != null && po.getCorrectionValue() != Double.MAX_VALUE) {
                commissionRate += po.getCorrectionValue();
            }
        }

        return commissionRate;
    }

    /**
     * HOPEWEALTH-1319
     *
     * 根据commissionLevel获取一条productioncommission记录
     *
     * @param commissionLevel
     * @param conn
     * @return
     * @throws Exception
     */
    public static ProductionCommissionPO getCommissionByLevel(String commissionLevel, String areaCode, Connection conn) throws Exception {
        // 获取一条记录
        String sql = "select * from sale_productioncommission pc where pc.State = 0 and pc.effectieTime < NOW() and pc.commissionLevel = '" + commissionLevel + "' and pc.areaCode = '" + areaCode + "'";
        List<ProductionCommissionPO> list = MySQLDao.query(sql, ProductionCommissionPO.class, null, conn);
        if (list == null || list.size() != 1) {
            MyException.newInstance(ReturnObjectCode.ORDER_COMMISSION_NOT_EXISTENT, "找不到合适的返佣").throwException();
        }
        return list.get(0);
    }

    /**
     * HOPEWEALTH-1319
     *
     * 根据commissionLevel获取一条CommissionCorrection记录
     *
     * @param commissionLevel
     * @param conn
     * @return
     * @throws Exception
     */
    public static CommissionCorrectionPO getCorrectionByLevel(String commissionLevel, String areaCode, Connection conn) throws Exception {
        String sql = "select * from sale_commissioncorrection cc where cc.State = 0 and cc.effectieTime < NOW() and cc.commissionLevel = '" + commissionLevel + "' and cc.areaCode = '" + areaCode + "'";
        List<CommissionCorrectionPO> list = MySQLDao.query(sql, CommissionCorrectionPO.class, null, conn);
        if (list == null || list.size() != 1) {
            MyException.newInstance(ReturnObjectCode.ORDER_COMMISSION_NOT_EXISTENT, "找不到合适的产品调整返佣").throwException();
        }
        return list.get(0);
    }

    /**
     * HOPEWEALTH-1319
     *
     * 根据commissionLevel获取一条CustomerTypeCommissionCorrection记录
     *
     * @param commissionLevel
     * @param conn
     * @return
     * @throws Exception
     */
    public static CustomerTypeCommissionCorrectionPO getCustomerTypeCorrectionByLevel(String commissionLevel, String areaCode, Connection conn) throws Exception {
        // 获取一条记录
        String sql = "select * from sale_customertypecommissioncorrection where State = 0 and effectieTime < NOW() and commissionLevel = '" + commissionLevel + "' and areaCode = '" + areaCode + "' ";
        List<CustomerTypeCommissionCorrectionPO> list = MySQLDao.query(sql, CustomerTypeCommissionCorrectionPO.class, null, conn);
        if (list == null || list.size() != 1) {
            MyException.newInstance(ReturnObjectCode.ORDER_COMMISSION_NOT_EXISTENT, "找不到合适的客户类型调整返佣").throwException();
        }
        return list.get(0);
    }




    /**
     * 获取周期单位名称
     *
     * 修改：邓超
     * 内容：添加代码
     * 时间：2016年4月20日
     *
     * @param interestUnit
     * @return
     * @throws Exception
     */
    public static String getInterestUnitName(int interestUnit) throws Exception {
        String unitName = "";
        if(interestUnit == PaymentPlanUnit.UnitDay) {
            unitName = "DATE";
        } else if(interestUnit == PaymentPlanUnit.UnitMonth) {
            unitName = "MONTH";
        } else if(interestUnit == PaymentPlanUnit.UnitYear) {
            unitName = "YEAR";
        } else {
            throw new Exception("获取付息单位异常");
        }
        return unitName;
    }

    /**
     * 将double类型的金额四舍五入，origin是原始金额，reservedBits为保留位数
     */
    public static double handleDouble(double origin, int reservedBits) {
        return Math.round(origin * Math.pow(10, reservedBits)) / Math.pow(10.0, reservedBits);
    }

    public static String format2String(double money) {
        if (money == 0 || money == Double.MAX_VALUE) {
            return "0.00";
        }

        String moneyString = new java.text.DecimalFormat("##,###.00").format(money).toString();

        if (!StringUtils.isEmpty(moneyString) && moneyString.indexOf(".") == 0) {
            moneyString = "0" + moneyString;
        }

        return moneyString;
    }

    public static String format2String(String money) {

        if (StringUtils.isNumeric(money)) {
            return MoneyUtils.format2String(Double.parseDouble(money));
        }

        return money;

    }

    public static int format2Fen(double money) {
        BigDecimal m = new BigDecimal(money * 100);
        return m.setScale(2,BigDecimal.ROUND_HALF_UP).intValue();
    }



    /**
     * 计算兑付计划的兑付时间，返回存有每一期兑付计划时间的字符串数组
     *
     * @param originDate 第一期兑付时间
     * @param count      兑付期数
     * @param cycle      兑付周期
     * @return
     */
    private static List<String> getIntrestDateEveryTime(String originDate, int count, int cycle, String unit) throws Exception {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        // 组装付息时间集合
        List<String> list = new ArrayList<String>();
        list.add(originDate);

        for (int i = 0; i < count - 1; i ++) {
            // 计算指定时间单位间隔的付息时间
            String paymentDate = TimeUtils.getTime(originDate.substring(0, 10), cycle, unit).substring(0,10);
            // 这一期付息时间变为下一期的原始时间
            originDate = paymentDate;
            list.add(paymentDate);
        }

        for (int i = 0; i < list.size(); i++) {
            list.set(i, format.format(format.parse(list.get(i))));
        }

        return list;
    }













}
