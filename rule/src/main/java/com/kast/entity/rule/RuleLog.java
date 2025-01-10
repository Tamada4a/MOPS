package com.kast.entity.rule;

import com.kast.entity.log.MatchMapLog;
import io.micronaut.core.annotation.Creator;
import io.micronaut.serde.annotation.Serdeable;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

/**
 * @author Kirill "Tamada" Simovin
 */
@Serdeable
public class RuleLog {
    @BsonProperty("id")
    private final Long id;

    @BsonProperty("matchMapLog")
    private final MatchMapLog matchMapLog;

    @Creator
    @BsonCreator
    public RuleLog(Long id, MatchMapLog matchMapLog) {
        this.id = id;
        this.matchMapLog = matchMapLog;
    }

    public Long getId() {
        return id;
    }

    public MatchMapLog getMatchMapLog() {
        return matchMapLog;
    }
}
