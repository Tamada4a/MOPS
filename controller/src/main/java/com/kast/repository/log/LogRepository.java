package com.kast.repository.log;

import com.kast.entity.log.MatchMapLog;
import io.micronaut.core.annotation.NonNull;
import io.reactivex.rxjava3.core.Single;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * @author Kirill "Tamada" Simovin
 */
public interface LogRepository {
    Single<MatchMapLog> save(@NonNull @NotNull @Valid MatchMapLog log);
}