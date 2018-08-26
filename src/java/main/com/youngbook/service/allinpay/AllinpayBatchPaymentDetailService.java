package com.youngbook.service.allinpay;

import com.youngbook.common.Database;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.Config4Status;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.allinpay.AllinpayBatchPaymentDetailPO;
import com.youngbook.entity.po.allinpay.AllinpayBatchPaymentStatus;
import com.youngbook.entity.po.allinpay.AllinpayQueryDetailPO;
import com.youngbook.service.BaseService;

import java.sql.Connection;
import java.util.List;

public class AllinpayBatchPaymentDetailService extends BaseService {

    /**
     * 通联的新增批量代付明细
     *
     * 修改：邓超
     * 内容：添加注释
     * 时间：2015-8-28
     *
     * @throws Exception
     * @author 邓超
     * @throws Exception
     */
    public int insertAllinpaybatchPaymentDetail(AllinpayBatchPaymentDetailPO allinpayBatchPaymentDetail, UserPO user, Connection conn) throws Exception{
        int count = 0;
        // 新增
        if (allinpayBatchPaymentDetail.getId().equals("")) {
            allinpayBatchPaymentDetail.setSid(MySQLDao.getMaxSid("bank_AllinpayBatchPaymentDetail", conn));
            allinpayBatchPaymentDetail.setId(IdUtils.getUUID32());
            allinpayBatchPaymentDetail.setState(Config.STATE_CURRENT);
            allinpayBatchPaymentDetail.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
            allinpayBatchPaymentDetail.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(allinpayBatchPaymentDetail, conn);
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    /**
     * 修改人：姚章鹏
     * 时间：2015年8月18日10:46:39
     * 根据业务id查出明细表中的金额
     * @param bizId
     * @param conn
     * @return
     * @throws Exception
     */
    public AllinpayBatchPaymentDetailPO getCustomerAccountById(String bizId,Connection conn) throws Exception {
        AllinpayBatchPaymentDetailPO temp = new AllinpayBatchPaymentDetailPO();
        temp.setBizId(bizId);
        temp.setState(Config.STATE_CURRENT);
        temp = MySQLDao.load(temp, AllinpayBatchPaymentDetailPO.class, conn);
        return temp;
    }

    public List<AllinpayBatchPaymentDetailPO> getDetailsByParentId(String parentId, Connection conn) throws Exception {

        AllinpayBatchPaymentDetailPO detail = new AllinpayBatchPaymentDetailPO();
        detail.setState(Config.STATE_CURRENT);
        detail.setParentid(parentId);

        List<AllinpayBatchPaymentDetailPO> list = MySQLDao.query(detail, AllinpayBatchPaymentDetailPO.class);

        return list;
    }

    public List<AllinpayBatchPaymentDetailPO> getDetailsByParentId(String parentId) throws Exception {

        Connection conn = Config.getConnection();
        try {
            return getDetailsByParentId(parentId, conn);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }
    }

    /**
     *修改人：姚章鹏
     * 时间：2015年8月13日14:56:04
     * 内容：更新parentId并查询出数据
     */
    public List<AllinpayBatchPaymentDetailPO> updateOrSelectAllinpaybatchPaymentDetail(String productHomeId, Connection conn) throws Exception{

        try {
            //组装SQL语句
            StringBuffer sbSQLUpdate = new StringBuffer();
            sbSQLUpdate.append("update bank_allinpaybatchpaymentdetail  set parentId='"+ Database.encodeSQL(productHomeId)+"' where 1=1 and State=0  and  status = " + AllinpayBatchPaymentStatus.UNFINISH);
            //更新附表中的parentId
            MySQLDao.update(sbSQLUpdate.toString(), conn);
            //组装SQL语句
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append(" select * from bank_allinpaybatchpaymentdetail where 1=1 and State=0 and  status = " + AllinpayBatchPaymentStatus.UNFINISH);

            //  查询代付明细数据
            List list = MySQLDao.query(sbSQL.toString(), AllinpayBatchPaymentDetailPO.class, null, conn);
            return list;
        }
        catch (Exception e) {
            throw e;
        }

    }


    public AllinpayBatchPaymentDetailPO insertOrUpdate(AllinpayBatchPaymentDetailPO po, String userId, Connection conn) throws Exception{
        int count = 0;
        // 新增
        if (po.getId().equals("")) {
            po.setSid(MySQLDao.getMaxSid("bank_AllinpayBatchPaymentDetail", conn));
            po.setId(IdUtils.getUUID32());
            po.setState(Config.STATE_CURRENT);
            po.setOperatorId(userId);
            po.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(po, conn);
        }
        // 更新
        else {
            AllinpayBatchPaymentDetailPO temp = new AllinpayBatchPaymentDetailPO();
            temp.setSid(po.getSid());
            temp = MySQLDao.load(temp, AllinpayBatchPaymentDetailPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                po.setSid(MySQLDao.getMaxSid("bank_AllinpayBatchPaymentDetail", conn));
                po.setState(Config.STATE_CURRENT);
                po.setOperatorId(userId);
                po.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(po, conn);
                po = MySQLDao.load(po, AllinpayBatchPaymentDetailPO.class, conn);
            }
        }
        if (count != 1) {
            throw new Exception("更新数据 AllinpayBatchPaymentDetailPO 失败");
        }
        return po;
    }
}
