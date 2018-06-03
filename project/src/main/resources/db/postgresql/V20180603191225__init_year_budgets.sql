INSERT INTO project_year_budget (id, project_id, year, budget) SELECT uuid_generate_v4(), p.id, 2018, p.budget FROM project p;
