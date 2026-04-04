package com.chokobar.api.repository;

import com.chokobar.api.entity.ApiCallLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiCallLogRepository extends JpaRepository<ApiCallLog, Long> {
}