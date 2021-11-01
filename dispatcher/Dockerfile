FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/dispatcher-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app
COPY ${JAR_FILE} dispatcher.jar
ENTRYPOINT ["java", "-jar", "dispatcher.jar"]

#Далее в командной строке необходимо ввести:
#docker run -p 8090:8090 6c8a153530ff
#где "6c8a153530ff" ID образа
#https://java-master.com/docker-%D0%BE%D1%81%D0%BD%D0%BE%D0%B2%D1%8B/