package com.youngbook.service.sale;

import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.sale.ProductionTransferPO;
import com.youngbook.entity.po.sale.ProductionTransferStatus;
import com.youngbook.service.BaseService;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * Created by zsq on 4/22/2015.
 */
@Component("productionTransferService")
public class ProductionTransferService extends BaseService {

    /**
     * 删除
     * @param productionTransfer
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete(ProductionTransferPO productionTransfer, UserPO user, Connection conn) throws Exception {
        int count = 0;

        productionTransfer.setState(Config.STATE_CURRENT);
        productionTransfer = MySQLDao.load(productionTransfer, ProductionTransferPO.class);
        productionTransfer.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(productionTransfer, conn);
        if (count == 1) {
            productionTransfer.setSid(MySQLDao.getMaxSid("Sale_ProductionTransfer", conn));
            productionTransfer.setState(Config.STATE_DELETE);
            productionTransfer.setOperateTime(TimeUtils.getNow());
            productionTransfer.setOperatorId(user.getId());
            count = MySQLDao.insert(productionTransfer, conn);
        }

        if (count != 1) {
            throw new Exception("删除失败");
        }

        return count;
    }

    public ProductionTransferPO loadProductionTransferPO(String id) throws Exception{
        ProductionTransferPO po = new ProductionTransferPO();

        po.setId(id);
        po.setState(Config.STATE_CURRENT);
        po = MySQLDao.load(po, ProductionTransferPO.class);

        return po;
    }

    public int insertOrUpdate(ProductionTransferPO productionTransfer, UserPO user, Connection conn) throws Exception{
        int count = 0;
        // 新增
        if (productionTransfer.getId().equals("")) {
            productionTransfer.setSid(MySQLDao.getMaxSid("Sale_ProductionTransfer", conn));
            productionTransfer.setId(IdUtils.getUUID32());
            productionTransfer.setState(Config.STATE_CURRENT);
            productionTransfer.setOperatorId(user.getId());
            productionTransfer.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(productionTransfer, conn);
        }
        // 更新
        else {
            ProductionTransferPO temp = new ProductionTransferPO();
            temp.setSid(productionTransfer.getSid());
            temp = MySQLDao.load(temp, ProductionTransferPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                productionTransfer.setSid(MySQLDao.getMaxSid("Sale_ProductionTransfer", conn));
                productionTransfer.setState(Config.STATE_CURRENT);
                productionTransfer.setOperatorId(user.getId());
                productionTransfer.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(productionTransfer, conn);
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    /**
     * 获取是树型下拉列表
     * @return
     */
    public JSONArray getStatusTree(){
        ProductionTransferStatus PTStatus = new ProductionTransferStatus();
        JSONArray array = PTStatus.toJsonArray();
        return array;
    }
}
