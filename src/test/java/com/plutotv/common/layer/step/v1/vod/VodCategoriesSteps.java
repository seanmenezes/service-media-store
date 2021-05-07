package com.plutotv.common.layer.step.v1.vod;

import com.plutotv.common.layer.endpoint.v1.vod.VodEndpointsV1;
import com.plutotv.common.model.bussines.v1.vod.category.VodCategory;
import com.plutotv.common.model.response.v1.vod.categories.VodCategories;
import com.plutotv.common.model.support.v1.vod.categories.VodCategoriesDatum;
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

public class VodCategoriesSteps {
    private final VodEndpointsV1 vodEndpointsV1 = new VodEndpointsV1();

    @Step
    public Response getV1VodCategoriesFullSync(String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(UPDATED_BEFORE, syncStop);
        return vodEndpointsV1.getVodCategories(requestParams);
    }

    @Step
    public Response getV1VodCategoriesFullSyncWithLimit(String limit, String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(LIMIT, limit);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return vodEndpointsV1.getVodCategories(requestParams);
    }

    @Step
    public Response getV1VodCategoriesFullSyncWithOffset(String offset, String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(OFFSET, offset);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return vodEndpointsV1.getVodCategories(requestParams);
    }

    @Step
    public Response getV1VodCategoriesDeltaUpdate(String syncStart, String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "delta-update");
        requestParams.put(UPDATED_AFTER, syncStart);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return vodEndpointsV1.getVodCategories(requestParams);
    }

    @Step
    public String getV1VodCategoriesMostRecentlyUpdatedCategoryTimestamp() {
        Response response = vodEndpointsV1.headVodCategories();
        response.then().statusCode(SC_NO_CONTENT);
        return response.getHeader(LAST_MODIFIED_HEADER);
    }

    @Step
    public VodCategory getFirstVodCategory(Response v1VodCategories, String replicationType) {
        return v1VodCategories.as(VodCategories.class).getData().stream()
                .filter(vodCategory -> Objects.equals(replicationType, vodCategory.getMeta().getReplicaAction()))
                .map(VodCategoriesDatum::getPayload)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        format("There are no vod categories with replication type %s for v1/vod/categories", replicationType)));
    }

    @Step
    public List<VodCategory> getFirstNVodCategories(int vodCategoryNumber, Response v1VodCategories) {
        List<VodCategory> vodCategoryList = v1VodCategories.as(VodCategories.class).getData().stream()
                .filter(rating -> Objects.equals("upsert", rating.getMeta().getReplicaAction()))
                .map(VodCategoriesDatum::getPayload)
                .limit(vodCategoryNumber)
                .collect(toList());
        assertThat(vodCategoryList)
                .as("It is expected at least %s vod categories, but actually there are %s categories",
                        vodCategoryNumber, vodCategoryList.size())
                .hasSize(vodCategoryNumber);
        return vodCategoryList;
    }

    @Step
    public List<VodCategory> getVodCategoriesWithUpsertReplicationType(Response v1VodCategories) {
        List<VodCategory> vodCategories = v1VodCategories.as(VodCategories.class).getData().stream()
                .filter(vodCategory -> Objects.equals("upsert", vodCategory.getMeta().getReplicaAction()))
                .map(VodCategoriesDatum::getPayload)
                .collect(toList());
        assertThat(vodCategories).as("it is expected that some vod categories with replicationType: upsert found").isNotEmpty();
        return vodCategories;
    }

    @Step
    public List<VodCategory> getVodCategoriesThatShouldNotBeReturnedForDeltaUpdate(String syncStop,
                                                                                   String syncStart,
                                                                                   List<VodCategory> vodCategories) {
        return vodCategories.stream().filter(vodCategory ->
                parse(vodCategory.getUpdatedAt()).isBefore(parse(syncStart))
                        && parse(vodCategory.getUpdatedAt()).equals(parse(syncStart))
                        && parse(vodCategory.getUpdatedAt()).isAfter(parse(syncStop)))
                .collect(toList());
    }

    @Step
    public long getVodCategoriesCount(Response v1VodCategories) {
        return v1VodCategories.as(VodCategories.class).getData().stream()
                .map(VodCategoriesDatum::getPayload)
                .count();
    }

    @Step
    public List<VodCategory> getVodCategoriesThatShouldNotBeReturnedForFullSync(String syncStop,
                                                                                List<VodCategory> vodCategories) {
        return vodCategories.stream()
                .filter(vodCategory -> parse(vodCategory.getUpdatedAt()).isAfter(parse(syncStop)))
                .collect(toList());
    }
}
