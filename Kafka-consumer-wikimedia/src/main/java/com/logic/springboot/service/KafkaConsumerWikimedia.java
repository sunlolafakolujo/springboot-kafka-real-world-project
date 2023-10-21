package com.logic.springboot.service;

import com.logic.springboot.model.WikimediaData;
import com.logic.springboot.repository.WikimediaDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerWikimedia {
    private static final Logger LOGGER= LoggerFactory.getLogger(KafkaConsumerWikimedia.class);

    private WikimediaDataRepository wikimediaDataRepository;

    public KafkaConsumerWikimedia(WikimediaDataRepository wikimediaDataRepository) {
        this.wikimediaDataRepository = wikimediaDataRepository;
    }

    @KafkaListener(topics = "wikimedia-topic", groupId = "myGroup")
    public void consumeMessage(String message){

        LOGGER.info(String.format("Event message received -> %s", message));
        WikimediaData wikimediaData= new WikimediaData();
        wikimediaData.setWikimediaEventData(message);
        wikimediaDataRepository.save(wikimediaData);
    }
}
