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

    String customerId = "66081B03C2BB401A9E013EBE381C57B6";
    String ip = "127.0.0.1";

    @Test
    public void checkAndRenewToken() throws Exception {



        Connection conn = Config.getConnection();
        try {
            TokenPO tokenPO = tokenService.checkAndRenewToken("C517E19B503848D48EB6A9646AD3D2F5", customerId, TokenBizType.CustomerLoginToken, ip, conn);
            System.out.println(tokenPO.getBizId());
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }
    }



    @Test
    public void newToken() throws Exception {



        Connection conn = Config.getConnection();
        try {
            TokenPO tokenPO = tokenService.newToken(customerId, TokenBizType.CustomerLoginToken, ip, conn);
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