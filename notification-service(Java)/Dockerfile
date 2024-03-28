FROM openjdk:17-oracle
ADD target/notification.jar notification.jar
ADD logback-spring.xml /opt/conf/notification/logback-spring.xml
EXPOSE 8091
ENTRYPOINT ["java", "-jar", "notification.jar"]