package com.sivalabs.springbootkafka.multi;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static com.sivalabs.springbootkafka.multi.SpringBootKafkaMultiApplication.TOPIC_TEST_1;
import static com.sivalabs.springbootkafka.multi.SpringBootKafkaMultiApplication.TOPIC_TEST_2;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootKafkaMultiApplicationTests {

    @ClassRule
    public static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, false, 1, TOPIC_TEST_1, TOPIC_TEST_2);

    @BeforeClass
    public static void setup() {
        System.setProperty("spring.kafka.bootstrap-servers", embeddedKafka.getEmbeddedKafka().getBrokersAsString());
    }

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;


    @Before
    public void setUp() throws Exception {
        // wait until the partitions are assigned
        for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry.getListenerContainers()) {
            ContainerTestUtils.waitForAssignment(messageListenerContainer, embeddedKafka.getEmbeddedKafka().getPartitionsPerTopic());
        }
    }

    @Autowired
    private KafkaTemplate<Integer, String> simpleKafkaTemplate;


    @Autowired
    private KafkaTemplate<String, SimpleMessage> jsonKafkaTemplate;

    @Autowired
    Sender sender;

    @Autowired
    SimpleReceiver simpleReceiver;

    @Autowired
    JsonReceiver jsonReceiver;

    @Test
    public void sendAndReceiveData() throws Exception {
       // simpleKafkaTemplate.send(TOPIC_TEST_1, 10,"foo");
        sender.send(10, "foo");
        //TimeUnit.SECONDS.sleep(5);
        simpleReceiver.getLatch().await(5, TimeUnit.SECONDS);
        assertEquals(0, simpleReceiver.getLatch().getCount());
    }


    @Test
    public void sendAndReceiveJsonData() throws Exception {
        SimpleMessage simpleMessage = new SimpleMessage();
        simpleMessage.setKey(110);
        simpleMessage.setValue("My Json Message");
        sender.send(simpleMessage);
        //jsonKafkaTemplate.send(TOPIC_TEST_2, "k1",simpleMessage);
        //TimeUnit.SECONDS.sleep(5);
        jsonReceiver.getLatch().await(5, TimeUnit.SECONDS);
        assertEquals(0, jsonReceiver.getLatch().getCount());
    }

}
