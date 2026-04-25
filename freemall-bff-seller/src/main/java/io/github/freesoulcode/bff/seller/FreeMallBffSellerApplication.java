package io.github.freesoulcode.bff.seller;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FreeMallBffSellerApplication {
    public static void main(String[] args) {
        SpringApplication.run(FreeMallBffSellerApplication.class, args);
    }
}
