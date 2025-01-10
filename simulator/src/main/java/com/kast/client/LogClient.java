package com.kast.client;

import com.kast.entity.log.MatchMapLog;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;

/**
 * @author Kirill "Tamada" Simovin
 * <a href="https://stackoverflow.com/a/72971362">Source</a>
 */
@Client(id = "http://localhost:8090")
public interface LogClient {
    @Post("/match/{id}")
    void sendLog(@Body MatchMapLog log, @PathVariable("id") Long id);

    @Post
    void checkConnection();
}