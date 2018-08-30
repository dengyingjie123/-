package com.youngbook.service.sale;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.sale.ISalemanGroupDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.sale.SalemanGroupPO;
import com.youngbook.entity.vo.Sale.SalesManVO;
import com.youngbook.service.BaseService;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

/**
 * 创建一个SalemanGroupServlet 类 继承BaseServlet类
 *
 * @author Codemaker
 */

@Component("salemanGroupService")
public class SalemanGroupService extends BaseService {

    @Autowired
    ISalemanGroupDao salemanGroupDao;

    public SalemanGroupPO getDefaultSalemanGroupByUserId(String userId, Connection conn) throws Exception {
        return salemanGroupDao.getDefaultSalemanGroupByUserId(userId, conn);
    }

    public SalemanGroupPO loadSalemanGroupPO(String id, Connection conn) throws Exception {
        return salemanGroupDao.loadSalemanGroupPO(id, conn);
    }




    /**
     * 添加或修改数据，并修改数据状态
     *
     * @param salemanGroup
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(SalemanGroupPO salemanGroup, UserPO user, Connection conn) throws Exception {
        //判断数据有效性
        if (salemanGroup == null) {
            throw new Exception("销售小组提交失败");
        }

        //当前用户操作类是否为空
        if (user == null) {
            throw new Exception("当前用户失效");
        }

        //当前数据链接是否空
        if (conn == null) {
            throw new Exception("链接错误");
        }
        int count = 0;
        // 新增
        if (salemanGroup.getId().equals("")) {
            salemanGroup.setSid(MySQLDao.getMaxSid("CRM_SalemanGroup", conn));
            salemanGroup.setId(IdUtils.getUUID32());
            salemanGroup.setState(Config.STATE_CURRENT);
            salemanGroup.setOperatorId(user.getId());
            salemanGroup.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(salemanGroup, conn);
        }
        // 更新
        else {
            SalemanGroupPO temp = new SalemanGroupPO();
            temp.setSid(salemanGroup.getSid());
            temp = MySQLDao.load(temp, SalemanGroupPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                salemanGroup.setSid(MySQLDao.getMaxSid("CRM_SalemanGroup", conn));
                salemanGroup.setState(Config.STATE_CURRENT);
                salemanGroup.setOperatorId(user.getId());
                salemanGroup.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(salemanGroup, conn);
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }



    /**
     * 根据条改编数据的状态
     *
     * @param salemanGroup
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete(SalemanGroupPO salemanGroup, UserPO user, Connection conn) throws Exception {

//判断数据有效性
        if (salemanGroup == null) {
            throw new Exception("销售小组提交失败");
        }

        //当前用户操作类是否为空
        if (user == null) {
            throw new Exception("当前用户失效");
        }

        //当前数据链接是否空
        if (conn == null) {
            throw new Exception("链接错误");
        }

        int count = 0;
        salemanGroup.setState(Config.STATE_CURRENT);
        salemanGroup = MySQLDao.load(salemanGroup, SalemanGroupPO.class);
        salemanGroup.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(salemanGroup, conn);
        if (count == 1) {
            salemanGroup.setSid(MySQLDao.getMaxSid("CRM_SalemanGroup", conn));
            salemanGroup.setState(Config.STATE_DELETE);
            salemanGroup.setOperateTime(TimeUtils.getNow());
            salemanGroup.setOperatorId(user.getId());
            count = MySQLDao.insert(salemanGroup, conn);
        }

        if (count != 1) {
            throw new Exception("删除失败");
        }
        return count;
    }

    /**
     * 获取该销售小组的销售人员列表
     *
     * @return
     * @throws Exception
     */
    public Pager saleManPager(String saleManGroupId, String salesmanName, int currentPage, int showPageCount, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("3A7A1804");
        dbSQL.addParameter4All("saleManGroupId", saleManGroupId);
        dbSQL.addParameter4All("salesmanName", salesmanName);
        dbSQL.initSQL();

        Pager pager = Pager.search(dbSQL.getSQL(), dbSQL.getParameters(), SalesManVO.class, currentPage, showPageCount, conn);
        return pager;
    }

    /***
     * 获取销售人员列表 与状态
     * @return
     * @throws Exception
     */
    public Pager saleManPager(String salemanGroupId, Connection conn) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT DISTINCT");
        sbSQL.append("     temp.sid,");
        sbSQL.append("     temp.id,");
        sbSQL.append("     temp.userName,");
        sbSQL.append("     temp.mobile,");
        sbSQL.append("     temp.address,");
        sbSQL.append("     temp.email,");
        sbSQL.append("     temp.jointime,");
        sbSQL.append("     temp.gender,");
        sbSQL.append("     temp.birthday,");
        sbSQL.append("     temp.idnumber,");
        sbSQL.append("     temp.salesLevel,");
        sbSQL.append("     temp.position,");
        sbSQL.append("     temp.state,");
        sbSQL.append("     temp.UserId,");
        sbSQL.append("     crs.saleManStatus,");
        sbSQL.append("     crs.defaultGroup");
        sbSQL.append(" FROM");
        sbSQL.append("     (");
        sbSQL.append("         SELECT");
        sbSQL.append("             U.sid,");
        sbSQL.append("             U.id,");
        sbSQL.append("             U.`name` AS userName,");
        sbSQL.append("             U.mobile,");
        sbSQL.append("             U.address,");
        sbSQL.append("             U.email,");
        sbSQL.append("             U.jointime,");
        sbSQL.append("             kvsex.V gender,");
        sbSQL.append("             U.birthday,");
        sbSQL.append("             U.idnumber,");
        sbSQL.append("             kv.V salesLevel,");
        sbSQL.append("             U.position,");
        sbSQL.append("             s.state,");
        sbSQL.append("             s.UserId");
        sbSQL.append("         FROM");
        sbSQL.append("             system_user U");
        sbSQL.append("         LEFT JOIN crm_saleman S ON S.UserId = U.id");
        sbSQL.append("         LEFT JOIN system_kv kvsex ON kvsex.K = U.gender AND kvsex.GroupName = 'Sex'");
        sbSQL.append("         LEFT JOIN system_kv kv ON kv.K = S.Sales_levelId AND kv.GroupName = 'sales_level'");
        sbSQL.append("         WHERE");
        sbSQL.append("             U.state = 0");
        sbSQL.append("     ) temp,");
        sbSQL.append("     crm_saleman_salemangroup crs");
        sbSQL.append(" WHERE");
        sbSQL.append("     1 = 1");
        sbSQL.append(" AND crs.saleManId = temp.id");
        sbSQL.append(" AND temp.state = 0");
        sbSQL.append(" AND temp.id IN (");
        sbSQL.append("     SELECT");
        sbSQL.append("         saleManId");
        sbSQL.append("     FROM");
        sbSQL.append("         crm_saleman_salemangroup");
        sbSQL.append("     WHERE");
        sbSQL.append("         saleManGroupId = '"+salemanGroupId+"'");
        sbSQL.append(" )");
        sbSQL.append(" AND crs.saleManGroupId = '"+salemanGroupId+"'");

        DatabaseSQL dbSQL = DatabaseSQL.getInstance(sbSQL.toString());

        Pager pager = Pager.search(dbSQL.getSQL(), dbSQL.getParameters(), SalesManVO.class, request, conn);
        return pager;
    }


//    /**
//     * 获取小组销售人员列表
//     * @param salemanGroupId
//     * @return
//     * @throws Exception
//     */
    public List saleManList(String salemanGroupId, Connection conn)throws  Exception {

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT DISTINCT");
        sbSQL.append("     temp.sid,");
        sbSQL.append("     temp.id,");
        sbSQL.append("     temp.userName,");
        sbSQL.append("     temp.mobile,");
        sbSQL.append("     temp.address,");
        sbSQL.append("     temp.email,");
        sbSQL.append("     temp.jointime,");
        sbSQL.append("     temp.gender,");
        sbSQL.append("     temp.birthday,");
        sbSQL.append("     temp.idnumber,");
        sbSQL.append("     temp.salesLevel,");
        sbSQL.append("     temp.position,");
        sbSQL.append("     temp.state,");
        sbSQL.append("     temp.UserId,");
        sbSQL.append("     temp.SaleManId AS saleManId,");
        sbSQL.append("     crs.saleManStatus");
        sbSQL.append(" FROM");
        sbSQL.append("     (");
        sbSQL.append("         SELECT");
        sbSQL.append("             U.sid,");
        sbSQL.append("             U.id,");
        sbSQL.append("             U.`name` AS userName,");
        sbSQL.append("             U.mobile,");
        sbSQL.append("             U.address,");
        sbSQL.append("             U.email,");
        sbSQL.append("             U.jointime,");
        sbSQL.append("             kvsex.V gender,");
        sbSQL.append("             U.birthday,");
        sbSQL.append("             U.idnumber,");
        sbSQL.append("             kv.V salesLevel,");
        sbSQL.append("             U.position,");
        sbSQL.append("             s.state,");
        sbSQL.append("             s.id AS SaleManId,");
        sbSQL.append("             s.UserId");
        sbSQL.append("         FROM");
        sbSQL.append("             system_user U");
        sbSQL.append("         LEFT JOIN crm_saleman S ON S.UserId = U.id");
        sbSQL.append("         LEFT JOIN system_kv kvsex ON kvsex.K = U.gender AND kvsex.GroupName = 'Sex'");
        sbSQL.append("         LEFT JOIN system_kv kv ON kv.K = S.Sales_levelId");
        sbSQL.append("         WHERE");
        sbSQL.append("             U.state = 0");
        sbSQL.append("     ) temp,");
        sbSQL.append("     crm_saleman_salemangroup crs");
        sbSQL.append(" WHERE");
        sbSQL.append("     1 = 1");
        sbSQL.append(" AND crs.saleManId = temp.id");
        sbSQL.append(" AND temp.state = 0");
        sbSQL.append(" AND temp.id IN (");
        sbSQL.append("     SELECT");
        sbSQL.append("         saleManId");
        sbSQL.append("     FROM");
        sbSQL.append("         crm_saleman_salemangroup");
        sbSQL.append("     WHERE");
        sbSQL.append("         saleManGroupId = ?");
        sbSQL.append(" )");
        sbSQL.append(" AND crs.saleManGroupId = ?");

        DatabaseSQL dbSQL = DatabaseSQL.getInstance(sbSQL.toString());
        dbSQL.addParameter(1, salemanGroupId).addParameter(2, salemanGroupId);


        return MySQLDao.search(dbSQL.getSQL(), dbSQL.getParameters(), SalesManVO.class, null, conn);
    }

    /**
     * 判断数据是否可以被删除
     * 修改人： 周海鸿
     * 时间：2015-7-7
     *
     * @param salemanGroup
     * @param conn
     * @return
     */
    public boolean isDelete(SalemanGroupPO salemanGroup, Connection conn) throws Exception {

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT DISTINCT ");
        sbSQL.append(" 	* ");
        sbSQL.append(" FROM ");
        sbSQL.append(" 	crm_salemangroup cs, ");
        sbSQL.append(" 	system_department sd ");
        sbSQL.append(" WHERE ");
        sbSQL.append(" 	1 = 1 ");
        sbSQL.append(" AND cs.state = 0 ");
        sbSQL.append(" AND cs.DepartmentId = sd.id ");
        sbSQL.append(" AND cs.DepartmentId = '"+Database.encodeSQL(salemanGroup.getDepartmentId())+"' ");
        //根据SQL语句查询数据
        List list = MySQLDao.query(sbSQL.toString(),conn);

        //返回的数据列表大于条
        if(list.size()>1){
            return true;
        }
        return false;
    }

}
