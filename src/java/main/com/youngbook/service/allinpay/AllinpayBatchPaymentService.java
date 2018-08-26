package com.youngbook.service.allinpay;

import com.aipg.common.*;
import com.aipg.payreq.Body;
import com.aipg.payreq.Trans_Detail;
import com.aipg.payreq.Trans_Sum;
import com.aipg.payresp.Ret_Detail;
import com.allinpay.XmlTools;
import com.youngbook.common.Database;
import com.youngbook.common.MyException;
import com.youngbook.common.ReturnObject;
import com.youngbook.common.config.AesEncrypt;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.Config4Status;
import com.youngbook.common.config.Config4W;
import com.youngbook.common.utils.*;
import com.youngbook.common.utils.bank.TranxServiceImpl;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.sale.IPaymentPlanDao;
import com.youngbook.dao.system.ILogDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.allinpay.*;
import com.youngbook.entity.po.core.TransferPO;
import com.youngbook.entity.po.core.TransferTargetType;
import com.youngbook.entity.po.customer.CustomerRefundStatus;
import com.youngbook.service.BaseService;
import com.youngbook.service.core.APICommandService;
import com.youngbook.service.core.IMoneyTransferService;
import com.youngbook.service.customer.CustomerRefundService;
import com.youngbook.service.system.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Component("allinpayBatchPaymentService")
public class AllinpayBatchPaymentService extends BaseService implements IMoneyTransferService {

    @Autowired
    IPaymentPlanDao paymentPlanDao;

    @Autowired
    ILogDao logDao;

    /**
     * 生成批量代付请求，间隔 30 秒就执行一次
     * 用法：在 Task 中 Run 方法里面运行
     * @throws Exception
     * 修改人：姚章鹏
     * 时间：2015年8月13日
     * 内容：生成批量代付请求，先判断是否有批量代付明细表，如果有就生成批量代付主表
     */
    public void buildBatchPaymentDatas() throws Exception {

        Connection conn = Config.getConnection();
        String backXml = null;


        AllinpayBatchPaymentPO head = null;
        List<AllinpayBatchPaymentDetailPO> listDetails = null;
        try {

            conn.setAutoCommit(false);

            // 查询未提现的通联明细
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT * FROM bank_allinpaybatchpaymentdetail WHERE 1 = 1 AND state = " + Config.STATE_CURRENT + " AND status = " + AllinpayBatchPaymentStatus.UNFINISH + " ");
            listDetails  = MySQLDao.query(sql.toString(), AllinpayBatchPaymentDetailPO.class, null);

            if (listDetails != null && listDetails.size() > 0) {

                //添加批量代付主表数据
                head = new AllinpayBatchPaymentPO();
                head.setTrx_code(Config4W.TRX_CODE); // 测试代付码：100002
                head.setVersion(Config4W.VERSION); // 版本：默认 3
                head.setData_type(Config4W.DATA_TYPE);// 数据类型：2 是 xml
                head.setLevel(Config4W.LEVEL);  // 默认 5
                head.setUser_name(Config.getSystemConfig("bank.pay.allinpay.userName")); // 测试的用户名
                head.setPassword(Config.getSystemConfig("bank.pay.allinpay.password"));  // 测试的密码
                String transactionId= StringUtils.buildNumberString(MySQLDao.getSequence("transactionId"), 5);// 交易 id
                head.setReq_sn(Config.getSystemConfig("bank.pay.allinpay.daifu.merchantId") + String.valueOf(System.currentTimeMillis()) +transactionId);// 交易批次号（交易批次号 = 商户 id + 时间 + 固定五位数）
                head.setBusiness_code(Config4W.BUSINESS_CODE);// 测试的交易代码
                head.setMerchant_id(Config.getSystemConfig("bank.pay.allinpay.daifu.merchantId")); // 测试的商户 ID
                head.setStatus(Config.STATE_CURRENT);
                int moneyValue = 0;

                for (AllinpayBatchPaymentDetailPO itemValue : listDetails) {
                    moneyValue += itemValue.getAmount();
                }

                head.setTotal_sum(moneyValue); // 总金额
                head.setTotal_item(listDetails.size());// 记录数目

                // 通联的新增批量代付主表并返回查询
                AllinpayBatchPaymentService service = new AllinpayBatchPaymentService();
                head = service.insertOrUpdate(head, Config.getSystemConfig("web.default.operatorId"), conn);

                // 更新代付明细表
                for (AllinpayBatchPaymentDetailPO detail : listDetails) {
                    detail.setParentid(head.getId());
                    AllinpayBatchPaymentDetailService detailService = new AllinpayBatchPaymentDetailService();
                    // 这里只做了更新操作
                    detailService.insertOrUpdate(detail, Config.getSystemConfig("web.default.operatorId"), conn);
                }

                // 代付主表、明细表都保存完毕，构建成xml发送给通联支付之后，保存数据
                conn.commit();
            }

        }
        catch (Exception e) {
            conn.rollback();
            throw e;
        }
        finally {
            Database.close(conn);
        }

        // 处理批量代付请求
        if (head == null || listDetails == null && listDetails.size() == 0) {
            LogService.info("没有可用的代付数据", this.getClass());
        }
        else {
            batchDaiFu(Config.getSystemConfig("bank.pay.allinpay.auth.url"), head, listDetails, false);
        }
    }

    /**
     * 处理生成 xml 请求，组装报文头部，并更新批量代付明细表的 parentId
     * 使其关联批量代付主表，
     * 修改人：姚章鹏
     * 时间：2015年8月10日15:40:15
     * 内容:处理代付请求
     */
    public String batchDaiFu(String url, AllinpayBatchPaymentPO head, List<AllinpayBatchPaymentDetailPO> listDetails, boolean isTLTFront) throws Exception {

        String xml = "";

        try {

            AipgReq aipg = new AipgReq();

            // 组装报文头部
            InfoReq info = makeReqDaiFu(String.valueOf(head.getTrx_code()), head);
            aipg.setINFO(info);
            Body body = new Body() ;// 创建 Body
            Trans_Sum trans_sum = new Trans_Sum() ;
            trans_sum.setBUSINESS_CODE(head.getBusiness_code()) ;
            trans_sum.setMERCHANT_ID(head.getMerchant_id()) ;

            // 更新附表 ParentId 并查询出所有明细数据
            trans_sum.setTOTAL_ITEM(String.valueOf(listDetails.size()));

            // 需要算金额
            int moneyValue = head.getTotal_sum();

            // 设置总金额
            trans_sum.setTOTAL_SUM(String.valueOf(moneyValue)) ;
            body.setTRANS_SUM(trans_sum) ;
            List <Trans_Detail> transList = new ArrayList<Trans_Detail>();

            // 组装 xml 数据
            for(AllinpayBatchPaymentDetailPO item : listDetails) {
                Trans_Detail trans_detail = new Trans_Detail();
                trans_detail.setSN(item.getSn()); // 设置 sn 序号
                trans_detail.setACCOUNT_NAME(item.getAccount_name()); // 帐户名
                trans_detail.setACCOUNT_PROP(String.valueOf(item.getAccount_pror()));
                trans_detail.setACCOUNT_NO(AesEncrypt.decrypt(item.getAccount_no())); // 账户号  数据库表已经aes加密存入 解密传到银行
                trans_detail.setBANK_CODE(item.getBank_code()); // 银行代码
                trans_detail.setAMOUNT(String.valueOf(item.getAmount())); // 金额
                trans_detail.setCURRENCY(item.getCurrency());
                // trans_detail.setSETTGROUPFLAG("xCHM");
                trans_detail.setSUMMARY("");
                trans_detail.setUNION_BANK(item.getUnion_bank());
                transList.add(trans_detail);
            }

            body.setDetails(transList) ;
            aipg.addTrx(body) ;

            // 组装 xml 数据
            xml = XmlTools.buildXml(aipg, true);

            LogService.info("生成通联支付代付指令", this.getClass());
            LogService.info(xml, this.getClass());

            // 发送 xml 文件
            TranxServiceImpl tranxService = new TranxServiceImpl();
            String backResp = tranxService.isFront(xml, isTLTFront, url);

            //解析返回报文
            parseBackResp(backResp);

            return backResp;
        }
        catch (Exception e){
            throw e;
        }

    }


    /**
     * 批量代付实现
     *
     * 此代付的实现仅仅为发出代付指令，返回指令还尚未解析。需要进一步完善。
     * 
     * V1版本尚未经过测试
     * @param transfers
     * @param conn
     * @return
     * @throws Exception
     */
    public ReturnObject doTransfer(List<TransferPO> transfers, Connection conn) throws Exception {

        String xml = "";

        String url = Config.getSystemConfig("bank.pay.allinpay.auth.url");
        String merchantId = Config.getSystemConfig("bank.pay.allinpay.daifu.merchantId");
        String userName = Config.getSystemConfig("bank.pay.allinpay.userName");
        String password = Config.getSystemConfig("bank.pay.allinpay.password");

        try {

            AipgReq aipg = new AipgReq();

            // 组装报文头部
            InfoReq info = new InfoReq();
            info.setTRX_CODE("100002");
            String requireSN_Sequence = String.valueOf(TimeUtils.getSimpleTimestamp());
            String requireSN = merchantId + requireSN_Sequence;
            info.setREQ_SN(requireSN);
            info.setUSER_NAME(userName);
            info.setUSER_PASS(password);
            info.setMERCHANT_ID(merchantId);
            info.setLEVEL("5");
            info.setDATA_TYPE("2");
            info.setVERSION("03");
            aipg.setINFO(info);


            Body body = new Body() ;// 创建 Body
            Trans_Sum trans_sum = new Trans_Sum() ;
            trans_sum.setBUSINESS_CODE("09900") ;
            trans_sum.setMERCHANT_ID(merchantId) ;

            // 更新附表 ParentId 并查询出所有明细数据
            trans_sum.setTOTAL_ITEM(String.valueOf(transfers.size()));

            int totalMoney = 0;
            for (int i = 0; i < transfers.size(); i++) {
                TransferPO transfer = transfers.get(i);
                totalMoney += Math.round(transfer.getMoney() * 100);
            }

            // 设置总金额
            trans_sum.setTOTAL_SUM(String.valueOf(totalMoney)) ;
            body.setTRANS_SUM(trans_sum) ;



            List <Trans_Detail> transList = new ArrayList<Trans_Detail>();

            // 组装 xml 数据
            for (int i = 0; i < transfers.size(); i++) {
                TransferPO transfer = transfers.get(i);


                if (transfer.getTargetType() == TransferTargetType.Company) {
                    if (StringUtils.isEmpty(transfer.getTargetProvinceName()) || StringUtils.isEmpty(transfer.getTargetCityName())) {
                        MyException.newInstance("对公账户必须包含省市名称信息").throwException();
                    }
                }

                Trans_Detail trans_detail = new Trans_Detail();
                trans_detail.setSN(StringUtils.buildNumberString(i + 1, 4)); // 设置 sn 序号
                trans_detail.setACCOUNT_NAME(transfer.getTargetAccountName()); // 帐户名
                trans_detail.setACCOUNT_PROP(String.valueOf(transfer.getTargetType()));
                trans_detail.setACCOUNT_NO(transfer.getTargetAccountNo()); // 账户号  数据库表已经aes加密存入 解密传到银行
                trans_detail.setBANK_CODE(transfer.getTargetBank()); // 银行代码
                trans_detail.setPROVINCE(transfer.getTargetProvinceName());
                trans_detail.setCITY(transfer.getTargetCityName());
                trans_detail.setAMOUNT(String.valueOf(NumberUtils.getMoney2Fen(transfer.getMoney()))); // 金额
                // trans_detail.setSETTGROUPFLAG("xCHM");
                trans_detail.setSUMMARY("");
                transList.add(trans_detail);
            }

            body.setDetails(transList) ;
            aipg.addTrx(body) ;

            // 组装 xml 数据
            xml = XmlTools.buildXml(aipg, true);

            LogService.info("生成通联支付代付指令", this.getClass());
            LogService.info(xml, this.getClass());

            // 记录日志-发送指令
            APICommandService apiCommandService = new APICommandService();
            apiCommandService.save4AllinpaySend(requireSN_Sequence, xml, conn);

            // 发送 xml 文件
            TranxServiceImpl tranxService = new TranxServiceImpl();
//            String backResp = "done";
            String backResp = tranxService.isFront(xml, false, url);

            // 记录日志-接收指令
            apiCommandService.save4AllinpayReceive(requireSN_Sequence, backResp, conn);

            LogService.info(backResp, this.getClass());

            //解析返回报文
            logDao.save("通联支付", "代付指令", backResp, conn);

            ReturnObject returnObject = new ReturnObject();
            if (backResp.contains("<RET_CODE>0000</RET_CODE>")) {
                returnObject.setCode(ReturnObject.CODE_SUCCESS);
                returnObject.setMessage("请求已受理。" + Config.getSystemConfig("order_moneytransfer.gongda.success_message") );
            }
            else {
                returnObject.setCode(ReturnObject.CODE_EXCEPTION);
                returnObject.setMessage("三方支付返回异常，请联系系统运维团队。" +  Config.getSystemConfig("order_moneytransfer.gongda.error_message"));
            }
            return returnObject;
        }
        catch (Exception e){
            throw e;
        }

    }

    /**
     * 解析返回的报文
     * 用法：将返回的报文放入 parseBackResp() 方法里面
     * @param backResp
     * @throws Exception
     * 修改人：姚章鹏
     * 时间：2015年8月13日14:52:12
     * 内容：解析报文
     */
    public void  parseBackResp(String backResp) throws Exception {

        if (StringUtils.isEmpty(backResp)) {
            throw new Exception("解析失败，传入xml为空");
        }

        LogService.info("开始解析通联支付代付", this.getClass());
        LogService.info(backResp, this.getClass());

        AipgRsp aipgRsp = XSUtil.xmlRsp(backResp);

        if (aipgRsp == null || aipgRsp.getINFO() == null || aipgRsp.getTrxData() == null || aipgRsp.getTrxData().size() == 0) {
            throw new Exception("解析为空");
        }

        // 解析出来不为空
        InfoRsp info = aipgRsp.getINFO();



        AllinpayBatchPaymentPO payment = new AllinpayBatchPaymentPO();
        Connection conn = Config.getConnection();
        try {
            // 处理代付主表数据，确保不被重复扫描并发送请求
            payment.setReq_sn(info.getREQ_SN());
            payment.setState(Config.STATE_CURRENT);
            payment = MySQLDao.load(payment, AllinpayBatchPaymentPO.class, conn);

            if (payment == null) {
                String message = "无法获得对应的代付请求，SN:["+info.getREQ_SN()+"]";
                LogService.info(message, this.getClass());
                throw new Exception(message);
            }

            payment.setState(Config.STATE_UPDATE);
            MySQLDao.update(payment, conn);

            payment.setSid(MySQLDao.getMaxSid("bank_AllinpayBatchPayment", conn));
            payment.setState(Config.STATE_CURRENT);
            payment.setOperateTime(TimeUtils.getNow());
            payment.setStatus(AllinpayBatchPaymentStatus.ACCEPTED);
            MySQLDao.insert(payment, conn);


            com.aipg.payresp.Body qrsp = (com.aipg.payresp.Body) aipgRsp.getTrxData().get(0);  //解析 info
            List<Ret_Detail> details = qrsp.getDetails(); //解析 detalis


            // 保存通联批量返回代付明细表
            for(Ret_Detail detail : details) {

                // 更新代付明细表数据
                int paymentDetailStatus = AllinpayBatchPaymentStatus.UNACCEPTED;
                if (detail.getRET_CODE().equals("0000")) {
                    paymentDetailStatus = AllinpayBatchPaymentStatus.ACCEPTED;
                }

                updateAllinpayBatchPaymentDetailStatus(payment.getId(), paymentDetailStatus, conn);
            }


            // 设置兑付计划状态
            AllinpayBatchPaymentDetailService allinpayBatchPaymentDetailService = new AllinpayBatchPaymentDetailService();
            List<AllinpayBatchPaymentDetailPO> listPaymentDetails = allinpayBatchPaymentDetailService.getDetailsByParentId(payment.getId(), conn);
            UserPO user = new UserPO();
            for (AllinpayBatchPaymentDetailPO paymentDetailPO : listPaymentDetails) {
                if (paymentDetailPO.getBizType() == AllinpayBatchPaymentType.Payment) {

                    // 设置兑付计划数据为接受状态
                    if (paymentDetailPO.getStatus() == AllinpayBatchPaymentStatus.ACCEPTED) {
                        paymentPlanDao.setPaymentPlanAccepted(paymentDetailPO.getBizId(), user, conn);
                    }
                    else if (paymentDetailPO.getStatus() == AllinpayBatchPaymentStatus.UNACCEPTED) {
                        // todo 设置兑付状态为不接受
                    }
                }
                else if (paymentDetailPO.getBizType() == AllinpayBatchPaymentType.Refund) {
                    // 设置退款数据为接受状态
                    CustomerRefundService customerRefundService = new CustomerRefundService();
                    if (paymentDetailPO.getStatus() == AllinpayBatchPaymentStatus.ACCEPTED) {
                        customerRefundService.setRefundStatus(paymentDetailPO.getBizId(), CustomerRefundStatus.Accepted);
                    }
                    else if (paymentDetailPO.getStatus() == AllinpayBatchPaymentStatus.UNACCEPTED) {
                        // todo 设置退款状态为不接受
                    }
                }
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }


        String callbackHeadId = "";

        // 受理成功
        // 插入返回主表
        AllinpayBatchPaymentCallbackPO allinpayBatchPaymentCallback = new AllinpayBatchPaymentCallbackPO();
        allinpayBatchPaymentCallback.setTrx_code(Integer.parseInt(info.getTRX_CODE()));
        allinpayBatchPaymentCallback.setVersion(info.getVERSION());
        allinpayBatchPaymentCallback.setData_type(Integer.parseInt(info.getDATA_TYPE()));
        allinpayBatchPaymentCallback.setReq_sn(info.getREQ_SN());
        allinpayBatchPaymentCallback.setRet_code(info.getRET_CODE());
        allinpayBatchPaymentCallback.setErr_msg(info.getERR_MSG());
        allinpayBatchPaymentCallback.setSigned_msg(info.getSIGNED_MSG());

        // 通联的新增批量代付返回主表 id
        callbackHeadId = insertAllinpayBatchPaymentCallback(allinpayBatchPaymentCallback);

        com.aipg.payresp.Body qrsp = (com.aipg.payresp.Body) aipgRsp.getTrxData().get(0);  //解析 info
        List<Ret_Detail> details = qrsp.getDetails(); //解析 detalis

        // 保存通联批量返回代付明细表
        for(Ret_Detail detail : details) {

            // 保存通联支付反馈明细信息
            int count = insertAllinpayBatchPaymentCallbackDetail(callbackHeadId, detail);
            if(count != 1) {
                throw new Exception("保存通联批量返回明细异常");
            }
        }

    }


    public void setPaymentPlanAccepted(String paymentId, Connection conn) throws Exception {

        // 设置兑付计划数据为接受状态
        AllinpayBatchPaymentDetailService allinpayBatchPaymentDetailService = new AllinpayBatchPaymentDetailService();
        List<AllinpayBatchPaymentDetailPO> listPaymentDetails = allinpayBatchPaymentDetailService.getDetailsByParentId(paymentId);
        for (AllinpayBatchPaymentDetailPO paymentDetailPO : listPaymentDetails) {
            UserPO user = new UserPO();
            paymentPlanDao.setPaymentPlanAccepted(paymentDetailPO.getBizId(), user, conn);
        }
    }

    /**
     * 创建人：姚章鹏
     * 时间：2015年8月22日17:16:35
     * 内容：更改通联代付主表的状态
     * @param conn
     * @throws Exception
     */
    public void  updateAllinpaybatchpaymentStatus(String parentId, int status, Connection conn) throws Exception {

        //请求更改成功
        int count = 0;
        AllinpayBatchPaymentPO allinpayBatchPayment = new AllinpayBatchPaymentPO();
        allinpayBatchPayment.setId(parentId);
        allinpayBatchPayment.setState(Config.STATE_CURRENT);
        allinpayBatchPayment.setStatus(status);

        allinpayBatchPayment = MySQLDao.load(allinpayBatchPayment, AllinpayBatchPaymentPO.class);
        if (allinpayBatchPayment != null) {
            allinpayBatchPayment.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(allinpayBatchPayment);

            if (count == 1) {
                allinpayBatchPayment.setSid(MySQLDao.getMaxSid("bank_AllinpayBatchPayment", conn));
                allinpayBatchPayment.setState(Config.STATE_CURRENT);
                allinpayBatchPayment.setStatus(status);
                allinpayBatchPayment.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
                allinpayBatchPayment.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(allinpayBatchPayment, conn);
            }
        }
    }

    /**
     *修改人：姚章鹏
     * 时间：2015年8月13日14:57:33
     * 内容：通联的新增批量代返回明细表
     **/
    public int  insertAllinpayBatchPaymentCallbackDetail(String id,Ret_Detail detail) throws Exception {
        Connection conn = Config.getConnection();

        int count = 0;
        try {
            AllinpayBatchPaymentCallbackDetailPO allinpayBatchPaymentCallbackDetail = new AllinpayBatchPaymentCallbackDetailPO();
            allinpayBatchPaymentCallbackDetail.setSid(MySQLDao.getMaxSid("bank_AllinpayBatchPaymentCallbackDetail", conn));
            allinpayBatchPaymentCallbackDetail.setId(IdUtils.getUUID32());
            allinpayBatchPaymentCallbackDetail.setState(Config.STATE_CURRENT);
            allinpayBatchPaymentCallbackDetail.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
            allinpayBatchPaymentCallbackDetail.setOperateTime(TimeUtils.getNow());
            allinpayBatchPaymentCallbackDetail.setSn(detail.getSN());
            allinpayBatchPaymentCallbackDetail.setRet_code(detail.getRET_CODE());
            allinpayBatchPaymentCallbackDetail.setErr_msg(detail.getERR_MSG());
            allinpayBatchPaymentCallbackDetail.setParentId(id);
            allinpayBatchPaymentCallbackDetail.setQueryStatus(Config4Status.STATUS_ALLINPAY_CALLBACK_QUERY_UNFINISH);
            count = MySQLDao.insert(allinpayBatchPaymentCallbackDetail, conn);
            if (count != 1) {
                throw new Exception("数据库异常");
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }
        return count;
    }

    /**
     * 组装报文头部
     * @param trxcod
     * @return
     *日期：2015年8月13日14:47:48
     */
    private InfoReq makeReqDaiFu(String trxcod,AllinpayBatchPaymentPO backAllinpayBatchPayment) throws Exception {
        InfoReq info=new InfoReq();
        info.setTRX_CODE(trxcod);
        info.setREQ_SN(backAllinpayBatchPayment.getReq_sn());
        info.setUSER_NAME(Config.getSystemConfig("bank.pay.allinpay.userName"));
        info.setUSER_PASS(Config.getSystemConfig("bank.pay.allinpay.password"));
        info.setMERCHANT_ID(backAllinpayBatchPayment.getMerchant_id());
        info.setLEVEL(String.valueOf(backAllinpayBatchPayment.getLevel()));
        info.setDATA_TYPE(String.valueOf(backAllinpayBatchPayment.getData_type()));
        info.setVERSION(backAllinpayBatchPayment.getVersion());
        return info;
    }

    /**
     *修改人：姚章鹏
     * 时间：2015年8月13日14:57:33
     * 内容：通联的新增批量代付主表
     **/
    public AllinpayBatchPaymentPO insertAllinpayBatchPayment(AllinpayBatchPaymentPO allinpayBatchPayment) throws Exception{
        Connection conn = Config.getConnection();

        try {
            int count = 0;
            int sid=MySQLDao.getMaxSid("bank_AllinpayBatchPayment", conn);
            AllinpayBatchPaymentPO temp = new AllinpayBatchPaymentPO();
            if (allinpayBatchPayment.getId().equals("")) {
                allinpayBatchPayment.setSid(sid);
                allinpayBatchPayment.setId(IdUtils.getUUID32());
                allinpayBatchPayment.setState(Config.STATE_CURRENT);
                allinpayBatchPayment.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
                allinpayBatchPayment.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(allinpayBatchPayment, conn);
            }
            if (count != 1) {
                throw new Exception("数据库异常");
            }else{
                //            根据sid查数据
                temp.setSid(sid);
                temp = MySQLDao.load(temp, AllinpayBatchPaymentPO.class);
            }
            return temp;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }



    }


    /**
     * 修改人：姚章鹏
     * 时间：2015年8月18日10:50:58
     * 内容： 通联的新增批量代付返回主表
     *
     */
    public String insertAllinpayBatchPaymentCallback(AllinpayBatchPaymentCallbackPO allinpayBatchPaymentCallback) throws Exception{

        int count = 0;
        Connection conn = Config.getConnection();

        try {

            String id = IdUtils.getUUID32();
            if (allinpayBatchPaymentCallback.getId().equals("")) {
                allinpayBatchPaymentCallback.setSid(MySQLDao.getMaxSid("bank_AllinpayBatchPaymentCallback", conn));
                allinpayBatchPaymentCallback.setId(id);
                allinpayBatchPaymentCallback.setState(Config.STATE_CURRENT);
                allinpayBatchPaymentCallback.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
                allinpayBatchPaymentCallback.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(allinpayBatchPaymentCallback, conn);
            }
            if (count != 1) {
                throw new Exception("数据库异常");
            }

            return id;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }

    }


    public AllinpayBatchPaymentPO insertOrUpdate(AllinpayBatchPaymentPO po, String userId, Connection conn) throws Exception{
        int count = 0;
        // 新增
        if (po.getId().equals("")) {
            po.setSid(MySQLDao.getMaxSid("bank_AllinpayBatchPayment", conn));
            po.setId(IdUtils.getUUID32());
            po.setState(Config.STATE_CURRENT);
            po.setOperatorId(userId);
            po.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(po, conn);
        }
        // 更新
        else {
            AllinpayBatchPaymentPO temp = new AllinpayBatchPaymentPO();
            temp.setSid(po.getSid());
            temp = MySQLDao.load(temp, AllinpayBatchPaymentPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                po.setSid(MySQLDao.getMaxSid("bank_AllinpayBatchPayment", conn));
                po.setState(Config.STATE_CURRENT);
                po.setOperatorId(userId);
                po.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(po, conn);
                po = MySQLDao.load(po, AllinpayBatchPaymentPO.class, conn);
            }
        }
        if (count != 1) {
            throw new Exception("更新数据AllinpayBatchPaymentPO失败");
        }
        return po;
    }


    /**
     * 更新代付明细表的状态
     * @param paymentId
     * @param status
     * @param conn
     * @throws Exception
     */
    public void updateAllinpayBatchPaymentDetailStatus(String paymentId, int status, Connection conn) throws Exception{
        AllinpayBatchPaymentDetailPO detail = new AllinpayBatchPaymentDetailPO();
        detail.setParentid(paymentId);
        detail.setState(Config.STATE_CURRENT);
        List<AllinpayBatchPaymentDetailPO> listDetails = MySQLDao.query(detail, AllinpayBatchPaymentDetailPO.class, conn);

        for (int i = 0; listDetails != null && i < listDetails.size(); i++) {
            detail = listDetails.get(i);
            detail.setState(Config.STATE_UPDATE);
            int count = MySQLDao.update(detail, conn);

            if (count == 1) {
                detail.setSid(MySQLDao.getMaxSid("bank_AllinpayBatchPaymentDetail", conn));
                detail.setState(Config.STATE_CURRENT);
                detail.setOperateTime(TimeUtils.getNow());
                detail.setStatus(status);
                count = MySQLDao.insert(detail, conn);
            }
        }
    }

}
