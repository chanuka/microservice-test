#FROM alpine:3.15.4

#ARG JAR_FILE

## Installs JRE (ignores: Pin versions in apk add)
## hadolint ignore=DL3018
#RUN apk --no-cache add openjdk8-jre && \
## Add group and user
    #addgroup app && adduser -G app -s /bin/sh -D app

#COPY --chown=app:app target/${JAR_FILE} /home/app/app.jar

#EXPOSE 8080
#USER app
#WORKDIR /home/app
#ENTRYPOINT ["java", "-jar", "app.jar"]



FROM openjdk:17-oracle
ADD target/auth-service.jar auth-service.jar
ENV DB_HOST=host.docker.internal
ENV DB_PORT=3306
ENV DB_NAME=haradb_new
ENV DB_USER=root
ENV DB_PASSWORD=password
EXPOSE 8088
ENTRYPOINT ["java", "-jar", "auth-service.jar"]