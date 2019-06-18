package com.sivalabs.scs;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Endpoints {

    String SIMPLE_INPUT = "simpleInput";

    @Input(SIMPLE_INPUT)
    SubscribableChannel simpleInput();


    String JSON_INPUT = "jsonInput";

    @Input(JSON_INPUT)
    SubscribableChannel jsonInput();


}
