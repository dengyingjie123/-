package com.youngbook.service.sale;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.sale.SaleTask4GroupPO;
import com.youngbook.entity.po.sale.SaleTask4SalemanPO;
import com.youngbook.entity.po.sale.SalemanGroupPO;
import com.youngbook.entity.po.sale.SalemanSalemangroupPO;
import com.youngbook.entity.vo.Sale.SaleTask4GroupVO;
import com.youngbook.entity.vo.Sale.SalesManVO;
import com.youngbook.entity.vo.production.ProductionVO;
import com.youngbook.service.BaseService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2015/5/22.
 * 产品销售小组分配业务逻辑类
 *
 * @author <a href="http://c.hopewealth.net/pages/viewpage.action?pageId=27066425
 *         " target="_blank">Zhouhaihong</a>
 */

public class SaleTask4GroupService extends BaseService {

    private Object productName;

    public Pager list(SaleTask4GroupVO salevo, List<KVObject> conditions) throws Exception {
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        HttpServletRequest request = ServletActionContext.getRequest();
        StringBuffer SQL = new StringBuffer();
        SQL.append(" SELECT  DISTINCT s4.Sid, s4.Id, s4.State,s4.OperatorId,");
        SQL.append(" s4.OperateTime,s4.ProductionId,");
        SQL.append(" s4.SaleGroupId,s4.TaskMoney,");
        SQL.append(" s4.StartTime,s4.EndTime,");
        SQL.append(" s4.WaitingMoney,s4.AppointmengMoney,");
        SQL.append("s4.SoldMoney,s4.TotoalCancelMoney,");
        SQL.append(" cp.`Name` as productionName,ca.`Name` as saleGroupName");
        SQL.append(" FROM sale_saletask4group s4 , crm_production cp,crm_salemangroup ca");
        SQL.append(" where  s4.state=0 AND  cp.state=0 and ca.state = 0");
        SQL.append(" ANd cp.Status=2 AND  s4.ProductionId=cp.id AND s4.SaleGroupId= ca.id");
        Pager pager = Pager.query(SQL.toString(), salevo, conditions, request, queryType);
        return pager;
    }


    /**
     * 添加或修改数据，并修改数据状态
     *
     * @param saleTask4Group
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(SaleTask4GroupPO saleTask4Group, UserPO user, Connection conn) throws Exception {
        int count = 0;
        // 新增
        if (saleTask4Group.getId().equals("")) {
            saleTask4Group.setSid(MySQLDao.getMaxSid("sale_SaleTask4Group", conn));
            saleTask4Group.setId(IdUtils.getUUID32());
            saleTask4Group.setState(Config.STATE_CURRENT);
            saleTask4Group.setOperatorId(user.getId());
            saleTask4Group.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(saleTask4Group, conn);
        }
        // 更新
        else {
            SaleTask4GroupPO temp = new SaleTask4GroupPO();
            temp.setSid(saleTask4Group.getSid());
            temp = MySQLDao.load(temp, SaleTask4GroupPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                saleTask4Group.setSid(MySQLDao.getMaxSid("sale_SaleTask4Group", conn));
                saleTask4Group.setState(Config.STATE_CURRENT);
                saleTask4Group.setOperatorId(user.getId());
                saleTask4Group.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(saleTask4Group, conn);
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
    public SaleTask4GroupPO loadSaleTask4GroupPO(String id) throws Exception {
        SaleTask4GroupPO po = new SaleTask4GroupPO();
        po.setId(id);
        po.setState(Config.STATE_CURRENT);
        po = MySQLDao.load(po, SaleTask4GroupPO.class);
        return po;
    }

    /**
     * 根据条改编数据的状态
     *
     * @param saleTask4Group
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete(SaleTask4GroupPO saleTask4Group, UserPO user, Connection conn) throws Exception {
        int count = 0;
        saleTask4Group.setState(Config.STATE_CURRENT);
        saleTask4Group = MySQLDao.load(saleTask4Group, SaleTask4GroupPO.class);
        saleTask4Group.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(saleTask4Group, conn);
        if (count == 1) {
            saleTask4Group.setSid(MySQLDao.getMaxSid("sale_SaleTask4Group", conn));
            saleTask4Group.setState(Config.STATE_DELETE);
            saleTask4Group.setOperateTime(TimeUtils.getNow());
            saleTask4Group.setOperatorId(user.getId());
            count = MySQLDao.insert(saleTask4Group, conn);
        }
        //将小组人员分配产品列表删除
        count = getSaleTask4Salemans(saleTask4Group, user, conn);
        if (count != 1) {
            throw new Exception("删除失败");
        }
        return count;
    }

    /**
     * 将本小组下的对应的产品的数据都删除
     *
     * @param salemanService
     * @param saleTask4SalemanPO
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int uploadSaleTask4Saleman(SaleTask4SalemanService salemanService, SaleTask4SalemanPO saleTask4SalemanPO, UserPO user, Connection conn) throws Exception {
        int count = salemanService.delete(saleTask4SalemanPO, user, conn);
        return count;
    }

    /**
     * 获取该小组对应分配产品的人员列表
     *
     * @param saleTask4Group
     * @param user
     * @param conn
     * @return
     */
    public int getSaleTask4Salemans(SaleTask4GroupPO saleTask4Group, UserPO user, Connection conn) throws Exception {
        //获取该小组与项目一样的小组人员列表
        String sql = "select * from crm_saleman_salemangroup sg where sg.salemangroupid = '" + Database.encodeSQL(saleTask4Group.getSaleGroupId()) + "'";
        List<SalemanSalemangroupPO> list = MySQLDao.query(sql, SalemanSalemangroupPO.class, new ArrayList<KVObject>(), conn);
        int count = 1;
        for(SalemanSalemangroupPO po : list) {
            count = MySQLDao.delete(po, conn);
        }
        return count;
    }

    /**
     * 获取与本项目分配无关的人员
     *
     * @param salesManVO
     * @param SQL2
     * @param conditions
     * @return
     * @throws Exception
     */
    public Pager SaleManlist(SalesManVO salesManVO, String SQL2, List<KVObject> conditions) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        //组装SQL语句
        StringBuffer SQl = new StringBuffer();
        SQl.append(" SELECT *,name as userName from(select sid,id,name,mobile,address,email,jointime,gender,birthday,idnumber,salesLevel,position,state,UserId from");
        SQl.append("  (select U.sid,U.id,U.name,U.mobile,U.address,U.email,U.jointime,kvsex.V gender,");
        SQl.append("  U.birthday,U.idnumber,kv.V salesLevel,U.position,s.state");
        SQl.append(",s.UserId from system_user U");
        SQl.append("  LEFT JOIN crm_saleman S on S.UserId=U.id");
        SQl.append("  Left JOin system_kv kvsex on kvsex.K=U.gender");
        SQl.append("  LEFT JOIN system_kv kv ON kv.K= S.Sales_levelId");
        SQl.append(" where U.state=0) temp where temp.state=0 ANd");
        SQl.append(" " + Database.encodeSQL(SQL2) + "");
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.query(SQl.toString(), salesManVO, conditions, request, queryType);
        return pager;
    }

    /**
     * 获取以分配项目名称
     *
     * @return
     */
    public JSONArray getProductName() throws Exception {
        StringBuffer SQL = new StringBuffer();
        SQL.append(" SELECT cp.id,cp.`Name`");
        SQL.append(" FROM crm_production cp,sale_saletask4group ss ");
        SQL.append("where cp.id=ss.ProductionId AND cp.state=0 AND ss.state=0 ");
        List ls = MySQLDao.query(SQL.toString(), ProductionVO.class, null);
        Iterator it = ls.iterator();
        JSONArray array = new JSONArray();
        JSONObject obj = new JSONObject();
        while (it.hasNext()) {
            ProductionVO p = (ProductionVO) it.next();
            array.add(toJSONArray(obj, p.getId(), p.getName()));
        }
        return array;
    }

    //装换为JSON数组
    public JSONObject toJSONArray(JSONObject obj, String id, String name) {
        obj.element("id", id);
        obj.element("text", name);
        return obj;
    }

    /**
     * 获取小组名称
     *
     * @return
     * @throws Exception
     */
    public JSONArray getSaleManGroupName() throws Exception {
        StringBuffer SQL = new StringBuffer();
        SQL.append("SELECT cs.`Name`,cs.Id FROM crm_salemangroup cs where cs.state=0");
        List ls = MySQLDao.query(SQL.toString(), SalemanGroupPO.class, null);
        Iterator it = ls.iterator();
        JSONArray array = new JSONArray();
        JSONObject obj = new JSONObject();
        while (it.hasNext()) {
            SalemanGroupPO p = (SalemanGroupPO) it.next();
            array.add(toJSONArray(obj, p.getId(), p.getName()));
        }
        return array;
    }
}
