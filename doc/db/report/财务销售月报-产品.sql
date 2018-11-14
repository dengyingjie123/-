set @this_year = '2018';
set @this_month = '07';
set @this_year_begin_time = CONCAT(@this_year,'-01-01 00:00:00');
set @this_month_begin_time = CONCAT(@this_year,'-',@this_month,'-01 00:00:00');
set @this_month_end_time = DATE_ADD(DATE_ADD(@this_month_begin_time, INTERVAL 1 MONTH), INTERVAL -1 SECOND);
set @last_year_begin_time = '2014-01-01 00:00:00';
set @last_year_end_time = DATE_ADD(CONCAT(@this_year,'-01-01 00:00:00'), INTERVAL -1 SECOND);
set @last_month_begin_time = DATE_ADD(@this_month_begin_time, INTERVAL -1 MONTH);
set @last_month_end_time = DATE_ADD(@this_month_begin_time, INTERVAL -1 SECOND);
SELECT
	p.`Name` '产品名称',
-- 年期初存量 =====================================
	(
		SELECT
			IFNULL(sum(o.money), 0)
		FROM
				view_order o
		WHERE
				1 = 1
		AND o.`status` not in (0)

		-- 未确认状态
		and (
		-- 未兑付情况，包含全部在月底之前创建的订单
			(o.`status` in (1,7,8,23,24) and o.PayTime<=@last_year_end_time) or
		-- 已兑付情况，兑付日期为开始时间之前与当前时间之间
				(o.`status`=5 and o.paymentPlanLastTime>=@last_year_end_time and o.paymentPlanLastTime<=now() and o.payTime<=@last_year_end_time)
		)
		and o.productionId=p.id
	) '年期初存量',
-- 年期初存量 结束 =====================================


-- 月期初存量 =====================================
	(
			-- 月底存续金额
		SELECT
				IFNULL(sum(o.money), 0)
		FROM
				view_order o
		WHERE
				1 = 1
		AND o.`status` not in (0)

		-- 未确认状态
		and (
		-- 未兑付情况，包含全部在月底之前创建的订单
				(o.`status` in (1,7,8,23,24) and o.PayTime<=@last_month_end_time) or
		-- 已兑付情况，兑付日期为开始时间之前与当前时间之间
				(o.`status`=5 and o.paymentPlanLastTime>=@last_month_end_time and o.paymentPlanLastTime<=now() and o.payTime<=@last_year_end_time)
		)
		and o.productionId=p.id
	) '月期初存量',
-- 月期初存量 结束 =====================================


-- 客户存量  =====================================
	(
	-- 存量客户
	select
			count(DISTINCT o.CustomerId)
	from
			view_order o
	where 1=1
	and o.`status` != 0
	and o.productionId=p.id
	and o.PayTime<=@this_month_end_time
	) '客户存量',
-- 客户存量 结束 =====================================

-- 本月募集  =====================================
	(
	SELECT
			IFNULL(sum(o.money),0)
	FROM
			view_order o
	WHERE
			1 = 1
	AND o.`status` not in (0)
	AND o.PayTime >= @this_month_begin_time
	AND o.PayTime <= @this_month_end_time
	and o.productionId=p.id

	) '本月募集',
-- 本月募集 结束  =====================================


-- 本月兑付  =====================================
	(
	SELECT
			IFNULL(sum(o.money),0)
	FROM
			view_order o
	WHERE
			1 = 1
	AND o.`status` not in (0)
	AND o.paymentPlanLastTime >= @this_month_begin_time
	AND o.paymentPlanLastTime <= @this_month_end_time
	and o.productionId=p.id

	) '本月兑付',
-- 本月兑付 结束  =====================================


	(
		SELECT
				IFNULL(sum(o.money),0)
		FROM
				view_order o
		WHERE
				1 = 1
		AND o.`status` not in (0)

		-- 未确认状态
		and (
		-- 未兑付情况，包含全部在月底之前创建的订单
				(o.`status` in (1,7,8,23,24) and o.PayTime<=@this_month_end_time) or
		-- 已兑付情况，兑付日期为开始时间之前与当前时间之间
				(o.`status`=5 and o.paymentPlanLastTime>=@this_month_end_time and o.paymentPlanLastTime<=now() and o.PayTime<=@this_month_end_time)
		)
	and o.productionId=p.id

	) '本月底存量',

	p.*
FROM
	crm_production p
WHERE
	p.state = 0
ORDER BY
	p.name
;

select @last_year_end_time, @last_month_begin_time, @last_month_end_time;