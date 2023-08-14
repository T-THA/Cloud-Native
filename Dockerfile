FROM openjdk:17

LABEL authors="nju17"

COPY ./release/Cloud-Native-0.0.1-SNAPSHOT.jar /app/Cloud-Native.jar

WORKDIR /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "Cloud-Native.jar"]