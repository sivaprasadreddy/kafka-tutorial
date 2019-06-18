package com.sivalabs.springbootkafkasample;

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

import static com.sivalabs.springbootkafkasample.SpringBootKafkaSampleApplication.TOPIC_TEST_1;
import static com.sivalabs.springbootkafkasample.SpringBootKafkaSampleApplication.TOPIC_TEST_2;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootKafkaSampleApplicationTests {

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
    private KafkaTemplate<String, String> template;

    @Autowired
    private Receiver receiver;

    @Test
    public void sendAndReceiveData() throws Exception {
        template.send(TOPIC_TEST_1, "foo");
        receiver.getLatch().await(5, TimeUnit.SECONDS);
        assertEquals(0, receiver.getLatch().getCount());
    }

}
