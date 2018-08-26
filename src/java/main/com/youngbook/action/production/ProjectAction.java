package com.youngbook.action.production;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.production.ProjectPO;
import com.youngbook.entity.vo.production.ProjectVO;
import com.youngbook.service.production.ProjectService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpUpgradeHandler;
import java.util.ArrayList;
import java.util.List;

public class ProjectAction extends BaseAction {

    private ProjectPO project = new ProjectPO();
    private ProjectVO projectVO = new ProjectVO();


    @Autowired
    ProjectService projectService;

    //添加，修改
    public String insertOrUpdate() throws Exception {
        project = HttpUtils.getInstanceFromRequest(getRequest(), "project", ProjectPO.class);
        projectService.insertOrUpdate(project, getLoginUser().getId(), getConnection());
        return SUCCESS;
    }

    // 删除
    public String delete() throws Exception {
        projectService.delete(project, getLoginUser(), getConnection());
        return SUCCESS;
    }

    //读取
    public String load() throws Exception {

        project = HttpUtils.getInstanceFromRequest(getRequest(), "project", ProjectPO.class);

        project.setState(Config.STATE_CURRENT);
        project = MySQLDao.load(project, ProjectPO.class);
        getResult().setReturnValue(project.toJsonObject4Form());
        return SUCCESS;
    }

    //判断
    public String del() throws Exception {
        project = HttpUtils.getInstanceFromRequest(getRequest(), "project", ProjectPO.class);
        int sta = projectService.del(project, getLoginUser(), getConnection());
        JSONArray array=new JSONArray();
        JSONObject json=new JSONObject();
        json.element("sta", sta);
        array.add(json);
        getResult().setReturnValue(array);
        return SUCCESS;
    }

    //列出数据
    public String list() throws Exception {
        projectVO = HttpUtils.getInstanceFromRequest(getRequest(), "projectVO", ProjectVO.class);
        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions =new ArrayList<KVObject>();
        Pager pager = projectService.list(projectVO, conditions, request);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    public ProjectPO getProject() {
        return project;
    }
    public void setProject(ProjectPO project) {
        this.project = project;
    }

    public ProjectVO getProjectVO() {
        return projectVO;
    }
    public void setProjectVO(ProjectVO projectVO) {
        this.projectVO = projectVO;
    }

}
