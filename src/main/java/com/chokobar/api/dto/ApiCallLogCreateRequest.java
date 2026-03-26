package com.chokobar.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiCallLogCreateRequest {

    private String apiPath;

    private String method;

    private String requestBody;

    private String responseBody;

    private Integer statusCode;
}