FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/dispatcher-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app
COPY ${JAR_FILE} dispatcher.jar
ENTRYPOINT ["java", "-jar", "dispatcher.jar"]
