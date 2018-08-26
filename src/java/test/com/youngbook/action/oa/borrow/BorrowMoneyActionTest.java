package com.youngbook.action.oa.borrow;

import com.opensymphony.xwork2.ActionProxy;
import com.youngbook.common.MyException;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.oa.borrow.BorrowMoneyPO;
import com.youngbook.entity.vo.oa.borrow.BorrowMoneyVO;
import com.youngbook.service.customer.CustomerPersonalService;
import com.youngbook.service.oa.borrow.BorrowMoneyService;
import com.youngbook.service.oa.borrow.BorrowMoneyServiceTest;
import org.apache.struts2.StrutsTestCase;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.sql.Connection;
import java.util.Date;

/**
 * BorrowMoneyAction Tester.
 *
 * @author <Authors name>
 * @since <pre>九月 25, 2015</pre>
 * @version 1.0
 */
public class BorrowMoneyActionTest extends StrutsTestCase {
    private BorrowMoneyAction bor=null;
    private BorrowMoneyService service=new BorrowMoneyService();
    private BorrowMoneyVO borrowMoneyVO = new BorrowMoneyVO();
    BorrowMoneyPO borrowMoney = new BorrowMoneyPO();
    Connection conn ;

    @Before
    public void before() throws Exception {
        conn = Config.getConnection();
        ActionProxy proxy =getActionProxy("/oa/borrow/BorrowMoney_list.action");
        bor = (BorrowMoneyAction)proxy.getAction();
        UserPO user  = new UserPO();
        user.setId("ab7996b505df42cda37400f550f7cf1c");
        request.getSession().setAttribute("loginPO",user);
    }

    @After
    public void after() throws Exception {
    }


    @Test
    public void testInsertOrUpdate() throws Exception {

        conn = Config.getConnection();
        ActionProxy proxy =getActionProxy("/oa/borrow/BorrowMoney_list.action");
        bor = (BorrowMoneyAction)proxy.getAction();
        UserPO user  = new UserPO();
        user.setId("ab7996b505df42cda37400f550f7cf1c");
        request.getSession().setAttribute("loginPO", user);

        borrowMoney.setApplicantName("admin");
         borrowMoney.setApplicantId("ab7996b505df42cda37400f550f7cf1c");
         borrowMoney.setApplicationTime(TimeUtils.getDateSimpleString(new Date()));
        borrowMoney.setMoney("50000");
        borrowMoneyVO.setControlString3("");
        borrowMoneyVO.setControlString1("1fb9b833-f65f-43f3-acbe-b3e495811a78");
        borrowMoneyVO.setControlString2("E22F72DA-800A-4BFC-B9E8-CE1040DC73A9");

        //要将action insertOrUpdate 添加Connection conn 的参数,两个对象参数
//        bor.insertOrUpdate(conn,borrowMoney,borrowMoneyVO);

//TODO: Test goes here... 
    }

    /**
     *
     * Method: load()
     *
     */
    @Test
    public void testLoad() throws Exception {
        conn = Config.getConnection();
        ActionProxy proxy =getActionProxy("/oa/borrow/BorrowMoney_list.action");
        bor = (BorrowMoneyAction)proxy.getAction();
        UserPO user  = new UserPO();
        user.setId("ab7996b505df42cda37400f550f7cf1c");
        request.getSession().setAttribute("loginPO", user);
        request.setParameter("borrowMoney.id", "ab7996b505df42cda37400f550f7cf1c");

        assertEquals("SUCCESS", bor.load());
//TODO: Test goes here... 
    }

    /**
     *
     * Method: delete()
     *
     */
    @Test
    public void testDelete() throws Exception {
        conn = Config.getConnection();
        ActionProxy proxy =getActionProxy("/oa/borrow/BorrowMoney_list.action");
        bor = (BorrowMoneyAction)proxy.getAction();
        UserPO user  = new UserPO();
        user.setId("ab7996b505df42cda37400f550f7cf1c");
        request.getSession().setAttribute("loginPO", user);
        request.setParameter("borrowMoney.id","ab7996b505df42cda37400f550f7cf1c");

        assertEquals("SUCCESS",  bor.delete());
//TODO: Test goes here... 
    }


} 
