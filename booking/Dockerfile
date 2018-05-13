FROM openjdk:9.0.4-12-jdk-slim-sid
COPY build/libs/booking-1.0-SNAPSHOT.jar .
CMD /usr/bin/java -Xmx400m -Xms400m -jar booking-1.0-SNAPSHOT.jar
EXPOSE 8081