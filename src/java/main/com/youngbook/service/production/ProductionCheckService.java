package com.youngbook.service.production;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.production.ProductionCheckPO;
import com.youngbook.entity.vo.production.ProductionCheckVO;
import com.youngbook.service.BaseService;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

/**
 * Created by Administrator on 2015/4/9.
 */
@Component("productionCheckService")
public class ProductionCheckService extends BaseService {
    /**
     * 添加更新数据
     * @param productionCheck 对应实体类
     * @param user 对应操作用户类
     * @param conn 数据库链接
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(ProductionCheckPO productionCheck, UserPO user, Connection conn) throws Exception{
        int count = 0;
        // 新增
        if (productionCheck.getId().equals("")) {
            productionCheck.setSid(MySQLDao.getMaxSid("CRM_ProductionCheck", conn));
            productionCheck.setId(IdUtils.getUUID32());
            productionCheck.setState(Config.STATE_CURRENT);
            productionCheck.setOperatorId(user.getId());
            productionCheck.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(productionCheck, conn);
        }
        // 更新
        else {
            ProductionCheckPO temp = new ProductionCheckPO();
            temp.setSid(productionCheck.getSid());
            temp = MySQLDao.load(temp, ProductionCheckPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                productionCheck.setSid(MySQLDao.getMaxSid("CRM_ProductionCheck", conn));
                productionCheck.setState(Config.STATE_CURRENT);
                productionCheck.setOperatorId(user.getId());
                productionCheck.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(productionCheck, conn);
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    /**
     * 获取单独数据
     * @param id
     * @return
     * @throws Exception
     */
    public ProductionCheckPO loadProductionCheckPO(String id) throws Exception{
        ProductionCheckPO po = new ProductionCheckPO();

        po.setId(id);
        po.setState(Config.STATE_CURRENT);
        po = MySQLDao.load(po, ProductionCheckPO.class);

        return po;
    }

    /**
     * 删除的方法
     * @param productionCheck 对应的类对象
     * @param user 操作对象
     * @param conn 数据连接
     * @return
     * @throws Exception
     */
    public int delete(ProductionCheckPO productionCheck, UserPO user, Connection conn) throws Exception {
        int count = 0;

        productionCheck.setState(Config.STATE_CURRENT);
        productionCheck = MySQLDao.load(productionCheck, ProductionCheckPO.class);
        productionCheck.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(productionCheck, conn);
        if (count == 1) {
            productionCheck.setSid(MySQLDao.getMaxSid("CRM_ProductionCheck", conn));
            productionCheck.setState(Config.STATE_DELETE);
            productionCheck.setOperateTime(TimeUtils.getNow());
            productionCheck.setOperatorId(user.getId());
            count = MySQLDao.insert(productionCheck, conn);
        }

        if (count != 1) {
            throw new Exception("删除失败");
        }

        return count;
    }

    /**
     * 获取数据列表
     * @return
     * @throws Exception
     */
    public Pager list(ProductionCheckVO productionCheckVO,List<KVObject> conditions) throws Exception{

        HttpServletRequest request = ServletActionContext.getRequest();

        //组装SQL语句
        StringBuffer SQL = new StringBuffer();
        SQL.append("SELECT CP.Sid,CP.Id,CP.state,CP.ProductionId,CP.Checker1Id,CP.Checker1Time," );
        SQL.append("CP.Checker1Content,CP.Checker2Id,CP.Checker2Time,");
        SQL.append("CP.Checker2Content,CP.Checker3Id,CP.Checker3Time,CP.Checker3Content,");
        SQL.append(" CASE CP.Checker1Tag WHEN 1 THEN '通过' ELSE '未通过' END as Checker1TagName,");
        SQL.append(" CASE CP.Checker2Tag WHEN 1 THEN '通过' ELSE '未通过' END as Checker2TagName,");
        SQL.append(" CASE CP.Checker3Tag WHEN 1 THEN '通过' ELSE '未通过' END as Checker3TagName,");
        SQL.append("SU1.Name Checker1Name,SU2.Name Checker2Name,SU3.Name Checker3Name,");
        SQL.append("CPD.Name productionName FROM CRM_ProductionCheck CP,crm_production CPD, ");
        SQL.append("system_user SU1,system_user SU2,system_user SU3 ");
        SQL.append(" where CP.ProductionId=CPD.id AND CP.state=0 AND CPD.state=0");
        SQL.append(" AND SU1.state=0 AND SU2.state=0 AND SU3.state=0");
        SQL.append(" AND SU1.operatorId=cp.Checker1Id AND SU2.operatorId=cp.Checker2Id AND SU3.operatorId=cp.Checker3Id");

        //查询类型
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        Pager pager = Pager.query(SQL.toString(),productionCheckVO,conditions,request,queryType);
        return pager;
    }
}
