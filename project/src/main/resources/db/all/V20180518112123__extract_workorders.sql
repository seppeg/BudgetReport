DROP TABLE project;
CREATE TABLE project (
  id UUID NOT NULL PRIMARY KEY,
  description TEXT NOT NULL,
  hours_spent NUMERIC(12,2) NOT NULL,
  budget NUMERIC(12,2) NOT NULL
);

CREATE TABLE workorder (
  id UUID NOT NULL,
  workorder TEXT NOT NULL,
  project_id UUID NOT NULL,
  CONSTRAINT fk_workorder_project FOREIGN KEY (project_id) references project(id)
);