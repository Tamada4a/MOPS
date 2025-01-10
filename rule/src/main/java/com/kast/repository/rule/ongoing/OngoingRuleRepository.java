package com.kast.repository.rule.ongoing;

import com.kast.entity.rule.RuleLog;
import com.mongodb.reactivestreams.client.FindPublisher;
import io.micronaut.core.annotation.NonNull;
import io.reactivex.rxjava3.core.Single;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * @author Kirill "Tamada" Simovin
 */
public interface OngoingRuleRepository {
    Single<RuleLog> save(@NonNull @NotNull @Valid RuleLog log);

    FindPublisher<RuleLog> findAllById(Long id);
}