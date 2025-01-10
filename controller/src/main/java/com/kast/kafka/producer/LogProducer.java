package com.kast.kafka.producer;

import com.kast.entity.log.MatchMapLog;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient
public interface LogProducer {

    // greet is a kafka topic
    @Topic("log")
    void sendLog(@KafkaKey Long id, MatchMapLog log);

    void sendLog(@Topic String topic, @KafkaKey Long id, MatchMapLog log);
}