FROM openjdk:17-oracle
ADD target/gateway.jar gateway.jar
ADD logback-spring.xml /opt/conf/gateway/logback-spring.xml
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "gateway.jar"]