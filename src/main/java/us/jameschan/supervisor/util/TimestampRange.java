package us.jameschan.supervisor.util;

import java.sql.Timestamp;

public class TimestampRange {
    private final Timestamp startTimestamp;

    private final Timestamp endTimestamp;

    public TimestampRange(Timestamp startTimestamp, Timestamp endTimestamp) {
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
    }

    public TimestampRange(Long startTimestamp, Long endTimestamp) {
        this(
            startTimestamp == null ? null : new Timestamp(startTimestamp),
            endTimestamp == null ? null : new Timestamp(endTimestamp)
        );
    }

    public Timestamp getStartTimestamp() {
        return startTimestamp;
    }

    public Timestamp getEndTimestamp() {
        return endTimestamp;
    }
}
