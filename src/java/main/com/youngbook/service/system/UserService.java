package com.youngbook.service.system;

import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.SessionConfig;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.NumberUtils;
import com.youngbook.common.utils.PasswordUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.sale.ISalemanGroupDao;
import com.youngbook.dao.sale.ISalesmanDao;
import com.youngbook.dao.system.IFilesDao;
import com.youngbook.dao.system.IPositionUserDao;
import com.youngbook.dao.system.IUserDao;
import com.youngbook.entity.po.DepartmentPO;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.sale.SalesmanPO;
import com.youngbook.entity.po.system.FilesPO;
import com.youngbook.entity.po.system.SmsPO;
import com.youngbook.entity.po.system.UserPositionType;
import com.youngbook.entity.po.system.UserPositionInfoPO;
import com.youngbook.entity.vo.system.UserVO;
import com.youngbook.service.BaseService;
import com.youngbook.service.customer.CustomerDistributionService;
import com.youngbook.service.customer.CustomerPersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-29
 * Time: 上午10:14
 * To change this template use File | Settings | File Templates.
 */
@Component("userService")
public class UserService extends BaseService {

    @Autowired
    IUserDao userDao;

    @Autowired
    ISalesmanDao salesmanDao;

    @Autowired
    IPositionUserDao positionUserDao;

    @Autowired
    ISalemanGroupDao salemanGroupDao;

    @Autowired
    CustomerDistributionService customerDistributionService;

    @Autowired
    CustomerPersonalService customerPersonalService;

    @Autowired
    IFilesDao filesDao;

    //短信服务
    @Autowired
    SmsService smsService;

    public UserPO disableUser(String userId, Connection conn) throws Exception {

        UserPO user = userDao.loadUserByUserId(userId, conn);

        // 1. 处理分配的客户


        return user;
    }

    public String getAvatarUrl(String userId, Connection conn) throws Exception {

        String moduleId = "9D451710";
        String url = "/modern2/include/img/customer_man.png";

        FilesPO filesPO = filesDao.loadByModuleBizId(moduleId, userId, conn);
        if (filesPO != null) {
            return "/system/file/FileDownload.action?moduleId="+moduleId+"&bizId="+userId+"&r="+ NumberUtils.randomNumbers(5);
        }

        return url;
    }


    public Pager listPagerUserPOs(String positionId, int currentPage, int showRowCount, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("listPagerUserPOs", this);
        dbSQL.addParameter4All("positionId", positionId);
        dbSQL.initSQL();

        Pager pager = MySQLDao.search(dbSQL.getSQL(), dbSQL.getParameters(), UserPO.class, currentPage, showRowCount, conn);

        return pager;
    }

    public UserPO register(String name, String mobile, String password, String referralCode, String userType, Connection conn) throws Exception {

        if (StringUtils.isEmptyAny(name, mobile, password, userType)) {
            MyException.newInstance("用户参数不完整", "name="+name+"&mobile="+mobile+"&password="+password+"&userType="+userType).throwException();
        }

        UserPO user = new UserPO();
        user.setName(name);
        user.setMobile(mobile);
        user.setPassword(password);
        user.setPositionTypeId(UserPositionType.SaleMan);

        String staffCode = newStaffCode(conn);
        user.setStaffCode(staffCode);

        user.setReferralCode(Config.getReferralCode(staffCode));

        user.setUserType(userType);

        UserPO checkUserPO = userDao.loadUserByMobile(mobile, conn);
        if (checkUserPO != null) {
            MyException.newInstance("手机号【"+mobile+"】已被占用，请检查", "mobile="+mobile).throwException();
        }

        int count = MySQLDao.insertOrUpdate(user, conn);

        // 若是销售岗位，则在销售成员表中增加销售记录
        SalesmanPO salesmanPO = null;
        if (user.getPositionTypeId().equals(UserPositionType.SaleMan)) {
            salesmanPO = salesmanDao.insertSalesman(user, conn);
        }

        if (salesmanPO == null) {
            MyException.newInstance("创建用户失败，请与管理员联系", "创建salesmanPO失败").throwException();
        }


        /**
         * 设置理财圈默认权限
         */
        positionUserDao.setDefaultFinanceCircle(user.getId(), conn);


        /**
         * 设置默认理财圈销售组
         */
        salemanGroupDao.setDefaultFinanceCircle(user.getId(), conn);

        if (count == 1) {
            return user;
        }

        return user;
    }

    public void loginFinish(UserPO user, String permissionString, HttpSession session) throws Exception {
        session.setAttribute(Config.SESSION_PERMISSION_STRING, permissionString);
        session.setAttribute(Config.SESSION_LOGINUSER_TYPE, SessionConfig.LOGIN_USER_TYPE_USER);
        //若登录成功，在session中放置登录状态“login”，使用1表示成功，使用0表示失败
        session.setAttribute(Config.SESSION_ACTION_LOGINTARGET_STRING, "1");

        //登录成功时将UserPO放入session中，以便于其他方法从session中获取当前登录的用户信息
        session.setAttribute(Config.SESSION_ACTION_LOGINUSER_STRING, user);

    }


    public UserPO login(String mobile, String password, Connection conn) throws Exception {

        if (StringUtils.isEmpty(mobile)) {
            MyException.newInstance("无法获得用户手机号").throwException();
        }

        if (StringUtils.isEmpty(password)) {
            MyException.newInstance("无法获得用户密码").throwException();
        }

        UserPO userPO = userDao.login(mobile, password, conn);

        return userPO;
    }

    public UserPO login(String userId, Connection conn) throws Exception {

        if (StringUtils.isEmpty(userId)) {
            MyException.newInstance("无法获得用户编号").throwException();
        }

        UserPO userPO = loadUserByUserId(userId, conn);

        if (userPO == null) {
            MyException.newInstance("无法获得对应用户").throwException();
        }

        return userDao.login(userPO.getMobile(), userPO.getPassword(), conn);
    }


    public UserPO loadUserByUserId(String userId, Connection conn) throws Exception {
        return userDao.loadUserByUserId(userId, conn);
    }

    public UserPO loadUserByMobile(String mobile, Connection conn) throws Exception {
        return userDao.loadUserByMobile(mobile, conn);
    }

    public UserPO loadUserByUserId(String userId) throws Exception {

        Connection conn = Config.getConnection();
        try {
            return userDao.loadUserByUserId(userId, conn);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }

    }


    public UserPositionInfoPO getUserPositionInfoByUserId(String userId, Connection conn) throws Exception {

        UserPO user = this.loadUserByUserId(userId, conn);

        DepartmentPO department = Config.getDefaultDepartmentByUserId(userId, conn);

        UserPositionInfoPO userPositionInfo = new UserPositionInfoPO();

        userPositionInfo.setUserName(user.getName());
        userPositionInfo.setUserId(userId);
        userPositionInfo.setDepartmentId(department.getId());
        userPositionInfo.setDepartmentName(department.getName());

        return userPositionInfo;
    }

    public List<UserPO> searchReferralCode(String code, Connection conn) throws Exception {

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("select * from system_user u where 1=1 and u.state=0 and (u.staffCode like ? or u.`name` like ?)");
        dbSQL.addParameter(1, "%" + code + "%").addParameter(2, "%" + code + "%");

        List<UserPO> list = MySQLDao.search(dbSQL.getSQL(), dbSQL.getParameters(), UserPO.class, null, conn);

        return list;
    }

    public String getReferralCode(String userId, Connection conn) throws Exception {
        UserPO user = new UserPO();
        user.setId(userId);
        user.setState(Config.STATE_CURRENT);
        user = MySQLDao.load(user, UserPO.class, conn);

        if (user != null) {
            return Config.getReferralCode(user.getStaffCode());
        }

        return null;
    }

    public String newStaffCode(Connection conn) throws Exception {
        return StringUtils.buildNumberString(MySQLDao.getSequence("userCode", conn), Config.StaffCodeLength);
    }


    /**
     * @description 方法实现说明
     * @author 徐明煜
     * @date 2019/1/4 14:03
     * @param user
     * @param conn
     * @return int
     * @throws Exception
     */
    public UserPO newOrUpdateUser(UserPO user, String operatorId, Connection conn) throws Exception {

        /**
         * 检查是否输入了密码，未输入则设置8位随机数字为密码
         */
        String newPassword = user.getPassword();
        if (StringUtils.isEmpty(newPassword)) {
            newPassword = NumberUtils.randomNumByLength(8);
        }
        /**
         * 转为md5值，保存到对象中
         */
        newPassword = PasswordUtils.getUserPassswordInMd5(newPassword);
        user.setPassword(newPassword);




        /**
         * 新增用户，保存到数据库
         */
        if (user.getId().equals("")) {
            /**
             * 创建短信对象并发送
             */
            SmsPO smsPO = new SmsPO();
            smsPO.setContent("密码为" + newPassword);
            smsPO.setReceiverMobile(user.getMobile());
            smsPO.setReceiverName(user.getName());
            List<SmsPO> list = new ArrayList<SmsPO>();
            list.add(smsPO);
            smsService.send(list, user, conn);




            /**
             * 设置新用户推荐码
             */
            String staffCode = this.newStaffCode(conn);
            user.setStaffCode(staffCode);
            user.setReferralCode(Config.getReferralCode(staffCode));
        }
        else {
            user.setReferralCode(null);
        }
        //保存到数据库
        MySQLDao.insertOrUpdate(user, operatorId, conn);




        // 若是销售岗位，则在销售成员表中增加销售记录
        if (user.getPositionTypeId().equals(UserPositionType.SaleMan)) {
            salesmanDao.insertSalesman(user, conn);
        }

        return user;
    }


    /**
     * 姚章鹏
     * 修改自己的密码
     */
    public int updateSelfPassword(UserPO user, Connection conn) throws Exception {
        int count = 0;
        UserPO temp = new UserPO();
        temp.setId(user.getId());
        temp.setState(Config.STATE_CURRENT);
        temp = MySQLDao.load(temp, UserPO.class);
        temp.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(temp, conn);
        if (count == 1) {
            temp.setPassword(StringUtils.md5(user.getPassword()));
        }
        temp.setSid(MySQLDao.getMaxSid("system_user", conn));
        temp.setState(Config.STATE_CURRENT);
        temp.setOperatorId(user.getId());
        temp.setOperateTime(TimeUtils.getNow());
        count = MySQLDao.insert(temp, conn);
        return count;
    }


    /**
     * 获取人员的的部门编号
     *
     * @param userID
     * @return
     */
    public DepartmentPO getDepartmentName(String userID) throws Exception {

        DepartmentPO departmentPO = Config.getDefaultDepartmentByUserId(userID);

        return departmentPO;
    }



    public Pager list(UserVO userVO, List<KVObject> conditions,String userType,HttpServletRequest request,Connection conn) throws  Exception{
        DatabaseSQL dbSQL = new DatabaseSQL();

        dbSQL.newSQL("listUser", "UserServiceSQL",UserService.class);
        dbSQL.addParameter4All("userType",userType); //销售人员类别
        dbSQL.initSQL();


        StringBuffer sbSQL = new StringBuffer(dbSQL.getSQL());
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.search(sbSQL.toString(), dbSQL.getParameters(), userVO, conditions, request, queryType, conn);
        return pager;

    }

}
