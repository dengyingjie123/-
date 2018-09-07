package com.youngbook.service.info;

import com.youngbook.common.MyException;
import com.youngbook.common.config.Config;
import com.youngbook.common.reflaction.MyClass;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.info.IExamAnswerDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.info.ExamAnswerPO;
import com.youngbook.entity.po.info.ExamAnswerSessionPO;
import com.youngbook.entity.po.info.ExamAnswerSessionStatus;
import com.youngbook.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

/**
 * 描述：这是培训管理->试题答案对应的Service类。
 * 时间：2015-04-14
 * 创建人：张舜清
 * 参考需求：<a href="http://c.hopewealth.net/pages/viewpage.action?pageId=25198880" target="_blank">链接</a>
 * @author 张舜清
 */
@Component("examAnswerService")
public class ExamAnswerService extends BaseService {

    @Autowired
    IExamAnswerDao examAnswerDao;

    public ExamAnswerSessionPO loadCurrentExamAnswerSessionPO(String paperId, int examAnswerSessionStatus, String customerId, Connection conn) throws Exception {
        ExamAnswerSessionPO examAnswerSessionPO = new ExamAnswerSessionPO();
        examAnswerSessionPO.setState(Config.STATE_CURRENT);
        examAnswerSessionPO.setPaperId(paperId);
        examAnswerSessionPO.setCustomerId(customerId);
        examAnswerSessionPO.setStatus(examAnswerSessionStatus);

        List<ExamAnswerSessionPO> list = MySQLDao.search(examAnswerSessionPO, ExamAnswerSessionPO.class, conn);

        if (list != null && list.size() == 1) {
            return list.get(0);
        }

        return null;

    }

    public ExamAnswerSessionPO getNewExamAnswerSession(String paperId, String customerId, Connection conn) throws Exception {

        ExamAnswerSessionPO examAnswerSessionPO = new ExamAnswerSessionPO();
        examAnswerSessionPO.setPaperId(paperId);
        examAnswerSessionPO.setCustomerId(customerId);
        examAnswerSessionPO.setStartTime(TimeUtils.getNow());
        examAnswerSessionPO.setStatus(0);

        MySQLDao.insertOrUpdate(examAnswerSessionPO, conn);

        return examAnswerSessionPO;
    }

    public void finish(String examAnswerSessionId, String paperId, String customerId, Connection conn) throws Exception {
        ExamAnswerSessionPO examAnswerSessionPO = new ExamAnswerSessionPO();
        examAnswerSessionPO.setId(examAnswerSessionId);
        examAnswerSessionPO.setState(Config.STATE_CURRENT);


        examAnswerSessionPO = MySQLDao.load(examAnswerSessionPO, ExamAnswerSessionPO.class, conn);

        if (examAnswerSessionPO == null) {
            MyException.newInstance("答案组编号异常").throwException();
        }

        if (!examAnswerSessionPO.getPaperId().equals(paperId)) {
            MyException.newInstance("答案组与试卷不匹配").throwException();
        }

        if (!examAnswerSessionPO.getCustomerId().equals(customerId)) {
            MyException.newInstance("答案组与用户不匹配").throwException();
        }

        if (examAnswerSessionPO.getStatus() != ExamAnswerSessionStatus.Processing) {
            MyException.newInstance("答案组不是进行中状态").throwException();
        }

        examAnswerSessionPO.setStopTime(TimeUtils.getNow());
        examAnswerSessionPO.setStatus(ExamAnswerSessionStatus.Finished);


        // 计算分数
        examAnswerSessionPO = examAnswerDao.calculateScore(examAnswerSessionPO, conn);


        MySQLDao.insertOrUpdate(examAnswerSessionPO, conn);
    }

   public void answer(String examAnswerSessionId, String questionId, String optionId, String customerId, Connection conn) throws Exception {

        if (StringUtils.isEmptyAny(examAnswerSessionId, questionId, optionId)) {
            MyException.newInstance("回答问题失败，输入参数有误").throwException();
        }

        ExamAnswerPO examAnswerPO = new ExamAnswerPO();
        examAnswerPO.setState(Config.STATE_CURRENT);
        examAnswerPO.setQuestionId(questionId);
        examAnswerPO.setCustomerId(customerId);
        examAnswerPO.setAnswerSessionId(examAnswerSessionId);

        ExamAnswerPO temp = MySQLDao.load(examAnswerPO, ExamAnswerPO.class, conn);

        if (temp != null) {
            examAnswerPO = temp;
        }
        examAnswerPO.setOptionId(optionId);

        MySQLDao.insertOrUpdate(examAnswerPO, conn);

    }


    /**
     * 新增和修改
     * @param examAnswer
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(ExamAnswerPO examAnswer, Connection conn) throws Exception{

        return MySQLDao.insertOrUpdate(examAnswer, conn);
    }

    /**
     * 删除
     * @param examAnswer
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete(ExamAnswerPO examAnswer, UserPO user, Connection conn) throws Exception {
        int count = 0;
        examAnswer.setState(Config.STATE_CURRENT);
        examAnswer = MySQLDao.load(examAnswer, ExamAnswerPO.class);
        examAnswer.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(examAnswer, conn);
        if (count == 1) {
            examAnswer.setSid(MySQLDao.getMaxSid("Info_ExamAnswer", conn));
            examAnswer.setState(Config.STATE_DELETE);
            examAnswer.setOperateTime(TimeUtils.getNow());
            examAnswer.setOperatorId(user.getId());
            count = MySQLDao.insert(examAnswer, conn);
        }
        if (count != 1) {
            throw new Exception("删除失败");
        }
        return count;
    }

    /**
     * 加载数据
     * @param id
     * @return
     * @throws Exception
     */
    public ExamAnswerPO loadExamAnswerPO(String id) throws Exception{
        ExamAnswerPO po = new ExamAnswerPO();
        po.setId(id);
        po.setState(Config.STATE_CURRENT);
        po = MySQLDao.load(po, ExamAnswerPO.class);
        return po;
    }
}
