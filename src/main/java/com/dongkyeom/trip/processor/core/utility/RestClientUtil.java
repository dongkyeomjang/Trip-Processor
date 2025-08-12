package com.dongkyeom.trip.processor.core.utility;

import com.dongkyeom.trip.processor.core.exception.error.ErrorCode;
import com.dongkyeom.trip.processor.core.exception.type.CommonException;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@Slf4j
@Component
public class RestClientUtil {

    private final RestClient restClient = RestClient.create();

    public JSONObject sendGetMethod(String url) {
        return new JSONObject(Objects.requireNonNull(restClient.get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new CommonException(ErrorCode.INVALID_ARGUMENT);
                })
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
                })
                .toEntity(JSONObject.class).getBody()));
    }
}
