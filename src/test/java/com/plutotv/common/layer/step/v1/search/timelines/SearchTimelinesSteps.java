package com.plutotv.common.layer.step.v1.search.timelines;

import com.plutotv.common.layer.endpoint.v1.search.SearchEndpointsV1;
import com.plutotv.common.model.response.v1.search.timelines.Timelines;
import com.plutotv.common.model.support.v1.search.timelines.SearchTimelinesDatum;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.plutotv.common.helper.HeaderHelper.LAST_MODIFIED_HEADER;
import static com.plutotv.common.helper.RequestParameterHelper.REPLICATION_TYPE;
import static com.plutotv.common.helper.RequestParameterHelper.UPDATED_BEFORE;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;

public class SearchTimelinesSteps {
    private final SearchEndpointsV1 searchEndpointsV1 = new SearchEndpointsV1();

    @Step
    public String getV1SearchTimelinesMostRecentlyUpdatedTimelineTimestamp() {
        Response response = searchEndpointsV1.getSearchTimelinesLastModifiedDateTime();
        response.then().statusCode(SC_NO_CONTENT);
        return response.getHeader(LAST_MODIFIED_HEADER);
    }

    @Step
    public Response getV1SearchTimelinesFullSync(String syncStop, Map<String, String> timelineStartStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(UPDATED_BEFORE, syncStop);
        requestParams.putAll(timelineStartStop);
        return searchEndpointsV1.getV1SearchTimelines(requestParams);
    }

    @Step
    public SearchTimelinesDatum getFirstTimeline(Response v1SearchTimelines, String replicationType) {
        return v1SearchTimelines.as(Timelines.class).getData().stream()
                .filter(timeline -> Objects.equals(replicationType, timeline.getMeta().getReplicaAction()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        format("There is no timeline with replication type %s for /v1/search/timelines", replicationType)));
    }
}
