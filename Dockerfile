FROM openjdk:8-jre-alpine
ADD target/secret-demo-0.0.1-SNAPSHOT.jar secret-demo-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar","-Xms150M","-Xmx250M", "secret-demo-0.0.1-SNAPSHOT.jar"]