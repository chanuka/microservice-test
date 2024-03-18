FROM openjdk:17-oracle
ADD target/product.jar product.jar
ADD logback-spring.xml /opt/conf/product/logback-spring.xml
EXPOSE 8093
ENTRYPOINT ["java", "-jar", "product.jar"]