package com.youngbook.service.production;

import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.MoneyUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.production.IProductionCompositionDao;
import com.youngbook.dao.sale.ISalemanGroupDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.production.ProductionPO;
import com.youngbook.entity.po.production.ProductionCompositionPO;
import com.youngbook.entity.po.sale.SalemanGroupPO;
import com.youngbook.entity.vo.production.ProductionCompositionVO;
import com.youngbook.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * 产品构成服务类
 *
 * 修改：邓超
 * 内容：优化代码
 * 时间：2016年4月20日
 */
@Component("productionCompositionService")
public class ProductionCompositionService extends BaseService {

    @Autowired
    IProductionCompositionDao productionCompositionDao;

    @Autowired
    ISalemanGroupDao salemanGroupDao;


    public ProductionCompositionPO getProductionCompositionPOByProductionIdAndMoney(String productionId, Double money, Connection conn) throws Exception {
        return productionCompositionDao.getProductionCompositionPOByProductionIdAndMoney(productionId, money, conn);
    }

    private ProductionPO production = new ProductionPO();

    /**
     * HOPEWEALTH-1319<br/>
     * 根据产品ID来获取其所有产品构成的最大返佣率<br/>
     *
     * @param productionId
     * @param conn
     * @return
     * @throws Exception
     */
    public double getMaxCommission(String productionId, Connection conn) throws Exception {
        double maxCommission = 0.0;
        List<ProductionCompositionVO> list = this.listCompositions(productionId, conn);
        // 遍历，取最大返佣值
        for (ProductionCompositionVO vo: list) {
            if (vo != null && vo.getCommissionRate() != Double.MAX_VALUE && vo.getCommissionRate() > maxCommission) {
                maxCommission = vo.getCommissionRate();
            }
        }

        return maxCommission;
    }

    /**
     * HOPEWEALTH-1319>
     * 根据产品ID获取所有产品构成的简单信息，无分页，包含返佣值的计算
     * @return
     * @throws Exception
     */
    public List<ProductionCompositionVO> listCompositions(String productionId, Connection conn) throws Exception {

        // 查询语句
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT");
        sb.append(" 	cps.sid,");
        sb.append(" 	cps.id,");
        sb.append(" 	cps.ProductionId,");
        sb.append(" 	cps.commissionLevel,");
        sb.append(" 	cps.needCommissionCorrection,");
        sb.append(" 	cps.needCustomerTypeCommissionCorrection");
        sb.append(" FROM");
        sb.append(" 	crm_productioncomposition AS cps,");
        sb.append(" 	crm_production AS pro");
        sb.append(" WHERE");
        sb.append(" 	cps.ProductionId = pro.id");
        sb.append(" AND cps.state = 0");
        sb.append(" AND pro.state = 0");
        sb.append(" AND pro.id = '" + productionId + "'");

        // 获取 web 销售组
        String salemanGroupId = Config.getSystemVariable("web.default.saleGroupId");
        SalemanGroupPO groupPO = salemanGroupDao.loadSalemanGroupPO(salemanGroupId, conn);
        if(groupPO == null) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "数据异常").throwException();
        }

        List<ProductionCompositionVO> list = MySQLDao.query(sb.toString(), ProductionCompositionVO.class, new ArrayList<KVObject>(), conn);
        // 遍历，set返佣率
        for (ProductionCompositionVO vo: list) {
            // 获取返佣等级(ID)
            String level = vo.getCommissionLevel();
            // 是否开启返佣修正
            int needCorrection = vo.getNeedCommissionCorrection();
            // 是否开启客户类型修正
            int needCustomerTypeCorrection = vo.getNeedCustomerTypeCommissionCorrection();
            // 计算返佣率
            Double rate = MoneyUtils.calcCommissionRate(level, groupPO.getAreaId(), needCorrection, needCustomerTypeCorrection, conn);
            vo.setCommissionRate(rate);
        }

        return list;
    }


    /**
     * HOPEWEALTH-1299
     * 根据产品ID获取所有产品构成
     * HOPEWEALTH-1319
     * 增加计算返佣率业务
     * @return
     * @throws Exception
     */
    public List<ProductionCompositionVO> listCompositions(String productionId, Integer currentPage, Integer showRowCount, Connection conn) throws Exception {

        // 查询语句
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT");
        sb.append(" 	cps.sid,");
        sb.append(" 	cps.id,");
        sb.append(" 	cps.ProductionId,");
        sb.append(" 	cps.`Name`,");
        sb.append(" 	cps.SizeStart,");
        sb.append(" 	cps.SizeStop,");
        sb.append(" 	cps.ExpectedYield,");
        sb.append(" 	cps.FloatingRate,");
        sb.append(" 	cps.BuyingRate,");
        sb.append(" 	cps.PayRate,");
        sb.append(" 	cps.commissionLevel,");
        sb.append(" 	cps.needCommissionCorrection,");
        sb.append(" 	cps.needCustomerTypeCommissionCorrection");
        sb.append(" FROM");
        sb.append(" 	crm_productioncomposition AS cps,");
        sb.append(" 	crm_production AS pro");
        sb.append(" WHERE");
        sb.append(" 	cps.ProductionId = pro.id");
        sb.append(" AND cps.state = 0");
        sb.append(" AND pro.state = 0");
        sb.append(" AND pro.id = ?");
        sb.append(" GROUP BY cps.id");
        sb.append(" ORDER BY");
        sb.append(" 	cps.operateTime desc");

        // 查询条件
        List<KVObject> kvObjects = new ArrayList<KVObject>();
        KVObject productionIdKV = new KVObject();
        productionIdKV.setIndex(1);
        productionIdKV.setValue(productionId);
        kvObjects.add(productionIdKV);

        // 查询
        Pager pager = MySQLDao.search(sb.toString(), kvObjects, new ProductionCompositionVO(), new ArrayList<KVObject>(), currentPage, showRowCount, null, conn);

        // 获取 web 销售组
        String salemanGroupId = Config.getSystemVariable("web.default.saleGroupId");
        SalemanGroupPO groupPO = salemanGroupDao.loadSalemanGroupPO(salemanGroupId, conn);
        if(groupPO == null) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "数据异常").throwException();
        }

        List list = pager.getData();
        // 遍历，set返佣率的值
        for (Object o: list) {
            ProductionCompositionVO vo = (ProductionCompositionVO) o;

            // 获取返佣等级(ID)
            String level = vo.getCommissionLevel();
            // 是否开启返佣修正
            int needCorrection = vo.getNeedCommissionCorrection();
            // 是否开启客户类型修正

            int needCustomerTypeCorrection = vo.getNeedCustomerTypeCommissionCorrection();
            // 计算返佣率
            Double rate = MoneyUtils.calcCommissionRate(level, groupPO.getAreaId(), needCorrection, needCustomerTypeCorrection, conn);
            vo.setCommissionRate(rate);

        }

        return list;
    }

    /**
     * 列出产品构成列表
     *
     * 修改：邓超
     * 内容：添加注释
     * 时间：2016年4月20日
     *
     * @param productionComposition
     * @param conditions
     * @return
     * @throws Exception
     */
    public Pager list(ProductionCompositionVO productionComposition, List<KVObject> conditions, int currentPage, int showPageCount, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("9E071805");
        dbSQL.initSQL();


        Pager pager = MySQLDao.search(dbSQL, productionComposition, conditions, currentPage, showPageCount, null, conn);

        return pager;
    }

    /**
     * 插入或修改
     *
     * 修改：邓超
     * 内容：添加注释
     * 时间：2016年4月20日
     *
     * @param productionComposition
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(ProductionCompositionPO productionComposition, UserPO user, Connection conn) throws Exception {

        if (user == null) {
            MyException.newInstance("无法获得用户信息，操作产品构成失败").throwException();
        }

        int count = 0;
        // 新增
        if (productionComposition.getId().equals("")) {
            productionComposition.setSid(MySQLDao.getMaxSid("crm_productioncomposition", conn));
            productionComposition.setId(IdUtils.getUUID32());
            productionComposition.setState(Config.STATE_CURRENT);
            productionComposition.setOperatorId(user.getId());
            productionComposition.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(productionComposition, conn);
        }
        // 更新
        else {
            ProductionCompositionPO temp = new ProductionCompositionPO();
            temp.setSid(productionComposition.getSid());
            temp = MySQLDao.load(temp, ProductionCompositionPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                productionComposition.setSid(MySQLDao.getMaxSid("crm_productioncomposition", conn));
                productionComposition.setState(Config.STATE_CURRENT);
                productionComposition.setOperatorId(user.getId());
                productionComposition.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(productionComposition, conn);
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    /**
     * 删除数据
     *
     * 修改：邓超
     * 内容：添加注释
     * 时间：2016年4月20日
     *
     * @param productionComposition
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete(ProductionCompositionPO productionComposition, UserPO user, Connection conn) throws Exception {
        int count = 0;
        productionComposition = MySQLDao.load(productionComposition, ProductionCompositionPO.class);
        productionComposition.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(productionComposition, conn);
        if (count == 1) {
            productionComposition.setSid(MySQLDao.getMaxSid("crm_productioncomposition", conn));
            productionComposition.setState(Config.STATE_DELETE);
            productionComposition.setOperateTime(TimeUtils.getNow());
            productionComposition.setOperatorId(user.getId());
            count = MySQLDao.insert(productionComposition, conn);
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }





    public ProductionPO getProduction() {
        return production;
    }
    public void setProduction(ProductionPO production) {
        this.production = production;
    }

}

