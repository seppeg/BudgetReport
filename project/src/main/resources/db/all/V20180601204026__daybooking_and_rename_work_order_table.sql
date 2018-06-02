ALTER TABLE workorder RENAME COLUMN workorder TO work_order;
ALTER TABLE workorder RENAME TO work_order;

CREATE TABLE day_booking (
  id UUID NOT NULL PRIMARY KEY,
  date DATE NOT NULL,
  work_order TEXT NOT NULL,
  hours NUMERIC(12,2) NOT NULL
);

CREATE INDEX i_day_booking_date ON day_booking(date);
