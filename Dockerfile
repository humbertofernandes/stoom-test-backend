FROM openjdk:15-jdk-alpine3.11
LABEL maintainer="humbertotpgfernandes@gmail.com"

ENV LANG C.UTF-8

RUN apk add --update bash

ADD target/stoom-api-1.0.0-SNAPSHOT.jar /app/app.jar

CMD java -jar /app/app.jar