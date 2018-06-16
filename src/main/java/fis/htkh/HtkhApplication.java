package fis.htkh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HtkhApplication {

    public static void main(String[] args) {
        SpringApplication.run(HtkhApplication.class, args);
    }
}
