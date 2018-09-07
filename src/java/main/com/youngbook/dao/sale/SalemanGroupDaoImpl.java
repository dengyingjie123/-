package com.youngbook.dao.sale;

import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.sale.SalemanGroupPO;
import com.youngbook.entity.po.sale.SalemanSalemangroupPO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2016/5/28.
 */
@Component("salemanGroupDao")
public class SalemanGroupDaoImpl implements ISalemanGroupDao {

    public void setDefaultFinanceCircle(String userId, Connection conn) throws Exception {

        SalemanSalemangroupPO salemanSalemangroupPO = new SalemanSalemangroupPO();
        salemanSalemangroupPO.setId(IdUtils.getUUID32());
        salemanSalemangroupPO.setSaleManGroupId(Config.getSystemConfig("financeCircle.salesgroup.default.id"));
        salemanSalemangroupPO.setSaleManId(userId);
        salemanSalemangroupPO.setSaleManStatus(1);
        salemanSalemangroupPO.setDefaultGroup(1);

        int count = MySQLDao.insert(salemanSalemangroupPO, conn);

        System.out.println(count);
    }



    /**
     * 根据制定的ID获取数据
     *
     * @param id
     * @return
     * @throws Exception
     */
    public SalemanGroupPO loadSalemanGroupPO(String id, Connection conn) throws Exception {
        SalemanGroupPO po = new SalemanGroupPO();

        po.setId(id);
        po.setState(Config.STATE_CURRENT);
        po = MySQLDao.load(po, SalemanGroupPO.class, conn);

        return po;
    }

    /**
     * 通过用户获取默认的销售组
     *
     * 作者：李昕骏
     * 内容：创建代码
     * 时间：2016年5月28日
     *
     * 修改：邓超
     * 内容：修改代码（修复 SQL）
     * 时间：2016年5月29日
     * 
     * @param userId
     * @param conn
     * @return
     * @throws Exception
     */
    public SalemanGroupPO getDefaultSalemanGroupByUserId(String userId, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.getInstance("select g.* from crm_saleman_salemangroup sg, crm_salemangroup g where sg.saleManId = ? and g.state = 0 and g.id = sg.salemanGroupId").addParameter(1, userId);

        List<SalemanGroupPO> groups = MySQLDao.search(dbSQL, SalemanGroupPO.class, conn);

        if (groups != null && groups.size() > 0) {
            return groups.get(0);
        }

        return null;
    }

    /**
     * 通过销售人员的编号来查找
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年4月20日
     *
     * @param salemanId
     * @param conn
     * @return
     * @throws Exception
     */
    public SalemanGroupPO loadBySaleman(String salemanId, Connection conn) throws Exception {

        if(StringUtils.isEmpty(salemanId) || conn == null) {
            MyException.newInstance(ReturnObject.CODE_EXCEPTION, "程序异常").throwException();
        }

        String sql = "select g.* from crm_saleman_SalemanGroup ssg, crm_salemanGroup g where 1 = 1 and g.state = 0 and ssg.salemangroupId = g.id and ssg.salemanId = '" + salemanId + "'";
        List<SalemanGroupPO> list = MySQLDao.query(sql, SalemanGroupPO.class, new ArrayList<KVObject>(), conn);

        if(list.size() == 0) {
            return null;
        }
        if(list.size() > 1) {
            MyException.newInstance(ReturnObjectCode.SALEMAN_GROUP_MORE_THAN_ONE, "销售人员不能存在于多个销售组中").throwException();
        }

        return list.get(0);

    }
}
