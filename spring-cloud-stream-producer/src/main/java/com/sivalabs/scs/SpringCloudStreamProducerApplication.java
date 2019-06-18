package com.sivalabs.scs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;

import java.util.Date;

@SpringBootApplication
@EnableBinding(Endpoints.class)
public class SpringCloudStreamProducerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamProducerApplication.class, args);
    }

    @Autowired
    Endpoints endpoints;

    @Autowired
    Sender sender;

    @Override
    public void run(String... args) {
        for (int i = 0; i < 5; i++) {
            sender.sendStringMessage("Hello "+new Date());
            SimpleMessage message = new SimpleMessage();
            message.setData("How are you? "+new Date());
            sender.sendObjectMessage(message);
        }
    }
}
