package com.chokobar.api.service;

import com.chokobar.api.entity.ApiCallLog;
import com.chokobar.api.repository.ApiCallLogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiCallLogService {

    private final ApiCallLogRepository apiCallLogRepository;

    @Transactional
    public void save(String apiPath, String method, String requestBody, String responseBody, Integer statusCode) {
        ApiCallLog log = ApiCallLog.builder()
                .apiPath(apiPath)
                .method(method)
                .requestBody(requestBody)
                .responseBody(responseBody)
                .statusCode(statusCode)
                .createdAt(LocalDateTime.now())
                .build();

        apiCallLogRepository.save(log);
    }
}