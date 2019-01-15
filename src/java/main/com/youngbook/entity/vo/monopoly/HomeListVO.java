package com.youngbook.entity.vo.monopoly;

import com.youngbook.entity.vo.BaseVO;
import com.youngbook.entity.vo.production.ProductionVO;

import java.util.List;


/**
 * @description: 大富翁APP发挥到前端的对象,包括首页轮播图，文章对象，产品对象
 * @author: 徐明煜
 * @createDate: 2019/1/15 17:52
 * @version: 1.1
 */
public class HomeListVO extends BaseVO {

    private List<String> frontImage;

    private List<ReturnArticleVO> listArticleVONews_24;

    private List<ReturnArticleVO> listArticleVONews_fund;

    private List<ReturnArticleVO> listArticleVONews_stock;

    private List<ReturnArticleVO> listArticleVONews_money;

    private List<ReturnProductionVO> listProductionVOs;

    public List<String> getFrontImage() {
        return frontImage;
    }

    public void setFrontImage(List<String> frontImage) {
        this.frontImage = frontImage;
    }

    public List<ReturnArticleVO> getListArticleVONews_24() {
        return listArticleVONews_24;
    }

    public void setListArticleVONews_24(List<ReturnArticleVO> listArticleVONews_24) {
        this.listArticleVONews_24 = listArticleVONews_24;
    }

    public List<ReturnArticleVO> getListArticleVONews_fund() {
        return listArticleVONews_fund;
    }

    public void setListArticleVONews_fund(List<ReturnArticleVO> listArticleVONews_fund) {
        this.listArticleVONews_fund = listArticleVONews_fund;
    }

    public List<ReturnArticleVO> getListArticleVONews_stock() {
        return listArticleVONews_stock;
    }

    public void setListArticleVONews_stock(List<ReturnArticleVO> listArticleVONews_stock) {
        this.listArticleVONews_stock = listArticleVONews_stock;
    }

    public List<ReturnArticleVO> getListArticleVONews_money() {
        return listArticleVONews_money;
    }

    public void setListArticleVONews_money(List<ReturnArticleVO> listArticleVONews_money) {
        this.listArticleVONews_money = listArticleVONews_money;
    }

    public List<ReturnProductionVO> getListProductionVOs() {
        return listProductionVOs;
    }

    public void setListProductionVOs(List<ReturnProductionVO> listProductionVOs) {
        this.listProductionVOs = listProductionVOs;
    }

    public HomeListVO(List<String> frontImage, List<ReturnArticleVO> listArticleVONews_24, List<ReturnArticleVO> listArticleVONews_fund, List<ReturnArticleVO> listArticleVONews_stock, List<ReturnArticleVO> listArticleVONews_money, List<ReturnProductionVO> listProductionVOs) {
        this.frontImage = frontImage;
        this.listArticleVONews_24 = listArticleVONews_24;
        this.listArticleVONews_fund = listArticleVONews_fund;
        this.listArticleVONews_stock = listArticleVONews_stock;
        this.listArticleVONews_money = listArticleVONews_money;
        this.listProductionVOs = listProductionVOs;
    }

    public HomeListVO() {
    }
}
