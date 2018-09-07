package com.youngbook.action.system;

/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/29/14
 * Time: 8:17 PM
 * To change this template use File | Settings | File Templates.
 */

import com.youngbook.action.BaseAction;
import com.youngbook.common.Database;
import com.youngbook.common.QueryType;
import com.youngbook.common.ReturnObject;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.vo.system.ProjectMenuVO;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/16/14
 * Time: 1:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class SaleMenuAction extends BaseAction {

    private ProjectMenuVO projectMenuVO = new ProjectMenuVO();
    private ReturnObject result;
    public String list() throws Exception {

        HttpServletRequest request = ServletActionContext.getRequest();

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        String sql ="select u.id id,u.name text\n" +
                "from system_user u\n" +
                "LEFT JOIN crm_saleman a on a.UserId=u.id and a.State=0\n" +
                "where u.state=0";
        List<ProjectMenuVO> list= MySQLDao.query(sql, ProjectMenuVO.class, null);
        JSONArray array=new JSONArray();
        for(ProjectMenuVO p:list){
            array.add(p.toJsonObject4Form());
        }
        result.setReturnValue(array);
        return SUCCESS;
    }

    public ProjectMenuVO getProjectMenuVO() {
        return projectMenuVO;
    }

    public void setProjectMenuVO(ProjectMenuVO projectMenuVO) {
        this.projectMenuVO = projectMenuVO;
    }

    public ReturnObject getResult() {
        return result;
    }

    public void setResult(ReturnObject result) {
        this.result = result;
    }
}
