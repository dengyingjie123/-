package com.youngbook.service.oa.finance;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.oa.finance.FinancemeetingapplicationwfaPO;
import com.youngbook.entity.po.oa.task.TaskPO;
import com.youngbook.entity.vo.oa.finance.FinancemeetingapplicationwfaVO;
import com.youngbook.entity.vo.oa.task.TaskVO;
import com.youngbook.service.BaseService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

/**
 * Created by admin on 2015/4/29.
 */
public class FinancemeetingService extends BaseService {
    public Pager list(FinancemeetingapplicationwfaVO financemeetingVO, List<KVObject> conditions) throws Exception {
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("SELECT of.*,kv.v AS DepartmentLeaderCommentTypeName, kv2.v AS GeneralManagerCommentTypeName,kv3.v AS HQFinanceDirectorCommentTypeName,kv4.v AS HQLeaderCommentTypeIdName,kv5.v AS HQCEOCommentTypeName FROM oa_financemeetingapplicationwfa of,system_kv kv,system_kv kv2,system_kv kv3,system_kv kv4,system_kv kv5,system_user su1,system_user su2,system_user su3,system_user su4,system_user su5 " +
                "WHERE of.state = 0 " +
                "AND kv.groupName = 'OA_WFAPassType' " +
                "AND kv.k = of.DepartmentLeaderCommentTypeId  " +
                "AND kv2.groupName = 'OA_WFAPassType' " +
                "AND kv2.k = of.GeneralManagerCommentTypeId " +
                "AND kv3.groupName = 'OA_WFAPassType' " +
                "AND kv3.k = of.HQFinanceDirectorCommentTypeId " +
                "AND kv4.groupName = 'OA_WFAPassType' " +
                "AND kv4.k = of.HQLeaderCommentTypeId " +
                "AND kv5.groupName = 'OA_WFAPassType' " +
                "AND kv5.k = of.HQCEOCommentTypeId " +

                "AND su1.id = of.DepartmentLeaderId " +
                "AND su2.id = of.GeneralManagerId " +
                "AND su3.id = of.HQFinanceDirectorId " +
                "AND su4.id = of.HQLeaderId " +
                "AND su5.id = of.HQCEOId " +
                "AND su1.state = 0 " +
                "AND su2.state = 0 " +
                "AND su3.state = 0 " +
                "AND su4.state = 0 " +
                "AND su5.state = 0");
        HttpServletRequest request = ServletActionContext.getRequest();
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.query(sbSQL.toString(),financemeetingVO,conditions,request,queryType);
        return pager;
    }

    public int insertOrUpdate(FinancemeetingapplicationwfaPO financemeetingapplicationwfa, UserPO user, Connection conn) throws Exception{
        int count = 0;
        // 新增
        if (financemeetingapplicationwfa.getId().equals("")) {
            financemeetingapplicationwfa.setSid(MySQLDao.getMaxSid("OA_Financemeetingapplicationwfa", conn));
            financemeetingapplicationwfa.setId(IdUtils.getUUID32());
            financemeetingapplicationwfa.setState(Config.STATE_CURRENT);
            financemeetingapplicationwfa.setOperatorId(user.getId());
            financemeetingapplicationwfa.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(financemeetingapplicationwfa, conn);
        }
        // 更新
        else {
            FinancemeetingapplicationwfaPO temp = new FinancemeetingapplicationwfaPO();
            temp.setSid(financemeetingapplicationwfa.getSid());
            temp = MySQLDao.load(temp, FinancemeetingapplicationwfaPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                financemeetingapplicationwfa.setSid(MySQLDao.getMaxSid("OA_Financemeetingapplicationwfa", conn));
                financemeetingapplicationwfa.setState(Config.STATE_CURRENT);
                financemeetingapplicationwfa.setOperatorId(user.getId());
                financemeetingapplicationwfa.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(financemeetingapplicationwfa, conn);
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }
    // 编写Service的delete
    public int delete(FinancemeetingapplicationwfaPO financemeetingapplicationwfa, UserPO user, Connection conn) throws Exception {
        int count = 0;

        financemeetingapplicationwfa.setState(Config.STATE_CURRENT);
        financemeetingapplicationwfa = MySQLDao.load(financemeetingapplicationwfa, FinancemeetingapplicationwfaPO.class);
        financemeetingapplicationwfa.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(financemeetingapplicationwfa, conn);
        if (count == 1) {
            financemeetingapplicationwfa.setSid(MySQLDao.getMaxSid("OA_Financemeetingapplicationwfa", conn));
            financemeetingapplicationwfa.setState(Config.STATE_DELETE);
            financemeetingapplicationwfa.setOperateTime(TimeUtils.getNow());
            financemeetingapplicationwfa.setOperatorId(user.getId());
            count = MySQLDao.insert(financemeetingapplicationwfa, conn);
        }

        if (count != 1) {
            throw new Exception("删除失败");
        }

        return count;
    }

    /**
     * 获取数据
     *
     * @param id
     * @return
     * @throws Exception
     */
    public FinancemeetingapplicationwfaPO loadFinancemeetingPO(String id) throws Exception {
        FinancemeetingapplicationwfaPO po = new FinancemeetingapplicationwfaPO();

        po.setId(id);
        po.setState(Config.STATE_CURRENT);
        po = MySQLDao.load(po, FinancemeetingapplicationwfaPO.class);

        return po;
    }

}
