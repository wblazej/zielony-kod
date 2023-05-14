FROM maven:3.6.3-openjdk-17-slim as builder
LABEL maintainer="Błażej Wrzosok <blazej.wrzosok@gmail.com>"

WORKDIR /app

COPY . .

RUN /app/build.sh

# -- 

FROM openjdk:17-alpine as runtime
WORKDIR /app

COPY --from=builder /app/target/ZielonyKod-1.0.0-jar-with-dependencies.jar .

EXPOSE 8080
ENTRYPOINT java $SYS_PROPS -jar ZielonyKod-1.0.0-jar-with-dependencies.jar
