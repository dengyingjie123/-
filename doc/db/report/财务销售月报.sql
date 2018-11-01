-- 1.0
set @this_year = '2018';
set @this_month = '08';
set @this_year_begin_time = CONCAT(@this_year,'-01-01 00:00:00');
set @this_month_begin_time = CONCAT(@this_year,'-',@this_month,'-01 00:00:00');
set @this_month_end_time = DATE_ADD(DATE_ADD(@this_month_begin_time, INTERVAL 1 MONTH), INTERVAL -1 SECOND);
set @this_month_end_time = '2018-08-17 23:59:59';
set @last_year_begin_time = '2014-01-01 00:00:00';
set @last_year_end_time = DATE_ADD(CONCAT(@this_year,'-01-01 00:00:00'), INTERVAL -1 SECOND);
set @last_month_begin_time = DATE_ADD(@this_month_begin_time, INTERVAL -1 MONTH);
set @last_month_end_time = DATE_ADD(@this_month_begin_time, INTERVAL -1 SECOND);

select t.GroupName, sum(t.`期末存量`) '期末存量' from (

SELECT
    s.GroupName, s.`NAME`,
    (
        SELECT
                IFNULL(sum(o.money),0)/10000
        FROM
                view_order o
        WHERE
                1 = 1
        AND o.`status` not in (0)
        -- 未确认状态
        and (
        -- 未兑付情况，包含全部在月底之前创建的订单
                (o.paymentPlanStatus not in (5) and o.PayTime<=@last_year_end_time) or
        -- 已兑付情况，兑付日期为开始时间之前与当前时间之间
                (o.paymentPlanStatus in (5) and o.paymentPlanLastTime>=@last_year_end_time and o.paymentPlanLastTime<=now())
        )
        and o.salesmanId=s.id
    ) '年初存量',

        (
        SELECT
                IFNULL(sum(o.money * p.discountRate),0)/10000
        FROM
                view_order o, crm_production p
        WHERE
                1 = 1
        AND o.`status` not in (0) and  p.state=0 and o.ProductionId=p.id
        -- 未确认状态
        and (
        -- 未兑付情况，包含全部在月底之前创建的订单
                (o.paymentPlanStatus not in (5) and o.PayTime<=@last_year_end_time) or
        -- 已兑付情况，兑付日期为开始时间之前与当前时间之间
                (o.paymentPlanStatus in (5) and o.paymentPlanLastTime>=@last_year_end_time and o.paymentPlanLastTime<=now())
        )
        and o.salesmanId=s.id
    ) '年初折标存量',

        (
        SELECT
                IFNULL(sum(o.money),0)/10000
        FROM
                view_order o
        WHERE
                1 = 1
        AND o.`status` not in (0)
        -- 未确认状态
        and (
        -- 未兑付情况，包含全部在月底之前创建的订单
                (o.paymentPlanStatus not in (5) and o.PayTime<=@last_month_end_time) or
        -- 已兑付情况，兑付日期为开始时间之前与当前时间之间
                (o.paymentPlanStatus in (5) and o.paymentPlanLastTime>=@last_month_end_time and o.paymentPlanLastTime<=now())
        )
        and o.salesmanId=s.id
    ) '月初存量',
        
        (
        SELECT
                IFNULL(sum(o.money * p.discountRate),0)/10000
        FROM
                view_order o, crm_production p
        WHERE
                1 = 1
        AND o.`status` not in (0) and  p.state=0 and o.ProductionId=p.id
        -- 未确认状态
        and (
        -- 未兑付情况，包含全部在月底之前创建的订单
                (o.paymentPlanStatus not in (5) and o.PayTime<=@last_month_end_time) or
        -- 已兑付情况，兑付日期为开始时间之前与当前时间之间
                (o.paymentPlanStatus in (5) and o.paymentPlanLastTime>=@last_month_end_time and o.paymentPlanLastTime<=now())
        )
        and o.salesmanId=s.id
    )'月初折标存量',


        (
        SELECT
                count(DISTINCT o.CustomerId)
        FROM
                view_order o
        WHERE
                1 = 1
        AND o.`status` not in (0)
        -- 未确认状态
        and (
        -- 未兑付情况，包含全部在月底之前创建的订单
                (o.paymentPlanStatus not in (5) and o.PayTime<=@last_month_end_time) or
        -- 已兑付情况，兑付日期为开始时间之前与当前时间之间
                (o.paymentPlanStatus in (5) and o.paymentPlanLastTime>=@last_month_end_time and o.paymentPlanLastTime<=now())
        )
        and o.salesmanId=s.id
    ) '存量客户数',

        (
        SELECT
                count(DISTINCT o.CustomerId)
        FROM
                view_order o, view_customer c
        WHERE
                o.CustomerId=c.id
        AND o.`status` not in (0)
        -- 未确认状态
        and o.PayTime>=@this_month_begin_time and o.PayTime<=@this_month_end_time
        and c.CreateTime>=@this_month_begin_time and c.CreateTime<=@this_month_end_time
        and o.salesmanId=s.id
    ) '新客户数',
        
        (
        SELECT
                IFNULL(sum(o.money),0)/10000
        FROM
                view_order o
        WHERE
                1=1
        AND o.`status` not in (0)
        -- 未确认状态
        and o.PayTime>=@this_month_begin_time and o.PayTime<=@this_month_end_time
        and o.salesmanId=s.id
    )'本月募集',


        (
        SELECT
                IFNULL(sum(o.money * p.discountRate),0)/10000
        FROM
                view_order o, crm_production p
        WHERE
                p.state=0
        AND o.`status` not in (0) and o.ProductionId=p.id
        -- 未确认状态
        and o.PayTime>=@this_month_begin_time and o.PayTime<=@this_month_end_time
        and o.salesmanId=s.id
    ) '本月募集折标',

        (
        SELECT
                IFNULL(sum(o.money),0)/10000
        FROM
                view_order o
        WHERE
                1=1
        AND o.`status` not in (0)
        AND o.paymentPlanStatus in (5)
        -- 未确认状态
        and o.paymentPlanLastTime>=@this_month_begin_time and o.paymentPlanLastTime<=@this_month_end_time
        and o.salesmanId=s.id
    ) '本月兑付',
        
        (
        SELECT
                IFNULL(sum(o.money * p.discountRate),0)/10000
        FROM
                view_order o, crm_production p
        WHERE
                p.state=0
        AND o.`status` not in (0)
        AND o.paymentPlanStatus in (5) and o.ProductionId=p.id
        -- 未确认状态
        and o.paymentPlanLastTime>=@this_month_begin_time and o.paymentPlanLastTime<=@this_month_end_time
        and o.salesmanId=s.id
    ) '本月兑付折标',
        0 '本月净增',
        0 '本月净增折标',
        (
        SELECT
                IFNULL(sum(o.money),0)/10000
        FROM
                view_order o
        WHERE
                1 = 1
        AND o.`status` not in (0)
        -- 未确认状态
        and (
        -- 未兑付情况，包含全部在月底之前创建的订单
                (o.paymentPlanStatus not in (5) and o.PayTime<=@this_month_end_time) or
        -- 已兑付情况，兑付日期为开始时间之前与当前时间之间
                (o.paymentPlanStatus in (5) and o.paymentPlanLastTime>=@this_month_end_time and o.paymentPlanLastTime<=now())
        )
        and o.salesmanId=s.id
    ) '期末存量',
        
        (
        SELECT
                IFNULL(sum(o.money * p.discountRate),0)/10000
        FROM
                view_order o, crm_production p
        WHERE
                1 = 1
        AND o.`status` not in (0) and p.state=0 and o.ProductionId=p.id
        -- 未确认状态
        and (
        -- 未兑付情况，包含全部在月底之前创建的订单
                (o.paymentPlanStatus not in (5) and o.PayTime<=@this_month_end_time) or
        -- 已兑付情况，兑付日期为开始时间之前与当前时间之间
                (o.paymentPlanStatus in (5) and o.paymentPlanLastTime>=@this_month_end_time and o.paymentPlanLastTime<=now())
        )
        and o.salesmanId=s.id
    ) '期末存量折标'
FROM
    view_salesman s
ORDER BY s.groupname, s.`NAME`
) t
GROUP BY t.GroupName
;

select @this_month_end_time;