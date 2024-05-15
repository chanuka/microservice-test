package com.cba.core.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Value("${DB_URL}")
    private String dbUrl;

    @Value("${DB_USER}")
    private String dbUsername;

    @Value("${DB_PASSWORD}")
    private String encryptedPassword;

    @Bean
    public DataSource dataSource() {

        System.out.println("****" + DecryptionUtility.encrypt("WPTSAPN"));

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver"); // Replace with your database driver
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(decryptedPassword());
        return dataSource;
    }


    // This method will fetch the decrypted password
    private String decryptedPassword() {
        return DecryptionUtility.decrypt(encryptedPassword);
    }
}
