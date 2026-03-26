package com.chokobar.api.controller;

import com.chokobar.api.dto.ApiCallLogCreateRequest;
import com.chokobar.api.service.ApiCallLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/logs")
public class ApiCallLogController {

    private final ApiCallLogService apiCallLogService;

    @PostMapping
    public ResponseEntity<String> createLog(@RequestBody ApiCallLogCreateRequest request) {

        log.info("Create log: {}", request);

        apiCallLogService.save(
                request.getApiPath(),
                request.getMethod(),
                request.getRequestBody(),
                request.getResponseBody(),
                request.getStatusCode()
        );

        return ResponseEntity.ok("로그 저장 완료");
    }
}