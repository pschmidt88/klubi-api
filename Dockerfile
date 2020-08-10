FROM openjdk:14-alpine
COPY build/libs/klubi-api-*-all.jar klubi-api.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "klubi-api.jar"]