package com.plutotv.common.layer.step.mongo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.plutotv.common.layer.endpoint.mongo.MongoEndpoints;
import com.plutotv.common.model.mongo.filter.FindByCondition;
import com.plutotv.common.model.mongo.filter.FindById;
import com.plutotv.common.model.mongo.filter.models.ById;
import com.plutotv.common.model.mongo.filter.models.Id;
import com.plutotv.common.model.mongo.filter.models.WhereCondition;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static com.plutotv.common.mapper.CustomObjectMapper.getObjectMapperNotFailedWhenUnknownProperties;
import static org.apache.http.HttpStatus.SC_OK;

public class MongoSteps {
    private final MongoEndpoints dalEndpoints = new MongoEndpoints();

    @Step
    public <T> T findCollectionById(String id, String collectionName, Class<T> collectionModel) throws JsonProcessingException {
        Id collectionId = new Id();
        collectionId.setId(id);
        ById filter = new ById();
        filter.setId(collectionId);

        FindById query = new FindById();
        query.setCollection(collectionName);
        query.setFilter(filter);

        Response collectionFromMongoResponse =
                dalEndpoints.findCollection(getObjectMapperNotFailedWhenUnknownProperties().writeValueAsString(query));
        collectionFromMongoResponse.then().statusCode(SC_OK);
        return collectionFromMongoResponse.as(collectionModel);
    }

    @Step
    public <T> T findCollectionByWhereCondition(String condition, String collectionName, Class<T> collectionModel) throws JsonProcessingException {
        WhereCondition filter = new WhereCondition();
        filter.setWhere(condition);

        FindByCondition query = new FindByCondition();
        query.setCollection(collectionName);
        query.setFilter(filter);

        Response collectionFromMongoResponse =
                dalEndpoints.findCollection(getObjectMapperNotFailedWhenUnknownProperties().writeValueAsString(query));
        collectionFromMongoResponse.then().statusCode(SC_OK);
        return collectionFromMongoResponse.as(collectionModel);
    }
}
