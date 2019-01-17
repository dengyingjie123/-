package com.youngbook.entity.vo.monopoly;

import java.util.List;

/**
 * @description: 返回产品对象及描述文章
 * @author: 徐明煜
 * @createDate: 2019/1/16 14:48
 * @version: 1.1
 */
public class ReturnProductionAndArticleVO {

    //文章对象
    private ReturnProductionVO production;

    //文章对象
    private List<ReturnArticleVO> article;

    public ReturnProductionVO getProduction() {
        return production;
    }

    public void setProduction(ReturnProductionVO production) {
        this.production = production;
    }

    public List<ReturnArticleVO> getArticle() {
        return article;
    }

    public void setArticle(List<ReturnArticleVO> article) {
        this.article = article;
    }
}
