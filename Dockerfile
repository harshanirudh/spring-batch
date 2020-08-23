FROM openjdk:8
EXPOSE 8080
ADD target/spring-batch-0.0.1-SNAPSHOT.jar spring-batch-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/spring-batch-0.0.1-SNAPSHOT.jar"]
