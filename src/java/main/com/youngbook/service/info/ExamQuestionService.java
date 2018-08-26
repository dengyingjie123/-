package com.youngbook.service.info;

import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.info.ExamAnswerPO;
import com.youngbook.entity.po.info.ExamOptionPO;
import com.youngbook.entity.po.info.ExamQuestionPO;
import com.youngbook.entity.vo.info.ExamQuestionVO;
import com.youngbook.service.BaseService;
import jdk.internal.dynalink.support.ClassLoaderGetterContextProvider;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

/**
 * 描述：这是培训管理->试题的Service类。
 * 时间：2015-04-14
 * 创建人：张舜清
 * 修改描述：
 * 修改时间：
 * 修改人：
 * 参考需求：<a href="http://c.hopewealth.net/pages/viewpage.action?pageId=25198880" target="_blank">链接</a>
 * @author 张舜清
 */
@Component("examQuestionService")
public class ExamQuestionService extends BaseService {

    public ExamQuestionVO loadExamQuestionVOByQuestionNO(String paperId, int questionNO, Connection conn) throws Exception {

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("loadExamQuestionVOByQuestionNO", "ExamQuestionServiceSQL", ExamQuestionService.class);
        dbSQL.addParameter4All("paperId", paperId);
        dbSQL.addParameter4All("questionNO", questionNO);
        dbSQL.initSQL();

        List<ExamQuestionVO> list = MySQLDao.search(dbSQL, ExamQuestionVO.class, conn);

        if (list == null || list.size() != 1) {
            MyException.newInstance("无法获得问题内容").throwException();
        }

        ExamQuestionVO examQuestionVO = list.get(0);


        DatabaseSQL dbSQLOption = new DatabaseSQL();
        dbSQLOption.newSQL("listExamQuestionOptionByExamQuestionId", "ExamQuestionServiceSQL", ExamQuestionService.class);
        dbSQLOption.addParameter4All("questionId", examQuestionVO.getId());
        dbSQLOption.initSQL();

        List<ExamOptionPO> listOption = MySQLDao.search(dbSQLOption, ExamOptionPO.class, conn);

        examQuestionVO.setExamOptionPOs(listOption);


        return examQuestionVO;
    }

    /**
     * 获取数据集合
     * @param examQuestionVO
     * @param conditions
     * @return
     * @throws Exception
     */
    public Pager listPaperExamQuestionVO(ExamQuestionVO examQuestionVO, int currentPage, int showRowCount, List<KVObject> conditions, Connection conn) throws Exception {

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("listExamQuestionVO", "ExamQuestionServiceSQL", ExamQuestionService.class);
        dbSQL.initSQL();

        StringBuffer sbSQL = new StringBuffer(dbSQL.getSQL());
        sbSQL.insert(0, "select DISTINCT _ft_.* from (").append(" ) _ft_ ");

        Pager pager = Pager.search(sbSQL.toString(), dbSQL.getParameters(), examQuestionVO, conditions, currentPage, showRowCount, queryType, conn);

        return pager;
    }

    /**
     * 新增和更新
     * @param examQuestion
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(ExamQuestionPO examQuestion, UserPO user, Connection conn) throws Exception{
        int count = 0;
        // 新增
        if (examQuestion.getId().equals("")) {
            examQuestion.setSid(MySQLDao.getMaxSid("Info_ExamQuestion", conn));
            examQuestion.setId(IdUtils.getUUID32());
            examQuestion.setState(Config.STATE_CURRENT);
            examQuestion.setOperatorId(user.getId());
            examQuestion.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(examQuestion, conn);
        }
        // 更新
        else {
            ExamQuestionPO temp = new ExamQuestionPO();
            temp.setSid(examQuestion.getSid());
            temp = MySQLDao.load(temp, ExamQuestionPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                examQuestion.setSid(MySQLDao.getMaxSid("Info_ExamQuestion", conn));
                examQuestion.setState(Config.STATE_CURRENT);
                examQuestion.setOperatorId(user.getId());
                examQuestion.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(examQuestion, conn);
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    /**
     * 加载数据
     * @param id
     * @return
     * @throws Exception
     */
    public ExamQuestionPO loadExamQuestionPO(String id) throws Exception{
        ExamQuestionPO po = new ExamQuestionPO();
        po.setId(id);
        po.setState(Config.STATE_CURRENT);
        po = MySQLDao.load(po, ExamQuestionPO.class);
        return po;
    }

    /**
     * 删除
     * @param examQuestion
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete(ExamQuestionPO examQuestion, UserPO user, Connection conn) throws Exception {
        int count = 0;
        examQuestion = MySQLDao.load(examQuestion, ExamQuestionPO.class);
        examQuestion.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(examQuestion, conn);
        if (count == 1) {
            examQuestion.setSid(MySQLDao.getMaxSid("Info_ExamQuestion", conn));
            examQuestion.setState(Config.STATE_DELETE);
            examQuestion.setOperateTime(TimeUtils.getNow());
            examQuestion.setOperatorId(user.getId());
            count = MySQLDao.insert(examQuestion, conn);
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }
}
