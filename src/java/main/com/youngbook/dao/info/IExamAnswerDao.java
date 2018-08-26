package com.youngbook.dao.info;

import com.youngbook.entity.po.info.ExamAnswerSessionPO;

import java.sql.Connection;

/**
 * Created by Lee on 1/16/2017.
 */
public interface IExamAnswerDao {
    public ExamAnswerSessionPO calculateScore(ExamAnswerSessionPO examAnswerSession, Connection conn) throws Exception;
}
