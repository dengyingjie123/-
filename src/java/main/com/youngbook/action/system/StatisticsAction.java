package com.youngbook.action.system;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.system.StatisticsPO;
import com.youngbook.entity.vo.oa.finance.MoneyLogVO;
import com.youngbook.entity.vo.system.StatisticsVO;
import com.youngbook.service.system.StatisticsService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/4/3.
 * 系统统计
 */
public class StatisticsAction extends BaseAction {
    //系统统计VO类
    private StatisticsVO statisticsVO = new StatisticsVO();
    private StatisticsPO statistics = new StatisticsPO();
    //逻辑处理类对象
    private StatisticsService service = new StatisticsService();


    /**
     * 获取数据集合
     *
     * @return
     * @throws Exception
     */
    public String list() throws Exception {


        //获取请求对象。
        HttpServletRequest request = ServletActionContext.getRequest();


        //调用逻辑类查询数据
        Pager pager = service.list(statisticsVO);
        //将数据封装成JSON返回页面操作。
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 获取单独一条数据
     *
     * @return
     * @throws Exception
     */
    public String load() throws Exception {

        //根据逻辑类获取对象
        statistics = service.loadStatisticsPO(statistics.getId());
        //将对象转换为JSON对象返回页面
        getResult().setReturnValue(statistics.toJsonObject4Form());

        return SUCCESS;
    }

    /**
     * 删除修改数据，更新语句状态
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {
        //调用逻辑类修改数据
        service.delete(statistics, getLoginUser(), getConnection());

        return SUCCESS;
    }

    /**
     * 添加更新数据
     * @return
     * @throws Exception
     */
    public String insertOrUpdate() throws Exception {
        /*
        调用逻辑类添加修改数据
           返回影响行数
         */
        int count = service.insertOrUpdate(statistics, getConnection());

        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }

    /**
     * Web首页显示实时统计数据
     * @return
     */
    public String statisticsForWeb() throws Exception {
        getResult().setReturnValue( service.listForWeb(getConnection()));

        return SUCCESS;
    }

    public StatisticsVO getStatisticsVO() {
        return statisticsVO;
    }

    public void setStatisticsVO(StatisticsVO statisticsVO) {
        this.statisticsVO = statisticsVO;
    }

    public StatisticsPO getStatistics() {
        return statistics;
    }

    public void setStatistics(StatisticsPO statistics) {
        this.statistics = statistics;
    }

    public StatisticsService getService() {
        return service;
    }

    public void setService(StatisticsService service) {
        this.service = service;
    }
}
