FROM openjdk:11
EXPOSE 8090
ADD target/Booking-0.0.1-SNAPSHOT Booking-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/Booking-0.0.1-SNAPSHOT.jar"]