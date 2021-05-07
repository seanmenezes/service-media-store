package com.plutotv.common.layer.step.v1.ratings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plutotv.common.layer.endpoint.v1.ratings.RatingsDescriptorsEndpointsV1;
import com.plutotv.common.model.bussines.v1.ratings.RatingDescriptor;
import com.plutotv.common.model.response.v1.ratings.Ratings;
import com.plutotv.common.model.response.v1.ratings.descriptors.RatingsDescriptors;
import com.plutotv.common.model.support.v1.ratings.RatingDescriptorsDatum;
import com.plutotv.common.model.support.v1.ratings.RatingsDatum;
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
import static java.util.stream.Collectors.toList;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.assertj.core.api.Assertions.assertThat;

public class RatingsDescriptorsSteps {
    private final RatingsDescriptorsEndpointsV1 ratingsDescriptorsEndpointsV1 = new RatingsDescriptorsEndpointsV1();

    @Step
    public Response getV1RatingsDescriptorsFullSync(String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(UPDATED_BEFORE, syncStop);
        return ratingsDescriptorsEndpointsV1.getRatingsDescriptors(requestParams);
    }

    @Step
    public Response getV1RatingsDescriptorsFullSync(String syncStop, ObjectMapper objectMapper) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(UPDATED_BEFORE, syncStop);
        return ratingsDescriptorsEndpointsV1.getRatingsDescriptors(requestParams, objectMapper);
    }

    @Step
    public Response getV1RatingsDescriptorsFullSyncWithLimit(String limit, String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(LIMIT, limit);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return ratingsDescriptorsEndpointsV1.getRatingsDescriptors(requestParams);
    }

    @Step
    public Response getV1RatingsDescriptorsFullSyncWithOffset(String offset, String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(OFFSET, offset);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return ratingsDescriptorsEndpointsV1.getRatingsDescriptors(requestParams);
    }

    @Step
    public Response getV1RatingsDeltaUpdate(String syncStart, String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "delta-update");
        requestParams.put(UPDATED_AFTER, syncStart);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return ratingsDescriptorsEndpointsV1.getRatingsDescriptors(requestParams);
    }

    @Step
    public String getV1RatingsMostRecentlyUpdatedRatingDescriptorTimestamp() {
        Response response = ratingsDescriptorsEndpointsV1.headRatingsDescriptors();
        response.then().statusCode(SC_NO_CONTENT);
        return response.getHeader(LAST_MODIFIED_HEADER);
    }

    @Step
    public RatingDescriptor getFirstRatingDescriptor(Response v1RatingsDescriptors, String replicationType) {
        return v1RatingsDescriptors.as(RatingsDescriptors.class).getData().stream()
                .filter(rating -> Objects.equals(replicationType, rating.getMeta().getReplicaAction()))
                .map(RatingDescriptorsDatum::getPayload)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        format("There are no ratings descriptors with replication type %s for /v1/ratings/descriptors", replicationType)));
    }

    @Step
    public List<RatingDescriptor> getFirstNRatingsDescriptors(int ratingsDescriptorsNumber, Response v1RatingsDescriptors) {
        List<RatingDescriptor> ratingDescriptorList = v1RatingsDescriptors.as(RatingsDescriptors.class).getData().stream()
                .filter(ratingDescriptor -> Objects.equals("upsert", ratingDescriptor.getMeta().getReplicaAction()))
                .map(RatingDescriptorsDatum::getPayload)
                .limit(ratingsDescriptorsNumber)
                .collect(toList());
        assertThat(ratingDescriptorList)
                .as("It is expected at least %s ratings descriptors, but actually there are %s ratings descriptors",
                        ratingsDescriptorsNumber, ratingDescriptorList.size())
                .hasSize(ratingsDescriptorsNumber);
        return ratingDescriptorList;
    }

    @Step
    public void assertThatRatingsDescriptorsLimitAsExpected(String ratingsDescriptorsCountExpected, long ratingsDescriptorsCountActual) {
        assertThat(ratingsDescriptorsCountActual)
                .as("Only %s ratings descriptor expected, but actually %s in response",
                        ratingsDescriptorsCountExpected, ratingsDescriptorsCountActual)
                .isEqualTo(parseLong(ratingsDescriptorsCountExpected));
    }

    @Step
    public void assertThatV1RatingsDescriptorsOffsetAsExpected(RatingDescriptor firstRatingDescriptorAfterOffsetExpected,
                                                               RatingDescriptor firstRatingDescriptorAfterOffsetActual) {
        assertThat(firstRatingDescriptorAfterOffsetExpected.getId())
                .as("First expected rating descriptor after offset is %s but actual is %s",
                        firstRatingDescriptorAfterOffsetExpected, firstRatingDescriptorAfterOffsetActual)
                .isEqualTo(firstRatingDescriptorAfterOffsetActual.getId());
    }

    @Step
    public long getRatingsDescriptorsCount(Response v1RatingsDescriptorsFullSyncWithLimit) {
        return v1RatingsDescriptorsFullSyncWithLimit.as(Ratings.class).getData().stream()
                .map(RatingsDatum::getPayload)
                .count();
    }
}
