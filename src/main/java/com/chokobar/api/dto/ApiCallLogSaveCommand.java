package com.chokobar.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiCallLogSaveCommand {

    private final String apiPath;
    private final String method;
    private final String requestHeaders;
    private final String requestBody;
    private final String responseBody;
    private final Integer statusCode;
    private final Long durationMs;
}
