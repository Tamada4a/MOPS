package com.kast.controller;

import com.kast.entity.log.MatchMapLog;
import com.kast.service.LogService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;

/**
 * @author Kirill "Tamada" Simovin
 */
@Controller("/match")
public class LogController {
    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @Post("/{id}")
    public void getLogs(@Body MatchMapLog matchMapLog, @PathVariable("id") Long id) {
        logService.save(matchMapLog, id);
    }
}
