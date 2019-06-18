package com.sivalabs.scs;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Endpoints {

    String SIMPLE_OUTPUT = "simpleOutput";

    @Output(SIMPLE_OUTPUT)
    MessageChannel simpleOutput();


    String JSON_OUTPUT = "jsonOutput";

    @Output(JSON_OUTPUT)
    SubscribableChannel jsonOutput();


}
