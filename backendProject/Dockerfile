
FROM openjdk:21-jdk
WORKDIR /app
COPY target/alm14-0.0.1-SNAPSHOT.jar /app/alm14-0.0.1-SNAPSHOT.jar
ENV SPRING_PROFILES_ACTIVE=mysql
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "/app/alm14-0.0.1-SNAPSHOT.jar"]
