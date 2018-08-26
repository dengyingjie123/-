package com.youngbook.action.info;

import com.youngbook.action.BaseAction;
import com.youngbook.annotation.Permission;
import com.youngbook.common.KVObject;
import com.youngbook.common.MyException;
import com.youngbook.common.Pager;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.info.ExamAnswerSessionPO;
import com.youngbook.entity.po.info.ExamAnswerSessionStatus;
import com.youngbook.entity.po.info.ExamQuestionPO;
import com.youngbook.entity.vo.info.ExamQuestionVO;
import com.youngbook.service.info.ExamAnswerService;
import com.youngbook.service.info.ExamQuestionService;
import org.apache.poi.hslf.record.PPDrawing;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

/**
 * 描述：这是培训管理->试题的Action类。
 * 时间：2015-04-14
 * 创建人：张舜清
 * 修改描述：
 * 修改时间：
 * 修改人：
 * 参考需求：<a href="http://c.hopewealth.net/pages/viewpage.action?pageId=25198880" target="_blank">链接</a>
 * @author 张舜清
 */
public class ExamQuestionAction extends BaseAction {

    ExamQuestionPO examQuestion = new ExamQuestionPO();
    ExamQuestionVO examQuestionVO = new ExamQuestionVO();

    @Autowired
    private ExamQuestionService examQuestionService;

    @Autowired
    private ExamAnswerService examAnswerService;

    public String loadCurrentExamAnswerSessionPO() throws Exception {
        String paperId = getHttpRequestParameter("paperId");
        String examAnswerSessionStatus = getHttpRequestParameter("examAnswerSessionStatus");

        if (!StringUtils.isNumeric(examAnswerSessionStatus)) {
            MyException.newInstance("答案组状态为空").throwException();
        }

        String customerId = Config.getLoginCustomerInSession(getRequest()).getId();
        ExamAnswerSessionPO examAnswerSessionPO = examAnswerService.loadCurrentExamAnswerSessionPO(paperId, Integer.parseInt(examAnswerSessionStatus), customerId, getConnection());

        if (examAnswerSessionPO == null) {
            examAnswerSessionPO = new ExamAnswerSessionPO();
            examAnswerSessionPO.setStatus(ExamAnswerSessionStatus.Processing);
        }

        getResult().setReturnValue(examAnswerSessionPO.toJsonObject());

        return SUCCESS;
    }

    public String finishExamQuestion() throws Exception {

        // String examAnswerSessionId, String paperId, String customerId, Connection conn

        String examAnswerSessionId = getHttpRequestParameter("examAnswerSessionId");
        String paperId = getHttpRequestParameter("paperId");
        String customerId = Config.getLoginCustomerInSession(getRequest()).getId();

        if (StringUtils.isEmpty(examAnswerSessionId)) {
            ExamAnswerSessionPO examAnswerSessionPO = examAnswerService.loadCurrentExamAnswerSessionPO(paperId, ExamAnswerSessionStatus.Processing, customerId, getConnection());

            if (examAnswerSessionPO != null) {
                examAnswerSessionId = examAnswerSessionPO.getId();
            }
        }

        examAnswerService.finish(examAnswerSessionId, paperId, customerId, getConnection());

        getResult().setReturnValue("1");

        return SUCCESS;
    }

    public String loadExamQuestionVOByQuestionNO() throws Exception {

        String paperId = getHttpRequestParameter("paperId");
        String questionNOString = getHttpRequestParameter("questionNO");
        Connection conn = getConnection();

        if (!StringUtils.isNumeric(questionNOString)) {
            MyException.newInstance("无法获得题号").throwException();
        }

        ExamQuestionVO examQuestionVO = examQuestionService.loadExamQuestionVOByQuestionNO(paperId, Integer.parseInt(questionNOString), conn);

        getResult().setReturnValue(examQuestionVO.toJsonObject());

        return SUCCESS;
    }

    /**
     * 获取数据列表的方法
     * @return
     * @throws Exception
     */
    public String listPaperExamQuestionVO() throws Exception {

        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, ExamQuestionVO.class);

        Pager pager = Pager.getInstance(getRequest());

        pager = examQuestionService.listPaperExamQuestionVO(examQuestionVO, pager.getCurrentPage(), pager.getShowRowCount(), conditions, getConnection());

        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }

    /**
     * 加载数据的方法
     * @return
     * @throws Exception
     */
    @Permission(require = "培训管理-试题-修改")
    public String load() throws Exception {
        examQuestion = examQuestionService.loadExamQuestionPO(examQuestion.getId());
        getResult().setReturnValue(examQuestion.toJsonObject4Form());
        return SUCCESS;
    }

    /**
     * 增加和更新的方法
     * @return
     * @throws Exception
     */
    @Permission(require = "培训管理-试题-新增")
    public String insertOrUpdate() throws Exception {
        int count = examQuestionService.insertOrUpdate(examQuestion, getLoginUser(), getConnection());
        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }

    /**
     * 删除的方法
     * @return
     * @throws Exception
     */
    @Permission(require = "培训管理-试题-删除")
    public String delete() throws Exception {
        examQuestionService.delete(examQuestion, getLoginUser(), getConnection());
        return SUCCESS;
    }

    // 对应的get和set方法
    public ExamQuestionPO getExamQuestion() {
        return examQuestion;
    }
    public void setExamQuestion(ExamQuestionPO examQuestion) {
        this.examQuestion = examQuestion;
    }

    public ExamQuestionVO getExamQuestionVO() {
        return examQuestionVO;
    }
    public void setExamQuestionVO(ExamQuestionVO examQuestionVO) {
        this.examQuestionVO = examQuestionVO;
    }

}
