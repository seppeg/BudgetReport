CREATE MATERIALIZED VIEW yearly_work_order_bookings AS (
  select work_order, year, max(sum_year) as hours
  from (
         select w.work_order,
           extract(year from date) as year,
           sum(hours) over (partition by work_order, extract(year from date) order by extract(year from date)) as sum_year
         from day_booking db
           join work_order w on w.id = db.work_order_id
       ) a
  group by work_order, year
);

CREATE INDEX i_yearly_booking_year ON yearly_work_order_bookings(year);
CREATE INDEX i_yearly_booking_work_order ON yearly_work_order_bookings(work_order);
CREATE INDEX i_yearly_booking_work_order_year ON yearly_work_order_bookings(work_order, year);