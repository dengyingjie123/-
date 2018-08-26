package com.youngbook.service.production;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.production.ProjectPO;
import com.youngbook.entity.vo.production.ProjectVO;
import com.youngbook.entity.po.production.ProductionPO;
import com.youngbook.service.BaseService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/14/14
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */
@Component("projectService")
public class ProjectService extends BaseService {


    public Pager list(ProjectVO projectVO, List<KVObject> conditions, HttpServletRequest request) throws Exception {

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

//       conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "sahngBaoShiJian desc"));               //排序

        //Pager pager = Pager.query(moneyLog,conditions,request,queryType);
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append("    project.sid,");
        sbSQL.append("    project.id,");
        sbSQL.append("    project. NAME,");
        sbSQL.append("    project.size,");
        sbSQL.append("    project.startTime,");
        sbSQL.append("    project.endTime,");
        sbSQL.append("    k1.v STATUS,");
        sbSQL.append("    k2.v type,");
        sbSQL.append("    k3.v investmentDirection,");
        sbSQL.append("    k4.v partner,");
        sbSQL.append("    project.description,");
        sbSQL.append("    k5.v industry");
        sbSQL.append(" FROM");
        sbSQL.append("    crm_project project");
        sbSQL.append(" LEFT JOIN system_kv k1 ON k1.GroupName = 'Project_Status'");
        sbSQL.append(" AND project.statusId = k1.k");
        sbSQL.append(" LEFT JOIN system_kv k2 ON k2.GroupName='Project_Type' and project.typeId = k2.K");
        sbSQL.append(" LEFT JOIN system_kv k3 ON project.InvestmentDirectionId = k3.k");
        sbSQL.append(" LEFT JOIN system_kv k4 ON project.PartnerId = k4.K");
        sbSQL.append(" LEFT JOIN system_kv k5 ON project.industryId = k5.K");
        sbSQL.append(" WHERE");
        sbSQL.append("    project.state = 0");

        Pager pager = Pager.query(sbSQL.toString(),projectVO,conditions,request,queryType);

        return pager;
    }

    /**
     *
     * @param project
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(ProjectPO project, String userId, Connection conn) throws Exception{

        MySQLDao.insertOrUpdate(project, userId, conn);

        return 1;
    }

    public int del(ProjectPO project, UserPO user, Connection conn) throws Exception {
        int sta=0;
        project = MySQLDao.load(project, ProjectPO.class);
        String id= project.getId();
        String sql="SELECT * from crm_production WHERE state=0 and ProjectId='" +Database.encodeSQL(id)+"'";
        List<ProductionPO> list= MySQLDao.query(sql,ProductionPO.class,null);
        if(list!=null&&list.size()!=0){
            sta=1;
        }
        return sta;
    }

    public int delete(ProjectPO project, UserPO user, Connection conn) throws Exception {
        int count = 0;
        project = MySQLDao.load(project, ProjectPO.class);
            project.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(project, conn);
            if (count == 1) {
                project.setSid(MySQLDao.getMaxSid("crm_project", conn));
                project.setState(Config.STATE_DELETE);
                project.setOperateTime(TimeUtils.getNow());
                project.setOperatorId(user.getId());
                count = MySQLDao.insert(project, conn);
            }else{
                throw new Exception("数据库异常");
            }
        return count;
    }
}

