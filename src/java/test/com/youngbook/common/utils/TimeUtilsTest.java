package com.youngbook.common.utils;

import org.junit.Test;

import javax.sound.midi.Soundbank;

import static org.junit.Assert.*;

public class TimeUtilsTest {
    @Test
    public void getFirstDayOfWeek() throws Exception {
        System.out.println(TimeUtils.getFirstDayOfWeek("2018-07-11"));
    }

    @Test
    public void getLastDayOfWeek() throws Exception {
        System.out.println(TimeUtils.getLastDayOfWeek("2018-07-11"));
    }

    @Test
    public void testGetSimpleTimestamp() throws Exception {
        System.out.println(TimeUtils.getSimpleTimestamp());
        Thread.sleep(1000);
        System.out.println(TimeUtils.getSimpleTimestamp());
        Thread.sleep(1000);
        System.out.println(TimeUtils.getSimpleTimestamp());
        Thread.sleep(1000);
        System.out.println(TimeUtils.getSimpleTimestamp());
    }

    @Test
    public void testGetDiffTime() throws Exception {
        TimeUtils.getDiffTime("2017-03-09", "2017-01-18", "yyyy-MM-dd");
    }
}