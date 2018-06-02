CREATE INDEX i_day_booking_work_order ON day_booking(work_order);
ALTER TABLE day_booking ADD CONSTRAINT uq_date_work_order UNIQUE (date, work_order);

CREATE MATERIALIZED VIEW monthly_work_order_bookings AS (
  select work_order, year, month, max(sum_month) as hours
  from (
         select work_order,
           extract(month from date) as month,
           extract(year from date) as year,
           sum(hours) over (partition by work_order, extract(month from date), extract(year from date) order by extract(year from date), extract(month from date)) as sum_month
         from day_booking db
       ) a
  group by work_order, year, month
);
REFRESH MATERIALIZED VIEW monthly_work_order_bookings;

CREATE INDEX i_monthly_booking_year ON monthly_work_order_bookings(year);
CREATE INDEX i_monthly_booking_work_order ON monthly_work_order_bookings(work_order);
CREATE INDEX i_monthly_booking_work_order_year ON monthly_work_order_bookings(work_order, year);