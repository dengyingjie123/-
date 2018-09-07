package com.youngbook;
import com.youngbook.service.customer.CustomerPersonalServiceTest;
import com.youngbook.service.oa.borrow.BorrowMoneyServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Created by Jevons on 2015/10/13.
 */
@RunWith(Suite.class)
@SuiteClasses({
        CustomerPersonalServiceTest.class,
        BorrowMoneyServiceTest.class
})
public class AllTest {
}
