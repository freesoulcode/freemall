package io.github.freesoulcode.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "io.github.freesoulcode")
@EnableFeignClients
public class FreeMallProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(FreeMallProductApplication.class, args);
    }
}
