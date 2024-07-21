FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ./target/Api-0.0.1.jar app.jar
EXPOSE 8080
EXPOSE 8888
ENTRYPOINT ["java","-jar","/app.jar"]

