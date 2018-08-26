package com.youngbook.service.oa.assetInfo;

import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.oa.assetInfo.AssetInfoPO;
import com.youngbook.entity.vo.oa.assetInfo.AssetInfoVO;
import com.youngbook.service.BaseService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

/**
 * 创建一个AssetInfoServlet 类 继承BaseServlet类
 *
 * @author Codemaker
 */

public class AssetInfoService extends BaseService {
    /**
     * 判断数据有效性
     * 判断id是否为null 为null则添加数据 不为null 则更新数据
     * 填充数据，执行添加或更新操作
     * 返回影响行数
     * 添加或修改数据，并修改数据状态
     *
     * @param assetInfo
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(AssetInfoPO assetInfo, UserPO user, Connection conn) throws Exception {
        int count = 0;
        // 新增
        if (assetInfo.getId().equals("")) {
            //填充数据
            assetInfo.setSid(MySQLDao.getMaxSid("OA_AssetInfo", conn));
            assetInfo.setId(IdUtils.getUUID32());
            assetInfo.setState(Config.STATE_CURRENT);
            assetInfo.setOperatorId(user.getId());
            assetInfo.setOperateTime(TimeUtils.getNow());

            //添加数据
            count = MySQLDao.insert(assetInfo, conn);
        }
        // 更新
        else {
            //填充数据
            AssetInfoPO temp = new AssetInfoPO();
            temp.setSid(assetInfo.getSid());
            temp = MySQLDao.load(temp, AssetInfoPO.class);
            temp.setState(Config.STATE_UPDATE);

            //更新数据
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                //填充数据
                assetInfo.setSid(MySQLDao.getMaxSid("OA_AssetInfo", conn));
                assetInfo.setState(Config.STATE_CURRENT);
                assetInfo.setOperatorId(user.getId());
                assetInfo.setOperateTime(TimeUtils.getNow());

                //添加
                count = MySQLDao.insert(assetInfo, conn);
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    /**
     * 根据制定的ID获取数据
     *
     * @param id
     * @return
     * @throws Exception
     */
    public AssetInfoPO loadAssetInfoPO(String id) throws Exception {
        //判断数据误是否为null
        if( StringUtils.isEmpty (id)){
            MyException.newInstance ("出差数据获取失败");
        }
        //设置查询数据条件
        AssetInfoPO po = new AssetInfoPO();
        po.setId(id);
        po.setState(Config.STATE_CURRENT);

        //查询数据
        po = MySQLDao.load(po, AssetInfoPO.class);

        return po;
    }

    /**
     * 根据条改编数据的状态
     *
     * @param assetInfo
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete(AssetInfoPO assetInfo, UserPO user, Connection conn) throws Exception {
        int count = 0;

        assetInfo.setState(Config.STATE_CURRENT);
        assetInfo = MySQLDao.load(assetInfo, AssetInfoPO.class);
        assetInfo.setState(Config.STATE_UPDATE);

        //更新数据状态
        count = MySQLDao.update(assetInfo, conn);

        //影响行数为一
        if (count == 1) {

            assetInfo.setSid(MySQLDao.getMaxSid("OA_AssetInfo", conn));
            assetInfo.setState(Config.STATE_DELETE);
            assetInfo.setOperateTime(TimeUtils.getNow());
            assetInfo.setOperatorId(user.getId());

            //添加数据
            count = MySQLDao.insert(assetInfo, conn);
        }

        if (count != 1) {
            throw new Exception("删除失败");
        }

        return count;
    }

    /**
     * 根据SQL语句获取数据集合
     *
     * @param assetInfo
     * @param conditions
     * @return
     * @throws Exception
     */
    public Pager List(AssetInfoVO assetInfo, List<KVObject> conditions) throws Exception {

        HttpServletRequest request = ServletActionContext.getRequest();

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        //创建sql语句
        StringBuffer SQL = new StringBuffer();
        SQL.append("");
        SQL.append("SELECT oa.Id,oa.Sid,oa.State,oa.OperatorId,oa.OperateTime,");
        SQL.append("oa.name,oa.DepartmentId,oa.purpose ,oa.Specification,");
        SQL.append("oa.Quanity,oa.UnitPrice,oa.Money,");
        SQL.append(" oa.BuyTime,oa.StoragePlace,oa.KeeperId,");
        SQL.append(" sd.name as departmentName");
        SQL.append(" FROM oa_assetinfo oa,");
        SQL.append(" system_department sd");
        SQL.append(" where oa.state=0 ");
        SQL.append(" ANd sd.ID=oa.DepartmentId");
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, " sid " + Database.ORDERBY_DESC));

        //获取分页对象
        Pager pager = Pager.query(SQL.toString(), assetInfo, conditions, request, queryType);

        return pager;
    }

    /*
    * 修改：周海鸿
    * 时间：2015-7-20
    * 内容：获取打印数据*/
    public AssetInfoVO getPrintDate(String id) throws Exception {

        //判断数据有效性
        if ("".equals(id) || null == id) {
            throw new Exception("出差数据打印数据获取失败");
        }

        //创建sql语句
        StringBuffer SQL = new StringBuffer();
        SQL.append("");
        SQL.append("SELECT oa.Id,oa.Sid,oa.State,oa.OperatorId,oa.OperateTime,");
        SQL.append("oa.name,oa.DepartmentId,oa.purpose ,oa.Specification,");
        SQL.append("oa.Quanity,oa.UnitPrice,oa.Money,");
        SQL.append(" oa.BuyTime,oa.StoragePlace,oa.KeeperId,");
        SQL.append(" sd.name as departmentName");
        SQL.append(" FROM oa_assetinfo oa,");
        SQL.append(" system_department sd");
        SQL.append(" where 1=1 ");
        SQL.append(" and oa.state=0 ");
        SQL.append("   ANd sd.ID=oa.DepartmentId");
        SQL.append(" AND oa.id = '" + Database.encodeSQL(id) + "'  ");

        //获取数据
        List li = MySQLDao.query(SQL.toString(), AssetInfoVO.class, null);

        return (AssetInfoVO) li.get(0);

    }
}

