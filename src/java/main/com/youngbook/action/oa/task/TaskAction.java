package com.youngbook.action.oa.task;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.oa.task.TaskPO;
import com.youngbook.entity.vo.oa.task.TaskVO;
import com.youngbook.service.oa.task.TaskService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class TaskAction extends BaseAction {

    private TaskVO taskVO = new TaskVO();
    private TaskPO task = new TaskPO();

    private TaskService service = new TaskService();

    /**
     * 获取OA_任务的列表
     * @return
     * @throws Exception
     */
    public String list() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        //根据时间获取时间查询对象
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request,TaskVO.class);
        //获取列表
        Pager pager = service.list(taskVO,conditions);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 添加并跟新OA_任务
     * @return
     * @throws Exception
     */
    public String insertOrUpdate() throws Exception {
        int count = service.insertOrUpdate(task, getLoginUser(), getConnection());
        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }

    /**
     * 获取单条OA_任务数据
     * @return
     * @throws Exception
     */
    public String load() throws Exception {
        task = service.loadTaskPO(task.getId());
        getResult().setReturnValue(task.toJsonObject4Form());
        return SUCCESS;
    }

    /**
     * 获取状态JSON数组
     * @return
     */
    public String StatusTree() throws Exception{
        getResult().setReturnValue(service.getStatusTree());
        return SUCCESS;
    }

    /**
     * 删除OA_任务
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {
        service.delete(task, getLoginUser(), getConnection());
        return SUCCESS;
    }

    public TaskVO getTaskVO() {
        return taskVO;
    }
    public void setTaskVO(TaskVO taskVO) {
        this.taskVO = taskVO;
    }

    public TaskPO getTask() {
        return task;
    }
    public void setTask(TaskPO task) {
        this.task = task;
    }

    public TaskService getService() {
        return service;
    }
    public void setService(TaskService service) {
        this.service = service;
    }

}
