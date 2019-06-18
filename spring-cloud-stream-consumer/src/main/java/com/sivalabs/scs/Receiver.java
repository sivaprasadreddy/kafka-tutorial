package com.sivalabs.scs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Receiver {

    @StreamListener(value = Endpoints.SIMPLE_INPUT)
    public void receiveString(String string) {
        log.info("Received String: "+string);
    }

    @StreamListener(value = Endpoints.JSON_INPUT)
    public void receiveObject(IncomingMessage message) {
        log.info("Received IncomingMessage: "+message);
    }
}
