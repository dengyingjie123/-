package com.youngbook.common.utils;

import org.junit.Test;
import org.patchca.service.Captcha;

import static org.junit.Assert.*;

public class VarificationUtilsTest {

    @Test
    public void testVarificationCodeGenerator() throws Exception {
        Captcha captcha = VarificationUtils.varificationCodeGenerator(5, 5, null, null, null, 0);
        String code = captcha.getChallenge();

        System.out.println(code);
    }
}