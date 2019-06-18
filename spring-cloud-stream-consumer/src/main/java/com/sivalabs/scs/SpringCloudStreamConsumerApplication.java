package com.sivalabs.scs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(Endpoints.class)
public class SpringCloudStreamConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamConsumerApplication.class, args);
    }

}
