
FROM postgres:10.4

RUN apt update && apt -y install postgresql-contrib
COPY init.sql /docker-entrypoint-initdb.d/