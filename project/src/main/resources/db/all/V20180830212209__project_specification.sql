DROP TABLE day_booking;
CREATE TABLE day_booking (
  id UUID NOT NULL PRIMARY KEY,
  date DATE NOT NULL,
  project_id UUID NOT NULL,
  hours NUMERIC(12,2) NOT NULL
);
DROP TABLE work_order;

CREATE INDEX i_day_booking_date ON day_booking(date);

DROP TABLE project_year_budget;
DROP TABLE project;
CREATE TABLE project (
  id UUID NOT NULL PRIMARY KEY,
  name TEXT NOT NULL UNIQUE,
  hours_spent NUMERIC(12,2) NOT NULL DEFAULT 0,
  budget NUMERIC(12,2) NOT NULL
);

CREATE TABLE project_specification (
  id UUID NOT NULL PRIMARY KEY,
  project_id UUID NOT NULL,
  CONSTRAINT fk_project_spec_project FOREIGN KEY(project_id) references project(id)
);

CREATE TABLE work_order_matching_rule (
  id UUID NOT NULL PRIMARY KEY,
  project_specification_id UUID NOT NULL,
  work_order TEXT NOT NULL,
  CONSTRAINT fk_matching_rule_spec FOREIGN KEY(project_specification_id) references project_specification(id)
);

ALTER TABLE day_booking ADD CONSTRAINT fk_day_booking_project FOREIGN KEY (project_id) REFERENCES project(id);