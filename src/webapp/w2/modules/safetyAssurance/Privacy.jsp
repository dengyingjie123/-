<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.service.cms.ArticleService" %>
<%@ page import="com.youngbook.entity.po.cms.ArticlePO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv=“X-UA-Compatible” content=“IE=8″>
    <title>合法合规</title>

    <link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/about/about.css" rel="stylesheet" type="text/css"/>
    <script language="javascript" src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>

</head>

<body>

<jsp:include page="/w2/top.jsp"/>

<div style="width: 100%;background: #fafafa;" >
    <div id="nav" class="w1200" style="padding-top: 7px;padding-bottom: 7px;">
        <a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex"><img src="<%=Config.getWebRoot()%>/w2/img/logo_Assistor.png" alt="开普乐"/></a>
        <span>
          <a id="param1" href="<%=Config.getWebRoot()%>/w2/index/ShowIndex" class="item" >首页</a>
        <a id="param2" href="<%=Config.getWebRoot()%>/w2/modules/production/productionList.jsp" >投资专区</a>

          <a id="param3" href="<%=Config.getWebRoot()%>/w2/modules/about.jsp" >新手指引</a> |
            <a id="param4" href="<%=Config.getWebRoot()%>/w2/customer/IndexShow" >我的账户</a>
        </span>
    </div>
</div>

<div id="position" class="w1200">
    <a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex">安全保障</a> &gt;合法合规
</div>

<div id="container">
    <div class="tabs-wrap" style="width:800px;margin:0 auto;background-color:white;padding: 10px;" >
        <div   style="display:block" class="about_cont">
            <h1 style="text-align: center">合法合规</h1>
        </div>
        <div  class="about_cont">
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;北京大成（昆明）律师事务所受聘作为我们的常年法律顾问，为我们提供强大的法律支持。</p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;北京大成律师事务所（以下简称“大成所”）是中国成立最早的合伙制律师事务所。2015年11月10日，大成律师事务所和全球十大律所之一的Dentons跨国律师事务所正式启动合并，成为世界上第一家全球多中心的律师事务所。新律所拥有逾 6,600 名律师，服务于 50 多个国家超过125个地区，业务遍及加拿大、美国、欧洲、英国、中东和非洲以及整个亚太地区，成为全球规模最大的律师事务所。</p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;大成所是中国唯一连续同时获得并保持“全国优秀律师事务所”、“司法部部级文明所”两大殊荣的律师事务所，并荣获“Acritas2015全球顶尖20家精英品牌律所”、“中国VC/PE人民币基金募资最佳法律顾问机构”、“中国VC/PE人民币基金投资最佳法律顾问机构”、“（VC/PE支持）中国境内并购市场最佳法律顾问机构”、“ALB最佳破产重组业务大奖”、“中国十佳IPO律师事务所”等多项殊荣。</p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;大成昆明所是大成所设立于云南的机构，共有员工260余人，设有金融部、诉讼仲裁部、资本市场部、公司部、投资并购部、土地矿产部、房地产与建设工程部、国际部、劳动法专业部、企业风险管控部、财富传承部、商事法律事务部、刑事部等专业部门，形成了以国际业务、金融及资本业务、传统业务为主的三大强势业务板块，能够有针对性地为客户提供专业化、多层次、跨法域的优质法律服务。</p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;以大成昆明所为中心，云南省各州市的十几家知名律师事务所也成为“大成全球化法律服务网络成员单位”，使大成的法律服务资源在云南实现了全省、全国、全球的无缝对接，确保客户能够及时得到优质、高效、周到的服务。</p>

            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>关于点金派网站的合法性，请参阅下文：</strong></p>

            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一、关于点金派网站投资模式的合法性</p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;根据《民法通则》第七十一条财产所有权是指所有人依法对自己的财产享有占有、使用、收益和处分的权利。点金派网站提供转让方转让其持有金融财产部分或全部权利的机会，符合现行法律的规定。</p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;二、关于点金派网站提供服务的合法性</p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;根据《合同法》第23章关于"居间合同"的规定，特别是第424条规定的"居间合同是居间人向委托人报告订立合同的机会或者提供订立合同的媒介服务，委托人支付报酬的合同"，点金派网站为客户提供投资机会的居间服务有着明确的法律基础。</p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;三、关于电子合同的合法性</p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;根据《电子签名法》的规定，民事活动中的合同或者其他文件、单证等文书，当事人可以约定使用电子签名、数据电文，不能因为合同采用电子签名、数据电文就否定其法律效力。同时，《电子签名法》中还规定，可靠的电子签名与手写签名或者盖章具有同等的法律效力，明确肯定了符合条件的电子签名与手写签名或盖章具有同等的效力。</p>
        </div>
    </div>

</div>

<jsp:include page="/w2/bottom.jsp"/>

</body>
</html>
