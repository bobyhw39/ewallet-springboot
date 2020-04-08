package com.enigma.seventhmarch;

import com.enigma.seventhmarch.properties.AccountProperties;
import com.enigma.seventhmarch.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties({
        AccountProperties.class
})
public class SeventhmarchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeventhmarchApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(AccountRepository accountRepository) {
        return (args) -> {
            System.out.println("Its running m8 :D");
        };
    }
}
