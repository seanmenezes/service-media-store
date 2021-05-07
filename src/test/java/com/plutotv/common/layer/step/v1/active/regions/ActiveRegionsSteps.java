package com.plutotv.common.layer.step.v1.active.regions;

import com.plutotv.common.layer.endpoint.v1.active.regions.ActiveRegionsEndpointsV1;
import com.plutotv.common.model.bussines.v1.active.regions.ActiveRegion;
import com.plutotv.common.model.response.v1.active.regions.ActiveRegions;
import com.plutotv.common.model.support.v1.active.regions.ActiveRegionsDatum;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.plutotv.common.helper.HeaderHelper.LAST_MODIFIED_HEADER;
import static com.plutotv.common.helper.RequestParameterHelper.*;
import static java.lang.Long.parseLong;
import static java.lang.String.format;
import static java.time.Instant.parse;
import static java.util.stream.Collectors.toList;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.assertj.core.api.Assertions.assertThat;

public class ActiveRegionsSteps {
    private final ActiveRegionsEndpointsV1 activeRegionsEndpointsV1 = new ActiveRegionsEndpointsV1();

    @Step
    public Response getV1ActiveRegionsFullSync(String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(UPDATED_BEFORE, syncStop);
        return activeRegionsEndpointsV1.getActiveRegions(requestParams);
    }

    @Step
    public Response getV1ActiveRegionsFullSyncWithLimit(String limit, String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(LIMIT, limit);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return activeRegionsEndpointsV1.getActiveRegions(requestParams);
    }

    @Step
    public Response getV1ActiveRegionsFullSyncWithOffset(String offset, String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(OFFSET, offset);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return activeRegionsEndpointsV1.getActiveRegions(requestParams);
    }

    @Step
    public Response getV1ActiveRegionsDeltaUpdate(String syncStart, String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "delta-update");
        requestParams.put(UPDATED_AFTER, syncStart);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return activeRegionsEndpointsV1.getActiveRegions(requestParams);
    }

    @Step
    public String getV1RatingsMostRecentlyUpdatedActiveRegionTimestamp() {
        Response response = activeRegionsEndpointsV1.headActiveRegions();
        response.then().statusCode(SC_NO_CONTENT);
        return response.getHeader(LAST_MODIFIED_HEADER);
    }
    @Step
    public ActiveRegion getFirstActiveRegion(Response v1ActiveRegion, String replicationType) {
        return v1ActiveRegion.as(ActiveRegions.class).getData().stream()
                .filter(activeRegion -> Objects.equals(replicationType, activeRegion.getMeta().getReplicaAction()))
                .map(ActiveRegionsDatum::getPayload)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        format("There are no active regions with replication type %s for /v1/active-regions", replicationType)));
    }
    @Step
    public List<ActiveRegion> getFirstNActiveRegions(int activeRegionsNumber, Response v1ActiveRegions) {
        List<ActiveRegion> activeRegionList = v1ActiveRegions.as(ActiveRegions.class).getData().stream()
                .filter(activeRegion -> Objects.equals("upsert", activeRegion.getMeta().getReplicaAction()))
                .map(ActiveRegionsDatum::getPayload)
                .limit(activeRegionsNumber)
                .collect(toList());
        assertThat(activeRegionList)
                .as("It is expected at least %s active regions, but actually there are %s", activeRegionsNumber, activeRegionList.size())
                .hasSize(activeRegionsNumber);
        return activeRegionList;
    }

    @Step
    public void assertThatActiveRegionsLimitAsExpected(String activeRegionsCountExpected, long activeRegionsCountActual) {
        assertThat(activeRegionsCountActual)
                .as("Only %s active regions expected, but actually %s in response",
                        activeRegionsCountExpected, activeRegionsCountActual)
                .isEqualTo(parseLong(activeRegionsCountExpected));
    }

    @Step
    public long getActiveRegionsCount(Response v1ActiveRegions) {
        return v1ActiveRegions.as(ActiveRegions.class).getData().stream()
                .map(ActiveRegionsDatum::getPayload)
                .count();
    }

    @Step
    public List<ActiveRegion> getActiveRegionsWithUpsertReplicationType(Response v1ActiveRegions) {
        List<ActiveRegion> activeRegions = v1ActiveRegions.as(ActiveRegions.class).getData().stream()
                .filter(activeRegion -> Objects.equals("upsert", activeRegion.getMeta().getReplicaAction()))
                .map(ActiveRegionsDatum::getPayload)
                .collect(toList());
        assertThat(activeRegions).as("it is expected that some activeRegions with replicationType: upsert found").isNotEmpty();
        return activeRegions;
    }

    @Step
    public List<ActiveRegion> getActiveRegionsThatShouldNotBeReturnedForDeltaUpdate(String syncStop,
                                                                                    String syncStart,
                                                                                    List<ActiveRegion> activeRegions) {
        return activeRegions.stream()
                .filter(activeRegion -> parse(activeRegion.getUpdatedAt()).isBefore(parse(syncStart))
                        && parse(activeRegion.getUpdatedAt()).equals(parse(syncStart))
                        && parse(activeRegion.getUpdatedAt()).isAfter(parse(syncStop)))
                .collect(toList());
    }

    @Step
    public void assertThatV1ActiveRegionsOffsetAsExpected(ActiveRegion firstActiveRegionAfterOffsetExpected,
                                                          ActiveRegion firstActiveRegionAfterOffsetActual) {
        assertThat(firstActiveRegionAfterOffsetExpected.getId())
                .as("First expected active region after offset is %s but actual is %s",
                        firstActiveRegionAfterOffsetExpected, firstActiveRegionAfterOffsetActual)
                .isEqualTo(firstActiveRegionAfterOffsetActual.getId());
    }
}
