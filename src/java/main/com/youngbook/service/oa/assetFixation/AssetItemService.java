package com.youngbook.service.oa.assetFixation;

import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.oa.assetFixation.AssetItemPO;
import com.youngbook.service.BaseService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

/**
 * Created by Jepson on 2015/6/8.
 */
public class AssetItemService extends BaseService {
    /**
     * 添加或修改数据，并修改数据状态
     *
     * @param assetItem
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(AssetItemPO assetItem, UserPO user, Connection conn) throws Exception{
        int count = 0;
        // 新增
        if (assetItem.getId().equals("")) {
            assetItem.setSid(MySQLDao.getMaxSid("OA_AssetItem", conn));
            assetItem.setId(IdUtils.getUUID32());
            assetItem.setState(Config.STATE_CURRENT);
            assetItem.setOperatorId(user.getId());
            assetItem.setOperateTime(TimeUtils.getNow());

            //添加
            count = MySQLDao.insert(assetItem, conn);
        }
        // 更新
        else {
            AssetItemPO temp = new AssetItemPO();
            temp.setSid(assetItem.getSid());
            temp = MySQLDao.load(temp, AssetItemPO.class);
            temp.setState(Config.STATE_UPDATE);

            //更新
            count = MySQLDao.update(temp, conn);
            if (count == 1) {

                assetItem.setSid(MySQLDao.getMaxSid("OA_AssetItem", conn));
                assetItem.setState(Config.STATE_CURRENT);
                assetItem.setOperatorId(user.getId());
                assetItem.setOperateTime(TimeUtils.getNow());

                //添加
                count = MySQLDao.insert(assetItem, conn);
            }
        }
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
    public AssetItemPO loadAssetItemPO(String id) throws Exception{
        if(StringUtils.isEmpty (id)){
            MyException.newInstance ("资产id不能为null");
        }
        //设置数据
        AssetItemPO po = new AssetItemPO();
        po.setId (id);
        po.setState(Config.STATE_CURRENT);

        //根据条件查询数据
        po = MySQLDao.load(po, AssetItemPO.class);

        return po;
    }

    /**
     * 根据条改编数据的状态
     * @param assetItem
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete(AssetItemPO assetItem, UserPO user, Connection conn) throws Exception {
        int count = 0;

        //设置数据
        assetItem.setState (Config.STATE_CURRENT);
        assetItem = MySQLDao.load(assetItem, AssetItemPO.class);
        assetItem.setState (Config.STATE_UPDATE);

        //更新数据状态
        count = MySQLDao.update(assetItem, conn);
        if (count == 1) {

            //设置数据
            assetItem.setSid(MySQLDao.getMaxSid("OA_AssetItem", conn));
            assetItem.setState(Config.STATE_DELETE);
            assetItem.setOperateTime(TimeUtils.getNow());
            assetItem.setOperatorId(user.getId());

            //添加数据
            count = MySQLDao.insert(assetItem, conn);
        }

        if (count != 1) {
            throw new Exception("删除失败");
        }

        return count;
    }

    /**
     * 2015-6-24
     * 周海鸿
     * 创建一个根据编号获取总金额的方法
     *
     * @param id
     * @return 差旅费报销总金额
     * @throws Exception
     */
    public String getMoneys(String id) throws Exception {
        //创建sql语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("");
        sbSQL.append(" SELECT");
        sbSQL.append(" sum(money) as money");
        sbSQL.append(" FROM");
        sbSQL.append(" OA_AssetItem");
        sbSQL.append (" WHERE");
        sbSQL.append(" 1=1");
        sbSQL.append (" AND  state =0 ");
        sbSQL.append(" AND  applicationId = '" + Database.encodeSQL(id) + "'");

        //根据sql语句获取数据
        List li = MySQLDao.query(sbSQL.toString(), AssetItemPO.class, null);

        //操作数据
        String moneys = null;
        if(li !=null && li.size()>0){
            AssetItemPO p = (AssetItemPO) li.get(0);
            moneys= String.valueOf(p.getMoney());
        }
        return moneys;
    }

    /*修改:周海鸿
    * 时间：2015-7-20
    * 内容：获取打印数据*/
    public  List<AssetItemPO> getPrintDate(String id)throws  Exception{

        //判断数据有效性
        if("".equals(id) || null== id){
            throw   new Exception("固定资产项目打印数据错误");
        }

        // 构造SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("select * from OA_AssetItem item where item.state = 0 and item.applicationId = '" + Database.encodeSQL(id) + "'");

        //获取数据
        List li =   MySQLDao.query(sbSQL.toString(),AssetItemPO.class,null);

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
     *
     *
     * @return
     * @throws Exception
     */
    public Pager list (AssetItemPO AssetItemPO, String id, List<KVObject> conditions, HttpServletRequest request) throws Exception {

        //判断id是否null
        if ( StringUtils.isEmpty (id) ) {
            MyException.newInstance ("资产信息不能为null");
        }

        //创建按sql语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("select * from OA_AssetItem item where item.state = 0 and item.applicationId = '" + Database.encodeSQL(id) + "'");


        //设置查询类型
        QueryType queryType = new QueryType (Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        //获取分页对象
        return Pager.query (sbSQL.toString (), AssetItemPO, conditions, request, queryType);

    }

}

