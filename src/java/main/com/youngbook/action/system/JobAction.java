package com.youngbook.action.system;

import com.opensymphony.xwork2.ActionSupport;
import com.youngbook.common.*;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.JobPO;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jayden
 * Date: 14-5-9
 * Time: 下午3:38
 * To change this template use File | Settings | File Templates.
 */
public class JobAction extends ActionSupport {
    private ReturnObject result;
    private JobPO job;

    public String list() {
        result = new ReturnObject();
        try{
            QueryType queryType = new QueryType(Database.NUMBER_EQUAL, Database.QUERY_EXACTLY);
            List<JobPO> joblist = MySQLDao.query(job,JobPO.class, null,queryType);
            Tree menuRoot = TreeOperator.createForest();
            for(JobPO jobs : joblist){
                Tree tree = new Tree(jobs.getId(),jobs.getJobName(),jobs.getDepartmentId(),jobs);
                TreeOperator.add(menuRoot, tree);
            }
            JSONObject json = TreeOperator.getJson4Tree(menuRoot);

            String jsonStr = json.toString();

            result.setCode(ReturnObject.CODE_SUCCESS);
            result.setMessage("操作成功");
            result.setReturnValue(jsonStr);
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("操作失败");
            result.setCode(ReturnObject.CODE_DB_EXCEPTION);
        }

        return SUCCESS;
    }

    public ReturnObject getResult() {
        return result;
    }

    public void setResult(ReturnObject result) {
        this.result = result;
    }

    public JobPO getJob() {
        return job;
    }

    public void setJob(JobPO job) {
        this.job = job;
    }
}
