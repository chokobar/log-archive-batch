package com.chokobar.api.service;

import com.chokobar.api.entity.ApiCallLog;
import com.chokobar.api.repository.ApiCallLogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiCallLogService {

    private final ApiCallLogRepository apiCallLogRepository;

    @Transactional
    public void save(String apiPath, String method, String requestHeaders, String requestBody,
                     String responseBody, Integer statusCode, Long durationMs) {

        ApiCallLog log = ApiCallLog.builder()
                .apiPath(apiPath)
                .method(method)
                .requestHeaders(requestHeaders)
                .requestBody(requestBody)
                .responseBody(responseBody)
                .statusCode(statusCode)
                .durationMs(durationMs)
                .build();

        apiCallLogRepository.save(log);
    }
}