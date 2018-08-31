CREATE MATERIALIZED VIEW monthly_project_bookings AS (
  select project, year_month, max(sum_month) as hours
  from (
         select p.name as project,
           to_date(extract(year from date)||'-'||extract(month from date), 'YYYY-MM') as year_month,
           sum(hours) over (partition by project_id, extract(month from date), extract(year from date) order by extract(year from date), extract(month from date)) as sum_month
         from day_booking db
           join project p on p.id = db.project_id
       ) a
  group by project, year_month
);

CREATE INDEX i_monthly_booking_year_month ON monthly_project_bookings(year_month);
CREATE INDEX i_monthly_booking_project ON monthly_project_bookings(project);
CREATE INDEX i_monthly_booking_project_year_month ON monthly_project_bookings(project, year_month);