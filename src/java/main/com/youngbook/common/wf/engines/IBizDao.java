package com.youngbook.common.wf.engines;
import com.youngbook.common.wf.admin.Action;
import com.youngbook.common.wf.admin.RouteList;

import java.sql.Connection;
import java.util.HashMap;

/**
 * <p>Title: </p>
 * <p>Description:
 *    BizDao是个体业务数据持久化的接口</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface IBizDao {

    /**
     * 程序；
     * 说明：插入新业务数据，在工作流第一个动作时需要使用。如：提出申请等。
     * @param conn 数据库连接，以便实现事务控制
     * @return
     */
    public int insert(Connection conn) throws Exception;
    /**
     * 程序：
     * 说明：更新业务数据，在工作流流转当中，需要对业务数据进行更新等操作。
     * @param conn
     * @return
     */
    public int update(Connection conn) throws Exception;
    /**
     * 程序：
     * 说明：删除业务流
     * @param conn
     * @return
     */
    public int delete(Connection conn) throws Exception;
    /**
     * 程序：
     * 说明：返回此业务流的唯一编号。
     *      对于业务流的唯一编号是指在业务数据表中的主键，例如：单号，票号等
     * @return 业务流的唯一编号
     */
    public String getYWID();
    /**
     * 程序：
     * 说明：设置此业务流的唯一编号
     *      对于业务流的唯一编号是指在业务数据表中的主键，例如：单号，票号等
     * @param YWID
     */
    public void setYWID(String YWID) throws Exception;
    /**
     * 程序：
     * 说明：跟据传入的字符串做为条件，查询业务数据是否满足此转向条件
     *      用户将Condition作为SQL语句的Where后的条件对业务数据表进行查询，
     *      同时，在Where语句后，还应将YWID（业务数据表的主键）为作条件一同查询，
     *      如：SELECT 主键 FROM 业务数据表 WHERE Condition and 主键=YWID。
     *      查询后，如果记录数大于0，则返回真，否则返回假
     * @param Condition 条件字串，如：abc='a' and che>100
     * @return
     */
    public boolean satisfyForwardCondition(String Condition,Connection conn) throws Exception;
    /**
     * 程序：
     * 说明：从HttPServletRequest中获取参数，并生成对象，
     *      在此方法中必须为YWID赋值，且不等于Integer.MAX_VALUE
     *      在request中包括了用户制做的表单项。
     * @param request
     * @return
     * @throws java.lang.Exception
     */
    // public void buildObject(HttpServletRequest request) throws Exception;
    /**
     * 程序：
     * 说明：从业务数据表中跟据YWID（主键）查找出记录，
     *      并将记录组合成HASHMAP返回,无数据库连接
     *      此方法主要用于记录业务流的历史数据，也就是说
     *      用户可将关注的业务数据项，组合在SQL语句中进行查询。
     *      返回后由引擎分析业务数据在流转前后的不同。
     *      并将不同的地方进行记录。
     *      注意：对于lob型的数据，无法进行记录
     * @return HashMap,Key为字段名,Value为记录值
     * @throws java.lang.Exception
     */
    public HashMap dataSnapShot(Connection conn) throws Exception;

    /**
     * 说明：根据传入的RouteList 来操作当业务结束的时候
     *       需要处理的一些业务上的操作。
     *
     * @param routeList 流转记录对象
     * @param conn 数据库连接
     * @return
     * @throws Exception
     */
    int afterOver(RouteList routeList,Action workflow, Connection conn)  throws Exception;

    /**
     * 说明：设置再afterOver中调用的service 的CLass 的全名称
     * @param className
     * @return
     */
    void setServiceClassName(String className) ;

    /**
     * 获取设置的service 的class 的全名称
     * @return
     */
    String getServiceClassName() ;
}
