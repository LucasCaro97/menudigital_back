FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/menudigital-0.0.1.jar /app/menudigital-0.0.1.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "menudigital-0.0.1.jar"]