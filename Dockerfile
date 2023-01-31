FROM openjdk:11
EXPOSE 8090
ADD target/booking-intigration booking-intigration.jar
ENTRYPOINT ["java","-jar","/booking-intigration.jar"]
