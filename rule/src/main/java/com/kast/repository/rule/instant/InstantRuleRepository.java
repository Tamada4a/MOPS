package com.kast.repository.rule.instant;

import com.kast.entity.log.MatchMapLog;
import com.kast.entity.rule.RuleLog;
import com.mongodb.reactivestreams.client.FindPublisher;
import com.mongodb.reactivestreams.client.MongoCollection;
import io.micronaut.core.annotation.NonNull;
import io.reactivex.rxjava3.core.Single;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * @author Kirill "Tamada" Simovin
 */
public interface InstantRuleRepository {
    Single<RuleLog> save(@NonNull @NotNull @Valid RuleLog log);

    FindPublisher<RuleLog> findAllById(Long id);
}