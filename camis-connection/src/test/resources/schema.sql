create schema dbo;

create table dbo.zrapuur1 (
  Werkorder VARCHAR(25),
  Datum DATE,
  Commentaar VARCHAR(255),
  Uren DECIMAL(28,8),
  Medewerker_ID VARCHAR(25),
  Periode int
);