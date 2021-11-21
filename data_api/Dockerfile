FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/data_api-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app
COPY ${JAR_FILE} data_api.jar
ENTRYPOINT ["java", "-jar", "data_api.jar"]