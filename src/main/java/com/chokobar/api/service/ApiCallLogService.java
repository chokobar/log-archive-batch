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
    public void save(String apiPath,
                     String method,
                     String requestHeaders,
                     String requestBody,
                     Integer statusCode) {

        ApiCallLog log = ApiCallLog.builder()
                .apiPath(apiPath)
                .method(method)
                .requestHeaders(requestHeaders)
                .requestBody(requestBody)
                .statusCode(statusCode)
                .build();

        apiCallLogRepository.save(log);
    }
}