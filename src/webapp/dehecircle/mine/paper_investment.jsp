<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.vo.production.OrderVO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%
  OrderVO orderVO = (OrderVO)request.getAttribute("orderVO");
%>
<div data-page="paper-investment" class="page">


  <div class="navbar">
    <div class="navbar-inner navbar-ph">
      <div class="left"><a href="#" class="back link"> <i class="icon-white icon-back"></i><span></span></a></div>
      <div class="center">认购协议</div>
      <div class="right">
        <%--<a href="customer/customer_save.jsp" class="link"><i class="icon material-icons">add</i></a>--%>
        <%--<a href="#" class="link"><i class="icon material-icons">more_vert</i></a>--%>
      </div>
    </div>
  </div>

  <div class="page-content">

    <%--<div class="content-block-title">支付码为【{{payCode}}】</div>--%>
    <%--<div class="list-block">--%>
      <%--<ul>--%>
        <%--<li>--%>
          <%--<div class="item-content">--%>
            <%--<div class="item-media"><i class="icon material-icons">local_atm</i></div>--%>
            <%--<div class="item-inner">--%>
              <%--<div class="item-title">网址</div>--%>
              <%--<div class="item-after">http://pay.dianjinpai.com</div>--%>
            <%--</div>--%>
          <%--</div>--%>
        <%--</li>--%>
      <%--</ul>--%>
    <%--</div>--%>


    <div class="content-block">
      <span class="txt-20 center color-blue">认购协议</span><br>
      <br><br>
      <span class="txt-20">甲方（投资人）：<%=orderVO.getCustomerName()%></span><br>
      <span class="txt-20">证件号码 ：<%=orderVO.getCustomerCertificateNumber()%></span><br>
      <br>
      乙方（综合服务方）:深圳开普乐信息服务有限公司<br>
      住所: <br>
      <br>
      &nbsp;&nbsp;&nbsp;&nbsp;鉴于即本协议所述政信通产品（以下简称“本产品”）对应的基础资产为已备案的定向融资工具产品，基本情况详见《产品说明概要》；本协议中的乙方，根据综合服务协议为本产品提供综合服务，并向甲方推荐本产品；本协议中的甲方，是本协议所述产品的投资人，并已完成在德恒普惠注册开户手续，符合《投资者适当性管理暂行办法》的规定，具有投资本产品的所有合法权利、授权及批准，其购买本产品的资金来源及用途合法。<br>
      甲、乙双方达成如下协议(下称“本协议”)：<br>
      1&nbsp;&nbsp;关于本产品的基本情况，详见《产品说明概要》。甲、乙双方签署本协议，视作同意按照本产品《产品说明概要》各相关条款执行。<br>
      2&nbsp;&nbsp;甲方认购本产品，视作已作出如下承诺：<br>
      （1）&nbsp;&nbsp;知晓本产品中金融资产交易中心仅负责该产品的注册（备案）、登记和结算，不对本产品的投资价值和投资风险承担任何责任。<br>
      （2）&nbsp;&nbsp;接受《产品说明概要》对本产品项下权利义务的所有规定并受其约束；<br>
      （3）&nbsp;&nbsp;本产品的发行人依有关法律、法规的规定发生合法变更，在经有关主管部门批准后并依法就该等变更进行信息披露时，投资者同意并接受该等变更；<br>
      （4）&nbsp;&nbsp;本产品的增信机构依有关法律、法规的规定发生合法变更，在经有关主管部门批准后并依法就该等变更进行信息披露时，投资者同意并接受该等变更。<br>
      3&nbsp;&nbsp;甲方指定以下账户，用于本产品认购和接收到期本息兑付：<br>
      <span class="txt-20">户名：<%=orderVO.getCustomerName()%></span><br>
      <span class="txt-20">开户行：<%=orderVO.getBankName()%></span><br>
      <span class="txt-20">银行账号：<%=orderVO.getBankNumber()%></span><br>
      4&nbsp;&nbsp;甲方签署本协议并在本协议规定的期限内支付认购本产品所对应金额后认购完成（认购信息详见产品认购信息表），收益起算日为成功认购后的第二个工作日。<br>
      5&nbsp;&nbsp;在本产品到期兑付日当日，发行人将应兑付本金及收益足额划转至产品募集专户。在本产品到期兑付日后3个工作日内，乙方将上述款项足额划转至甲方认购产品的资金账户上（账户信息见本协议第3条），进行到期偿付。<br>
      6&nbsp;&nbsp;由于非人力可控制原因或第三方原因，如技术条件限制、通讯线路故障、工作未及时衔接等原因，导致认购金额或交付本产品不能按照约定条件履行时，遇有上述情形的一方不承担违约或赔偿责任，但应在知晓上述情况后立即将情况通知对方。<br>
      7&nbsp;&nbsp;陈述与保证<br>
      7.1&nbsp;&nbsp;甲、乙双方均保证其为合法设立并有效存续的法人或具有完全民事行为能力的自然人，有权签署并履行本协议。<br>
      7.2&nbsp;&nbsp;甲方向乙方作出如下陈述与保证：<br>
      7.2.1&nbsp;&nbsp;甲方本次认购行为以及交易款项来源均符合有关法律法规等规范性文件的规定, 包括但不限于有关反洗钱等方面法律法规和规范性文件的规定。<br>
      甲方确认其清楚并愿意严格遵守中华人民共和国反贪污受贿等法律法规及规范性文件的规定，承诺在签订并履行本协议时，廉洁自律，不接受不当利益。<br>
      7.2.2&nbsp;&nbsp;甲方符合《投资者适当性管理暂行办法》的规定，承诺有足够的专业胜任能力对本产品的购买作独立的投资分析及决定。 <br>
      7.2.3&nbsp;&nbsp;甲方已认真阅读本产品的《产品说明概要》及其他有关的信息披露文件，其认购本产品是在完全知悉和充分考虑本产品投资风险的基础上，作出的独立投资判断。<br>
      7.2.4&nbsp;&nbsp;甲方通过网络页面点击确认的方式选择接受本协议，即表示同意接受本协议的全部约定内容以及与本协议有关的各项规则和流程。<br>
      7.3&nbsp;&nbsp; 甲方承诺，知晓本产品的发行完全是一种市场行为，本产品不存在优先受偿权，若未来出现发行方未能及时兑付本产品本金及收益等情形，风险由投资人自行承担，与乙方无关。<br>
      8&nbsp;&nbsp;不可抗力<br>
      如发生不可抗力事件，遭受该事件的一方应立即通知对方，并在十五天内提供书面证明文件说明有关事件的细节和不能履行或部分不能履行或需延迟履行本协议的原因，然后由各方协商是否延期履行本协议或终止本协议。<br>
      “不可抗力”是指本协议各方的注册地、营业地或本协议履行地发生对其不能预见、不能避免和不能克服的事件，该事件妨碍、影响或延误任何一方根据本协议履行其全部或部分义务。该事件包括但不限于：<br>
      （1）地震、台风、水灾、火灾、战争等不可抗力之情况；<br>
      （2）新的法律、法规的颁布、实施和现行法律、法规的修改或有权机构对现行法律、法规的解释的变动；<br>
      （3）国家的政治、经济、金融等状况的重大变化。<br>
      9&nbsp;&nbsp;仲裁或其他争议解决机制<br>
      对因本产品发行、付息、兑付等其他有关事项的解释和履行发生的争议，以及履行本协议而产生或与本协议有关的任何争议，各方应首先通过协商解决。若协商未成，则可向乙方所在地有管辖权的人民法院提起诉讼。<br>
      10&nbsp;&nbsp;其它<br>
      10.1&nbsp;&nbsp;本协议适用中华人民共和国法律并从其解释。<br>
      10.2&nbsp;&nbsp;本协议附件、补充协议与本协议具有同等法律效力。<br>
      
      <table width="100%" class="gridtable"><tr>
        <td colspan="4">产品认购信息表</td>
        </tr>
        <tr>
          <td colspan="4">产品信息</td>
        </tr>
        <tr>
          <td>产品名称</td>
          <td colspan="3"><%=orderVO.getProductionName()%></td>
        </tr>
        <tr>
          <td>投资期限</td>
          <td colspan="3"><%=orderVO.getInvestTermView()%></td>
        </tr>
        <tr>
          <td>所属项目</td>
          <td><%=orderVO.getProjectName()%></td>
          <td>预期年化收益率</td>
          <td><%=orderVO.getProductionCompositionName()%></td>
        </tr>
        <tr>
          <td colspan="4">客户信息</td>
        </tr>
        <tr>
          <td>姓名</td>
          <td colspan="3"><%=orderVO.getCustomerName()%></td>
        </tr>
        <tr>
          <td>证件号</td>
          <td colspan="3"><%=orderVO.getCustomerCertificateNumber()%></td>
        </tr>
        <tr>
          <td colspan="4">订单信息</td>
        </tr>
        <tr>
          <td>订单号</td>
          <td colspan="3"><%=orderVO.getPayCode()%></td>
        </tr>
        <tr>
          <td>投资金额</td>
          <td><%=orderVO.getMoneyString()%></td>
          <td>支付金额</td>
          <td><%=orderVO.getMoneyString()%></td>
        </tr>
        <tr>
          <td>投资时间</td>
          <td><%=orderVO.getPayDate()%></td>
          <td>起息日</td>
          <td><%=orderVO.getValueDate()%></td>
        </tr>
        <tr>
          <td>备注</td>
          <td colspan="3">1.上述币种均指人民币；2.每年度为365个自然日。</td>
        </tr>
      </table>



    <%--<span style="font-size: 20px; color:darkred;">支付码为【{{payCode}}】</span><br>--%>
      <%--请用PC电脑，访问网址 <br>--%>
      <%--<span style="font-size: 20px; color:darkblue;">http://pay.dianjinpai.com</span><br>--%>
      <%--便可以通过U盾网银方式完成支付。<br>--%>
      <%--注意：此支付码仅在15分钟内有效。--%>
    </div>

      <div class="content-block">
        &nbsp;
      </div>

  </div>

</div>