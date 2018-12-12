package com.youngbook.dao.production;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.MyException;
import com.youngbook.common.Pager;
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

    public ProductionPO insertOrUpdate(ProductionPO productionPO, Connection conn) throws Exception {
        MySQLDao.insertOrUpdate(productionPO, conn);

        return productionPO;
    }

    public List<ProductionPO> listProductionPOByProductionNameOrProductionNO(String productionName, String productionNO, Connection conn) throws Exception {

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("listProductionPO", "ProductionDaoImplSQL", ProductionDaoImpl.class);
        dbSQL.addParameter4All("productionName", "%" + productionName + "%");
        dbSQL.addParameter4All("productionNO", "%"+productionNO+"%");
        dbSQL.initSQL();

        List<ProductionPO> list = MySQLDao.search(dbSQL, ProductionPO.class, conn);

        return list;
    }

    public List<ProductionVO> getListProductionVO(ProductionVO productionVO, Connection conn) throws Exception {

        if (productionVO == null) {
            productionVO = new ProductionVO();
        }

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("4D541808")
                .addParameter4All("productionId", productionVO.getId())
                .addParameter4All("projectTypeId", productionVO.getProjectType());
        dbSQL.initSQL();

        StringBuffer sbSQL = new StringBuffer(dbSQL.getSQL());
        sbSQL.insert(0, "select DISTINCT _ft_.* from (").append(" ) _ft_ ");

        List<ProductionVO> list = MySQLDao.search(dbSQL, ProductionVO.class, conn);



        for (int i = 0; list != null && i < list.size(); i++){
            productionVO = list.get(i);


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


    public ProductionPO loadProductionById(String id, Connection conn) throws Exception {
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


    /**
     * @description 获取在售产品信息
     *
     * @author 苟熙霖
     *
     * @date 2018/11/27 14:10
     * @param productionVO
     * @param currentPage
     * @param showRowCount
     * @param conn
     * @return com.youngbook.common.Pager
     * @throws Exception
     */
    @Override
    public Pager getPagerProductionVO( ProductionVO productionVO, int currentPage, int showRowCount, Connection conn ) throws Exception {

        DatabaseSQL databaseSQL = DatabaseSQL.newInstance( "65FE18SG" );
        databaseSQL.initSQL();
        databaseSQL.init4Pager();
        Pager pager = MySQLDao.search( databaseSQL, productionVO,null, currentPage,showRowCount,null,conn );




        return pager;
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
