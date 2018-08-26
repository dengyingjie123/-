package com.youngbook.service.sale;

import com.youngbook.common.KVObject;
import com.youngbook.common.MyException;
import com.youngbook.common.ReturnObject;
import com.youngbook.common.ReturnObjectCode;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.sale.SalemanGroupPO;
import com.youngbook.entity.po.sale.SalemanSalemangroupPO;
import com.youngbook.service.BaseService;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/5/20.
 *
 * @author <a href="http://c.hopewealth.net/pages/viewpage.action?pageId=27066425
 *         " target="_blank">Zhouhaihong</a>
 */
@Component("salemanSalemangroupService")
public class SalemanSalemangroupService extends BaseService {

    /**
     * 添加数据
     * @param Saleman_salemangrou
     * @param conn
     * @return
     * @throws Exception
     */
    public int insert(SalemanSalemangroupPO Saleman_salemangrou,  Connection conn) throws Exception {
        int count = 0;
        // 新增
        Saleman_salemangrou.setId(IdUtils.getUUID32());
        Saleman_salemangrou.setSaleManStatus(1);

        count = MySQLDao.insert(Saleman_salemangrou, conn);
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    /**
     * 删除销售小组人员
     * @param Saleman_salemangrou
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete(SalemanSalemangroupPO Saleman_salemangrou,  Connection conn) throws Exception {
        int count = 0;
        // 删除
        count = MySQLDao.delete(Saleman_salemangrou, conn);
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    /**
     * 获取小组对象
     * @param saleman_salemangroup
     * @param conditions
     * @param connection
     * @return
     * @throws Exception
     */
    public SalemanSalemangroupPO load(SalemanSalemangroupPO saleman_salemangroup,List<KVObject> conditions, Connection connection) throws Exception {
        StringBuffer SQL =new StringBuffer();
        SQL.append("select * from CRM_SaleMan_SaleManGroup");
        SQL.append(" where  SaleManGroupId = '"+saleman_salemangroup.getSaleManGroupId());
        SQL.append("' AND  SaleManId ='"+saleman_salemangroup.getSaleManId()+"'");
         List<SalemanSalemangroupPO>  ls = MySQLDao.query(SQL.toString(),SalemanSalemangroupPO.class,conditions);
       if(ls.size()<0){
          throw new Exception("销售小组查询错误");
       }
        return ls.get(0);
    }

    /**
     * 修改成员的身份
     * @param SalemanSalemangroup
     * @param connection
     * @return
     */
    public int updateSaleMan(SalemanSalemangroupPO SalemanSalemangroup, Connection connection) throws Exception{
        StringBuffer SQL= new StringBuffer();
        int count =  MySQLDao.update(SalemanSalemangroup,connection);
        return count;
    }


    /**
     * 通过saleManId查找该销售的所有销售组信息
     * @param saleManId
     * @param conn
     * @return
     * @throws Exception
     */
    public List<SalemanSalemangroupPO> getSalemanSalemangroupsBySalemanId(String saleManId, Connection conn) throws Exception {
        //组装SQL
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("select * from CRM_SaleMan_SaleManGroup where saleManId = ? ");
        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL(sbSQL.toString());
        dbSQL.addParameter(1, saleManId);


        //查询
        List<SalemanSalemangroupPO> salemanSalemangroups = new ArrayList<SalemanSalemangroupPO>();
        salemanSalemangroups = MySQLDao.search(dbSQL, SalemanSalemangroupPO.class, conn);


        return salemanSalemangroups;
    }

    /**
     * 设置销售的默认销售组
     * @param saleManSaleManGroup
     * @param conn
     * @return
     * @throws Exception
     */
    public int updateDefaultGroup(SalemanSalemangroupPO saleManSaleManGroup, Connection conn) throws Exception {
        int count = MySQLDao.update(saleManSaleManGroup, conn);
        return count;
    }
}
