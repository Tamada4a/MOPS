package com.kast.kafka.consumer;

import com.kast.entity.log.MatchMapLog;
import com.kast.service.LogService;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;

/**
 * @author Kirill "Tamada" Simovin
 */
@KafkaListener(offsetReset = OffsetReset.EARLIEST)
public class LogConsumer {
    private final LogService logService;

    public LogConsumer(LogService logService) {
        this.logService = logService;
    }

    @Topic("log")
    public void receive(@KafkaKey Long id, MatchMapLog log) {
        logService.performLogs(id, log);
    }
}