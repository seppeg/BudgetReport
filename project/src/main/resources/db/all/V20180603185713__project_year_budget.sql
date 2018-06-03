CREATE TABLE project_year_budget (
  id UUID NOT NULL PRIMARY KEY,
  project_id UUID NOT NULL,
  year INT NOT NULL,
  budget NUMERIC(12,2) NOT NULL,
  CONSTRAINT fk_project_budget FOREIGN KEY(project_id) references project(id)
);