package com.api.feature.apilog.repository;

import com.api.feature.apilog.entity.ApiLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiLogRepository extends JpaRepository<ApiLog, Long>, ApiLogQuery {

}
