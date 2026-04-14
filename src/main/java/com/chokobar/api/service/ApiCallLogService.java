package com.chokobar.api.service;

import com.chokobar.api.dto.ApiCallLogSaveCommand;
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
    public void save(ApiCallLogSaveCommand command) {
        ApiCallLog log = ApiCallLog.builder()
                .apiPath(command.getApiPath())
                .method(command.getMethod())
                .requestHeaders(command.getRequestHeaders())
                .requestBody(command.getRequestBody())
                .responseBody(command.getResponseBody())
                .statusCode(command.getStatusCode())
                .durationMs(command.getDurationMs())
                .build();

        apiCallLogRepository.save(log);
    }
}