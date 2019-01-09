create or replace view v_customer_saleman as
SELECT
    u.id userId,
    cd.saleManId,
    u.`name` saleManName,
    cd.saleGroupId,
    sg.`Name` saleGroupName,
    c.id, c.PersonalNumber, c.`Name`, c.LoginName, c.Mobile, c.CreateTime, c.state,
    c.id linkCustomerId, '' IdCard,
    cd.`Status` distributionStatus
FROM
    crm_customerpersonal c
left JOIN crm_customerdistribution cd on cd.state=0 and cd.CustomerId=c.id
left JOIN system_user u on u.state=0 and u.id=cd.SaleManId
left join crm_salemangroup sg on sg.state=0 and cd.saleGroupId=sg.id
WHERE
    1 = 1
and c.state=0
UNION all
SELECT
    u.id userId,
    cd.saleManId,
    u.`name` saleManName,
    cd.saleGroupId,
    sg.`Name` saleGroupName,
    c.id, '' PersonalNumber, c.`Name`, '' LoginName, c.Mobile, '' CreateTime, c.state,
    c.id linkCustomerId, '' IdCard,
    cd.`Status` distributionStatus
FROM
    crm_customerinstitution c
left JOIN crm_customerdistribution cd on cd.state=0 and cd.CustomerId=c.id
left JOIN system_user u on u.state=0 and u.id=cd.SaleManId
left join crm_salemangroup sg on sg.state=0 and cd.saleGroupId=sg.id
WHERE
    1 = 1
and c.state=0;

create or replace view view_salesman as 
SELECT
    g. NAME GroupName,
    sg.defaultGroup,
    u.id,
    u. NAME,
    u.mobile,
    u.staffCode,
    CONCAT('S',u.staffCode) referralCode,
    u.`password`
FROM
    system_user u
LEFT JOIN crm_saleman_salemangroup sg ON sg.saleManId = u.Id
LEFT JOIN crm_salemangroup g ON g.state = 0 AND g.id = sg.saleManGroupId
WHERE
    u.state = 0
AND sg.defaultGroup = 1
AND u.`Status` = '9662';



create or replace view view_salesman_customer_distribution as
SELECT
    DISTINCT
    s.id salesmanId,
    s. NAME salesmanName,
    c.id customerId,
    c.`Name` customerName,
    c.CreateTime,
    0 customerType,
    cd.`Status`
FROM
    crm_customerpersonal c
LEFT JOIN crm_customerdistribution cd ON cd.state = 0 AND cd.CustomerId = c.id
LEFT JOIN view_salesman s ON s.Id = cd.SaleManId
WHERE
    1 = 1
AND c.state = 0
-- and c.id in (select o.customerId from crm_order o where o.state=0 and o.`Status` in (1,5,7,8,11,23,24,25) and o.CustomerId=c.id)
AND cd.CustomerId = c.id
AND s.Id = cd.SaleManId
union all
SELECT
    DISTINCT
    s.id salesmanId,
    s. NAME salesmanName,
    c.id customerId,
    c.`Name` customerName,
    c.CreateTime,
    1 customerType,
    cd.`Status`
FROM
    crm_customerinstitution c
LEFT JOIN crm_customerdistribution cd ON cd.state = 0 AND cd.CustomerId = c.id
LEFT JOIN view_salesman s ON s.Id = cd.SaleManId
WHERE
    1 = 1
AND c.state = 0
-- and c.id in (select o.customerId from crm_order o where o.state=0 and o.`Status` in (1,5,7,8,11,23,24,25) and o.CustomerId=c.id)
AND cd.CustomerId = c.id
AND s.Id = cd.SaleManId;


-- ======================================================================
create or replace view v_sale_production as
SELECT
    pc.*,
    spc.commissionRate
FROM
    crm_productioncomposition pc,
    sale_productioncommission spc
WHERE 1=1
    and pc.state = 0
and spc.State=0
and spc.areaCode='0000'
and pc.commissionLevel=spc.commissionLevel;


create or replace view view_order_01 as    
SELECT
    DISTINCT
    -- 所属销售编号
    scd.salesmanId,
    -- 所属销售名称
    scd.salesmanName,
    -- 推荐人编号
    s.id referralSalesmanId,
    -- 推荐人名称
    s.name referralSalesmanName,
    (select count(*) from core_paymentplan plan where plan.state=0 and plan.`Status`='1' and plan.OrderId=o.id) paymentPlanPaidCount,
    (select count(*) from core_paymentplan plan where plan.state=0 and plan.`Status`!='1' and plan.OrderId=o.id) paymentPlanUnpaidCount,
    (select plan.PaymentTime from core_paymentplan plan where plan.state=0 and plan.OrderId=o.id ORDER BY plan.PaymentTime desc limit 1) paymentPlanLastTime,
    o.sid, o.id, o.operatorId, o.operateTime, o.OrderNum, o.CustomerName, o.CustomerId, o.ProductionId, o.ProductionCompositionId,
    o.accountId, o.createtime,
    p.`Name` productionName,
    pc.`Name` productionCompositionName,
    o.Money, o.appointmentTime, o.PayTime, o.contractNo, o.referralCode, o.`Status` status,
    o.orderConfirmUserId01,
    (select name from system_user where state=0 and id=o.orderConfirmUserId01) orderConfirmUserName01,
    o.orderConfirmUserTime01,
    o.orderConfirmUserId02,
    (select name from system_user where state=0 and id=o.orderConfirmUserId02) orderConfirmUserName02,
    o.orderConfirmUserTime02,
    o.financeMoneyConfirm,
    o.financeMoneyConfirmUserId,
    (select name from system_user where state=0 and id=o.financeMoneyConfirmUserId) financeMoneyConfirmUserName,
    o.financeMoneyConfirmTime,
    o.allinpayCircle_deposit_status,	
    o.allinpayCircle_payByShare_status,	
    o.allinpayCircle_payByShare_time
FROM
    crm_order o
left join view_salesman_customer_distribution scd on  scd.customerId=o.CustomerId
left join view_salesman s on s.referralCode=o.referralCode
left join crm_production p on p.state=0 and p.id=o.ProductionId
left join crm_productioncomposition pc on pc.state=0 and pc.id=o.ProductionCompositionId
WHERE
    o.state = 0
and o.`Status` in (0,1,5,7,8,11,23,24,25)
;

create or replace view view_order as
select
    
    -- paymentPlanStatus，订单兑付计划状态，筛选有用状态
    -- 1：未兑付，5：已全部兑付，8：部分兑付
    IF(vo.paymentPlanUnpaidCount>0, if(vo.paymentPlanPaidCount=0, 1, 8), if (vo.paymentPlanPaidCount>0, 5, 1)) as paymentPlanStatus,
    vo.*
from view_order_01 vo
;

-- 正在存续中的订单
create or replace view view_order_open as
select * from view_order where `status` in (1,8,23,24);

-- 已结束的订单，已兑付或已转让
create or replace view view_order_close as
select * from view_order where `status` in (5,11);

create or replace view view_customerpersonal as
select DISTINCT
    cd.salesmanId salemanId,
    cd.salesmanName salemanName,
    sex.v sexName,
    g.id groupId,
    g.`Name` groupName,
    cd.status distributionStatus,
    cc.Number idCardNumber,
    c.*
from crm_customerpersonal c
left join system_kv sex on sex.state=0 and sex.GroupName='Sex' and sex.K=c.Sex
left join view_salesman_customer_distribution cd on cd.customerId=c.id
left join crm_saleman_salemangroup sg on sg.defaultGroup=1 and sg.saleManId=cd.salesmanId
left join CRM_SalemanGroup g on g.state=0 and g.Id=sg.saleManGroupId
left join crm_customercertificate cc on cc.state=0 and cc.CustomerId=c.id
where c.state=0
;


-- 机构客户视图
create or replace view view_customerinstitution as
select DISTINCT
    cd.salesmanId salemanId,
    cd.salesmanName salemanName,
    g.id groupId,
    g.`Name` groupName,
    cd.status distributionStatus,
    cc.Number idCardNumber,
    c.*
from crm_customerinstitution c
left join view_salesman_customer_distribution cd on cd.customerId=c.id
left join crm_saleman_salemangroup sg on sg.defaultGroup=1 and sg.saleManId=cd.salesmanId
left join CRM_SalemanGroup g on g.state=0 and g.Id=sg.saleManGroupId
left join crm_customercertificate cc on cc.state=0 and cc.CustomerId=c.id
where c.state=0
;


create or replace view view_customer as
SELECT
    u.id userId,
    cd.salesmanId,
    u.`name` saleManName,
    sg.Id saleGroupId,
    sg.`Name` saleGroupName,
    c.id, c.PersonalNumber, c.`Name`, c.LoginName, c.Mobile, c.CreateTime, c.state, c.birthday,
    c.id linkCustomerId, '' IdCard,
    cd.`Status` distributionStatus
FROM
    crm_customerpersonal c
left JOIN view_salesman_customer_distribution cd on cd.CustomerId=c.id
left JOIN system_user u on u.state=0 and u.id=cd.salesmanId
Left join crm_saleman_salemangroup ssg on ssg.saleManId=cd.salesmanId and ssg.defaultGroup=1
left join crm_salemangroup sg on sg.state=0 and sg.id=ssg.saleManGroupId
WHERE
    1 = 1
and c.state=0
UNION all
SELECT
    u.id userId,
    cd.salesmanId,
    u.`name` saleManName,
    sg.id saleGroupId,
    sg.`Name` saleGroupName,
    c.id, '' PersonalNumber, c.`Name`, '' LoginName, c.Mobile, '' CreateTime, c.state, '' birthday,
    c.id linkCustomerId, '' IdCard,
    cd.`Status` distributionStatus
FROM
    crm_customerinstitution c
left JOIN view_salesman_customer_distribution cd on cd.CustomerId=c.id
left JOIN system_user u on u.state=0 and u.id=cd.salesmanId
Left join crm_saleman_salemangroup ssg on ssg.saleManId=cd.salesmanId and ssg.defaultGroup=1
left join crm_salemangroup sg on sg.state=0 and sg.id=ssg.saleManGroupId
WHERE
    1 = 1
and c.state=0 