package com.api.feature.apilog.sevice;

import com.api.core.enums.ApiExceptionEnum;
import com.api.feature.apilog.entity.ApiLog;
import com.api.feature.apilog.repository.ApiLogRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ApiLogService {

    private final ApiLogRepository apiLogRepository;

    public ApiLogService(ApiLogRepository apiLogRepository) {
        this.apiLogRepository = apiLogRepository;
    }


    @Transactional(rollbackOn = Exception.class)
    public void save(ApiLog apiLog) {
//        apiLogRepository.save(apiLog);
    }

    @Transactional(rollbackOn = Exception.class)
    public void saveException(ApiLog apiLog, ApiExceptionEnum apiExceptionEnum) {
        apiLog.setUpException(apiExceptionEnum.getCode(), apiExceptionEnum.getMessage());
//        apiLogRepository.save(apiLog);
    }

    @Transactional(rollbackOn = Exception.class)
    public void saveException(ApiLog apiLog, int code, String message) {
        apiLog.setUpException(code, message);
//        apiLogRepository.save(apiLog);
    }


}
