package com.youngbook.action.api.monopoly;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObjects;
import com.youngbook.common.MyException;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.KVPO;
import com.youngbook.entity.po.cms.ArticlePO;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.entity.po.customer.CustomerScorePO;
import com.youngbook.entity.po.customer.CustomerScoreType;
import com.youngbook.entity.po.production.ProductionDisplayType;
import com.youngbook.entity.po.production.ProductionPO;
import com.youngbook.entity.po.web.AdImagePO;
import com.youngbook.entity.vo.cms.ArticleVO;
import com.youngbook.entity.vo.customer.CustomerScoreVO;
import com.youngbook.entity.vo.monopoly.HomeListVO;
import com.youngbook.entity.vo.monopoly.ReturnProductionAndArticleVO;
import com.youngbook.entity.vo.monopoly.ReturnArticleVO;
import com.youngbook.entity.vo.monopoly.ReturnProductionVO;
import com.youngbook.entity.vo.production.ProductionVO;
import com.youngbook.service.cms.ArticleService;
import com.youngbook.service.customer.CustomerPersonalService;
import com.youngbook.service.customer.CustomerScoreService;
import com.youngbook.service.production.ProductionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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


    /**
     * @description 将ArticleVO对象转成ReturnArticleVO对象，并返回需要的对象数量
     * @author 徐明煜
     * @date 2019/1/16 13:48
     * @param listArticleVO
     * @param size
     * @return java.util.List<com.youngbook.entity.vo.monopoly.ReturnArticleVO>
     * @throws Exception
     */
    private List<ReturnArticleVO> transforArticleVO(List<ArticleVO> listArticleVO, Integer size) throws Exception {

        //新建返回列表
        List<ReturnArticleVO> listReturnArticleVO = new ArrayList<>();

        //防止空指针异常
        if(listArticleVO.size() < size){
            size = listArticleVO.size();
        }



        //进行对象转换
        for (int i =0; i < size; i++){
            //新建返回对象
            ReturnArticleVO returnArticleVO = new ReturnArticleVO();


            /**
             * 从数据库读出的日期末尾多零，进行格式化
             */
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //创建要显示的日期格式
            Date date = fmt.parse(listArticleVO.get(i).getPublishedTime());      //将从数据库读出来的 timestamp 类型的时间转换为java的Date类型
            String s = fmt.format(date);
            listArticleVO.get(i).setPublishedTime(s);




            /**
             * 复制对象属性
             */
            BeanUtils.copyProperties(listArticleVO.get(i), returnArticleVO);
            listReturnArticleVO.add(returnArticleVO);
        }

        return listReturnArticleVO;
    }


    /**
     * @description 根据请求需要各种类文章的数量返回大富翁首页数据
     * @author 徐明煜
     * @date 2019/1/16 14:34
     * @param
     * @return java.lang.String
     * @throws Exception
     */
    public String loadPage_home_list() throws Exception {

        //获得需要的对象数量
        if(StringUtils.isEmptyAny(getHttpRequestParameter("news24")
                , getHttpRequestParameter("news_fund")
                , getHttpRequestParameter("news_stock")
                , getHttpRequestParameter("news_money")
                , getHttpRequestParameter("production"))){
            MyException.newInstance("参数不完整").throwException();
        }
        Integer news24 = Integer.parseInt(getHttpRequestParameter("news24"));
        Integer news_fund = Integer.parseInt(getHttpRequestParameter("news_fund"));
        Integer news_stock = Integer.parseInt(getHttpRequestParameter("news_stock"));
        Integer news_money = Integer.parseInt(getHttpRequestParameter("news_money"));
        Integer production  = Integer.parseInt(getHttpRequestParameter("production"));



        ArticleVO articleVO = new ArticleVO();
        articleVO.setColumnName("商城");

        List<ArticleVO> listArticleVO = articleService.getListArticleVO(articleVO, getConnection());




        ArticleVO articleNews = new ArticleVO();
        articleNews.setColumnName("德合汇资讯");
        List<ArticleVO> listArticleVONews = articleService.getListArticleVO(articleNews, getConnection());



        /**
         * 24小时要闻
         */
        ArticleVO articleNews_24 = new ArticleVO();
        articleNews_24.setColumnName("24小时要闻");
        List<ArticleVO> listArticleVONews_24 = articleService.getListArticleVO(articleNews_24, getConnection());

        //对象转换
        List<ReturnArticleVO> listReturnArticleVONews_24 = transforArticleVO(listArticleVONews_24, news24);





        /**
         * 基金资讯
         */
        ArticleVO articleNews_fund = new ArticleVO();
        articleNews_fund.setColumnName("基金资讯");
        List<ArticleVO> listArticleVONews_fund = articleService.getListArticleVO(articleNews_fund, getConnection());

        //对象转换
        List<ReturnArticleVO> listReturnArticleVONews_fund = transforArticleVO(listArticleVONews_fund, news_fund);




        /**
         * 股票资讯
         */
        ArticleVO articleNews_stock = new ArticleVO();
        articleNews_stock.setColumnName("股票资讯");
        List<ArticleVO> listArticleVONews_stock = articleService.getListArticleVO(articleNews_stock, getConnection());

        //对象转换
        List<ReturnArticleVO> listReturnArticleVONews_stock = transforArticleVO(listArticleVONews_stock, news_stock);




        /**
         * 理财资讯
         */
        ArticleVO articleNews_money = new ArticleVO();
        articleNews_money.setColumnName("理财资讯");
        List<ArticleVO> listArticleVONews_money = articleService.getListArticleVO(articleNews_money, getConnection());

        //对象转换
        List<ReturnArticleVO> listReturnArticleVONews_money = transforArticleVO(listArticleVONews_money, news_money);



        /**
         * 产品
         */
        ProductionVO productionVO = new ProductionVO();
        List<ProductionVO> listProductionVOs = productionService.getListProductionVO(productionVO, getConnection());
        List<ReturnProductionVO> listReturnProductionVO = new ArrayList<>();
        //限制返回数量最大值
        if(listProductionVOs.size() <= production){
            production = listProductionVOs.size();
        }
        //产品属性部分屏蔽
        for (int i =0; i < production; i++){
            ReturnProductionVO returnArticleVO = new ReturnProductionVO();
            BeanUtils.copyProperties(listProductionVOs.get(i), returnArticleVO);
            listReturnProductionVO.add(returnArticleVO);
        }




        /**
         * 轮播图全路径
         */
        List<HashMap> listImage = new ArrayList<>();
        //本机IP地址
        String ip = InetAddress.getLocalHost().getHostAddress();
        //组装录井对象
        HashMap<String, String> image01 = new HashMap();
        image01.put("src", "http://" + ip + ":8080" + "/core/monopoly/image/home/banner01.png");
        HashMap<String, String> image02 = new HashMap();
        image02.put("src", "http://" + ip + ":8080" + "/core/monopoly/image/home/banner02.png");
        listImage.add(image01);
        listImage.add(image02);


        /**
         * 组装返回对象
         */
        HomeListVO homeListVO = new HomeListVO(listImage, listReturnArticleVONews_24, listReturnArticleVONews_fund, listReturnArticleVONews_stock, listReturnArticleVONews_money, listReturnProductionVO);
        getResult().setReturnValue(homeListVO);

        return SUCCESS;
    }


    /**
     * @description 根据产品Id查找产品详情及相关文章
     * @author 徐明煜
     * @date 2019/1/16 16:05
     * @param
     * @return java.lang.String
     * @throws Exception
     */
    public String loadPage_production_detail() throws Exception {

        /**
         * 产品对象
         */
        String productionId = getHttpRequestParameter("productionId");
        ProductionPO productionPO = productionService.loadProductionById(productionId, getConnection());
        //对产品对象进行部分属性屏蔽
        ReturnProductionVO returnProductionVO = new ReturnProductionVO();
        BeanUtils.copyProperties(productionPO, returnProductionVO);




        /**
         * 描述文章
         */
        ArticleVO articleVO = new ArticleVO();
        articleVO.setBizId(productionPO.getProductHomeId());
        List<ArticleVO> listArticleVO = articleService.getListArticleVO(articleVO, getConnection());

        List<ReturnArticleVO> listReturnArticleVO = transforArticleVO(listArticleVO, listArticleVO.size());




        /**
         * 组装返回json,包含产品详情及相应文章描述
         */
        //新建返回对象
        ReturnProductionAndArticleVO returnProductionAndArticleVO = new ReturnProductionAndArticleVO();
        //向返回对象写入产品和描述文章信息
        returnProductionAndArticleVO.setArticle(listReturnArticleVO);
        returnProductionAndArticleVO.setProduction(returnProductionVO);
        getResult().setReturnValue(returnProductionAndArticleVO);

        return SUCCESS;
    }


    /**
     * @description 根据文章Id返回文章详情
     * @author 徐明煜
     * @date 2019/1/16 16:05
     * @param
     * @return java.lang.String
     * @throws Exception
     */
    public String load_article_detail() throws Exception {

        /**
         * 根据文章id查找文章
         */
        String articleId = getHttpRequestParameter("articleId");
        ArticlePO articlePO = articleService.loadArticlePOById(articleId, getConnection());




        /**
         * 组装返回文章详情
         */
        ReturnArticleVO returnArticleVO = new ReturnArticleVO();
        BeanUtils.copyProperties(articlePO, returnArticleVO);
        getResult().setReturnValue(returnArticleVO);

        return SUCCESS;
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
