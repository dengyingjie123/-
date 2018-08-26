package com.youngbook.service.sale;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.sale.InvestmentParticipantPO;
import com.youngbook.entity.vo.Sale.InvestmentParticipantVO;
import com.youngbook.service.BaseService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2015/4/7.
 * 投资参与值
 */
public class InvestmentParticipantService extends BaseService {

    public Pager List(InvestmentParticipantVO investmentParticipantVO, List<KVObject> conditions) throws Exception {
        //获取请求
        HttpServletRequest request = ServletActionContext.getRequest();

        //组装SQL语句
        StringBuffer SQL = new StringBuffer();
        SQL.append("SELECT DISTINCT SI.Sid,SI.Id,SI.State,CC.`Name` CustomerName,");
        SQL.append("SI.JoinStatus,SI.JoinType,SI.JoinMoney,SI.JoinTime,");
        SQL.append("SI.AppointmentTime,SI.QuitTime,kv_status.V kv_statusName,kv_type.V kv_typeName,");
        SQL.append("us. NAME operatorName,sip.`Name` investmentplanName");
        SQL.append(" FROM sale_investmentparticipant AS SI , crm_customerpersonal AS CC ,");
        SQL.append("system_kv AS kv_status ,system_kv AS kv_type ,system_user AS us ,");
        SQL.append("sale_investmentplan AS sip WHERE CC.id = SI.CustomerId");
        SQL.append(" AND kv_status.k = SI.JoinStatus");
        SQL.append(" AND kv_type.k = SI.JoinType");
        SQL.append(" AND sip.id = SI.InvestmentId");
        SQL.append(" AND us.Id=SI.OperatorId");
        SQL.append(" AND SI.State=0 AND us.state=0 AND CC.state = 0");
        SQL.append(" AND kv_status.GroupName = 'Sale_InvestmentParticipantJoinStatus'");
        SQL.append(" AND kv_type.GroupName = 'Sale_InvestmentParticipantJoinType' ");
        //设置查询类型
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        Pager pager = Pager.query(SQL.toString(), investmentParticipantVO, conditions, request, queryType);
        return pager;
    }

    /**
     * 根据id编号获取数据
     * @param id
     * @return
     * @throws Exception
     */
    public InvestmentParticipantPO loadInvestmentParticipantPO(String id) throws Exception{
        InvestmentParticipantPO po = new InvestmentParticipantPO();
        po.setId(id);
        //设置查询的语句的状态
        po.setState(Config.STATE_CURRENT);
        //获取数据
        po = MySQLDao.load(po, InvestmentParticipantPO.class);
        return po;
    }
}
