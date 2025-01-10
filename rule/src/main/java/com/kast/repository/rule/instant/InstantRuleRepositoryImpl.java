package com.kast.repository.rule.instant;

import com.kast.entity.log.MatchMapLog;
import com.kast.entity.rule.RuleLog;
import com.mongodb.client.model.Filters;
import com.mongodb.reactivestreams.client.FindPublisher;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import io.reactivex.rxjava3.core.Single;
import jakarta.inject.Singleton;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;

/**
 * @author Kirill "Tamada" Simovin
 */
@Singleton
public class InstantRuleRepositoryImpl implements InstantRuleRepository {
    private final MongoClient mongoClient;

    public InstantRuleRepositoryImpl(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public Single<RuleLog> save(RuleLog log) {
        return Single
                .fromPublisher(getCollection().insertOne(log))
                .map(success -> log);
    }

    @Override
    public FindPublisher<RuleLog> findAllById(Long id) {
        Bson targetLog = Filters.eq("id", id);
        return getCollection().find(targetLog);
    }

    @NotNull
    private MongoCollection<RuleLog> getCollection() {
        return mongoClient
                .getDatabase("mops")
                .getCollection("instant", RuleLog.class);
    }
}
