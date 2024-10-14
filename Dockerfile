FROM openjdk:21
WORKDIR /app
COPY target/menudigital-0.0.1.jar /app/menudigital-0.0.1.jar
ENTRYPOINT ["java", "-jar", "menudigital-0.0.1.jar"]