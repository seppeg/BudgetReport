CREATE TABLE project (
  id BIGINT NOT NULL PRIMARY KEY,
  workorder TEXT NOT NULL,
  description TEXT NOT NULL,
  hours_spent NUMERIC(12,2) NOT NULL,
  budget NUMERIC(12,2) NOT NULL
);