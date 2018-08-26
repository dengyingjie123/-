package com.youngbook.service.oa.administration;

import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.oa.administration.SealUsageItemPO;
import com.youngbook.service.BaseService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

/**
 * Created by haihong on 2015/7/1.
 * 用章类型类类
 *
 * @author <a href="http://c.hopewealth.net/pages/viewpage.action?pageId=27066425
 *         " target="_blank">Zhouhaihong</a>
 */

public class SealUsageItemService extends BaseService {
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
    public int insertOrUpdate (SealUsageItemPO sealUsageItem, UserPO user, Connection conn) throws Exception {
        //判断吧数据的有效性
        // 数据操作实体类是否为空
        if ( sealUsageItem == null ) {
            throw new Exception ("用章类型数据提交失败");
        }

        //当前用户操作类是否为空
        if ( user == null ) {
            throw new Exception ("当前用户失效");
        }

        //当前数据链接是否空
        if ( conn == null ) {
            throw new Exception ("链接错误");
        }

        //设置影响函数变量
        int count = 0;

        // 判断id的是否为null
        //新增
        if ( sealUsageItem.getId ().equals ("") ) {

            //填充数据
            sealUsageItem.setSid (MySQLDao.getMaxSid ("OA_SealUsageItem", conn));
            sealUsageItem.setId (IdUtils.getUUID32 ());
            sealUsageItem.setState (Config.STATE_CURRENT);
            sealUsageItem.setOperatorId (user.getId ());
            sealUsageItem.setOperateTime (TimeUtils.getNow ());

            //添加数据
            count = MySQLDao.insert (sealUsageItem, conn);
        }
        // 更新
        else {
            //填充数据
            SealUsageItemPO temp = new SealUsageItemPO ();
            temp.setSid (sealUsageItem.getSid ());
            temp = MySQLDao.load (temp, SealUsageItemPO.class, conn);
            temp.setState (Config.STATE_UPDATE);

            //更新数据
            count = MySQLDao.update (temp, conn);
            //如果放回的影响函数为1
            if ( count == 1 ) {
                sealUsageItem.setSid (MySQLDao.getMaxSid ("OA_SealUsageItem", conn));
                sealUsageItem.setState (Config.STATE_CURRENT);
                sealUsageItem.setOperatorId (user.getId ());
                sealUsageItem.setOperateTime (TimeUtils.getNow ());

                //插入新数据
                count = MySQLDao.insert (sealUsageItem, conn);
            }
        }
        if ( count != 1 ) {
            throw new Exception ("数据库异常");
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
    public SealUsageItemPO loadSealUsageItemPO (String id) throws Exception {
        //判断id的是否为null；

        if ( StringUtils.isEmpty (id) ) {
            throw new Exception ("用章类型数据错误");
        }

        //设置查询数据
        SealUsageItemPO po = new SealUsageItemPO ();
        po.setId (id);
        po.setState (Config.STATE_CURRENT);

        //获取数据
        po = MySQLDao.load (po, SealUsageItemPO.class);

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
    public int delete (SealUsageItemPO sealUsageItem, UserPO user, Connection conn) throws Exception {
        //判断数据是否为null
        if ( sealUsageItem == null ) {
            MyException.newInstance ("删除用章类型数据失败");
        }

        //更新数据状态
        int count = 0;

        sealUsageItem.setState (Config.STATE_CURRENT);
        sealUsageItem = MySQLDao.load (sealUsageItem, SealUsageItemPO.class);
        sealUsageItem.setState (Config.STATE_UPDATE);
        count = MySQLDao.update (sealUsageItem, conn);

        //更新数据的影响行数为1
        if ( count == 1 ) {
            sealUsageItem.setSid (MySQLDao.getMaxSid ("OA_SealUsageItem", conn));
            sealUsageItem.setState (Config.STATE_DELETE);
            sealUsageItem.setOperateTime (TimeUtils.getNow ());
            sealUsageItem.setOperatorId (user.getId ());
            count = MySQLDao.insert (sealUsageItem, conn);
        }

        if ( count != 1 ) {
            throw new Exception ("删除失败");
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
    public List<SealUsageItemPO> getItemData (String id) throws Exception {
        //判断数据有效性
        if ( "".equals (id) || null == id ) {
            throw new Exception ("用章类型打印数据获取失败");
        }

        // 构造SQL语句
        StringBuffer sbSQL = new StringBuffer ();
        sbSQL.append ("select * from OA_SealUsageItem");
        sbSQL.append (" where 1=1");
        sbSQL.append (" and state=0");
        sbSQL.append (" and applicationId = '" + Database.encodeSQL (id) + "'");

        //获取数据
        List li = MySQLDao.query (sbSQL.toString (), SealUsageItemPO.class, null);

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
    public Pager list (SealUsageItemPO sealUsageItem, String id, List<KVObject> conditions, HttpServletRequest request) throws Exception {
        if ( StringUtils.isEmpty (id) ) {
            MyException.newInstance ("用章管理id获取错误");
        }

        // 构造SQL语句
        StringBuffer sbSQL = new StringBuffer ();
        sbSQL.append ("select * from OA_SealUsageItem");
        sbSQL.append (" where 1=1");
        sbSQL.append (" and state=0");
        sbSQL.append (" and applicationId = '" + Database.encodeSQL (id) + "'");

        //设置查询类型
        QueryType queryType = new QueryType (Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        //获取分页对象
        return Pager.query (sbSQL.toString (), sealUsageItem, conditions, request, queryType);

    }
}
