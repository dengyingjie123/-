package com.youngbook.common.utils;

import junit.framework.TestCase;
import org.junit.Test;

public class NumberUtilsTest extends TestCase {

    @Test
    public void testRandomNumbers() throws Exception {
        for (int i = 0; i < 100; i++) {
            String code = String.valueOf(NumberUtils.randomNumbers(6));
            System.out.println(code);
        }
    }
}