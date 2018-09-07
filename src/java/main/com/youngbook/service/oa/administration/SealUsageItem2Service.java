package com.youngbook.service.oa.administration;

import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.oa.administration.SealUsageItem2PO;
import com.youngbook.entity.po.oa.administration.SealUsageWFA2PO;
import com.youngbook.entity.po.oa.administration.SealusageItem2Status;
import com.youngbook.service.BaseService;
import com.youngbook.service.wf.BizRouteService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * Created by haihong on 2015/7/1.
 * 用章类型类类
 */

public class SealUsageItem2Service extends BaseService {
    /**
     * 判断数据有效性
     * 判断id是否为null 为null则添加数据 不为null 则更新数据
     * 填充数据，执行添加或更新操作
     * 返回影响行数
     * <p/>
     * 添加或修改数据，并修改数据状态
     *
     * @param sealUsageItem 用章类型数据域
     * @param user          操作员数据
     * @param conn          数据库连接
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(SealUsageItem2PO sealUsageItem, UserPO user, Connection conn) throws Exception {
        //判断吧数据的有效性
        // 数据操作实体类是否为空
        if (sealUsageItem == null) {
            throw new Exception("用章类型数据提交失败");
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
        if (sealUsageItem.getId().equals("")) {

            //填充数据
            sealUsageItem.setSid(MySQLDao.getMaxSid("OA_SealUsageItem2", conn));
            sealUsageItem.setId(IdUtils.getUUID32());
            sealUsageItem.setState(Config.STATE_CURRENT);
            sealUsageItem.setOperatorId(user.getId());
            sealUsageItem.setOperateTime(TimeUtils.getNow());

            //更新主表信息
            //判断是否需要外带
            int status = sealUsageItem.getStatus();
            if (   SealusageItem2Status.STATUS_OK ==status  ) {
                SealUsageWFA2PO temp = new SealUsageWFA2PO();
                //主表编号
                temp.setId(sealUsageItem.getApplicationId());
                temp.setState(Config.STATE_CURRENT);
                temp = MySQLDao.load(temp, SealUsageWFA2PO.class);
                //更新数据
                temp.setState(Config.STATE_UPDATE);
                count = MySQLDao.update(temp, conn);
                //判断影响函数是不是1条
                if (count == 1) {
                    temp.setSid(MySQLDao.getMaxSid("oa_sealusagewfa2", conn));
                    temp.setState(Config.STATE_CURRENT);
                    temp.setOperatorId(user.getId());
                    temp.setOperateTime(TimeUtils.getNow());
                    //需要外带
                    temp.setIsOut(SealusageItem2Status.ISOUT_OK);
                    //设置全部已经接收
                    temp.setIsAllReceive(SealusageItem2Status.ISALLRECEIVE_NO);
                    //没有全部归还
                    temp.setIsAllOutBack(SealusageItem2Status.ISALLOUTBACK_NO);
                    count = MySQLDao.insert(temp, conn);
                }
            }
            //添加数据
            count = MySQLDao.insert(sealUsageItem, conn);
        }
        // 更新
        else {
            //填充数据
            SealUsageItem2PO temp = new SealUsageItem2PO();
            temp.setSid(sealUsageItem.getSid());
            temp.setState(Config.STATE_CURRENT);
            temp = MySQLDao.load(temp, SealUsageItem2PO.class, conn);
            temp.setState(Config.STATE_UPDATE);
            //更新数据
            count = MySQLDao.update(temp, conn);
            //如果放回的影响函数为1
            if (count == 1) {
                sealUsageItem.setSid(MySQLDao.getMaxSid("OA_SealUsageItem2", conn));
                sealUsageItem.setState(Config.STATE_CURRENT);
                sealUsageItem.setOperatorId(user.getId());
                sealUsageItem.setOperateTime(TimeUtils.getNow());
                //插入新数据
                count = MySQLDao.insert(sealUsageItem, conn);
                //更新主表信息
                if (count == 1 && sealUsageItem.getStatus() == SealusageItem2Status.STATUS_OK) {
                    SealUsageWFA2PO SealUsageWFA2POtemp = new SealUsageWFA2PO();
                    //主表编号
                    SealUsageWFA2POtemp.setId(sealUsageItem.getApplicationId());
                    SealUsageWFA2POtemp.setState(Config.STATE_CURRENT);
                    SealUsageWFA2POtemp = MySQLDao.load(SealUsageWFA2POtemp, SealUsageWFA2PO.class);
                    SealUsageWFA2POtemp.setState(Config.STATE_UPDATE);
                    count = MySQLDao.update(SealUsageWFA2POtemp, conn);
                    //判断影响函数是不是1条
                    if (count == 1) {
                        SealUsageWFA2POtemp.setSid(MySQLDao.getMaxSid("oa_sealusagewfa2", conn));
                        SealUsageWFA2POtemp.setState(Config.STATE_CURRENT);
                        SealUsageWFA2POtemp.setOperatorId(user.getId());
                        SealUsageWFA2POtemp.setOperateTime(TimeUtils.getNow());
                        //需要外带
                        SealUsageWFA2POtemp.setIsOut(SealusageItem2Status.ISOUT_OK);

                        //设置全部已经接收
                        SealUsageWFA2POtemp.setIsAllReceive(SealusageItem2Status.ISALLRECEIVE_NO);
                        //没有全部归还
                        SealUsageWFA2POtemp.setIsAllOutBack(SealusageItem2Status.ISALLOUTBACK_NO);
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
    public SealUsageItem2PO loadSealUsageItemPO(String id) throws Exception {
        //判断id的是否为null；

        if (StringUtils.isEmpty(id)) {
            throw new Exception("用章类型数据错误");
        }

        //设置查询数据
        SealUsageItem2PO po = new SealUsageItem2PO();
        po.setId(id);
        po.setState(Config.STATE_CURRENT);

        //获取数据
        po = MySQLDao.load(po, SealUsageItem2PO.class);

        return po;
    }

    /**
     * 判断传入的数据是否为null
     * 执行更新书状态操作
     * 根据条改编数据的状态
     *
     * @param sealUsageItem 用章类型数据对象
     * @param user          操作人
     * @param conn          数据库连接
     * @return
     * @throws Exception
     */
    public int delete(SealUsageItem2PO sealUsageItem, UserPO user, Connection conn) throws Exception {
        //判断数据是否为null
        if (sealUsageItem == null) {
            MyException.newInstance("删除用章类型数据失败");
        }

        //更新数据状态
        int count = 0;

        sealUsageItem.setState(Config.STATE_CURRENT);
        sealUsageItem = MySQLDao.load(sealUsageItem, SealUsageItem2PO.class);
        sealUsageItem.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(sealUsageItem, conn);

        //更新数据的影响行数为1
        if (count == 1) {
            sealUsageItem.setSid(MySQLDao.getMaxSid("OA_SealUsageItem2", conn));
            sealUsageItem.setState(Config.STATE_DELETE);
            sealUsageItem.setOperateTime(TimeUtils.getNow());
            sealUsageItem.setOperatorId(user.getId());
            count = MySQLDao.insert(sealUsageItem, conn);


            //如果被删除的数据是字表的最后一条数据，并且被删除的数据的状态等于外带状态的话 更改主表状态
            if( sealUsageItem.getStatus()==SealusageItem2Status.ISOUT_OK){
                // 构造SQL语句
                StringBuffer sbSQL = new StringBuffer();
                sbSQL.append("select * from OA_SealUsageItem2");
                sbSQL.append(" where 1=1");
                sbSQL.append(" and state=0");
                sbSQL.append(" and applicationId = '" + Database.encodeSQL(sealUsageItem.getApplicationId()) + "'");
                //获取数据
                List<SealUsageItem2PO> SealUsageItem2POs = MySQLDao.query(sbSQL.toString(), SealUsageItem2PO.class, null,conn);
                boolean flag = true;
                for(SealUsageItem2PO item2po : SealUsageItem2POs){
                    //如果剩下字表数据还有需要外带出去的则不需要更改主表数据
                    if(item2po.getStatus() == SealusageItem2Status.ISOUT_OK){
                        flag = false;
                        break;
                    }
                }
                if(flag) {
                    SealUsageWFA2PO SealUsageWFA2POtemp = new SealUsageWFA2PO();
                    //主表编号
                    SealUsageWFA2POtemp.setId(sealUsageItem.getApplicationId());
                    SealUsageWFA2POtemp.setState(Config.STATE_CURRENT);
                    SealUsageWFA2POtemp = MySQLDao.load(SealUsageWFA2POtemp, SealUsageWFA2PO.class);
                    SealUsageWFA2POtemp.setState(Config.STATE_UPDATE);
                    count = MySQLDao.update(SealUsageWFA2POtemp, conn);
                    //判断影响函数是不是1条
                    if (count == 1) {
                        SealUsageWFA2POtemp.setSid(MySQLDao.getMaxSid("oa_sealusagewfa2", conn));
                        SealUsageWFA2POtemp.setState(Config.STATE_CURRENT);
                        SealUsageWFA2POtemp.setOperatorId(user.getId());
                        SealUsageWFA2POtemp.setOperateTime(TimeUtils.getNow());
                        //不需要外带
                        SealUsageWFA2POtemp.setIsOut(SealusageItem2Status.ISOUT_NO);
                        SealUsageWFA2POtemp.setIsAllReceive("");
                        SealUsageWFA2POtemp.setIsAllOutBack("");
                        count = MySQLDao.insert(SealUsageWFA2POtemp, conn);
                    }
                }
            }
        }

        if (count != 1) {
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
     * 内容：获取用章类型打印数据
     */
    public List<SealUsageItem2PO> getItemData(String id) throws Exception {
        //判断数据有效性
        if ("".equals(id) || null == id) {
            throw new Exception("用章类型打印数据获取失败");
        }

        // 构造SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("select * from OA_SealUsageItem2");
        sbSQL.append(" where 1=1");
        sbSQL.append(" and state=0");
        sbSQL.append(" and applicationId = '" + Database.encodeSQL(id) + "'");

        //获取数据
        List li = MySQLDao.query(sbSQL.toString(), SealUsageItem2PO.class, null);

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
     * 获取用章列表数据
     *
     * @return
     * @throws Exception
     */
    public Pager list(SealUsageItem2PO sealUsageItem, String id, List<KVObject> conditions, HttpServletRequest request, Connection conn) throws Exception {
        if (StringUtils.isEmpty(id)) {
            MyException.newInstance("用章管理id获取错误");
        }

        // 构造SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("select * from OA_SealUsageItem2");
        sbSQL.append(" where 1=1");
        sbSQL.append(" and state=0");
        sbSQL.append(" and applicationId = '" + Database.encodeSQL(id) + "'");

        //设置查询类型
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        //获取分页对象
        return Pager.query(sbSQL.toString(), sealUsageItem, conditions, request, queryType, conn);

    }

    /**
     * 获取需要外带的类型列表
     *
     * @param id
     * @param conn
     * @return
     * @throws Exception
     */
    public List<SealUsageItem2PO> list(String id, Connection conn) throws Exception {
        // 构造SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("select * from OA_SealUsageItem2");
        sbSQL.append(" where 1=1");
        sbSQL.append(" and state=0");
        sbSQL.append(" and Status=1");
        sbSQL.append(" and applicationId = '" + Database.encodeSQL(id) + "'");
        return MySQLDao.query(sbSQL.toString(), SealUsageItem2PO.class, null, conn);
    }

    /**
     * 是否全部接收
     * @param id
     * @param conn
     * @return
     * @throws Exception
     */
    public List<SealUsageItem2PO> Receivelist(String id, Connection conn) throws Exception {
        // 构造SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("select * from OA_SealUsageItem2");
        sbSQL.append(" where 1=1");
        sbSQL.append(" and state=0");
        sbSQL.append(" and Status=1");
        sbSQL.append(" and applicationId = '" + Database.encodeSQL(id) + "'");
        sbSQL.append(" and ReceiveIsConfirm = "+SealusageItem2Status.RECEIVEISCONFIRM_NO);
        return MySQLDao.query(sbSQL.toString(), SealUsageItem2PO.class, null, conn);
    }

    /**
     * 是否全部归还
     * @param id
     * @param conn
     * @return
     * @throws Exception
     */
    public List<SealUsageItem2PO> OutBacklist(String id, Connection conn) throws Exception {
        // 构造SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("select * from OA_SealUsageItem2");
        sbSQL.append(" where 1=1");
        sbSQL.append(" and state=0");
        sbSQL.append(" and Status=1");
        sbSQL.append(" and applicationId = '" + Database.encodeSQL(id) + "'");
        sbSQL.append(" and ReceiveIsConfirm = "+SealusageItem2Status.RECEIVEISCONFIRM_OK);
        sbSQL.append(" and OutBackIsConfirm = "+SealusageItem2Status.OUTBACKISCONFIRM_NO);
        return MySQLDao.query(sbSQL.toString(), SealUsageItem2PO.class, null, conn);
    }

    /**
     * 批量确认印章接收
     *
     * @return
     * @throws Exception
     */
    public int updateReceive(SealUsageItem2PO sealUsageItem, UserPO user, Connection conn) throws Exception {
        int count = 0;
        //获得需要批量签收的数组
        String[] ids = sealUsageItem.getId().split(",");
        //循环数组
        for (int i = 0; i < ids.length; i++) {
            //根据数组的的元素查询用在类型
            //填充数据
            SealUsageItem2PO temp = new SealUsageItem2PO();
            temp.setId(ids[i]);
            temp.setState(Config.STATE_CURRENT);
            //获取当前数据
            temp = MySQLDao.load(temp, SealUsageItem2PO.class, conn);
            //不需要外带数据
            if (temp.getStatus() == SealusageItem2Status.ISOUT_NO) {
                MyException.deal(MyException.newInstance("该印章不需要外带，不用归还"));
            }
            temp.setState(Config.STATE_UPDATE);
            //更新数据
            count = MySQLDao.update(temp, conn);
            //如果放回的影响函数为1
            if (count == 1) {
                sealUsageItem.setId(ids[i]);
                sealUsageItem.setSid(MySQLDao.getMaxSid("OA_SealUsageItem2", conn));
                sealUsageItem.setState(Config.STATE_CURRENT);
                sealUsageItem.setOperatorId(user.getId());
                sealUsageItem.setOperateTime(TimeUtils.getNow());
                //设置确认人信息
                sealUsageItem.setReceiveId(user.getId());
                sealUsageItem.setReceiveName(user.getName());
                sealUsageItem.setReceiveTime(TimeUtils.getNow());
                //设置份数
                sealUsageItem.setTopies(temp.getTopies());
                //设置用章
                sealUsageItem.setSealId(temp.getSealId());
                sealUsageItem.setSealName(temp.getSealName());
                //设置外带地点
                sealUsageItem.setSentToAddress(temp.getSentToAddress());
                //外带人
                sealUsageItem.setSealId(temp.getSealId());
                //状态
                sealUsageItem.setStatus(temp.getStatus());
                //确认接收
                sealUsageItem.setReceiveIsConfirm(SealusageItem2Status.RECEIVEISCONFIRM_OK);
                //未归还
                sealUsageItem.setOutBackIsConfirm(SealusageItem2Status.OUTBACKISCONFIRM_NO);
                //归还人
                sealUsageItem.setOutBackId(temp.getOutBackId());
                sealUsageItem.setOutBackName(temp.getOutBackName());

                //插入新数据
                count = MySQLDao.insert(sealUsageItem, conn);
            }
        }
        //数组长度为0 不需要更新主表信息
        if (ids.length == 0) {
            return count;
        }
        //更改主表信息
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("	select *from oa_sealusageitem2 where 1=1  and state = 0 and status = 1");
        sbSQL.append("	and ApplicationId ='" + sealUsageItem.getApplicationId() + "'");
        sbSQL.append("	and ReceiveIsConfirm = " + SealusageItem2Status.RECEIVEISCONFIRM_NO + ";");
        //判断是否全部接收
        List<Map<String, Object>> list = MySQLDao.query(sbSQL.toString(), conn);
        //已经全部确认接收
        if (list.size() == 0) {
            SealUsageWFA2PO temp = new SealUsageWFA2PO();
            //主表编号
            temp.setId(sealUsageItem.getApplicationId());
            temp.setState(Config.STATE_CURRENT);
            temp = MySQLDao.load(temp, SealUsageWFA2PO.class);

            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            //判断影响函数是不是1条
            if (count == 1) {
                temp.setSid(MySQLDao.getMaxSid("oa_sealusagewfa2", conn));
                temp.setState(Config.STATE_CURRENT);
                temp.setOperatorId(user.getId());
                temp.setOperateTime(TimeUtils.getNow());
                //需要外带
                temp.setIsOut(SealusageItem2Status.ISOUT_OK);
                //设置全部已经接收
                temp.setIsAllReceive(SealusageItem2Status.ISALLRECEIVE_OK);
                //没有全部归还
                temp.setIsAllOutBack(SealusageItem2Status.ISALLOUTBACK_NO);
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
    public int updateOutBack(SealUsageItem2PO sealUsageItem, UserPO user, Connection conn) throws Exception {
        int count = 0;
        //获得需要批量签收的数组
        String[] ids = sealUsageItem.getId().split(",");
        //数组长度为0 不需要更新主表信息
        if (ids.length == 0) {
            return count;
        }
        //循环数组
        for (int i = 0; i < ids.length; i++) {
            //根据数组的的元素查询用在类型
            //填充数据
            SealUsageItem2PO temp = new SealUsageItem2PO();
            temp.setId(ids[i]);
            temp.setState(Config.STATE_CURRENT);
            //获取当前数据
            temp = MySQLDao.load(temp, SealUsageItem2PO.class, conn);
            //不需要外带数据
            if (temp.getStatus() == SealusageItem2Status.ISOUT_NO) {
                MyException.deal(MyException.newInstance("该印章不需要外带，不用确认"));
            }
            temp.setState(Config.STATE_UPDATE);
            //更新数据
            count = MySQLDao.update(temp, conn);
            //如果放回的影响函数为1
            if (count == 1) {
                sealUsageItem.setId(ids[i]);
                sealUsageItem.setSid(MySQLDao.getMaxSid("OA_SealUsageItem2", conn));
                sealUsageItem.setState(Config.STATE_CURRENT);
                sealUsageItem.setOperatorId(user.getId());
                sealUsageItem.setOperateTime(TimeUtils.getNow());
                //设置确认人信息
                sealUsageItem.setReceiveId(temp.getReceiveId());
                sealUsageItem.setReceiveName(temp.getReceiveName());
                sealUsageItem.setReceiveTime(temp.getReceiveTime());

                //设置份数
                sealUsageItem.setTopies(temp.getTopies());
                //设置用章
                sealUsageItem.setSealId(temp.getSealId());
                sealUsageItem.setSealName(temp.getSealName());
                //设置外带地点
                sealUsageItem.setSentToAddress(temp.getSentToAddress());
                //状态
                sealUsageItem.setStatus(temp.getStatus());
                //确认接收
                sealUsageItem.setReceiveIsConfirm(SealusageItem2Status.RECEIVEISCONFIRM_OK);

                //确认归还
                sealUsageItem.setOutBackIsConfirm(SealusageItem2Status.OUTBACKISCONFIRM_OK);
                //确认归还时间
                sealUsageItem.setOutBackTime(TimeUtils.getNow());
                sealUsageItem.setOutBackId(temp.getOutBackId());
                sealUsageItem.setOutBackName(temp.getOutBackName());
                //插入新数据
                count = MySQLDao.insert(sealUsageItem, conn);
            }
        }

        //更改主表信息
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("	select *from oa_sealusageitem2 where 1=1 and state=0 ");
        sbSQL.append("	and ApplicationId ='" + sealUsageItem.getApplicationId() + "'");
        sbSQL.append("	and ReceiveIsConfirm = " + SealusageItem2Status.RECEIVEISCONFIRM_OK + "");
        sbSQL.append("	and  OutBackIsConfirm  = " + SealusageItem2Status.OUTBACKISCONFIRM_NO + ";");
        //判断是否全部接收
        List<Map<String, Object>> list = MySQLDao.query(sbSQL.toString(), conn);
        //已经全部确认接收
        if (list.size() == 0) {
            SealUsageWFA2PO temp = new SealUsageWFA2PO();
            //主表编号
            temp.setId(sealUsageItem.getApplicationId());
            temp.setState(Config.STATE_CURRENT);
            temp = MySQLDao.load(temp, SealUsageWFA2PO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            //判断影响函数是不是1条
            if (count == 1) {
                temp.setSid(MySQLDao.getMaxSid("oa_sealusagewfa2", conn));
                temp.setState(Config.STATE_CURRENT);
                temp.setOperatorId(user.getId());
                temp.setOperateTime(TimeUtils.getNow());
                //需要外带
                temp.setIsOut(SealusageItem2Status.ISOUT_OK);
                //设置全部已经接收
                temp.setIsAllReceive(SealusageItem2Status.ISALLRECEIVE_OK);
                //全部归还
                temp.setIsAllOutBack(SealusageItem2Status.ISALLOUTBACK_OK);
                count = MySQLDao.insert(temp, conn);
            }
        }
        return count;
    }
}
