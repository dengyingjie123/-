package com.youngbook.service.task;

import junit.framework.TestCase;
import org.junit.Test;

public class SmsSenderTaskTest extends TestCase {

    @Test
    public void testRun() throws Exception {
        SmsSenderTask task = new SmsSenderTask();

        task.run();
    }
}