package com.youngbook.service.sale;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.sale.IProductionCommissionDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.sale.ProductionCommissionPO;
import com.youngbook.entity.vo.Sale.ProductionCommissionVO;
import com.youngbook.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

/**
 * 返佣率的service类
 * Created by 曾威恺 on 2016/03/21.
 */
@Component("productionCommissionService")
public class ProductionCommissionService extends BaseService {

    @Autowired
    IProductionCommissionDao productionCommissionDao;

    /**
     * 添加或修改数据，并修改数据状态
     * @param productionCommission
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(ProductionCommissionPO productionCommission, UserPO user, Connection conn) throws Exception{
        int count = 0;
       /* // 新增
        if (productionCommission.getId().equals("")) {
            productionCommission.setSid(MySQLDao.getMaxSid("sale_ProductionCommission", conn));
            productionCommission.setId(UUIDGenerator.getUUID32());
            productionCommission.setState(Config.STATE_CURRENT);
            productionCommission.setOperatorId(user.getId());
            productionCommission.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(productionCommission, conn);
        }
        // 更新
        else {
            ProductionCommissionPO temp = new ProductionCommissionPO();
            temp.setSid(productionCommission.getSid());
            temp = MySQLDao.load(temp, ProductionCommissionPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                productionCommission.setSid(MySQLDao.getMaxSid("sale_ProductionCommission", conn));
                productionCommission.setState(Config.STATE_CURRENT);
                productionCommission.setOperatorId(user.getId());
                productionCommission.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(productionCommission, conn);
            }
        }*/
        count=MySQLDao.insertOrUpdate(productionCommission,conn);
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;

    }

    /**
     * 根据制定的ID获取数据
     * @param id
     * @return
     * @throws Exception
     */
    public List<ProductionCommissionVO> getProductionCommissionVOByProctionId(String id,Connection conn) throws Exception{
        return  productionCommissionDao.getProductionCommissionVOByProctionId(id,conn);
    }

    public List<ProductionCommissionVO> getProductionCommissionVOByProctionId(String id) throws Exception{

        Connection conn = Config.getConnection();
        try {
            return  productionCommissionDao.getProductionCommissionVOByProctionId(id,conn);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }

    }

    /**
     * 根据条改编数据的状态
     * @param productionCommission
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete(ProductionCommissionPO productionCommission, UserPO user, Connection conn) throws Exception {
        int count = 0;

        count=MySQLDao.remove(productionCommission,user.getId(),conn);

        if (count != 1) {
            throw new Exception("删除失败");
        }

        return count;
    }

    public ProductionCommissionPO loadProductionCommissionPO(String id, Connection conn) throws Exception {
        return productionCommissionDao.loadProductionCommissionPOById(id, conn);
    }

    public Pager list(ProductionCommissionPO productionCommissionPO, List<KVObject> conditions, HttpServletRequest request,Connection conn) throws Exception{
        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("listProductionCommission","ProductionCommissionSQL",ProductionCommissionService.class);
        dbSQL.initSQL();


        StringBuffer sbSQL=new StringBuffer(dbSQL.getSQL());
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.search(sbSQL.toString(), dbSQL.getParameters(), productionCommissionPO, conditions, request, queryType, conn);
        return pager;

    }
}
