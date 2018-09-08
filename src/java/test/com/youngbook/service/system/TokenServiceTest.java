package com.youngbook.service.system;

import com.youngbook.common.Database;
import com.youngbook.common.config.Config;
import com.youngbook.entity.po.system.TokenBizType;
import com.youngbook.entity.po.system.TokenPO;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

/**
 * Created by leevits on 9/8/2018.
 */
public class TokenServiceTest {

    TokenService tokenService = Config.getBeanByName("tokenService", TokenService.class);

    @Test
    public void newToken() throws Exception {

        String customerId = "66081B03C2BB401A9E013EBE381C57B6";


        Connection conn = Config.getConnection();
        try {
            TokenPO tokenPO = tokenService.newToken(customerId, TokenBizType.CustomerLoginToken, "127.0.0.1", conn);
            System.out.println(tokenPO.getBizId());
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }


    }

}