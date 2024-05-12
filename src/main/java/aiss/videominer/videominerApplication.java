package aiss.videominer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "aiss.videominer.repository")
@EnableTransactionManagement
@EntityScan(basePackages = "aiss.videominer.model")
@ComponentScan(basePackages = {"aiss.videominer.controller", "aiss.videominer.service"})
public class videominerApplication {

    public static void main(String[] args) {
        SpringApplication.run(videominerApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
