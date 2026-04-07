//package com.chokobar.api.controller;
//
//import com.chokobar.api.service.ApiCallLogService;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Enumeration;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/logs")
//public class ApiCallLogController {
//
//    private final ApiCallLogService apiCallLogService;
//    private final ObjectMapper objectMapper;
//
//    @PostMapping
//    public ResponseEntity<String> createLog(HttpServletRequest request,
//                                            @RequestBody(required = false) String requestBody
//    ) {
//        String apiPath = request.getRequestURI();
//        String method = request.getMethod();
//        String requestHeaders = extractHeadersAsJson(request);
//
//        log.info("apiPath={}, method={}, requestBody={}", apiPath, method, requestBody);
//
//        apiCallLogService.save(
//                apiPath,
//                method,
//                requestHeaders,
//                requestBody,
//                200
//        );
//
//        return ResponseEntity.ok("로그 저장 완료");
//    }
//
//    private String extractHeadersAsJson(HttpServletRequest request) {
//        Map<String, String> headerMap = new LinkedHashMap<>();
//        Enumeration<String> headerNames = request.getHeaderNames();
//
//        while (headerNames.hasMoreElements()) {
//            String headerName = headerNames.nextElement();
//            headerMap.put(headerName, request.getHeader(headerName));
//        }
//
//        try {
//            return objectMapper.writeValueAsString(headerMap);
//        } catch (JsonProcessingException e) {
//            log.error("헤더 JSON 변환 실패", e);
//            return "{}";
//        }
//    }
//}