package com.logic.springboot.service;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
public class WikimediaKafkaProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaKafkaProducer.class);

    private KafkaTemplate<String, String> kafkaTemplate;

    public WikimediaKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage() throws InterruptedException {
//        LOGGER.info(String.format("Sending message -> %s", message));

        String topic = "wikimedia-topic";

//        To read real time stream data from wikimedia, we use event source
        EventHandler eventHandler = new WikimediaChangesHandler(kafkaTemplate, topic);

        String eventUrl = "https://stream.wikimedia.org/v2/stream/recentchange";

        EventSource.Builder builder = new EventSource.Builder(eventHandler, URI.create(eventUrl));

        EventSource eventSource = builder.build();

        eventSource.start();

        TimeUnit.MINUTES.sleep(10);
    }
}
