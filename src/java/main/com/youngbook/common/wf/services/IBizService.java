package com.youngbook.common.wf.services;

import com.youngbook.common.wf.admin.Action;
import com.youngbook.common.wf.admin.RouteList;
import com.youngbook.common.wf.engines.IBizDao;
import com.youngbook.entity.po.wf.BizRoutePO;

import java.sql.Connection;

/**
 * Created by zhouhaihong on 2015/9/9.
 */


public interface IBizService {

    void afterOver(BizRoutePO bizdao,RouteList routeList,Action worlkflow, Connection conn) throws Exception;
}
