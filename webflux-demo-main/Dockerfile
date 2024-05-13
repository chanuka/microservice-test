FROM openjdk:17-oracle
ADD target/inventory.jar inventory.jar
ADD logback-spring.xml /opt/conf/inventory/logback-spring.xml
EXPOSE 8094
ENTRYPOINT ["java", "-jar", "inventory.jar"]