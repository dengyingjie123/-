package com.youngbook.common.utils;

import com.youngbook.common.KVObject;
import com.youngbook.common.config.Config;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.production.OrderPO;
import com.youngbook.entity.po.production.ProductionInfoPO;
import com.youngbook.entity.po.production.ProductionPO;
import com.youngbook.entity.vo.production.SalesPersonInfo;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class DataUtils {

    public static void main(String[] args) throws Exception {

        DataUtils.clearReferralCodes();

    }

    /**
     * 整理订单的推荐码
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年6月30日
     *
     * @throws Exception
     */
    public static void clearReferralCodes() throws Exception {

        // 拿到数据库连接
        Connection conn = Config.getConnection();

        // 修改 HBxxxxx、HCxxxxx 这类推荐码，变成 Sxxxxx
        String clearHBSQL = "select * from crm_order o where o.state = 0 and ( o.referralCode like '%HB%' or o.referralCode like '%HC%' )";
        List<OrderPO> hbOrders = MySQLDao.search(clearHBSQL, new ArrayList<KVObject>(), OrderPO.class, new ArrayList<KVObject>(), conn);
        for(OrderPO order : hbOrders) {
            String referralCode = order.getReferralCode();
            Integer length = referralCode.length();
            String newReferralCode = "S" + referralCode.substring(length - 5, length);

            System.out.println("\r\n-->-->--> " + referralCode + " --> " + newReferralCode + "\r\n");
            order.setReferralCode(newReferralCode);
            // MySQLDao.insertOrUpdate(order, conn);
        }

        // 用户状态设为被修改 9E67DBB7EF994E7AA645E928DD9D0A92、928E51E0928B43CD819BFCFA0777B217、1FF8C91047B5419493155DDFB6431596
        String updateUserStateSQL = "select * from system_user u where u.state = 0 and u.id in ('9E67DBB7EF994E7AA645E928DD9D0A92', '928E51E0928B43CD819BFCFA0777B217', '1FF8C91047B5419493155DDFB6431596') ";
        List<UserPO> users = MySQLDao.search(updateUserStateSQL, new ArrayList<KVObject>(), UserPO.class, new ArrayList<KVObject>(), conn);
        for(UserPO user : users) {
            user.setState(1);

            System.out.println("\r\n-->-->--> " + user.getId() + " --> state --> " + user.getState() + "\r\n");
            // MySQLDao.insertOrUpdate(user, conn);
        }

        // 将订单里推荐码为空的，设为视图的推荐码
        String clearEmptyReferralCodeSQL = "select * from crm_order o where o.state = 0 and ( o.referralCode = '' or o.referralCode is null )";
        List<OrderPO> emptyReferralCodeOrders = MySQLDao.search(clearEmptyReferralCodeSQL, new ArrayList<KVObject>(), OrderPO.class, new ArrayList<KVObject>(), conn);
        for(OrderPO order : emptyReferralCodeOrders) {
            // 查询视图
            String viewSQL = "select distinct b.referralCode from crm_salespersoninfo_vw b where b.SalesPersonID = '" + order.getSalemanId() + "'";
            List<SalesPersonInfo> infos = MySQLDao.search(viewSQL, new ArrayList<KVObject>(), SalesPersonInfo.class, new ArrayList<KVObject>(), conn);
            if(infos.size() == 0) {
                ;
            }
            else if (infos.size() == 1) {
                SalesPersonInfo info = infos.get(0);
                order.setReferralCode(info.getReferralCode());
                System.out.println("\r\n-->-->--> Order（" + order.getId() + "） Empty Referral Code -->--> " + info.getReferralCode() + "\r\n");
                // MySQLDao.insertOrUpdate(order, conn);
            } else {
                new Exception("订单在视图中对应的销售人员大于 1 条");
            }
        }

        // 将推荐码为手机号的特定状态订单，改成视图对应的推荐码
        String clearMobileReferralCodeSQL = "select * from crm_order o where o.state = 0 and ( o.referralCode like '1%' ) and o.status <> 99 and o.status <> 4 and o.status <> 22 and o.status <> 6 and o.status <> 0 and o.status <> 14 and o.status <> 21";
        List<OrderPO> mobileReferralCodeOrders = MySQLDao.search(clearMobileReferralCodeSQL, new ArrayList<KVObject>(), OrderPO.class, new ArrayList<KVObject>(), conn);
        for(OrderPO order : mobileReferralCodeOrders) {
            // 查询视图
            String viewSQL = "select distinct b.referralCode from crm_salespersoninfo_vw b where b.SalesMobile = '" + order.getReferralCode() + "' and b.SalesPersonID <> '892069ED820D4C54B4365DBA5D368D5C'";
            List<SalesPersonInfo> infos = MySQLDao.search(viewSQL, new ArrayList<KVObject>(), SalesPersonInfo.class, new ArrayList<KVObject>(), conn);
            if(infos.size() == 0) {
                ;
            }
            else if (infos.size() == 1) {
                SalesPersonInfo info = infos.get(0);
                System.out.println("\r\n-->-->--> Order（" + order.getReferralCode() + "） Mobile Referral Code -->--> " + info.getReferralCode() + "\r\n");
                order.setReferralCode(info.getReferralCode());
                // MySQLDao.insertOrUpdate(order, conn);
            } else {
                new Exception("订单在视图中对应的销售人员大于 1 条");
            }
        }

        // 最后将推荐码是姓名的，改成视图对应的推荐码
        String clearNotSReferralCodeSQL = "select * from crm_order o where o.state = 0 and ( o.referralCode not like '%S%' ) and o.status <> 99 and o.status <> 4 and o.status <> 22 and o.status <> 6 and o.status <> 0 and o.status <> 14 and o.status <> 21";
        List<OrderPO> notSReferralCodeOrders = MySQLDao.search(clearNotSReferralCodeSQL, new ArrayList<KVObject>(), OrderPO.class, new ArrayList<KVObject>(), conn);
        for(OrderPO order : notSReferralCodeOrders) {
            // 查询视图
            String viewSQL = "select distinct b.referralCode from crm_salespersoninfo_vw b where b.SalesPerson = '" + order.getReferralCode() + "'";
            List<SalesPersonInfo> infos = MySQLDao.search(viewSQL, new ArrayList<KVObject>(), SalesPersonInfo.class, new ArrayList<KVObject>(), conn);
            if(infos.size() == 0) {
                ;
            }
            else if (infos.size() == 1) {
                SalesPersonInfo info = infos.get(0);
                System.out.println("\r\n-->-->--> Order（" + order.getReferralCode() + "） Mobile Referral Code -->--> " + info.getReferralCode() + "\r\n");
                order.setReferralCode(info.getReferralCode());
                // MySQLDao.insertOrUpdate(order, conn);
            } else {
                new Exception("订单在视图中对应的销售人员大于 1 条");
            }
        }

    }

    /**
     * 根据产品，造 productionInfo 表的数据
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年4月13日
     *
     * @throws Exception
     */
    public static void buildProductionInformations() throws Exception {

        Connection conn = Config.getConnection();

        String sql = "select * from crm_production p where p.state = 0";
        List<ProductionPO> productions = MySQLDao.query(sql, ProductionPO.class, new ArrayList<KVObject>(), conn);
        for(ProductionPO production : productions) {
            ProductionInfoPO infoPO = new ProductionInfoPO();
            infoPO.setContent1("Content 1");
            infoPO.setContent2("Content 2");
            infoPO.setContent3("Content 3");
            infoPO.setContent4("Content 4");
            infoPO.setContent5("Content 5");
            infoPO.setContent6("Content 6");
            infoPO.setContent7("Content 7");
            infoPO.setContent8("Content 8");
            infoPO.setContent9("Content 9");
            infoPO.setContent10("Content 10");
            infoPO.setTitle1("Title 1");
            infoPO.setTitle2("Title 2");
            infoPO.setTitle3("Title 3");
            infoPO.setTitle4("Title 4");
            infoPO.setTitle5("Title 5");
            infoPO.setTitle6("Title 6");
            infoPO.setTitle7("Title 7");
            infoPO.setTitle8("Title 8");
            infoPO.setTitle9("Title 9");
            infoPO.setTitle10("Title 10");
            infoPO.setIsDisplay1("3946");
            infoPO.setIsDisplay2("3946");
            infoPO.setIsDisplay3("3946");
            infoPO.setIsDisplay4("3946");
            infoPO.setIsDisplay5("3946");
            infoPO.setIsDisplay6("3946");
            infoPO.setIsDisplay7("3946");
            infoPO.setIsDisplay8("3946");
            infoPO.setIsDisplay9("3946");
            infoPO.setIsDisplay10("3946");
            infoPO.setProductionId(production.getId());
            infoPO.setDescription("产品描述");
            infoPO.setWebsiteDisplayName(production.getName());

            MySQLDao.insertOrUpdate(infoPO, conn);
        }

    }

}
