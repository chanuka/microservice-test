FROM openjdk:17-oracle
ADD target/order.jar order.jar
ADD logback-spring.xml /opt/conf/order/logback-spring.xml
EXPOSE 8092
ENTRYPOINT ["java", "-jar", "order.jar"]