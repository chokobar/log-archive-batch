package com.chokobar.api.filter;

import com.chokobar.api.service.ApiCallLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiLoggingFilter extends OncePerRequestFilter {

    private final ApiCallLogService apiCallLogService;
    private final ObjectMapper objectMapper;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();

        // 필요 없는 경로 제외
        return uri.startsWith("/css/")
                || uri.startsWith("/js/")
                || uri.startsWith("/images/")
                || uri.startsWith("/favicon")
                || uri.startsWith("/error");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request, 1024 * 1024);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            long endTime = System.currentTimeMillis();
            long durationMs = endTime - startTime;

            String apiPath = wrappedRequest.getRequestURI();
            String method = wrappedRequest.getMethod();
            String requestHeaders = extractHeadersAsJson(wrappedRequest);
            String requestBody = getRequestBody(wrappedRequest);
            String responseBody = getResponseBody(wrappedResponse);
            int statusCode = wrappedResponse.getStatus();

            log.info("API LOG apiPath={}, method={}, status={}, durationMs={}",
                    apiPath, method, statusCode, durationMs);

            try {
                apiCallLogService.save(
                        apiPath,
                        method,
                        requestHeaders,
                        requestBody,
                        responseBody,
                        statusCode,
                        durationMs
                );
            } catch (Exception e) {
                log.error("API 로그 DB 저장 실패", e);
            }

            wrappedResponse.copyBodyToResponse();
        }
    }

    private String extractHeadersAsJson(HttpServletRequest request) {
        Map<String, String> headerMap = new LinkedHashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String value = request.getHeader(headerName);

            // 민감정보 마스킹
            if ("authorization".equalsIgnoreCase(headerName)) {
                value = "****";
            }

            headerMap.put(headerName, value);
        }

        try {
            return objectMapper.writeValueAsString(headerMap);
        } catch (Exception e) {
            log.error("헤더 JSON 변환 실패", e);
            return "{}";
        }
    }

    private String getRequestBody(ContentCachingRequestWrapper request) {
        byte[] content = request.getContentAsByteArray();
        if (content.length == 0) {
            return null;
        }

        return new String(content, StandardCharsets.UTF_8);
    }

    private String getResponseBody(ContentCachingResponseWrapper response) {
        byte[] content = response.getContentAsByteArray();
        if (content.length == 0) {
            return null;
        }

        return new String(content, StandardCharsets.UTF_8);
    }
}