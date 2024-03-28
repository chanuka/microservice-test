FROM openjdk:17-oracle
ADD target/eureka.jar eureka.jar
ADD logback-spring.xml /opt/conf/eureka/logback-spring.xml
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "eureka.jar"]