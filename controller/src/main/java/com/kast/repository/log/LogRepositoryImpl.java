package com.kast.repository.log;

import com.kast.entity.log.MatchMapLog;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import io.reactivex.rxjava3.core.Single;
import jakarta.inject.Singleton;
import org.jetbrains.annotations.NotNull;

/**
 * @author Kirill "Tamada" Simovin
 */
@Singleton
public class LogRepositoryImpl implements LogRepository {
    private final MongoClient mongoClient;

    public LogRepositoryImpl(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public Single<MatchMapLog> save(MatchMapLog log) {
        return Single
                .fromPublisher(getCollection().insertOne(log))
                .map(success -> log);
    }

    @NotNull
    private MongoCollection<MatchMapLog> getCollection() {
        return mongoClient
                .getDatabase("mops")
                .getCollection("logs", MatchMapLog.class);
    }
}
