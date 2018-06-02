CREATE TABLE monthly_work_order_bookings (
  work_order TEXT NOT NULL,
  year INT NOT NULL,
  month INT NOT NULL,
  hours NUMERIC(12,2) NOT NULL
);