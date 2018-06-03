DROP MATERIALIZED VIEW monthly_work_order_bookings;

ALTER TABLE day_booking DROP COLUMN work_order;

CREATE MATERIALIZED VIEW monthly_work_order_bookings AS (
  select work_order, year, month, max(sum_month) as hours
  from (
    select w.work_order,
      extract(month from date) as month,
      extract(year from date) as year,
      sum(hours) over (partition by work_order_id, extract(month from date), extract(year from date) order by extract(year from date), extract(month from date)) as sum_month
    from day_booking db
    join work_order w on w.id = db.work_order_id
  ) a
  group by work_order, year, month
);

CREATE INDEX i_monthly_booking_year ON monthly_work_order_bookings(year);
CREATE INDEX i_monthly_booking_work_order ON monthly_work_order_bookings(work_order);
CREATE INDEX i_monthly_booking_work_order_year ON monthly_work_order_bookings(work_order, year);