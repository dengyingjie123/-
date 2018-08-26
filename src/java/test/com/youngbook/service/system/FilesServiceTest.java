package com.youngbook.service.system;

import com.youngbook.common.Database;
import com.youngbook.common.config.Config;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

public class FilesServiceTest {

    FilesService filesService = Config.getBeanByName("filesService", FilesService.class);

    @Test
    public void testChangeFilePath() throws Exception {
        Connection conn = Config.getConnection();
        try {
            conn.setAutoCommit(true);

            filesService.changeFilePath2Relative(conn);

//            conn.rollback();
        }
        catch (Exception e) {
            conn.rollback();
            throw e;
        }
        finally {
            Database.close(conn);
        }
    }
}