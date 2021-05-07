package com.plutotv.common.layer.step.v1.vod;

import com.plutotv.common.layer.endpoint.v1.vod.VodEndpointsV1;
import com.plutotv.common.model.bussines.v1.vod.category_entries.VodCategoryEntry;
import com.plutotv.common.model.response.v1.vod.category_entries.VodCategoryEntries;
import com.plutotv.common.model.support.v1.vod.category_entries.VodCategoryEntriesDatum;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.plutotv.common.helper.HeaderHelper.LAST_MODIFIED_HEADER;
import static com.plutotv.common.helper.RequestParameterHelper.*;
import static java.lang.String.format;
import static java.time.Instant.parse;
import static java.util.stream.Collectors.toList;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.assertj.core.api.Assertions.assertThat;

public class VodCategoryEntriesSteps {
    private final VodEndpointsV1 vodEndpointsV1 = new VodEndpointsV1();

    @Step
    public Response getV1VodCategoryEntriesFullSync(String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(UPDATED_BEFORE, syncStop);
        return vodEndpointsV1.getVodCategoryEntries(requestParams);
    }

    @Step
    public Response getV1VodCategoryEntriesFullSyncWithLimit(String limit, String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(LIMIT, limit);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return vodEndpointsV1.getVodCategoryEntries(requestParams);
    }

    @Step
    public Response getV1VodCategoryEntriesFullSyncWithOffset(String offset, String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(OFFSET, offset);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return vodEndpointsV1.getVodCategoryEntries(requestParams);
    }

    @Step
    public Response getV1VodCategoryEntriesDeltaUpdate(String syncStart, String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "delta-update");
        requestParams.put(UPDATED_AFTER, syncStart);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return vodEndpointsV1.getVodCategoryEntries(requestParams);
    }

    @Step
    public String getV1VodCategoryEntriesMostRecentlyUpdatedCategoryEntryTimestamp() {
        Response response = vodEndpointsV1.headVodCategoryEntries();
        response.then().statusCode(SC_NO_CONTENT);
        return response.getHeader(LAST_MODIFIED_HEADER);
    }

    @Step
    public VodCategoryEntry getFirstVodCategoryEntry(Response v1VodCategoryEntries, String replicationType) {
        return v1VodCategoryEntries.as(VodCategoryEntries.class).getData().stream()
                .filter(rating -> Objects.equals(replicationType, rating.getMeta().getReplicaAction()))
                .map(VodCategoryEntriesDatum::getPayload)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        format("There are no vod category entries with replication type %s for /v1/vod/category-entries", replicationType)));
    }

    @Step
    public List<VodCategoryEntry> getFirstNVodCategoryEntries(int vodCategoryEntriesNumber, Response v1VodCategoryEntries) {
        List<VodCategoryEntry> vodCategoryEntryList = v1VodCategoryEntries.as(VodCategoryEntries.class).getData().stream()
                .filter(rating -> Objects.equals("upsert", rating.getMeta().getReplicaAction()))
                .map(VodCategoryEntriesDatum::getPayload)
                .limit(vodCategoryEntriesNumber)
                .collect(toList());
        assertThat(vodCategoryEntryList)
                .as("It is expected at least %s vod category entries, but actually there are %s entries",
                        vodCategoryEntriesNumber, vodCategoryEntryList.size())
                .hasSize(vodCategoryEntriesNumber);
        return vodCategoryEntryList;
    }

    @Step
    public List<VodCategoryEntry> getVodCategoryEntriesWithUpsertReplicationType(Response v1VodCategoryEntries) {
        List<VodCategoryEntry> vodCategoryEntries = v1VodCategoryEntries.as(VodCategoryEntries.class).getData().stream()
                .filter(vodCategoryEntry -> Objects.equals("upsert", vodCategoryEntry.getMeta().getReplicaAction()))
                .map(VodCategoryEntriesDatum::getPayload)
                .collect(toList());
        assertThat(vodCategoryEntries)
                .as("it is expected that some vod category entries with replicationType: upsert found")
                .isNotEmpty();
        return vodCategoryEntries;
    }

    @Step
    public List<VodCategoryEntry> getVodCategoryEntriesThatShouldNotBeReturnedForDeltaUpdate(String syncStop,
                                                                                             String syncStart,
                                                                                             List<VodCategoryEntry> vodCategoryEntries) {
        return vodCategoryEntries.stream()
                .filter(vodCategoryEntry -> parse(vodCategoryEntry.getUpdatedAt()).isBefore(parse(syncStart))
                        && parse(vodCategoryEntry.getUpdatedAt()).equals(parse(syncStart))
                        && parse(vodCategoryEntry.getUpdatedAt()).isAfter(parse(syncStop)))
                .collect(toList());
    }

    @Step
    public long getVodCategoryEntriesCount(Response v1VodCategoryEntries) {
        return v1VodCategoryEntries.as(VodCategoryEntries.class).getData().stream()
                .map(VodCategoryEntriesDatum::getPayload)
                .count();
    }

    @Step
    public List<VodCategoryEntry> getVodCategoryEntriesThatShouldNotBeReturnedForFullSync(String syncStop,
                                                                                          List<VodCategoryEntry> vodCategoryEntries) {
        return vodCategoryEntries.stream()
                .filter(vodCategoryEntry -> parse(vodCategoryEntry.getUpdatedAt()).isAfter(parse(syncStop)))
                .collect(toList());
    }
}
