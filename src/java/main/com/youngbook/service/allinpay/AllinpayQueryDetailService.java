package com.youngbook.service.allinpay;

import com.youngbook.common.KVObject;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.allinpay.AllinpayBatchPaymentCallbackDetailPO;
import com.youngbook.entity.po.allinpay.AllinpayBatchPaymentPO;
import com.youngbook.entity.po.allinpay.AllinpayQueryDetailPO;
import com.youngbook.service.BaseService;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AllinpayQueryDetailService extends BaseService {

    /**
     * 修改通联查询的明细
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-9-10
     * 用法：new AllinpayQueryDetailPO().update()
     *
     * @author 邓超
     * @param   queryDetailPO 通联回调明细对象
     * @param userId
     * @param conn
     * @return AllinpayQueryDetailPO
     * @throws Exception
     */
    public Integer update(AllinpayQueryDetailPO queryDetailPO, String userId, Connection conn) throws Exception {

        queryDetailPO.setState(Config.STATE_UPDATE);
        queryDetailPO.setOperatorId(userId);
        queryDetailPO.setOperateTime(TimeUtils.getNow());

        Integer count = MySQLDao.update(queryDetailPO, conn);

        if(count != 1) {
            throw new Exception("修改数据失败");
        }

        queryDetailPO.setSid(MySQLDao.getMaxSid("bank_allinpayquerydetail", conn));
        queryDetailPO.setState(Config.STATE_CURRENT);
        queryDetailPO.setOperateTime(TimeUtils.getNow());

        return MySQLDao.insert(queryDetailPO, conn);

    }

    /**
     * 保存通联查询的明细
     *
     * 1、通过通联代付的回调明细对象，找到通联代付的原请求对象
     * 2、通过原请求对象，封装通联的查询对象
     * 3、返回查询对象以作其他处理
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-9-10
     * 用法：new AllinpayQueryDetailPO().save()
     *
     * @author 邓超
     * @param callbackDetailPO  通联回调明细对象
     * @param status            交易状态，0 成功、1 失败、2 全部、3 退票
     * @param type              查询类型，0 按完成日期、1 按提交日期
     * @param startDay          开始日，YYYYMMDDHHmmss
     * @param endDay            结束日，YYYYMMDDHHmmss
     * @param userId
     * @param conn
     * @return AllinpayQueryDetailPO
     * @throws Exception
     */
    public AllinpayQueryDetailPO save(AllinpayBatchPaymentCallbackDetailPO callbackDetailPO, Integer status, Integer type, String startDay, String endDay, String userId, Connection conn) throws Exception {

        // 校验数据
        if(callbackDetailPO == null || conn == null) {
            throw new Exception("通联回调明细实体或者数据库连接为 null");
        }

        // 通过通联返回的明细，查询原代付请求的主表
        String sql =
                "SELECT * " +
                        "FROM bank_allinpaybatchpayment m, " +
                        "bank_allinpaybatchpaymentcallback c, " +
                        "bank_allinpaybatchpaymentcallbackdetail cd " +
                        "WHERE m.state = 0 " +
                        "AND c.state = 0 " +
                        "AND cd.state = 0 " +
                        "AND cd.parentId = c.id " +
                        "AND c.req_sn = m.req_sn " +
                        "AND cd.parentId = '" + callbackDetailPO.getParentId() + "' " +
                        "AND cd.sn = '" + callbackDetailPO.getSn() + "'";
        List<AllinpayBatchPaymentPO> paymentPOList = MySQLDao.query(sql, AllinpayBatchPaymentPO.class, new ArrayList<KVObject>());

        if(paymentPOList.size() != 1) {
            throw new Exception("没有查询到符合的原请求记录");
        }

        AllinpayBatchPaymentPO paymentPO = paymentPOList.get(0);

        // 先查询是否已经存在相同数据，存在则直接返回，而不是新增
        AllinpayQueryDetailPO tempPO = new AllinpayQueryDetailPO();
        tempPO.setState(Config.STATE_CURRENT);
        tempPO.setQuery_sn(paymentPO.getReq_sn());
        tempPO = MySQLDao.load(tempPO, AllinpayQueryDetailPO.class);
        if(tempPO != null) {
            return tempPO;
        }

        // 构造查询明细实体
        AllinpayQueryDetailPO queryDetailPO = new AllinpayQueryDetailPO();
        queryDetailPO.setMerchant_id(paymentPO.getMerchant_id());
        queryDetailPO.setQuery_sn(paymentPO.getReq_sn());
        queryDetailPO.setStatus(String.valueOf(status));
        queryDetailPO.setType(String.valueOf(type));
        queryDetailPO.setStart_day(startDay);
        queryDetailPO.setEnd_day(endDay);
        queryDetailPO.setSid(MySQLDao.getMaxSid("bank_allinpayquerydetail", conn));
        queryDetailPO.setId(UUID.randomUUID().toString());
        queryDetailPO.setState(Config.STATE_CURRENT);
        queryDetailPO.setOperatorId(userId);
        queryDetailPO.setOperateTime(TimeUtils.getNow());

        // 保存数据
        Integer queryDetailCount = MySQLDao.insert(queryDetailPO, conn);

        // 返回实体
        return queryDetailCount == 1 ? queryDetailPO : null;

    }

}
