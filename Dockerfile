FROM gradle:jdk14 AS BUILD

ADD . .
ADD gradle.properties /home/gradle/.gradle/

RUN gradle clean shadowJar

FROM openjdk:14-alpine
COPY --from=BUILD /home/gradle/build/libs/klubi-api-*-all.jar klubi-api.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "klubi-api.jar"]