# BudgetReport

## Configuration
Create a credentials.env file in booking, project and camis-connection. 
The databases for booking and project are created inside docker so feel free to choose any user/passwords.
The database for camis is not, valid credentials must be specified.

**Booking**
* SPRING_DATASOURCE_USERNAME=aaa
* SPRING_DATASOURCE_PASSWORD=bbb
* POSTGRES_USER=aaa
* POSTGRES_PASSWORD=bbb

**Project**
* SPRING_DATASOURCE_USERNAME=ccc
* SPRING_DATASOURCE_PASSWORD=ddd
* POSTGRES_USER=ccc
* POSTGRES_PASSWORD=ddd

**Camis-connection**
* SPRING_DATASOURCE_USERNAME=eee
* SPRING_DATASOURCE_PASSWORD=fff

### DNS
You may need to add following hosts to /etc/hosts 
unless your dns resolver will already resolve wildcards for localhost.

* 127.0.0.1 booking.localhost
* 127.0.0.1 project.localhost
* 127.0.0.1 camis.localhost


## Build & Run:
* ./gradlew ngBuildProd
* ./gradlew build
* docker-compose build
* docker-compose up -d