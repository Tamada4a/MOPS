package com.kast.service;

import com.kast.entity.log.MatchMapLog;
import com.kast.entity.log.enums.LogType;
import com.kast.kafka.producer.LogProducer;
import com.kast.repository.log.LogRepositoryImpl;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.inject.Singleton;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Kirill "Tamada" Simovin
 */
@Singleton
public class LogService {
    private static final Logger LOG = LoggerFactory.getLogger(LogService.class);

    private final LogRepositoryImpl logRepository;
    private final LogProducer logProducer;

    private final Counter acceptedCounter;
    private final Counter skippedCounter;

    public LogService(LogRepositoryImpl logRepository, LogProducer logProducer, @NotNull MeterRegistry meterRegistry) {
        this.logRepository = logRepository;
        this.logProducer = logProducer;
        this.acceptedCounter = meterRegistry.counter("accepted_logs");
        this.skippedCounter = meterRegistry.counter("skipped_logs");
    }

    public void save(@NotNull MatchMapLog matchMapLog, Long id) {
        if (matchMapLog.getType() == LogType.ROUND_STARTED) {
            skippedCounter.increment();
            return;
        }
        acceptedCounter.increment();
        LOG.info("Received new log from id: {}! Sending it!", id);
        logProducer.sendLog(id, matchMapLog);
        LOG.info("Log with id: {} sent successfully!", id);
        MatchMapLog saved = logRepository.save(matchMapLog).blockingGet();
        LOG.info("Log with id: {} saved successfully!", id);
    }
}
