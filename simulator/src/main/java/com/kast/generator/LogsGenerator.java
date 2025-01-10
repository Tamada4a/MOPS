package com.kast.generator;

import com.kast.client.LogClient;
import com.kast.entity.log.MatchMapLog;
import com.kast.interfaces.pause.IPaused;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import org.instancio.Instancio;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * @author Kirill "Tamada" Simovin
 */
@Singleton
public class LogsGenerator implements IPaused {
    private static final Logger LOG = LoggerFactory.getLogger(LogsGenerator.class);
    private boolean paused = true;

    private final LogClient logClient;
    private final Counter checks;

    public LogsGenerator(LogClient logClient, @NotNull MeterRegistry meterRegistry) {
        this.logClient = logClient;
        this.checks = meterRegistry.counter("logs_generated");
    }

    @Scheduled(fixedDelay = "5s", condition = "#{!this.paused}")
    public void startDataSimulator() {
        MatchMapLog log = Instancio.of(MatchMapLog.class).create();
        checks.increment();
        long max = 100;
        long min = 1;
        long id = new Random().nextLong(max - min) + min;
        LOG.info("Sending new log from id: {}!", id);
        logClient.sendLog(log, id);
        LOG.info("Log with id: {} sent successfully!", id);
    }

    @Override
    public boolean isPaused() {
        return paused;
    }

    @Override
    public void onStartup(StartupEvent event) {
        this.paused = false;
        LOG.info("Logs generator enabled!");
    }
}
