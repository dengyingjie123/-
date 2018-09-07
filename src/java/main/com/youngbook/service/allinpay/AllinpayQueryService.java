package com.youngbook.service.allinpay;

import com.aipg.common.*;
import com.aipg.transquery.QTDetail;
import com.aipg.transquery.QTransRsp;
import com.aipg.transquery.TransQueryReq;
import com.allinpay.XmlTools;
import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.Config4Status;
import com.youngbook.common.config.Config4W;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.bank.TranxServiceImpl;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.allinpay.*;
import com.youngbook.service.BaseService;
import com.youngbook.service.customer.CustomerRefundService;
import com.youngbook.service.sale.PaymentPlanService;
import com.youngbook.service.system.LogService;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AllinpayQueryService extends BaseService {

    AllinpayBatchPaymentService paymentService = new AllinpayBatchPaymentService();
    AllinpayQueryDetailService detailService = new AllinpayQueryDetailService();

    public void query(Integer status, Integer type, String startDay, String endDay, String userId, Connection conn) throws Exception {

        String xml = null;

        // 获取未被查询过的通联回调明细
        String sql = "select * from bank_allinpaybatchpaymentcallbackdetail cd where cd.state = 0 and cd.queryStatus = " + Config4Status.STATUS_ALLINPAY_CALLBACK_QUERY_UNFINISH + " limit 0, 1";
        List<AllinpayBatchPaymentCallbackDetailPO> details = MySQLDao.query(sql, AllinpayBatchPaymentCallbackDetailPO.class, new ArrayList<KVObject>());

        List<AllinpayQueryDetailPO> queryDetails = new ArrayList<AllinpayQueryDetailPO>();

        // 新增通联结果查询的明细，并组装到集合
        for (AllinpayBatchPaymentCallbackDetailPO detail : details) {
            AllinpayQueryDetailPO queryDetail = this.detailService.save(detail, status, type, startDay, endDay, userId, conn);
            if (queryDetail != null) {
                queryDetails.add(queryDetail);
            }
        }

        // 有查询的明细才能构建查询主表
        if (queryDetails.size() > 0) {

            // 新增并获取到主表数据
            AllinpayQueryPO queryMain = this.save(userId, conn);

            // 把明细和主表关联：明细的父 ID 设为主表的 ID
            for (AllinpayQueryDetailPO queryDetail : queryDetails) {

                queryDetail.setParentId(queryMain.getId());
                Integer count = detailService.update(queryDetail, userId, conn);
                if (count != 1) {
                    throw new Exception("更新数据失败");
                }

            }

            // 组装 xml
            xml = buildXML(queryMain, queryDetails, "", "");

        }

        if (xml == null) {
            throw new Exception("报文数据组织失败");
        }

        String url = Config.getSystemConfig("bank.pay.allinpay.auth.url");

        // 解析返回的 XML
        TranxServiceImpl tranxService = new TranxServiceImpl();
        String backResp = tranxService.isFront(xml, false, url);
        parseXML(backResp);

    }

    /**
     * 创建人：姚章鹏
     * <p/>
     * 功能：组装交易结果查询的 xml
     * 注意：若不填 QUERY_SN 则必填开始时间和结束时间
     *
     * @param queryMain   交易结果主表数据
     * @param queryDetail 交易结果明细表
     * @param startDate   开始时间
     * @param endDate     结束时间
     * @throws Exception
     */
    public String buildXML(AllinpayQueryPO queryMain, List<AllinpayQueryDetailPO> queryDetail, String startDate, String endDate) throws Exception {

        String xml = "";

        // 创建请求对象
        AipgReq aipgReq = new AipgReq();
        InfoReq info = makeReqTrade(queryMain.getTrx_code(), queryMain);
        aipgReq.setINFO(info);

        // 组装 QueryReq
        for (AllinpayQueryDetailPO query : queryDetail) {

            TransQueryReq dr = new TransQueryReq();
            aipgReq.addTrx(dr);

            dr.setMERCHANT_ID(query.getMerchant_id()); // 商户号
            dr.setQUERY_SN(query.getQuery_sn());
            dr.setSTATUS(Integer.parseInt(query.getStatus()));
            dr.setTYPE(Integer.parseInt(query.getType()));

            // 若不填 QUERY_SN 则必填开始时间和结束时间
            if (query.getQuery_sn() == null || "".equals(query.getQuery_sn())) {
                dr.setSTART_DAY(startDate);
                dr.setEND_DAY(endDate);
            }

        }

        xml = XmlTools.buildXml(aipgReq, true);
        return xml;

    }

    /**
     * 解析通联结果查询反馈的 XML
     * <p/>
     * 作者：邓超
     * 时间：2015-9-11
     *
     * @param callbackResponse
     * @throws Exception
     * @author 邓超
     */
    public void parseXML(String callbackResponse) throws Exception {

        LogService.info("开始解析通联支付结果查询的反馈", this.getClass());
        LogService.info(callbackResponse, this.getClass());

        AipgRsp aipgRsp = XSUtil.xmlRsp(callbackResponse);

        if (aipgRsp == null || aipgRsp.getINFO() == null || aipgRsp.getTrxData() == null || aipgRsp.getTrxData().size() == 0) {
            throw new Exception("通联支付结果查询的反馈解析为空");
        }

        // 解析出来不为空
        InfoRsp info = aipgRsp.getINFO();

        String callbackHeadId = "";

        // 受理成功，插入返回主表
        AllinpayQueryCallbackPO callbackPO = new AllinpayQueryCallbackPO();
        callbackPO.setTrx_code(info.getTRX_CODE());
        callbackPO.setVersion(info.getVERSION());
        callbackPO.setData_type(info.getDATA_TYPE());
        callbackPO.setReq_sn(info.getREQ_SN());
        callbackPO.setRet_code(info.getRET_CODE());
        callbackPO.setErr_msg(info.getERR_MSG());
        callbackPO.setSigned_msg(info.getSIGNED_MSG());

        // 通联的新增批量代付返回主表 id
        callbackHeadId = insert(callbackPO);

        // 解析 info
        QTransRsp qrsp = (QTransRsp)aipgRsp.getTrxData().get(0);
        // 解析 detalis
        List<QTDetail> details = qrsp.getDetails();

        // 保存通联批量返回代付名表
        for (QTDetail ptDetail : details) {

            // 保存通联支付反馈明细信息
            AllinpayQueryCallbackDetailPO detailPO = insertAllinpayQueryCallbackDetail(callbackHeadId, ptDetail);
            if (detailPO != null) {

                    // 根据返回值，进行进一步测处理
                    updateBizStatusFromCallback(ptDetail);

            }
        }

    }

    /**
     * 修改系统业务的状态
     * <p/>
     * 修改人：姚章鹏
     * 时间：2015年8月13日15:00:44
     * 内容：根据返回的 sn 匹配明细表中是否有相应的 sn
     *
     * @param detail
     * @throws Exception
     */
    public void updateBizStatusFromCallback(QTDetail detail) throws Exception {

        // 查询请求前的 sn 和返回的 sn 是否匹配
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" bank_allinpaybatchpaymentdetail ");
        sql.append(" WHERE ");
        sql.append(" 1 = 1 ");
        sql.append(" AND state = " + Config.STATE_CURRENT + " ");
        sql.append(" AND status = " + AllinpayBatchPaymentStatus.UNFINISH + "  ");
        sql.append(" AND sn = '" + Database.encodeSQL(detail.getSN()) + "' ");

        List<AllinpayBatchPaymentDetailPO> list = MySQLDao.query(sql.toString(), AllinpayBatchPaymentDetailPO.class, new ArrayList<KVObject>());

        // 如果 ret_code 是 0000 代表是成功的
        int status = AllinpayBatchPaymentStatus.FAILED;

        if (detail.getRET_CODE().equals("0000")) {
            status = AllinpayBatchPaymentStatus.SUCCESS;
        }

        if (list != null && list.size() == 1) {

            Connection conn = Config.getConnection();

            AllinpayBatchPaymentDetailPO paymentDetail = list.get(0);

            try {

                conn.setAutoCommit(false);

                // 修改明细表状态
                paymentDetail.setState(Config.STATE_UPDATE);
                MySQLDao.update(paymentDetail, conn);
                // 增加一条新记录
                paymentDetail.setSid(MySQLDao.getMaxSid("bank_allinpaybatchpaymentdetail", conn));
                paymentDetail.setState(Config.STATE_CURRENT);
                paymentDetail.setStatus(status);
                paymentDetail.setOperateTime(TimeUtils.getNow());
                MySQLDao.insert(paymentDetail, conn);

                // 通过 ParentId 修改主表的状态
                paymentService.updateAllinpaybatchpaymentStatus(paymentDetail.getParentid(), status, conn);
                conn.commit();

                // 修改对应业务的状态
                try {

                    // 获取业务类型
                    int bizType = paymentDetail.getBizType();

                    // 业务类型是提现类型
                    if (bizType == Config4Status.STATUS_CUSTOMER_WITHDRAW_TYPE) {
                    /*
                    Connection withdrawConn = Config.getConnection();
                    try {
                        // 执行提现业务，独立的事务
                        withdrawConn.setAutoCommit(false);
                        CustomerWithdrawService withdrawService = new CustomerWithdrawService();
                        withdrawService.executionWithdrawType(list, detail, withdrawConn);
                        withdrawConn.commit();
                    } catch (Exception e) {
                        withdrawConn.rollback();
                        throw e;
                    } finally {
                        Database.close(withdrawConn);
                    }
                    */
                    }

                    // 业务类型是兑付类型
                    else if (bizType == AllinpayBatchPaymentType.Payment) {
                        Connection paymentConn = Config.getConnection();
                        try {
                            // 执行兑付业务，独立的事务
                            paymentConn.setAutoCommit(false);
                            PaymentPlanService paymentPlanService = new PaymentPlanService();
                            paymentPlanService.executionPaymentType(list, detail, paymentConn);
                            paymentConn.commit();
                        } catch (Exception e) {
                            paymentConn.rollback();
                            throw e;
                        } finally {
                            Database.close(paymentConn);
                        }
                    }

                    // 业务类型是银行卡认证
//                    else if (bizType == Config4Status.STATUS_CUSTOMER_BANKCARD_TYPE) {
//                        Connection bankAuthenticationConn = Config.getConnection();
//                        try {
//                            bankAuthenticationConn.setAutoCommit(false);
//                            // 执行验证业务，独立的事务
//                            CustomerBankAuthenticationService service = new CustomerBankAuthenticationService();
//                            service.executionBankAuthenticationType(list, detail, bankAuthenticationConn);
//                            bankAuthenticationConn.commit();
//                        } catch (Exception e) {
//                            bankAuthenticationConn.rollback();
//                            throw e;
//                        } finally {
//                            Database.close(bankAuthenticationConn);
//                        }
//                    }

                    // 业务类型是退款类型
                    else if (bizType == AllinpayBatchPaymentType.Refund) {
                        Connection refundConn = Config.getConnection();
                        try {
                            // 执行退款业务，独立的事务
                            CustomerRefundService customerRefundService = new CustomerRefundService();
                            customerRefundService.executionRefundType(list, refundConn);
                        } catch (Exception e) {
                            refundConn.rollback();
                            throw e;
                        } finally {
                            if (refundConn != null) {
                                refundConn.close();
                            }
                        }
                    }

                    // 修改通联代付回调明细的结果查询状态
                    Connection queryStatusConn = Config.getConnection();
                    try {
                        updateCallbackDetailQueryStatus(paymentDetail, true, queryStatusConn);
                    } catch (Exception e) {
                        e.printStackTrace();
                        conn.rollback();
                        throw e;
                    } finally {
                        if(queryStatusConn != null) {
                            queryStatusConn.close();
                        }
                    }

                } catch (Exception e) {

                    // 修改通联代付回调明细的结果查询状态
                    Connection queryStatusConn = Config.getConnection();
                    try {
                        updateCallbackDetailQueryStatus(paymentDetail, false, queryStatusConn);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        conn.rollback();
                        throw e1;
                    } finally {
                        if(queryStatusConn != null) {
                            queryStatusConn.close();
                        }
                    }

                    throw e;

                }

            } catch (Exception e) {
                conn.rollback();
                throw e;
            } finally {
                Database.close(conn);
            }

        }
    }

    /**
     * 修改通联回调的结果查询状态
     *
     * 作者：邓超
     * 时间：2015-9-19
     *
     * @param paymentDetail
     * @param flag
     * @param conn
     * @return
     * @throws Exception
     */
    private void updateCallbackDetailQueryStatus(AllinpayBatchPaymentDetailPO paymentDetail, Boolean flag, Connection conn) throws Exception {

        Boolean result = true;
        String sn = paymentDetail.getSn();
        Integer queryStatus = flag ? Config4Status.STATUS_ALLINPAY_CALLBACK_QUERY_SUCCESS : Config4Status.STATUS_ALLINPAY_CALLBACK_QUERY_FAILED;

        // 查询通联代付返回明细
        String sql = "select * from bank_allinpaybatchpaymentcallbackdetail cd where cd.state = 0 and cd.sn = '" + sn + "'";
        List<AllinpayBatchPaymentCallbackDetailPO> details = MySQLDao.query(sql, AllinpayBatchPaymentCallbackDetailPO.class, new ArrayList<KVObject>(), conn);

        // 修改返回明细的结果查询状态
        for(AllinpayBatchPaymentCallbackDetailPO detail : details) {

            try {
                detail.setState(Config.STATE_UPDATE);
                detail.setOperateTime(TimeUtils.getNow());
                Integer count = MySQLDao.update(detail, conn);

                if (count != 1) {
                    throw new Exception("修改通联回调的结果查询状态失败");
                }

                detail.setSid(MySQLDao.getMaxSid("bank_allinpaybatchpaymentcallbackdetail", conn));
                detail.setState(Config.STATE_CURRENT);
                detail.setQueryStatus(queryStatus);
                detail.setOperateTime(TimeUtils.getNow());

                MySQLDao.insert(detail, conn);
            } catch (Exception e) {

                throw e;

            }
        }

    }

    /**
     * 插入通联结果查询回调的明细
     *
     * @param parentId
     * @param detail
     * @return
     * @throws Exception
     */
    public AllinpayQueryCallbackDetailPO insertAllinpayQueryCallbackDetail(String parentId, QTDetail detail) throws Exception {

        Connection conn = Config.getConnection();
        int count = 0;

        try {

            AllinpayQueryCallbackDetailPO detailPO = new AllinpayQueryCallbackDetailPO();
            detailPO.setSid(MySQLDao.getMaxSid("bank_allinpayquerycallbackdetail", conn));
            detailPO.setId(IdUtils.getUUID32());
            detailPO.setState(Config.STATE_CURRENT);
            detailPO.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
            detailPO.setOperateTime(TimeUtils.getNow());

            detailPO.setSn(detail.getSN());
            detailPO.setRet_code(detail.getRET_CODE());
            detailPO.setErr_msg(detail.getERR_MSG());
            detailPO.setBatchid(detail.getBATCHID());
            detailPO.setTrxdir(detail.getTRXDIR());
            detailPO.setSettDay(detail.getSETTDAY());
            detailPO.setFinTime(detail.getFINTIME());
            detailPO.setSubmitTime(detail.getSUBMITTIME());
            detailPO.setAccount_no(detail.getACCOUNT_NO());
            detailPO.setAccount_name(detail.getACCOUNT_NAME());
            detailPO.setAmount(detail.getAMOUNT());
            detailPO.setCust_userId(detail.getCUST_USERID());
            detail.setREMARK(detail.getREMARK());
            detailPO.setParentId(parentId);

            count = MySQLDao.insert(detailPO, conn);

            if (count != 1) {
                throw new Exception("数据库异常");
            }

            return detailPO;

        } catch (Exception e) {
            throw e;
        } finally {
            Database.close(conn);
        }
    }

    /**
     * 新增通联结果查询的回调主表
     * <p/>
     * 作者：邓超
     * 时间：2015-9-11
     *
     * @param callbackPO
     * @return String
     * @throws Exception
     * @author 邓超
     */
    public String insert(AllinpayQueryCallbackPO callbackPO) throws Exception {
        int count = 0;
        Connection conn = Config.getConnection();

        try {

            String id = IdUtils.getUUID32();
            if (callbackPO.getId().equals("")) {
                callbackPO.setSid(MySQLDao.getMaxSid("bank_allinpayquerycallback", conn));
                callbackPO.setId(id);
                callbackPO.setState(Config.STATE_CURRENT);
                callbackPO.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
                callbackPO.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(callbackPO, conn);
            }

            if (count != 1) {
                throw new Exception("数据库异常");
            }

            return id;
        } catch (Exception e) {
            throw e;
        } finally {
            Database.close(conn);
        }
    }

    /**
     * 组装报文头部
     *
     * @param trxcod
     * @return 日期：2015年8月13日14:47:48
     */
    private InfoReq makeReqTrade(String trxcod, AllinpayQueryPO allinpayQuery) throws Exception {

        InfoReq info = new InfoReq();
        info.setTRX_CODE(trxcod);
        info.setREQ_SN(allinpayQuery.getReq_sn());
        info.setUSER_NAME(Config.getSystemConfig("bank.pay.allinpay.userName"));
        info.setUSER_PASS(Config.getSystemConfig("bank.pay.allinpay.password"));
        // info.setLEVEL(String.valueOf(allinpayQuery.getLevel()));
        info.setDATA_TYPE(String.valueOf(allinpayQuery.getData_type()));
        info.setVERSION(allinpayQuery.getVersion());
        return info;

    }

    /**
     * 保存通联查询的主表
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-9-10
     * 用法：new AllinpayQueryPO().save()
     *
     * @param userId
     * @param conn
     * @return AllinpayQueryDetailPO
     * @throws Exception
     * @author 邓超
     */
    public AllinpayQueryPO save(String userId, Connection conn) throws Exception {

        AllinpayQueryPO main = new AllinpayQueryPO();

        // 基本数据
        main.setSid(MySQLDao.getMaxSid("bank_allinpayquery", conn));
        main.setId(UUID.randomUUID().toString());
        main.setState(Config.STATE_CURRENT);
        main.setOperatorId(userId);
        main.setOperateTime(TimeUtils.getNow());

        // 通联结果查询主表数据
        main.setTrx_code(String.valueOf(Config4W.TRX_TRADE));
        main.setVersion(String.valueOf(Config4W.VERSION));
        main.setData_type(String.valueOf(Config4W.DATA_TYPE));
        main.setUser_name(Config.getSystemConfig("bank.pay.allinpay.userName"));
        main.setUser_pass(Config.getSystemConfig("bank.pay.allinpay.password"));

        // 交易批次号（交易批次号 = 商户 id + 时间 + 固定五位数）
        String transId = StringUtils.buildNumberString(MySQLDao.getSequence("allinpayQueryTransId"), 5);
        main.setReq_sn(Config.getSystemConfig("bank.pay.allinpay.daifu.merchantId") + String.valueOf(System.currentTimeMillis()) + transId);

        Integer count = MySQLDao.insert(main, conn);

        return count == 1 ? main : null;

    }

}
