package com.plutotv.common.layer.step.v1.ratings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plutotv.common.layer.endpoint.v1.ratings.RatingsEndpointsV1;
import com.plutotv.common.model.bussines.v1.ratings.Rating;
import com.plutotv.common.model.response.v1.ratings.Ratings;
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

public class RatingsSteps {
    private final RatingsEndpointsV1 ratingsEndpointsV1 = new RatingsEndpointsV1();

    @Step
    public Response getV1RatingsFullSync(String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(UPDATED_BEFORE, syncStop);
        return ratingsEndpointsV1.getRatings(requestParams);
    }

    @Step
    public Response getV1RatingsFullSync(String syncStop, ObjectMapper objectMapper) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(UPDATED_BEFORE, syncStop);
        return ratingsEndpointsV1.getRatings(requestParams, objectMapper);
    }

    @Step
    public Response getV1RatingsFullSyncWithLimit(String limit, String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(LIMIT, limit);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return ratingsEndpointsV1.getRatings(requestParams);
    }

    @Step
    public Response getV1RatingsFullSyncWithOffset(String offset, String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(OFFSET, offset);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return ratingsEndpointsV1.getRatings(requestParams);
    }

    @Step
    public Response getV1RatingsDeltaUpdate(String syncStart, String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "delta-update");
        requestParams.put(UPDATED_AFTER, syncStart);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return ratingsEndpointsV1.getRatings(requestParams);
    }

    @Step
    public String getV1RatingsMostRecentlyUpdatedRatingTimestamp() {
        Response response = ratingsEndpointsV1.headRatings();
        response.then().statusCode(SC_NO_CONTENT);
        return response.getHeader(LAST_MODIFIED_HEADER);
    }

    @Step
    public Rating getFirstRating(Response v1Ratings, String replicationType) {
        return v1Ratings.as(Ratings.class).getData().stream()
                .filter(rating -> Objects.equals(replicationType, rating.getMeta().getReplicaAction()))
                .map(RatingsDatum::getPayload)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        format("There are no ratings with replication type %s for /v1/ratings", replicationType)));
    }

    @Step
    public List<Rating> getFirstNRatings(int ratingsNumber, Response v1Ratings) {
        List<Rating> ratingList = v1Ratings.as(Ratings.class).getData().stream()
                .filter(rating -> Objects.equals("upsert", rating.getMeta().getReplicaAction()))
                .map(RatingsDatum::getPayload)
                .limit(ratingsNumber)
                .collect(toList());
        assertThat(ratingList)
                .as("It is expected at least %s ratings, but actually there are %s ratings", ratingsNumber, ratingList.size())
                .hasSize(ratingsNumber);
        return ratingList;
    }

    @Step
    public void assertThatRatingsLimitAsExpected(String ratingsCountExpected, long ratingsCountActual) {
        assertThat(ratingsCountActual)
                .as("Only %s ratings expected, but actually %s in response",
                        ratingsCountExpected, ratingsCountActual)
                .isEqualTo(parseLong(ratingsCountExpected));
    }

    @Step
    public long getRatingsCount(Response v1RatingsFullSyncWithLimit) {
        return v1RatingsFullSyncWithLimit.as(Ratings.class).getData().stream()
                .map(RatingsDatum::getPayload)
                .count();
    }

    @Step
    public void assertThatV1RatingsOffsetAsExpected(Rating firstRatingAfterOffsetExpected, Rating firstRatingAfterOffsetActual) {
        assertThat(firstRatingAfterOffsetExpected.getId())
                .as("First expected rating after offset is %s but actual is %s",
                        firstRatingAfterOffsetExpected, firstRatingAfterOffsetActual)
                .isEqualTo(firstRatingAfterOffsetActual.getId());
    }
}
