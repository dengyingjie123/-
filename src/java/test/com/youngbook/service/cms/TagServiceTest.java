package com.youngbook.service.cms;

import com.youngbook.common.Database;
import com.youngbook.common.config.Config;
import com.youngbook.entity.po.cms.ArticleTagPO;
import com.youngbook.entity.po.cms.TagPO;
import org.junit.Test;

import java.sql.Connection;

/**
 * Created by Lee on 10/31/2017.
 */
public class TagServiceTest {

    TagService tagService = Config.getBeanByName("tagService", TagService.class);

    @Test
    public void newTag() throws Exception {
        // 42385268F1014218B506D730A122536B

        Connection conn = Config.getConnection();
        try {

            for (int i = 0; i < 10; i++) {
                ArticleTagPO articleTagPO = tagService.newTag("42385268F1014218B506D730A122536B", "abc", "0000", conn);
                System.out.println(articleTagPO.toJsonObject().toString());
            }


        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }
    }

    @Test
    public void removeTag() throws Exception {

    }



    @Test
    public void insertOrUpdate() throws Exception {
        Connection conn = Config.getConnection();
        try {
            TagPO tagPO = new TagPO();
            tagPO.setName("Tag1");

            tagPO = tagService.insertOrUpdate(tagPO, "000", conn);
            System.out.println(tagPO.toJsonObject().toString());


            for (int i = 0; i < 10; i++) {
                tagPO.setName("newTag" + i);

                tagPO = tagService.insertOrUpdate(tagPO, "000", conn);
                System.out.println(tagPO.toJsonObject().toString());
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }
    }




}