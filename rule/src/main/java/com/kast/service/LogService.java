package com.kast.service;

import com.kast.entity.log.MatchMapLog;
import com.kast.entity.rule.RuleLog;
import com.kast.repository.rule.instant.InstantRuleRepositoryImpl;
import com.kast.repository.rule.ongoing.OngoingRuleRepositoryImpl;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.inject.Singleton;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/**
 * @author Kirill "Tamada" Simovin
 */
@Singleton
public class LogService {
    private static final Logger LOG = LoggerFactory.getLogger(LogService.class);

    private final InstantRuleRepositoryImpl instantRuleRepository;
    private final OngoingRuleRepositoryImpl ongoingRuleRepository;
    private final Counter instantCounter;
    private final Counter ongoingCounter;

    public LogService(InstantRuleRepositoryImpl instantRuleRepository, OngoingRuleRepositoryImpl ongoingRuleRepository,
                      @NotNull MeterRegistry meterRegistry) {
        this.instantRuleRepository = instantRuleRepository;
        this.ongoingRuleRepository = ongoingRuleRepository;
        this.instantCounter = meterRegistry.counter("instant_rule");
        this.ongoingCounter = meterRegistry.counter("ongoing_rule");
    }

    public void performLogs(Long id, @NotNull MatchMapLog matchMapLog) {
        LOG.info("Received new log from kafka with id: {}!", id);
        if (matchMapLog.getTAlive() == null || matchMapLog.getTAlive() <= 200) {
            LOG.info("Skipped log with id: {}!", id);
            return;
        }
        RuleLog ruleLog = new RuleLog(id, matchMapLog);

        int size = Flux.from(instantRuleRepository.findAllById(id)).collectList().block().size();
        if (size != 0 && size % 10 == 0) {
            ongoingRuleRepository.save(ruleLog).blockingSubscribe();
            LOG.info("Ongoing rule with id: {} saved successfully!", id);
            ongoingCounter.increment();
        }
        instantRuleRepository.save(ruleLog).blockingSubscribe();
        LOG.info("Instant rule with id: {} saved successfully!", id);
        instantCounter.increment();
    }
}
