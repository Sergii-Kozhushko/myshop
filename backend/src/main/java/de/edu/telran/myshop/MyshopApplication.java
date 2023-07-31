package de.edu.telran.myshop;

import de.edu.telran.myshop.security.SpringSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EnableTransactionManagement
//@ComponentScan(basePackages = {"de.edu.telran.myshop"})
//@RefreshScope

public class MyshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyshopApplication.class, args);
    }

}
