package com.sivalabs.scs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class Sender {

    @Autowired
    private Endpoints endpoints;

    public void sendStringMessage(String msg) {
        endpoints.simpleOutput().send(
                MessageBuilder.withPayload(msg).build()
        );
    }

    public void sendObjectMessage(SimpleMessage message) {
        endpoints.jsonOutput().send(
                MessageBuilder.withPayload(message).build()
        );
    }


}
