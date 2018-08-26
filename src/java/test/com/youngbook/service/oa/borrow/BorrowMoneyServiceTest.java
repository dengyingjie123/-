package com.youngbook.service.oa.borrow;

import com.youngbook.action.oa.borrow.BorrowMoneyAction;
import com.youngbook.common.Pager;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.oa.borrow.BorrowMoneyPO;
import com.youngbook.entity.vo.oa.borrow.BorrowMoneyVO;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.sql.Connection;
import java.util.Date;

/**
 * BorrowMoneyService Tester.
 *
 * @author <Authors name>
 * @since <pre>九月 25, 2015</pre>
 * @version 1.0
 */
public class BorrowMoneyServiceTest {
   private BorrowMoneyAction bor=null;
   private BorrowMoneyService service=new BorrowMoneyService();
   private BorrowMoneyVO borrowMoneyVO = new BorrowMoneyVO();
   BorrowMoneyPO borrowMoney = new BorrowMoneyPO();

   @Before
   public void before() throws Exception {
   }

   @After
   public void after() throws Exception {
   }

   /**
    *
    * Method: insertOrUpdate(BorrowMoneyPO borrowMoney, BorrowMoneyVO borrowMoneyVO, UserPO user, Connection conn)
    *
    */
   @Test
   public void testInsertOrUpdate() throws Exception {

      Connection conn = Config.getConnection();
      UserPO user  = new UserPO();
      user.setId("ab7996b505df42cda37400f550f7cf1c");

      borrowMoney.setApplicantName("admin");
      borrowMoney.setApplicantId("ab7996b505df42cda37400f550f7cf1c");
      borrowMoney.setApplicationTime(TimeUtils.getDateSimpleString(new Date()));
      borrowMoney.setMoney("50000");
      borrowMoneyVO.setControlString3("");
      borrowMoneyVO.setControlString1("1fb9b833-f65f-43f3-acbe-b3e495811a78");
      borrowMoneyVO.setControlString2("E22F72DA-800A-4BFC-B9E8-CE1040DC73A9");


      service.insertOrUpdate(borrowMoney,borrowMoneyVO,user,conn);

//TODO: Test goes here... 
   }

   /**
    *
    * Method: loadBorrowMoneyPO(String id)
    *
    */
   @Test
   public void testLoadBorrowMoneyPO() throws Exception {
      Connection conn = Config.getConnection();
      String id="6C34C33D51CD4D78930F8D7819A334A5";
      service.loadBorrowMoneyPO(id);

//TODO: Test goes here... 
   }

   /**
    *
    * Method: delete(BorrowMoneyPO borrowMoney, UserPO user, Connection conn)
    *
    */
   @Test
   public void testDelete() throws Exception {
      Connection conn = Config.getConnection();
      UserPO user  = new UserPO();
      user.setId("ab7996b505df42cda37400f550f7cf1c");
      borrowMoney.setId("6C34C33D51CD4D78930F8D7819A334A5");
      service.delete(borrowMoney,user,conn);

//TODO: Test goes here... 
   }

   /**
    *
    * Method: list(BorrowMoneyVO borrowMoneyVO, UserPO user, List<KVObject> conditions)
    *
    */
   @Test
   public void testList() throws Exception {

      Connection conn = Config.getConnection();
      UserPO user  = new UserPO();
      user.setId("ab7996b505df42cda37400f550f7cf1c");
      service.list(borrowMoneyVO,user,null);
//TODO: Test goes here... 
   }

   /**
    *
    * Method: Waitlist(BorrowMoneyVO borrowMoneyVO, UserPO user, List<KVObject> conditions)
    *
    */
   @Test
   public void testWaitlist() throws Exception {

      Connection conn = Config.getConnection();
      UserPO user  = new UserPO();
      user.setId("ab7996b505df42cda37400f550f7cf1c");
      service.list(borrowMoneyVO, user, null);
//TODO: Test goes here... 
   }

   /**
    *
    * Method: Participantlist(BorrowMoneyVO BorrowMoneyVO, UserPO user, List<KVObject> conditions)
    *
    */
   @Test
   public void testParticipantlist() throws Exception {
      Connection conn = Config.getConnection();
      UserPO user  = new UserPO();
      user.setId("ab7996b505df42cda37400f550f7cf1c");
      service.list(borrowMoneyVO,user,null);
//TODO: Test goes here... 
   }

   /**
    *
    * Method: insertTitle(Pager pager)
    *
    */
   @Test
   public void testInsertTitle() throws Exception {
      Connection conn = Config.getConnection();
      UserPO user  = new UserPO();
      user.setId("ab7996b505df42cda37400f550f7cf1c");
      Pager pager =service.list(borrowMoneyVO, user, null);
      service.insertTitle(pager);
//TODO: Test goes here... 
   }

   /**
    *
    * Method: getPrintDate(String id)
    *
    */
   @Test
   public void testGetPrintDate() throws Exception {
      String id="6C34C33D51CD4D78930F8D7819A334A5";
      service.getPrintDate(id);


//TODO: Test goes here... 
   }




} 
