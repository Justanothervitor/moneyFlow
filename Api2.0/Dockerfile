FROM docker.io/amazoncorretto:17-alpine
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY target/Api-0.0.1-SNAPSHOT.jar app.jar
RUN ls -a
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar", "-Dspring.config.additional-locaiton=/opt/moneyflow/myapp/conf/","/opt/moneyflow/myapp/app.jar"]