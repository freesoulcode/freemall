package io.github.freesoulcode.merchant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "io.github.freesoulcode")
@EnableFeignClients
public class FreeMallMerchantApplication {
    public static void main(String[] args) {
        SpringApplication.run(FreeMallMerchantApplication.class, args);
    }
}
