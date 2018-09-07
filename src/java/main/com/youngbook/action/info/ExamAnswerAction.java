package com.youngbook.action.info;

import com.youngbook.action.BaseAction;
import com.youngbook.annotation.Permission;
import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.entity.po.info.ExamAnswerPO;
import com.youngbook.entity.po.info.ExamAnswerSessionPO;
import com.youngbook.entity.po.info.ExamAnswerSessionStatus;
import com.youngbook.entity.vo.info.ExamAnswerVO;
import com.youngbook.service.info.ExamAnswerService;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：这是培训管理->试题答案对应的Action类。
 * 时间：2015-04-14
 * 创建人：张舜清
 * 参考需求：<a href="http://c.hopewealth.net/pages/viewpage.action?pageId=25198880" target="_blank">链接</a>
 * @author 张舜清
 */
public class ExamAnswerAction extends BaseAction {

    // 实例化一个PO对象
    private ExamAnswerPO examAnswer = new ExamAnswerPO();
    // 实例化一个VO对象
    private ExamAnswerVO examAnswerVO = new ExamAnswerVO();
    // 实例化一个Service对象

    @Autowired
    private ExamAnswerService examAnswerService;

    public String answer() throws Exception {
        String examAnswerSessionId = getHttpRequestParameter("examAnswerSessionId");
        String paperId = getHttpRequestParameter("paperId");
        String questionId = getHttpRequestParameter("questionId");
        String optionId = getHttpRequestParameter("optionId");
        String customerId = Config.getLoginCustomerInSession(getRequest()).getId();
        Connection conn = getConnection();



        if (StringUtils.isEmpty(examAnswerSessionId)) {


            ExamAnswerSessionPO examAnswerSessionPO = examAnswerService.loadCurrentExamAnswerSessionPO(paperId, ExamAnswerSessionStatus.Processing, customerId, conn);

            if (examAnswerSessionPO == null) {
                examAnswerSessionPO = examAnswerService.getNewExamAnswerSession(paperId, customerId, conn);
            }

            examAnswerSessionId = examAnswerSessionPO.getId();
        }

        examAnswerService.answer(examAnswerSessionId, questionId, optionId, customerId, conn);

        getResult().setReturnValue("1");

        return SUCCESS;
    }

    /**
     * 新增和修改的方法
     * @return
     * @throws Exception
     */
    public String insertOrUpdate() throws Exception {
        // 调用对应Service的insertOrUpdate
        int count = examAnswerService.insertOrUpdate(examAnswer, getConnection());
        // 判断是否不等于1
        if (count != 1) {
            // if条件成立就把结果信息返回给页面
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }

    /**
     * 删除的方法
     * @return
     * @throws Exception
     */
    @Permission(require = "培训管理-试题答案-删除")
    public String delete() throws Exception {
        // 调用对应Service的delete
        examAnswerService.delete(examAnswer, getLoginUser(), getConnection());
        return SUCCESS;
    }

    /**
     * 获取数据的方法
     * @return
     * @throws Exception
     */
    public String list() throws Exception {
        // 组建查询SQL语句
        StringBuffer SQL = new StringBuffer();
        SQL.append("SELECT ea.*,cp.`Name` customerName,eq.Question,eo.Description,eo.`Value` examOptionValue,eo.`Name` optionName,eo.Score examOptionScore");
        SQL.append(" FROM info_examanswer ea,info_examoption eo,info_examquestion eq,crm_customerpersonal cp ");
        SQL.append(" WHERE 1 = 1 AND ea.State=0 AND eo.State=0 AND eq.State=0 AND cp.state=0 AND ea.CustomerId=cp.id AND ea.QuestionId=eq.Id AND ea.OptionId=eo.Id");
        // 通过ServletActionContext获取getRequest()请求放到HttpServletRequest的request里面
        HttpServletRequest request = ServletActionContext.getRequest();
        // 实例一个集合
        List<KVObject> conditions =new ArrayList<KVObject>();
        // 设置查询的类型
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        // 通过SQL语句查询数据后封装到VO对象后返回一个分页对象
        Pager pager = Pager.query(SQL.toString(),examAnswerVO,conditions,request,queryType);
        // 获取结果设置到Json对象
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 加载数据的方法
     * @return
     * @throws Exception
     */
    @Permission(require = "培训管理-试题答案-修改")
    public String load() throws Exception {
        // 调用对应Service的load
        examAnswer = examAnswerService.loadExamAnswerPO(examAnswer.getId());
        // 获取结果设置到Json对象
        getResult().setReturnValue(examAnswer.toJsonObject4Form());
        return SUCCESS;
    }

    // 对应的get和set方法
    public ExamAnswerPO getExamAnswer() {
        return examAnswer;
    }
    public void setExamAnswer(ExamAnswerPO examAnswer) {
        this.examAnswer = examAnswer;
    }

    public ExamAnswerVO getExamAnswerVO() {
        return examAnswerVO;
    }
    public void setExamAnswerVO(ExamAnswerVO examAnswerVO) {
        this.examAnswerVO = examAnswerVO;
    }

}
