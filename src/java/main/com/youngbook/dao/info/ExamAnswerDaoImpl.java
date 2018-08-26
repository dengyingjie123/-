package com.youngbook.dao.info;

import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.info.ExamAnswerSessionPO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 1/16/2017.
 */
@Component("examAnswerDao")
public class ExamAnswerDaoImpl implements IExamAnswerDao {
    public ExamAnswerSessionPO calculateScore(ExamAnswerSessionPO examAnswerSession, Connection conn) throws Exception {

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("calculateScore", "ExamAnswerDaoImplSQL", ExamAnswerDaoImpl.class);
        dbSQL.addParameter4All("examAnswerSessionId", examAnswerSession.getId());
        dbSQL.initSQL();

        List<ExamAnswerSessionPO> list = MySQLDao.search(dbSQL, ExamAnswerSessionPO.class, conn);

        if (list != null && list.size() == 1) {
            String score = list.get(0).getScore();

            examAnswerSession.setScore(score);

        }

        return examAnswerSession;
    }
}
