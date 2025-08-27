FROM amazoncorretto:17
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY ./Api/target/Api-0.0.1.jar app.jar
RUN ls -a
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]

