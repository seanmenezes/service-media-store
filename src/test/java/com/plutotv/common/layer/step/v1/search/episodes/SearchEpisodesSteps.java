package com.plutotv.common.layer.step.v1.search.episodes;

import com.plutotv.common.layer.endpoint.v1.search.SearchEndpointsV1;
import com.plutotv.common.model.response.v1.search.episodes.Episodes;
import com.plutotv.common.model.support.v1.search.episodes.SearchEpisodeDatum;
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

public class SearchEpisodesSteps {
    private final SearchEndpointsV1 searchEndpointsV1 = new SearchEndpointsV1();

    @Step
    public String getV1SearchEpisodesMostRecentlyUpdatedEpisodeTimestamp() {
        Response response = searchEndpointsV1.getSearchEpisodesLastModifiedDateTime();
        response.then().statusCode(SC_NO_CONTENT);
        return response.getHeader(LAST_MODIFIED_HEADER);
    }

    @Step
    public Response getV1SearchEpisodesFullSync(String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(UPDATED_BEFORE, syncStop);
        return searchEndpointsV1.getV1SearchEpisodes(requestParams);
    }

    @Step
    public SearchEpisodeDatum getFirstEpisode(Response v1SearchEpisodes, String replicationType) {
        return v1SearchEpisodes.as(Episodes.class).getData().stream()
                .filter(episode -> Objects.equals(replicationType, episode.getMeta().getReplicaAction()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        format("There is no episode with replication type %s for /v1/search/episodes", replicationType)));
    }
}
