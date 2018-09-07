package com.youngbook.service.task;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArchiveLogDataTaskTest {

    @Test
    public void testRun() throws Exception {
        ArchiveLogDataTask archiveLogDataTask = new ArchiveLogDataTask();

        archiveLogDataTask.run();
    }
}