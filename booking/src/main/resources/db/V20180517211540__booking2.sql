DROP TABLE booking;
CREATE TABLE booking (
  id UUID NOT NULL PRIMARY KEY,
  workorder TEXT NOT NULL,
  description TEXT NOT NULL,
  employee TEXT NOT NULL,
  hours NUMERIC(4,2) NOT NULL,
  date DATE NOT NULL
);