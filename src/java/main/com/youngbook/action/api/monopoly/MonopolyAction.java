package com.youngbook.action.api.monopoly;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObjects;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.KVPO;
import com.youngbook.entity.po.cms.ArticlePO;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.entity.po.customer.CustomerScorePO;
import com.youngbook.entity.po.customer.CustomerScoreType;
import com.youngbook.entity.po.production.ProductionDisplayType;
import com.youngbook.entity.vo.cms.ArticleVO;
import com.youngbook.entity.vo.customer.CustomerScoreVO;
import com.youngbook.entity.vo.production.ProductionVO;
import com.youngbook.service.cms.ArticleService;
import com.youngbook.service.customer.CustomerPersonalService;
import com.youngbook.service.customer.CustomerScoreService;
import com.youngbook.service.production.ProductionService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leevits on 6/27/2018.
 */
public class MonopolyAction extends BaseAction {

    @Autowired
    ArticleService articleService;

    @Autowired
    CustomerScoreService customerScoreService;

    @Autowired
    CustomerPersonalService customerPersonalService;

    @Autowired
    ProductionService productionService;

    public String loadPage_home_list() throws Exception {

        ArticleVO articleVO = new ArticleVO();
        articleVO.setColumnName("商城");

        List<ArticleVO> listArticleVO = articleService.getListArticleVO(articleVO, getConnection());

        getRequest().setAttribute("listArticleVO", listArticleVO);


        ArticleVO articleNews = new ArticleVO();
        articleNews.setColumnName("德合汇资讯");
        List<ArticleVO> listArticleVONews = articleService.getListArticleVO(articleNews, getConnection());
        getRequest().setAttribute("listArticleVONews", listArticleVONews);


        /**
         * 24小时要闻
         */
        ArticleVO articleNews_24 = new ArticleVO();
        articleNews_24.setColumnName("24小时要闻");
        List<ArticleVO> listArticleVONews_24 = articleService.getListArticleVO(articleNews_24, getConnection());
        getRequest().setAttribute("listArticleVONews_24", listArticleVONews_24);



        /**
         * 基金资讯
         */
        ArticleVO articleNews_fund = new ArticleVO();
        articleNews_fund.setColumnName("基金资讯");
        List<ArticleVO> listArticleVONews_fund = articleService.getListArticleVO(articleNews_fund, getConnection());
        getRequest().setAttribute("listArticleVONews_fund", listArticleVONews_fund);




        /**
         * 股票资讯
         */
        ArticleVO articleNews_stock = new ArticleVO();
        articleNews_stock.setColumnName("股票资讯");
        List<ArticleVO> listArticleVONews_stock = articleService.getListArticleVO(articleNews_stock, getConnection());
        getRequest().setAttribute("listArticleVONews_stock", listArticleVONews_stock);




        /**
         * 理财资讯
         */
        ArticleVO articleNews_money = new ArticleVO();
        articleNews_money.setColumnName("理财资讯");
        List<ArticleVO> listArticleVONews_money = articleService.getListArticleVO(articleNews_money, getConnection());
        getRequest().setAttribute("listArticleVONews_money", listArticleVONews_money);



        /**
         * 产品
         */
        ProductionVO productionVO = new ProductionVO();
        List<ProductionVO> listProductionVOs = productionService.getListProductionVO(productionVO, getConnection());

        DatabaseSQL dbSQL = DatabaseSQL.getInstance("select * from system_kv where groupname='Project_Type' ORDER BY Orders");
        List<KVPO> projectTypes = MySQLDao.search(dbSQL, KVPO.class, getConnection());

        JSONObject json = new JSONObject();


        for (int i = 0; projectTypes != null && i < projectTypes.size(); i++) {

            KVPO type = projectTypes.get(i);

            List<ProductionVO> productionVOs = new ArrayList<ProductionVO>();

            for (int j = 0; listProductionVOs != null && j < listProductionVOs.size(); j++) {
                productionVO = listProductionVOs.get(j);

                if (productionVO.getDisplayType() != null && productionVO.getDisplayType().equals(ProductionDisplayType.FinanceCircle)) {
                    if (productionVO.getProjectType().equals(type.getK())) {
                        productionVOs.add(productionVO);
                    }
                }
            }


            getRequest().setAttribute("type" + (i + 1), type);
            getRequest().setAttribute("productions" + (i + 1), productionVOs);

        }


        return "home_list";
    }


    public String loadPage_production_detail() throws Exception {

        String productionId = getHttpRequestParameter("productionId");

        ProductionVO productionVO = new ProductionVO();
        productionVO.setId(productionId);
        List<ProductionVO> listProductionVO = productionService.getListProductionVO(productionVO, getConnection());

        if (listProductionVO != null && listProductionVO.size() == 1) {
            productionVO = listProductionVO.get(0);


            /**
             * 描述文章
             */
            ArticleVO articleVO = new ArticleVO();
            articleVO.setBizId(productionVO.getProductHomeId());
            List<ArticleVO> listArticleVO = articleService.getListArticleVO(articleVO, getConnection());

            if (listArticleVO != null && listArticleVO.size() > 0) {
                articleVO = listArticleVO.get(0);

                getRequest().setAttribute("articleVO", articleVO);
            }

            getRequest().setAttribute("productionVO", productionVO);

        }



        return "production_detail";
    }


    private void buildProductionList() throws Exception {

        /**
         * 产品
         */
        ProductionVO productionVO = new ProductionVO();
        List<ProductionVO> listProductionVOs = productionService.getListProductionVO(productionVO, getConnection());

        DatabaseSQL dbSQL = DatabaseSQL.getInstance("select * from system_kv where groupname='Project_Type' ORDER BY Orders");
        List<KVPO> projectTypes = MySQLDao.search(dbSQL, KVPO.class, getConnection());


        KVObjects kvObjects = new KVObjects();

        for (int i = 0; projectTypes != null && i < projectTypes.size(); i++) {

            KVPO type = projectTypes.get(i);

            List<ProductionVO> productionVOs = new ArrayList<ProductionVO>();

            for (int j = 0; listProductionVOs != null && j < listProductionVOs.size(); j++) {
                productionVO = listProductionVOs.get(j);

                if (productionVO.getDisplayType() != null && productionVO.getDisplayType().equals(ProductionDisplayType.FinanceCircle)) {
                    if (productionVO.getProjectType().equals(type.getK())) {
                        productionVOs.add(productionVO);
                    }
                }
            }


            kvObjects.addItem("productions" + (i + 1), productionVOs);


            getRequest().setAttribute("type" + (i + 1), type);
            getRequest().setAttribute("productions" + (i + 1), productionVOs);

        }
    }

    public String loadPage_production_list() throws Exception {

        buildProductionList();

        return "production_list";
    }

    public String loadPage_more_list() throws Exception {

        return "more_list";
    }


    public String loadPage_production_list_JYS() throws Exception {


        buildProductionList();

        return "production_list_jys";
    }

    public String loadPage_goods_detail() throws Exception {

        String articleId = HttpUtils.getParameter(getRequest(), "articleId");

        ArticlePO articlePO = articleService.loadArticlePOById(articleId, getConnection());

        getRequest().setAttribute("articlePO", articlePO);

        return "goods_detail";
    }

    public String loadPage_info_detail() throws Exception {

        String articleId = HttpUtils.getParameter(getRequest(), "articleId");

        ArticlePO articlePO = articleService.loadArticlePOById(articleId, getConnection());

        getRequest().setAttribute("articlePO", articlePO);

        return "info_detail";
    }


    // score_list
    public String loadPage_score_list() throws Exception {

        String customerId = HttpUtils.getParameter(getRequest(), "customerId");

        CustomerScoreVO customerScoreVO = customerScoreService.getCustomerScoreVO(customerId, getLoginCustomer().getId(), getConnection());

        getRequest().setAttribute("customerScoreVO", customerScoreVO);

        return "score_list";
    }

    public String loadPage_score_add_list() throws Exception {

        String customerId = HttpUtils.getParameter(getRequest(), "customerId");

        List<CustomerScorePO> listCustomerScorePO = customerScoreService.getListCustomerScorePO(customerId, CustomerScoreType.Add, getConnection());

        getRequest().setAttribute("listCustomerScorePO", listCustomerScorePO);

        return "score_add_list";
    }

    public String loadPage_score_use_list() throws Exception {

        String customerId = HttpUtils.getParameter(getRequest(), "customerId");

        List<CustomerScorePO> listCustomerScorePO = customerScoreService.getListCustomerScorePO(customerId, CustomerScoreType.Use, getConnection());

        getRequest().setAttribute("listCustomerScorePO", listCustomerScorePO);

        return "score_use_list";
    }

    public String loadPage_score_detail() throws Exception {

        String customerScoreId = HttpUtils.getParameter(getRequest(), "customerScoreId");

        CustomerScorePO customerScorePO = customerScoreService.loadCustomerScorePOById(customerScoreId, getConnection());

        getRequest().setAttribute("customerScorePO", customerScorePO);

        return "score_detail";
    }

    public String loadPage_mine_change_mobile() throws Exception {

        String customerId = getHttpRequestParameter("customerId");
        String newMobile = getHttpRequestParameter("mobile");
        String password = getHttpRequestParameter("password");
        String mobileCode = getHttpRequestParameter("mobileCode");

        CustomerPersonalPO customerPersonalPO = customerPersonalService.changeMobileByPassword(customerId, newMobile, password, mobileCode, getLoginCustomer().getId(), getConnection());

        getResult().setReturnValue(customerPersonalPO);

        return SUCCESS;
    }


    public String loadPage_score_use() throws Exception {

        try {
            String articleId = HttpUtils.getParameter(getRequest(), "articleId");

            ArticlePO articlePO = articleService.loadArticlePOById(articleId, getConnection());

            CustomerScorePO customerScorePO = customerScoreService.useScore(getLoginCustomer().getId(), articlePO.getScore(), articlePO.getId(), "兑换【"+articlePO.getTitle()+"】，使用积分【"+articlePO.getScore()+"】", getLoginCustomer().getId(), getConnection());

            getRequest().setAttribute("customerScorePO", customerScorePO);

            return "score_use_success";
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return "score_use_error";
    }

}
