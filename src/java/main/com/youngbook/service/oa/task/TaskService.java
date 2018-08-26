package com.youngbook.service.oa.task;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.oa.task.TaskPO;
import com.youngbook.entity.po.oa.task.TaskStatus;
import com.youngbook.entity.vo.oa.task.TaskVO;
import com.youngbook.service.BaseService;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

/**
 * Created by Administrator on 2015/4/14.
 * OA_任务
 */
public class TaskService extends BaseService {
    /**
     * 查询列表数据
     *
     * @param tackVO     对应列表对象
     * @param conditions 查询条件
     * @return
     */
    public Pager list(TaskVO tackVO, List<KVObject> conditions) throws Exception {
        //获取HTTP请求对象
        HttpServletRequest request = ServletActionContext.getRequest();

        StringBuffer SQL = new StringBuffer();

        //组装SQL语句
        SQL.append("SELECT ot.Sid, ot.Id, ot.State, ot.OperatorId, ot.OperateTime, ot.CatalogId,");
        SQL.append(" ot.`Name`, ot.Description,ot.process, ot.`Status`,ot.StartTime,ot.StopTime,ot.CreatorId,");
        SQL.append("ot.CreateTime,ot.ExecutorId,ot.ExecuteTime,ot.CheckerId,ot.CheckTime,");
        SQL.append("us1.`name` creatorName,us2.`name` executorName,us3.`name` checkerName,opus.`name` operatorName");
       SQL.append(",case ot.`Status` when 1 then '状态1' when 2 then '状态2' end statusName");
        SQL.append(" FROM oa_task  ot,");
        SQL.append("system_user us1,system_user us2,system_user us3,system_user opus");
        SQL.append(" where ot.OperatorId=opus.id AND opus.state=0 AND ot.state=0");
        SQL.append(" AND us1.id=ot.CreatorId AND us1.state=0");
        SQL.append(" AND us2.id=ot.ExecutorId AND us2.state=0");
        SQL.append(" ANd us3.id=ot.CheckerId AND us3.state=0");
        //设置查询类型
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        //返回分页对象

        Pager pager = Pager.query(SQL.toString(), tackVO, conditions, request, queryType);

        return pager;
    }

    /**
     * 添加跟新OA_任务
     *
     * @param task
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(TaskPO task, UserPO user, Connection conn) throws Exception {
        int count = 0;
        // 新增
        if (task.getId().equals("")) {
            task.setSid(MySQLDao.getMaxSid("OA_Task", conn));
            task.setId(IdUtils.getUUID32());
            task.setState(Config.STATE_CURRENT);
            task.setOperatorId(user.getId());
            task.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(task, conn);
        }
        // 更新
        else {
            TaskPO temp = new TaskPO();
            temp.setSid(task.getSid());
            temp = MySQLDao.load(temp, TaskPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                task.setSid(MySQLDao.getMaxSid("OA_Task", conn));
                task.setState(Config.STATE_CURRENT);
                task.setOperatorId(user.getId());
                task.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(task, conn);
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    /**
     * 获取单挑OA_任务数据
     *
     * @param id
     * @return
     * @throws Exception
     */
    public TaskPO loadTaskPO(String id) throws Exception {
        TaskPO po = new TaskPO();

        po.setId(id);
        po.setState(Config.STATE_CURRENT);
        po = MySQLDao.load(po, TaskPO.class);

        return po;
    }

    /**
     * 删除数据
     *
     * @param task
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete(TaskPO task, UserPO user, Connection conn) throws Exception {
        int count = 0;

        task.setState(Config.STATE_CURRENT);
        task = MySQLDao.load(task, TaskPO.class);
        task.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(task, conn);
        if (count == 1) {
            task.setSid(MySQLDao.getMaxSid("OA_Task", conn));
            task.setState(Config.STATE_DELETE);
            task.setOperateTime(TimeUtils.getNow());
            task.setOperatorId(user.getId());
            count = MySQLDao.insert(task, conn);
        }

        if (count != 1) {
            throw new Exception("删除失败");
        }

        return count;
    }
    /***
     * 获取是树型下拉列表
     * @return
     */
    public JSONArray getStatusTree(){
        TaskStatus TStatus = new TaskStatus();
        JSONArray array = TStatus.toJsonArray();
        return array;
    }
}
