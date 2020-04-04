FROM gradle:6.3-jdk11 AS BUILD

ADD . .
ADD gradle.properties /home/gradle/.gradle/

RUN gradle clean shadowJar

FROM adoptopenjdk:11-jre-hotspot

COPY --from=BUILD /home/gradle/build/libs/api.jar /app/

CMD ["/usr/bin/java", "-jar", "/app/klubi-api.jar", "server", "/app/config/default.yaml"]
