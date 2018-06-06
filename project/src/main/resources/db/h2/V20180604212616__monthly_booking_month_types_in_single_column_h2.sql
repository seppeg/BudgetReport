DROP TABLE monthly_work_order_bookings;

CREATE TABLE monthly_work_order_bookings (
  work_order TEXT NOT NULL,
  year_month DATE NOT NULL,
  hours NUMERIC(12,2) NOT NULL
);