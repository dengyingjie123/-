package com.youngbook.entity.vo.monopoly;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.entity.vo.BaseVO;

/**
 * @description: 大富翁APP发挥到前端的文章对象，屏蔽部分数据
 * @author: 徐明煜
 * @createDate: 2019/1/15 17:41
 * @version: 1.1
 */
public class ReturnArticleVO extends BaseVO {

    //文章Id
    private String id;

    //文章内容
    private String content;

    //文章标题
    private String title;

    //发布日期
    @DataAdapter(fieldType = FieldType.DATE)
    private String publishedTime = new String();

    // 缩略图
    private String image = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(String publishedTime) {
        this.publishedTime = publishedTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
