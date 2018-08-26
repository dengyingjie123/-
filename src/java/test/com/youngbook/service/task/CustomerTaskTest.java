package com.youngbook.service.task;

import com.youngbook.common.utils.ExcelUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerTaskTest {

    @Test
    public void testTask() throws Exception {
        CustomerTask customerTask = new CustomerTask();
        customerTask.test();
    }
}