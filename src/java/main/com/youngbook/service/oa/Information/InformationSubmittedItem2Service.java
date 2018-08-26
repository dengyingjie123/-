package com.youngbook.service.oa.Information;

import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.oa.Information.InformationSubmitted2PO;
import com.youngbook.entity.po.oa.Information.InformationSubmittedItem2PO;
import com.youngbook.entity.po.oa.Information.InformationSubmittedItem2Status;
import com.youngbook.entity.vo.oa.Information.InformationSubmittedItem2VO;
import com.youngbook.service.BaseService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * Created by haihong on 2015/7/1.
 * 资料报送类型类类
 */

public class InformationSubmittedItem2Service extends BaseService {
    /**
     * 判断数据有效性
     * 判断id是否为null 为null则添加数据 不为null 则更新数据
     * 填充数据，执行添加或更新操作
     * 返回影响行数
     * <p/>
     * 添加或修改数据，并修改数据状态
     *
     * @param informationSubmittedItem2PO 资料报送类型数据域
     * @param user                        操作员数据
     * @param conn                        数据库连接
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(InformationSubmittedItem2PO informationSubmittedItem2PO, UserPO user, Connection conn) throws Exception {
        //判断吧数据的有效性
        // 数据操作实体类是否为空
        if (informationSubmittedItem2PO == null) {
            throw new Exception("资料报送类型数据提交失败");
        }

        //当前用户操作类是否为空
        if (user == null) {
            throw new Exception("当前用户失效");
        }

        //当前数据链接是否空
        if (conn == null) {
            throw new Exception("链接错误");
        }

        //设置影响函数变量
        int count = 0;

        // 判断id的是否为null
        //新增
        if (informationSubmittedItem2PO.getId().equals("")) {

            //填充数据
            informationSubmittedItem2PO.setSid(MySQLDao.getMaxSid("oa_InformationSubmittedItem2", conn));
            informationSubmittedItem2PO.setId(IdUtils.getUUID32());
            informationSubmittedItem2PO.setState(Config.STATE_CURRENT);
            informationSubmittedItem2PO.setOperatorId(user.getId());
            informationSubmittedItem2PO.setOperateTime(TimeUtils.getNow());

            //添加数据
            count = MySQLDao.insert(informationSubmittedItem2PO, conn);
            //更新主表信息
            //判断是否需要外带
            int status = informationSubmittedItem2PO.getStatus();
            if (InformationSubmittedItem2Status.STATUS_OK == status) {
                InformationSubmitted2PO temp = new InformationSubmitted2PO();
                //主表编号
                temp.setId(informationSubmittedItem2PO.getApplicationId());
                temp.setState(Config.STATE_CURRENT);
                temp = MySQLDao.load(temp, InformationSubmitted2PO.class);
                //更新数据
                temp.setState(Config.STATE_UPDATE);
                count = MySQLDao.update(temp, conn);
                //判断影响函数是不是1条
                if (count == 1) {
                    temp.setSid(MySQLDao.getMaxSid("OA_InformationSubmitted2", conn));
                    temp.setState(Config.STATE_CURRENT);
                    temp.setOperatorId(user.getId());
                    temp.setOperateTime(TimeUtils.getNow());
                    //需要外带
                    temp.setIsOut(InformationSubmittedItem2Status.ISOUT_OK);
                    //设置全部已经接收
                    temp.setIsAllReceive(InformationSubmittedItem2Status.ISALLRECEIVE_NO);
                    //没有全部归还
                    temp.setIsAllOutBack(InformationSubmittedItem2Status.ISALLOUTBACK_NO);
                    count = MySQLDao.insert(temp, conn);
                }
            }
        }
        // 更新
        else {
            //填充数据
            InformationSubmittedItem2PO temp = new InformationSubmittedItem2PO();
            temp.setSid(informationSubmittedItem2PO.getSid());
            temp.setState(Config.STATE_CURRENT);
            temp = MySQLDao.load(temp, InformationSubmittedItem2PO.class, conn);
            temp.setState(Config.STATE_UPDATE);
            //更新数据
            count = MySQLDao.update(temp, conn);
            //如果放回的影响函数为1
            if (count == 1) {
                informationSubmittedItem2PO.setSid(MySQLDao.getMaxSid("oa_InformationSubmittedItem2", conn));
                informationSubmittedItem2PO.setState(Config.STATE_CURRENT);
                informationSubmittedItem2PO.setOperatorId(user.getId());
                informationSubmittedItem2PO.setOperateTime(TimeUtils.getNow());
                //插入新数据
                count = MySQLDao.insert(informationSubmittedItem2PO, conn);
                //更新主表信息
                if (count == 1 && informationSubmittedItem2PO.getStatus() == InformationSubmittedItem2Status.STATUS_OK) {
                    InformationSubmitted2PO SealUsageWFA2POtemp = new InformationSubmitted2PO();
                    //主表编号
                    SealUsageWFA2POtemp.setId(informationSubmittedItem2PO.getApplicationId());
                    SealUsageWFA2POtemp.setState(Config.STATE_CURRENT);
                    SealUsageWFA2POtemp = MySQLDao.load(SealUsageWFA2POtemp, InformationSubmitted2PO.class);
                    SealUsageWFA2POtemp.setState(Config.STATE_UPDATE);
                    count = MySQLDao.update(SealUsageWFA2POtemp, conn);
                    //判断影响函数是不是1条
                    if (count == 1) {
                        SealUsageWFA2POtemp.setSid(MySQLDao.getMaxSid("OA_InformationSubmitted2", conn));
                        SealUsageWFA2POtemp.setState(Config.STATE_CURRENT);
                        SealUsageWFA2POtemp.setOperatorId(user.getId());
                        SealUsageWFA2POtemp.setOperateTime(TimeUtils.getNow());
                        //需要外带
                        SealUsageWFA2POtemp.setIsOut(InformationSubmittedItem2Status.ISOUT_OK);

                        //设置全部已经接收
                        SealUsageWFA2POtemp.setIsAllReceive(InformationSubmittedItem2Status.ISALLRECEIVE_NO);
                        //没有全部归还
                        SealUsageWFA2POtemp.setIsAllOutBack(InformationSubmittedItem2Status.ISALLOUTBACK_NO);
                        count = MySQLDao.insert(SealUsageWFA2POtemp, conn);
                    }

                }
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    /**
     * 判断id是否为null
     * 设置查询条件
     * 获取数据
     * 海鸿
     * 2015-8-18
     * <p/>
     * 根据制定的ID获取数据
     *
     * @param id 指定id 数据
     * @return
     * @throws Exception
     */
    public InformationSubmittedItem2PO loadInformationSubmittedItem2PO(String id) throws Exception {
        //判断id的是否为null；

        if (StringUtils.isEmpty(id)) {
            throw new Exception("资料报送类型数据错误");
        }

        //设置查询数据
        InformationSubmittedItem2PO po = new InformationSubmittedItem2PO();
        po.setId(id);
        po.setState(Config.STATE_CURRENT);

        //获取数据
        po = MySQLDao.load(po, InformationSubmittedItem2PO.class);

        return po;
    }

    /**
     * 判断传入的数据是否为null
     * 执行更新书状态操作
     * 根据条改编数据的状态
     *
     * @param informationSubmittedItem2PO 资料报送类型数据对象
     * @param user                        操作人
     * @param conn                        数据库连接
     * @return
     * @throws Exception
     */
    public int delete(InformationSubmittedItem2PO informationSubmittedItem2PO, UserPO user, Connection conn) throws Exception {
        //判断数据是否为null
        if (informationSubmittedItem2PO == null) {
            MyException.newInstance("删除资料报送类型数据失败");
        }

        //更新数据状态
        int count = 0;

        informationSubmittedItem2PO.setState(Config.STATE_CURRENT);
        informationSubmittedItem2PO = MySQLDao.load(informationSubmittedItem2PO, InformationSubmittedItem2PO.class);
        informationSubmittedItem2PO.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(informationSubmittedItem2PO, conn);

        //更新数据的影响行数为1
        if (count == 1) {
            informationSubmittedItem2PO.setSid(MySQLDao.getMaxSid("oa_InformationSubmittedItem2", conn));
            informationSubmittedItem2PO.setState(Config.STATE_DELETE);
            informationSubmittedItem2PO.setOperateTime(TimeUtils.getNow());
            informationSubmittedItem2PO.setOperatorId(user.getId());
            count = MySQLDao.insert(informationSubmittedItem2PO, conn);

            //被删除的数据是主表的最后一条字表数据，并且主表的数据是需要外带的e 更改主表数据
            if (informationSubmittedItem2PO.getStatus() == InformationSubmittedItem2Status.STATUS_OK) {
                //获取所有资料列表
                // 构造SQL语句
                StringBuffer sbSQL = new StringBuffer();
                sbSQL.append("select * from oa_InformationSubmittedItem2");
                sbSQL.append(" where 1=1");
                sbSQL.append(" and state=0");
                sbSQL.append(" and applicationId = '" + Database.encodeSQL(informationSubmittedItem2PO.getApplicationId()) + "'");
                //获取数据
                List<InformationSubmittedItem2PO> InformationSubmittedItem2POs = MySQLDao.query(sbSQL.toString(), InformationSubmittedItem2PO.class, null,conn);

                boolean flag = true;
                for (InformationSubmittedItem2PO item2po : InformationSubmittedItem2POs) {
                    if (item2po.getStatus() == InformationSubmittedItem2Status.STATUS_OK) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    InformationSubmitted2PO SealUsageWFA2POtemp = new InformationSubmitted2PO();
                    //主表编号
                    SealUsageWFA2POtemp.setId(informationSubmittedItem2PO.getApplicationId());
                    SealUsageWFA2POtemp.setState(Config.STATE_CURRENT);
                    SealUsageWFA2POtemp = MySQLDao.load(SealUsageWFA2POtemp, InformationSubmitted2PO.class);
                    SealUsageWFA2POtemp.setState(Config.STATE_UPDATE);
                    count = MySQLDao.update(SealUsageWFA2POtemp, conn);
                    //判断影响函数是不是1条
                    if (count == 1) {
                        SealUsageWFA2POtemp.setSid(MySQLDao.getMaxSid("OA_InformationSubmitted2", conn));
                        SealUsageWFA2POtemp.setState(Config.STATE_CURRENT);
                        SealUsageWFA2POtemp.setOperatorId(user.getId());
                        SealUsageWFA2POtemp.setOperateTime(TimeUtils.getNow());
                        //不需要外带
                        SealUsageWFA2POtemp.setIsOut(InformationSubmittedItem2Status.ISOUT_NO);
                        SealUsageWFA2POtemp.setIsAllReceive("");
                        SealUsageWFA2POtemp.setIsAllOutBack("");
                        count = MySQLDao.insert(SealUsageWFA2POtemp, conn);
                    }
                }
            }
        }

        if (count != 1)

        {
            throw new Exception("删除失败");
        }

        return count;
    }

    /**
     * 判断id是否null
     * 构建查询sql
     * 获取数据列表
     * <p/>
     * 修改：周海鸿
     * 时间：2015-7-20
     * 内容：获取资料报送类型打印数据
     */
    public List<InformationSubmittedItem2PO> getItemData(String id) throws Exception {
        //判断数据有效性
        if ("".equals(id) || null == id) {
            throw new Exception("资料报送类型打印数据获取失败");
        }

        // 构造SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("select * from oa_InformationSubmittedItem2");
        sbSQL.append(" where 1=1");
        sbSQL.append(" and state=0");
        sbSQL.append(" and applicationId = '" + Database.encodeSQL(id) + "'");

        //获取数据
        List li = MySQLDao.query(sbSQL.toString(), InformationSubmittedItem2PO.class, null);

        return li;
    }

    /**
     * 判断传入的id 的有效性
     * 创建SQL语句
     * 设置查询你类型
     * 放回查分页对象
     * <p/>
     * 海鸿
     * 2015-8-18
     * 获取资料报送列表数据
     *
     * @return
     * @throws Exception
     */
    public Pager list(InformationSubmittedItem2VO informationSubmittedItem2VO, String id, List<KVObject> conditions, HttpServletRequest request, Connection conn) throws Exception {
        if (StringUtils.isEmpty(id)) {
            MyException.newInstance("资料报送管理id获取错误");
        }

        // 构造SQL语句
        StringBuffer sbSQL = new StringBuffer();sbSQL.append("	SELECT");
        sbSQL.append("	item2.Sid,");
        sbSQL.append("	item2.Id,");
        sbSQL.append("	item2.State,");
        sbSQL.append("	item2.OperatorId,");
        sbSQL.append("	item2.OperateTime,");
        sbSQL.append("	item2.ApplicationId,");
        sbSQL.append("	item2.DataForKVId,");
        sbSQL.append("	item2.DataName,");
        sbSQL.append("	item2.DataComment,");
        sbSQL.append("	item2.Topies,");
        sbSQL.append("	item2.`Status`,");
        sbSQL.append("	item2.SentToAddress,");
        sbSQL.append("	item2.ReceiveName,");
        sbSQL.append("	item2.ReceiveId,");
        sbSQL.append("	item2.ReceiveTime,");
        sbSQL.append("	item2.ReceiveIsConfirm,");
        sbSQL.append("	item2.OutBackName,");
        sbSQL.append("	item2.OutBackId,");
        sbSQL.append("	item2.OutBackTime,");
        sbSQL.append("	item2.OutBackIsConfirm,");
        sbSQL.append("	itemkv.V as dataForKVName");
        sbSQL.append("	FROM");
        sbSQL.append("	oa_informationsubmitteditem2 AS item2 ,");
        sbSQL.append("	system_kv AS itemkv");
        sbSQL.append("	WHERE");
        sbSQL.append("	1=1");
        sbSQL.append("	AND item2.State = 0 ");
        sbSQL.append("	AND item2.DataForKVId = itemkv.K");
        sbSQL.append("	AND itemkv.GroupName='OA_Information_Group'");
        sbSQL.append(" AND item2.applicationId = '" + Database.encodeSQL(id) + "'");

        //设置查询类型
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        //获取分页对象
        return Pager.query(sbSQL.toString(), informationSubmittedItem2VO, conditions, request, queryType, conn);

    }

    /**
     * 获取需要外带的类型列表
     *
     * @param id
     * @param conn
     * @return
     * @throws Exception
     */
    public List<InformationSubmittedItem2PO> list(String id, Connection conn) throws Exception {
        // 构造SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("select * from oa_InformationSubmittedItem2");
        sbSQL.append(" where 1=1");
        sbSQL.append(" and state=0");
        sbSQL.append(" and Status=1");
        sbSQL.append(" and applicationId = '" + Database.encodeSQL(id) + "'");
        return MySQLDao.query(sbSQL.toString(), InformationSubmittedItem2PO.class, null, conn);
    }

    /**
     * 是否全部接收
     *
     * @param id
     * @param conn
     * @return
     * @throws Exception
     */
    public List<InformationSubmittedItem2PO> receiveList(String id, Connection conn) throws Exception {
        // 构造SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("select * from oa_InformationSubmittedItem2");
        sbSQL.append(" where 1=1");
        sbSQL.append(" and state=0");
        sbSQL.append(" and Status=1");
        sbSQL.append(" and applicationId = '" + Database.encodeSQL(id) + "'");
        sbSQL.append(" and ReceiveIsConfirm = " + InformationSubmittedItem2Status.RECEIVEISCONFIRM_NO);
        return MySQLDao.query(sbSQL.toString(), InformationSubmittedItem2PO.class, null, conn);
    }

    /**
     * 是否全部归还
     *
     * @param id
     * @param conn
     * @return
     * @throws Exception
     */
    public List<InformationSubmittedItem2PO> outBackList(String id, Connection conn) throws Exception {
        // 构造SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("select * from oa_InformationSubmittedItem2");
        sbSQL.append(" where 1=1");
        sbSQL.append(" and state=0");
        sbSQL.append(" and Status=1");
        sbSQL.append(" and applicationId = '" + Database.encodeSQL(id) + "'");
        sbSQL.append(" and ReceiveIsConfirm = " + InformationSubmittedItem2Status.RECEIVEISCONFIRM_OK);
        sbSQL.append(" and OutBackIsConfirm = " + InformationSubmittedItem2Status.OUTBACKISCONFIRM_NO);
        return MySQLDao.query(sbSQL.toString(), InformationSubmittedItem2PO.class, null, conn);
    }

    /**
     * 批量确认印章接收
     *
     * @return
     * @throws Exception
     */
    public int updateReceive(InformationSubmittedItem2PO informationSubmittedItem2PO, UserPO user, Connection conn) throws Exception {
        int count = 0;
        //获得需要批量签收的数组
        String[] ids = informationSubmittedItem2PO.getId().split(",");
        //循环数组
        for (int i = 0; i < ids.length; i++) {
            //根据数组的的元素查询用在类型
            //填充数据
            InformationSubmittedItem2PO temp = new InformationSubmittedItem2PO();
            temp.setId(ids[i]);
            temp.setState(Config.STATE_CURRENT);
            //获取当前数据
            temp = MySQLDao.load(temp, InformationSubmittedItem2PO.class, conn);
            //不需要外带数据
            if (temp.getStatus() == InformationSubmittedItem2Status.ISOUT_NO) {
                MyException.deal(MyException.newInstance("该印章不需要外带，不用归还"));
            }
            temp.setState(Config.STATE_UPDATE);
            //更新数据
            count = MySQLDao.update(temp, conn);
            //如果放回的影响函数为1
            if (count == 1) {
                informationSubmittedItem2PO.setId(ids[i]);
                informationSubmittedItem2PO.setSid(MySQLDao.getMaxSid("oa_InformationSubmittedItem2", conn));
                informationSubmittedItem2PO.setState(Config.STATE_CURRENT);
                informationSubmittedItem2PO.setOperatorId(user.getId());
                informationSubmittedItem2PO.setOperateTime(TimeUtils.getNow());
                //设置确认人信息
                informationSubmittedItem2PO.setReceiveId(user.getId());
                informationSubmittedItem2PO.setReceiveName(user.getName());
                informationSubmittedItem2PO.setReceiveTime(TimeUtils.getNow());
                //设置份数
                informationSubmittedItem2PO.setTopies(temp.getTopies());
                //设置资料报送
                informationSubmittedItem2PO.setDataComment(temp.getDataComment());
                informationSubmittedItem2PO.setDataName(temp.getDataName());
                //设置外带地点
                informationSubmittedItem2PO.setSentToAddress(temp.getSentToAddress());
                //状态
                informationSubmittedItem2PO.setStatus(temp.getStatus());
                //确认接收
                informationSubmittedItem2PO.setReceiveIsConfirm(InformationSubmittedItem2Status.RECEIVEISCONFIRM_OK);
                //未归还
                informationSubmittedItem2PO.setOutBackIsConfirm(InformationSubmittedItem2Status.OUTBACKISCONFIRM_NO);
                //归还人
                informationSubmittedItem2PO.setOutBackId(temp.getOutBackId());
                informationSubmittedItem2PO.setOutBackName(temp.getOutBackName());

                //插入新数据
                count = MySQLDao.insert(informationSubmittedItem2PO, conn);
            }
        }
        //数组长度为0 不需要更新主表信息
        if (ids.length == 0) {
            return count;
        }
        //更改主表信息
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("	select *from oa_InformationSubmittedItem2 where 1=1  and state = 0 and status = 1");
        sbSQL.append("	and ApplicationId ='" + informationSubmittedItem2PO.getApplicationId() + "'");
        sbSQL.append("	and ReceiveIsConfirm = " + InformationSubmittedItem2Status.RECEIVEISCONFIRM_NO + ";");
        //判断是否全部接收
        List<Map<String, Object>> list = MySQLDao.query(sbSQL.toString(), conn);
        //已经全部确认接收
        if (list.size() == 0) {
            InformationSubmitted2PO temp = new InformationSubmitted2PO();
            //主表编号
            temp.setId(informationSubmittedItem2PO.getApplicationId());
            temp.setState(Config.STATE_CURRENT);
            temp = MySQLDao.load(temp, InformationSubmitted2PO.class);

            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            //判断影响函数是不是1条
            if (count == 1) {
                temp.setSid(MySQLDao.getMaxSid("oa_sealusagewfa2", conn));
                temp.setState(Config.STATE_CURRENT);
                temp.setOperatorId(user.getId());
                temp.setOperateTime(TimeUtils.getNow());
                //需要外带
                temp.setIsOut(InformationSubmittedItem2Status.ISOUT_OK);
                //设置全部已经接收
                temp.setIsAllReceive(InformationSubmittedItem2Status.ISALLRECEIVE_OK);
                //没有全部归还
                temp.setIsAllOutBack(InformationSubmittedItem2Status.ISALLOUTBACK_NO);
                count = MySQLDao.insert(temp, conn);

            }
        }
        return count;
    }

    /**
     * 批量确认印章归还
     *
     * @return
     * @throws Exception
     */
    public int updateOutBack(InformationSubmittedItem2PO informationSubmittedItem2PO, UserPO user, Connection conn) throws Exception {
        int count = 0;
        //获得需要批量签收的数组
        String[] ids = informationSubmittedItem2PO.getId().split(",");
        //数组长度为0 不需要更新主表信息
        if (ids.length == 0) {
            return count;
        }
        //循环数组
        for (int i = 0; i < ids.length; i++) {
            //根据数组的的元素查询用在类型
            //填充数据
            InformationSubmittedItem2PO temp = new InformationSubmittedItem2PO();
            temp.setId(ids[i]);
            temp.setState(Config.STATE_CURRENT);
            //获取当前数据
            temp = MySQLDao.load(temp, InformationSubmittedItem2PO.class, conn);
            //不需要外带数据
            if (temp.getStatus() == InformationSubmittedItem2Status.ISOUT_NO) {
                MyException.deal(MyException.newInstance("该印章不需要外带，不用确认"));
            }
            temp.setState(Config.STATE_UPDATE);
            //更新数据
            count = MySQLDao.update(temp, conn);
            //如果放回的影响函数为1
            if (count == 1) {
                informationSubmittedItem2PO.setId(ids[i]);
                informationSubmittedItem2PO.setSid(MySQLDao.getMaxSid("oa_InformationSubmittedItem2", conn));
                informationSubmittedItem2PO.setState(Config.STATE_CURRENT);
                informationSubmittedItem2PO.setOperatorId(user.getId());
                informationSubmittedItem2PO.setOperateTime(TimeUtils.getNow());
                //设置确认人信息
                informationSubmittedItem2PO.setReceiveId(temp.getReceiveId());
                informationSubmittedItem2PO.setReceiveName(temp.getReceiveName());
                informationSubmittedItem2PO.setReceiveTime(temp.getReceiveTime());

                //设置份数
                informationSubmittedItem2PO.setTopies(temp.getTopies());
                //设置资料报送
                informationSubmittedItem2PO.setDataComment(temp.getDataComment());
                informationSubmittedItem2PO.setDataName(temp.getDataName());
                //设置外带地点
                informationSubmittedItem2PO.setSentToAddress(temp.getSentToAddress());
                //状态
                informationSubmittedItem2PO.setStatus(temp.getStatus());
                //确认接收
                informationSubmittedItem2PO.setReceiveIsConfirm(InformationSubmittedItem2Status.RECEIVEISCONFIRM_OK);

                //确认归还
                informationSubmittedItem2PO.setOutBackIsConfirm(InformationSubmittedItem2Status.OUTBACKISCONFIRM_OK);
                //确认归还时间
                informationSubmittedItem2PO.setOutBackTime(TimeUtils.getNow());
                informationSubmittedItem2PO.setOutBackId(temp.getOutBackId());
                informationSubmittedItem2PO.setOutBackName(temp.getOutBackName());
                //插入新数据
                count = MySQLDao.insert(informationSubmittedItem2PO, conn);
            }
        }

        //更改主表信息
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("	select *from oa_InformationSubmittedItem2 where 1=1 and state=0 ");
        sbSQL.append("	and ApplicationId ='" + informationSubmittedItem2PO.getApplicationId() + "'");
        sbSQL.append("	and ReceiveIsConfirm = " + InformationSubmittedItem2Status.RECEIVEISCONFIRM_OK + "");
        sbSQL.append("	and  OutBackIsConfirm  = " + InformationSubmittedItem2Status.OUTBACKISCONFIRM_NO + ";");
        //判断是否全部归还
        List<Map<String, Object>> list = MySQLDao.query(sbSQL.toString(), conn);
        //已经全部确认归还
        if (list.size() == 0) {
            InformationSubmitted2PO temp = new InformationSubmitted2PO();
            //主表编号
            temp.setId(informationSubmittedItem2PO.getApplicationId());
            temp.setState(Config.STATE_CURRENT);
            temp = MySQLDao.load(temp, InformationSubmitted2PO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            //判断影响函数是1条
            if (count == 1) {
                temp.setSid(MySQLDao.getMaxSid("OA_InformationSubmitted2", conn));
                temp.setState(Config.STATE_CURRENT);
                temp.setOperatorId(user.getId());
                temp.setOperateTime(TimeUtils.getNow());
                //需要外带
                temp.setIsOut(InformationSubmittedItem2Status.ISOUT_OK);
                //设置全部已经接收
                temp.setIsAllReceive(InformationSubmittedItem2Status.ISALLRECEIVE_OK);
                //全部归还
                temp.setIsAllOutBack(InformationSubmittedItem2Status.ISALLOUTBACK_OK);
                count = MySQLDao.insert(temp, conn);
            }
        }
        return count;
    }
}
