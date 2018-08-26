package com.youngbook.dao.production;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.MyException;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.NumberUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.production.ProductionPO;
import com.youngbook.entity.vo.production.ProductionVO;
import com.youngbook.entity.wvo.production.ProductionWVO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Lee on 2016/5/28.
 */
@Component("productionDao")
public class ProductionDaoImpl implements IProductionDao {

    public List<ProductionPO> listProductionPOByProductionNameOrProductionNO(String productionName, String productionNO, Connection conn) throws Exception {

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("listProductionPO", "ProductionDaoImplSQL", ProductionDaoImpl.class);
        dbSQL.addParameter4All("productionName", "%" + productionName + "%");
        dbSQL.addParameter4All("productionNO", "%"+productionNO+"%");
        dbSQL.initSQL();

        List<ProductionPO> list = MySQLDao.search(dbSQL, ProductionPO.class, conn);

        return list;
    }

    public List<ProductionVO> listAllProductionVO(Connection conn) throws Exception {

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("listAllProductionVO", "ProductionDaoImplSQL", ProductionDaoImpl.class);
        dbSQL.initSQL();


        StringBuffer sbSQL = new StringBuffer(dbSQL.getSQL());
        sbSQL.insert(0, "select DISTINCT _ft_.* from (").append(" ) _ft_ ");

        List<ProductionVO> list = MySQLDao.search(dbSQL, ProductionVO.class, conn);



        for (int i = 0; list != null && i < list.size(); i++){
            ProductionVO productionVO = list.get(i);


            /**
             * 计算剩余天数
             */
            String time1 = productionVO.getStopTime();
            Map<String, String> remainTime = TimeUtils.getDiffTime(time1, TimeUtils.getNowDate(), TimeUtils.Format_YYYY_MM_DD);

            productionVO.setRemainDays(0);
            if (StringUtils.isNumeric(remainTime.get("days"))) {
                int days = Integer.parseInt(remainTime.get("days"));
                productionVO.setRemainDays(days);
            }


            /**
             * 销售百分比
             */
            String percent = NumberUtils.format(productionVO.getSaleMoney() / productionVO.getSize(), 2);
            productionVO.setSaleMoneyPercent(Double.parseDouble(percent));
        }

        return list;
    }

//    public  boolean checkIsProductionBelongOnlineProject(String productionId, Connection conn) throws Exception {
//        return checkIsProductionBelongProject(productionId, "271758E97BC44BBBA67F01C1D7866308", conn);
//    }

    /**
     * 判断是否是给定产品是否属于某项目
     * @param productionId
     * @param projectId
     * @param conn
     * @return
     * @throws Exception
     */
    public boolean checkIsProductionBelongProject(String productionId, String projectId, Connection conn) throws Exception {

        // 271758E97BC44BBBA67F01C1D7866308 网站项目
        DatabaseSQL dbSQL = DatabaseSQL.getInstance("select p.* from crm_production p, crm_productionhome ph where 1=1 and p.state=0 and ph.state=0 and p.productHomeId=ph.Id and ph.ProjectId=? and p.id=?").addParameter(1, projectId).addParameter(2, productionId);

        List<ProductionPO> productionPOs = MySQLDao.search(dbSQL, ProductionPO.class, conn);

        if (productionPOs != null && productionPOs.size() == 1) {
            return true;
        }

        return false;
    }

    public ProductionPO getProductionById(String id, Connection conn) throws Exception {
        // 查询出订单购买了哪个产品
        ProductionPO production = new ProductionPO();
        production.setId(id);
        production.setState(Config.STATE_CURRENT);
        production = MySQLDao.load(production,ProductionPO.class,conn);
        return production;
    }

    public ProductionWVO getProductByProductionIdAndMoney(String productionId, double money, Connection conn) throws Exception {

        if (StringUtils.isEmpty(productionId)) {
            MyException.newInstance("无法获得产品分期编号").throwException();
        }

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("getProductByProductionIdAndMoney", "ProductionDaoImplSQL", this.getClass());
        dbSQL.addParameter4All("productionId", productionId).addParameter4All("money", money);
        dbSQL.initSQL();

        List<ProductionWVO> list = MySQLDao.search(dbSQL, ProductionWVO.class, conn);

        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }


    public List<ProductionWVO> getListProductByProductionIdAndMoney(String productionId, Connection conn) throws Exception {

        if (StringUtils.isEmpty(productionId)) {
            MyException.newInstance("无法获得产品分期编号").throwException();
        }

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("getProductByProductionIdAndMoney", "ProductionDaoImplSQL", this.getClass());
        dbSQL.addParameter4All("productionId", productionId);
        dbSQL.initSQL();

        List<ProductionWVO> list = MySQLDao.search(dbSQL, ProductionWVO.class, conn);

        if (list != null) {
            return list;
        }
        return null;
    }

}
