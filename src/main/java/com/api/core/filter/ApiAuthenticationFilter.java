package com.api.core.filter;

import com.api.feature.apilog.entity.ApiLog;
import com.api.core.exception.exception.ApiException;
import com.api.feature.apilog.sevice.ApiLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class ApiAuthenticationFilter extends OncePerRequestFilter {

    private static final String API_KEY = "ApiServiceKey";
    private final ApiLogService apiLogService;

    private static final String LOG_HEAD = "REQ START {}";
    private static final String LOG_END = "REQ END {} exTimeMs {}";

    public ApiAuthenticationFilter(ApiLogService apiLogService) {
        this.apiLogService = apiLogService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info(LOG_HEAD, request.getRequestURI());
        long startMs = System.currentTimeMillis();
        long endMs;
        if (isSwagger(request)) {
            filterChain.doFilter(request, response);
            endMs = System.currentTimeMillis();
            log.info(LOG_END, request.getRequestURI(), (endMs - startMs));
            return;
        }
        ContentCachingRequestWrapper requestToCache = new ContentCachingRequestWrapper(request);
        HttpServletResponse responseToCache = new ContentCachingResponseWrapper(response);

        String reqHeaders = getHeaders(requestToCache).toString();

        ApiLog apiLog = ApiLog.builder().reqIp(request.getRemoteAddr()).reqMethod(request.getMethod()).reqUrl(request.getRequestURI()).reqParam(request.getQueryString()).reqHeader(reqHeaders).build();

        try {
            String reqApiKey = requestToCache.getHeader(API_KEY);
            apiLog.setUpApiKeyAndToken(reqApiKey);

            filterChain.doFilter(requestToCache, responseToCache);
            String reqBody = getRequestBody(requestToCache);
            apiLog.setUpReqBody(reqBody);

            String responseBody = getResponseBody(responseToCache);
            apiLog.setUpResponse(response.getStatus(), responseBody);
            apiLogService.save(apiLog);
            endMs = System.currentTimeMillis();
            log.info(LOG_END, request.getRequestURI(), (endMs - startMs));
        } catch (ApiException apiException) {
            apiLog.setUpWhenApiKeyIsNull("apiFilterException");
            if (apiLog.existHealthCheckServerIp()) {
                log.info("ApiLog : {}", apiLog.printDetail());
            } else {
                apiLogService.saveException(apiLog, apiException.getStatusCode(), apiException.getMessage());
            }
            endMs = System.currentTimeMillis();
            log.error(LOG_END, request.getRequestURI(), (endMs - startMs));
            response.sendError(BAD_REQUEST.value(), apiException.getMessage());
        } catch (MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
            apiLogService.saveException(apiLog, BAD_REQUEST.value(), methodArgumentTypeMismatchException.getMessage());
            endMs = System.currentTimeMillis();
            log.error(LOG_END, request.getRequestURI(), (endMs - startMs));
            response.sendError(BAD_REQUEST.value(), BAD_REQUEST.getReasonPhrase());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Filter Exception : {} ", e.getMessage());
            endMs = System.currentTimeMillis();
            log.error(LOG_END, request.getRequestURI(), (endMs - startMs));
            apiLogService.saveException(apiLog, INTERNAL_SERVER_ERROR.value(), e.getMessage());
            response.sendError(INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR.getReasonPhrase());
        }
    }

    private static boolean isSwagger(HttpServletRequest request) {
        return request.getRequestURI().contains("swagger") || request.getRequestURI().contains("/api-docs") || request.getRequestURI().contains("favicon.ico");
    }

    private Map<String, Object> getHeaders(HttpServletRequest request) {
        Map<String, Object> headerMap = new HashMap<>();

        Enumeration<String> headerArray = request.getHeaderNames();
        while (headerArray.hasMoreElements()) {
            String headerName = headerArray.nextElement();
            headerMap.put(headerName, request.getHeader(headerName));
        }
        return headerMap;
    }

    private String getRequestBody(ContentCachingRequestWrapper request) {
        String reqBody = "-";
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                try {
                    reqBody = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                    reqBody = maskingRequestBody(reqBody);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    reqBody = "- fail" + e.getMessage();
                }
            }
        }
        return reqBody;
    }

    private String maskingRequestBody(String requestBody) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            LinkedHashMap<String, Object> map = mapper.readValue(requestBody, LinkedHashMap.class);
            if (map.containsKey("pwd")) {
                map.put("pwd", "****");
            }
            if (map.containsKey("password")) {
                map.put("pwd", "****");
            }
            JsonElement json = JsonParser.parseString(map.toString());
            requestBody = json.toString();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return requestBody;
    }

    private String getResponseBody(final HttpServletResponse response) throws IOException {
        String payload = null;
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (wrapper != null) {
            wrapper.setCharacterEncoding("UTF-8");
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                wrapper.copyBodyToResponse();
            }
        }
        return null == payload ? " - " : payload;
    }


}
