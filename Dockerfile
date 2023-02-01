FROM openjdk:11
EXPOSE 8090
ADD target/*.jar book.jar
ENTRYPOINT ["java", "-jar","/book.jar"]
