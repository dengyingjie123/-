package com.youngbook.action.info;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.entity.po.info.ExamOptionPO;
import com.youngbook.entity.vo.info.ExamOptionVO;
import com.youngbook.service.info.ExamOptionService;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：这是培训管理->试题->试题选项的Action类。
 * 时间：2015-04-14
 * 创建人：张舜清
 * 修改描述：
 * 修改时间：
 * 修改人：
 * 参考需求：<a href="http://c.hopewealth.net/pages/viewpage.action?pageId=25198880" target="_blank">链接</a>
 * @author 张舜清
 */
public class ExamOptionAction extends BaseAction {

    private ExamOptionPO examOption = new ExamOptionPO();
    private ExamOptionVO examOptionVO = new ExamOptionVO();

    @Autowired
    private ExamOptionService examOptionService;

    /**
     * 新增和更新的方法
     * @return
     * @throws Exception
     */
    public String insertOrUpdate() throws Exception {
        int count = examOptionService.insertOrUpdate(examOption, getLoginUser(), getConnection());
        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }

    /**
     * 删除方法的方法
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {
        examOptionService.delete(examOption, getLoginUser(), getConnection());
        return SUCCESS;
    }

    /**
     * 获取数据的方法
     * @return
     * @throws Exception
     */
    public String list() throws Exception {
        StringBuffer SQL = new StringBuffer();
        SQL.append("SELECT eo.*, u.`name` OperatorName,eq.question ");
        SQL.append(" FROM info_examoption eo,system_user u,info_examquestion eq ");
        SQL.append(" WHERE 1 = 1 AND eo.State = 0 AND u.state = 0 AND eq.State = 0 AND eo.OperatorId = u.Id AND eq.OperatorId = u.Id AND eo.QuestionId = eq.Id");
        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions =new ArrayList<KVObject>();
        if(!examOption.getQuestionId().equals("")){
            conditions.add(new KVObject("questionId", " in ('"+Database.encodeSQL(examOption.getQuestionId())+"')"));
        }
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.query(SQL.toString(), examOptionVO, conditions, request, queryType);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 加载数据的方法
     * @return
     * @throws Exception
     */
    public String load() throws Exception {
        examOption = examOptionService.loadExamOptionPO(examOption.getId());
        getResult().setReturnValue(examOption.toJsonObject4Form());
        return SUCCESS;
    }

    // 对应的get和set方法
    public ExamOptionPO getExamOption() {
        return examOption;
    }
    public void setExamOption(ExamOptionPO examOption) {
        this.examOption = examOption;
    }

    public ExamOptionVO getExamOptionVO() {
        return examOptionVO;
    }
    public void setExamOptionVO(ExamOptionVO examOptionVO) {
        this.examOptionVO = examOptionVO;
    }

}
