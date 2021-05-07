/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.common.helper;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static java.time.temporal.ChronoUnit.HOURS;

public class RequestParameterHelper {

    public static final String REPLICATION_TYPE = "replicationType";
    public static final String UPDATED_BEFORE = "updatedBefore";
    public static final String UPDATED_AFTER = "updatedAfter";
    public static final String START = "start";
    public static final String STOP = "stop";
    public static final String PREV_STOP = "prevStop";
    public static final String OFFSET = "offset";
    public static final String LIMIT = "limit";
    public static final String TAGS = "tags";
    public static final String MAX_DURATION_SEC = "maxDurationSec";

    public static Map<String, String> getStartAndStopParams(long hours) {
        HashMap<String, String> startStopParams = new HashMap<>();
        Instant nowTimestamp = Instant.now().truncatedTo(HOURS);
        startStopParams.put(START, nowTimestamp.minus(1, HOURS).toString());
        startStopParams.put(STOP, nowTimestamp.minus(1, HOURS).plus(hours, HOURS).toString());
        return startStopParams;
    }
}
