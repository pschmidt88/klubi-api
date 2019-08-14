FROM gradle:5.5-jdk8 AS BUILD

ADD . .
ADD gradle.properties /home/gradle/.gradle/

RUN gradle clean shadowJar

FROM java:8-jre-alpine

COPY --from=BUILD /home/gradle/build/libs/api.jar /app/

CMD ["/usr/bin/java", "-jar", "/app/klubi-api.jar", "server", "/app/config/default.yaml"]
